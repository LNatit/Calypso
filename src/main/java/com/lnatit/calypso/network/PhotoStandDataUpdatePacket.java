package com.lnatit.calypso.network;

import com.lnatit.calypso.resource.ResourceRegistry;
import com.lnatit.calypso.resource.photostand.PhotoStand;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;

import static com.lnatit.calypso.Calypso.MODID;

public record PhotoStandDataUpdatePacket(HashMap<ResourceLocation, PhotoStand> data) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PhotoStandDataUpdatePacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MODID, "update_photo_stand_data"));
    public static final StreamCodec<ByteBuf, PhotoStandDataUpdatePacket> STREAM_CODEC = NeoForgeStreamCodecs.lazy(
            () -> StreamCodec.composite(
                    ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, PhotoStand.STREAM_CODEC),
                    PhotoStandDataUpdatePacket::data, PhotoStandDataUpdatePacket::new
            ));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final PhotoStandDataUpdatePacket packet, IPayloadContext context) {
        ResourceRegistry.PHOTO_STAND_MANAGER.acceptUpdatePacket(packet);
    }
}
