package com.lnatit.calypso.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.lnatit.calypso.Calypso.MODID;

public class StatRegistry
{
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT,
                                                                                           MODID
    );

    public static final RegistryObject<ResourceLocation> INTERACT_WITH_CAPACITY_FURNACE = STATS.register(
            "interact_with_capacity_furnace",
            () -> new ResourceLocation(MODID, "interact_with_capacity_furnace")
    );
    public static final RegistryObject<ResourceLocation> INTERACT_WITH_RECYCLE_BIN = STATS.register(
            "interact_with_recycle_bin",
            () -> new ResourceLocation(MODID, "interact_with_recycle_bin")
    );
}
