package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerWatz;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.gui.GUIWatz;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Compat;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.util.function.Function;

import api.hbm.fluid.IFluidStandardTransceiver;
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
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWatz extends TileEntityMachineBase implements IFluidStandardTransceiver, IGUIProvider {
	
	public FluidTank[] tanks;
	public int heat;
	public double fluxLastBase;		//flux created by the previous passive emission, only used for display
	public double fluxLastReaction;	//flux created by the previous reaction, used for the next reaction
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
			
			List<TileEntityWatz> segments = new ArrayList();
			segments.add(this);
			this.subscribeToTop();
			
			/* accumulate all segments */
			for(int y = yCoord - 3; y >= 0; y -= 3) {
				TileEntity tile = Compat.getTileStandard(worldObj, xCoord, y, zCoord);
				if(tile instanceof TileEntityWatz) {
					segments.add((TileEntityWatz) tile);
				} else {
					break;
				}
			}
			
			/* set up shared tanks */
			FluidTank[] sharedTanks = new FluidTank[3];
			for(int i = 0; i < 3; i++) sharedTanks[i] = new FluidTank(tanks[i].getTankType(), 0);
			
			for(TileEntityWatz segment : segments) {
				segment.setupCoolant();
				for(int i = 0; i < 3; i++) {
					sharedTanks[i].changeTankSize(sharedTanks[i].getMaxFill() + segment.tanks[i].getMaxFill());
					sharedTanks[i].setFill(sharedTanks[i].getFill() + segment.tanks[i].getFill());
				}
			}
			
			//update coolant, bottom to top
			for(int i = segments.size() - 1; i >= 0; i--) {
				TileEntityWatz segment = segments.get(i);
				segment.updateCoolant(sharedTanks);
			}
			
			/* update reaction, top to bottom */
			this.updateReaction(null, sharedTanks);
			for(int i = 1; i < segments.size(); i++) {
				TileEntityWatz segment = segments.get(i);
				TileEntityWatz above = segments.get(i - 1);
				segment.updateReaction(above, sharedTanks);
			}
			
			//TODO: call fluidSend on the bottom-most segment
			
			/* re-distribute fluid from shared tanks back into actual tanks, bottom to top */
			for(int i = segments.size() - 1; i >= 0; i--) {
				TileEntityWatz segment = segments.get(i);
				for(int j = 0; j < 3; j++) {
					int min = Math.min(segment.tanks[j].getMaxFill(), sharedTanks[j].getFill());
					segment.tanks[j].setFill(min);
					sharedTanks[j].setFill(sharedTanks[j].getFill() - min);
				}
			}
			
			/* send sync packets (order doesn't matter) */
			for(TileEntityWatz segment : segments) {
				segment.sendPacket(sharedTanks);
				segment.heat *= 0.99; //cool 1% per tick
			}
			
			segments.get(segments.size() - 1).sendOutBottom();
		}
	}
	
	/** basic sanity checking, usually wouldn't do anything except when NBT loading borks */
	public void setupCoolant() {
		
		if(!tanks[0].getTankType().hasTrait(FT_Heatable.class)) {
			tanks[0].setTankType(Fluids.COOLANT);
		}
		
		tanks[1].setTankType(tanks[0].getTankType().getTrait(FT_Heatable.class).getFirstStep().typeProduced);
	}
	
	public void updateCoolant(FluidTank[] tanks) {
		
		double coolingFactor = 0.05D; //20% per tick, TEMP
		double heatToUse = this.heat * coolingFactor;
		
		FT_Heatable trait = tanks[0].getTankType().getTrait(FT_Heatable.class);
		HeatingStep step = trait.getFirstStep();
		
		int heatCycles = (int) (heatToUse / step.heatReq);
		int coolCycles = tanks[0].getFill() / step.amountReq;
		int hotCycles = (tanks[1].getMaxFill() - tanks[1].getFill()) / step.amountProduced;
		
		int cycles = Math.min(heatCycles, Math.min(hotCycles, coolCycles));
		this.heat -= cycles * step.heatReq;
		tanks[0].setFill(tanks[0].getFill() - coolCycles * step.amountReq);
		tanks[1].setFill(tanks[1].getFill() + hotCycles * step.amountProduced);
	}

	/** enforces strict top to bottom update order (instead of semi-random based on placement) */
	public void updateReaction(TileEntityWatz above, FluidTank[] tanks) {
		
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
		this.fluxLastBase = baseFlux;
		this.fluxLastReaction = addedFlux;
		
		if(above != null) {
			for(int i = 0; i < 24; i++) {
				ItemStack stackBottom = slots[i];
				ItemStack stackTop = above.slots[i];
				
				/* items fall down if the bottom slot is empty */
				if(stackBottom == null && stackTop != null) {
					slots[i] = stackTop.copy();
					above.decrStackSize(i, stackTop.stackSize);
				}
				
				/* items switch places if the top slot is depleted */
				if(stackBottom != null && stackBottom.getItem() == ModItems.watz_pellet && stackTop != null && stackTop.getItem() == ModItems.watz_pellet_depleted) {
					ItemStack buf = stackTop.copy();
					above.slots[i] = stackBottom.copy();
					slots[i] = buf;
				}
			}
		}
	}
	
	public void sendPacket(FluidTank[] tanks) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("heat", this.heat);
		data.setDouble("flux", this.fluxLastReaction + this.fluxLastBase);
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].writeToNBT(data, "t" + i);
		}
		this.networkPack(data, 25);
	}
	
	/** Prevent manual updates when another segment is above this one */
	public boolean updateLock() {
		return Compat.getTileStandard(worldObj, xCoord, yCoord + 3, zCoord) instanceof TileEntityWatz;
	}
	
	protected void subscribeToTop() {
		this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord, yCoord + 3, zCoord, ForgeDirection.UP);
		this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord + 2, yCoord + 3, zCoord, ForgeDirection.UP);
		this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord - 2, yCoord + 3, zCoord, ForgeDirection.UP);
		this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord, yCoord + 3, zCoord + 2, ForgeDirection.UP);
		this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord, yCoord + 3, zCoord - 2, ForgeDirection.UP);
	}
	
	protected void sendOutBottom() {
		
		for(DirPos pos : getSendingPos()) {
			if(tanks[1].getFill() > 0) this.sendFluid(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			if(tanks[2].getFill() > 0) this.sendFluid(tanks[2].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected DirPos[] getSendingPos() {
		return new DirPos[] {
				new DirPos(xCoord, yCoord - 1, zCoord, ForgeDirection.DOWN),
				new DirPos(xCoord + 2, yCoord - 1, zCoord, ForgeDirection.DOWN),
				new DirPos(xCoord - 2, yCoord - 1, zCoord, ForgeDirection.DOWN),
				new DirPos(xCoord, yCoord - 1, zCoord + 2, ForgeDirection.DOWN),
				new DirPos(xCoord, yCoord - 1, zCoord - 2, ForgeDirection.DOWN)
		};
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.heat = nbt.getInteger("heat");
		this.fluxDisplay = nbt.getDouble("flux");
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].readFromNBT(nbt, "t" + i);
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() == ModItems.watz_pellet;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
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

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[1], tanks[2] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0] };
	}
}
