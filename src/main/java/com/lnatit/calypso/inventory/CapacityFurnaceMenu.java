package com.lnatit.calypso.inventory;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.lnatit.calypso.inventory.InventoryRegistry.CAPACITY_FURNACE;
import static com.lnatit.calypso.inventory.MenuOffsets.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CapacityFurnaceMenu extends AbstractFurnaceMenu
{
    public static final int INGREDIENT_SLOT_START = 0;
    public static final int INGREDIENT_SLOT_END = 4;
    public static final int FUEL_SLOT_START = 4;
    public static final int FUEL_SLOT_END = 8;
    public static final int RESULT_SLOT_START = 8;
    public static final int RESULT_SLOT_END = 12;
    public static final int SLOT_COUNT = 12;
    public static final int DATA_COUNT = 4;
    private static final int INV_SLOT_START = 12;
    private static final int INV_SLOT_END = 39;
    private static final int USE_ROW_SLOT_START = 39;
    private static final int USE_ROW_SLOT_END = 48;

    public CapacityFurnaceMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(SLOT_COUNT), new SimpleContainerData(DATA_COUNT));
    }

    public CapacityFurnaceMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(CAPACITY_FURNACE.get(), RecipeType.SMELTING, RecipeBookType.FURNACE, containerId, playerInventory,
              container, data
        );
        checkContainerSize(container, SLOT_COUNT);
        checkContainerDataCount(data, DATA_COUNT);
        this.slots.clear();
        this.lastSlots.clear();
        this.remoteSlots.clear();

        // Ingredient Slots
        this.addSlot(new Slot(container, INGREDIENT_SLOT_START, CF_WORKSPACE_X + 75, CF_WORKSPACE_Y + 22));
        for (int i = 1; i < 4; i++) {
            this.addSlot(new Slot(container, INGREDIENT_SLOT_START + i, CF_WORKSPACE_X + 44 + 18 - i * 18, CF_WORKSPACE_Y + 18 + 2 - i * 2));
        }
        // Fuel Slots
        this.addSlot(new FurnaceFuelSlot(this, container, FUEL_SLOT_START, CF_WORKSPACE_X + 75, CF_WORKSPACE_Y + 57));
        for (int i = 1; i < 4; i++) {
            this.addSlot(new FurnaceFuelSlot(this, container, FUEL_SLOT_START + i, CF_WORKSPACE_X + 44 + 18 - i * 18, CF_WORKSPACE_Y + 53 + 2 - i * 2));
        }
        // Result Slots
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlot(new FurnaceResultSlot(playerInventory.player, container, RESULT_SLOT_START + 2 * j + i,
                                                   CF_WORKSPACE_X + 125 + 18 * i, CF_WORKSPACE_Y + 28 + 18 * j
                ));
            }
        }
        // Player's Inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, CF_INVENTORY_X + 8 + j * 18, CF_INVENTORY_Y + 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k, CF_INVENTORY_X + 8 + k * 18, CF_INVENTORY_Y + 142));
        }
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        super.fillCraftSlotsStackedContents(itemHelper);
    }

    @Override
    public void clearCraftingContent() {
        int index;
        for (index = INGREDIENT_SLOT_START; index < INGREDIENT_SLOT_END; index++) {
            this.getSlot(index).set(ItemStack.EMPTY);
        }
        for (index = RESULT_SLOT_START; index < RESULT_SLOT_END; index++) {
            this.getSlot(index).set(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean recipeMatches(RecipeHolder<AbstractCookingRecipe> recipe) {
        return super.recipeMatches(recipe);
    }

    @Override
    public int getResultSlotIndex() {
        return RESULT_SLOT_START;
    }

    @Override
    public int getGridWidth() {
        return super.getGridWidth();
    }

    @Override
    public int getGridHeight() {
        return super.getGridHeight();
    }

    @Override
    public int getSize() {
        return SLOT_COUNT;
    }

    @Override
    public boolean stillValid(Player player) {
        return super.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack target = slot.getItem();
            itemstack = target.copy();
            if (index >= RESULT_SLOT_START && index < RESULT_SLOT_END) {
                if (!this.moveItemStackTo(target, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(target, itemstack);
            }
            else if (index >= INV_SLOT_START) {
                if (this.canSmelt(target)) {
                    if (!this.moveItemStackTo(target, INGREDIENT_SLOT_START, INGREDIENT_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (this.isFuel(target)) {
                    if (!this.moveItemStackTo(target, FUEL_SLOT_START, FUEL_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < INV_SLOT_END) {
                    if (!this.moveItemStackTo(target, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < USE_ROW_SLOT_END) {
                    if (!this.moveItemStackTo(target, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.moveItemStackTo(target, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (target.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }

            if (target.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, target);
        }

        return itemstack;
    }

    @Override
    protected boolean canSmelt(ItemStack stack) {
        return super.canSmelt(stack);
    }

    @Override
    protected boolean isFuel(ItemStack stack) {
        return super.isFuel(stack);
    }

    @Override
    public float getBurnProgress() {
        return super.getBurnProgress();
    }

    @Override
    public float getLitProgress() {
        return super.getLitProgress();
    }

    @Override
    public boolean isLit() {
        return super.isLit();
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return super.getRecipeBookType();
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex < FUEL_SLOT_START || slotIndex >= FUEL_SLOT_END;
    }
}
