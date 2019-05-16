package com.blockvader.enchantingtweaks.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapMomentumProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(ICapMomentum.class)
	public static final Capability<ICapMomentum> MOMENTUM = null;
	
	private ICapMomentum instance = MOMENTUM.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == MOMENTUM;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == MOMENTUM ? MOMENTUM.<T> cast (this.instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound) MOMENTUM.getStorage().writeNBT(MOMENTUM, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		MOMENTUM.getStorage().readNBT(MOMENTUM, this.instance, null, nbt);
	}
}
