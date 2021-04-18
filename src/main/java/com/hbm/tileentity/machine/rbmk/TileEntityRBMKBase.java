package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Base class for all RBMK components, active or passive. Handles heat and the explosion sequence
 * @author hbm
 *
 */
public abstract class TileEntityRBMKBase extends TileEntity implements INBTPacketReceiver {
	
	public double heat;

	public boolean hasLid() {
		return false;
	}
	
	/**
	 * Approx melting point of steel
	 * This metric won't be used because fuel tends to melt much earlier than that
	 * @return
	 */
	public double maxHeat() {
		return 1500D;
	}
	
	/**
	 * Around the same for every component except boilers which do not have passive cooling
	 * @return
	 */
	public double passiveCooling() {
		return 5D;
	}
	
	//necessary checks to figure out whether players are close enough to ensure that the reactor can be safely used
	public boolean shouldUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			moveHeat();
			coolPassively();
			
			NBTTagCompound data = new NBTTagCompound();
			this.writeToNBT(data);
			this.networkPack(data, 10);
		}
	}
	
	public static final ForgeDirection[] heatDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};
	
	/**
	 * Moves heat to neighboring parts, if possible, in a relatively fair manner
	 */
	private void moveHeat() {
		
		List<TileEntityRBMKBase> rec = new ArrayList();
		double req = 0;
		
		for(ForgeDirection dir : heatDirs) {
			
			TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
			
			if(te instanceof TileEntityRBMKBase) {
				TileEntityRBMKBase base = (TileEntityRBMKBase) te;
				
				if(base.heat < this.heat) {
					rec.add(base);
					
					req += (this.heat - base.heat) / 2D;
				}
			}
		}
		
		if(rec.size() > 0) {
			
			double max = req / rec.size();
			
			for(TileEntityRBMKBase base : rec) {
				
				double move = (this.heat - base.heat) / 2D;
				
				if(move > max)
					move = max;
				
				base.heat += move;
				this.heat -= move;
			}
		}
	}
	
	/**
	 * TODO: add faster passive cooling based on temperature (blackbody radiation has an exponent of 4!)
	 */
	private void coolPassively() {
		
		this.heat -= this.passiveCooling();
		
		if(heat < 0)
			heat = 0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.heat = nbt.getDouble("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setDouble("heat", this.heat);
	}
	
	public void networkPack(NBTTagCompound nbt, int range) {

		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.readFromNBT(nbt);
	}
	
	public void getDiagData(NBTTagCompound nbt) {
		this.writeToNBT(nbt);
	}
	
	@SideOnly(Side.CLIENT)
	public static void diagnosticPrintHook(RenderGameOverlayEvent.Pre event) {

		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.theWorld;
		MovingObjectPosition mop = mc.objectMouseOver;
		ScaledResolution resolution = event.resolution;
		
		if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK && world.getBlock(mop.blockX, mop.blockY, mop.blockZ) instanceof RBMKBase) {
			
			RBMKBase rbmk = (RBMKBase)world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			int[] pos = rbmk.findCore(world, mop.blockX, mop.blockY, mop.blockZ);
			
			if(pos == null)
				return;
			
			TileEntityRBMKBase te = (TileEntityRBMKBase)world.getTileEntity(pos[0], pos[1], pos[2]);
			NBTTagCompound flush = new NBTTagCompound();
			te.getDiagData(flush);
			Set<String> keys = flush.func_150296_c();
			
			GL11.glPushMatrix();
			
			int pX = resolution.getScaledWidth() / 2 + 8;
			int pZ = resolution.getScaledHeight() / 2;
			
			List<String> exceptions = new ArrayList();
			exceptions.add("x");
			exceptions.add("y");
			exceptions.add("z");
			exceptions.add("items");
			exceptions.add("id");

			String title = "Dump of Ordered Data Diagnostic (DODD)";
			mc.fontRenderer.drawString(title, pX + 1, pZ - 19, 0x006000);
			mc.fontRenderer.drawString(title, pX, pZ - 20, 0x00FF00);

			mc.fontRenderer.drawString(I18nUtil.resolveKey(rbmk.getUnlocalizedName() + ".name"), pX + 1, pZ - 9, 0x606000);
			mc.fontRenderer.drawString(I18nUtil.resolveKey(rbmk.getUnlocalizedName() + ".name"), pX, pZ - 10, 0xffff00);
			
			String[] ents = new String[keys.size()];
			keys.toArray(ents);
			Arrays.sort(ents);
			
			for(String key : ents) {
				
				if(exceptions.contains(key))
					continue;
				
				mc.fontRenderer.drawString(key + ": " + flush.getTag(key), pX, pZ, 0xFFFFFF);
				pZ += 10;
			}

			GL11.glDisable(GL11.GL_BLEND);

			GL11.glPopMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
		}
	}
	
	public void onOverheat() {
		
		for(int i = 0; i < 4; i++) {
			worldObj.setBlock(xCoord, yCoord + i, zCoord, Blocks.lava);
		}
	}
	
	public void onMelt(int reduce) {

		reduce = MathHelper.clamp_int(reduce, 1, 3);
		
		if(worldObj.rand.nextInt(3) == 0)
			reduce++;
		
		for(int i = 3; i >= 0; i--) {
			
			if(i <= 4 - reduce) {
				
				if(reduce > 1 && i == 4 - reduce && worldObj.rand.nextInt(3) == 0)
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.corium_block);
				else
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris);
				
			} else {
				worldObj.setBlock(xCoord, yCoord + i, zCoord, Blocks.air);
			}
			worldObj.markBlockForUpdate(xCoord, yCoord + i, zCoord);
		}
	}
	
	public static HashSet<TileEntityRBMKBase> columns = new HashSet();
	
	public void meltdown() {
		
		columns.clear();
		getFF(xCoord, yCoord, zCoord);
		
		int minX = xCoord;
		int maxX = xCoord;
		int minZ = zCoord;
		int maxZ = zCoord;
		
		//set meltdown bounds
		for(TileEntityRBMKBase rbmk : columns) {

			if(rbmk.xCoord < minX)
				minX = rbmk.xCoord;
			if(rbmk.xCoord > maxX)
				maxX = rbmk.xCoord;
			if(rbmk.zCoord < minZ)
				minZ = rbmk.zCoord;
			if(rbmk.zCoord > maxZ)
				maxZ = rbmk.zCoord;
		}
		
		for(TileEntityRBMKBase rbmk : columns) {

			int distFromMinX = rbmk.xCoord - minX;
			int distFromMaxX = maxX - rbmk.xCoord;
			int distFromMinZ = rbmk.zCoord - minZ;
			int distFromMaxZ = maxZ - rbmk.zCoord;
			
			int minDist = Math.min(distFromMinX, Math.min(distFromMaxX, Math.min(distFromMinZ, distFromMaxZ)));
			
			rbmk.onMelt(minDist + 1);
		}
	}
	
	private void getFF(int x, int y, int z) {
		
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRBMKBase) {
			
			TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
			
			if(!columns.contains(rbmk)) {
				columns.add(rbmk);
				getFF(x + 1, y, z);
				getFF(x - 1, y, z);
				getFF(x, y, z + 1);
				getFF(x, y, z - 1);
			}
		}
	}
}
