package com.evandev.shutterup;

import com.evandev.shutterup.block.ShutterBlock;
import com.evandev.shutterup.block.WeatheringShutterBlock;
//? if <1.21 {
/*import net.minecraft.sounds.SoundEvents;
*///?}
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
//? if <1.21 {
/*import net.minecraft.world.level.block.SoundType;
*///?}
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModRegistry {
    public static final Map<String, Supplier<Block>> BLOCKS = new LinkedHashMap<>();
    public static final Map<String, Supplier<Item>> ITEMS = new LinkedHashMap<>();

    //? if >=1.21 {
    public static final BlockSetType COPPER = BlockSetType.COPPER;
    //?} else
    //public static final BlockSetType COPPER = new BlockSetType("shutterup:copper", true, SoundType.COPPER, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);

    public static final Supplier<Block> ACACIA_SHUTTER = registerBlock("acacia_shutter", BlockSetType.ACACIA, Blocks.ACACIA_TRAPDOOR);
    public static final Supplier<Block> BAMBOO_SHUTTER = registerBlock("bamboo_shutter", BlockSetType.BAMBOO, Blocks.BAMBOO_TRAPDOOR);
    public static final Supplier<Block> BIRCH_SHUTTER = registerBlock("birch_shutter", BlockSetType.BIRCH, Blocks.BIRCH_TRAPDOOR);
    public static final Supplier<Block> CHERRY_SHUTTER = registerBlock("cherry_shutter", BlockSetType.CHERRY, Blocks.CHERRY_TRAPDOOR);
    public static final Supplier<Block> CRIMSON_SHUTTER = registerBlock("crimson_shutter", BlockSetType.CRIMSON, Blocks.CRIMSON_TRAPDOOR);
    public static final Supplier<Block> DARK_OAK_SHUTTER = registerBlock("dark_oak_shutter", BlockSetType.DARK_OAK, Blocks.DARK_OAK_TRAPDOOR);
    public static final Supplier<Block> JUNGLE_SHUTTER = registerBlock("jungle_shutter", BlockSetType.JUNGLE, Blocks.JUNGLE_TRAPDOOR);
    public static final Supplier<Block> MANGROVE_SHUTTER = registerBlock("mangrove_shutter", BlockSetType.MANGROVE, Blocks.MANGROVE_TRAPDOOR);
    public static final Supplier<Block> OAK_SHUTTER = registerBlock("oak_shutter", BlockSetType.OAK, Blocks.OAK_TRAPDOOR);
    public static final Supplier<Block> SPRUCE_SHUTTER = registerBlock("spruce_shutter", BlockSetType.SPRUCE, Blocks.SPRUCE_TRAPDOOR);
    public static final Supplier<Block> WARPED_SHUTTER = registerBlock("warped_shutter", BlockSetType.WARPED, Blocks.WARPED_TRAPDOOR);

    public static final Supplier<Block> IRON_SHUTTER = registerBlock("iron_shutter", BlockSetType.IRON, Blocks.IRON_TRAPDOOR);

    public static final Supplier<Block> COPPER_SHUTTER = registerCopperBlock("copper_shutter", WeatheringCopper.WeatherState.UNAFFECTED, false);
    public static final Supplier<Block> EXPOSED_COPPER_SHUTTER = registerCopperBlock("exposed_copper_shutter", WeatheringCopper.WeatherState.EXPOSED, false);
    public static final Supplier<Block> WEATHERED_COPPER_SHUTTER = registerCopperBlock("weathered_copper_shutter", WeatheringCopper.WeatherState.WEATHERED, false);
    public static final Supplier<Block> OXIDIZED_COPPER_SHUTTER = registerCopperBlock("oxidized_copper_shutter", WeatheringCopper.WeatherState.OXIDIZED, false);

    public static final Supplier<Block> WAXED_COPPER_SHUTTER = registerCopperBlock("waxed_copper_shutter", WeatheringCopper.WeatherState.UNAFFECTED, true);
    public static final Supplier<Block> WAXED_EXPOSED_COPPER_SHUTTER = registerCopperBlock("waxed_exposed_copper_shutter", WeatheringCopper.WeatherState.EXPOSED, true);
    public static final Supplier<Block> WAXED_WEATHERED_COPPER_SHUTTER = registerCopperBlock("waxed_weathered_copper_shutter", WeatheringCopper.WeatherState.WEATHERED, true);
    public static final Supplier<Block> WAXED_OXIDIZED_COPPER_SHUTTER = registerCopperBlock("waxed_oxidized_copper_shutter", WeatheringCopper.WeatherState.OXIDIZED, true);

    private static Supplier<Block> registerCopperBlock(String name, WeatheringCopper.WeatherState state, boolean waxed) {
        Supplier<Block> blockSupplier = new Supplier<>() {
            private Block instance;

            @Override
            public Block get() {
                if (instance == null) {
                    BlockBehaviour.Properties props = copyProperties(Blocks.COPPER_BLOCK).noOcclusion();
                    instance = waxed ? new ShutterBlock(COPPER, props) : new WeatheringShutterBlock(state, COPPER, props);
                }
                return instance;
            }
        };

        Supplier<Item> itemSupplier = new Supplier<>() {
            private Item instance;

            @Override
            public Item get() {
                if (instance == null) {
                    instance = new BlockItem(blockSupplier.get(), new Item.Properties());
                }
                return instance;
            }
        };

        BLOCKS.put(name, blockSupplier);
        ITEMS.put(name, itemSupplier);

        return blockSupplier;
    }

    private static Supplier<Block> registerBlock(String name, BlockSetType type, Block baseTrapdoor) {
        Supplier<Block> blockSupplier = new Supplier<>() {
            private Block instance;

            @Override
            public Block get() {
                if (instance == null) {
                    instance = new ShutterBlock(type, copyProperties(baseTrapdoor).noOcclusion());
                }
                return instance;
            }
        };

        Supplier<Item> itemSupplier = new Supplier<>() {
            private Item instance;

            @Override
            public Item get() {
                if (instance == null) {
                    instance = new BlockItem(blockSupplier.get(), new Item.Properties());
                }
                return instance;
            }
        };

        BLOCKS.put(name, blockSupplier);
        ITEMS.put(name, itemSupplier);

        return blockSupplier;
    }

    private static BlockBehaviour.Properties copyProperties(Block block) {
        //? if >=1.21 {
        return BlockBehaviour.Properties.ofFullCopy(block);
        //?} else
        //return BlockBehaviour.Properties.copy(block);
    }

    public static void init() {
    }
}