package com.lnatit.calypso.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.lnatit.calypso.Calypso.MODID;

public class Models
{
    public static class ModelProvider extends ItemModelProvider
    {
        public ModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, MODID, existingFileHelper);
        }

        // Register Item Models
        @Override
        protected void registerModels() {

        }
    }

    public static class StateProvider extends BlockStateProvider
    {
        public StateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
            super(output, MODID, exFileHelper);
        }

        // Register Block Models
        @Override
        protected void registerStatesAndModels() {
//            this.simpleBlockWithItem();

        }
    }
}
