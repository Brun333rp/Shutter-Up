package com.evandev.shutterup.datagen;

import com.evandev.shutterup.ModRegistry;
import com.evandev.shutterup.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        FabricTagBuilder axeBuilder = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE);
        FabricTagBuilder pickaxeBuilder = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE);

        FabricTagBuilder needsStoneToolBuilder = getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL);
        FabricTagBuilder incorrectGoldBuilder = getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_GOLD_TOOL);
        FabricTagBuilder incorrectWoodenBuilder = getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_WOODEN_TOOL);

        FabricTagBuilder shuttersBuilder = getOrCreateTagBuilder(ModTags.Blocks.SHUTTERS).addTag(ModTags.Blocks.WOODEN_SHUTTERS);
        FabricTagBuilder woodenShuttersBuilder = getOrCreateTagBuilder(ModTags.Blocks.WOODEN_SHUTTERS);

        ModRegistry.BLOCKS.forEach((name, blockSupplier) -> {
            if (name.contains("iron") || name.contains("copper")) {
                pickaxeBuilder.add(blockSupplier.get());

                needsStoneToolBuilder.add(blockSupplier.get());
                incorrectGoldBuilder.add(blockSupplier.get());
                incorrectWoodenBuilder.add(blockSupplier.get());

                shuttersBuilder.add(blockSupplier.get());
            } else {
                axeBuilder.add(blockSupplier.get());

                woodenShuttersBuilder.add(blockSupplier.get());
            }
        });

        getOrCreateTagBuilder(ModTags.Blocks.SABLE_QUARTER_VOLUME).addTag(ModTags.Blocks.SHUTTERS);
        getOrCreateTagBuilder(ModTags.Blocks.SABLE_SUPER_LIGHT).addTag(ModTags.Blocks.SHUTTERS);
    }
}