package com.blockvader.enchantingtweaks;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class HookPlayerMessage implements IMessage{
	
	private int ID;
	private int level;
	
	public HookPlayerMessage(int ID, int level)
	{
		this.ID = ID;
		this.level = level;
	}
	
	public HookPlayerMessage()
	{
	}

	public int getId()
	{
		return this.ID;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.ID = buf.readInt();
		this.level = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.ID);
		buf.writeInt(this.level);
	}

}
