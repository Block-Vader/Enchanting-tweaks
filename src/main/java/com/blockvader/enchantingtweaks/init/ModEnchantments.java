package com.blockvader.enchantingtweaks.init;

import com.blockvader.enchantingtweaks.PotionCounterattack;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentCounterattack;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentSpellproof;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentWeightless;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEnchantments {
	
	public static Enchantment spellproof;
	public static Enchantment weightless;
	public static Enchantment counterattack;
	
	public static Potion counterattackeffect;
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		counterattackeffect = new PotionCounterattack(false, 16695827, 0);
		counterattackeffect.setPotionName("effect.counterattack");
		counterattackeffect.setRegistryName("counterattack");
		counterattackeffect.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "26107045-abed-4c54-a645-75c3ae5c7a27", 0.2f, 2);
		event.getRegistry().register(counterattackeffect);
	}
	
	@SubscribeEvent
	public void registerEnchantments(RegistryEvent.Register<Enchantment> event)
	{
		spellproof = new EnchantmentSpellproof();
		weightless = new EnchantmentWeightless();
		counterattack = new EnchantmentCounterattack();
		
		event.getRegistry().register(spellproof);
		event.getRegistry().register(weightless);
		event.getRegistry().register(counterattack);
	}

}
