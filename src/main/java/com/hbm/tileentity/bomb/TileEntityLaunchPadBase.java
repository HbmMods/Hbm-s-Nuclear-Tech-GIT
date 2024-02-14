package com.hbm.tileentity.bomb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileShuttle;
import com.hbm.entity.missile.EntityMissileStealth;
import com.hbm.entity.missile.EntityMissileTier0.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileTier0.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileTier0.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileTier0.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileTier0.EntityMissileTaint;
import com.hbm.entity.missile.EntityMissileTier1.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileTier1.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileTier1.EntityMissileDecoy;
import com.hbm.entity.missile.EntityMissileTier1.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileTier1.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileTier2.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileTier2.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileTier2.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileTier2.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileTier2.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTier3.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileTier3.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileTier3.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileTier3.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileTier4.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileTier4.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileTier4.EntityMissileVolcano;
import com.hbm.interfaces.IBomb.BombReturnCode;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerLaunchPadLarge;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUILaunchPadLarge;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.MissileFuel;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IRadarCommandReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class TileEntityLaunchPadBase extends TileEntityMachineBase implements IEnergyUser, IFluidStandardReceiver, IGUIProvider, IRadarCommandReceiver {
	
	/** Automatic instantiation of generic missiles, i.e. everything that both extends EntityMissileBaseNT and needs a designator */
	public static final HashMap<ComparableStack, Class<? extends EntityMissileBaseNT>> missiles = new HashMap();
	
	public static void registerLaunchables() {

		//Tier 0
		missiles.put(new ComparableStack(ModItems.missile_micro), EntityMissileMicro.class);
		missiles.put(new ComparableStack(ModItems.missile_schrabidium), EntityMissileSchrabidium.class);
		missiles.put(new ComparableStack(ModItems.missile_bhole), EntityMissileBHole.class);
		missiles.put(new ComparableStack(ModItems.missile_taint), EntityMissileTaint.class);
		missiles.put(new ComparableStack(ModItems.missile_emp), EntityMissileEMP.class);
		//Tier 1
		missiles.put(new ComparableStack(ModItems.missile_generic), EntityMissileGeneric.class);
		missiles.put(new ComparableStack(ModItems.missile_decoy), EntityMissileDecoy.class);
		missiles.put(new ComparableStack(ModItems.missile_incendiary), EntityMissileIncendiary.class);
		missiles.put(new ComparableStack(ModItems.missile_cluster), EntityMissileCluster.class);
		missiles.put(new ComparableStack(ModItems.missile_buster), EntityMissileBunkerBuster.class);
		//Tier 2
		missiles.put(new ComparableStack(ModItems.missile_strong), EntityMissileStrong.class);
		missiles.put(new ComparableStack(ModItems.missile_incendiary_strong), EntityMissileIncendiaryStrong.class);
		missiles.put(new ComparableStack(ModItems.missile_cluster_strong), EntityMissileClusterStrong.class);
		missiles.put(new ComparableStack(ModItems.missile_buster_strong), EntityMissileBusterStrong.class);
		missiles.put(new ComparableStack(ModItems.missile_emp_strong), EntityMissileEMPStrong.class);
		//Tier 3
		missiles.put(new ComparableStack(ModItems.missile_burst), EntityMissileBurst.class);
		missiles.put(new ComparableStack(ModItems.missile_inferno), EntityMissileInferno.class);
		missiles.put(new ComparableStack(ModItems.missile_rain), EntityMissileRain.class);
		missiles.put(new ComparableStack(ModItems.missile_drill), EntityMissileDrill.class);
		missiles.put(new ComparableStack(ModItems.missile_shuttle), EntityMissileShuttle.class);
		//Tier 4
		missiles.put(new ComparableStack(ModItems.missile_nuclear), EntityMissileNuclear.class);
		missiles.put(new ComparableStack(ModItems.missile_nuclear_cluster), EntityMissileMirv.class);
		missiles.put(new ComparableStack(ModItems.missile_volcano), EntityMissileVolcano.class);

		missiles.put(new ComparableStack(ModItems.missile_doomsday), EntityMissileDoomsday.class);
		missiles.put(new ComparableStack(ModItems.missile_stealth), EntityMissileStealth.class);
	}

	public ItemStack toRender;
	
	public long power;
	public final long maxPower = 100_000;

	public int prevRedstonePower;
	public int redstonePower;
	public Set<BlockPos> activatedBlocks = new HashSet<>(4);
	
	public FluidTank[] tanks;

	public TileEntityLaunchPadBase() {
		super(7);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.NONE, 24_000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 24_000);
	}

	@Override
	public String getName() {
		return "container.launchPad";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.redstonePower > 0 && this.prevRedstonePower == 0) {
				this.launchFromDesignator();
			}
			
			this.prevRedstonePower = this.redstonePower;
			
			this.power = Library.chargeTEFromItems(slots, 2, power, maxPower);
			tanks[0].loadTank(3, 4, slots);
			tanks[1].loadTank(5, 6, slots);
			
			if(this.isMissileValid()) {
				if(slots[0].getItem() instanceof ItemMissile) {
					ItemMissile missile = (ItemMissile) slots[0].getItem();
					setFuel(missile);
				}
			}

			this.networkPackNT(250);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		
		buf.writeLong(this.power);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		
		if(slots[0] != null) {
			buf.writeBoolean(true);
			buf.writeInt(Item.getIdFromItem(slots[0].getItem()));
			buf.writeShort((short) slots[0].getItemDamage());
		} else {
			buf.writeBoolean(false);
		}
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		
		this.power = buf.readLong();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		
		if(buf.readBoolean()) {
			this.toRender = new ItemStack(Item.getItemById(buf.readInt()), 1, buf.readShort());
		} else {
			this.toRender = null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");

		this.redstonePower = nbt.getInteger("redstonePower");
		this.prevRedstonePower = nbt.getInteger("prevRedstonePower");
		NBTTagCompound activatedBlocks = nbt.getCompoundTag("activatedBlocks");
		this.activatedBlocks.clear();
		for(int i = 0; i < activatedBlocks.func_150296_c().size() / 3; i++) {
			this.activatedBlocks.add(new BlockPos(activatedBlocks.getInteger("x" + i), activatedBlocks.getInteger("y" + i), activatedBlocks.getInteger("z" + i)));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "t0");
		tanks[1].writeToNBT(nbt, "t1");

		nbt.setInteger("redstonePower", redstonePower);
		nbt.setInteger("prevRedstonePower", prevRedstonePower);
		NBTTagCompound activatedBlocks = new NBTTagCompound();
		int i = 0;
		for(BlockPos p : this.activatedBlocks) {
			activatedBlocks.setInteger("x" + i, p.getX());
			activatedBlocks.setInteger("y" + i, p.getY());
			activatedBlocks.setInteger("z" + i, p.getZ());
			i++;
		}
		nbt.setTag("activatedBlocks", activatedBlocks);
	}

	public void updateRedstonePower(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		boolean powered = worldObj.isBlockIndirectlyGettingPowered(x, y, z);
		boolean contained = activatedBlocks.contains(pos);
		if(!contained && powered){
			activatedBlocks.add(pos);
			if(redstonePower == -1){
				redstonePower = 0;
			}
			redstonePower++;
		} else if(contained && !powered){
			activatedBlocks.remove(pos);
			redstonePower--;
			if(redstonePower == 0){
				redstonePower = -1;
			}
		}
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }
	@Override public FluidTank[] getAllTanks() { return this.tanks; }
	@Override public FluidTank[] getReceivingTanks() { return this.tanks; }

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLaunchPadLarge(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILaunchPadLarge(player.inventory, this);
	}
	
	@SuppressWarnings("incomplete-switch") //shut up
	public void setFuel(ItemMissile missile) {
		switch(missile.fuel) {
		case ETHANOL_PEROXIDE:
			tanks[0].setTankType(Fluids.ETHANOL);
			tanks[1].setTankType(Fluids.ACID);
			break;
		case KEROSENE_PEROXIDE:
			tanks[0].setTankType(Fluids.KEROSENE);
			tanks[1].setTankType(Fluids.ACID);
			break;
		case KEROSENE_LOXY:
			tanks[0].setTankType(Fluids.KEROSENE);
			tanks[1].setTankType(Fluids.OXYGEN);
			break;
		case JETFUEL_LOXY:
			tanks[0].setTankType(Fluids.KEROSENE_REFORM);
			tanks[1].setTankType(Fluids.OXYGEN);
			break;
		}
	}
	
	/** Requires the missile slot to be non-null and he item to be compatible */
	public boolean isMissileValid() {
		return slots[0] != null && slots[0].getItem() instanceof ItemMissile;
	}
	
	public boolean hasFuel() {
		if(this.power < 75_000) return false;
		
		if(slots[0] != null && slots[0].getItem() instanceof ItemMissile) {
			ItemMissile missile = (ItemMissile) slots[0].getItem();
			if(this.tanks[0].getFill() < missile.fuelCap) return false;
			if(this.tanks[1].getFill() < missile.fuelCap) return false;
			
			return true;
		}
		
		return false;
	}
	
	public Entity instantiateMissile(int targetX, int targetZ) {
		
		if(slots[0] == null) return null;
		
		Class<? extends EntityMissileBaseNT> clazz = TileEntityLaunchPadBase.missiles.get(new ComparableStack(slots[0]).makeSingular());
		
		if(clazz != null) {
			try {
				EntityMissileBaseNT missile = clazz.getConstructor(World.class, float.class, float.class, float.class, int.class, int.class).newInstance(worldObj, xCoord + 0.5F, yCoord + (float) getLaunchOffset() /* Position arguments need to be floats, jackass */, zCoord + 0.5F, targetX, targetZ);
				if(GeneralConfig.enableExtendedLogging) MainRegistry.logger.log(Level.INFO, "[MISSILE] Tried to launch missile at " + xCoord + " / " + yCoord + " / " + zCoord + " to " + xCoord + " / " + zCoord + "!");
				missile.getDataWatcher().updateObject(3, (byte) MathHelper.clamp_int(this.getBlockMetadata() - 10, 2, 5));
				return missile;
			} catch(Exception e) { }
		}

		if(slots[0].getItem() == ModItems.missile_anti_ballistic) {
			EntityMissileAntiBallistic missile = new EntityMissileAntiBallistic(worldObj);
			missile.posX = xCoord + 0.5D;
			missile.posY = yCoord + getLaunchOffset();
			missile.posZ = zCoord + 0.5D;
			return missile;
		}
		
		return null;
	}
	
	public void finalizeLaunch(Entity missile) {
		worldObj.spawnEntityInWorld(missile);
		worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);

		this.power -= 75_000;
		
		if(slots[0] != null && slots[0].getItem() instanceof ItemMissile) {
			ItemMissile item = (ItemMissile) slots[0].getItem();
			tanks[0].setFill(tanks[0].getFill() - item.fuelCap);
			tanks[1].setFill(tanks[1].getFill() - item.fuelCap);
		}
		
		this.decrStackSize(0, 1);
	}
	
	public BombReturnCode launchFromDesignator() {
		if(!canLaunch()) return BombReturnCode.ERROR_MISSING_COMPONENT;
		
		boolean needsDesignator = needsDesignator(slots[0].getItem());

		int targetX = 0;
		int targetZ = 0;
		
		if(slots[1] != null && slots[1].getItem() instanceof IDesignatorItem) {
			IDesignatorItem designator = (IDesignatorItem) slots[1].getItem();
			
			if(!designator.isReady(worldObj, slots[1], xCoord, yCoord, zCoord) && needsDesignator) return BombReturnCode.ERROR_MISSING_COMPONENT;
			
			Vec3 coords = designator.getCoords(worldObj, slots[1], xCoord, yCoord, zCoord);
			targetX = (int) Math.floor(coords.xCoord);
			targetZ = (int) Math.floor(coords.zCoord);
			
		} else {
			if(needsDesignator) return BombReturnCode.ERROR_MISSING_COMPONENT;
		}
		
		return this.launchToCoordinate(targetX, targetZ);
	}
	
	public BombReturnCode launchToEntity(Entity entity) {
		if(!canLaunch()) return BombReturnCode.ERROR_MISSING_COMPONENT;
		
		Entity e = instantiateMissile((int) Math.floor(entity.posX), (int) Math.floor(entity.posZ));
		if(e != null) {
			
			if(e instanceof EntityMissileAntiBallistic) {
				EntityMissileAntiBallistic abm = (EntityMissileAntiBallistic) e;
				abm.tracking = entity;
			}
			
			finalizeLaunch(e);
			return BombReturnCode.LAUNCHED;
		}
		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}
	
	public BombReturnCode launchToCoordinate(int targetX, int targetZ) {
		if(!canLaunch()) return BombReturnCode.ERROR_MISSING_COMPONENT;
		
		Entity e = instantiateMissile(targetX, targetZ);
		if(e != null) {
			finalizeLaunch(e);
			return BombReturnCode.LAUNCHED;
		}
		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}

	@Override
	public boolean sendCommandPosition(int x, int y, int z) {
		return this.launchToCoordinate(x, z) == BombReturnCode.LAUNCHED;
	}

	@Override
	public boolean sendCommandEntity(Entity target) {
		return this.launchToEntity(target) == BombReturnCode.LAUNCHED;
	}
	
	public boolean needsDesignator(Item item) {
		return item != ModItems.missile_anti_ballistic;
	}
	
	/** Full launch condition, checks if the item is launchable, fuel and power are present and any additional checks based on launch pad type */
	public boolean canLaunch() {
		return this.isMissileValid() && this.hasFuel() && this.isReadyForLaunch();
	}
	
	public int getFuelState() {
		return getGaugeState(0);
	}
	
	public int getOxidizerState() {
		return getGaugeState(1);
	}
	
	public int getGaugeState(int tank) {
		if(slots[0] == null) return 0;
		
		if(slots[0].getItem() instanceof ItemMissile) {
			ItemMissile missile = (ItemMissile) slots[0].getItem();
			MissileFuel fuel = missile.fuel;
			
			if(fuel == MissileFuel.SOLID) return 0;
			return tanks[tank].getFill() >= missile.fuelCap ? 1 : -1;
		}
		
		return 0;
	}
	
	/** Any extra conditions for launching in addition to the missile being valid and fueled */
	public abstract boolean isReadyForLaunch();
	public abstract double getLaunchOffset();
}
