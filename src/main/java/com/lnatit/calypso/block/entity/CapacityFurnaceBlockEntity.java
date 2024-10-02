package com.lnatit.calypso.block.entity;

import com.lnatit.calypso.inventory.CapacityFurnaceMenu;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;

import static com.lnatit.calypso.block.BlockRegistry.CAPACITY_FURNACE_BETYPE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CapacityFurnaceBlockEntity extends AbstractFurnaceBlockEntity
{
    protected static final int SLOT_INGREDIENT = 0;
    protected static final int SLOT_FUEL = 4;
    protected static final int SLOT_RESULT = 8;
    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_FOR_SIDES = new int[]{4, 5, 6, 7};
    private static final int[] SLOTS_FOR_DOWN = new int[]{8, 9, 10, 11, 4, 5, 6, 7};

    public static final Component DEFAULT_NAME = Component.translatable("container.calypso.capacity_furnace");

    private boolean slotsChanged = true;
    private AbstractCookingRecipe recipe = null;

    public CapacityFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(CAPACITY_FURNACE_BETYPE.get(), pos, blockState, RecipeType.SMELTING);
        this.items = NonNullList.withSize(12, ItemStack.EMPTY);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new CapacityFurnaceMenu(containerId, inventory, this, this.dataAccess);
    }

    /**
     * Lit        Y   Y   Y   N   N   N   N
     * Input      Y   Y   N   Y   Y   Y   N
     * canBurn    Y   N   N   Y   Y   N   N
     * FuelAvl    /   /   /   Y   N   /   /
     * Action   Burn Rst Rst L>B  S  Rst Rst
     * *L: Lit  B: Burn  S: Shrink progress
     *
     * @param level       current level
     * @param pos         pos of the BE
     * @param state       blockState
     * @param blockEntity BE to tick
     */
    public static void serverTick(Level level, BlockPos pos, BlockState state, CapacityFurnaceBlockEntity blockEntity) {
        boolean litOld = blockEntity.isLit();
        boolean changed = false;
        int ingredientIndex = blockEntity.getInputSlot(SLOT_INGREDIENT);

        if (litOld) {
            blockEntity.litTime--;
        }

        if (blockEntity.slotsChanged) {
            blockEntity.rearrangeItems();
            ItemStack ingredient = blockEntity.items.get(ingredientIndex);

            if (blockEntity.recipe == null || !ingredient.isEmpty()) {
                blockEntity.recipe = blockEntity.quickCheck.getRecipeFor(blockEntity,
                                                                         level
                ).orElse(null);
            }

            blockEntity.slotsChanged = false;
        }
        boolean canBurn = canBurn(level.registryAccess(), blockEntity.recipe, blockEntity);

        if (!canBurn) {
            // Reset State
            blockEntity.cookingProgress = 0;
        }
        else {
            if (!blockEntity.isLit()) {
                // try to light the fire, else shrink the progress
                if (blockEntity.light()) {
                    changed = true;
                }
                else if (blockEntity.cookingProgress > 0) {
                    blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0,
                                                            blockEntity.cookingTotalTime
                    );
                }
            }

            if (blockEntity.isLit()) {
                blockEntity.cookingProgress++;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = getTotalCookTime(level, blockEntity);
                    if (burn(level.registryAccess(), blockEntity.recipe, blockEntity, ingredientIndex)) {
                        blockEntity.setRecipeUsed(blockEntity.recipe);
                    }

                    changed = true;
                }
            }
        }

        if (litOld != blockEntity.isLit()) {
            changed = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
            level.setBlock(pos, state, 3);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    // Only check whether the input is able to smelt & output slot is available.
    // Won't modify BlockEntity.
    private static boolean canBurn(RegistryAccess registryAccess, @javax.annotation.Nullable AbstractCookingRecipe recipe, CapacityFurnaceBlockEntity furnace) {
        ItemStack ingredient = furnace.items.get(SLOT_INGREDIENT);
        if (!ingredient.isEmpty() && recipe != null) {
            ItemStack simResult = recipe.assemble(furnace, registryAccess);
            return !simResult.isEmpty() && furnace.tryMergeToResult(simResult, true);
        }
        else {
            return false;
        }
    }

    private static boolean burn(RegistryAccess registryAccess, @javax.annotation.Nullable AbstractCookingRecipe recipe, CapacityFurnaceBlockEntity furnace, int ingredientSlot) {
        if (recipe != null) {
            ItemStack ingredient = furnace.items.get(ingredientSlot);
            ItemStack simResult = recipe.assemble(furnace, registryAccess);

            furnace.tryMergeToResult(simResult, false);

            if (ingredient.is(Blocks.WET_SPONGE.asItem()) && !furnace.items.get(
                    SLOT_FUEL).isEmpty() && furnace.items.get(SLOT_FUEL).is(Items.BUCKET)) {
                furnace.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            ingredient.shrink(1);
            // rearrange the ingredients
            if (ingredient.isEmpty()) {
                furnace.slotsChanged = true;
                for (int i = ingredientSlot + 1; i < SLOT_FUEL; ++i) {
                    ItemStack curr = furnace.items.get(i);
                    furnace.items.set(i - 1, curr);
                    if (curr.isEmpty()) {
                        return true;
                    }
                }
                furnace.items.set(SLOT_FUEL - 1, ItemStack.EMPTY);
            }
            return true;
        }
        else {
            return false;
        }
    }

    private void mergeItems(int startSlot) {
        for (int i = startSlot; i < startSlot + 3; i++) {
            ItemStack stack = this.items.get(i);
            // make bucket not stackable
            int maxStackSize = stack.is(Items.BUCKET) ? 1 : Math.min(stack.getMaxStackSize(), this.getMaxStackSize());
            int diff = maxStackSize - stack.getCount();
            if (stack.isEmpty() || diff <= 0) {
                continue;
            }
            for (int j = i + 1; j <= startSlot + 3; j++) {
                ItemStack o = this.items.get(j);
                if (!o.isEmpty() && ItemStack.isSameItemSameTags(stack, o)) {
                    if (diff < o.getCount()) {
                        stack.grow(diff);
                        o.shrink(diff);
                    }
                    else {
                        stack.grow(o.getCount());
                        this.items.set(j, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    /**
     * Only used to rearrange player-put Items
     * Item-rearrangement related to burn logic should be handled locally
     */
    private void rearrangeItems() {
        mergeItems(SLOT_INGREDIENT);
        mergeItems(SLOT_FUEL);

        LinkedList<ItemStack> itemsCopy = new LinkedList<>(this.items);
        for (int index = SLOT_INGREDIENT + 2; index >= SLOT_INGREDIENT; index--) {
            if (itemsCopy.get(index).isEmpty()) {
                itemsCopy.remove(index);
                itemsCopy.add(SLOT_INGREDIENT + 3, ItemStack.EMPTY);
            }
        }

        int fuelOffset = isFuel(itemsCopy.get(SLOT_FUEL + 3)) ? 1 : 0;
        for (int index = SLOT_FUEL + 2; index >= SLOT_FUEL; index--) {
            ItemStack curr = itemsCopy.get(index);
            if (curr.isEmpty()) {
                itemsCopy.remove(index);
                itemsCopy.add(SLOT_FUEL + 3, ItemStack.EMPTY);
            }
            else {
                if (isFuel(curr)) {
                    fuelOffset = 1;
                }
                else if (fuelOffset > 0) {
                    if (index == SLOT_FUEL) {
                        itemsCopy.set(SLOT_FUEL, itemsCopy.get(SLOT_FUEL + fuelOffset));
                        itemsCopy.set(SLOT_FUEL + fuelOffset, curr);
                    }
                    else {
                        fuelOffset++;
                    }
                }
            }
        }

        for (int i = 0; i < 12; i++) {
            this.items.set(i, itemsCopy.get(i));
        }
    }

    private int getInputSlot(int slotStart) {
        ItemStack stack = this.items.get(slotStart);
        ItemStack nextStack;
        int i;
        for (i = slotStart; i < slotStart + 3; i++) {
            nextStack = this.items.get(i + 1);
            if (!ItemStack.isSameItemSameTags(stack, nextStack)) {
                break;
            }
            stack = nextStack;
        }
        return i;
    }

    private boolean tryMergeToResult(ItemStack stack, boolean simulate) {
        for (int i = SLOT_RESULT; i < SLOT_RESULT + 4; i++) {
            ItemStack curr = this.items.get(i);
            if (curr.isEmpty()) {
                if (!simulate) {
                    this.items.set(i, stack);
                }
                return true;
            }
            else if (ItemStack.isSameItemSameTags(curr,
                                                        stack
            ) && curr.getCount() + stack.getCount() <= curr.getMaxStackSize() && curr.getCount() <= this.getMaxStackSize()) {
                if (!simulate) {
                    curr.grow(stack.getCount());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Try to light the fire
     *
     * @return success or not
     */
    private boolean light() {
        int fuelIndex = this.getInputSlot(SLOT_FUEL);
        ItemStack fuel = this.items.get(fuelIndex);

        if (!fuel.isEmpty() && this.getBurnDuration(fuel) > 0) {
            if (fuel.hasCraftingRemainingItem()) {
                ItemStack remainder = fuel.getCraftingRemainingItem();

                if (!tryMergeToResult(remainder, false)) {
                    for (int i = SLOT_FUEL + 3; i >= fuelIndex; i--) {
                        ItemStack curr = this.items.get(i);
                        if (curr.isEmpty()) {
                            this.items.set(i, remainder);
                        }
                        else if (ItemStack.isSameItemSameTags(curr,
                                                                    remainder
                        ) && curr.getCount() + remainder.getCount() <= curr.getMaxStackSize() && curr.getCount() <= this.getMaxStackSize()) {
                            curr.grow(remainder.getCount());
                        }
                        else if (i == fuelIndex) {
                            return false;
                        }
                    }
                }
            }

            this.litTime = this.getBurnDuration(fuel);
            this.litDuration = this.litTime;
            fuel.shrink(1);

            if (fuel.isEmpty()) {
                LinkedList<ItemStack> itemsCopy = new LinkedList<>(items);

                if (fuelIndex != SLOT_FUEL) {
                    itemsCopy.remove(fuelIndex);
                    itemsCopy.add(SLOT_FUEL + 3, ItemStack.EMPTY);
                }
                else {
                    for (int i = SLOT_FUEL + 1; i <= SLOT_FUEL + 3; i++) {
                        ItemStack stack = this.items.get(i);
                        if (isFuel(stack)) {
                            itemsCopy.set(SLOT_FUEL, stack);
                            itemsCopy.remove(i);
                            itemsCopy.add(SLOT_FUEL + 3, ItemStack.EMPTY);
                            break;
                        }
                    }
                }

                for (int i = 0; i < 12; i++) {
                    this.items.set(i, itemsCopy.get(i));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        }
        else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction != Direction.DOWN || index >= SLOT_RESULT || stack.is(Items.WATER_BUCKET) || stack.is(
                Items.BUCKET);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.cookingTotalTime = getTotalCookTime(this.level, this);
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.slotsChanged = true;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index >= SLOT_RESULT && index <= SLOT_RESULT + 3) {
            return false;
        }
        else if (index >= SLOT_INGREDIENT && index <= SLOT_INGREDIENT + 3) {
            return true;
        }
        else {
            ItemStack itemstack = this.items.get(index);
            return stack.getBurnTime(RecipeType.SMELTING) > 0 || stack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }
}
