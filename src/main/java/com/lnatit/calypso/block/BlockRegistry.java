package com.lnatit.calypso.block;

import com.lnatit.calypso.block.entity.CapacityFurnaceBlockEntity;
import com.lnatit.calypso.block.entity.CutoutPhotoStandBlockEntity;
import com.lnatit.calypso.block.entity.RecycleBinBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MODID;

public class BlockRegistry
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<CapacityFurnaceBlock> CAPACITY_FURNACE = BLOCKS.register("capacity_furnace",
                                                                                               CapacityFurnaceBlock::new
    );
    public static final DeferredBlock<RecycleBinBlock> RECYCLE_BIN = BLOCKS.register("recycle_bin",
                                                                                     RecycleBinBlock::new
    );
    public static final DeferredBlock<CutoutPhotoStandBlock> CUTOUT_PHOTO_STAND = BLOCKS.register("cutout_photo_stand",
                                                                                                  CutoutPhotoStandBlock::new
    );

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CapacityFurnaceBlockEntity>> CAPACITY_FURNACE_BETYPE = BLOCK_ENTITY_TYPES.register(
            "capacity_furnace",
            () -> BlockEntityType.Builder.of(CapacityFurnaceBlockEntity::new, CAPACITY_FURNACE.get()).build(null)
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RecycleBinBlockEntity>> RECYCLE_BIN_BETYPE = BLOCK_ENTITY_TYPES.register(
            "recycle_bin", () -> BlockEntityType.Builder.of(RecycleBinBlockEntity::new, RECYCLE_BIN.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CutoutPhotoStandBlockEntity>> CUTOUT_PHOTO_STAND_BETYPE = BLOCK_ENTITY_TYPES.register(
            "cutout_photo_stand",
            () -> BlockEntityType.Builder.of(CutoutPhotoStandBlockEntity::new, CUTOUT_PHOTO_STAND.get()).build(null)
    );
}
