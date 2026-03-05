package com.hbm.tileentity.machine.albion;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.machine.albion.TileEntityPASource.Particle;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPABeamline extends TileEntityLoadedBase implements IParticleUser {
	
	public boolean window = false;
	public boolean didPass = false;
	public float light;
	public float prevLight;
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			
			this.prevLight = this.light;
			if(this.light > 0) this.light -= 0.25F;
			
			if(this.light > this.prevLight) this.prevLight = this.light;
			
		} else {
			this.networkPackNT(150);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(window);
		buf.writeBoolean(didPass);
		didPass = false;
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.window = buf.readBoolean();
		this.didPass = buf.readBoolean();
		if(didPass) light = 2F;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.window = nbt.getBoolean("window");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("window", window);
	}

	@Override
	public boolean canParticleEnter(Particle particle, ForgeDirection dir, int x, int y, int z) {
		ForgeDirection beamlineDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		BlockPos input = new BlockPos(xCoord, yCoord, zCoord).offset(beamlineDir, -1);
		return input.compare(x, y, z) && beamlineDir == dir;
	}

	@Override
	public void onEnter(Particle particle, ForgeDirection dir) {
		particle.addDistance(3);
		this.didPass = true;
	}

	@Override
	public BlockPos getExitPos(Particle particle) {
		ForgeDirection beamlineDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		return new BlockPos(xCoord, yCoord, zCoord).offset(beamlineDir, 2);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 1,
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
}
