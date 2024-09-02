package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityZirnoxDestroyed extends TileEntity {
	
	public boolean onFire = true;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		onFire = nbt.getBoolean("fire");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("onFire", onFire);
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			radiate(worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			if(this.worldObj.rand.nextInt(5000) == 0)
				onFire = false;
			
			if(onFire && this.worldObj.getTotalWorldTime() % 50 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkflame");
				data.setInteger("maxAge", 90);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 1.75, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1.75, zCoord + 0.5, 75));
				MainRegistry.proxy.effectNT(data);
				worldObj.playSoundEffect(xCoord + 0.5F, yCoord + 0.5, zCoord + 0.5, "fire.fire", 1.0F + worldObj.rand.nextFloat(), worldObj.rand.nextFloat() * 0.7F + 0.3F);
			}
		}
	}

	private void radiate(World world, int x, int y, int z) {

		float rads = onFire ? 500000F : 75000F;
		double range = 100D;

		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x + 0.5, y + 0.5, z + 0.5, x + 0.5, y + 0.5, z + 0.5).expand(range, range, range));

		for(EntityLivingBase e : entities) {

			Vec3 vec = Vec3.createVectorHelper(e.posX - (x + 0.5), (e.posY + e.getEyeHeight()) - (y + 0.5), e.posZ - (z + 0.5));
			double len = vec.lengthVector();
			vec = vec.normalize();

			float res = 0;

			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(x + 0.5 + vec.xCoord * i);
				int iy = (int)Math.floor(y + 0.5 + vec.yCoord * i);
				int iz = (int)Math.floor(z + 0.5 + vec.zCoord * i);

				res += world.getBlock(ix, iy, iz).getExplosionResistance(null);
			}

			if(res < 1)
				res = 1;

			float eRads = rads;
			eRads /= (float)res;
			eRads /= (float)(len * len);

			ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);

			if(onFire && len < 5) {
				e.attackEntityFrom(DamageSource.onFire, 2);
			}
		}
	}

	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 3, yCoord, zCoord - 3, xCoord + 4, yCoord + 3, zCoord + 4);
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}