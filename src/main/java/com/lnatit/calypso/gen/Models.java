package com.lnatit.calypso.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.lnatit.calypso.Calypso.MOD_ID;

public class Models
{
    public static class ModelProvider extends ItemModelProvider
    {
        public ModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, MOD_ID, existingFileHelper);
        }

        // Register Item Models
        @Override
        protected void registerModels() {

        }
    }

    public static class StateProvider extends BlockStateProvider
    {
        public StateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
            super(output, MOD_ID, exFileHelper);
        }

        // Register Block Models
        @Override
        protected void registerStatesAndModels() {
//            this.simpleBlockWithItem();

        }
    }
}
