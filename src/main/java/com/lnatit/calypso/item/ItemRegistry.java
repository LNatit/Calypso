package com.lnatit.calypso.item;

import com.lnatit.calypso.block.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.lnatit.calypso.Calypso.MODID;

public class ItemRegistry
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

//    public static final RegistryObject<Item> TEST = ITEMS.register("test", Item::new);
    public static final RegistryObject<BlockItem> CAPACITY_FURNACE = registerSimpleBlockItem(BlockRegistry.CAPACITY_FURNACE);
    public static final RegistryObject<BlockItem> RECYCLE_BIN = registerSimpleBlockItem(BlockRegistry.RECYCLE_BIN);
//    public static final RegistryObject<BlockItem> PHOTO_STAND = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE);
//    public static final RegistryObject<BlockItem> PHOTO_STAND_1 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_1);
//    public static final RegistryObject<BlockItem> PHOTO_STAND_2 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_2);
//    public static final RegistryObject<BlockItem> PHOTO_STAND_3 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_3);
//    public static final RegistryObject<BlockItem> PHOTO_STAND_5 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_5);
//    public static final RegistryObject<BlockItem> PHOTO_STAND_6 = ITEMS.registerSimpleBlockItem(BlockRegistry.PHOTO_STAND_CORE_6);

    public static RegistryObject<BlockItem> registerSimpleBlockItem(RegistryObject<? extends Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
