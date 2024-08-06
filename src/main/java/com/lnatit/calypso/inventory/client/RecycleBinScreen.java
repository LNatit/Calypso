package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.RecycleBinMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RecycleBinScreen extends AbstractContainerScreen<RecycleBinMenu>
{
    public RecycleBinScreen(RecycleBinMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }
}
