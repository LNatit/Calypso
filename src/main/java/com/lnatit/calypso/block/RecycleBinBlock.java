package com.lnatit.calypso.block;

import com.lnatit.calypso.block.entity.CapacityFurnaceBlockEntity;
import com.lnatit.calypso.block.entity.RecycleBinBlockEntity;
import com.lnatit.calypso.misc.StatRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RecycleBinBlock extends BaseEntityBlock
{
    public static final MapCodec<RecycleBinBlock> CODEC = simpleCodec(RecycleBinBlock::new);

    private RecycleBinBlock(Properties properties) {
        super(properties);
    }

    public RecycleBinBlock()
    {
        this(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.5F));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RecycleBinBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof RecycleBinBlockEntity) {
                player.openMenu((MenuProvider) blockentity);
                player.awardStat(StatRegistry.INTERACT_WITH_RECYCLE_BIN.get());
            }
            return InteractionResult.CONSUME;
        }
    }
}
