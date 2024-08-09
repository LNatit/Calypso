package com.lnatit.calypso.client;

import com.lnatit.calypso.block.BlockRegistry;
import com.lnatit.calypso.client.block.CutoutPhotoStandBlockEntityRenderer;
import com.lnatit.calypso.resource.ResourceRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import static com.lnatit.calypso.Calypso.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistry
{
    public static void onClientReloadListenerRegistry(RegisterClientReloadListenersEvent event) {

    }

    @SubscribeEvent
    public static void onModelRegistry(ModelEvent.RegisterAdditional event) {
        // 在这个事件里注册的模型json文件被读入为UnbakedModel，接着在ModelBakery中bake
        event.register(
                ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID, "photo_stand/default")));
    }

    @SubscribeEvent
    public static void onBlockEntityRendererRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockRegistry.CUTOUT_PHOTO_STAND_BETYPE.get(),
                                          CutoutPhotoStandBlockEntityRenderer::new
        );
    }

    @SubscribeEvent
    public static void onClientExtensionRegistry(RegisterClientExtensionsEvent event) {

    }
}
