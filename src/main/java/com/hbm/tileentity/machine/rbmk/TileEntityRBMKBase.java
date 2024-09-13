package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluid.IFluidConductor;
import api.hbm.fluid.IFluidConnector;
import api.hbm.fluid.IPipeNet;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.entity.effect.EntitySpear;
import com.hbm.entity.projectile.EntityRBMKDebris;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.packet.toclient.NBTPacket;
import com.hbm.saveddata.TomSaveData;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.IOverpressurable;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.Compat;
import com.hbm.util.I18nUtil;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.*;

/**
 * Base class for all RBMK components, active or passive. Handles heat and the explosion sequence
 * @author hbm
 *
 */
public abstract class TileEntityRBMKBase extends TileEntityLoadedBase implements INBTPacketReceiver {
	
	public double heat;
	
	public int water;
	public static final int maxWater = 16000;
	public int steam;
	public static final int maxSteam = 16000;

	public boolean hasLid() {
		
		if(!isLidRemovable())
			return true;
		
		return this.getBlockMetadata() != RBMKBase.DIR_NO_LID.ordinal() + RBMKBase.offset;
	}

	public boolean isLidRemovable() {
		return true;
	}
	
	/**
	 * Approx melting point of steel
	 * Fuels often burn much hotter than this but it won't affect the column too much due to low diffusion
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
		return RBMKDials.getPassiveCooling(worldObj); //default: 1.0D
	}
	
	//necessary checks to figure out whether players are close enough to ensure that the reactor can be safely used
	public boolean shouldUpdate() {
		return true;
	}
	
	//unused
	public int trackingRange() {
		return 15;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.worldObj.theProfiler.startSection("rbmkBase_heat_movement");
			moveHeat();
			if(RBMKDials.getReasimBoilers(worldObj)) {
				this.worldObj.theProfiler.endStartSection("rbmkBase_reasim_boilers");
				boilWater();
			}

			this.worldObj.theProfiler.endStartSection("rbmkBase_rpassive_cooling");
			coolPassively();
			this.worldObj.theProfiler.endSection();
			
			NBTTagCompound data = new NBTTagCompound();
			this.writeToNBT(data);
			this.networkPack(data, trackingRange());
		}
	}
	
	/**
	 * The ReaSim boiler dial causes all RBMK parts to behave like boilers
	 */
	private void boilWater() {
		
		if(heat < 100D)
			return;
		
		double heatConsumption = RBMKDials.getBoilerHeatConsumption(worldObj);
		double availableHeat = (this.heat - 100) / heatConsumption;
		double availableWater = this.water;
		double availableSpace = this.maxSteam - this.steam;
		
		int processedWater = (int) Math.floor(Math.min(availableHeat, Math.min(availableWater, availableSpace)) * RBMKDials.getReaSimBoilerSpeed(worldObj));
		
		this.water -= processedWater;
		this.steam += processedWater;
		this.heat -= processedWater * heatConsumption;
	}
	
