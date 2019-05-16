package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.EnchantmentLootBonus;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentItemLink extends EnchantmentLootBonus{
	
	public EnchantmentItemLink() {
		super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
	}
}
