package com.blockvader.enchantingtweaks.capabilities;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapMomentum implements ICapMomentum {
	
	private int momentum;
	private int time;
	
	@Override
	public int getMomentum()
	{
		return momentum;
	}
	
	@Override
	public int getTime()
	{
		return time;
	}

	@Override
	public void setMomentum(int momentum)
	{
		this.momentum = momentum;
	}
	
	@Override
	public void setTime(int time)
	{
		this.time = time;
	}
	
	@Override
	public void addTime(int time)
	{
		this.time = Math.max(this.time + time, 0);
		if (this.time <= 0)
		{
			this.momentum = 0;
		}
	}
	
	public static void register()
	{
		CapabilityManager.INSTANCE.register(ICapMomentum.class, new Storage(), new Factory());
	}
	
	private static class Storage implements IStorage<ICapMomentum>
	{

		@Override
		public NBTBase writeNBT(Capability<ICapMomentum> capability, ICapMomentum instance, EnumFacing side)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("momentum", instance.getMomentum());
			nbt.setInteger("time", instance.getTime());
			return nbt;
		}

		@Override
		public void readNBT(Capability<ICapMomentum> capability, ICapMomentum instance, EnumFacing side, NBTBase nbt)
		{
			if (nbt instanceof NBTTagCompound)
			{
				int momentum = ((NBTTagCompound)nbt).getInteger("momentum");
				instance.setMomentum(momentum);
				int time = ((NBTTagCompound)nbt).getInteger("time");
				instance.setTime(time);
			}
		}
	}
	
	private static class Factory implements Callable<ICapMomentum>
	{

		@Override
		public ICapMomentum call() throws Exception
		{
			return new CapMomentum();
		}
		
	}

}
