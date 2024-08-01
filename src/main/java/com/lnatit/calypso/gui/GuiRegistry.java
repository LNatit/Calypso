package com.lnatit.calypso.gui;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MOD_ID;

public class GuiRegistry
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<CapacityFurnaceMenu>> CAPACITY_FURNACE = MENUS.register(
            "capacity_furnace", () -> new MenuType<>(CapacityFurnaceMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
