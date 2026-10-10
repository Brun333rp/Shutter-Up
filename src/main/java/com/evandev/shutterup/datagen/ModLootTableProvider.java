package com.evandev.shutterup.datagen;

//? if fabric {
import com.evandev.shutterup.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
//? if >=1.21 {
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
//?}

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    //? if >=1.21 {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
    //?} else {
    /*public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    *///?}

    @Override
    public void generate() {
        ModRegistry.BLOCKS.values().forEach(blockSupplier -> {
            dropSelf(blockSupplier.get());
        });
    }
}
//?}
