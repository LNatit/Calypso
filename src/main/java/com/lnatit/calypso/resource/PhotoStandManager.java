package com.lnatit.calypso.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.lnatit.calypso.ModRegistries;
import com.lnatit.calypso.resource.photostand.PhotoStand;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.*;

public class PhotoStandManager extends SimpleJsonResourceReloadListener
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().create();

    private HashMap<ResourceLocation, PhotoStand> photoStands = new HashMap<>();

    public PhotoStandManager() {
        super(GSON, Registries.elementsDirPath(ModRegistries.PHOTO_STAND));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        this.photoStands.clear();
        RegistryOps<JsonElement> jsonOps = this.makeConditionalOps();

        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            Optional<PhotoStand> result = PhotoStand.CODEC.parse(jsonOps, entry.getValue()).result();
            if (result.isPresent()) {
                this.photoStands.put(entry.getKey(), result.get());
            }
            else {
                LOGGER.warn("Couldn't parse photo_stand for {}", entry.getKey());
            }
        }
    }

    public PhotoStand getPhotoStand(ResourceLocation location) {
        return this.photoStands.get(location);
    }

    public void updatePhotoStands(HashMap<ResourceLocation, PhotoStand> photoStands) {
        this.photoStands.clear();
    }

    public Set<ResourceLocation> getResourceLocations() {
        return this.photoStands.keySet();
    }
}
