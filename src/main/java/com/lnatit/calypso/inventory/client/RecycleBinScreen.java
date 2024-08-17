package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.RecycleBinMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.lnatit.calypso.Calypso.MODID;

public class RecycleBinScreen extends AbstractContainerScreen<RecycleBinMenu>
{
    public static final ResourceLocation FLAME_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/sprites/burning_flame");
    public static final ResourceLocation SPARK_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/sprites/destruction_spark");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/container/recycle_bin.png");

    public RecycleBinScreen(RecycleBinMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }
}
