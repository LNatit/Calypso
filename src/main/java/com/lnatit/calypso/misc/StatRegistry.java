package com.lnatit.calypso.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MOD_ID;

public class StatRegistry
{
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT,
                                                                                           MOD_ID
    );

    public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_CAPACITY_FURNACE = STATS.register(
            "interact_with_capacity_furnace",
            () -> ResourceLocation.fromNamespaceAndPath(MOD_ID, "interact_with_capacity_furnace")
    );
}
