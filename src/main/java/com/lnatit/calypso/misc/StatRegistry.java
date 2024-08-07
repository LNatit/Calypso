package com.lnatit.calypso.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MODID;

public class StatRegistry
{
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT,
                                                                                           MODID
    );

    public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_CAPACITY_FURNACE = STATS.register(
            "interact_with_capacity_furnace",
            () -> ResourceLocation.fromNamespaceAndPath(MODID, "interact_with_capacity_furnace")
    );
    public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_RECYCLE_BIN = STATS.register(
            "interact_with_recycle_bin",
            () -> ResourceLocation.fromNamespaceAndPath(MODID, "interact_with_recycle_bin")
    );
}
