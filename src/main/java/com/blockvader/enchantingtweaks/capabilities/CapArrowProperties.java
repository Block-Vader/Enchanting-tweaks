package com.blockvader.enchantingtweaks.capabilities;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapArrowProperties implements ICapArrowProperties{
	
	private int groundingLevel;
	
	private int hasItemLink;

	@Override
	public int getItemLink()
	{
		return this.hasItemLink;
	}

	@Override
	public int getGroundingLevel()
	{
		return this.groundingLevel;
	}

	@Override
	public void setItemLink(int link)
	{
		this.hasItemLink = link;
	}

	@Override
	public void setGroundingLevel(int level)
	{
		this.groundingLevel = level;
	}
	
	public static void register()
	{
		CapabilityManager.INSTANCE.register(ICapArrowProperties.class, new Storage(), new Factory());
	}
	
	private static class Storage implements IStorage<ICapArrowProperties>
	{

		@Override
		public NBTBase writeNBT(Capability<ICapArrowProperties> capability, ICapArrowProperties instance, EnumFacing side)
		{
			NBTTagCompound compound  = new NBTTagCompound();
			compound.setInteger("groundingLevel", instance.getGroundingLevel());
			compound.setInteger("hasItemLink", instance.getItemLink());
			return compound;
		}

		@Override
		public void readNBT(Capability<ICapArrowProperties> capability, ICapArrowProperties instance, EnumFacing side, NBTBase nbt)
		{
			if (nbt instanceof NBTTagCompound)
			{
				instance.setGroundingLevel(((NBTTagCompound) nbt).getInteger("groundingLevel"));
				instance.setItemLink(((NBTTagCompound) nbt).getInteger("hasItemLink"));
			}
		}
	}
	
	private static class Factory implements Callable<ICapArrowProperties>
	{

		@Override
		public ICapArrowProperties call() throws Exception
		{
			return new CapArrowProperties();
		}
		
	}

}
