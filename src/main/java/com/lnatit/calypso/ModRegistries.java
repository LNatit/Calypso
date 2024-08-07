package com.lnatit.calypso;

import com.lnatit.calypso.resource.photostand.PhotoStand;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static com.lnatit.calypso.Calypso.MODID;
import static net.minecraft.resources.ResourceKey.createRegistryKey;

public class ModRegistries
{
    public static final ResourceKey<Registry<PhotoStand>> PHOTO_STAND = createRegistryKey(ResourceLocation.fromNamespaceAndPath(
            MODID, "photo_stand"));

}
