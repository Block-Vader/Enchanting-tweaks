package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentInstabilityCurse extends Enchantment {
	
	public EnchantmentInstabilityCurse()
	{
		super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	public int getMinEnchantability(int enchantmentLevel)
    {
        return 15;
    }
	
	public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
    
    @Override
    public int getMaxLevel()
    {
    	return 1;
    }
    
    @Override
    public boolean isTreasureEnchantment()
    {
    	return true;
    }
    
    @Override
    public boolean isCurse()
    {
    	return true;
    }
}
