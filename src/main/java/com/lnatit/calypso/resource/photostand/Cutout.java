package com.lnatit.calypso.resource.photostand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public record Cutout(int pos, int offset)
{

    public static final Codec<Cutout> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.INT.fieldOf("pos").forGetter(o -> o.pos),
            Codec.INT.fieldOf("offset").forGetter(o -> o.offset)
    ).apply(ins, Cutout::new));
    public static final StreamCodec<ByteBuf, Cutout> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            Cutout::pos,
            ByteBufCodecs.VAR_INT,
            Cutout::offset,
            Cutout::new
    );


    public enum Pose implements StringRepresentable
    {
        STANDING("standing"),
        SNEAKING("sneaking");

        public static final Codec<Pose> CODEC = StringRepresentable.fromEnum(Pose::values);

        final String name;

        Pose(String name) {
            this.name = name;
        }

        @Override
        @NotNull
        public String getSerializedName() {
            return this.name;
        }
    }
}
