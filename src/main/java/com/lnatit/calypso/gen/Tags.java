package com.lnatit.calypso.gen;

import com.lnatit.calypso.block.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.lnatit.calypso.Calypso.MODID;

public class Tags
{
    public static class TagProvider extends BlockTagsProvider
    {
        public TagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            // TODO update it occasionally
            tag(BlockRegistry.PHOTO_STAND)
                    .add(BlockRegistry.PHOTO_STAND_CORE.get(), BlockRegistry.PHOTO_STAND_CORE_2.get(),
                         BlockRegistry.PHOTO_STAND_CORE_1.get(), BlockRegistry.PHOTO_STAND_CORE_3.get(),
                         BlockRegistry.PHOTO_STAND_CORE_5.get(), BlockRegistry.PHOTO_STAND_CORE_6.get(),
                         BlockRegistry.PHOTO_STAND_PART.get()
                    );
        }
    }
}
