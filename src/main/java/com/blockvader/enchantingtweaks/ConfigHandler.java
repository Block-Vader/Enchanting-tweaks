package com.blockvader.enchantingtweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.blockvader.enchantingtweaks.eventhandler.ConfigEventHandler;
import com.google.common.collect.Maps;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
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
	public static ArrayList<PotionType> EffectList = new ArrayList<>();
	public static ArrayList<Class<? extends Entity >> FlyingEntityList = new ArrayList<>();
	public static boolean BoostProbability;
	public static boolean ModifyEnchantingTable;
	public static boolean ColourfulEnchantments;
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
		Property modifyenchantingtable = config.get(CATEGOTY_MAIN, "Modify enchanting table", true);
		modifyenchantingtable.setComment("Turning this off disables all enchanting table related features. Do it if you have any other mod, that changes how enchanting table works (like quark)");
		modifyenchantingtable.setLanguageKey(Main.MOD_ID + ".configgui.modify_enchanting_table");
		Property colourfulenchantments = config.get(CATEGOTY_MAIN, "Colourful enchantment names", true);
		colourfulenchantments.setComment("If this is true, enchantment name colours are based on rarity similar to item name colours");
		colourfulenchantments.setLanguageKey(Main.MOD_ID + ".configgui.colourful_enchantments");
		Property bookitems = config.get(CATEGOTY_MAIN, "Book items", new String[]{"minecraft:book", "minecraft:written_book", "minecraft:writable_book", "minecraft:enchanted_book", "minecraft:knowledge_book"});
		bookitems.setComment("List of items, which can be placed to bookshelves");
		bookitems.setLanguageKey(Main.MOD_ID + ".configgui.book_items");
		bookitems.setValidationPattern(Pattern.compile("[a-z|_]++:[a-z|_]++"));
		Property enchantability = config.get(CATEGOTY_MAIN, "Enchantability customization", new String[]{"minecraft:shield,1", "minecraft:iron_horse_armor,9", "minecraft:golden_horse_armor,25", "minecraft:diamond_horse_armor,10", 
				"minecraft:wooden_hoe,15", "minecraft:stone_hoe,5", "minecraft:iron_hoe,14", "minecraft:diamond_hoe,10", "minecraft:golden_hoe,22"});
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
		Property effectlist = config.get(CATEGOTY_MAIN, "Instability effects", new String[]{"minecraft:night_vision", "minecraft:invisibility", "minecraft:leaping", "minecraft:fire_resistance", "minecraft:swiftness",
				"minecraft:regeneration", "minecraft:healing", "minecraft:water_breathing", "minecraft:strength", "minecraft:weakness", "minecraft:slowness", "minecraft:poison", "minecraft:harming"});
		effectlist.setComment("Possible potion types instability enchantment can apply to shot arrows");
		effectlist.setLanguageKey(Main.MOD_ID + "configgui.instability_effect_list");
		effectlist.setValidationPattern(Pattern.compile("[a-z|_]++:[a-z|_]++"));
		Property flyingentitylist= config.get(CATEGOTY_MAIN, "Flying entities", new String[]{"minecraft:bat", "minecraft:ghast", "minecraft:parrot", "minecraft:ender_dragon", "minecraft:wither", "minecraft:vex"});
		flyingentitylist.setComment("List of entities that take extra damage from \"Grounding\" enchantment");
		flyingentitylist.setLanguageKey(Main.MOD_ID + "configgui.flying_entity_list");
		flyingentitylist.setValidationPattern(Pattern.compile("[a-z|_]++:[a-z|_]++"));
		Property version = config.get(CATEGOTY_MAIN, "Version", "1.2.1");
		version.setComment("Last version the config was loaded in. Necessary to update the config");
		if (version.getString().equals("1.2.0"))
		{
			System.out.println("Updating " + Main.MOD_ID + " config");
			ArrayList<String> list = new ArrayList<>(Arrays.asList(enchantability.getStringList()));
			list.addAll(Arrays.asList("minecraft:wooden_hoe,15", "minecraft:stone_hoe,5", "minecraft:iron_hoe,14", "minecraft:diamond_hoe,10", "minecraft:golden_hoe,22"));
			enchantability.set(list.stream().toArray(String[]::new));
			version.set("1.2.1");
		}
		if (readFields)
		{
			AllowedOnTable.clear();
			stringToMap(allowedontable, AllowedOnTable);
			
			AllowedOnAnvil.clear();
			stringToMap(allowedonanvil, AllowedOnAnvil);
			
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
			
			ModifyEnchantingTable = modifyenchantingtable.getBoolean();
			
			ColourfulEnchantments = colourfulenchantments.getBoolean();
			
			EffectList.clear();
			for (String s: effectlist.getStringList())
			{
				if (PotionType.getPotionTypeForName(s) != null)
				{
					EffectList.add(PotionType.getPotionTypeForName(s));
				}
				else
				{
					System.out.println("There is no potion type with id " + s);
				}
			}
			
			FlyingEntityList.clear();
			for (String s: flyingentitylist.getStringList())
			{
				if (EntityList.getClass(new ResourceLocation(s)) != null)
				{
					FlyingEntityList.add(EntityList.getClass(new ResourceLocation(s)));
				}
				else
				{
					System.out.println("There is no entity with id " + s);
				}
			}
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
            System.out.println(AllowedOnAnvil.get(item));
			return AllowedOnAnvil.get(item).contains(enchantment);
		}
		return false;
	}
}
