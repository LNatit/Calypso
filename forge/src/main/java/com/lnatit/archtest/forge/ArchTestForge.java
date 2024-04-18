package com.lnatit.archtest.forge;

import com.lnatit.archtest.ArchTest;
import net.minecraftforge.fml.common.Mod;

@Mod(ArchTest.MOD_ID)
public class ArchTestForge {
    public ArchTestForge() {
        ArchTest.init();
    }
}