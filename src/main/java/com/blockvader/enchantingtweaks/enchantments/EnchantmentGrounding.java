package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentGrounding extends EnchantmentArrowDamage {
	
	
    public EnchantmentGrounding() {
		super(Rarity.UNCOMMON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
	}

    @Override
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 5 + 8 * (enchantmentLevel - 1);
    }
    
    @Override
    protected boolean canApplyTogether(Enchantment ench)
    {
    	if (ench == Enchantments.POWER) return false;
    	return super.canApplyTogether(ench);
    }
}
