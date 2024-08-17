package com.lnatit.calypso.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;

import static com.lnatit.calypso.Calypso.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkRegistry
{
    public static final String NETWORK_VERSION = "1.0.0";

    @SubscribeEvent
    public static void onPayloadHandlerRegister(RegisterPayloadHandlersEvent event) {
//        event.registrar(NETWORK_VERSION).playToClient(
//                PhotoStandDataUpdatePacket.TYPE,
//                PhotoStandDataUpdatePacket.STREAM_CODEC,
//                new MainThreadPayloadHandler<>(PhotoStandDataUpdatePacket::handle)
//        );
    }
}
