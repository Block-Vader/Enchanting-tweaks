package com.blockvader.enchantingtweaks;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CapabilitySyncMessage implements IMessage{
	
	private int momentum;
	
	public CapabilitySyncMessage(int momentum) {
		this.momentum = momentum;
	}
	
	public CapabilitySyncMessage() {
	}
	
	public int getMomentum()
	{
		return momentum;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		momentum = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(momentum);
	}

}
