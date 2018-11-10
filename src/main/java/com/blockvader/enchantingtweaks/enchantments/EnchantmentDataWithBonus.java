package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.WeightedRandom;

public class EnchantmentDataWithBonus extends WeightedRandom.Item
{
	/** Enchantment object associated with this EnchantmentData */
	public final Enchantment enchantment;
	/** Enchantment level associated with this EnchantmentData */
	public final int enchantmentLevel;

	public EnchantmentDataWithBonus(Enchantment enchantmentObj, int enchLevel, int bonus)
	{
		super(enchantmentObj.getRarity().getWeight() + Math.min(bonus, enchantmentObj.getMaxLevel()*6));
		this.enchantment = enchantmentObj;
		this.enchantmentLevel = enchLevel;
    }
}
