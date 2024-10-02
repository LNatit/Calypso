package com.lnatit.calypso.block.entity;

import com.lnatit.calypso.inventory.RecycleBinMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;

import static com.lnatit.calypso.block.BlockRegistry.RECYCLE_BIN_BETYPE;

public class RecycleBinBlockEntity extends BaseContainerBlockEntity
{
    public static final int SLOT_INPUT = 27;
    public static final Component DEFAULT_NAME = Component.translatable("container.calypso.recycle_bin");

    private int destoryCounter = 0;
    private NonNullList<ItemStack> items = NonNullList.withSize(28, ItemStack.EMPTY);
    private final ContainerData dataAccess = new ContainerData()
    {
        @Override
        public int get(int index) {
            if (index == 0) {
                return RecycleBinBlockEntity.this.destoryCounter;
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                RecycleBinBlockEntity.this.destoryCounter = value;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public RecycleBinBlockEntity(BlockPos pos, BlockState blockState) {
        super(RECYCLE_BIN_BETYPE.get(), pos, blockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        rearrangeItems();
        return new RecycleBinMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack remainder = ContainerHelper.removeItem(this.items, index, count);
        this.setChanged();
        return remainder;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack remainder =  ContainerHelper.takeItem(this.items, index);
        this.setChanged();
        return remainder;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.items.set(index, stack);
        this.setChanged();
    }

    private void rearrangeItems() {
        ItemStack input = this.items.get(SLOT_INPUT);
        if (!input.isEmpty()) {
            LinkedList<ItemStack> bufCopy = new LinkedList<>(this.items.subList(0, SLOT_INPUT));
            bufCopy.addFirst(input);
            int firstEmpty = SLOT_INPUT;
            int diff = Math.min(input.getMaxStackSize(), this.getMaxStackSize()) - input.getCount();
            for (int i = SLOT_INPUT; i > 0; i--) {
                ItemStack curr = bufCopy.get(i);
                if (curr.isEmpty()) {
                    firstEmpty = i;
                }
                else if (diff > 0 && ItemStack.isSameItemSameTags(curr, input)) {
                    int count = curr.getCount();
                    if (count > diff) {
                        curr.shrink(diff);
                        input.grow(diff);
                        diff = 0;
                    }
                    else {
                        bufCopy.set(i, ItemStack.EMPTY);
                        firstEmpty = i;
                        input.grow(count);
                        diff -= count;
                    }
                }
            }

            if (firstEmpty != SLOT_INPUT) {
                bufCopy.remove(firstEmpty);
            }

//            // Then try shift the queue
//            for (int i = 0; i < SLOT_INPUT; i++) {
//                // Remove one empty stack is enough
//                if (bufCopy.get(i).isEmpty()) {
//                    bufCopy.remove(i);
//                    break;
//                }
//            }


            for (int i = 0; i < SLOT_INPUT; i++) {
                this.items.set(i, bufCopy.get(i));
            }
            if (bufCopy.size() > SLOT_INPUT && !bufCopy.get(SLOT_INPUT).isEmpty()) {
                this.destoryCounter++;
            }
            this.items.set(SLOT_INPUT, ItemStack.EMPTY);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        rearrangeItems();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
