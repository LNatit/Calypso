package com.lnatit.calypso.gen;

import com.lnatit.calypso.item.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.lnatit.calypso.block.BlockRegistry.*;

public class Loots
{
    public static class LootProvider extends LootTableProvider
    {
        public LootProvider(PackOutput output) {
            super(
                    output,
                    Set.of(CAPACITY_FURNACE.getId(), RECYCLE_BIN.getId()),
                    List.of(new SubProviderEntry(CustomBlockLoot::new, LootContextParamSets.BLOCK))
            );
        }

        @Override
        protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext context) {
            // 模组自定义的战利品表 DataProvider 必须覆盖此方法，以绕过对原版战利品表的检查
            map.forEach((key, value) -> value.validate(context));
        }
    }

    public static class CustomBlockLoot extends BlockLootSubProvider
    {
        protected CustomBlockLoot() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            this.dropOther(CAPACITY_FURNACE.get(), ItemRegistry.CAPACITY_FURNACE.get());
            this.dropOther(RECYCLE_BIN.get(), ItemRegistry.RECYCLE_BIN.get());
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            Set<Block> ret = new HashSet<>();
            ret.add(CAPACITY_FURNACE.get());
            ret.add(RECYCLE_BIN.get());
            // 模组自定义的方块战利品表必须覆盖此方法，以绕过对原版方块战利品表的检查（此处返回该模组的所有方块）
            return ret;
        }
    }
}
