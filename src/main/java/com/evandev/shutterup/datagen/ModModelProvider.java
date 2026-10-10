package com.evandev.shutterup.datagen;

//? if fabric {
import com.evandev.shutterup.Constants;
import com.evandev.shutterup.ModRegistry;
import com.evandev.shutterup.block.ShutterBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        ModelTemplate TEMPLATE_CLOSED = new ModelTemplate(
                Optional.of(Constants.id("block/template_shutter_closed")),
                Optional.empty(),
                TextureSlot.TEXTURE
        );
        ModelTemplate TEMPLATE_OPEN = new ModelTemplate(
                Optional.of(Constants.id("block/template_shutter_open")),
                Optional.empty(),
                TextureSlot.TEXTURE
        );
        ModelTemplate TEMPLATE_OPEN_LEFT_BLOCKED = new ModelTemplate(
                Optional.of(Constants.id("block/template_shutter_open_left_blocked")),
                Optional.empty(),
                TextureSlot.TEXTURE
        );
        ModelTemplate TEMPLATE_OPEN_RIGHT_BLOCKED = new ModelTemplate(
                Optional.of(Constants.id("block/template_shutter_open_right_blocked")),
                Optional.empty(),
                TextureSlot.TEXTURE
        );

        ModelTemplate TEMPLATE_OPEN_BOTH_BLOCKED = new ModelTemplate(
                Optional.of(Constants.id("block/template_shutter_open_both_blocked")),
                Optional.empty(),
                TextureSlot.TEXTURE
        );

        ModRegistry.BLOCKS.forEach((name, blockSupplier) -> {
            Block block = blockSupplier.get();
            if (block instanceof ShutterBlock) {
                generateShutter(generator, block, name, TEMPLATE_CLOSED, TEMPLATE_OPEN, TEMPLATE_OPEN_LEFT_BLOCKED, TEMPLATE_OPEN_RIGHT_BLOCKED, TEMPLATE_OPEN_BOTH_BLOCKED);
            }
        });
    }

    private void generateShutter(BlockModelGenerators generator, Block block, String name, ModelTemplate closedTemplate, ModelTemplate openTemplate, ModelTemplate leftBlockedTemplate, ModelTemplate rightBlockedTemplate, ModelTemplate bothBlockedTemplate) {
        String textureName = name.replace("waxed_", "");
        ResourceLocation textureLoc = Constants.id("block/" + textureName);
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.TEXTURE, textureLoc);

        ResourceLocation closedModel = closedTemplate.create(block, textureMapping, generator.modelOutput);
        ResourceLocation openModel = openTemplate.createWithSuffix(block, "_open", textureMapping, generator.modelOutput);
        ResourceLocation leftBlockedModel = leftBlockedTemplate.createWithSuffix(block, "_open_left_blocked", textureMapping, generator.modelOutput);
        ResourceLocation rightBlockedModel = rightBlockedTemplate.createWithSuffix(block, "_open_right_blocked", textureMapping, generator.modelOutput);
        ResourceLocation bothBlockedModel = bothBlockedTemplate.createWithSuffix(block, "_open_both_blocked", textureMapping, generator.modelOutput);

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.properties(ShutterBlock.FACE, ShutterBlock.FACING, ShutterBlock.OPEN, ShutterBlock.BLOCKED_LEFT, ShutterBlock.BLOCKED_RIGHT)
                        .generate((face, dir, open, left, right) -> {
                            VariantProperties.Rotation xRot = switch (face) {
                                case FLOOR -> VariantProperties.Rotation.R270;
                                case CEILING -> VariantProperties.Rotation.R90;
                                default -> VariantProperties.Rotation.R0;
                            };

                            VariantProperties.Rotation yRot = switch (dir) {
                                case EAST -> VariantProperties.Rotation.R90;
                                case SOUTH -> VariantProperties.Rotation.R180;
                                case WEST -> VariantProperties.Rotation.R270;
                                default -> VariantProperties.Rotation.R0;
                            };

                            ResourceLocation model;
                            if (!open) {
                                model = closedModel;
                            } else if (left && right) {
                                model = bothBlockedModel;
                            } else if (left) {
                                model = leftBlockedModel;
                            } else if (right) {
                                model = rightBlockedModel;
                            } else {
                                model = openModel;
                            }

                            return Variant.variant()
                                    .with(VariantProperties.MODEL, model)
                                    .with(VariantProperties.X_ROT, xRot)
                                    .with(VariantProperties.Y_ROT, yRot);
                        })
        ));
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        ModRegistry.ITEMS.forEach((name, itemSupplier) -> {
            String textureName = name.replace("waxed_", "");
            ResourceLocation itemTexture = Constants.id("item/" + textureName);

            ResourceLocation parent = Constants.location("minecraft", "item/generated");

            new ModelTemplate(Optional.of(parent), Optional.empty(), TextureSlot.LAYER0)
                    .create(ModelLocationUtils.getModelLocation(itemSupplier.get()), TextureMapping.layer0(itemTexture), generator.output);
        });
    }
}
//?}
