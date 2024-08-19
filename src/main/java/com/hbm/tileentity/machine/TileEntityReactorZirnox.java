package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.entity.projectile.EntityZirnoxDebris;
import com.hbm.entity.projectile.EntityZirnoxDebris.DebrisType;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerReactorZirnox;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIReactorZirnox;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemZirnoxRod;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityReactorZirnox extends TileEntityMachineBase implements IControlReceiver, IFluidStandardTransceiver, SimpleComponent, IGUIProvider, IInfoProviderEC, CompatHandler.OCComponent {

	public int heat;
	public static final int maxHeat = 100000;
	public int pressure;
	public static final int maxPressure = 100000;
	public boolean isOn = false;

	public FluidTank steam;
	public FluidTank carbonDioxide;
	public FluidTank water;
	protected int output;
	
	private static final int[] slots_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };

	public static final HashMap<ComparableStack, ItemStack> fuelMap = new HashMap<ComparableStack, ItemStack>();
	static {
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.NATURAL_URANIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_natural_uranium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.URANIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_uranium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.TH232.ordinal()), new ItemStack(ModItems.rod_zirnox, 1, EnumZirnoxType.THORIUM_FUEL.ordinal()));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.THORIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_thorium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.MOX_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_mox_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.PLUTONIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_plutonium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.U233_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_u233_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.U235_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_u235_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.LES_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_les_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.LITHIUM.ordinal()), new ItemStack(ModItems.rod_zirnox_tritium));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.ZFB_MOX.ordinal()), new ItemStack(ModItems.rod_zirnox_zfb_mox_depleted));
	}

	public TileEntityReactorZirnox() {
		super(28);
		steam = new FluidTank(Fluids.SUPERHOTSTEAM, 8000);
		carbonDioxide = new FluidTank(Fluids.CARBONDIOXIDE, 16000);
		water = new FluidTank(Fluids.WATER, 32000);
	}

	@Override
	public String getName() {
		return "container.zirnox";
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_io;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return i < 24 && stack.getItem() instanceof ItemZirnoxRod;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return i < 24 && !(stack.getItem() instanceof ItemZirnoxRod);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		heat = nbt.getInteger("heat");
		pressure = nbt.getInteger("pressure");
		isOn = nbt.getBoolean("isOn");
		steam.readFromNBT(nbt, "steam");
		carbonDioxide.readFromNBT(nbt, "carbondioxide");
		water.readFromNBT(nbt, "water");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", heat);
		nbt.setInteger("pressure", pressure);
		nbt.setBoolean("isOn", isOn);
		steam.writeToNBT(nbt, "steam");
		carbonDioxide.writeToNBT(nbt, "carbondioxide");
		water.writeToNBT(nbt, "water");

	}

	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		
		this.heat = data.getInteger("heat");
		this.pressure = data.getInteger("pressure");
		this.isOn = data.getBoolean("isOn");
		steam.readFromNBT(data, "t0");
		carbonDioxide.readFromNBT(data, "t1");
		water.readFromNBT(data, "t2");
	}

	public int getGaugeScaled(int i, int type) {
		switch (type) {
			case 0: return (steam.getFill() * i) / steam.getMaxFill();
			case 1: return (carbonDioxide.getFill() * i) / carbonDioxide.getMaxFill();
			case 2: return (water.getFill() * i) / water.getMaxFill();
			case 3: return (this.heat * i) / maxHeat;
			case 4: return (this.pressure * i)  / maxPressure;
			default: return 1;
		}
	}

	private int[] getNeighbouringSlots(int id) {

		switch(id) {
		case 0: return new int[] { 1, 7 };
		case 1: return new int[] { 0, 2, 8 };
		case 2: return new int[] { 1, 9 };
		case 3: return new int[] { 4, 10 };
		case 4: return new int[] { 3, 5, 11 };
		case 5: return new int[] { 4, 6, 12 };
		case 6: return new int[] { 5, 13 };
		case 7: return new int[] { 0, 8, 14 };
		case 8: return new int[] { 1, 7, 9, 15 };
		case 9: return new int[] { 2, 8, 16};
		case 10: return new int[] { 3, 11, 17 };
		case 11: return new int[] { 4, 10, 12, 18 };
		case 12: return new int[] { 5, 11, 13, 19 };
		case 13: return new int[] { 6, 12, 20 };
		case 14: return new int[] { 7, 15, 21 }; 
		case 15: return new int[] { 8, 14, 16, 22 };
		case 16: return new int[] { 9, 15, 23 };
		case 17: return new int[] { 10, 18 };
		case 18: return new int[] { 11, 17, 19 };
		case 19: return new int[] { 12, 18, 20 };
		case 20: return new int[] { 13, 19 };
		case 21: return new int[] { 14, 22 };
		case 22: return new int[] { 15, 21, 23 };
		case 23: return new int[] { 16, 22 };
		}

		return null;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.output = 0;
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				this.updateConnections();
			}
			
			carbonDioxide.loadTank(24, 26, slots);
			water.loadTank(25, 27, slots);
			
			if(isOn) {
				for(int i = 0; i < 24; i++) {

					if(slots[i] != null) {
						if(slots[i].getItem() instanceof ItemZirnoxRod)
							decay(i);
						else if(slots[i].getItem() == ModItems.meteorite_sword_bred)
							slots[i] = new ItemStack(ModItems.meteorite_sword_irradiated);
					}
				}
			}
			
			//2(fill) + (x * fill%)
			this.pressure = (this.carbonDioxide.getFill() * 2) + (int)((float)this.heat * ((float)this.carbonDioxide.getFill() / (float)this.carbonDioxide.getMaxFill()));

			if(this.heat > 0 && this.heat < maxHeat) {
				if(this.water.getFill() > 0 && this.carbonDioxide.getFill() > 0 && this.steam.getFill() < this.steam.getMaxFill()) {
					generateSteam();
					//(x * pressure) / 1,000,000
					this.heat -= (int) ((float)this.heat * (float)this.pressure / 1000000F);
				} else {
					this.heat -= 10;
				}
				
			}
			
			for(DirPos pos : getConPos()) {
				this.sendFluid(steam, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			checkIfMeltdown();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", heat);
			data.setInteger("pressure", pressure);
			data.setBoolean("isOn", isOn);
			steam.writeToNBT(data, "t0");
			carbonDioxide.writeToNBT(data, "t1");
			water.writeToNBT(data, "t2");
			this.networkPack(data, 150);
		}
	}

	private void generateSteam() {
		
		// function of SHS produced per tick
		// (heat - 10256)/100000 * steamFill (max efficiency at 14b) * 25 * 5 (should get rid of any rounding errors)
		if(this.heat > 10256) {
			int cycle = (int)((((float)heat - 10256F) / (float)maxHeat) * Math.min(((float)carbonDioxide.getFill() / 14000F), 1F) * 25F * 5F);
			this.output = cycle;
			
			water.setFill(water.getFill() - cycle);
			steam.setFill(steam.getFill() + cycle);
			
			if(water.getFill() < 0)
				water.setFill(0);

			if(steam.getFill() > steam.getMaxFill())
				steam.setFill(steam.getMaxFill());
		}
	}

	private boolean hasFuelRod(int id) {
		if(slots[id] != null) {
			if(slots[id].getItem() instanceof ItemZirnoxRod) {
				final EnumZirnoxType num = EnumUtil.grabEnumSafely(EnumZirnoxType.class, slots[id].getItemDamage());
				return !num.breeding;
			}
		}

		return false;
	}

	private int getNeighbourCount(int id) {

		int[] neighbours = this.getNeighbouringSlots(id);

		if(neighbours == null)
			return 0;

		int count = 0;

		for(int i = 0; i < neighbours.length; i++)
			if(hasFuelRod(neighbours[i]))
				count++;

		return count;

	}

	// itemstack in slots[id] has to contain ItemZirnoxRod
	private void decay(int id) {
		int decay = getNeighbourCount(id);
		final EnumZirnoxType num = EnumUtil.grabEnumSafely(EnumZirnoxType.class, slots[id].getItemDamage());

		if(!num.breeding)
			decay++;

		for(int i = 0; i < decay; i++) {
			this.heat += num.heat;
			ItemZirnoxRod.incrementLifeTime(slots[id]);
			
			if(ItemZirnoxRod.getLifeTime(slots[id]) > num.maxLife) {
				slots[id] = fuelMap.get(new ComparableStack(getStackInSlot(id))).copy();
				break;
			}
		}
	}

	private void checkIfMeltdown() {
		if (this.pressure > maxPressure || this.heat > maxHeat) {
			meltdown();
		}
	}

	private void spawnDebris(DebrisType type) {

		EntityZirnoxDebris debris = new EntityZirnoxDebris(worldObj, xCoord + 0.5D, yCoord + 4D, zCoord + 0.5D, type);
		debris.motionX = worldObj.rand.nextGaussian() * 0.75D;
		debris.motionZ = worldObj.rand.nextGaussian() * 0.75D;
		debris.motionY = 0.01D + worldObj.rand.nextDouble() * 1.25D;

		if(type == DebrisType.CONCRETE) {
			debris.motionX *= 0.25D;
			debris.motionY += worldObj.rand.nextDouble();
			debris.motionZ *= 0.25D;
		}

		if(type == DebrisType.EXCHANGER) {
			debris.motionX += 0.5D;
			debris.motionY *= 0.1D;
			debris.motionZ += 0.5D;
		}

		worldObj.spawnEntityInWorld(debris);
	}

	private void zirnoxDebris() {
		
		for(int i = 0; i < 2; i++) {
			spawnDebris(DebrisType.EXCHANGER);
		}
		
		for(int i = 0; i < 20; i++) {
			spawnDebris(DebrisType.CONCRETE);
			spawnDebris(DebrisType.BLANK);
		}
		
		for(int i = 0; i < 10; i++) {
			spawnDebris(DebrisType.ELEMENT);
			spawnDebris(DebrisType.GRAPHITE);
			spawnDebris(DebrisType.SHRAPNEL);
		}

	}

	private void meltdown() {

		for(int i = 0; i < slots.length; i++) {
			this.slots[i] = null;
		}

		int[] dimensions = {1, 0, 2, 2, 2, 2,};
		worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.zirnox_destroyed, this.getBlockMetadata(), 3);
		MultiblockHandlerXR.fillSpace(worldObj, this.xCoord, this.yCoord, this.zCoord, dimensions, ModBlocks.zirnox_destroyed, ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset));
		worldObj.playSoundEffect(xCoord, yCoord + 2, zCoord, "hbm:block.rbmk_explosion", 10.0F, 1.0F);
		worldObj.createExplosion(null, this.xCoord, this.yCoord + 3, this.zCoord, 12.0F, true);
		zirnoxDebris();
		ExplosionNukeGeneric.waste(worldObj, this.xCoord, this.yCoord, this.zCoord, 35);
		
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class,
				AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(100, 100, 100));
		
		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.achZIRNOXBoom);
		}
		
		if(MobConfig.enableElementals) {
			for(EntityPlayer player : players) {
				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean("radMark", true);
			}
		}
	}

	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(water.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(carbonDioxide.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + rot.offsetX * 3, this.yCoord + 1, this.zCoord + rot.offsetZ * 3, rot),
				new DirPos(this.xCoord + rot.offsetX * 3, this.yCoord + 3, this.zCoord + rot.offsetZ * 3, rot),
				new DirPos(this.xCoord + rot.offsetX * -3, this.yCoord + 1, this.zCoord + rot.offsetZ * -3, rot.getOpposite()),
				new DirPos(this.xCoord + rot.offsetX * -3, this.yCoord + 3, this.zCoord + rot.offsetZ * -3, rot.getOpposite())
		};
	}

	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList<FluidTank>();
		list.add(steam);
		list.add(carbonDioxide);
		list.add(water);

		return list;
	}

	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 5, zCoord + 3);
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("control")) {
			this.isOn = !this.isOn;
		}
		
		if(data.hasKey("vent")) {
			int fill = this.carbonDioxide.getFill();
			this.carbonDioxide.setFill(fill - 1000);
			if(this.carbonDioxide.getFill() < 0)
				this.carbonDioxide.setFill(0);
		}
		
		this.markDirty();
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { steam };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { water, carbonDioxide };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { water, steam, carbonDioxide };
	}
  
	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "zirnox_reactor";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTemp(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPressure(Context context, Arguments args) {
		return new Object[] {pressure};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWater(Context context, Arguments args) {
		return new Object[] {water.getFill()};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteam(Context context, Arguments args) {
		return new Object[] {steam.getFill()};
	}	

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCarbonDioxide(Context context, Arguments args) {
		return new Object[] {carbonDioxide.getFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isActive(Context context, Arguments args) {
		return new Object[] {isOn};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {heat, pressure, water.getFill(), steam.getFill(), carbonDioxide.getFill(), isOn};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setActive(Context context, Arguments args) {
		isOn = args.checkBoolean(0);
		return new Object[] {};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
				"getTemp",
				"getPressure",
				"getWater",
				"getSteam",
				"getCarbonDioxide",
				"isActive",
				"getInfo",
				"setActive"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch(method) {
			case ("getTemp"):
				return getTemp(context, args);
			case ("getPressure"):
				return getPressure(context, args);
			case ("getWater"):
				return getWater(context, args);
			case ("getSteam"):
				return getSteam(context, args);
			case ("getCarbonDioxide"):
				return getCarbonDioxide(context, args);
			case ("isActive"):
				return isActive(context, args);
			case ("getInfo"):
				return getInfo(context, args);
			case ("setActive"):
				return setActive(context, args);
		}
		throw new NoSuchMethodException();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerReactorZirnox(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIReactorZirnox(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_HEAT_C, Math.round(heat * 1.0E-5D * 780.0D + 20.0D));
		data.setDouble(CompatEnergyControl.D_MAXHEAT_C, Math.round(maxHeat * 1.0E-5D * 780.0D + 20.0D));
		data.setLong(CompatEnergyControl.L_PRESSURE_BAR, Math.round(pressure * 1.0E-5D * 30.0D));
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, output);
		data.setDouble(CompatEnergyControl.D_OUTPUT_MB, output);
	}
}
