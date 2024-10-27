package com.lnatit.calypso.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.lnatit.calypso.block.entity.RecycleBinBlockEntity;
import com.lnatit.calypso.misc.StatRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RecycleBinBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Shapes.join(
                            Block.box(2.0D, 0.0D, 3.0D, 14.0D, 13.0D, 13.0D),
                            Block.box(2.0D, 13.0D, 4.0D, 14.0D, 16.0D, 13.0D),
                            BooleanOp.OR
                    ).optimize(),
                    Direction.EAST, Shapes.join(
                            Block.box(3.0D, 0.0D, 2.0D, 13.0D, 13.0D, 14.0D),
                            Block.box(3.0D, 13.0D, 2.0D, 12.0D, 16.0D, 14.0D),
                            BooleanOp.OR
                    ).optimize(),
                    Direction.SOUTH, Shapes.join(
                            Block.box(2.0D, 0.0D, 3.0D, 14.0D, 13.0D, 13.0D),
                            Block.box(2.0D, 13.0D, 3.0D, 14.0D, 16.0D, 12.0D),
                            BooleanOp.OR
                    ).optimize(),
                    Direction.WEST, Shapes.join(
                            Block.box(3.0D, 0.0D, 2.0D, 13.0D, 13.0D, 14.0D),
                            Block.box(4.0D, 13.0D, 2.0D, 13.0D, 16.0D, 14.0D),
                            BooleanOp.OR
                    ).optimize()
            ));
    public static final MapCodec<RecycleBinBlock> CODEC = simpleCodec(RecycleBinBlock::new);

    private RecycleBinBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public RecycleBinBlock() {
        this(
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.METAL)
//                        .requiresCorrectToolForDrops()
                        .strength(3.5F)
                        .lightLevel(Blocks.litBlockEmission(13))
                        .noOcclusion()
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABBS.get(state.getValue(FACING));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction);
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
        }
        else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof RecycleBinBlockEntity) {
                player.openMenu((MenuProvider) blockentity);
                player.awardStat(StatRegistry.INTERACT_WITH_RECYCLE_BIN.get());
            }
            return InteractionResult.CONSUME;
        }
    }
}
