package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.inventory.container.ContainerICFPress;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIICFPress;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemICFPellet;
import com.hbm.items.machine.ItemICFPellet.EnumICFFuel;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityICFPress extends TileEntityMachineBase implements IFluidStandardReceiver, IGUIProvider, IFluidCopiable {
	
	public FluidTank[] tanks;
	public int muon;
	public static final int maxMuon = 16;

	public TileEntityICFPress() {
		super(8);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.DEUTERIUM, 16_000);
		this.tanks[1] = new FluidTank(Fluids.TRITIUM, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineICFPress";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.tanks[0].setType(6, slots);
			this.tanks[1].setType(7, slots);
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				this.subscribeToAllAround(tanks[0].getTankType(), this);
				this.subscribeToAllAround(tanks[1].getTankType(), this);
			}
			
			if(muon <= 0 && slots[2] != null && slots[2].getItem() == ModItems.particle_muon) {
				
				ItemStack container = slots[2].getItem().getContainerItem(slots[2]);
				boolean canStore = false;
				
				if(container == null) {
					canStore = true;
				} else if(slots[3] == null) {
					slots[3] = container.copy();
					canStore = true;
				} else if(slots[3].getItem() == container.getItem() && slots[3].getItemDamage() == container.getItemDamage() && slots[3].stackSize < slots[3].getMaxStackSize()) {
					slots[3].stackSize++;
					canStore = true;
				}
				
				if(canStore) {
					this.muon = this.maxMuon;
					this.decrStackSize(2, 1);
					this.markDirty();
				}
			}
			
			press();
			
			this.networkPackNT(15);
		}
	}
	
	public void press() {
		if(slots[0] == null || slots[0].getItem() != ModItems.icf_pellet_empty) return;
		if(slots[1] != null) return;
		
		ItemICFPellet.init();
		
		EnumICFFuel fuel1 = getFuel(tanks[0], slots[4], 0);
		EnumICFFuel fuel2 = getFuel(tanks[1], slots[5], 1);
		
		if(fuel1 == null || fuel2 == null || fuel1 == fuel2) return;
		
		slots[1] = ItemICFPellet.setup(fuel1, fuel2, muon > 0);
		
		if(muon > 0) muon--;

		this.decrStackSize(0, 1);
		if(usedFluid[0]) tanks[0].setFill(tanks[0].getFill() - 1000); else this.decrStackSize(4, 1);
		if(usedFluid[1]) tanks[1].setFill(tanks[1].getFill() - 1000); else this.decrStackSize(5, 1);
		
		this.markChanged();
	}

	public static boolean[] usedFluid = new boolean[2];
	
	public EnumICFFuel getFuel(FluidTank tank, ItemStack slot, int index) {
		usedFluid[index] = false;
		if(tank.getFill() >= 1000 && ItemICFPellet.fluidMap.containsKey(tank.getTankType())) {
			usedFluid[index] = true;
			return ItemICFPellet.fluidMap.get(tank.getTankType());
		}
		if(slot == null) return null;
		List<MaterialStack> mats = Mats.getMaterialsFromItem(slot);
		if(mats == null || mats.size() != 1) return null;
		MaterialStack mat = mats.get(0);
		if(mat.amount != MaterialShapes.INGOT.q(1)) return null;
		return ItemICFPellet.materialMap.get(mat.material);
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeByte((byte) muon);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		
		this.muon = buf.readByte();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(stack.getItem() == ModItems.icf_pellet_empty) return slot == 0;
		if(stack.getItem() == ModItems.particle_muon) return slot == 2;
		return slot == 4 || slot == 5;
	}

	public static final int[] topBottom = new int[] {0, 1, 2, 3, 4};
	public static final int[] sides = new int[] {0, 1, 2, 3, 5};

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 || side == 1 ? topBottom : sides;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot == 1 || slot == 3;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
		this.muon = nbt.getByte("muon");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tanks[0].writeToNBT(nbt, "t0");
		tanks[1].writeToNBT(nbt, "t1");
		nbt.setByte("muon", (byte) muon);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerICFPress(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIICFPress(player.inventory, this);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return tanks;
	}
}
