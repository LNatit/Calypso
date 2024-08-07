package com.lnatit.calypso.resource.photostand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import static com.lnatit.calypso.Calypso.MODID;

public record Texture(int width, int height)
{
    public static final ResourceLocation EMPTY = ResourceLocation.fromNamespaceAndPath(MODID, "empty");

    public static final Codec<Texture> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.INT.fieldOf("width").forGetter(o -> o.width),
            Codec.INT.fieldOf("height").forGetter(o -> o.height)
    ).apply(ins, Texture::new));
    public static final StreamCodec<ByteBuf, Texture> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            Texture::width,
            ByteBufCodecs.VAR_INT,
            Texture::height,
            Texture::new
    );
}
