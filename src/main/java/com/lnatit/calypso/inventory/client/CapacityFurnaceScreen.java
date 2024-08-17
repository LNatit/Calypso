package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.CapacityFurnaceMenu;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.lnatit.calypso.Calypso.MODID;

public class CapacityFurnaceScreen extends AbstractFurnaceScreen<CapacityFurnaceMenu>
{
    private static final ResourceLocation LIT_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "container/capacity_furnace/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "container/capacity_furnace/burn_progress");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/container/capacity_furnace.png");

    public CapacityFurnaceScreen(CapacityFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, new SmeltingRecipeBookComponent(), playerInventory, title, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();
    }
}
