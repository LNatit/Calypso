package com.lnatit.calypso.gui.client;

import com.lnatit.calypso.gui.GuiRegistry;
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
        event.register(GuiRegistry.CAPACITY_FURNACE.get(), CapacityFurnaceScreen::new);
    }
}
