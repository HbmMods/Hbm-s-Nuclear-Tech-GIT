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
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemZirnoxBreedingRod;
import com.hbm.items.machine.ItemZirnoxRod;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityReactorZirnox extends TileEntityMachineBase implements IFluidContainer, IFluidAcceptor, IFluidSource {

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

	private static final HashMap<ComparableStack, ItemStack> fuelMap = new HashMap<ComparableStack, ItemStack>();
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
	}

	public TileEntityReactorZirnox() {
		super(28);
		steam = new FluidTank(FluidType.SUPERHOTSTEAM, 8000, 0);
		carbonDioxide = new FluidTank(FluidType.CARBONDIOXIDE, 16000, 1);
		water = new FluidTank(FluidType.WATER, 32000, 2);
	}

	@Override
	public String getName() {
		return "container.zirnox";
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
		case 0:
			return new int[] { 1, 7 };
		case 1:
			return new int[] { 0, 2, 8 };
		case 2:
			return new int[] { 1, 9 };
		case 3:
			return new int[] { 4, 10 };
		case 4:
			return new int[] { 3, 5, 11 };
		case 5:
			return new int[] { 4, 6, 12 };
		case 6:
			return new int[] { 5, 13 };
		case 7:
			return new int[] { 0, 8, 14 };
		case 8:
			return new int[] { 1, 7, 9, 15 };
		case 9:
			return new int[] { 2, 8, 16};
		case 10:
			return new int[] { 3, 11, 17 };
		case 11:
			return new int[] { 4, 10, 12, 18 };
		case 12:
			return new int[] { 5, 11, 13, 19 };
		case 13:
			return new int[] { 6, 12, 20 };
		case 14:
			return new int[] { 7, 15, 21 }; 
		case 15:
			return new int[] { 8, 14, 16, 22 };
		case 16:
			return new int[] { 9, 15, 23 };
		case 17:
			return new int[] { 10, 18 };
		case 18:
			return new int[] { 11, 17, 19 };
		case 19:
			return new int[] { 12, 18, 20 };
		case 20:
			return new int[] { 13, 19 };
		case 21:
			return new int[] { 14, 22 };
		case 22:
			return new int[] { 15, 21, 23 };
		case 23:
			return new int[] { 16, 22 };
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

			this.pressure = (int) ((float)this.heat * (1.5 * this.carbonDioxide.getFill() / 16000));

			if(this.heat > 0 && this.heat < maxHeat) {
				if(this.water.getFill() > 0 && this.carbonDioxide.getFill() > 0) {
					generateSteam();
					this.heat -= (int) ((float)this.heat * (Math.sqrt(this.carbonDioxide.getFill()) / 1800));
				} else {
					this.heat -= 10;
				}
				
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
		// heat% * 25 * 1 (should get rid of any rounding errors)
		int Water = (int) (((float)heat / maxHeat) * 25);
		int Steam = Water * 1;

		water.setFill(water.getFill() - Water);
		steam.setFill(steam.getFill() + Steam);

		if(water.getFill() < 0)
			water.setFill(0);

		if(steam.getFill() > steam.getMaxFill())
			steam.setFill(steam.getMaxFill());

	}

	private boolean hasFuelRod(int id) {
		if(id > 23)
			return false;

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
		if(id > 23)
			return;

		int decay = getNeighbourCount(id);

		if(!(slots[id].getItem() instanceof ItemZirnoxBreedingRod)) {
		decay = getNeighbourCount(id) + 1;
		}

		for(int i = 0; i < decay; i++) {
			ItemZirnoxRod rod = ((ItemZirnoxRod) slots[id].getItem());
			this.heat += rod.heat;
			ItemZirnoxRod.setLifeTime(slots[id], ItemZirnoxRod.getLifeTime(slots[id]) + 1);
			
			if(ItemZirnoxRod.getLifeTime(slots[id]) > ((ItemZirnoxRod) slots[id].getItem()).lifeTime) {
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

		if(MobConfig.enableElementals) {
			@SuppressWarnings("unchecked")
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(100, 100, 100));

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

	public boolean getTact() {
		if(age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	public int getMaxFluidFill(FluidType type) {
		switch (type) {
		case SUPERHOTSTEAM: return steam.getMaxFill();
		case CARBONDIOXIDE: return carbonDioxide.getMaxFill();
		case WATER: return water.getMaxFill();
		default: return 0;
		}
	}

	public void setFluidFill(int i, FluidType type) {
		switch (type) {
		case SUPERHOTSTEAM: steam.setFill(i);
			break;
		case CARBONDIOXIDE: carbonDioxide.setFill(i);
			break;
		case WATER: water.setFill(i);
			break;
		default: break;
		}
	}

	public int getFluidFill(FluidType type) {
		switch (type) {
		case SUPERHOTSTEAM: return steam.getFill();
		case CARBONDIOXIDE: return carbonDioxide.getFill();
		case WATER: return water.getFill();
		default: return 0;
		}
	}

	public void setFillstate(int fill, int index) {
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

	public void setType(FluidType type, int index) {
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

}