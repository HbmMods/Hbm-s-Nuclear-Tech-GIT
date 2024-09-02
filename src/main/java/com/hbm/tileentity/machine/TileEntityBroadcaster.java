package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.LoopedSoundPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBroadcaster extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5 - 25, yCoord + 0.5 - 25, zCoord + 0.5 - 25, xCoord + 0.5 + 25, yCoord + 0.5 + 25, zCoord + 0.5 + 25));
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) instanceof EntityLivingBase) {
				EntityLivingBase e = (EntityLivingBase)list.get(i);
				double d = Math.sqrt(Math.pow(e.posX - (xCoord + 0.5), 2) + Math.pow(e.posY - (yCoord + 0.5), 2) + Math.pow(e.posZ - (zCoord + 0.5), 2));
				
				if(d <= 25) {
					if(e.getActivePotionEffect(Potion.confusion) == null || e.getActivePotionEffect(Potion.confusion).getDuration() < 100)
						e.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 0));
				}
				
				if(d <= 15) {
					double t = (15 - d) / 15 * 10;
					e.attackEntityFrom(ModDamageSource.broadcast, (float) t);
				}
			}
		}

		if(!worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
