package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.CapacityFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import static com.lnatit.calypso.Calypso.MODID;
import static com.lnatit.calypso.inventory.MenuOffsets.*;

public class CapacityFurnaceScreen extends AbstractFurnaceScreen<CapacityFurnaceMenu>
{
    private static final ResourceLocation LIT_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                                      "container/capacity_furnace/lit_progress"
    );
    private static final ResourceLocation BURN_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                                       "container/capacity_furnace/burn_progress"
    );
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                          "textures/gui/container/capacity_furnace.png"
    );

    public CapacityFurnaceScreen(CapacityFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, new SmeltingRecipeBookComponent(), playerInventory, title, TEXTURE, LIT_PROGRESS_SPRITE,
              BURN_PROGRESS_SPRITE
        );

        this.inventoryLabelX += CF_INVENTORY_X;
        this.inventoryLabelY += CF_INVENTORY_Y;
    }

    @Override
    public void init() {
        this.imageWidth = CF_TEXTURE_WIDTH;
        this.imageHeight = CF_TEXTURE_HEIGHT;

        super.init();
        this.clearWidgets();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int lit_progress = Mth.ceil(this.menu.getLitProgress() * 13.0F) + 1;
            guiGraphics.blitSprite(LIT_PROGRESS_SPRITE, 16, 16, 0, 16 - lit_progress, CF_WORKSPACE_X + i + 75,
                                   CF_WORKSPACE_Y + j + 39 + 16 - lit_progress, 16, lit_progress
            );
        }

        int burn_progress = Mth.ceil(this.menu.getBurnProgress() * 24.0F);
        guiGraphics.blitSprite(BURN_PROGRESS_SPRITE, 24, 16, 0, 0, CF_WORKSPACE_X + i + 96, CF_WORKSPACE_Y + j + 36,
                               burn_progress, 16
        );
    }
}
