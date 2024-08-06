package com.lnatit.calypso.inventory.client;

import com.lnatit.calypso.inventory.InventoryRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.lnatit.calypso.Calypso.MOD_ID;

@EventBusSubscriber(value = Dist.CLIENT, modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ScreenRegistry
{
    @SubscribeEvent
    public static void onScreenRegister(RegisterMenuScreensEvent event) {
        event.register(InventoryRegistry.CAPACITY_FURNACE.get(), CapacityFurnaceScreen::new);
        event.register(InventoryRegistry.RECYCLE_BIN.get(), RecycleBinScreen::new);
    }
}
