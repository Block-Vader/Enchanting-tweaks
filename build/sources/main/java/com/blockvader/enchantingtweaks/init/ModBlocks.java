package com.blockvader.enchantingtweaks.init;

import com.blockvader.enchantingtweaks.blocks.BlockBookshelfContainer;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	
	//Vanilla
	public static Block acacia_book_container;
	public static Block birch_book_container;
	public static Block dark_oak_book_container;
	public static Block jungle_book_container;
	public static Block oak_book_container;
	public static Block spruce_book_container;
	//Thaumcraft
	public static Block greatwood_book_container;
	public static Block silverwood_book_container;
	//Natura
	public static Block maple_book_container;
	public static Block silverbell_book_container;
	public static Block amaranth_book_container;
	public static Block tigerwood_book_container;
	public static Block willow_book_container;
	public static Block eucalyptus_book_container;
	public static Block hopseed_book_container;
	public static Block sakura_book_container;
	public static Block redwood_book_container;
	public static Block ghostwood_book_container;
	public static Block bloodwood_book_container;
	public static Block darkwood_book_container;
	public static Block fusewood_book_container;
	//BoP
	public static Block sacred_oak_book_container;
	public static Block cherry_book_container;
	public static Block umbran_book_container;
	public static Block fir_book_container;
	public static Block ethereal_book_container;
	public static Block magic_book_container;
	public static Block mangrove_book_container;
	public static Block palm_book_container;
	public static Block pine_book_container;
	public static Block hellbark_book_container;
	public static Block jacaranda_book_container;
	public static Block mahagony_book_container;
	public static Block ebony_book_container;
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();
		//Vanilla
		acacia_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(acacia_book_container, "acacia_book_container", registry);
		birch_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(birch_book_container, "birch_book_container", registry);
		dark_oak_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(dark_oak_book_container, "dark_oak_book_container", registry);
		jungle_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(jungle_book_container, "jungle_book_container", registry);
		oak_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(oak_book_container, "oak_book_container", registry);
		spruce_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(spruce_book_container, "spruce_book_container", registry);
		//Thaumcraft
		greatwood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(greatwood_book_container, "greatwood_book_container", registry);
		silverwood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(silverwood_book_container, "silverwood_book_container", registry);
		//Natura
		maple_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(maple_book_container, "maple_book_container", registry);
		silverbell_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(silverbell_book_container, "silverbell_book_container", registry);
		tigerwood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(tigerwood_book_container, "tigerwood_book_container", registry);
		amaranth_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(amaranth_book_container, "amaranth_book_container", registry);
		willow_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(willow_book_container, "willow_book_container", registry);
		eucalyptus_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(eucalyptus_book_container, "eucalyptus_book_container", registry);
		hopseed_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(hopseed_book_container, "hopseed_book_container", registry);
		sakura_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(sakura_book_container, "sakura_book_container", registry);
		redwood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(redwood_book_container, "redwood_book_container", registry);
		ghostwood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(ghostwood_book_container, "ghostwood_book_container", registry);
		bloodwood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(bloodwood_book_container, "bloodwood_book_container", registry);
		darkwood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(darkwood_book_container, "darkwood_book_container", registry);
		fusewood_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(fusewood_book_container, "fusewood_book_container", registry);
		//BoP
		sacred_oak_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(sacred_oak_book_container, "sacred_oak_book_container", registry);
		cherry_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(cherry_book_container, "cherry_book_container", registry);
		umbran_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(umbran_book_container, "umbran_book_container", registry);
		fir_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(fir_book_container, "fir_book_container", registry);
		ethereal_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(ethereal_book_container, "ethereal_book_container", registry);
		magic_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(magic_book_container, "magic_book_container", registry);
		mangrove_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(mangrove_book_container, "mangrove_book_container", registry);
		palm_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(palm_book_container, "palm_book_container", registry);
		pine_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(pine_book_container, "pine_book_container", registry);
		hellbark_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(hellbark_book_container, "hellbark_book_container", registry);
		jacaranda_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(jacaranda_book_container, "jacaranda_book_container", registry);
		mahagony_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(mahagony_book_container, "mahagony_book_container", registry);
		ebony_book_container = new BlockBookshelfContainer();
		registerBlockBookshelfContainer(ebony_book_container, "ebony_book_container", registry);
	}
	
	private static void registerBlockBookshelfContainer(Block block, String name, IForgeRegistry<Block> registry)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(name);
		registry.register(block);
	}
}
