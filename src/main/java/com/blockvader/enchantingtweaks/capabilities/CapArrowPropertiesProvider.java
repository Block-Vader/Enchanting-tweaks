package com.blockvader.enchantingtweaks.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapArrowPropertiesProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(ICapArrowProperties.class)
	public static final Capability<ICapArrowProperties> ARROW_PROPERTIES = null;
	
	private ICapArrowProperties instance = ARROW_PROPERTIES.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == ARROW_PROPERTIES;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == ARROW_PROPERTIES ? ARROW_PROPERTIES.<T> cast (this.instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound) ARROW_PROPERTIES.getStorage().writeNBT(ARROW_PROPERTIES, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		ARROW_PROPERTIES.getStorage().readNBT(ARROW_PROPERTIES, this.instance, null, nbt);
	}
}
