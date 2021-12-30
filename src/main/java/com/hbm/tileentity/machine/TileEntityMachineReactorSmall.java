package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineReactor;
import com.hbm.blocks.machine.MachineReactorSmall;
import com.hbm.config.MobConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPlateFuel;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

//TODO: Fix all unneeded methods; fix reactor control; Add seven digit displays for total flux + heat; revamp gui; revamp breeder to rely on reactor and use total flux calcs;
public class TileEntityMachineReactorSmall extends TileEntityMachineBase implements ISidedInventory {

	public int heat;
	public final int maxHeat = 50000;
	public int rods;
	public static final int rodsMax = 50;
	public boolean retracting = true;
	public int[] slotFlux = new int[12];
	int totalFlux = 0;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15 };
	private static final int[] slots_side = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14 };

	public TileEntityMachineReactorSmall() {
		super(12);
	}
	
	private static final HashMap<ComparableStack, ItemStack> fuelMap = new HashMap<ComparableStack, ItemStack>();
	static {
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_u233), new ItemStack(ModItems.waste_plate_u233, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_u235), new ItemStack(ModItems.waste_plate_u235, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_mox), new ItemStack(ModItems.waste_plate_mox, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_pu239), new ItemStack(ModItems.waste_plate_pu239, 1, 1));
		fuelMap.put(new ComparableStack(ModItems.plate_fuel_sa326), new ItemStack(ModItems.waste_plate_sa326, 1, 1));
	}
	
	public String getName() {
		return "container.reactorSmall";
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
		rods = nbt.getInteger("rods");
		retracting = nbt.getBoolean("ret");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", heat);
		nbt.setInteger("rods", rods);
		nbt.setBoolean("ret", retracting);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i < 12 && i >= 0)
			if(fuelMap.containsValue(stack))
				return true;
		
		return false;

	}

	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
	}
	
	public boolean hasHeat() {
		return heat > 0;
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			if(retracting && rods > 0) {

				if(rods == rodsMax)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 1.0F, 0.75F);
				
				rods --;
				if(rods == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 1.0F, 1.0F);
			}
			if(!retracting && rods < rodsMax) {
				
				if(rods == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 1.0F, 0.75F);
				
				rods ++;
				
				if(rods == rodsMax)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 1.0F, 1.0F);
			}
			
			if(rods > rodsMax)
				rods = rodsMax;
			if(rods < 0)
				rods = 0;
			
			if(rods > 0)
				reaction();
				for(byte i = 0; i < slotFlux.length; i++) {
					totalFlux += slotFlux[i];
				}
			
			getInteractions();
			
			if(this.heat > 0) {
				byte water = getWater();
				
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

			if(rods > 0 && heat > 0 && !(blocksRad(xCoord + 1, yCoord + 1, zCoord) && blocksRad(xCoord - 1, yCoord + 1, zCoord) && blocksRad(xCoord, yCoord + 1, zCoord + 1) && blocksRad(xCoord, yCoord + 1, zCoord - 1))) {
				float rad = (float) heat / (float) maxHeat * 50F;
				ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, rad);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", heat);
			data.setInteger("rods", rods);
			data.setBoolean("ret", retracting);
			data.setIntArray("slotFlux", slotFlux);
			this.networkPack(data, 150);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.heat = data.getInteger("heat");
		this.rods = data.getInteger("rods");
		this.retracting = data.getBoolean("ret");
		this.slotFlux = data.getIntArray("slotFlux");
	}
	
	private byte getWater() {
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
	
	private void getInteractions() {
		getInteractionForBlock(xCoord + 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord - 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord + 1);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord - 1);
	}

	private void getInteractionForBlock(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if(b == ModBlocks.machine_reactor) {

			int[] pos = ((MachineReactor) ModBlocks.machine_reactor).findCore(worldObj, x, y, z);

			if(pos != null) {

				TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);

				if(tile instanceof TileEntityMachineReactor) {

					TileEntityMachineReactor reactor = (TileEntityMachineReactor) tile;

					if(reactor.charge <= 1 && this.heat > 0) {
						reactor.charge = 1;
						reactor.heat = (int) Math.floor(heat * 4 / maxHeat) + 1;
					}
				}
			}
		}
	}

	private boolean blocksRad(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);

		if(b == ModBlocks.block_lead || b == ModBlocks.block_desh || b == ModBlocks.machine_reactor_small || b == ModBlocks.machine_reactor)
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
			if(slots[i] == null) 
				continue;
			
			if(slots[i].getItem() instanceof ItemPlateFuel) {
				ItemPlateFuel rod = (ItemPlateFuel) slots[i].getItem();
				
				int outFlux = rod.react(worldObj, slots[i], slotFlux[i] + 10);
				rod.setLifeTime(slots[i], rod.getLifeTime(slots[i]) + outFlux);
				this.heat += outFlux * 2;
				slotFlux[i] = 0;
				
				int[] neighborSlots = getNeighboringSlots(i);
				
				if(ItemPlateFuel.getLifeTime(slots[i]) > rod.lifeTime) {
					slots[i] = fuelMap.get(new ComparableStack(slots[i])).copy();
				}
				
				for(byte j = 0; j < neighborSlots.length; j++) {
					slotFlux[neighborSlots[j]] += outFlux * (rods / rodsMax);
				}
				continue;
			}
			
			if(slots[i].getItem() == ModItems.meteorite_sword_bred)
				slots[i] = new ItemStack(ModItems.meteorite_sword_irradiated);
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


	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}