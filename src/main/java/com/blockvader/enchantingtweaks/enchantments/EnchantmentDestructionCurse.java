package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentDestructionCurse extends Enchantment {
	
	public EnchantmentDestructionCurse()
	{
		super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
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
    
    @Override
    protected boolean canApplyTogether(Enchantment ench)
    {
    	if (ench == Enchantments.FORTUNE || ench == Enchantments.SILK_TOUCH) return false;
    	return super.canApplyTogether(ench);
    }

}
