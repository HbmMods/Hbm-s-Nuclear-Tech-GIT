package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TileEntityTesla extends TileEntityMachineBase implements IConsumer {
	
	public long power;
	public static final long maxPower = 100000;
	
	public static int range = 10;
	public static double offset = 1.75;
	
	public List<double[]> targets = new ArrayList();

	public TileEntityTesla() {
		super(0);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.targets.clear();
			
			if(power >= 5000) {
				power -= 5000;

				double dx = xCoord + 0.5;
				double dy = yCoord + offset;
				double dz = zCoord + 0.5;
				
				List<EntityLivingBase> targets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(dx - range, dy - range, dz - range, dx + range, dy + range, dz + range));
				
				for(EntityLivingBase e : targets) {
					
					if(e instanceof EntityOcelot)
						continue;
					
					Vec3 vec = Vec3.createVectorHelper(e.posX - dx, e.posY + e.height / 2 - dy, e.posZ - dz);
					
					if(vec.lengthVector() > range)
						continue;
					
					if(!(e instanceof EntityPlayer && Library.checkForFaraday((EntityPlayer)e)))
						if(e.attackEntityFrom(ModDamageSource.electricity, MathHelper.clamp_float(e.getMaxHealth() * 0.5F, 3, 20) / (float)targets.size()))
							worldObj.playSoundAtEntity(e, "hbm:weapon.tesla", 1.0F, 1.0F);
					
					this.targets.add(new double[] {e.posX, e.posY + e.height / 2, e.posZ});
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("length", (short)targets.size());
			int i = 0;
			for(double[] d : this.targets) {
				data.setDouble("x" + i, d[0]);
				data.setDouble("y" + i, d[1]);
				data.setDouble("z" + i, d[2]);
				i++;
			}
			
			this.networkPack(data, 100);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		int s = data.getShort("length");
		
		this.targets.clear();
		
		for(int i = 0; i < s; i++)
			this.targets.add(new double[] {
					data.getDouble("x" + i),
					data.getDouble("y" + i),
					data.getDouble("z" + i)
			});
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
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
