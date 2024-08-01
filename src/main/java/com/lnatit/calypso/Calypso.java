package com.lnatit.calypso;

import com.lnatit.calypso.block.BlockRegistry;
import com.lnatit.calypso.gui.GuiRegistry;
import com.lnatit.calypso.item.ItemRegistry;
import com.lnatit.calypso.misc.StatRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import static com.lnatit.calypso.Calypso.*;

@Mod(MOD_ID)
public class Calypso
{
    public static final String MOD_ID = "calypso";
    public static final Logger LOGGER = LogUtils.getLogger();


    public Calypso(IEventBus modBus, ModContainer container)
    {
        ItemRegistry.ITEMS.register(modBus);
        BlockRegistry.BLOCKS.register(modBus);
        BlockRegistry.BLOCK_ENTITY_TYPES.register(modBus);
        GuiRegistry.MENUS.register(modBus);
        StatRegistry.STATS.register(modBus);
    }
}
