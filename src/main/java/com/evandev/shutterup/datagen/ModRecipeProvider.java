package com.evandev.shutterup.datagen;

//? if fabric {
import com.evandev.shutterup.Constants;
import com.evandev.shutterup.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
//? if >=1.21 {
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
//?} else
//import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

//? if >=1.21 {
import java.util.concurrent.CompletableFuture;
//?} else
//import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    //? if >=1.21 {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
    //?} else {
    /*public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
    *///?}
        ModRegistry.ITEMS.forEach((name, itemSupplier) -> {
            if (name.contains("waxed_")) {
                String unwaxedName = name.replace("waxed_", "");
                Item unwaxedItem = ModRegistry.ITEMS.get(unwaxedName).get();
                ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, itemSupplier.get())
                        .requires(unwaxedItem)
                        .requires(Items.HONEYCOMB)
                        .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                        .save(exporter);
            } else if (name.equals("iron_shutter")) {
                ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, itemSupplier.get(), 2)
                        .pattern("P P")
                        .define('P', Items.IRON_INGOT)
                        .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                        .save(exporter);
            } else if (name.equals("copper_shutter")) {
                Item ingot = Items.COPPER_INGOT;

                ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, itemSupplier.get(), 2)
                        .pattern("P P")
                        .define('P', ingot)
                        .unlockedBy("has_copper_ingot", has(ingot))
                        .save(exporter);
            } else {
                String plankName = name.replace("_shutter", "_planks");
                Item plankItem = BuiltInRegistries.ITEM.get(Constants.location("minecraft", plankName));

                if (plankItem != Items.AIR) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, itemSupplier.get(), 4)
                            .pattern("P P")
                            .pattern("P P")
                            .define('P', plankItem)
                            .unlockedBy("has_planks", has(plankItem))
                            .save(exporter);
                }
            }
        });
    }
}
//?}
