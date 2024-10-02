package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.CapacityFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.lnatit.calypso.Calypso.MODID;

public class CapacityFurnaceScreen extends AbstractFurnaceScreen<CapacityFurnaceMenu>
{
    private static final ResourceLocation LIT_PROGRESS_SPRITE =
            new ResourceLocation(MODID, "textures/gui/sprites/container/capacity_furnace/lit_progress.png");
    private static final ResourceLocation BURN_PROGRESS_SPRITE =
            new ResourceLocation(MODID, "textures/gui/sprites/container/capacity_furnace/burn_progress.png");
    private static final ResourceLocation BACKGROUND =
            new ResourceLocation(MODID, "textures/gui/container/capacity_furnace.png");

    public static final ResourceLocation FOREGROUND =
            new ResourceLocation(MODID, "textures/gui/container/capacity_furnace_foreground.png");

    public CapacityFurnaceScreen(CapacityFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, new SmeltingRecipeBookComponent(), playerInventory, title, BACKGROUND);

        this.inventoryLabelX += 0;
        this.inventoryLabelY += 3;

        this.imageWidth = 176;
        this.imageHeight = 169;
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.blit(FOREGROUND, 0, 0, 0, 0, this.imageWidth, this.imageHeight);
        super.renderLabels(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int lit_progress = this.menu.getLitProgress();
            guiGraphics.blit(LIT_PROGRESS_SPRITE, i + 77, j + 42 + 15 - lit_progress, 0, 15 - lit_progress, 16, lit_progress, 16, 16);
        }

        int burn_progress = this.menu.getBurnProgress();
        guiGraphics.blit(BURN_PROGRESS_SPRITE, i + 98, j + 41, 0, 0, burn_progress, 16, 24, 16);
    }
}
