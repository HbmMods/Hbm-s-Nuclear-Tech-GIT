package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEAssemblerPacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityRadiobox extends TileEntity {
	
	public double freq;
	public int type;
	public String message;
	public int music;
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote && this.getBlockMetadata() > 5) {
			
			int range = 15;
			
			List<Entity> entities = worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
			for(Entity entity : entities)
				entity.attackEntityFrom(ModDamageSource.enervation, 20.0F);
		}
	}

}
