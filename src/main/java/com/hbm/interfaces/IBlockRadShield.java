package com.hbm.interfaces;

import com.hbm.modules.BlockRadShieldModule;

import net.minecraft.block.Block;
@Untested
public interface IBlockRadShield
{
	public BlockRadShieldModule getShieldModule();
	
	public default IBlockRadShield addEMShield(float shield)
	{
		getShieldModule().addEMShield(shield);
		return this;
	}
	
	public default IBlockRadShield addNeutronShield(float shield)
	{
		getShieldModule().addNeutronShield(shield);
		return this;
	}
	
	public default Block toBlock()
	{
		return (Block)this;
	}
}
