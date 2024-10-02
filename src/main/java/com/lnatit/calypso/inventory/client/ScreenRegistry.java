package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.InventoryRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.lnatit.calypso.Calypso.MODID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ScreenRegistry
{
    @SubscribeEvent
    public static void onScreenRegister(FMLCommonSetupEvent event) {
        MenuScreens.register(InventoryRegistry.CAPACITY_FURNACE.get(), CapacityFurnaceScreen::new);
        MenuScreens.register(InventoryRegistry.RECYCLE_BIN.get(), RecycleBinScreen::new);
    }
}
