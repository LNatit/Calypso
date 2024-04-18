package com.lnatit.calypso.fabric;

import com.lnatit.calypso.Calypso;
import net.fabricmc.api.ModInitializer;

public class CalypsoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Calypso.init();
    }
}