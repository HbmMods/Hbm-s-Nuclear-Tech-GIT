package com.hbm.blocks.machine.rbmk;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKDebrisRadiating extends RBMKDebrisBurning {

	@Override
	public int tickRate(World world) {

		return 20 + world.rand.nextInt(20);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!world.isRemote) {
			
			radiate(world, x, y, z);
			
			if(rand.nextInt(5) == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkflame");
				data.setInteger("maxAge", 300);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x + rand.nextDouble(), y + 1.75, z + rand.nextDouble()), new TargetPoint(world.provider.dimensionId, x + 0.5, y + 1.75, z + 0.5, 75));
				//MainRegistry.proxy.effectNT(data);
				world.playSoundEffect(x + 0.5F, y + 0.5, z + 0.5, "fire.fire", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F);

			}
			
			ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(6));
			Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			if(rand.nextInt(10) == 0 && block == Blocks.air) {
				world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, ModBlocks.gas_meltdown);
			}
			
			//Boron sand helps stop the fission reaction; 0.66% chance every 20-40 ticks for one side
			int chance = block == ModBlocks.sand_boron_layer || block == ModBlocks.sand_boron ? 25 : 1000;
			
			if(rand.nextInt(chance) == 0) {
				
				int meta = world.getBlockMetadata(x, y, z);
				
				if(meta < 15) {
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
					world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
				} else {
					world.setBlock(x, y, z, ModBlocks.pribris_burning);
				}
				
			} else {
				world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
			}
		}
	}
	
	private void radiate(World world, int x, int y, int z) {
		
		float rads = 1000000F;
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
			
			if(len < 5) {
				e.attackEntityFrom(DamageSource.inFire, 100);
			}
			
			if(e instanceof EntityPlayer && len < 10) {
				EntityPlayer p = (EntityPlayer) e;
				
				if(p.getHeldItem() != null && p.getHeldItem().getItem() == ModItems.marshmallow && p.getHeldItem().getItemDamage() != 1 && p.getRNG().nextInt(100) == 0) {
					p.getHeldItem().setItemDamage(1);
				}
			}
		}
	}
}
