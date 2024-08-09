package com.lnatit.calypso.resource;

import com.lnatit.calypso.network.PhotoStandDataUpdatePacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.lnatit.calypso.Calypso.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class ResourceRegistry
{
    public static final PhotoStandManager PHOTO_STAND_MANAGER = new PhotoStandManager();

    @SubscribeEvent
    public static void onRegisterListener(AddReloadListenerEvent event) {
        event.addListener(PHOTO_STAND_MANAGER);
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        PhotoStandDataUpdatePacket packet = PHOTO_STAND_MANAGER.generateUpdatePacket();
        if (event.getPlayer() == null) {
            PacketDistributor.sendToAllPlayers(packet);
        }
        else {
            PacketDistributor.sendToPlayer(event.getPlayer(), packet);
        }
    }
}
