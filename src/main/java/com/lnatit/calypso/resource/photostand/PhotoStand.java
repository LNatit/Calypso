package com.lnatit.calypso.resource.photostand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record PhotoStand(Size size, List<Cutout> cutouts)
{
    public static final Codec<PhotoStand> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Size.CODEC.fieldOf("size").forGetter(o -> o.size),
            Cutout.CODEC.listOf().fieldOf("cutouts").forGetter(o -> o.cutouts)
    ).apply(ins, PhotoStand::new));
    public static final StreamCodec<ByteBuf, PhotoStand> STREAM_CODEC = StreamCodec.composite(
            Size.STREAM_CODEC,
            PhotoStand::size,
            ByteBufCodecs.collection(ArrayList::new, Cutout.STREAM_CODEC),
            PhotoStand::cutouts,
            PhotoStand::new
    );
}
