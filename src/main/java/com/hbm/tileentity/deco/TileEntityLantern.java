package com.hbm.tileentity.deco;

import java.util.List;

import com.hbm.entity.mob.glyphid.EntityGlyphid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityLantern extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 20 == 0) {
			
			List<EntityGlyphid> glyphids = worldObj.getEntitiesWithinAABB(EntityGlyphid.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 5.5, zCoord + 0.5, xCoord + 0.5, yCoord + 5.5, zCoord + 0.5).expand(7.5, 7.5, 7.5));
			
			for(EntityGlyphid glyphid : glyphids) {
				glyphid.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord,
					yCoord,
					zCoord,
					xCoord + 1,
					yCoord + 6,
					zCoord + 1
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
