package com.lnatit.calypso.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

import static com.lnatit.calypso.Calypso.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        gen.addProvider(event.includeClient(), new Models.ModelProvider(output, helper));
        gen.addProvider(event.includeClient(), new Models.StateProvider(output, helper));

        gen.addProvider(event.includeClient(), new Tags.TagProvider(output, lookup, helper));

        gen.addProvider(event.includeServer(), new Loots.LootProvider(output));
    }
}
