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

public class CapacityFurnaceScreen extends AbstractFurnaceScreen<CapacityFurnaceMenu>
{
    private static final ResourceLocation LIT_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                                      "container/capacity_furnace/lit_progress"
    );
    private static final ResourceLocation BURN_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                                       "container/capacity_furnace/burn_progress"
    );
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                             "textures/gui/container/capacity_furnace.png"
    );

    public static final ResourceLocation FOREGROUND = ResourceLocation.fromNamespaceAndPath(MODID,
                                                                                            "textures/gui/container/capacity_furnace_foreground.png"
    );

    public CapacityFurnaceScreen(CapacityFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, new SmeltingRecipeBookComponent(), playerInventory, title, BACKGROUND, LIT_PROGRESS_SPRITE,
              BURN_PROGRESS_SPRITE
        );

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
            int lit_progress = Mth.ceil(this.menu.getLitProgress() * 13.0F) + 1;
            guiGraphics.blitSprite(LIT_PROGRESS_SPRITE, 16, 16, 0, 15 - lit_progress, i + 77,
                                   j + 42 + 15 - lit_progress, 16, lit_progress
            );
        }

        int burn_progress = Mth.ceil(this.menu.getBurnProgress() * 24.0F);
        guiGraphics.blitSprite(BURN_PROGRESS_SPRITE, 24, 16, 0, 0, i + 98, j + 41,
                               burn_progress, 16
        );
    }
}
