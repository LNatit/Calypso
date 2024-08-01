package com.lnatit.calypso.block;

import com.lnatit.calypso.block.entity.CapacityFurnaceBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MOD_ID;

public class BlockRegistry
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static final DeferredBlock<CapacityFurnaceBlock> CAPACITY_FURNACE = BLOCKS.register("capacity_furnace",
                                                                                               () -> new CapacityFurnaceBlock()
    );

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CapacityFurnaceBlockEntity>> CAPACITY_FURNACE_BETYPE = BLOCK_ENTITY_TYPES.register(
            "capacity_furnace",
            () -> BlockEntityType.Builder.of(CapacityFurnaceBlockEntity::new, CAPACITY_FURNACE.get()).build(null)
    );
}
