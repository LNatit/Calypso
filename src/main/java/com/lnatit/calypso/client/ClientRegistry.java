package com.lnatit.calypso.client;

import com.lnatit.calypso.resource.ResourceRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import static com.lnatit.calypso.Calypso.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistry
{
    @SubscribeEvent
    public static void onModelRegistry(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation resourceLocation : ResourceRegistry.PHOTO_STAND_MANAGER.getResourceLocations()){
            event.register(ModelResourceLocation.standalone(resourceLocation));
        }
    }
}
