package com.evandev.shutterup;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SHUTTERS = create("shutters");
        public static final TagKey<Block> WOODEN_SHUTTERS = create("wooden_shutters");

        public static final TagKey<Block> SABLE_QUARTER_VOLUME = TagKey.create(Registries.BLOCK, Constants.location("sable", "quarter_volume"));
        public static final TagKey<Block> SABLE_SUPER_LIGHT = TagKey.create(Registries.BLOCK, Constants.location("sable", "super_light"));

        private static TagKey<Block> create(String name) {
            return TagKey.create(Registries.BLOCK, Constants.id(name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SHUTTERS = create("shutters");
        public static final TagKey<Item> WOODEN_SHUTTERS = create("wooden_shutters");

        private static TagKey<Item> create(String name) {
            return TagKey.create(Registries.ITEM, Constants.id(name));
        }
    }
}
