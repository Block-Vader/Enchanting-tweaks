package com.blockvader.enchantingtweaks.eventhandler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.blockvader.enchantingtweaks.ConfigHandler;
import com.blockvader.enchantingtweaks.Main;
import com.blockvader.enchantingtweaks.ModGUIHandler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OpenCustomGUI {
	
	@SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
	public void onTableInteract(PlayerInteractEvent.RightClickBlock event)
	{
		World worldIn = event.getWorld();
		EntityPlayer playerIn = event.getEntityPlayer();
		BlockPos pos = event.getPos();
		if (!worldIn.isRemote)
        {
			{
				TileEntity tileentity = worldIn.getTileEntity(pos);

				if (tileentity instanceof TileEntityEnchantmentTable)
				{
					if (Main.iscsb_ench_tableLoaded)
					{
						playerIn.openGui(Main.instance, ModGUIHandler.ID_ENCHANTMENT_WITH_LAPIS, worldIn, pos.getX(), pos.getY(), pos.getZ());
	            		event.setCanceled(true);
	            	}
	            	else if (!playerIn.isSneaking())
	            	{
	            		playerIn.openGui(Main.instance, ModGUIHandler.ID_ENCHANTMENT, worldIn, pos.getX(), pos.getY(), pos.getZ());
	            		event.setCanceled(true);
	            	}
	            }
			}
        }
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onGuiOpen(GuiOpenEvent event)
	{
		if (event.getGui() != null)
		{
			//System.out.println(event.getGui().toString());
		}
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
	public void onAnvilUse(AnvilUpdateEvent event)
	{
		if (ConfigHandler.AllowedOnAnvil.containsKey((event.getLeft().getItem())))
		{
			ItemStack itemstack = event.getLeft();
			int maximumCost = 1;
	        int i = 0;
	        int j = 0;
	        int k = 0;
			ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = event.getRight();
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
            j = j + itemstack.getRepairCost() + (itemstack2.isEmpty() ? 0 : itemstack2.getRepairCost());
            
            boolean flag = false;

            if (!itemstack2.isEmpty())
            {
                flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !ItemEnchantedBook.getEnchantments(itemstack2).hasNoTags();

                if (itemstack1.isItemStackDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2))
                {
                    int l2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);

                    if (l2 <= 0)
                    {
                        return;
                    }

                    int i3;

                    for (i3 = 0; l2 > 0 && i3 < itemstack2.getCount(); ++i3)
                    {
                        int j3 = itemstack1.getItemDamage() - l2;
                        itemstack1.setItemDamage(j3);
                        ++i;
                        l2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
                    }

                    event.setMaterialCost(i3);
                }
                else
                {
                    if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !(itemstack1.isItemStackDamageable() || ConfigHandler.AllowedOnTable.containsKey(itemstack.getItem()))))
                    {
                        return;
                    }

                    if (itemstack1.isItemStackDamageable() && !flag)
                    {
                        int l = itemstack.getMaxDamage() - itemstack.getItemDamage();
                        int i1 = itemstack2.getMaxDamage() - itemstack2.getItemDamage();
                        int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
                        int k1 = l + j1;
                        int l1 = itemstack1.getMaxDamage() - k1;

                        if (l1 < 0)
                        {
                            l1 = 0;
                        }

                        if (l1 < itemstack1.getItemDamage()) // vanilla uses metadata here instead of damage.
                        {
                            itemstack1.setItemDamage(l1);
                            i += 2;
                        }
                    }

                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
                    boolean flag2 = false;
                    boolean flag3 = false;

                    for (Enchantment enchantment1 : map1.keySet())
                    {
                        if (enchantment1 != null)
                        {
                            int i2 = map.containsKey(enchantment1) ? ((Integer)map.get(enchantment1)).intValue() : 0;
                            int j2 = ((Integer)map1.get(enchantment1)).intValue();
                            j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                            boolean flag1 = ConfigHandler.canApplyOnAnvil(itemstack.getItem(), enchantment1);

                            if (itemstack.getItem() == Items.ENCHANTED_BOOK)
                            {
                                flag1 = true;
                            }

                            for (Enchantment enchantment : map.keySet())
                            {
                                if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment))
                                {
                                    flag1 = false;
                                    ++i;
                                }
                            }

                            if (!flag1)
                            {
                                flag3 = true;
                            }
                            else
                            {
                                flag2 = true;

                                if (j2 > enchantment1.getMaxLevel())
                                {
                                    j2 = enchantment1.getMaxLevel();
                                }

                                map.put(enchantment1, Integer.valueOf(j2));
                                int k3 = 0;

                                switch (enchantment1.getRarity())
                                {
                                    case COMMON:
                                        k3 = 1;
                                        break;
                                    case UNCOMMON:
                                        k3 = 2;
                                        break;
                                    case RARE:
                                        k3 = 4;
                                        break;
                                    case VERY_RARE:
                                        k3 = 8;
                                }

                                if (flag)
                                {
                                    k3 = Math.max(1, k3 / 2);
                                }

                                i += k3 * j2;

                                if (itemstack.getCount() > 1)
                                {
                                    i = 40;
                                }
                            }
                        }
                    }

                    if (flag3 && !flag2)
                    {
                        event.setCanceled(true);
                        return;
                    }
                }
            }

            if (StringUtils.isBlank(event.getName()))
            {
                if (itemstack.hasDisplayName())
                {
                    k = 1;
                    i += k;
                    itemstack1.clearCustomName();
                }
            }
            else if (!event.getName().equals(itemstack.getDisplayName()))
            {
                k = 1;
                i += k;
                itemstack1.setStackDisplayName(event.getName());
            }
            if (flag && !itemstack1.getItem().isBookEnchantable(itemstack1, itemstack2)) 
            {
            	itemstack1 = ItemStack.EMPTY;
            	event.setCanceled(true);
            }

            maximumCost = j + i;

            if (i <= 0)
            {
                itemstack1 = ItemStack.EMPTY;
            	event.setCanceled(true);
            }

            if (k == i && k > 0 && maximumCost >= 40)
            {
                maximumCost = 39;
            }

            if (maximumCost >= 40)
            {
                itemstack1 = ItemStack.EMPTY;
            	event.setCanceled(true);
            }

            if (!itemstack1.isEmpty())
            {
                int k2 = itemstack1.getRepairCost();

                if (!itemstack2.isEmpty() && k2 < itemstack2.getRepairCost())
                {
                    k2 = itemstack2.getRepairCost();
                }

                if (k != i || k == 0)
                {
                    k2 = k2 * 2 + 1;
                }

                itemstack1.setRepairCost(k2);
                event.setCost(maximumCost);
                EnchantmentHelper.setEnchantments(map, itemstack1);
            }

            event.setOutput(itemstack1);
		}
	}

}
