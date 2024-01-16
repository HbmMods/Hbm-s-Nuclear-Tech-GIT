package com.hbm.tileentity.bomb;

import java.util.HashMap;

import org.apache.logging.log4j.Level;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileShuttle;
import com.hbm.entity.missile.EntityMissileStealth;
import com.hbm.entity.missile.EntityMissileTier0.*;
import com.hbm.entity.missile.EntityMissileTier1.*;
import com.hbm.entity.missile.EntityMissileTier2.*;
import com.hbm.entity.missile.EntityMissileTier3.*;
import com.hbm.entity.missile.EntityMissileTier4.*;
import com.hbm.interfaces.IBomb.BombReturnCode;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerLaunchPadTier1;
import com.hbm.inventory.gui.GUILaunchPadTier1;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IRadarCommandReceiver;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityLaunchPad extends TileEntityMachineBase implements IEnergyUser, SimpleComponent, IGUIProvider, IRadarCommandReceiver {
	
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
		missiles.put(new ComparableStack(ModItems.missile_endo), EntityMissileEndo.class);
		missiles.put(new ComparableStack(ModItems.missile_exo), EntityMissileExo.class);
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
	public final long maxPower = 100000;
	
	private static final int[] slots_bottom = new int[] {0, 1, 2};
	private static final int[] slots_side = new int[] {0};
	
	public TileEntityLaunchPad() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.launchPad";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 2, power, maxPower);
			this.updateConnections();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			if(slots[0] != null) {
				data.setInteger("id", Item.getIdFromItem(slots[0].getItem()));
				data.setShort("meta", (short) slots[0].getItemDamage());
			}
			networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		
		if(nbt.hasKey("id")) {
			this.toRender = new ItemStack(Item.getItemById(nbt.getInteger("id")), 1, nbt.getShort("meta"));
		} else {
			this.toRender = null;
		}
	}
	
	private void updateConnections() {
		this.trySubscribe(worldObj, xCoord + 1, yCoord, zCoord, Library.POS_X);
		this.trySubscribe(worldObj, xCoord - 1, yCoord, zCoord, Library.NEG_X);
		this.trySubscribe(worldObj, xCoord, yCoord, zCoord + 1, Library.POS_Z);
		this.trySubscribe(worldObj, xCoord, yCoord, zCoord - 1, Library.NEG_Z);
		this.trySubscribe(worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		
		if(slots == null || slots.length != 3) slots = new ItemStack[3];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? new int[0] : slots_side);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
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
	public long transferPower(long power) {
		this.power += power;
		if(this.power > this.getMaxPower()) {
			long overshoot = this.power - this.getMaxPower();
			this.power = this.getMaxPower();
			return overshoot;
		}
		return 0;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.UNKNOWN;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public boolean hasPower() {
		return this.power >= 75_000;
	}

	@Override
	public boolean sendCommandPosition(int x, int y, int z) {
		return this.launchToCoordinate(x, z) == BombReturnCode.LAUNCHED;
	}

	@Override
	public boolean sendCommandEntity(Entity target) {
		return this.launchToEntity(target) == BombReturnCode.LAUNCHED;
	}
	
	public BombReturnCode launchFromDesignator() {
		if(slots[0] == null) return BombReturnCode.ERROR_MISSING_COMPONENT;
		
		boolean needsDesignator = missiles.containsKey(new ComparableStack(slots[0]).makeSingular());

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
		if(!hasPower()) return BombReturnCode.ERROR_MISSING_COMPONENT;
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
		if(!hasPower()) return BombReturnCode.ERROR_MISSING_COMPONENT;
		Entity e = instantiateMissile(targetX, targetZ);
		if(e != null) {
			finalizeLaunch(e);
			return BombReturnCode.LAUNCHED;
		}
		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}
	
	public Entity instantiateMissile(int targetX, int targetZ) {
		
		if(slots[0] == null) return null;
		
		if(slots[0].getItem() == ModItems.missile_carrier) {
			EntityCarrier missile = new EntityCarrier(worldObj);
			missile.posX = xCoord + 0.5F;
			missile.posY = yCoord + 1F;
			missile.posZ = zCoord + 0.5F;
			if(slots[1] != null) {
				missile.setPayload(slots[1]);
				this.slots[1] = null;
			}
			worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "hbm:entity.rocketTakeoff", 100.0F, 1.0F);
			return missile;
		}
		
		Class<? extends EntityMissileBaseNT> clazz = this.missiles.get(new ComparableStack(slots[0]).makeSingular());
		
		if(clazz != null) {
			try {
				EntityMissileBaseNT missile = clazz.getConstructor(World.class, float.class, float.class, float.class, int.class, int.class).newInstance(worldObj, xCoord + 0.5F, yCoord + 2F, zCoord + 0.5F, targetX, targetZ);
				worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
				if(GeneralConfig.enableExtendedLogging) MainRegistry.logger.log(Level.INFO, "[MISSILE] Tried to launch missile at " + xCoord + " / " + yCoord + " / " + zCoord + " to " + xCoord + " / " + zCoord + "!");
				return missile;
			} catch(Exception e) { }
		}

		if(slots[0].getItem() == ModItems.missile_anti_ballistic) {
			EntityMissileAntiBallistic missile = new EntityMissileAntiBallistic(worldObj);
			missile.posX = xCoord + 0.5F;
			missile.posY = yCoord + 0.5F;
			missile.posZ = zCoord + 0.5F;
			worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
			return missile;
		}
		
		return null;
	}
	
	public void finalizeLaunch(Entity missile) {
		this.power -= 75_000;
		worldObj.spawnEntityInWorld(missile);
		this.decrStackSize(0, 1);
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "launch_pad";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoords(Context context, Arguments args) {
		if (slots[1] != null && slots[1].getItem() instanceof IDesignatorItem) {
			int xCoord2;
			int zCoord2;
			if (slots[1].stackTagCompound != null) {
				xCoord2 = slots[1].stackTagCompound.getInteger("xCoord");
				zCoord2 = slots[1].stackTagCompound.getInteger("zCoord");
			} else
				return new Object[] {false};

			// Not sure if i should have this
			/*
			if(xCoord2 == xCoord && zCoord2 == zCoord) {
				xCoord2 += 1;
			}
			*/

			return new Object[] {xCoord2, zCoord2};
		}
		return new Object[] {false, "Designator not found"};
	}
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setCoords(Context context, Arguments args) {
		if (slots[1] != null && slots[1].getItem() instanceof IDesignatorItem) {
			slots[1].stackTagCompound = new NBTTagCompound();
			slots[1].stackTagCompound.setInteger("xCoord", args.checkInteger(0));
			slots[1].stackTagCompound.setInteger("zCoord", args.checkInteger(1));

			return new Object[] {true};
		}
		return new Object[] {false, "Designator not found"};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] launch(Context context, Arguments args) {
		((LaunchPad) ModBlocks.launch_pad).explode(worldObj, xCoord, yCoord, zCoord);
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLaunchPadTier1(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILaunchPadTier1(player.inventory, this);
	}
}
