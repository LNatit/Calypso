package com.lnatit.calypso.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.lnatit.calypso.block.BlockRegistry.CUTOUT_PHOTO_STAND_BETYPE;

public class CutoutPhotoStandBlockEntity extends BlockEntity
{
    public CutoutPhotoStandBlockEntity(BlockPos pos, BlockState blockState) {
        super(CUTOUT_PHOTO_STAND_BETYPE.get(), pos, blockState);
    }
}
