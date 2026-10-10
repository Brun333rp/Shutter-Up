package com.evandev.shutterup.datagen;

//? if fabric {
import com.evandev.shutterup.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
//? if >=1.21 {
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
//?}
import java.util.regex.Pattern;

public class ModLangProvider extends FabricLanguageProvider {

    //? if >=1.21 {
    public ModLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
    //?} else {
    /*public ModLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
    *///?}
        translationBuilder.add("itemGroup.shutterup", "Shutter Up");
        translationBuilder.add("block_type.shutterup.shutter", "%s Shutter");

        translationBuilder.add("subtitles.block.generic.open", "Shutter opens");
        translationBuilder.add("subtitles.block.generic.close", "Shutter closes");

        ModRegistry.BLOCKS.forEach((name, blockSupplier) -> {
            String readableName = name.replace('_', ' ');
            readableName = Pattern.compile("\\b([a-z])").matcher(readableName)
                    .replaceAll(m -> m.group(1).toUpperCase());

            translationBuilder.add(blockSupplier.get(), readableName);
        });
    }
}
//?}
