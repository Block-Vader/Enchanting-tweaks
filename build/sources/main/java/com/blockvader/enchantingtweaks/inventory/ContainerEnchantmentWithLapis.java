package com.blockvader.enchantingtweaks.inventory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.enchantments.EnchantmentDataWithBonus;
import com.blockvader.enchantingtweaks.tileentities.TileEntityBookshelf;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.crazysnailboy.mods.enchantingtable.inventory.ContainerEnchantment;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ContainerEnchantmentWithLapis extends ContainerEnchantment
{
	private World world;
	private BlockPos pos;
    private final Random rand;
    private Map<Enchantment, Integer> bonus = Maps.<Enchantment, Integer>newLinkedHashMap();
	
	public ContainerEnchantmentWithLapis(InventoryPlayer playerInv, World worldIn) {
		this(playerInv, worldIn, BlockPos.ORIGIN);
	}

	public ContainerEnchantmentWithLapis(InventoryPlayer playerInv, World worldIn, BlockPos pos) {
		super(playerInv, worldIn, pos);
		this.world = worldIn;
		this.pos = pos;
        this.rand = new Random();
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        if (inventoryIn == this.tableInventory)
        {
            ItemStack itemstack = inventoryIn.getStackInSlot(0);

            if (!itemstack.isEmpty() && (itemstack.isItemEnchantable() || (ConfigHandler.Enchantability.containsKey(itemstack.getItem()) && !itemstack.isItemEnchanted())))
            {
                if (!this.world.isRemote)
                {
                    float power = 0;
                    if (!bonus.isEmpty()) bonus.clear();

                    for (int j = -1; j <= 1; ++j)
                    {
                        for (int k = -1; k <= 1; ++k)
                        {
                            if ((j != 0 || k != 0) && this.world.isAirBlock(this.pos.add(k, 0, j)) && this.world.isAirBlock(this.pos.add(k, 1, j)))
                            {
                                power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k * 2, 0, j * 2));
                                this.addBonus(bonus, pos.add(k * 2, 0, j * 2));
                                power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k * 2, 1, j * 2));
                                this.addBonus(bonus, pos.add(k * 2, 1, j * 2));
                                if (k != 0 && j != 0)
                                {
                                    power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k * 2, 0, j));
                                    this.addBonus(bonus, pos.add(k * 2, 0, j));
                                    power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k * 2, 1, j));
                                    this.addBonus(bonus, pos.add(k * 2, 1, j));
                                    power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k, 0, j * 2));
                                    this.addBonus(bonus, pos.add(k, 0, j * 2));
                                    power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k, 1, j * 2));
                                    this.addBonus(bonus, pos.add(k, 1, j * 2));
                                }
                            }
                        }
                    }

                    this.rand.setSeed((long)this.xpSeed);

                    for (int i1 = 0; i1 < 3; ++i1)
                    {
                    	this.enchantLevels[i1] = this.calcItemStackEnchantability(this.rand, i1, (int)power, itemstack);
                        this.enchantClue[i1] = -1;
                        this.worldClue[i1] = -1;

                        if (this.enchantLevels[i1] < i1 + 1)
                        {
                            this.enchantLevels[i1] = 0;
                        }
                        this.enchantLevels[i1] = net.minecraftforge.event.ForgeEventFactory.onEnchantmentLevelSet(world, pos, i1, (int)power, itemstack, enchantLevels[i1]);
                    }

                    for (int j1 = 0; j1 < 3; ++j1)
                    {
                        if (this.enchantLevels[j1] > 0)
                        {
                            List<EnchantmentDataWithBonus> list = this.getEnchantmentList(itemstack, j1, this.enchantLevels[j1]);

                            if (list != null && !list.isEmpty())
                            {
                            	EnchantmentDataWithBonus enchantmentdata = list.get(this.rand.nextInt(list.size()));
                                this.enchantClue[j1] = Enchantment.getEnchantmentID(enchantmentdata.enchantment);
                                this.worldClue[j1] = enchantmentdata.enchantmentLevel;
                            }
                        }
                    }

                    this.detectAndSendChanges();
                }
            }
            else
            {
                for (int i = 0; i < 3; ++i)
                {
                    this.enchantLevels[i] = 0;
                    this.enchantClue[i] = -1;
                    this.worldClue[i] = -1;
                }
            }
        }
    }
	
	public boolean enchantItem(EntityPlayer playerIn, int id)
    {
        ItemStack itemstack = this.tableInventory.getStackInSlot(0);
        ItemStack itemstack1 = this.tableInventory.getStackInSlot(1);
        int i = id + 1;

        if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !playerIn.capabilities.isCreativeMode)
        {
            return false;
        }
        else if (this.enchantLevels[id] > 0 && !itemstack.isEmpty() && (playerIn.experienceLevel >= i && playerIn.experienceLevel >= this.enchantLevels[id] || playerIn.capabilities.isCreativeMode))
        {
            if (!this.world.isRemote)
            {
                List<EnchantmentDataWithBonus> list = this.getEnchantmentList(itemstack, id, this.enchantLevels[id]);

                if (!list.isEmpty())
                {
                    playerIn.onEnchant(itemstack, i);
                    boolean flag = itemstack.getItem() == Items.BOOK;

                    if (flag)
                    {
                        itemstack = new ItemStack(Items.ENCHANTED_BOOK);
                        this.tableInventory.setInventorySlotContents(0, itemstack);
                    }

                    for (int j = 0; j < list.size(); ++j)
                    {
                    	EnchantmentData enchantmentdata = new EnchantmentData(list.get(j).enchantment, list.get(j).enchantmentLevel);

                        if (flag)
                        {
                            ItemEnchantedBook.addEnchantment(itemstack, enchantmentdata);
                        }
                        else
                        {
                            itemstack.addEnchantment(enchantmentdata.enchantment, enchantmentdata.enchantmentLevel);
                        }
                    }

                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        itemstack1.shrink(i);

                        if (itemstack1.isEmpty())
                        {
                            this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                        }
                    }

                    playerIn.addStat(StatList.ITEM_ENCHANTED);

                    if (playerIn instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.ENCHANTED_ITEM.trigger((EntityPlayerMP)playerIn, itemstack, i);
                    }

                    this.tableInventory.markDirty();
                    this.xpSeed = playerIn.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                    this.world.playSound((EntityPlayer)null, this.pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
	
	private List<EnchantmentDataWithBonus> getEnchantmentList(ItemStack stack, int enchantSlot, int level)
    {
        this.rand.setSeed((long)(this.xpSeed + enchantSlot));
        List<EnchantmentDataWithBonus> list = this.buildEnchantmentList(this.rand, stack, level, false);

        if (stack.getItem() == Items.BOOK && list.size() > 1)
        {
            list.remove(this.rand.nextInt(list.size()));
        }

        return list;
    }
	
	public List<EnchantmentDataWithBonus> buildEnchantmentList(Random randomIn, ItemStack itemStackIn, int level, boolean allowTreasure)
    {
        List<EnchantmentDataWithBonus> list = Lists.<EnchantmentDataWithBonus>newArrayList();
        Item item = itemStackIn.getItem();
        int i = 0;
        
        if (ConfigHandler.Enchantability.containsKey(item))
        {
        	i = ConfigHandler.Enchantability.get(item);
        }
        else
        {
        	i = item.getItemEnchantability(itemStackIn);
        }

        if (i <= 0)
        {
            return list;
        }
        else
        {
            level = level + 1 + randomIn.nextInt(i / 4 + 1) + randomIn.nextInt(i / 4 + 1);
            float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
            level = MathHelper.clamp(Math.round((float)level + (float)level * f), 1, Integer.MAX_VALUE);
            List<EnchantmentDataWithBonus> list1 = this.getEnchantmentDatas(level, itemStackIn, allowTreasure);

            if (!list1.isEmpty())
            {
            	
                list.add(WeightedRandom.getRandomItem(randomIn, list1));

                while (randomIn.nextInt(50) <= level)
                {
                	this.removeIncompatible(list1, (EnchantmentDataWithBonus)Util.getLastElement(list));

                    if (list1.isEmpty())
                    {
                        break;
                    }

                    list.add(WeightedRandom.getRandomItem(randomIn, list1));
                    level /= 2;
                }
            }

            return list;
        }
    }
	
	public List<EnchantmentDataWithBonus> getEnchantmentDatas(int p_185291_0_, ItemStack p_185291_1_, boolean allowTreasure)
    {
        List<EnchantmentDataWithBonus> list = Lists.<EnchantmentDataWithBonus>newArrayList();
        Item item = p_185291_1_.getItem();
        
        if (ConfigHandler.AllowedOnTable.containsKey(item))
        {
        	for (Enchantment enchantment : ConfigHandler.AllowedOnTable.get(item))
            {
                for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i)
                {
                    if (p_185291_0_ >= enchantment.getMinEnchantability(i) && p_185291_0_ <= enchantment.getMaxEnchantability(i))
                    {
                    	if (bonus.containsKey(enchantment))
                    	{
                    		list.add(new EnchantmentDataWithBonus(enchantment, i, bonus.get(enchantment)));
                    	}
                    	else
                    	{
                    		list.add(new EnchantmentDataWithBonus(enchantment, i, 0));
                    	}
                        break;
                    }
                }
            }
        }
        
        else
        {
	        boolean flag = p_185291_1_.getItem() == Items.BOOK;
	
	        for (Enchantment enchantment : Enchantment.REGISTRY)
	        {
	            if ((!enchantment.isTreasureEnchantment() || allowTreasure) && (enchantment.canApplyAtEnchantingTable(p_185291_1_) || (flag && enchantment.isAllowedOnBooks())))
	            {
	                for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i)
	                {
	                    if (p_185291_0_ >= enchantment.getMinEnchantability(i) && p_185291_0_ <= enchantment.getMaxEnchantability(i))
	                    {
	                    	if (bonus.containsKey(enchantment))
	                    	{
	                    		list.add(new EnchantmentDataWithBonus(enchantment, i, bonus.get(enchantment)));
	                    	}
	                    	else
	                    	{
	                    		list.add(new EnchantmentDataWithBonus(enchantment, i, 0));
	                    	}
	                        break;
	                    }
	                }
	            }
	        }
        }

        return list;
    }
	
	public int calcItemStackEnchantability(Random rand, int enchantNum, int power, ItemStack stack)
    {
        Item item = stack.getItem();
        
        int i = 0;
        
        if (ConfigHandler.Enchantability.containsKey(item))
        {
        	i = ConfigHandler.Enchantability.get(item);
        }
        else
        {
        	i = item.getItemEnchantability(stack);
        }

        if (i <= 0)
        {
            return 0;
        }
        else
        {
            if (power > 15)
            {
                power = 15;
            }

            int j = rand.nextInt(8) + 1 + (power >> 1) + rand.nextInt(power + 1);

            if (enchantNum == 0)
            {
                return Math.max(j / 3, 1);
            }
            else
            {
                return enchantNum == 1 ? j * 2 / 3 + 1 : Math.max(j, power * 2);
            }
        }
    }
	
	private void addBonus( Map<Enchantment, Integer> map, BlockPos pos)
	{
		if (!ConfigHandler.BoostProbability) return;
		if (world.getTileEntity(pos) != null)
		{
			if (world.getTileEntity(pos).getClass() == TileEntityBookshelf.class)
			{
				TileEntityBookshelf bookshelf = (TileEntityBookshelf) world.getTileEntity(pos);
				bookshelf.getEnchantBonus(map);
			}
		}
	}
	
	public void removeIncompatible(List<EnchantmentDataWithBonus> p_185282_0_, EnchantmentDataWithBonus p_185282_1_)
    {
        Iterator<EnchantmentDataWithBonus> iterator = p_185282_0_.iterator();

        while (iterator.hasNext())
        {
            if (!p_185282_1_.enchantment.isCompatibleWith((iterator.next()).enchantment))
            {
                iterator.remove();
            }
        }
    }
}
