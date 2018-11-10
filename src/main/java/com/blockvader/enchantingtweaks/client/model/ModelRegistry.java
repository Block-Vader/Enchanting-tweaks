package com.blockvader.enchantingtweaks.client.model;

import com.blockvader.enchantingtweaks.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModelRegistry {
	
	@SubscribeEvent
	public void registerBlockModels(ModelRegistryEvent event)
	{
		//Vanilla
		registerRender(ModBlocks.acacia_book_container);
		registerRender(ModBlocks.birch_book_container);
		registerRender(ModBlocks.dark_oak_book_container);
		registerRender(ModBlocks.jungle_book_container);
		registerRender(ModBlocks.oak_book_container);
		registerRender(ModBlocks.spruce_book_container);
		//Thaumcraft
		registerRender(ModBlocks.greatwood_book_container);
		registerRender(ModBlocks.silverwood_book_container);
		//Natura
		registerRender(ModBlocks.maple_book_container);
		registerRender(ModBlocks.silverbell_book_container);
		registerRender(ModBlocks.amaranth_book_container);
		registerRender(ModBlocks.tigerwood_book_container);
		registerRender(ModBlocks.willow_book_container);
		registerRender(ModBlocks.eucalyptus_book_container);
		registerRender(ModBlocks.hopseed_book_container);
		registerRender(ModBlocks.sakura_book_container);
		registerRender(ModBlocks.redwood_book_container);
		registerRender(ModBlocks.ghostwood_book_container);
		registerRender(ModBlocks.bloodwood_book_container);
		registerRender(ModBlocks.darkwood_book_container);
		registerRender(ModBlocks.fusewood_book_container);
		//BoP
		registerRender(ModBlocks.sacred_oak_book_container);
		registerRender(ModBlocks.cherry_book_container);
		registerRender(ModBlocks.umbran_book_container);
		registerRender(ModBlocks.fir_book_container);
		registerRender(ModBlocks.ethereal_book_container);
		registerRender(ModBlocks.magic_book_container);
		registerRender(ModBlocks.mangrove_book_container);
		registerRender(ModBlocks.palm_book_container);
		registerRender(ModBlocks.pine_book_container);
		registerRender(ModBlocks.hellbark_book_container);
		registerRender(ModBlocks.jacaranda_book_container);
		registerRender(ModBlocks.mahagony_book_container);
		registerRender(ModBlocks.ebony_book_container);
	}
	
	private static void registerRender(Block block)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

}
