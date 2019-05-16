package com.blockvader.enchantingtweaks.capabilities;

public interface ICapMomentum {
	
	public int getMomentum();
	
	public int getTime();
	
	public void setMomentum(int momentum);

	public void setTime(int time);
	
	public void addTime(int time);
}
