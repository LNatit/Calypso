package com.lnatit.calypso.resource;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

import static com.lnatit.calypso.Calypso.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ResourceRegistry
{
    public static final PhotoStandManager PHOTO_STAND_MANAGER = new PhotoStandManager();

    @SubscribeEvent
    public static void onRegisterListener(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener(PHOTO_STAND_MANAGER);
    }
}
