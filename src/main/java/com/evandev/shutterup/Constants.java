package com.evandev.shutterup;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "shutterup";
	public static final String MOD_NAME = "ShutterUp";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation id(String path) {
		return location(MOD_ID, path);
	}

	public static ResourceLocation location(String namespace, String path) {
		//? if >=1.21 {
		return ResourceLocation.fromNamespaceAndPath(namespace, path);
		//?} else
		//return new ResourceLocation(namespace, path);
	}
}
