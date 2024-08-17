package com.lnatit.calypso.inventory;

import com.lnatit.calypso.inventory.slot.RecycleSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import static com.lnatit.calypso.inventory.InventoryRegistry.RECYCLE_BIN;

public class RecycleBinMenu extends AbstractContainerMenu
{
    public static final int CONTAINER_SIZE = 28;
    public static final int INPUT_SLOT = 27;
    private static final int INV_SLOT_START = 28;
    private static final int INV_SLOT_END = 55;
    private static final int USE_ROW_SLOT_START = 55;
    private static final int USE_ROW_SLOT_END = 64;

    private final Container container;

    public RecycleBinMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(CONTAINER_SIZE));
    }

    public RecycleBinMenu(int containerId, Inventory playerInventory, Container container)
    {
        super(RECYCLE_BIN.get(), containerId);
        checkContainerSize(container, CONTAINER_SIZE);
        this.container = container;

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                this.addSlot(new RecycleSlot(container, k + j * 9, 8 + k * 18, 32 + j * 18));
            }
        }
        // the ordinal matters!!!
        this.addSlot(new Slot(container, INPUT_SLOT, 9, 8));

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 88 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 144));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack from = slot.getItem();
            itemstack = from.copy();
            if (index < INV_SLOT_START) {
                if (!this.moveItemStackTo(from, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                Slot input = this.slots.get(INPUT_SLOT);
                input.setByPlayer(from);
                input.setChanged();
                slot.set(ItemStack.EMPTY);
                slot.setChanged();
                return ItemStack.EMPTY;
            }

            if (from.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }
}
