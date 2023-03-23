package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerWatz;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIWatz;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Compat;
import com.hbm.util.EnumUtil;
import com.hbm.util.function.Function;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityWatz extends TileEntityMachineBase implements IGUIProvider {
	
	public FluidTank[] tanks;
	public int heat;
	public double fluxLastReaction; //stores the flux created by the reaction, excludes passive emission
	public double fluxDisplay;
	
	/* lock types for item IO */
	public boolean isLocked = false;
	public ItemStack[] locks = new ItemStack[24];
	
	public TileEntityWatz() {
		super(24);
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.COOLANT, 64_000);
		this.tanks[1] = new FluidTank(Fluids.COOLANT_HOT, 64_000);
		this.tanks[2] = new FluidTank(Fluids.WATZ, 64_000);
	}

	@Override
	public String getName() {
		return "container.watz";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && !updateLock()) {
			
			//TODO: figure out how to make fluid transport instant instead of sloshy,
			//perhaps an initial count that combines all tanks into one large virtual one?
			
			updateManual(true);
		}
	}

	/** enforces strict top to bottom update order (instead of semi-random based on placement) */
	public void updateManual(boolean topMost) {
		
		//TODO: do heat to coolant first
		
		List<ItemStack> pellets = new ArrayList();
		
		for(int i = 0; i < 24; i++) {
			ItemStack stack = slots[i];
			if(stack != null && stack.getItem() == ModItems.watz_pellet) {
				pellets.add(stack);
			}
		}
		
		double baseFlux = 0D;
		
		/* init base flux */
		for(ItemStack stack : pellets) {
			EnumWatzType type = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
			baseFlux += type.passive;
		}
		
		double inputFlux = baseFlux + fluxLastReaction;
		double addedFlux = 0D;
		double addedHeat = 0D;
		
		for(ItemStack stack : pellets) {
			EnumWatzType type = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
			Function burnFunc = type.burnFunc;
			Function heatMod = type.heatMult;
			
			if(burnFunc != null) {
				double mod = heatMod != null ? heatMod.effonix(heat) : 1D;
				double burn = burnFunc.effonix(inputFlux) * mod;
				addedFlux += burn;
				addedHeat += type.heatEmission * burn;
			}
		}
		
		for(ItemStack stack : pellets) {
			EnumWatzType type = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
			Function absorbFunc = type.absorbFunc;
			
			if(absorbFunc != null) {
				addedHeat += absorbFunc.effonix(baseFlux + fluxLastReaction);
			}
		}
		
		this.heat += addedHeat;
		this.fluxLastReaction = addedFlux;
		
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("heat", this.heat);
		data.setDouble("flux", this.fluxLastReaction + baseFlux);
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].writeToNBT(data, "t" + i);
		}
		this.networkPack(data, 25);
		
		TileEntity below = Compat.getTileStandard(worldObj, xCoord, yCoord - 3, zCoord);
		if(below instanceof TileEntityWatz) {
			TileEntityWatz watz = (TileEntityWatz) below;
			//TODO: move down fluids and exchange pellets
			watz.updateManual(false);
		}
	}
	
	/** Prevent manual updates when another segment is above this one */
	public boolean updateLock() {
		return Compat.getTileStandard(worldObj, xCoord, yCoord + 3, zCoord) instanceof TileEntityWatz;
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.heat = nbt.getInteger("heat");
		this.fluxDisplay = nbt.getDouble("flux");
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].readFromNBT(nbt, "t" + i);
		}
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 3,
					zCoord + 4
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerWatz(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIWatz(player.inventory, this);
	}
}
