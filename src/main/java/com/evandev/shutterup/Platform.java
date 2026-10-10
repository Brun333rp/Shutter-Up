package com.evandev.shutterup;

//? if fabric {
import net.fabricmc.loader.api.FabricLoader;
//?} else if forge {
/*import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
*///?} else {
/*import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
*///?}

public final class Platform {
    private Platform() {
    }

    public static String getPlatformName() {
        //? if fabric {
        return "Fabric";
        //?} else if forge {
        /*return "Forge";
        *///?} else {
        /*return "NeoForge";
        *///?}
    }

    public static boolean isModLoaded(String modId) {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(modId);
        //?} else
        //return ModList.get().isLoaded(modId);
    }

    public static boolean isDevelopmentEnvironment() {
        //? if fabric {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
        //?} else
        //return !FMLLoader.isProduction();
    }
}
