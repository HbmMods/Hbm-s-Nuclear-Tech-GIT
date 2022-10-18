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
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemZirnoxBreedingRod;
import com.hbm.items.machine.ItemZirnoxRod;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityReactorZirnox extends TileEntityMachineBase implements IFluidContainer, IFluidAcceptor, IFluidSource, IControlReceiver, IFluidStandardTransceiver, SimpleComponent {

	public int heat;
	public static final int maxHeat = 100000;
	public int pressure;
	public static final int maxPressure = 100000;
	public boolean isOn = false;

	public List<IFluidAcceptor> list = new ArrayList<IFluidAcceptor>();
	public byte age;

	public FluidTank steam;
	public FluidTank carbonDioxide;
	public FluidTank water;
	
	private static final int[] slots_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };

	public static final HashMap<ComparableStack, ItemStack> fuelMap = new HashMap<ComparableStack, ItemStack>();
	static {
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_natural_uranium_fuel), new ItemStack(ModItems.rod_zirnox_natural_uranium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_uranium_fuel), new ItemStack(ModItems.rod_zirnox_uranium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_th232), new ItemStack(ModItems.rod_zirnox_thorium_fuel));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_thorium_fuel), new ItemStack(ModItems.rod_zirnox_thorium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_mox_fuel), new ItemStack(ModItems.rod_zirnox_mox_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_plutonium_fuel), new ItemStack(ModItems.rod_zirnox_plutonium_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_u233_fuel), new ItemStack(ModItems.rod_zirnox_u233_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_u235_fuel), new ItemStack(ModItems.rod_zirnox_u235_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_les_fuel), new ItemStack(ModItems.rod_zirnox_les_fuel_depleted));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_lithium), new ItemStack(ModItems.rod_zirnox_tritium));
		fuelMap.put(new ComparableStack(ModItems.rod_zirnox_zfb_mox), new ItemStack(ModItems.rod_zirnox_zfb_mox_depleted));
	}

	public TileEntityReactorZirnox() {
		super(28);
		steam = new FluidTank(Fluids.SUPERHOTSTEAM, 8000, 0);
		carbonDioxide = new FluidTank(Fluids.CARBONDIOXIDE, 16000, 1);
		water = new FluidTank(Fluids.WATER, 32000, 2);
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
		this.heat = data.getInteger("heat");
		this.pressure = data.getInteger("pressure");
		this.isOn = data.getBoolean("isOn");
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

			age++;

			if (age >= 20) {
				age = 0;
			}

			if(age == 9 || age == 19) {
				fillFluidInit(steam.getTankType());
			}
			
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
				this.sendFluid(steam.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			checkIfMeltdown();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", heat);
			data.setInteger("pressure", pressure);
			data.setBoolean("isOn", isOn);
			this.networkPack(data, 150);
			
			steam.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			carbonDioxide.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			water.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
		}
	}

	private void generateSteam() {
		
		// function of SHS produced per tick
		// (heat - 10256)/100000 * steamFill (max efficiency at 14b) * 25 * 5 (should get rid of any rounding errors)
		if(this.heat > 10256) {
			int Water = (int)((((float)heat - 10256F) / (float)maxHeat) * Math.min(((float)carbonDioxide.getFill() / 14000F), 1F) * 25F * 5F);
			int Steam = Water * 1;
			
			water.setFill(water.getFill() - Water);
			steam.setFill(steam.getFill() + Steam);
			
			if(water.getFill() < 0)
				water.setFill(0);

			if(steam.getFill() > steam.getMaxFill())
				steam.setFill(steam.getMaxFill());
		}
	}

	private boolean hasFuelRod(int id) {
		if(slots[id] != null) {
			if(!(slots[id].getItem() instanceof ItemZirnoxBreedingRod)) {
				return slots[id].getItem() instanceof ItemZirnoxRod;
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

		if(!(slots[id].getItem() instanceof ItemZirnoxBreedingRod)) {
			decay++;
		}

		for(int i = 0; i < decay; i++) {
			ItemZirnoxRod rod = ((ItemZirnoxRod) slots[id].getItem());
			this.heat += rod.heat;
			ItemZirnoxRod.setLifeTime(slots[id], ItemZirnoxRod.getLifeTime(slots[id]) + 1);
			
			if(ItemZirnoxRod.getLifeTime(slots[id]) > rod.lifeTime) {
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

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		fillFluid(this.xCoord + rot.offsetX * 3, this.yCoord + 1, this.zCoord + rot.offsetZ * 3, getTact(), type);
		fillFluid(this.xCoord + rot.offsetX * 3, this.yCoord + 3, this.zCoord + rot.offsetZ * 3, getTact(), type);

		fillFluid(this.xCoord + rot.offsetX * -3, this.yCoord + 1, this.zCoord + rot.offsetZ * -3, getTact(), type);
		fillFluid(this.xCoord + rot.offsetX * -3, this.yCoord + 3, this.zCoord + rot.offsetZ * -3, getTact(), type);
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

	public boolean getTact() {
		if(age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	public int getMaxFluidFill(FluidType type) {
		if(type == Fluids.SUPERHOTSTEAM) return 0;
		if(type == Fluids.CARBONDIOXIDE) return carbonDioxide.getMaxFill();
		if(type == Fluids.WATER) return water.getMaxFill();
		
		return 0;
	}

	public void setFluidFill(int i, FluidType type) {
		if(type == Fluids.SUPERHOTSTEAM) steam.setFill(i);
		if(type == Fluids.CARBONDIOXIDE) carbonDioxide.setFill(i);
		if(type == Fluids.WATER) water.setFill(i);
	}

	public int getFluidFill(FluidType type) {
		if(type == Fluids.SUPERHOTSTEAM) return steam.getFill();
		if(type == Fluids.CARBONDIOXIDE) return carbonDioxide.getFill();
		if(type == Fluids.WATER) return water.getFill();
		return 0;
	}

	public void setFillForSync(int fill, int index) {
		switch (index) {
		case 0: steam.setFill(fill);
			break;
		case 1: carbonDioxide.setFill(fill);
			break;
		case 2: water.setFill(fill);
			break;
		default: break;
		}
	}

	public void setTypeForSync(FluidType type, int index) {
		switch (index) {
		case 0: steam.setTankType(type);
			break;
		case 1: carbonDioxide.setTankType(type);
			break;
		case 2: water.setTankType(type);
			break;
		default: break;
		}
	}

	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList<FluidTank>();
		list.add(steam);
		list.add(carbonDioxide);
		list.add(water);

		return list;
	}

	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	public void clearFluidList(FluidType type) {
		list.clear();
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
	public String getComponentName() {
		return "zirnox_reactor";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTemp(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPressure(Context context, Arguments args) {
		return new Object[] {pressure};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWater(Context context, Arguments args) {
		return new Object[] {water.getFill()};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteam(Context context, Arguments args) {
		return new Object[] {steam.getFill()};
	}	

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCarbonDioxide(Context context, Arguments args) {
		return new Object[] {carbonDioxide.getFill()};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] isActive(Context context, Arguments args) {
		return new Object[] {isOn};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setActive(Context context, Arguments args) {
		isOn = Boolean.parseBoolean(args.checkString(0));
		return new Object[] {};
	}
}
