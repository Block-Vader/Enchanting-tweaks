package com.blockvader.enchantingtweaks.init;

import java.util.ArrayList;

import com.blockvader.enchantingtweaks.blocks.BlockBookshelfContainer;
import com.blockvader.enchantingtweaks.blocks.BlockFertilizedFarmland;
import com.blockvader.enchantingtweaks.items.ItemBookshelfContainer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModBlocks {
	
	//Vanilla
	public static Block ACACIA_BOOK_CONTAINER;
	public static Block BIRCH_BOOK_CONTAINER;
	public static Block DARK_OAK_BOOK_CONTAINER;
	public static Block JUNGLE_BOOK_CONTAINER;
	public static Block OAK_BOOK_CONTAINER;
	public static Block SPRUCE_BOOK_CONTAINER;
	//Thaumcraft
	public static Block GREATWOOD_BOOK_CONTAINER;
	public static Block SILVERWOOD_BOOK_CONTAINER;
	//Natura
	public static Block MAPLE_BOOK_CONTAINER;
	public static Block SILVERBELL_BOOK_CONTAINER;
	public static Block AMARANTH_BOOK_CONTAINER;
	public static Block TIGERWOOD_BOOK_CONTAINER;
	public static Block WILLOW_BOOK_CONTAINER;
	public static Block EUCALYPTUS_BOOK_CONTAINER;
	public static Block HOPSEED_BOOK_CONTAINER;
	public static Block SAKURA_BOOK_CONTAINER;
	public static Block REDWOOD_BOOK_CONTAINER;
	public static Block GHOSTWOOD_BOOK_CONTAINER;
	public static Block BLOODWOOD_BOOK_CONTAINER;
	public static Block DARKWOOD_BOOK_CONTAINER;
	public static Block FUSEWOOD_BOOK_CONTAINER;
	//BoP
	public static Block SACRED_OAK_BOOK_CONTAINER;
	public static Block CHERRY_BOOK_CONTAINER;
	public static Block UMBRAN_BOOK_CONTAINER;
	public static Block FIR_BOOK_CONTAINER;
	public static Block ETHEREAL_BOOK_CONTAINER;
	public static Block MAGIC_BOOK_CONTAINER;
	public static Block MANGROVE_BOOK_CONTAINER;
	public static Block PALM_BOOK_CONTAINER;
	public static Block PINE_BOOK_CONTAINER;
	public static Block HELLBARK_BOOK_CONTAINER;
	public static Block JACARANDA_BOOK_CONTAINER;
	public static Block MAHAGONY_BOOK_CONTAINER;
	public static Block EBONY_BOOK_CONTAINER;
	
	public static Block FERTILIZED_FARMLAND;
	
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		ACACIA_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("acacia_book_container");
		BIRCH_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("birch_book_container");
		DARK_OAK_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("dark_oak_book_container");
		FERTILIZED_FARMLAND = new BlockFertilizedFarmland().setRegistryName("fertilized_farmland");
		JUNGLE_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("jungle_book_container");
		OAK_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("oak_book_container");
		SPRUCE_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("spruce_book_container");
		GREATWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("greatwood_book_container");
		SILVERWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("silverwood_book_container");
		MAPLE_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("maple_book_container");
		SILVERBELL_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("silverbell_book_container");
		TIGERWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("tigerwood_book_container");
		AMARANTH_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("amaranth_book_container");
		WILLOW_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("willow_book_container");
		EUCALYPTUS_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("eucalyptus_book_container");
		HOPSEED_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("hopseed_book_container");
		SAKURA_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("sakura_book_container");
		REDWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("redwood_book_container");
		GHOSTWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("ghostwood_book_container");
		BLOODWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("bloodwood_book_container");
		DARKWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("darkwood_book_container");
		FUSEWOOD_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("fusewood_book_container");
		SACRED_OAK_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("sacred_oak_book_container");
		CHERRY_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("cherry_book_container");
		UMBRAN_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("umbran_book_container");
		FIR_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("fir_book_container");
		ETHEREAL_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("ethereal_book_container");
		MAGIC_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("magic_book_container");
		MANGROVE_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("mangrove_book_container");
		PALM_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("palm_book_container");
		PINE_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("pine_book_container");
		HELLBARK_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("hellbark_book_container");
		JACARANDA_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("jacaranda_book_container");
		MAHAGONY_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("mahagony_book_container");
		EBONY_BOOK_CONTAINER = new BlockBookshelfContainer().setRegistryName("ebony_book_container");
		
		blocks.add(ACACIA_BOOK_CONTAINER);
		blocks.add(BIRCH_BOOK_CONTAINER);
		blocks.add(DARK_OAK_BOOK_CONTAINER);
		blocks.add(FERTILIZED_FARMLAND);
		blocks.add(JUNGLE_BOOK_CONTAINER);
		blocks.add(OAK_BOOK_CONTAINER);
		blocks.add(SPRUCE_BOOK_CONTAINER);
		blocks.add(GREATWOOD_BOOK_CONTAINER);
		blocks.add(SILVERWOOD_BOOK_CONTAINER);
		blocks.add(MAPLE_BOOK_CONTAINER);
		blocks.add(SILVERBELL_BOOK_CONTAINER);
		blocks.add(AMARANTH_BOOK_CONTAINER);
		blocks.add(TIGERWOOD_BOOK_CONTAINER);
		blocks.add(WILLOW_BOOK_CONTAINER);
		blocks.add(EUCALYPTUS_BOOK_CONTAINER);
		blocks.add(HOPSEED_BOOK_CONTAINER);
		blocks.add(SAKURA_BOOK_CONTAINER);
		blocks.add(REDWOOD_BOOK_CONTAINER);
		blocks.add(GHOSTWOOD_BOOK_CONTAINER);
		blocks.add(BLOODWOOD_BOOK_CONTAINER);
		blocks.add(DARKWOOD_BOOK_CONTAINER);
		blocks.add(FUSEWOOD_BOOK_CONTAINER);
		blocks.add(SACRED_OAK_BOOK_CONTAINER);
		blocks.add(UMBRAN_BOOK_CONTAINER);
		blocks.add(CHERRY_BOOK_CONTAINER);
		blocks.add(FIR_BOOK_CONTAINER);
		blocks.add(ETHEREAL_BOOK_CONTAINER);
		blocks.add(MAGIC_BOOK_CONTAINER);
		blocks.add(MANGROVE_BOOK_CONTAINER);
		blocks.add(PALM_BOOK_CONTAINER);
		blocks.add(PINE_BOOK_CONTAINER);
		blocks.add(HELLBARK_BOOK_CONTAINER);
		blocks.add(JACARANDA_BOOK_CONTAINER);
		blocks.add(MAHAGONY_BOOK_CONTAINER);
		blocks.add(EBONY_BOOK_CONTAINER);
		
		for (Block block : blocks)
		{
			block.setUnlocalizedName(block.getRegistryName().toString().replaceAll(":", "."));
			event.getRegistry().register(block);
		}
	}
	
	@SubscribeEvent
	public void registerBlockItems(RegistryEvent.Register<Item> event)
	{
		for (Block block : blocks)
		{
			if (block != FERTILIZED_FARMLAND)
			event.getRegistry().register(new ItemBookshelfContainer(block).setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName()));
		}
	}
}
