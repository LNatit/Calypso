package com.lnatit.calypso.gen;

import net.minecraft.resources.ResourceLocation;

import static com.lnatit.calypso.Calypso.MODID;

public class Utils
{
    public static ResourceLocation from(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }


}
