package com.blockvader.enchantingtweaks.eventhandler;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;

import com.blockvader.enchantingtweaks.init.ModEnchantments;

import net.minecraft.block.BlockFrostedIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentHandler {
	private static final UUID modifierUUID = UUID.fromString("e6107045-134f-4c54-a645-75c3ae5c7a27");
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onBlock(LivingAttackEvent event)
	{
		if (!event.getEntity().world.isRemote)
		{
			EntityLivingBase living = event.getEntityLiving();
			boolean flag = getShieldEnchantmentLevel(living, ModEnchantments.spellproof) > 0;
			if (event.getAmount() > 0.0F && canBlockDamageSource(living, event.getSource(), flag))
	        {
				int l = getShieldEnchantmentLevel(living, ModEnchantments.counterattack);
				int rand = new Random().nextInt(4);
				if (l > rand)
				{
					living.addPotionEffect(new PotionEffect(ModEnchantments.counterattackeffect, 60));
				}
	        }
			if (event.getSource().getImmediateSource() instanceof EntityLivingBase)
			{
				EntityLivingBase attacker = (EntityLivingBase) event.getSource().getImmediateSource();
				attacker.removePotionEffect(ModEnchantments.counterattackeffect);
			}
		}
	}
	/*
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onKnockback(LivingKnockBackEvent event)
	{
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.BLAST_PROTECTION, event.getEntityLiving());
		event.setStrength(event.getOriginalStrength() * (1 - 0.15f * i));
	}*/
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onHurt(LivingHurtEvent event)
	{
		float amount = event.getAmount();
		EntityLivingBase living = event.getEntityLiving();
		if (event.getAmount() > 0.0F && canBlockDamageSource(living, event.getSource(), true))
        {
			int l = getShieldEnchantmentLevel(living, ModEnchantments.spellproof);
			if (l > 0)
			{
				event.setAmount(amount*(1.0f-(l/4.0F)));
				if (event.getEntityLiving() instanceof EntityPlayer)
				{
					damageShield(amount, (EntityPlayer)event.getEntityLiving());
				}
			}
        }
	}
	/*
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onProjectileHit(ProjectileImpactEvent event)
	{
		Entity entity = event.getRayTraceResult().entityHit;
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entity;
			int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, living);
			if (new Random().nextInt(20) < i*21)
			{
				event.getEntity().setDead();
				event.setCanceled(true);
			}
		}
	}*/
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		AttributeModifier modifier = new AttributeModifier(modifierUUID, "light_shield", 4, 2);
		if (entity.isActiveItemStackBlocking() && getShieldEnchantmentLevel(entity, ModEnchantments.weightless) > 0)
		{
			if (!entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(modifier))
			{
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(modifier);
			}
		}
		else 
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(modifier);
		}
		//Unfortunately, I can't correctly recreate effect of some armor enchantments, so I have to copy these enchantments on item in regular armor slot
		if (entity instanceof EntityHorse)
		{
			EntityHorse horse = (EntityHorse) event.getEntityLiving();
			if (horse.getHorseArmorType() != HorseArmorType.NONE)
			{
				ItemStack armor = this.getHorseArmor(horse);
				if (!EnchantmentHelper.getEnchantments(armor).isEmpty())
				{
					if (horse.getItemStackFromSlot(EntityEquipmentSlot.LEGS) == ItemStack.EMPTY)
					{
						ItemStack stack = new ItemStack(Items.STICK);
						EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(armor), stack);
						horse.setItemStackToSlot(EntityEquipmentSlot.FEET, stack);
						horse.setDropChance(EntityEquipmentSlot.FEET, -100.0f); //Makes sure the item will never be dropped, even with high looting level
					}
					else if (EnchantmentHelper.getEnchantments(armor) != EnchantmentHelper.getEnchantments(horse.getItemStackFromSlot(EntityEquipmentSlot.LEGS)))
					{
						ItemStack stack = new ItemStack(Items.LEATHER_BOOTS);
						EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(armor), stack);
						horse.setItemStackToSlot(EntityEquipmentSlot.FEET, stack);
					}
				}
				else
				{
					horse.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
				}
			}
		}
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FROST_WALKER, entity);

		if (i > 0)
		{
			this.freezeNearby(entity, entity.world, entity.getPosition(), i);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onFOVChange(FOVUpdateEvent event)
	{
		if (event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(modifierUUID) != null)
		{
			float f = 1.0F;
	
	        if (event.getEntity().capabilities.isFlying)
	        {
	            f *= 1.1F;
	        }
	
	        IAttributeInstance iattributeinstance = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
	        double value = (double) iattributeinstance.getAttributeValue() / 5;
	        f = (float)((double)f * ((value / (double)event.getEntity().capabilities.getWalkSpeed() + 1.0D) / 2.0D));
	
	        if (event.getEntity().capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
	        {
	            f = 1.0F;
	        }
	
	        if (event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() == Items.BOW)
	        {
	            int i = event.getEntity().getItemInUseMaxCount();
	            float f1 = (float)i / 20.0F;
	
	            if (f1 > 1.0F)
	            {
	                f1 = 1.0F;
	            }
	            else
	            {
	                f1 = f1 * f1;
	            }
	
	            f *= 1.0F - f1 * 0.15F;
	        }
	        
	        event.setNewfov(f);
		}
	}
	
	private boolean canBlockDamageSource(EntityLivingBase living, DamageSource damageSourceIn, boolean blocksUnblockable)
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
	
	private void damageShield(float damage, EntityPlayer player)
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
	
	public int getShieldEnchantmentLevel(EntityLivingBase living, Enchantment enchantment)
	{
		if (living.isActiveItemStackBlocking())
		{
			return EnchantmentHelper.getEnchantmentLevel(enchantment, living.getHeldItem(living.getActiveHand()));
		}
		return 0;
	}
	
	public static ItemStack getHorseArmor(EntityHorse horse)
	{
		Class clazz = horse.getClass();
		while (clazz != null)
		{
			if (clazz == EntityHorse.class)
			{
				Field f;
				try {
					f = clazz.getDeclaredField("HORSE_ARMOR_STACK");
					f.setAccessible(true);
			        try {
						DataParameter<ItemStack> armorparam = (DataParameter<ItemStack>) f.get(horse);
						ItemStack armor = horse.getDataManager().get(armorparam);
						return armor;
					} catch (IllegalArgumentException e)
			        {
						e.printStackTrace();
					} catch (IllegalAccessException e)
			        {
						e.printStackTrace();
					}
				} catch (NoSuchFieldException e1)
				{
					e1.printStackTrace();
				} catch (SecurityException e1)
				{
					e1.printStackTrace();
				}
			}
			clazz = clazz.getSuperclass();
		}
		return ItemStack.EMPTY;
	}
	
	public void freezeNearby(EntityLivingBase living, World worldIn, BlockPos pos, int level)
    {
        if (living.onGround)
        {
            float f = (float)Math.min(16, 2 + level);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f)))
            {
                if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f))
                {
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getMaterial() == Material.AIR)
                    {
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.WATER && (iblockstate1.getBlock() == net.minecraft.init.Blocks.WATER || iblockstate1.getBlock() == net.minecraft.init.Blocks.FLOWING_WATER) && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && worldIn.mayPlace(Blocks.FROSTED_ICE, blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity)null))
                        {
                            worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.FROSTED_ICE.getDefaultState());
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.FROSTED_ICE, MathHelper.getInt(living.getRNG(), 60, 120));
                        }
                        
                        if (iblockstate1.getBlock() == Blocks.FROSTED_ICE && iblockstate1.getValue(BlockFrostedIce.AGE) > 0)
                        {
                        	worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.FROSTED_ICE.getDefaultState());
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.FROSTED_ICE, MathHelper.getInt(living.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }
}
