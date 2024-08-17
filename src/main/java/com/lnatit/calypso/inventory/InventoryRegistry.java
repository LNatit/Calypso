package com.lnatit.calypso.inventory;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MODID;

public class InventoryRegistry
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CapacityFurnaceMenu>> CAPACITY_FURNACE = MENUS.register(
            "capacity_furnace", () -> new MenuType<>(CapacityFurnaceMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<RecycleBinMenu>> RECYCLE_BIN = MENUS.register(
            "recycle_bin", () -> new MenuType<>(RecycleBinMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
