package com.blockvader.enchantingtweaks.init;

import com.blockvader.enchantingtweaks.items.ItemBookshelfContainer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
	
	//Vanilla
	public static Item acacia_book_container;
	public static Item birch_book_container;
	public static Item dark_oak_book_container;
	public static Item jungle_book_container;
	public static Item oak_book_container;
	public static Item spruce_book_container;
	//Thaumcraft
	public static Item greatwood_book_container;
	public static Item silverwood_book_container;
	//Natura
	public static Item maple_book_container;
	public static Item silverbell_book_container;
	public static Item amaranth_book_container;
	public static Item tigerwood_book_container;
	public static Item willow_book_container;
	public static Item eucalyptus_book_container;
	public static Item hopseed_book_container;
	public static Item sakura_book_container;
	public static Item redwood_book_container;
	public static Item ghostwood_book_container;
	public static Item bloodwood_book_container;
	public static Item darkwood_book_container;
	public static Item fusewood_book_container;
	//BoP
	public static Item sacred_oak_book_container;
	public static Item cherry_book_container;
	public static Item umbran_book_container;
	public static Item fir_book_container;
	public static Item ethereal_book_container;
	public static Item magic_book_container;
	public static Item mangrove_book_container;
	public static Item palm_book_container;
	public static Item pine_book_container;
	public static Item hellbark_book_container;
	public static Item jacaranda_book_container;
	public static Item mahagony_book_container;
	public static Item ebony_book_container;
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();
		//Vanilla
		acacia_book_container = new ItemBookshelfContainer(ModBlocks.acacia_book_container);
		registerItemBlock(acacia_book_container, "acacia_book_container", registry);
		birch_book_container = new ItemBookshelfContainer(ModBlocks.birch_book_container);
		registerItemBlock(birch_book_container, "birch_book_container", registry);
		dark_oak_book_container = new ItemBookshelfContainer(ModBlocks.dark_oak_book_container);
		registerItemBlock(dark_oak_book_container, "dark_oak_book_container", registry);
		jungle_book_container = new ItemBookshelfContainer(ModBlocks.jungle_book_container);
		registerItemBlock(jungle_book_container, "jungle_book_container", registry);
		oak_book_container = new ItemBookshelfContainer(ModBlocks.oak_book_container);
		registerItemBlock(oak_book_container, "oak_book_container", registry);
		spruce_book_container = new ItemBookshelfContainer(ModBlocks.spruce_book_container);
		registerItemBlock(spruce_book_container, "spruce_book_container", registry);
		//Thaumcraft
		greatwood_book_container = new ItemBookshelfContainer(ModBlocks.greatwood_book_container);
		registerItemBlock(greatwood_book_container, "greatwood_book_container", registry);
		silverwood_book_container = new ItemBookshelfContainer(ModBlocks.silverwood_book_container);
		registerItemBlock(silverwood_book_container, "silverwood_book_container", registry);
		//Natura
		maple_book_container = new ItemBookshelfContainer(ModBlocks.maple_book_container);
		registerItemBlock(maple_book_container, "maple_book_container", registry);
		silverbell_book_container = new ItemBookshelfContainer(ModBlocks.silverbell_book_container);
		registerItemBlock(silverbell_book_container, "silverbell_book_container", registry);
		amaranth_book_container = new ItemBookshelfContainer(ModBlocks.amaranth_book_container);
		registerItemBlock(amaranth_book_container, "amaranth_book_container", registry);
		tigerwood_book_container = new ItemBookshelfContainer(ModBlocks.tigerwood_book_container);
		registerItemBlock(tigerwood_book_container, "tigerwood_book_container", registry);
		willow_book_container = new ItemBookshelfContainer(ModBlocks.willow_book_container);
		registerItemBlock(willow_book_container, "willow_book_container", registry);
		eucalyptus_book_container = new ItemBookshelfContainer(ModBlocks.eucalyptus_book_container);
		registerItemBlock(eucalyptus_book_container, "eucalyptus_book_container", registry);
		hopseed_book_container = new ItemBookshelfContainer(ModBlocks.hopseed_book_container);
		registerItemBlock(hopseed_book_container, "hopseed_book_container", registry);
		sakura_book_container = new ItemBookshelfContainer(ModBlocks.sakura_book_container);
		registerItemBlock(sakura_book_container, "sakura_book_container", registry);
		redwood_book_container = new ItemBookshelfContainer(ModBlocks.redwood_book_container);
		registerItemBlock(redwood_book_container, "redwood_book_container", registry);
		ghostwood_book_container = new ItemBookshelfContainer(ModBlocks.ghostwood_book_container);
		registerItemBlock(ghostwood_book_container, "ghostwood_book_container", registry);
		bloodwood_book_container = new ItemBookshelfContainer(ModBlocks.bloodwood_book_container);
		registerItemBlock(bloodwood_book_container, "bloodwood_book_container", registry);
		darkwood_book_container = new ItemBookshelfContainer(ModBlocks.darkwood_book_container);
		registerItemBlock(darkwood_book_container, "darkwood_book_container", registry);
		fusewood_book_container = new ItemBookshelfContainer(ModBlocks.fusewood_book_container);
		registerItemBlock(fusewood_book_container, "fusewood_book_container", registry);
		//BoP
		sacred_oak_book_container = new ItemBookshelfContainer(ModBlocks.sacred_oak_book_container);
		registerItemBlock(sacred_oak_book_container, "sacred_oak_book_container", registry);
		cherry_book_container = new ItemBookshelfContainer(ModBlocks.cherry_book_container);
		registerItemBlock(cherry_book_container, "cherry_book_container", registry);
		umbran_book_container = new ItemBookshelfContainer(ModBlocks.umbran_book_container);
		registerItemBlock(umbran_book_container, "umbran_book_container", registry);
		fir_book_container = new ItemBookshelfContainer(ModBlocks.fir_book_container);
		registerItemBlock(fir_book_container, "fir_book_container", registry);
		ethereal_book_container = new ItemBookshelfContainer(ModBlocks.ethereal_book_container);
		registerItemBlock(ethereal_book_container, "ethereal_book_container", registry);
		magic_book_container = new ItemBookshelfContainer(ModBlocks.magic_book_container);
		registerItemBlock(magic_book_container, "magic_book_container", registry);
		mangrove_book_container = new ItemBookshelfContainer(ModBlocks.mangrove_book_container);
		registerItemBlock(mangrove_book_container, "mangrove_book_container", registry);
		palm_book_container = new ItemBookshelfContainer(ModBlocks.palm_book_container);
		registerItemBlock(palm_book_container, "palm_book_container", registry);
		pine_book_container = new ItemBookshelfContainer(ModBlocks.pine_book_container);
		registerItemBlock(pine_book_container, "pine_book_container", registry);
		hellbark_book_container = new ItemBookshelfContainer(ModBlocks.hellbark_book_container);
		registerItemBlock(hellbark_book_container, "hellbark_book_container", registry);
		jacaranda_book_container = new ItemBookshelfContainer(ModBlocks.jacaranda_book_container);
		registerItemBlock(jacaranda_book_container, "jacaranda_book_container", registry);
		mahagony_book_container = new ItemBookshelfContainer(ModBlocks.mahagony_book_container);
		registerItemBlock(mahagony_book_container, "mahagony_book_container", registry);
		ebony_book_container = new ItemBookshelfContainer(ModBlocks.ebony_book_container);
		registerItemBlock(ebony_book_container, "ebony_book_container", registry);
	}
	
	private static void registerItemBlock(Item item, String name, IForgeRegistry<Item> registry)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(name);
		registry.register(item);
	}
}
