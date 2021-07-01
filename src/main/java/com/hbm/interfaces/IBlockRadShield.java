package com.hbm.interfaces;

import com.hbm.modules.BlockRadShieldModule;

import net.minecraft.block.Block;
@Untested
public interface IBlockRadShield
{
	public BlockRadShieldModule getModule();
	
	public default IBlockRadShield addEMShield(float shield)
	{
		getModule().addEMShield(shield);
		return this;
	}
	
	public default IBlockRadShield addNeutronShield(float shield)
	{
		getModule().addNeutronShield(shield);
		return this;
	}
	
	public default Block toBlock()
	{
		return (Block)this;
	}
}
