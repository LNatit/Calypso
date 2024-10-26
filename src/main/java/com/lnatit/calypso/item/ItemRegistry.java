package com.lnatit.calypso.item;

import com.lnatit.calypso.block.BlockRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lnatit.calypso.Calypso.MODID;

public class ItemRegistry
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> AFFLATUS = ITEMS.registerItem("afflatus", Item::new);
    public static final DeferredItem<DreamDiaryItem> DREAM_DIARY = ITEMS.registerItem("dream_diary", DreamDiaryItem::new);
    public static final DeferredItem<LeafShearsItem> LEAF_SHEARS = ITEMS.registerItem("leaf_shears", LeafShearsItem::new,
            new Item.Properties().durability(59).component(DataComponents.TOOL, LeafShearsItem.createToolProperties())
    );

    public static final DeferredItem<BlockItem> CAPACITY_FURNACE = ITEMS.registerSimpleBlockItem(BlockRegistry.CAPACITY_FURNACE);
    public static final DeferredItem<BlockItem> RECYCLE_BIN = ITEMS.registerSimpleBlockItem(BlockRegistry.RECYCLE_BIN);

    // WIP Contents...
    public static final DeferredItem<Item> TEST = ITEMS.registerItem("test", Item::new);
    public static final DeferredItem<BlockItem> PHOTO_STAND = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE);
    public static final DeferredItem<BlockItem> PHOTO_STAND_1 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_1);
    public static final DeferredItem<BlockItem> PHOTO_STAND_2 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_2);
    public static final DeferredItem<BlockItem> PHOTO_STAND_3 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_3);
    public static final DeferredItem<BlockItem> PHOTO_STAND_5 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_5);
    public static final DeferredItem<BlockItem> PHOTO_STAND_6 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_6);
}
