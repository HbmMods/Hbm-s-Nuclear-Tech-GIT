package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerReactorResearch;
import com.hbm.inventory.gui.GUIReactorResearch;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPlateFuel;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;

import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
//TODO: fix reactor control;
public class TileEntityReactorResearch extends TileEntityMachineBase implements IControlReceiver, SimpleComponent, IGUIProvider, IInfoProviderEC, CompatHandler.OCComponent {
	
	@SideOnly(Side.CLIENT)
	public double lastLevel;
	public double level;
	public double speed = 0.04;
	public double targetLevel;
	
	public int heat;
	public byte water;
	public final int maxHeat = 50000;
	public int[] slotFlux = new int[12];
	public int totalFlux = 0;
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	public TileEntityReactorResearch() {
		super(12);
	}
	
	private static final HashMap<ComparableStack, ItemStack> fuelMap = new HashMap<ComparableStack, ItemStack>();
	static {
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_u233), new ItemStack(ModItems.waste_plate_u233, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_u235), new ItemStack(ModItems.waste_plate_u235, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_mox), new ItemStack(ModItems.waste_plate_mox, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_pu239), new ItemStack(ModItems.waste_plate_pu239, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_sa326), new ItemStack(ModItems.waste_plate_sa326, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_ra226be), new ItemStack(ModItems.waste_plate_ra226be, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_pu238be), new ItemStack(ModItems.waste_plate_pu238be, 1, 1));
	}
	
	public String getName() {
		return "container.reactorResearch";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i < 12 && i <= 0)
			if(itemStack.getItem().getClass() == ItemPlateFuel.class)
				return true;
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		heat = nbt.getInteger("heat");
		water = nbt.getByte("water");
		level = nbt.getDouble("level");
		targetLevel = nbt.getDouble("targetLevel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", heat);
		nbt.setByte("water", water);
		nbt.setDouble("level", level);
		nbt.setDouble("targetLevel", targetLevel);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_io;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i < 12 && i >= 0)
			if(fuelMap.containsValue(stack))
				return true;
		
		return false;

	}
	
	@Override
	public void updateEntity() {
		
		rodControl();

		if(!worldObj.isRemote) {
			totalFlux = 0;
			
			if(level > 0) {
				reaction();
			}
						
			if(this.heat > 0) {
				water = getWater();
				
				if(water > 0) {
					this.heat -= (this.heat * (float) 0.07 * water / 12);
				} else if(water == 0) {
					this.heat -= 1;
				}
			
				if(this.heat < 0)
					this.heat = 0;
			}

			if(this.heat > maxHeat) {
				this.explode();
			}

			if(level > 0 && heat > 0 && !(blocksRad(xCoord + 1, yCoord + 1, zCoord) && blocksRad(xCoord - 1, yCoord + 1, zCoord) && blocksRad(xCoord, yCoord + 1, zCoord + 1) && blocksRad(xCoord, yCoord + 1, zCoord - 1))) {
				float rad = (float) heat / (float) maxHeat * 50F;
				ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, rad);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", heat);
			data.setByte("water", water);
			data.setDouble("level", level);
			data.setDouble("targetLevel", targetLevel);
			data.setIntArray("slotFlux", slotFlux);
			data.setInteger("totalFlux", totalFlux);
			this.networkPack(data, 150);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		
		this.heat = data.getInteger("heat");
		this.water = data.getByte("water");
		this.level = data.getDouble("level");
		this.targetLevel = data.getDouble("targetLevel");
		this.slotFlux = data.getIntArray("slotFlux");
		this.totalFlux = data.getInteger("totalFlux");
	}
	
	public byte getWater() {
		byte water = 0;
		
		for(byte d = 0; d < 6; d++) {
			ForgeDirection dir = ForgeDirection.getOrientation(d);
			if(d < 2) {
				if(worldObj.getBlock(xCoord, yCoord + 1 + dir.offsetY * 2, zCoord).getMaterial() == Material.water)
					water++;
			} else {
				for(byte i = 0; i < 3; i++) {
					if(worldObj.getBlock(xCoord + dir.offsetX, yCoord + i, zCoord + dir.offsetZ).getMaterial() == Material.water)
						water++;
				}
			}
		}
		
		return water;
	}
	
	public boolean isSubmerged() {
		
		return worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).getMaterial() == Material.water ||
				worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).getMaterial() == Material.water ||
				worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).getMaterial() == Material.water ||
				worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).getMaterial() == Material.water;
	}
	
	/*private void getInteractions() {
		getInteractionForBlock(xCoord + 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord - 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord + 1);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord - 1);
	}

	private void getInteractionForBlock(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);
		TileEntity te = worldObj.getTileEntity(x, y, z);
	}*/

	private boolean blocksRad(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);

		if((b == Blocks.water || b == Blocks.flowing_water) && worldObj.getBlockMetadata(x, y, z) == 0)
			return true;

		if(b == ModBlocks.block_lead || b == ModBlocks.block_desh || b == ModBlocks.reactor_research || b == ModBlocks.machine_reactor_breeding)
			return true;

		if(b.getExplosionResistance(null) >= 100)
			return true;

		return false;
	}
	
	private int[] getNeighboringSlots(int id) {

		switch(id) {
		case 0:
			return new int[] { 1, 5 };
		case 1:
			return new int[] { 0, 6 };
		case 2:
			return new int[] { 3, 7 };
		case 3:
			return new int[] { 2, 4, 8 };
		case 4:
			return new int[] { 3, 9 };
		case 5:
			return new int[] { 0, 6, 0xA };
		case 6:
			return new int[] { 1, 5, 0xB };
		case 7:
			return new int[] { 2, 8 };
		case 8:
			return new int[] { 3, 7, 9 };
		case 9:
			return new int[] { 4, 8 };
		case 10:
			return new int[] { 5, 0xB };
		case 11:
			return new int[] { 6, 0xA };
		}

		return null;
	}
	
	private void reaction() {
		for(byte i = 0; i < 12; i++) {
			if(slots[i] == null)  {
				slotFlux[i] = 0;
				continue;
			}
			
			if(slots[i].getItem() instanceof ItemPlateFuel) {
				ItemPlateFuel rod = (ItemPlateFuel) slots[i].getItem();
				
				int outFlux = rod.react(worldObj, slots[i], slotFlux[i]);
				this.heat += outFlux * 2;
				slotFlux[i] = 0;
				totalFlux += outFlux;
				
				int[] neighborSlots = getNeighboringSlots(i);
				
				if(ItemPlateFuel.getLifeTime(slots[i]) > rod.lifeTime) {
					slots[i] = fuelMap.get(new ComparableStack(slots[i])).copy();
				}
				
				for(byte j = 0; j < neighborSlots.length; j++) {
					slotFlux[neighborSlots[j]] += (int) (outFlux * level);
				}
				continue;
			}
			
			if(slots[i].getItem() == ModItems.meteorite_sword_bred)
				slots[i] = new ItemStack(ModItems.meteorite_sword_irradiated);
			
			slotFlux[i] = 0;
		}
	}

	private void explode() {
		
		for(int i = 0; i < slots.length; i++) {
			this.slots[i] = null;
		}
		
		worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		
		for(byte d = 0; d < 6; d++) {
			ForgeDirection dir = ForgeDirection.getOrientation(d);
			if(d < 2) {
				if(worldObj.getBlock(xCoord, yCoord + 1 + dir.offsetY * 2, zCoord).getMaterial() == Material.water)
					worldObj.setBlockToAir(xCoord, yCoord + 1 + dir.offsetY * 2, zCoord);
			} else {
				for(byte i = 0; i < 3; i++) {
					if(worldObj.getBlock(xCoord + dir.offsetX, yCoord + i, zCoord + dir.offsetZ).getMaterial() == Material.water)
						worldObj.setBlockToAir(xCoord + dir.offsetX, yCoord + i, zCoord + dir.offsetZ);
				}
			}
		}
		
		worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 18.0F, true);
		worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.deco_steel);
		worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, ModBlocks.corium_block);
		worldObj.setBlock(this.xCoord, this.yCoord + 2, this.zCoord, ModBlocks.deco_steel);

		ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, 50);
		
		if(MobConfig.enableElementals) {
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(100, 100, 100));
			
			for(EntityPlayer player : players) {
				player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG).setBoolean("radMark", true);
			}
		}
	}
	
	//Control Rods
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("level")) {
			this.setTarget(data.getDouble("level"));
		}
		
		this.markDirty();
	}
	
	public void setTarget(double target) {
		this.targetLevel = target;
	}
	
	public void rodControl() {
		if(worldObj.isRemote) {
			
			this.lastLevel = this.level;
		
		} else {
			
			if(level < targetLevel) {
				
				level += speed;
				
				if(level >= targetLevel)
					level = targetLevel;
			}
			
			if(level > targetLevel) {
				
				level -= speed;
				
				if(level <= targetLevel)
					level = targetLevel;
			}
		}
	}
	
	public int[] getDisplayData() {
		int[] data = new int[2];
		data[0] = this.totalFlux;
		data[1] = (int) Math.round((this.heat) * 0.00002 * 980 + 20);
		return data;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "research_reactor";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTemp(Context context, Arguments args) { // or getHeat, whatever.
		return new Object[] {heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getLevel(Context context, Arguments args) {
		return new Object[] {level * 100};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTargetLevel(Context context, Arguments args) {
		return new Object[] {targetLevel};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFlux(Context context, Arguments args) {
		return new Object[] {totalFlux};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {heat, level, targetLevel, totalFlux};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
				"getTemp",
				"getLevel",
				"getTargetLevel",
				"getFlux",
				"getInfo"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch(method) {
			case ("getTemp"):
				return getTemp(context, args);
			case ("getLevel"):
				return getLevel(context, args);
			case ("getTargetLevel"):
				return getTargetLevel(context, args);
			case ("getFlux"):
				return getFlux(context, args);
			case ("getInfo"):
				return getInfo(context, args);
		}
		throw new NoSuchMethodException();
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setLevel(Context context, Arguments args) {
		double newLevel = args.checkDouble(0)/100.0;
		targetLevel = MathHelper.clamp_double(newLevel, 0, 1.0);
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerReactorResearch(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIReactorResearch(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_HEAT_C, Math.round(heat * 2.0E-5D * 980.0D + 20.0D));
		data.setInteger(CompatEnergyControl.I_FLUX, totalFlux);
		data.setInteger(CompatEnergyControl.I_WATER, water);
	}
}
