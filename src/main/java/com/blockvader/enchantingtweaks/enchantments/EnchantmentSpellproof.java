package com.blockvader.enchantingtweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EnchantmentSpellproof extends Enchantment {
	public EnchantmentSpellproof()
	{
		super(Rarity.RARE, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND});
	}
	
	/**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + 15 * (enchantmentLevel - 1);
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
    
    @Override
    public int getMaxLevel()
    {
    	return 3;
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
    	return stack.getItem() instanceof ItemShield;
    }
    
    public static boolean canBlockDamageSource(EntityLivingBase living, DamageSource damageSourceIn, boolean blocksUnblockable)
    {
		boolean flag = !damageSourceIn.isUnblockable();
		if (blocksUnblockable && !damageSourceIn.isDamageAbsolute()) flag = true;
        if ( flag && living.isActiveItemStackBlocking())
        {
            Vec3d vec3d = damageSourceIn.getDamageLocation();
            if (vec3d != null)
            {
                Vec3d vec3d1 = living.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(living.posX, living.posY, living.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

                if (vec3d2.dotProduct(vec3d1) < 0.0D)
                {
                    return true;
                }
            }
        }
    	
    	return false;
    }
	
	public static void damageShield(float damage, EntityPlayer player)
	{
	    if (damage >= 3.0F && player.getActiveItemStack().getItem() instanceof ItemShield)
	    {
	        ItemStack copyBeforeUse = player.getActiveItemStack().copy();
	        int i = 1 + MathHelper.floor(damage);
	        player.getActiveItemStack().damageItem(i, player);

	        if (player.getActiveItemStack().isEmpty())
	        {
	            EnumHand enumhand = player.getActiveHand();
	            net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, enumhand);

	            if (enumhand == EnumHand.MAIN_HAND)
	            {
	            	player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
	            }
	            else
	            {
	                player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
	            }

	            player.resetActiveHand();
	            player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
	        }
	    }
	}

}
