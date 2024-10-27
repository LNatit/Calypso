package com.lnatit.calypso.gen;

import com.google.common.collect.Iterables;
import com.lnatit.calypso.block.BlockRegistry;
import com.lnatit.calypso.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Loots {
    public static class LootProvider extends LootTableProvider {
        public LootProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, Set.of(), List.of(new SubProviderEntry(CustomBlockLoot::new, LootContextParamSets.BLOCK)), registries);
        }
    }

    public static class CustomBlockLoot extends BlockLootSubProvider {
        protected CustomBlockLoot(HolderLookup.Provider lookupProvider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
        }

        @Override
        protected void generate() {
            this.dropSelf(BlockRegistry.RECYCLE_BIN.get());
            this.add(BlockRegistry.CAPACITY_FURNACE.get(), block -> createSingleItemTable(ItemRegistry.CAPACITY_FURNACE.get()));
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Iterables.transform(BlockRegistry.BLOCKS.getEntries(), DeferredHolder::get);
        }
    }
}
