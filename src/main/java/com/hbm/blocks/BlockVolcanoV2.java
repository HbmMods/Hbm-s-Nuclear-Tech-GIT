package com.hbm.blocks;

import java.util.List;
import java.util.Random;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.packet.toclient.BufPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IBufPacketReceiver;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockVolcanoV2 extends BlockContainer {

	public BlockVolcanoV2(Material rock) {
		super(rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLightningVolcano();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public static class TileEntityLightningVolcano extends TileEntity implements IBufPacketReceiver {
		
		public int chargetime;
		public float flashd;

		public TileEntityLightningVolcano() {
			// Quick pre-set to prevent it immediately firing on load
			chargetime = (new Random()).nextInt(400) + 100;
		}

		@Override
		public void updateEntity() {
			// flashd is used locally to render the spike, and on the server for timing
			if(chargetime > 0) {
				flashd = 0;
			} else {
				flashd += 0.3f;
				flashd = Math.min(100.0f, flashd + 0.3f * (100.0f - flashd) * 0.15f);
			}

			if(!worldObj.isRemote) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());

				if(chargetime == 1) {
					double x = (int) (xCoord + dir.offsetX * (worldObj.getTotalWorldTime() / 5L) % 1) + 0.5;
					double y = (int) (yCoord + dir.offsetY * (worldObj.getTotalWorldTime() / 5L) % 1) + 20.5;
					double z = (int) (zCoord + dir.offsetZ * (worldObj.getTotalWorldTime() / 5L) % 1) + 0.5;
						
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "plasmablast");
					data.setFloat("r", 1F);
					data.setFloat("g", 1F);
					data.setFloat("b", 1F);
					data.setFloat("scale", 10 * 4);
					data.setFloat("yaw", 90);
						
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(worldObj.provider.dimensionId, x, y, z, 256));
					worldObj.playSoundEffect(x, y, z, "ambient.weather.thunder", 200, 0.8F + this.worldObj.rand.nextFloat() * 0.2F);
					vapor();
				}

				// we only want to modify chargetime on the server, since it is synced to clients
				if(chargetime > 0) chargetime--;

				if(flashd >= 100) {
					chargetime = worldObj.rand.nextInt(400) + 100;
				}

				PacketDispatcher.wrapper.sendToAllAround(new BufPacket(xCoord, yCoord, zCoord, this), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 256));
			}
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			chargetime = nbt.getInteger("charge");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("charge", this.chargetime);
		}
		
		@Override
		public AxisAlignedBB getRenderBoundingBox() {
			return TileEntity.INFINITE_EXTENT_AABB;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeInt(chargetime);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			chargetime = buf.readInt();
		}
		private void vapor() {

			List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class,
					AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord + 0.5, this.zCoord - 0.5, this.xCoord + 1.5,
							this.yCoord + 60, this.zCoord + 1.5));
			
			if (!entities.isEmpty()) {
				for (Entity e : entities) {

					if(e instanceof EntityLivingBase)
						if(e.attackEntityFrom(ModDamageSource.electricity, MathHelper.clamp_float(((EntityLivingBase) e).getMaxHealth() * 1F, 3, 20)))
							worldObj.playSoundAtEntity(e, "hbm:weapon.tesla", 1.0F, 1.0F);
				}
			}
		}
		
	}
	

}
