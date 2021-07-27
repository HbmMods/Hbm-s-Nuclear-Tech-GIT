package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteResonator;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAMSBase extends TileEntity implements ISidedInventory, ISource, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];

	public long power = 0;
	public static final long maxPower = 1000000000000000L;
	public int field = 0;
	public static final int maxField = 100;
	public int efficiency = 0;
	public static final int maxEfficiency = 100;
	public int heat = 0;
	public static final int maxHeat = 5000;
	public int age = 0;
	public int warning = 0;
	public int mode = 0;
	public boolean locked = false;
	public FluidTank[] tanks;
	public List<IConsumer> list = new ArrayList();
	public int color = -1;
	
	Random rand = new Random();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0 };
	private static final int[] slots_side = new int[] { 0 };
	
	private String customName;
	
	public TileEntityAMSBase() {
		slots = new ItemStack[16];
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(FluidType.COOLANT, 8000, 0);
		tanks[1] = new FluidTank(FluidType.CRYOGEL, 8000, 1);
		tanks[2] = new FluidTank(FluidType.DEUTERIUM, 8000, 2);
		tanks[3] = new FluidTank(FluidType.TRITIUM, 8000, 3);
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.amsBase";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=128;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "coolant1");
		tanks[1].readFromNBT(nbt, "coolant2");
		tanks[2].readFromNBT(nbt, "fuel1");
		tanks[3].readFromNBT(nbt, "fuel2");
		field = nbt.getInteger("field");
		efficiency = nbt.getInteger("efficiency");
		heat = nbt.getInteger("heat");
		locked = nbt.getBoolean("locked");
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "coolant1");
		tanks[1].writeToNBT(nbt, "coolant2");
		tanks[2].writeToNBT(nbt, "fuel1");
		tanks[3].writeToNBT(nbt, "fuel2");
		nbt.setInteger("field", field);
		nbt.setInteger("efficiency", efficiency);
		nbt.setInteger("heat", heat);
		nbt.setBoolean("locked", locked);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			for(int i = 0; i < tanks.length; i++)
				tanks[i].setFill(tanks[i].getMaxFill());
			
			if(!locked) {
				
				age++;
				if(age >= 20)
				{
					age = 0;
				}
				
				if(age == 9 || age == 19)
					ffgeuaInit();

				tanks[0].setType(0, 1, slots);
				tanks[1].setType(2, 3, slots);
				tanks[2].setType(4, 5, slots);
				tanks[3].setType(6, 7, slots);
				
				for(int i = 0; i < 4; i++)
					tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
				
				int f1 = 0, f2 = 0, f3 = 0, f4 = 0;
				int booster = 0;

				if(worldObj.getTileEntity(xCoord + 6, yCoord, zCoord) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)worldObj.getTileEntity(xCoord + 6, yCoord, zCoord);
					if(!te.locked && worldObj.getBlockMetadata(xCoord + 6, yCoord, zCoord) == 4) {
						f1 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(worldObj.getTileEntity(xCoord - 6, yCoord, zCoord) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)worldObj.getTileEntity(xCoord - 6, yCoord, zCoord);
					if(!te.locked && worldObj.getBlockMetadata(xCoord - 6, yCoord, zCoord) == 5) {
						f2 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(worldObj.getTileEntity(xCoord, yCoord, zCoord + 6) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)worldObj.getTileEntity(xCoord, yCoord, zCoord + 6);
					if(!te.locked && worldObj.getBlockMetadata(xCoord, yCoord, zCoord + 6) == 2) {
						f3 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(worldObj.getTileEntity(xCoord, yCoord, zCoord - 6) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)worldObj.getTileEntity(xCoord, yCoord, zCoord - 6);
					if(!te.locked && worldObj.getBlockMetadata(xCoord, yCoord, zCoord - 6) == 3) {
						f4 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				
				this.field = Math.round(calcField(f1, f2, f3, f4));
				
				mode = 0;
				if(field > 0)
					mode = 1;
				if(booster > 0)
					mode = 2;
				
				if(worldObj.getTileEntity(xCoord, yCoord + 9, zCoord) instanceof TileEntityAMSEmitter) {
					TileEntityAMSEmitter te = (TileEntityAMSEmitter)worldObj.getTileEntity(xCoord, yCoord + 9, zCoord);
						this.efficiency = te.efficiency;
				}
				
				this.color = -1;
				
				float powerMod = 1;
				float heatMod = 1;
				float fuelMod = 1;
				long powerBase = 0;
				int heatBase = 0;
				int fuelBase = 0;
				
				if(slots[8] != null && slots[9] != null && slots[10] != null && slots[11] != null && slots[12] != null &&
						slots[8].getItem() instanceof ItemCatalyst && slots[9].getItem() instanceof ItemCatalyst &&
						slots[10].getItem() instanceof ItemCatalyst && slots[11].getItem() instanceof ItemCatalyst &&
						slots[12].getItem() instanceof ItemAMSCore && hasResonators() && efficiency > 0) {
					int a = ((ItemCatalyst)slots[8].getItem()).getColor();
					int b = ((ItemCatalyst)slots[9].getItem()).getColor();
					int c = ((ItemCatalyst)slots[10].getItem()).getColor();
					int d = ((ItemCatalyst)slots[11].getItem()).getColor();

					int e = this.calcAvgHex(a, b);
					int f = this.calcAvgHex(c, d);
					
					int g = this.calcAvgHex(e, f);
					
					this.color = g;

					
					for(int i = 8; i < 12; i++) {
						powerBase += ItemCatalyst.getPowerAbs(slots[i]);
						powerMod *= ItemCatalyst.getPowerMod(slots[i]);
						heatMod *= ItemCatalyst.getHeatMod(slots[i]);
						fuelMod *= ItemCatalyst.getFuelMod(slots[i]);
					}

					powerBase = ItemAMSCore.getPowerBase(slots[12]);
					heatBase = ItemAMSCore.getHeatBase(slots[12]);
					fuelBase = ItemAMSCore.getFuelBase(slots[12]);
					
					powerBase *= this.efficiency;
					powerBase *= Math.pow(1.25F, booster);
					heatBase *= Math.pow(1.25F, booster);
					heatBase *= (100 - field);
					
					if(this.getFuelPower(tanks[2].getTankType()) > 0 && this.getFuelPower(tanks[3].getTankType()) > 0 &&
							tanks[2].getFill() > 0 && tanks[3].getFill() > 0) {

						power += (powerBase * powerMod * gauss(1, (heat - (maxHeat / 2)) / maxHeat)) / 1000 * getFuelPower(tanks[2].getTankType()) * getFuelPower(tanks[3].getTankType());
						heat += (heatBase * heatMod) / (float)(this.field / 100F);
						tanks[2].setFill((int)(tanks[2].getFill() - fuelBase * fuelMod));
						tanks[3].setFill((int)(tanks[3].getFill() - fuelBase * fuelMod));
						if(tanks[2].getFill() <= 0)
							tanks[2].setFill(0);
						if(tanks[3].getFill() <= 0)
							tanks[3].setFill(0);
						
						radiation();
						
						if(heat > maxHeat) {
							explode();
							heat = maxHeat;
						}
						
						if(field <= 0)
							explode();
					}
				}
				
				if(power > maxPower)
					power = maxPower;
				
				
				if(heat > 0 && tanks[0].getFill() > 0 && tanks[1].getFill() > 0) {
					heat -= (this.getCoolingStrength(tanks[0].getTankType()) * this.getCoolingStrength(tanks[1].getTankType()));

					tanks[0].setFill(tanks[0].getFill() - 10);
					tanks[1].setFill(tanks[1].getFill() - 10);

					if(tanks[0].getFill() < 0)
						tanks[0].setFill(0);
					if(tanks[1].getFill() < 0)
						tanks[1].setFill(0);
					
					if(heat < 0)
						heat = 0;
				}
				
			} else {
				field = 0;
				efficiency = 0;
				power = 0;
				warning = 3;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, locked ? 1 : 0, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, color, 1), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, efficiency, 2), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, field, 3), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
		}
	}
	
	private void radiation() {
		
		double maxSize = 5;
		double minSize = 0.5;
		double scale = minSize;
		scale += ((((double)this.tanks[2].getFill()) / ((double)this.tanks[2].getMaxFill())) + (((double)this.tanks[3].getFill()) / ((double)this.tanks[3].getMaxFill()))) * ((maxSize - minSize) / 2);

		scale *= 0.60;
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord - 10 + 0.5, yCoord - 10 + 0.5 + 6, zCoord - 10 + 0.5, xCoord + 10 + 0.5, yCoord + 10 + 0.5 + 6, zCoord + 10 + 0.5));
		
		for(Entity e : list) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHazmat((EntityPlayer)e)))
				if(!Library.isObstructed(worldObj, xCoord + 0.5, yCoord + 0.5 + 6, zCoord + 0.5, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
					e.attackEntityFrom(ModDamageSource.ams, 1000);
					e.setFire(3);
				}
		}

		List<Entity> list2 = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord - scale + 0.5, yCoord - scale + 0.5 + 6, zCoord - scale + 0.5, xCoord + scale + 0.5, yCoord + scale + 0.5 + 6, zCoord + scale + 0.5));
		
		for(Entity e : list2) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHaz2((EntityPlayer)e)))
					e.attackEntityFrom(ModDamageSource.amsCore, 10000);
		}
	}
	
	private void explode() {
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 10; i++) {

	    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(this.worldObj, 100);
	    		cloud.posX = xCoord + rand.nextInt(201) - 100;
	    		cloud.posY = yCoord + rand.nextInt(201) - 100;
	    		cloud.posZ = zCoord + rand.nextInt(201) - 100;
	    		this.worldObj.spawnEntityInWorld(cloud);
			}
			
			int radius = (int)(50 + (double)(tanks[2].getFill() + tanks[3].getFill()) / 16000D * 150);
			
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFacExperimental(worldObj, radius, xCoord, yCoord, zCoord));
			
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	
	private int getCoolingStrength(FluidType type) {
		switch(type) {
		case WATER:
			return 5;
		case OIL:
			return 15;
		case COOLANT:
			return this.heat / 250;
		case CRYOGEL:
			return this.heat > heat/2 ? 25 : 5;
		default:
			return 0;
		}
	}
	
	private int getFuelPower(FluidType type) {
		switch(type) {
		case DEUTERIUM:
			return 50;
		case TRITIUM:
			return 75;
		default:
			return 0;
		}
	}
	
	private float gauss(float a, float x) {
		
		//Greater values -> less difference of temperate impact
		double amplifier = 0.10;
		
		return (float) ( (1/Math.sqrt(a * Math.PI)) * Math.pow(Math.E, -1 * Math.pow(x, 2)/amplifier) );
	}
	
	private float calcEffect(float a, float x) {
		return (float) (gauss( 1 / a, x / maxHeat) * Math.sqrt(Math.PI * 2) / (Math.sqrt(2) * Math.sqrt(maxPower)));
	}
	
	private float calcField(int a, int b, int c, int d) {
		return (float)(a + b + c + d) * (a * 25 + b * 25 + c * 25 + d  * 25) / 40000;
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
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getEfficiencyScaled(int i) {
		return (efficiency * i) / maxEfficiency;
	}
	
	public int getFieldScaled(int i) {
		return (field * i) / maxField;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
	}
	
	public boolean hasResonators() {
		
		if(slots[13] != null && slots[14] != null && slots[15] != null &&
				slots[13].getItem() == ModItems.sat_chip && slots[14].getItem() == ModItems.sat_chip && slots[15].getItem() == ModItems.sat_chip) {
			
		    SatelliteSavedData data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    if(data == null) {
		        worldObj.perWorldStorage.setData("satellites", new SatelliteSavedData());
		        data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    }
		    data.markDirty();

		    int i1 = ItemSatChip.getFreq(slots[13]);
		    int i2 = ItemSatChip.getFreq(slots[14]);
		    int i3 = ItemSatChip.getFreq(slots[15]);
		    
		    if(data.getSatFromFreq(i1) != null && data.getSatFromFreq(i2) != null && data.getSatFromFreq(i3) != null &&
		    		data.getSatFromFreq(i1) instanceof SatelliteResonator && data.getSatFromFreq(i2) instanceof SatelliteResonator && data.getSatFromFreq(i3) instanceof SatelliteResonator &&
		    		i1 != i2 && i1 != i3 && i2 != i3)
		    	return true;
			
		}
		
		return true;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getMaxFill();
		else if(type.name().equals(tanks[3].getTankType().name()))
			return tanks[3].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if(type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
		else if(type.name().equals(tanks[3].getTankType().name()))
			tanks[3].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else if(type.name().equals(tanks[3].getTankType().name()))
			return tanks[3].getFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index < 4 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index < 4 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord - 2, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 2, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 2, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 2, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
	}
	
	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		list.add(tanks[2]);
		list.add(tanks[3]);
		
		return list;
	}
}