	public static final ForgeDirection[] neighborDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};
	
	protected TileEntityRBMKBase[] neighborCache = new TileEntityRBMKBase[4];
	
	/**
	 * Moves heat to neighboring parts, if possible, in a relatively fair manner
	 */
	private void moveHeat() {
		
		List<TileEntityRBMKBase> rec = new ArrayList();
		rec.add(this);
		double heatTot = this.heat;
		int waterTot = this.water;
		int steamTot = this.steam;
		
		int index = 0;
		for(ForgeDirection dir : neighborDirs) {
			
			if(neighborCache[index] != null && neighborCache[index].isInvalid())
				neighborCache[index] = null;
			
			if(neighborCache[index] == null) {
				TileEntity te = Compat.getTileStandard(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
				
				if(te instanceof TileEntityRBMKBase) {
					TileEntityRBMKBase base = (TileEntityRBMKBase) te;
					neighborCache[index] = base;
				}
			}
			
			index++;
		}
		
		for(TileEntityRBMKBase base : neighborCache) {
			
			if(base != null) {
				rec.add(base);
				heatTot += base.heat;
				waterTot += base.water;
				steamTot += base.steam;
			}
		}
		
		int members = rec.size();
		double stepSize = RBMKDials.getColumnHeatFlow(worldObj);
		
		if(members > 1) {
			
			double targetHeat = heatTot / (double)members;
			
			int tWater = waterTot / members;
			int rWater = waterTot % members;
			int tSteam = steamTot / members;
			int rSteam = steamTot % members;
			
			for(TileEntityRBMKBase rbmk : rec) {
				double delta = targetHeat - rbmk.heat;
				rbmk.heat += delta * stepSize;
				
				//set to the averages, rounded down
				rbmk.water = tWater;
				rbmk.steam = tSteam;
			}
			
			//add the modulo to make up for the losses coming from rounding
			this.water += rWater;
			this.steam += rSteam;
			
			this.markDirty();
		}
	}
	
	@Override
	public void markDirty() {
		
		if(this.worldObj != null) {
			this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		}
	}
	
	protected void coolPassively() {

		if(TomSaveData.forWorld(worldObj).fire > 1e-5) {
			double light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord) / 15D;
			if(heat < 20 + (480 * light)) {
				this.heat += this.passiveCooling() * 2;
			}
		}
		
		this.heat -= this.passiveCooling();
		
		if(heat < 20)
			heat = 20D;
	}
	
	protected static boolean diag = false;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		if(!diag) {
			super.readFromNBT(nbt);
		}

		this.heat = nbt.getDouble("heat");
		this.water = nbt.getInteger("water");
		this.steam = nbt.getInteger("steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		if(!diag) {
			super.writeToNBT(nbt);
		}
		
		nbt.setDouble("heat", this.heat);
		nbt.setInteger("water", this.water);
		nbt.setInteger("steam", this.steam);
	}
	
	public void networkPack(NBTTagCompound nbt, int range) {

		diag = true;
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
		diag = false;
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		
		diag = true;
		this.readFromNBT(nbt);
		diag = false;
	}
	
	public void getDiagData(NBTTagCompound nbt) {
		diag = true;
		this.writeToNBT(nbt);
		diag = false;
	}
	
	@SideOnly(Side.CLIENT)
	public static void diagnosticPrintHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution resolution = event.resolution;

		RBMKBase rbmk = (RBMKBase) world.getBlock(x, y, z);
		int[] pos = rbmk.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntityRBMKBase te = (TileEntityRBMKBase) world.getTileEntity(pos[0], pos[1], pos[2]);
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
	
	public void onOverheat() {
		
		for(int i = 0; i < 4; i++) {
			worldObj.setBlock(xCoord, yCoord + i, zCoord, Blocks.lava);
		}
	}
	
	public void onMelt(int reduce) {
		
		standardMelt(reduce);
		
		if(this.getBlockMetadata() == RBMKBase.DIR_NORMAL_LID.ordinal() + RBMKBase.offset)
			spawnDebris(DebrisType.LID);
	}
	
	protected void standardMelt(int reduce) {
		
		int h = RBMKDials.getColumnHeight(worldObj);
		reduce = MathHelper.clamp_int(reduce, 1, h);
		
		if(worldObj.rand.nextInt(3) == 0)
			reduce++;
		
		for(int i = h; i >= 0; i--) {
			
			if(i <= h + 1 - reduce) {
				
				if(reduce > 1 && i == h + 1 - reduce) {
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris_burning);
				} else {
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris);
				}
				
			} else {
				worldObj.setBlock(xCoord, yCoord + i, zCoord, Blocks.air);
			}
			worldObj.markBlockForUpdate(xCoord, yCoord + i, zCoord);
		}
	}
	
	protected void spawnDebris(DebrisType type) {

		EntityRBMKDebris debris = new EntityRBMKDebris(worldObj, xCoord + 0.5D, yCoord + 4D, zCoord + 0.5D, type);
		debris.motionX = worldObj.rand.nextGaussian() * 0.25D;
		debris.motionZ = worldObj.rand.nextGaussian() * 0.25D;
		debris.motionY = 0.25D + worldObj.rand.nextDouble() * 1.25D;
		
		if(type == DebrisType.LID) {
			debris.motionX *= 0.5D;
			debris.motionY += 0.5D;
			debris.motionZ *= 0.5D;
		}
		
		worldObj.spawnEntityInWorld(debris);
	}
	
	public static HashSet<TileEntityRBMKBase> columns = new HashSet();
	public static HashSet<IPipeNet> pipes = new HashSet();
	
	//assumes that !worldObj.isRemote
	public void meltdown() {
		
		RBMKBase.dropLids = false;
		
		columns.clear();
		pipes.clear();
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
		
		for(TileEntityRBMKBase rbmk : columns) {
			
			if(rbmk instanceof TileEntityRBMKRod && worldObj.getBlock(rbmk.xCoord, rbmk.yCoord, rbmk.zCoord) == ModBlocks.corium_block) {
				
				for(int x = rbmk.xCoord - 1; x <= rbmk.xCoord + 1; x ++) {
					for(int y = rbmk.yCoord - 1; y <= rbmk.yCoord + 1; y ++) {
						for(int z = rbmk.zCoord - 1; z <= rbmk.zCoord + 1; z ++) {
							
							Block b = worldObj.getBlock(x, y, z);
							
							if(worldObj.rand.nextInt(3) == 0 && (b == ModBlocks.pribris || b == ModBlocks.pribris_burning)) {
								
								if(RBMKBase.digamma)
									worldObj.setBlock(x, y, z, ModBlocks.pribris_digamma);
								else
									worldObj.setBlock(x, y, z, ModBlocks.pribris_radiating);
							}
						}
					}
				}
			}
		}
		
		/* Hanlde overpressure event */
		if(RBMKDials.getOverpressure(worldObj) && !pipes.isEmpty()) {
			HashSet<IFluidConductor> pipeBlocks = new HashSet();
			HashSet<IFluidConnector> pipeReceivers = new HashSet();
			
			//unify all parts into single sets to prevent redundancy
			pipes.forEach(x -> {
				pipeBlocks.addAll(x.getLinks());
				pipeReceivers.addAll(x.getSubscribers());
			});
			
			int count = 0;
			int max = Math.min(pipeBlocks.size() / 5, 100);
			Iterator<IFluidConductor>  itPipes = pipeBlocks.iterator();
			Iterator<IFluidConnector>  itReceivers = pipeReceivers.iterator();
			
			while(itPipes.hasNext() && count < max) {
				IFluidConductor pipe = itPipes.next();
				if(pipe instanceof TileEntity) {
					TileEntity tile = (TileEntity) pipe;
					worldObj.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, Blocks.air);
				}
				count++;
			}
			
			while(itReceivers.hasNext()) {
				IFluidConnector con = itReceivers.next();
				if(con instanceof TileEntity) {
					TileEntity tile = (TileEntity) con;
					if(con instanceof IOverpressurable) {
						((IOverpressurable) con).explode(worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
					} else {
						worldObj.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, Blocks.air);
						worldObj.newExplosion(null, tile.xCoord + 0.5, tile.yCoord + 0.5, tile.zCoord + 0.5, 5F, false, false);
					}
				}
			}
		}
		
		int smallDim = Math.min(maxX - minX, maxZ - minZ);
		int avgX = minX + (maxX - minX) / 2;
		int avgZ = minZ + (maxZ - minZ) / 2;
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "rbmkmush");
		data.setFloat("scale", smallDim);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, avgX + 0.5, yCoord + 1, avgZ + 0.5), new TargetPoint(worldObj.provider.dimensionId,avgX + 0.5, yCoord + 1, avgZ + 0.5, 250));
		MainRegistry.proxy.effectNT(data);
		
		worldObj.playSoundEffect(avgX + 0.5, yCoord + 1, avgZ + 0.5, "hbm:block.rbmk_explosion", 50.0F, 1.0F);
		
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class,
				AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(50, 50, 50));
		
		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.achRBMKBoom);
		}
		
		if(RBMKBase.digamma) {
			EntitySpear spear = new EntitySpear(worldObj);
			spear.posX = avgX + 0.5;
			spear.posZ = avgZ + 0.5;
			spear.posY = yCoord + 100;
			worldObj.spawnEntityInWorld(spear);
		}
		
		RBMKBase.dropLids = true;
		RBMKBase.digamma = false;
	}
	
	private void getFF(int x, int y, int z) {
		
		TileEntity te = Compat.getTileStandard(worldObj, x, y, z);
		
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
	
	public boolean isModerated() {
		return false;
	}
	
	public abstract ColumnType getConsoleType();
	
	public NBTTagCompound getNBTForConsole() {
		return null;
	}
	
	public static List<String> getFancyStats(NBTTagCompound nbt) {
		return null;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 17, zCoord + 1);
	}
}
