package com.blockvader.enchantingtweaks.eventhandler;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.Set;
import com.blockvader.enchantingtweaks.CapabilitySyncMessage;
import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.HookPlayerMessage;
import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.blocks.BlockFertilizedFarmland;
import com.blockvader.enchantingtweaks.capabilities.CapArrowPropertiesProvider;
import com.blockvader.enchantingtweaks.capabilities.CapMomentumProvider;
import com.blockvader.enchantingtweaks.capabilities.ICapArrowProperties;
import com.blockvader.enchantingtweaks.capabilities.ICapMomentum;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentMeatHook;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentReach;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentSpellproof;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentWeightless;
import com.blockvader.enchantingtweaks.init.ModBlocks;
import com.blockvader.enchantingtweaks.init.ModEnchantments;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFrostedIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentHandler {
	
	private static final ResourceLocation CAP_ARROW_PROPERTIES = new ResourceLocation(Main.MOD_ID, "extra_arrow_properties");
    private static final ResourceLocation CAP_MOMENTUM = new ResourceLocation(Main.MOD_ID, "time_mining");
	
	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof EntityArrow)
		{
			event.addCapability(CAP_ARROW_PROPERTIES, new CapArrowPropertiesProvider());
		}
		
		if (event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(CAP_MOMENTUM, new CapMomentumProvider());
		}
	}
	
	@SubscribeEvent
    public void applyRandomEffect(EntityJoinWorldEvent event)
    {
    	if (event.getEntity() instanceof EntityTippedArrow && !event.getWorld().isRemote)
    	{
    		EntityTippedArrow arrow = (EntityTippedArrow) event.getEntity();
			Entity shooter = arrow.shootingEntity;
			if (shooter instanceof EntityLivingBase)
			{
				Random random = ((EntityLivingBase)shooter).getRNG();
    			if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.INSTABILITY_CURSE, (EntityLivingBase) shooter) > 0 && random.nextBoolean())
    			{
    				int i = random.nextInt(ConfigHandler.EffectList.size());
    				PotionType potionIn = ConfigHandler.EffectList.get(i);
    				ItemStack effectSource = new ItemStack(Items.TIPPED_ARROW);
    				PotionUtils.addPotionToItemStack(effectSource, potionIn);
    				arrow.setPotionEffect(effectSource); //You cannot set tipped potion effect directly, it has to be taken from an item
    				arrow.pickupStatus = PickupStatus.CREATIVE_ONLY;  //Prevents tipped arrow farming;
    			}
			}
    	}
    }
	
	
	
	@SubscribeEvent
    public void onHoeUse(UseHoeEvent event)
	{
		ItemStack hoe = event.getCurrent();
		EntityPlayer player = event.getEntityPlayer();
		World worldIn = event.getWorld();
		BlockPos pos = event.getPos();
		if (worldIn.getBlockState(pos).getBlock() instanceof BlockCrops) pos = pos.down();
		int l1 = Math.min(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FERTILIZER, hoe), 3);
		int l2 = Math.min(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.AREA, hoe), 2);
		for (int x = -l2; x <= l2; x++)
		{
			for (int z = -l2; z <= l2; z++)
			{
				BlockPos pos1 = pos.add(x, 0, z);
				IBlockState state = worldIn.getBlockState(pos1);
	            Block block = state.getBlock();
	            if (worldIn.isAirBlock(pos1.up()) || worldIn.getBlockState(pos1.up()).getBlock() instanceof BlockCrops)
	            {
	                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
	                {
	                    this.setBlock(hoe, player, worldIn, pos1, l1);
	                }
	                if (block == Blocks.FARMLAND && l1 > 0)
	                {
	                    this.setBlock(hoe, player, worldIn, pos1, l1);
	                }
	                if (block == Blocks.DIRT)
	                {
	                    if ((BlockDirt.DirtType)state.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT)
	                    {
	                    	this.setBlock(hoe, player, worldIn, pos1, l1);
	                    }
	                }
	                if (player.getHeldItemMainhand().isEmpty()) return;
	            }
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void reduceMomentum(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityPlayer)
		{
			ICapMomentum cap = entity.getCapability(CapMomentumProvider.MOMENTUM, null);
			int momentum = cap.getMomentum();
			if (momentum > 0)
			{
				if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.MOMENTUM, entity) > 0)
				{
					cap.addTime(-1);
				}
				else
				{
					cap.setMomentum(0);
				}
				if (entity instanceof EntityPlayerMP)
				ETPacketHandler.NETWORK.sendTo(new CapabilitySyncMessage(cap.getMomentum()), (EntityPlayerMP) entity);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		EntityPlayer player = event.getPlayer();
		BlockPos pos = event.getPos();
		int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.MOMENTUM, player);
		if (l > 0)
		{
			ICapMomentum cap = player.getCapability(CapMomentumProvider.MOMENTUM, null);
			int m = cap.getMomentum();
			m = (int) Math.min(m + (16 * event.getState().getBlockHardness(event.getWorld(), event.getPos())), l * 100);
			cap.setMomentum(m);
			cap.setTime(200);
			if (player instanceof EntityPlayerMP)
			ETPacketHandler.NETWORK.sendTo(new CapabilitySyncMessage(cap.getMomentum()), (EntityPlayerMP) player);
		}
		int l2 = Math.min(EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.AREA, player), 2);
		if (event.getState().getBlock() instanceof BlockCrops && player.getHeldItemMainhand().getItem() instanceof ItemHoe)
		{
			if (l2 > 0)
			{
				event.setCanceled(true);
				for (int x = -l2; x <= l2; x++)
				{
					for (int z = -l2; z <= l2; z++)
					{
						BlockPos pos1 = pos.add(x, 0, z);
						IBlockState state = event.getWorld().getBlockState(pos1);
						if (state.getBlock() instanceof BlockCrops)
						{
							boolean b = (!player.isCreative() && state.getValue(BlockCrops.AGE) == 7);
							event.getWorld().destroyBlock(pos1, b);
							if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.REGROWTH, player) > 0)
							{
								event.getWorld().setBlockState(pos1, state.withProperty(BlockCrops.AGE, 0));
							}
							player.getHeldItemMainhand().damageItem(1, player);
							if (player.getHeldItemMainhand().isEmpty()) return;
						}
					}
				}
			}
			else if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.REGROWTH, player) > 0)
			{
				IBlockState state = event.getWorld().getBlockState(pos);
				boolean b = (!player.isCreative() && state.getValue(BlockCrops.AGE) == 7);
				event.getWorld().destroyBlock(pos, b);
				event.getWorld().setBlockState(pos, state.withProperty(BlockCrops.AGE, 0));
				player.getHeldItemMainhand().damageItem(1, player);
				event.setCanceled(true);
			}
		}
		
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void boostMining(PlayerEvent.BreakSpeed event)
	{
		EntityPlayer player = event.getEntityPlayer();
		if (player.getHeldItemMainhand().getItem() instanceof ItemTool)
		{
			ICapMomentum cap = player.getCapability(CapMomentumProvider.MOMENTUM, null);
			int m = cap.getMomentum();
			Set<String> type = ((ItemTool)player.getHeldItemMainhand().getItem()).getToolClasses(player.getHeldItemMainhand());
			boolean b = false;
			for (String s : type)
	        {
	            if (event.getState().getBlock().isToolEffective(s, event.getState()))
	                b = true;
	        }
			if (m > 0 && b)
			{
				event.setNewSpeed((float) event.getOriginalSpeed() * (1 + (m / 100.0f)));
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void attractItems(LivingUpdateEvent event)
    {
    	EntityLivingBase entity = event.getEntityLiving();
    	if (entity instanceof EntityPlayer && EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.MAGNETIC, entity) > 0)
    	{
    		AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - 8, entity.posY - 8, entity.posZ - 8, entity.posX + 8, entity.posY + 8, entity.posZ + 8);
    		for (EntityItem item: entity.getEntityWorld().getEntitiesWithinAABB(EntityItem.class, aabb))
    		{
    			//Similar to onUpdate in EntityXPOrb, makes items move towards the player
    			double d1 = (entity.posX - item.posX) / 8.0D;
                double d2 = (entity.posY + (double)entity.getEyeHeight() / 2.0D - item.posY) / 8.0D;
                double d3 = (entity.posZ - item.posZ) / 8.0D;
                double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                double d5 = 1.0D - d4;

                if (d5 > 0.0D)
                {
                    d5 = d5 * d5;
                    item.motionX += d1 / d4 * d5 * 0.1D;
                    item.motionY += d2 / d4 * d5 * 0.1D;
                    item.motionZ += d3 / d4 * d5 * 0.1D;
                }
                
                item.move(MoverType.SELF, item.motionX, item.motionY, item.motionZ);
    		}
    	}
    }
	
	@SubscribeEvent
    public void destroyDroppedItems(HarvestDropsEvent event)
    {
    	if (event.getHarvester() != null)
    	{
	    	if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.DESTRUCTION_CURSE, event.getHarvester()) > 0)
	    	{
	    		event.setDropChance(0);
	    	}
    	}
    }
	
	@SubscribeEvent
    public void applyPropertiesToArrow(EntityJoinWorldEvent event)
    {
    	if (event.getEntity() instanceof EntityArrow && !event.getWorld().isRemote)
    	{
    		EntityArrow arrow = (EntityArrow)event.getEntity();
    		ICapArrowProperties cap = arrow.getCapability(CapArrowPropertiesProvider.ARROW_PROPERTIES, null);
    		if (arrow.shootingEntity instanceof EntityLivingBase)
    		{
	    		int g = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.GROUNDING, (EntityLivingBase) arrow.shootingEntity);
	    		cap.setGroundingLevel(g);
	    		int l = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.ITEM_LINK, (EntityLivingBase) arrow.shootingEntity);
	    		cap.setItemLink(l);
    		}
    	}
    }
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
    public void teleportItems(LivingDropsEvent event)
	{
		Entity source = event.getSource().getImmediateSource();
		Entity trueSource = event.getSource().getTrueSource();
		if (trueSource instanceof EntityPlayer && source instanceof EntityArrow)
		{
			EntityPlayer player = ((EntityPlayer)trueSource);
			ICapArrowProperties cap = source.getCapability(CapArrowPropertiesProvider.ARROW_PROPERTIES, null);
			int i = cap.getItemLink();
			if (player.getRNG().nextInt(3) < i)
			{
				for (EntityItem item : event.getDrops())
				{
					if (!player.inventory.addItemStackToInventory(item.getItem()));
					item.setPosition(player.posX, player.posY, player.posZ);
					trueSource.world.spawnEntity(item);
				}
				event.setCanceled(true);
			}
		}
	}
    
    @SubscribeEvent
    public void useDamageBonus(LivingDamageEvent event)
    {
    	Entity source = event.getSource().getImmediateSource();
    	if (source instanceof EntityArrow)
    	{
    		Entity target = event.getEntityLiving();
    		boolean b = false;
    		for (Class<? extends Entity > c : ConfigHandler.FlyingEntityList)
    		{
    			if (c.isInstance(target))
    			{
    				b = true;
    			}
    		}
    		if (b)
    		{
    			ICapArrowProperties cap = source.getCapability(CapArrowPropertiesProvider.ARROW_PROPERTIES, null);
    			event.setAmount(event.getAmount()*(cap.getGroundingLevel() * 0.4f + 1.0f));
    		}
    	}
    }
	
	@SubscribeEvent
    public void onFishingRodUse(PlayerInteractEvent.RightClickItem event)
    {
    	if (event.getItemStack().getItem() instanceof ItemFishingRod && !event.getWorld().isRemote)
    	{
    		ItemStack rod = event.getItemStack();
    		EntityPlayer player = event.getEntityPlayer();
    		int i = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.MEAT_HOOK, rod);
    		if (i > 0 && player.fishEntity != null)
    		{
    			EntityFishHook hook = player.fishEntity;
    			if (hook.caughtEntity != null)
    			{
    				if (hook.caughtEntity instanceof EntityPlayerMP)
    				{
    					ETPacketHandler.NETWORK.sendTo(new HookPlayerMessage(player.getEntityId(), i), (EntityPlayerMP) hook.caughtEntity);
    				}
    				EnchantmentMeatHook.bringInHookedEntity(hook.caughtEntity, player, i);
    	            hook.caughtEntity = null; //Prevents vanilla retraction which reduces fishing rod durability by 5
    	            rod.damageItem(1, player); // Reduces fishing rod durability just by 1
    			}
    		}
    	}
    }
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onBlock(LivingAttackEvent event)
	{
		if (!event.getEntity().world.isRemote)
		{
			EntityLivingBase living = event.getEntityLiving();
			boolean flag = getShieldEnchantmentLevel(living, ModEnchantments.SPELLPROOF) > 0;
			if (event.getAmount() > 0.0F && EnchantmentSpellproof.canBlockDamageSource(living, event.getSource(), flag))
	        {
				int l = getShieldEnchantmentLevel(living, ModEnchantments.COUNTERATTACK);
				int rand = new Random().nextInt(4);
				if (l > rand)
				{
					living.addPotionEffect(new PotionEffect(ModEnchantments.POTION_COUNTERATTACK, 60));
				}
	        }
			if (event.getSource().getImmediateSource() instanceof EntityLivingBase)
			{
				EntityLivingBase attacker = (EntityLivingBase) event.getSource().getImmediateSource();
				attacker.removePotionEffect(ModEnchantments.POTION_COUNTERATTACK);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onHurt(LivingHurtEvent event)
	{
		float amount = event.getAmount();
		EntityLivingBase living = event.getEntityLiving();
		if (event.getAmount() > 0.0F && EnchantmentSpellproof.canBlockDamageSource(living, event.getSource(), true))
        {
			int l = getShieldEnchantmentLevel(living, ModEnchantments.SPELLPROOF);
			if (l > 0)
			{
				event.setAmount(amount*(1.0f-(l/4.0F)));
				if (event.getEntityLiving() instanceof EntityPlayer)
				{
					EnchantmentSpellproof.damageShield(amount, (EntityPlayer)event.getEntityLiving());
				}
			}
        }
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		
		if (entity.isActiveItemStackBlocking() && getShieldEnchantmentLevel(entity, ModEnchantments.WEIGHTLESS) > 0)
		{
			AttributeModifier modifier = new AttributeModifier(EnchantmentWeightless.modifierUUID, "light_shield", 4, 2);
			if (!entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(modifier))
			{
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(modifier);
			}
		}
		else 
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(EnchantmentWeightless.modifierUUID);
		}
		if (entity instanceof EntityPlayer)
		{
			int i = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.REACH, entity);
			if (i > 0)
			{
				AttributeModifier extraReach = new AttributeModifier(EnchantmentReach.modifierUUID, "extra_reach", i, 0);
				if (!entity.getEntityAttribute(EntityPlayer.REACH_DISTANCE).hasModifier(extraReach))
				{
					entity.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(extraReach);
				}
			}
			else 
			{
				entity.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(EnchantmentReach.modifierUUID);
			}
		}
		//Unfortunately, I can't correctly recreate effect of some armor enchantments, so I have to copy these enchantments on item in regular armor slot
		if (entity instanceof EntityHorse)
		{
			EntityHorse horse = (EntityHorse) event.getEntityLiving();
			if (horse.getHorseArmorType() != HorseArmorType.NONE)
			{
				ItemStack armor = EnchantmentHandler.getHorseArmor(horse);
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
	
	public int getShieldEnchantmentLevel(EntityLivingBase living, Enchantment enchantment)
	{
		if (living.isActiveItemStackBlocking())
		{
			return EnchantmentHelper.getEnchantmentLevel(enchantment, living.getHeldItem(living.getActiveHand()));
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public static ItemStack getHorseArmor(EntityHorse horse)
	{
		Class<? extends EntityHorse> clazz = horse.getClass();
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
			clazz = (Class<? extends EntityHorse>) clazz.getSuperclass();
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
	
	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, int lvl)
    {
        worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
    	IBlockState state = lvl > 0 ? ModBlocks.FERTILIZED_FARMLAND.getDefaultState().withProperty(BlockFertilizedFarmland.FERTILITY, 5 * lvl) : Blocks.FARMLAND.getDefaultState();
        worldIn.setBlockState(pos, state, 11);
        stack.damageItem(1, player);
    }
}
