package com.lnatit.calypso.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PhotoStand extends HorizontalDirectionalBlock
{
    public static final VoxelShape STAND = box(-16, 0, -16, 32, 1, 32);
    public static final BooleanProperty LEFT_ATTACHED = BooleanProperty.create("left_attached");
    public static final BooleanProperty RIGHT_ATTACHED = BooleanProperty.create("right_attached");
    public static final BooleanProperty FRONT_ATTACHED = BooleanProperty.create("front_attached");

    protected PhotoStand() {
        super(Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instabreak().pushReaction(
                PushReaction.DESTROY).noOcclusion());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return STAND;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    public static class CoreBlock extends PhotoStand implements EntityBlock
    {
        public final byte width;
        public final VoxelShape shape = STAND;

        public static final BooleanProperty LEFT_ATTACHED = PhotoStand.LEFT_ATTACHED;
        public static final BooleanProperty RIGHT_ATTACHED = PhotoStand.RIGHT_ATTACHED;

        public CoreBlock(int width) {
            this.width = (byte) width;
            this.registerDefaultState(
                    this.stateDefinition.any().setValue(LEFT_ATTACHED, width > 1).setValue(RIGHT_ATTACHED, width > 2));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return null;
        }

        @Override
        public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
            if (direction.getAxis().isHorizontal()) {
                if (state.getValue(LEFT_ATTACHED) && direction.getClockWise() == state.getValue(FACING)) {
                    if (!neighborState.is(BlockRegistry.PHOTO_STAND_PART.get())) {
                        return Blocks.AIR.defaultBlockState();
                    }
                }

                if (state.getValue(RIGHT_ATTACHED) && direction.getCounterClockWise() == state.getValue(FACING)) {
                    if (!neighborState.is(BlockRegistry.PHOTO_STAND_PART.get())) {
                        return Blocks.AIR.defaultBlockState();
                    }
                }

                return state;
            }
            else {
                return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
        }

        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            Direction direction = context.getHorizontalDirection().getOpposite();
            // TODO check world border
            return this.defaultBlockState().setValue(FACING, direction);
        }

        @Override
        public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
            super.setPlacedBy(level, pos, state, placer, stack);
            if (!level.isClientSide && this.width > 1) {
                BlockPos target = pos;
                Direction direction = state.getValue(FACING).getCounterClockWise();
                for (int i = 1; i < width; i++) {
                    target = target.relative(direction, i);
                    BlockState part = BlockRegistry.PHOTO_STAND_PART.get().defaultBlockState()
                                                                    .setValue(FACING, direction)
                                                                    .setValue(FRONT_ATTACHED, i < width - 2);
                    level.setBlock(target, part, 3);
                    direction = direction.getOpposite();
                }
                level.blockUpdated(pos, Blocks.AIR);
                state.updateNeighbourShapes(level, pos, 3);
            }
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING, LEFT_ATTACHED, RIGHT_ATTACHED);
        }
    }

    public static class PartBlock extends PhotoStand
    {
        public static final BooleanProperty FRONT_ATTACHED = PhotoStand.FRONT_ATTACHED;

        public PartBlock() {
            this.registerDefaultState(this.stateDefinition.any().setValue(FRONT_ATTACHED, Boolean.FALSE));
        }

        @Override
        public RenderShape getRenderShape(BlockState state) {
            return RenderShape.INVISIBLE;
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            Direction direction = state.getValue(FACING).getOpposite();
            BlockPos attached = pos.relative(direction);
            BlockState attachedState = level.getBlockState(attached);
            if (attachedState.getBlock() instanceof PhotoStand) {
                Vec3i norm = direction.getNormal();
                VoxelShape shape = attachedState.getShape(level, attached, context);
                return shape.move(norm.getX(), norm.getY(), norm.getZ());
            }
            return super.getShape(state, level, pos, context);
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            Direction direction = state.getValue(FACING).getOpposite();
            BlockPos attached = pos.relative(direction);
            BlockState attachedState = level.getBlockState(attached);
            if (attachedState.getBlock() instanceof PhotoStand) {
                Vec3i norm = direction.getNormal();
                VoxelShape shape = attachedState.getCollisionShape(level, attached, context);
                return shape.move(norm.getX(), norm.getY(), norm.getZ());
            }
            return super.getCollisionShape(state, level, pos, context);
        }

        @Override
        public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
            if (direction == state.getValue(FACING).getOpposite() || state.getValue(
                    FRONT_ATTACHED) && direction == state.getValue(FACING)) {
                return neighborState.is(BlockRegistry.PHOTO_STAND) ? state : Blocks.AIR.defaultBlockState();
            }
            else {
                return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING, FRONT_ATTACHED);
        }
    }
}
