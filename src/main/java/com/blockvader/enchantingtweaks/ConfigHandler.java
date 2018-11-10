package com.blockvader.enchantingtweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.blockvader.enchantingtweaks.eventhandler.ConfigEventHandler;
import com.google.common.collect.Maps;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public class ConfigHandler {
	
	public static Configuration config;
	public static final String CATEGOTY_MAIN = "main";
	public static final String ITEM_TWEAKS = "tweaks";
	public static List<Item> BookItems = new ArrayList<>();
	
	public static Map<Item, List<Enchantment>> AllowedOnTable = Maps.<Item, List<Enchantment>>newLinkedHashMap();
	public static Map<Item, List<Enchantment>> AllowedOnAnvil = Maps.<Item, List<Enchantment>>newLinkedHashMap();
	public static Map<Item, Integer> Enchantability = Maps.<Item, Integer>newLinkedHashMap();
	public static boolean BoostProbability;
	public static int version;
	
	public static void Inti()
	{
		File configFile = new File(Loader.instance().getConfigDir(), Main.MOD_ID + ".cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}
	
	public static Configuration getConfig()
	{
		return config;
	}
	
	public static void clientPreInit()
	{
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}
	
	public static void syncFromFiles()
	{
		syncConfig(true, true);
	}
	
	public static void syncFromFields()
	{
		syncConfig(false, false);
	}
	
	public static void syncFromGui()
	{
		syncConfig(false, true);
	}
	
	public static void syncConfig(boolean loadFromFile, boolean readFields)
	{
		if (loadFromFile) config.load();
		Property boostprobability = config.get(CATEGOTY_MAIN, "Enchanted book effect", true);
		boostprobability.setComment("Wheather or not will enchanted books in bookshleves increase probability of you getting corresponding enchantment");
		boostprobability.setLanguageKey(Main.MOD_ID + ".configgui.enchanted_book_effect");
		Property bookitems = config.get(CATEGOTY_MAIN, "Book items", new String[]{"minecraft:book", "minecraft:written_book", "minecraft:writable_book", "minecraft:enchanted_book", "minecraft:knowledge_book"});
		bookitems.setComment("List of items, which can be placed to bookshelves");
		bookitems.setLanguageKey(Main.MOD_ID + ".configgui.book_items");
		Pattern pattern = Pattern.compile("[a-z|_]++:[a-z|_]++");
		bookitems.setValidationPattern(pattern);
		Property enchantability = config.get(CATEGOTY_MAIN, "Enchantability customization", new String[]{"minecraft:shield,1", "minecraft:iron_horse_armor,9", "minecraft:golden_horse_armor,25", "minecraft:diamond_horse_armor,10"});
		enchantability.setComment("Sets enchantability value for any item (setting it for non-enchantable item will make it enchantable)");
		enchantability.setLanguageKey(Main.MOD_ID + ".configgui.enchantability_customization");
		enchantability.setValidationPattern(Pattern.compile("[a-z|_]++:[a-z|_]++,[0-9]{1,2}"));
		Property allowedontable = config.get(CATEGOTY_MAIN, "Allowed on table", new String[]{
				"minecraft:iron_horse_armor[minecraft:protection,minecraft:projectile_protection,minecraft:blast_protection,minecraft:fire_protection,minecraft:feather_falling,minecraft:aqua_affinity,minecraft:depth_strider]",
				"minecraft:golden_horse_armor[minecraft:protection,minecraft:projectile_protection,minecraft:blast_protection,minecraft:fire_protection,minecraft:feather_falling,minecraft:aqua_affinity,minecraft:depth_strider]",
				"minecraft:diamond_horse_armor[minecraft:protection,minecraft:projectile_protection,minecraft:blast_protection,minecraft:fire_protection,minecraft:feather_falling,minecraft:aqua_affinity,minecraft:depth_strider]"});
		allowedontable.setComment("Overrides list of enchantments you can apply on item via enchanting table, format mod:item[mod:enchantment1,mod:enchantment2,...]");
		allowedontable.setLanguageKey(Main.MOD_ID + ".configgui.allowed_on_table");
		allowedontable.setValidationPattern(Pattern.compile("[a-z|_]++:[a-z|_]++\\[[a-z|_|,|:]*\\]"));
		Property allowedonanvil = config.get(CATEGOTY_MAIN, "Allowed on anvil", new String[]{
				"minecraft:iron_horse_armor[minecraft:protection,minecraft:projectile_protection,minecraft:blast_protection,minecraft:fire_protection,minecraft:feather_falling,minecraft:aqua_affinity,minecraft:frost_walker,minecraft:depth_strider]",
				"minecraft:golden_horse_armor[minecraft:protection,minecraft:projectile_protection,minecraft:blast_protection,minecraft:fire_protection,minecraft:feather_falling,minecraft:aqua_affinity,minecraft:frost_walker,minecraft:depth_strider]",
				"minecraft:diamond_horse_armor[minecraft:protection,minecraft:projectile_protection,minecraft:blast_protection,minecraft:fire_protection,minecraft:feather_falling,minecraft:aqua_affinity,minecraft:frost_walker,minecraft:depth_strider]",});
		allowedonanvil.setComment("Overrides list of enchantments you can apply on item via combining with enchanted book, format mod:item[mod:enchantment1,mod:enchantment2,...]");
		allowedonanvil.setLanguageKey(Main.MOD_ID + ".configgui.allowed_on_anvil");
		allowedonanvil.setValidationPattern(Pattern.compile("[a-z|_]++:[a-z|_]++\\[[a-z|_|,|:]*\\]"));
		Property version = config.get(CATEGOTY_MAIN, "Version", "1.1");
		version.setComment("Last version the config was loaded in. Necessary to update the config");
		if (readFields)
		{
			AllowedOnTable.clear();
			stringToMap(allowedontable, AllowedOnTable);
			
			AllowedOnAnvil.clear();
			stringToMap(allowedontable, AllowedOnAnvil);
			
			Enchantability.clear();
			for (String s: enchantability.getStringList())
			{
				String[] parts = s.split(",");
				if (Item.getByNameOrId(parts[0]) != null)
				{
					int i = Integer.parseInt(parts[1]);
					Enchantability.put(Item.getByNameOrId(parts[0]), i);
				}
				else
				{
					System.out.println("There is no item with id " + s);
				}
			}
			BookItems.clear();
			for (String s: bookitems.getStringList())
			{
				if (Item.getByNameOrId(s) != null)
				{
					BookItems.add(Item.getByNameOrId(s));
				}
				else
				{
					System.out.println("There is no item with id " + s);
				}
			}
			BoostProbability = boostprobability.getBoolean();
		}
		
		if (config.hasChanged()) 
		{
			config.save();
		}
	}
	
	private static void stringToMap(Property stringlist, Map<Item, List<Enchantment>> map)
	{
		for (String s1: stringlist.getStringList())
		{
			String[] parts = s1.split("\\[");
			if (Item.getByNameOrId(parts[0]) != null)
			{
				String s2 = StringUtils.remove(parts[1], ']');
				String[] parts2 = s2.split(",");
				List<Enchantment> list = new ArrayList<>();
				for (int i = 0; i < parts2.length; i++)
				{
					String s3 = parts2[i];
					if (Enchantment.getEnchantmentByLocation(s3) != null)
					{
						list.add(Enchantment.getEnchantmentByLocation(s3));
					}
					else
					{
						System.out.println("There is no enchantment with id " + s3);
					}
				}
				map.put(Item.getByNameOrId(parts[0]), list);
			}
			else
			{
				System.out.println("There is no item with id " + parts[0]);
			}
		}
	}
	
	public static boolean canApplyOnTable(Item item, Enchantment enchantment)
	{
		if (AllowedOnTable.containsKey(item))
		{
			return AllowedOnTable.get(item).contains(enchantment);
		}
		return false;
	}
	
	public static boolean canApplyOnAnvil(Item item, Enchantment enchantment)
	{
		if (AllowedOnAnvil.containsKey(item))
		{
			return AllowedOnAnvil.get(item).contains(enchantment);
		}
		return false;
	}
}
