package com.lnatit.calypso.block.entity;

import com.lnatit.calypso.inventory.RecycleBinMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;

import static com.lnatit.calypso.block.BlockRegistry.RECYCLE_BIN_BETYPE;

public class RecycleBinBlockEntity extends BaseContainerBlockEntity
{
    public static final int SLOT_INPUT = 27;
    public static final Component DEFAULT_NAME = Component.translatable("container.calypso.recycle_bin");

    private NonNullList<ItemStack> items = NonNullList.withSize(28, ItemStack.EMPTY);

    public RecycleBinBlockEntity(BlockPos pos, BlockState blockState) {
        super(RECYCLE_BIN_BETYPE.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        rearrangeItems();
        return new RecycleBinMenu(containerId, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    private void rearrangeItems() {
        ItemStack input = this.items.get(SLOT_INPUT);
        if (!input.isEmpty()) {
            // fill the last-put slot first
            LinkedList<ItemStack> bufCopy = new LinkedList<>(this.items.subList(0, SLOT_INPUT));
            bufCopy.add(0, input);
            int diff = Math.min(input.getMaxStackSize(), this.getMaxStackSize()) - input.getCount();
            for (int i = SLOT_INPUT; i > 0; i--) {
                ItemStack curr = bufCopy.get(i);
                if (!curr.isEmpty() && ItemStack.isSameItemSameComponents(curr, input)) {
                    int count = curr.getCount();
                    if (count > diff) {
                        curr.shrink(diff);
                        input.grow(diff);
                        break;
                    }
                    else {
                        bufCopy.remove(i);
                        input.grow(count);
                        diff -= count;
                        if (diff == 0) {
                            break;
                        }
                    }
                }
            }

            for (int i = 0; i < SLOT_INPUT; i++)
            {
                this.items.set(i, bufCopy.get(i));
            }
            this.items.set(SLOT_INPUT, ItemStack.EMPTY);
        }
    }

    @Override
    public void setChanged() {
        rearrangeItems();
        super.setChanged();
    }
}
