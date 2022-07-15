package com.hbm.inventory.fluid.types;

import java.util.List;

import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.render.util.EnumSymbol;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;

public class FluidTypeExplosive extends FluidType
{
	protected float power;
	public FluidTypeExplosive(String name, int color, int p, int f, int r, EnumSymbol symbol)
	{
		super(name, color, p, f, r, symbol);
	}

	public FluidTypeExplosive(int forcedId, String name, int color, int p, int f, int r, EnumSymbol symbol)
	{
		super(forcedId, name, color, p, f, r, symbol);
	}

	public FluidTypeExplosive setPower(float p)
	{
		power = p;
		return this;
	}
	
	public float getPower()
	{
		return power;
	}
	
	@Override
	public void addInfo(List<String> info)
	{
		super.addInfo(info);
		
		info.add(EnumChatFormatting.RED + "[Explosive]");
	}
	
	@Override
	public void onTankBroken(TileEntity te, FluidTank tank)
	{
		if (tank.getFill() > 0)
		{
			ExplosionNT exp = new ExplosionNT(te.getWorldObj(), null, te.xCoord, te.yCoord, te.zCoord, power * tank.getFill() / 1000);
			exp.addAttrib(ExAttrib.FIRE);
			exp.explode();
		}
	}
}
