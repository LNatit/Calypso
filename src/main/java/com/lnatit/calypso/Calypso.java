package com.lnatit.calypso;

import com.lnatit.calypso.block.BlockRegistry;
import com.lnatit.calypso.inventory.InventoryRegistry;
import com.lnatit.calypso.item.ItemRegistry;
import com.lnatit.calypso.misc.StatRegistry;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.lnatit.calypso.Calypso.*;

@Mod(MODID)
public class Calypso
{
    public static final String MODID = "calypso";
    public static final Logger LOGGER = LogUtils.getLogger();


    public Calypso()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegistry.ITEMS.register(modBus);
        BlockRegistry.BLOCKS.register(modBus);
        BlockRegistry.BLOCK_ENTITY_TYPES.register(modBus);
        InventoryRegistry.MENUS.register(modBus);
        StatRegistry.STATS.register(modBus);
    }
}
