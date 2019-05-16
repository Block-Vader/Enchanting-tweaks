package com.blockvader.enchantingtweaks.init;

import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.PotionCounterattack;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentArea;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentCounterattack;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentDestructionCurse;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentFertilizer;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentGrounding;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentInstabilityCurse;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentItemLink;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentMagnetism;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentMeatHook;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentMomentum;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentReach;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentRegrowth;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentSpellproof;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentWeightless;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEnchantments {
	
	public static Enchantment AREA;
	public static Enchantment SPELLPROOF;
	public static Enchantment WEIGHTLESS;
	public static Enchantment COUNTERATTACK;
	public static Enchantment FERTILIZER;
	public static Enchantment GROUNDING;
	public static Enchantment ITEM_LINK;
	public static Enchantment MOMENTUM;
	public static Enchantment MAGNETIC;
	public static Enchantment MEAT_HOOK;
	public static Enchantment REACH;
	public static Enchantment REGROWTH;
	public static Enchantment DESTRUCTION_CURSE;
	public static Enchantment INSTABILITY_CURSE;
	
	public static Potion POTION_COUNTERATTACK;
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		POTION_COUNTERATTACK = new PotionCounterattack(false, 16695827, 0);
		POTION_COUNTERATTACK.setPotionName("effect.counterattack");
		POTION_COUNTERATTACK.setRegistryName("counterattack");
		POTION_COUNTERATTACK.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "26107045-abed-4c54-a645-75c3ae5c7a27", 0.2f, 2);
		event.getRegistry().register(POTION_COUNTERATTACK);
	}
	
	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event)
	{
		event.getRegistry().registerAll
		(
			AREA = new EnchantmentArea().setRegistryName("area").setName(Main.MOD_ID + ".area"),
			SPELLPROOF = new EnchantmentSpellproof().setRegistryName("spellproof").setName(Main.MOD_ID + ".spellproof"),
			WEIGHTLESS = new EnchantmentWeightless().setRegistryName("weightless").setName(Main.MOD_ID + ".weightless"),
			COUNTERATTACK = new EnchantmentCounterattack().setRegistryName("counterattack").setName(Main.MOD_ID + ".counterattack"),
			FERTILIZER = new EnchantmentFertilizer().setRegistryName("fertilizer").setName(Main.MOD_ID + ".fertilizer"),
			GROUNDING = new EnchantmentGrounding().setRegistryName("grounding").setName(Main.MOD_ID + ".grounding"),
			ITEM_LINK = new EnchantmentItemLink().setRegistryName("item_link").setName(Main.MOD_ID + ".item_link"),
			MOMENTUM = new EnchantmentMomentum().setRegistryName("momentum").setName(Main.MOD_ID + ".momentum"),
			MAGNETIC = new EnchantmentMagnetism().setRegistryName("magnetism").setName(Main.MOD_ID + ".magnetism"),
			MEAT_HOOK = new EnchantmentMeatHook().setRegistryName("meat_hook").setName(Main.MOD_ID + ".meat_hook"),
			REACH = new EnchantmentReach().setRegistryName("reach").setName(Main.MOD_ID + ".reach"),
			REGROWTH = new EnchantmentRegrowth().setRegistryName("regrowth").setName(Main.MOD_ID + ".regrowth"),
			DESTRUCTION_CURSE = new EnchantmentDestructionCurse().setRegistryName("destruction_curse").setName(Main.MOD_ID + ".destruction_curse"),
			INSTABILITY_CURSE = new EnchantmentInstabilityCurse().setRegistryName("instability_curse").setName(Main.MOD_ID + ".instability_curse")
		);
	}

}
