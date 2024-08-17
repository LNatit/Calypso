package com.lnatit.calypso.gen;

import com.lnatit.calypso.block.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.*;
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
            this.simpleBlock(BlockRegistry.PHOTO_STAND_PART.get(), models().getBuilder("photo_stand_part").renderType("cutout").ao(false));
            this.simpleBlockWithItem(BlockRegistry.PHOTO_STAND_CORE.get(), cubeAll(BlockRegistry.PHOTO_STAND_CORE.get()));
        }
    }
}
