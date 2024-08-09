package com.lnatit.calypso.item;

import com.lnatit.calypso.block.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MODID;

public class ItemRegistry
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> TEST = ITEMS.registerItem("test", Item::new);
    public static final DeferredItem<BlockItem> CAPACITY_FURNACE = ITEMS.registerSimpleBlockItem(BlockRegistry.CAPACITY_FURNACE);
    public static final DeferredItem<BlockItem> RECYCLE_BIN = ITEMS.registerSimpleBlockItem(BlockRegistry.RECYCLE_BIN);
    public static final DeferredItem<BlockItem> CUTOUT_PHOTO_STAND = ITEMS.registerSimpleBlockItem(BlockRegistry.CUTOUT_PHOTO_STAND);
}
