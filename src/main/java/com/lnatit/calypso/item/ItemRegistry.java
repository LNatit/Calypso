package com.lnatit.calypso.item;

import com.lnatit.calypso.block.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MOD_ID;

public class ItemRegistry
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<BlockItem> CAPACITY_FURNACE = ITEMS.registerSimpleBlockItem(BlockRegistry.CAPACITY_FURNACE);
    public static final DeferredItem<BlockItem> RECYCLE_BIN = ITEMS.registerSimpleBlockItem(BlockRegistry.RECYCLE_BIN);
}
