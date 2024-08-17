package com.lnatit.calypso.block;

import com.lnatit.calypso.block.entity.CapacityFurnaceBlockEntity;
import com.lnatit.calypso.block.entity.RecycleBinBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
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
    public static final DeferredBlock<PhotoStand.PartBlock> PHOTO_STAND_PART = BLOCKS.register("photo_stand_part", PhotoStand.PartBlock::new);
    public static final DeferredBlock<PhotoStand.CoreBlock> PHOTO_STAND_CORE = BLOCKS.register("photo_stand_core", () -> new PhotoStand.CoreBlock(4));
    public static final DeferredBlock<PhotoStand.CoreBlock> PHOTO_STAND_CORE_1 = BLOCKS.register("photo_stand_core_1", () -> new PhotoStand.CoreBlock(1));
    public static final DeferredBlock<PhotoStand.CoreBlock> PHOTO_STAND_CORE_2 = BLOCKS.register("photo_stand_core_2", () -> new PhotoStand.CoreBlock(2));
    public static final DeferredBlock<PhotoStand.CoreBlock> PHOTO_STAND_CORE_3 = BLOCKS.register("photo_stand_core_3", () -> new PhotoStand.CoreBlock(3));
    public static final DeferredBlock<PhotoStand.CoreBlock> PHOTO_STAND_CORE_5 = BLOCKS.register("photo_stand_core_5", () -> new PhotoStand.CoreBlock(5));
    public static final DeferredBlock<PhotoStand.CoreBlock> PHOTO_STAND_CORE_6 = BLOCKS.register("photo_stand_core_6", () -> new PhotoStand.CoreBlock(6));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CapacityFurnaceBlockEntity>> CAPACITY_FURNACE_BETYPE = BLOCK_ENTITY_TYPES.register(
            "capacity_furnace",
            () -> BlockEntityType.Builder.of(CapacityFurnaceBlockEntity::new, CAPACITY_FURNACE.get()).build(null)
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RecycleBinBlockEntity>> RECYCLE_BIN_BETYPE = BLOCK_ENTITY_TYPES.register(
            "recycle_bin", () -> BlockEntityType.Builder.of(RecycleBinBlockEntity::new, RECYCLE_BIN.get()).build(null)
    );

    public static final TagKey<Block> PHOTO_STAND = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, "photo_stand"));
}
