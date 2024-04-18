package com.lnatit.calypso.forge;

import com.lnatit.calypso.Calypso;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Calypso.MOD_ID)
public class CalypsoForge {
    public CalypsoForge() {
        Calypso.init();
    }
}