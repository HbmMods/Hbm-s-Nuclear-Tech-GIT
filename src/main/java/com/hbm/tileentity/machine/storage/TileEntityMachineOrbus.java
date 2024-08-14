package com.hbm.tileentity.machine.storage;

import com.hbm.blocks.BlockDummyable;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.IOverpressurable;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineOrbus extends TileEntityBarrel implements IOverpressurable{

	public TileEntityMachineOrbus() {
		super(512000);
	}
	
	@Override
	public String getName() {
		return "container.orbus";
	}
	
	@Override
	public void checkFluidInteraction() { } //NO!

	protected DirPos[] conPos;
	
	@Override
	protected DirPos[] getConPos() {
		
		if(conPos != null)
			return conPos;
		
		conPos = new DirPos[8];
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -1; i < 6; i += 6) {
			ForgeDirection out = i == -1 ? ForgeDirection.DOWN : ForgeDirection.UP;
			int index = i == -1 ? 0 : 4;
			conPos[index + 0] = new DirPos(xCoord,								yCoord + i,	zCoord,								out);
			conPos[index + 1] = new DirPos(xCoord + dir.offsetX,				yCoord + i,	zCoord + dir.offsetZ,				out);
			conPos[index + 2] = new DirPos(xCoord + rot.offsetX,				yCoord + i,	zCoord + rot.offsetZ,				out);
			conPos[index + 3] = new DirPos(xCoord + dir.offsetX + rot.offsetX,	yCoord + i,	zCoord + dir.offsetZ + rot.offsetZ,	out);
		}
		
		return conPos;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 2,
					yCoord + 5,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	// apathy is a tragedy and boredom is a crime
	@Override
	public void explode(World world, int x, int y, int z) {
		if(tank.getTankType() == Fluids.AMAT || tank.getTankType() == Fluids.ASCHRAB) {
		//if(this.hasExploded) return;
	    	float amat = Math.min(tank.getFill()/100,90);
	    	float aschrab = Math.min(tank.getFill()/100,90);
	    	if(this.hasExploded && !worldObj.isRemote) {
	    		if(amat>0)
	    		{
	    			if(amat >= 25)
	    			{
	    				EntityBalefire bf = new EntityBalefire(worldObj);
	    				bf.antimatter();
	    	    		bf.setPosition(xCoord, yCoord, zCoord);
	    				bf.destructionRange = (int) amat;
	    				worldObj.spawnEntityInWorld(bf);
	    				EntityNukeTorex.startFacAnti(worldObj, xCoord, yCoord, zCoord, amat * 1.5F);
	    				return;
	    			}
	    			else
	    			{
	    				new ExplosionVNT(worldObj, xCoord, yCoord, zCoord, amat).makeAmat().explode();
	    			}
	    		}
	    		if(aschrab>0)
	    		{
	    			EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, (int) aschrab);
	    			if(!ex.isDead) {
	    				worldObj.spawnEntityInWorld(ex);
	    	
	    				EntityCloudFleija cloud = new EntityCloudFleija(worldObj, (int) aschrab);
	    				cloud.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
	    				worldObj.spawnEntityInWorld(cloud);
	    			}
	    			return;			
	    		}	
	    	}
		}
	    	this.markChanged();
	    			
	}
}
