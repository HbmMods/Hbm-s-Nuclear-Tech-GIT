package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ParticleUtil;

import api.hbm.block.IToolable;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class PartEmitter extends BlockContainer implements IToolable, ITooltipProvider {

	public PartEmitter() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPartEmitter();
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		if(tool == ToolType.HAND_DRILL) {
			TileEntityPartEmitter te = (TileEntityPartEmitter) world.getTileEntity(x, y, z);
			te.effect = (te.effect + 1) % te.effectCount;
			te.markDirty();
			return true;
		}

		return false;
	}

	public static class TileEntityPartEmitter extends TileEntity {

		public static final int range = 150;
		public int effect = 0;
		public static final int effectCount = 4;

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				double x = xCoord + 0.5;
				double y = yCoord + 0.5;
				double z = zCoord + 0.5;
				NBTTagCompound data = new NBTTagCompound();

				if(effect == 1) {
					ParticleUtil.spawnGasFlame(worldObj, xCoord + worldObj.rand.nextDouble(), yCoord + 4.5 + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), worldObj.rand.nextGaussian() * 0.2, 0.1, worldObj.rand.nextGaussian() * 0.2);
				}

				if(effect == 2) {
					data.setString("type", "tower");
					data.setFloat("lift", 5F);
					data.setFloat("base", 0.25F);
					data.setFloat("max", 5F);
					data.setInteger("life", 560 + worldObj.rand.nextInt(20));
					data.setInteger("color",0x404040);
				}
				if(effect == 3) {
					data.setString("type", "tower");
					data.setFloat("lift", 0.5F);
					data.setFloat("base", 1F);
					data.setFloat("max", 10F);
					data.setInteger("life", 750 + worldObj.rand.nextInt(250));

					x = xCoord + 0.5 + worldObj.rand.nextDouble() * 3 - 1.5;
					y =  yCoord + 1;
					z = zCoord + 0.5 + worldObj.rand.nextDouble() * 3 - 1.5;

				}

				if(data.hasKey("type")) {
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
				}
			}
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}

		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.effect = nbt.getInteger("effect");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("effect", this.effect);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Use hand drill to cycle special effects");
	}
}
