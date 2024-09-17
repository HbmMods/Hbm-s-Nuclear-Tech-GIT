package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.config.VersatileConfig;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.inventory.container.ContainerStorageDrum;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIStorageDrum;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import api.hbm.fluid.IFluidStandardSender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityStorageDrum extends TileEntityMachineBase implements IFluidStandardSender, IBufPacketReceiver, IGUIProvider, IFluidCopiable {


	public FluidTank[] tanks;
	private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };
	public int age = 0;

	public TileEntityStorageDrum() {
		super(24);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.WASTEFLUID, 16000);
		tanks[1] = new FluidTank(Fluids.WASTEGAS, 16000);
	}

	@Override
	public String getName() {
		return "container.storageDrum";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			float rad = 0;
			
			int liquid = 0;
			int gas = 0;
			
			for(int i = 0; i < 24; i++) {
				
				if(slots[i] != null) {
					
					Item item = slots[i].getItem();
					
					if(worldObj.getTotalWorldTime() % 20 == 0) {
						rad += HazardSystem.getHazardLevelFromStack(slots[i], HazardRegistry.RADIATION);
					}
					
					int meta = slots[i].getItemDamage();
					
					if(item == ModItems.nuclear_waste_long && worldObj.rand.nextInt(VersatileConfig.getLongDecayChance()) == 0) {
						ItemWasteLong.WasteClass wasteClass = ItemWasteLong.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid;
						gas += wasteClass.gas;
						slots[i] = new ItemStack(ModItems.nuclear_waste_long_depleted, 1, meta);
					}
					
					if(item == ModItems.nuclear_waste_long_tiny && worldObj.rand.nextInt(VersatileConfig.getLongDecayChance() / 10) == 0) {
						ItemWasteLong.WasteClass wasteClass = ItemWasteLong.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid / 10;
						gas += wasteClass.gas / 10;
						slots[i] = new ItemStack(ModItems.nuclear_waste_long_depleted_tiny, 1, meta);
					}
					
					if(item == ModItems.nuclear_waste_short && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance()) == 0) {
						ItemWasteShort.WasteClass wasteClass = ItemWasteShort.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid;
						gas += wasteClass.gas;
						slots[i] = new ItemStack(ModItems.nuclear_waste_short_depleted, 1, meta);
					}
					
					if(item == ModItems.nuclear_waste_short_tiny && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 10) == 0) {
						ItemWasteShort.WasteClass wasteClass = ItemWasteShort.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid / 10;
						gas += wasteClass.gas / 10;
						slots[i] = new ItemStack(ModItems.nuclear_waste_short_depleted_tiny, 1, meta);
					}
					
					if(item == ModItems.ingot_au198 && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 20) == 0) {
						slots[i] = new ItemStack(ModItems.ingot_mercury, 1, meta);
					}
					if(item == ModItems.nugget_au198 && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 100) == 0) {
						slots[i] = new ItemStack(ModItems.nugget_mercury, 1, meta);
					}
					
					if(item == ModItems.ingot_pb209 && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 10) == 0) {
						slots[i] = new ItemStack(ModItems.ingot_bismuth, 1, meta);
					}
					if(item == ModItems.nugget_pb209 && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 50) == 0) {
						slots[i] = new ItemStack(ModItems.nugget_bismuth, 1, meta);
					}

					if(item == ModItems.powder_sr90 && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 10) == 0) {
						slots[i] = new ItemStack(ModItems.powder_zirconium, 1, meta);
					}
					if(item == ModItems.nugget_sr90 && worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 50) == 0) {
						slots[i] = new ItemStack(ModItems.nugget_zirconium, 1, meta);
					}
				}
			}

			this.tanks[0].setFill(this.tanks[0].getFill() + liquid);
			this.tanks[1].setFill(this.tanks[1].getFill() + gas);
			
			for(int i = 0; i < 2; i++) {
				
				int overflow = Math.max(this.tanks[i].getFill() - this.tanks[i].getMaxFill(), 0);
				
				if(overflow > 0) {
					this.tanks[i].setFill(this.tanks[i].getFill() - overflow);
					this.tanks[i].getTankType().onFluidRelease(this, this.tanks[i], overflow);
				}
			}
			
			age++;
			
			if(age >= 20)
				age -= 20;
			
			this.sendFluidToAll(tanks[0], this);
			this.sendFluidToAll(tanks[1], this);
			
			this.sendStandard(25);
			
			if(rad > 0) {
				radiate(worldObj, xCoord, yCoord, zCoord, rad);
			}
		}
	}

	@Override public void serialize(ByteBuf buf) {
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}
	
	private void radiate(World world, int x, int y, int z, float rads) {
		
		double range = 32D;
		
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x + 0.5, y + 0.5, z + 0.5, x + 0.5, y + 0.5, z + 0.5).expand(range, range, range));
		
		for(EntityLivingBase e : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - (x + 0.5), (e.posY + e.getEyeHeight()) - (y + 0.5), e.posZ - (z + 0.5));
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(x + 0.5 + vec.xCoord * i);
				int iy = (int)Math.floor(y + 0.5 + vec.yCoord * i);
				int iz = (int)Math.floor(z + 0.5 + vec.zCoord * i);
				
				res += world.getBlock(ix, iy, iz).getExplosionResistance(null);
			}
			
			if(res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= (float)res;
			eRads /= (float)(len * len);
			
			ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		Item item = itemStack.getItem();
		
		if(item == ModItems.nuclear_waste_long || 
				item == ModItems.nuclear_waste_long_tiny || 
				item == ModItems.nuclear_waste_short || 
				item == ModItems.nuclear_waste_short_tiny || 
				item == ModItems.ingot_au198)
			return true;
		
		return false;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {

		Item item = itemStack.getItem();
		
		if(item == ModItems.nuclear_waste_long_depleted || 
				item == ModItems.nuclear_waste_long_depleted_tiny || 
				item == ModItems.nuclear_waste_short_depleted || 
				item == ModItems.nuclear_waste_short_depleted_tiny || 
				item == ModItems.ingot_mercury)
			return true;
		
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_arr;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "liquid");
		this.tanks[1].readFromNBT(nbt, "gas");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "liquid");
		this.tanks[1].writeToNBT(nbt, "gas");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[0], tanks[1] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerStorageDrum(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIStorageDrum(player.inventory, this);
	}

	@Override
	public FluidTank getTankToPaste() {
		return null;
	}
}
