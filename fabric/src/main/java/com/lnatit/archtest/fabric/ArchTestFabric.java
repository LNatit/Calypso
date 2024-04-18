package com.lnatit.archtest.fabric;

import com.lnatit.archtest.ArchTest;
import net.fabricmc.api.ModInitializer;

public class ArchTestFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ArchTest.init();
    }
}