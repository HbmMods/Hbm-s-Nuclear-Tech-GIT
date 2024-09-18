package com.hbm.tileentity.machine;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK3.ATEntry;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.container.ContainerCore;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICore;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ArmorUtil;
import com.hbm.util.CompatEnergyControl;

import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityCore extends TileEntityMachineBase implements IGUIProvider, IInfoProviderEC {
	
	public int field;
	public int heat;
	public int color;
	public FluidTank[] tanks;
	private boolean lastTickValid = false;
	public boolean meltdownTick = false;
	protected int consumption;
	protected int prevConsumption;

	public TileEntityCore() {
		super(3);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.DEUTERIUM, 128000);
		tanks[1] = new FluidTank(Fluids.TRITIUM, 128000);
	}

	@Override
	public String getName() {
		return "container.dfcCore";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.prevConsumption = this.consumption;
			this.consumption = 0;
			
			int chunkX = xCoord >> 4;
			int chunkZ = zCoord >> 4;
			
			meltdownTick = false;
			
			lastTickValid = worldObj.getChunkProvider().chunkExists(chunkX, chunkZ) &&
					worldObj.getChunkProvider().chunkExists(chunkX + 1, chunkZ + 1) &&
					worldObj.getChunkProvider().chunkExists(chunkX + 1, chunkZ - 1) &&
					worldObj.getChunkProvider().chunkExists(chunkX - 1, chunkZ + 1) &&
					worldObj.getChunkProvider().chunkExists(chunkX - 1, chunkZ - 1);
			
			if(lastTickValid && heat > 0 && heat >= field) {
				
				int fill = tanks[0].getFill() + tanks[1].getFill();
				int max = tanks[0].getMaxFill() + tanks[1].getMaxFill();
				int mod = heat * 10;
				
				int size = Math.max(Math.min(fill * mod / max, 1000), 50);
				
				boolean canExplode = true;
				Iterator<Entry<ATEntry, Long>> it = EntityNukeExplosionMK3.at.entrySet().iterator();
				while(it.hasNext()) {
					Entry<ATEntry, Long> next = it.next();
					if(next.getValue() < worldObj.getTotalWorldTime()) {
						it.remove();
						continue;
					}
					ATEntry entry = next.getKey();
					if(entry.dim != worldObj.provider.dimensionId)  continue;
					Vec3 vec = Vec3.createVectorHelper(xCoord + 0.5 - entry.x, yCoord + 0.5 - entry.y, zCoord + 0.5 - entry.z);
					if(vec.lengthVector() < 300) {
						canExplode = false;
						break;
					}
				}
				
				if(canExplode) {
					
					EntityNukeExplosionMK3 ex = new EntityNukeExplosionMK3(worldObj);
					ex.posX = xCoord + 0.5;
					ex.posY = yCoord + 0.5;
					ex.posZ = zCoord + 0.5;
					ex.destructionRange = size;
					ex.speed = BombConfig.blastSpeed;
					ex.coefficient = 1.0F;
					ex.waste = false;
					worldObj.spawnEntityInWorld(ex);
					
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.explode", 100000.0F, 1.0F);
					
					EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(worldObj, size);
					cloud.posX = xCoord;
					cloud.posY = yCoord;
					cloud.posZ = zCoord;
					worldObj.spawnEntityInWorld(cloud);
					
				} else {
					meltdownTick = true;
					ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, 100);
				}
			}
			
			if(slots[0] != null && slots[2] != null && slots[0].getItem() instanceof ItemCatalyst && slots[2].getItem() instanceof ItemCatalyst)
				color = calcAvgHex(
						((ItemCatalyst)slots[0].getItem()).getColor(),
						((ItemCatalyst)slots[2].getItem()).getColor()
				);
			else
				color = 0;
			
			if(heat > 0)
				radiation();
			
			NBTTagCompound data = new NBTTagCompound();
			tanks[0].writeToNBT(data, "t0");
			tanks[1].writeToNBT(data, "t1");
			data.setInteger("field", field);
			data.setInteger("heat", heat);
			data.setInteger("color", color);
			data.setBoolean("melt", meltdownTick);
			networkPack(data, 250);
			
			heat = 0;
			
			if(lastTickValid && field > 0) {
				field -= 1;
			}
			
			this.markDirty();
		} else {
			
			//TODO: sick particle effects
		}
		
	}

	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);

		tanks[0].readFromNBT(data, "t0");
		tanks[1].readFromNBT(data, "t1");
		field = data.getInteger("field");
		heat = data.getInteger("heat");
		color = data.getInteger("color");
		meltdownTick = data.getBoolean("melt");
	}
	
	private void radiation() {
		
		double scale = this.meltdownTick ? 5 : 3;
		double range = this.meltdownTick ? 50 : 10;
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(range, range, range));
		
		for(Entity e : list) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHazmat((EntityPlayer)e)))
				if(!Library.isObstructed(worldObj, xCoord + 0.5, yCoord + 0.5 + 6, zCoord + 0.5, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
					e.attackEntityFrom(ModDamageSource.ams, 1000);
					e.setFire(3);
				}
		}

		List<Entity> list2 =worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(scale, scale, scale));
		
		for(Entity e : list2) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHaz2((EntityPlayer)e)))
					e.attackEntityFrom(ModDamageSource.amsCore, 10000);
		}
	}
	
	public int getFieldScaled(int i) {
		return (field * i) / 100;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / 100;
	}
	
	public boolean isReady() {
		
		if(!lastTickValid)
			return false;
		
		if(getCore() == 0)
			return false;
		
		if(color == 0)
			return false;
		
		if(getFuelEfficiency(tanks[0].getTankType()) <= 0 || getFuelEfficiency(tanks[1].getTankType()) <= 0)
			return false;
		
		return true;
	}
	
	//100 emitter watt = 10000 joules = 1 heat = 10mB burned
	public long burn(long joules) {
		
		//check if a reaction can take place
		if(!isReady())
			return joules;
		
		int demand = (int)Math.ceil((double)joules / 1000D);
		
		//check if the reaction has enough valid fuel
		if(tanks[0].getFill() < demand || tanks[1].getFill() < demand)
			return joules;
		
		this.consumption += demand;
		
		heat += (int)Math.ceil((double)joules / 10000D);

		tanks[0].setFill(tanks[0].getFill() - demand);
		tanks[1].setFill(tanks[1].getFill() - demand);
		
		return (long) (joules * getCore() * getFuelEfficiency(tanks[0].getTankType()) * getFuelEfficiency(tanks[1].getTankType()));
	}
	
	public float getFuelEfficiency(FluidType type) {
		if(type == Fluids.HYDROGEN)
			return 1.0F;
		if(type == Fluids.DEUTERIUM)
			return 1.5F;
		if(type == Fluids.TRITIUM)
			return 1.7F;
		if(type == Fluids.OXYGEN)
			return 1.2F;
		if(type == Fluids.PEROXIDE)
			return 1.4F;
		if(type == Fluids.XENON)
			return 1.5F;
		if(type == Fluids.SAS3)
			return 2.0F;
		if(type == Fluids.BALEFIRE)
			return 2.5F;
		if(type == Fluids.AMAT)
			return 2.2F;
		if(type == Fluids.ASCHRAB)
			return 2.7F;
		return 0;
	}
	
	//TODO: move stats to the AMSCORE class
	public int getCore() {
		
		if(slots[1] == null) {
			return 0;
		}
		
		if(slots[1].getItem() == ModItems.ams_core_sing)
			return 500;
		
		if(slots[1].getItem() == ModItems.ams_core_wormhole)
			return 650;
		
		if(slots[1].getItem() == ModItems.ams_core_eyeofharmony)
			return 800;
		
		if(slots[1].getItem() == ModItems.ams_core_thingy)
			return 2500;
		
		return 0;
	}
	
	private int calcAvgHex(int h1, int h2) {

		int r1 = ((h1 & 0xFF0000) >> 16);
		int g1 = ((h1 & 0x00FF00) >> 8);
		int b1 = ((h1 & 0x0000FF) >> 0);
		
		int r2 = ((h2 & 0xFF0000) >> 16);
		int g2 = ((h2 & 0x00FF00) >> 8);
		int b2 = ((h2 & 0x0000FF) >> 0);

		int r = (((r1 + r2) / 2) << 16);
		int g = (((g1 + g2) / 2) << 8);
		int b = (((b1 + b2) / 2) << 0);
		
		return r | g | b;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		tanks[0].readFromNBT(nbt, "fuel1");
		tanks[1].readFromNBT(nbt, "fuel2");
		this.field = nbt.getInteger("field");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tanks[0].writeToNBT(nbt, "fuel1");
		tanks[1].writeToNBT(nbt, "fuel2");
		nbt.setInteger("field", this.field);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 4,
					yCoord + 0.5 - 4,
					zCoord + 0.5 - 4,
					xCoord + 0.5 + 5,
					yCoord + 0.5 + 5,
					zCoord + 0.5 + 5
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCore(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICore(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, this.prevConsumption);
	}
}
