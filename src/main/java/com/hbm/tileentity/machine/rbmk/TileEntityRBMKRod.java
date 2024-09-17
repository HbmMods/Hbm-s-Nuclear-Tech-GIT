package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.container.ContainerRBMKRod;
import com.hbm.inventory.gui.GUIRBMKRod;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.Compat;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.ParticleUtil;

import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver, IRBMKLoadable, SimpleComponent, IInfoProviderEC, CompatHandler.OCComponent {
	
	//amount of "neutron energy" buffered for the next tick to use for the reaction
	public double fluxFast;
	public double fluxSlow;
	public boolean hasRod;

	public TileEntityRBMKRod() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.rbmkRod";
	}
	
	@Override
	public boolean isModerated() {
		return ((RBMKRod)this.getBlockType()).moderated;
	}
	
	@Override
	public int trackingRange() {
		return 25;
	}

	@SuppressWarnings("incomplete-switch") //shut the fuck up
	@Override
	public void receiveFlux(NType type, double flux) {
		
		switch(type) {
		case FAST: this.fluxFast += flux; break;
		case SLOW: this.fluxSlow += flux; break;
		}
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
				
				ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());
				
				double fluxIn = fluxFromType(rod.nType);
				double fluxOut = rod.burn(worldObj, slots[0], fluxIn);
				NType rType = rod.rType;
				
				rod.updateHeat(worldObj, slots[0], 1.0D);
				this.heat += rod.provideHeat(worldObj, slots[0], heat, 1.0D);
				
				if(!this.hasLid()) {
					ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, (float) ((this.fluxFast + this.fluxSlow) * 0.05F));
				}
				
				super.updateEntity();
				
				if(this.heat > this.maxHeat()) {
					
					if(RBMKDials.getMeltdownsDisabled(worldObj)) {
						ParticleUtil.spawnGasFlame(worldObj, xCoord + 0.5, yCoord + RBMKDials.getColumnHeight(worldObj) + 0.5, zCoord + 0.5, 0, 0.2, 0);
					} else {
						this.meltdown();
					}
					this.fluxFast = 0;
					this.fluxSlow = 0;
					return;
				}
				
				if(this.heat > 10_000) this.heat = 10_000;
				
				//for spreading, we want the buffered flux to be 0 because we want to know exactly how much gets reflected back
				this.fluxFast = 0;
				this.fluxSlow = 0;

				this.worldObj.theProfiler.startSection("rbmkRod_flux_spread");
				spreadFlux(rType, fluxOut);
				this.worldObj.theProfiler.endSection();
				
				hasRod = true;
				
			} else {

				this.fluxFast = 0;
				this.fluxSlow = 0;
				
				hasRod = false;
				
				super.updateEntity();
			}
		}
	}
	
	/**
	 * SLOW: full efficiency for slow neutrons, fast neutrons have half efficiency
	 * FAST: fast neutrons have 100% efficiency, slow only 30%
	 * ANY: just add together whatever we have because who cares
	 * @param type
	 * @return
	 */
	
	private double fluxFromType(NType type) {
		
		switch(type) {
		case SLOW: return this.fluxFast * 0.5D + this.fluxSlow;
		case FAST: return this.fluxFast + this.fluxSlow * 0.3D;
		case ANY: return this.fluxFast + this.fluxSlow;
		}
		
		return 0.0D;
	}
	
	public static final ForgeDirection[] fluxDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};
	
	protected static NType stream;
	
	protected void spreadFlux(NType type, double fluxOut) {
		
		int range = RBMKDials.getFluxRange(worldObj);
		
		for(ForgeDirection dir : fluxDirs) {
			
			stream = type;
			double flux = fluxOut;
			
			for(int i = 1; i <= range; i++) {
				
				flux = runInteraction(xCoord + dir.offsetX * i, yCoord, zCoord + dir.offsetZ * i, flux);
				
				if(flux <= 0)
					break;
			}
		}
	}
	
	protected double runInteraction(int x, int y, int z, double flux) {
		
		TileEntity te = Compat.getTileStandard(worldObj, x, y, z);
		
		if(te instanceof TileEntityRBMKBase) {
			TileEntityRBMKBase base = (TileEntityRBMKBase) te;
			
			if(!base.hasLid())
				ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, (float) (flux * 0.05F));
			
			if(base.isModerated()) {
				this.stream = NType.SLOW;
			}
		}

		//burn baby burn
		if(te instanceof TileEntityRBMKRod) {
			TileEntityRBMKRod rod = (TileEntityRBMKRod)te;
			
			if(rod.getStackInSlot(0) != null && rod.getStackInSlot(0).getItem() instanceof ItemRBMKRod) {
				rod.receiveFlux(stream, flux);
				return 0;
			} else {
				return flux;
			}
		}
		
		if(te instanceof TileEntityRBMKOutgasser) {
			TileEntityRBMKOutgasser rod = (TileEntityRBMKOutgasser)te;
			
			if(!rod.canProcess()) {
				return flux;
			}
		}
		
		if(te instanceof IRBMKFluxReceiver) {
			IRBMKFluxReceiver rod = (IRBMKFluxReceiver)te;
			rod.receiveFlux(stream, flux);
			return 0;
		}
		
		//multiply neutron count with rod setting
		if(te instanceof TileEntityRBMKControl) {
			TileEntityRBMKControl control = (TileEntityRBMKControl)te;
			
			if(control.getMult() == 0.0D)
				return 0;
			
			flux *= control.getMult();
			
			return flux;
		}
		
		//set neutrons to slow
		if(te instanceof TileEntityRBMKModerator) {
			stream = NType.SLOW;
			return flux;
		}
		
		//return the neutrons back to this with no further action required
		if(te instanceof TileEntityRBMKReflector) {
			this.receiveFlux(this.isModerated() ? NType.SLOW : stream, flux);
			return 0;
		}
		
		//break the neutron flow and nothign else
		if(te instanceof TileEntityRBMKAbsorber) {
			return 0;
		}
		
		if(te instanceof TileEntityRBMKBase) {
			return flux;
		}
		
		int limit = RBMKDials.getColumnHeight(worldObj);
		int hits = 0;
		for(int h = 0; h <= limit; h++) {
			
			if(!worldObj.getBlock(x, y + h, z).isOpaqueCube())
				hits++;
		}
		
		if(hits > 0)
			ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, (float) (flux * 0.05F * hits / (float)limit));
		
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.fluxFast = nbt.getDouble("fluxFast");
		this.fluxSlow = nbt.getDouble("fluxSlow");
		this.hasRod = nbt.getBoolean("hasRod");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("fluxFast", this.fluxFast);
		nbt.setDouble("fluxSlow", this.fluxSlow);
		nbt.setBoolean("hasRod", this.hasRod);
	}
	
	public void getDiagData(NBTTagCompound nbt) {
		this.writeToNBT(nbt);
		
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());

			nbt.setString("f_yield", rod.getYield(slots[0]) + " / " + rod.yield + " (" + (rod.getEnrichment(slots[0]) * 100) + "%)");
			nbt.setString("f_xenon", rod.getPoison(slots[0]) + "%");
			nbt.setString("f_heat", rod.getCoreHeat(slots[0]) + " / " + rod.getHullHeat(slots[0])  + " / " + rod.meltingPoint);
		}
	}
	
	@Override
	public void onMelt(int reduce) {

		boolean moderated = this.isModerated();
		int h = RBMKDials.getColumnHeight(worldObj);
		reduce = MathHelper.clamp_int(reduce, 1, h);
		
		if(worldObj.rand.nextInt(3) == 0)
			reduce++;
		
		boolean corium = slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod;
		
		if(corium && slots[0].getItem() == ModItems.rbmk_fuel_drx) 
			RBMKBase.digamma = true;
		
		slots[0] = null;

		if(corium) {
			
			for(int i = h; i >= 0; i--) {
				worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.corium_block, 5, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord + i, zCoord);
			}
			
			int count = 1 + worldObj.rand.nextInt(RBMKDials.getColumnHeight(worldObj));
			
			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.FUEL);
			}
		} else {
			this.standardMelt(reduce);
		}
		
		if(moderated) {
			
			int count = 2 + worldObj.rand.nextInt(2);
			
			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.GRAPHITE);
			}
		}
		
		spawnDebris(DebrisType.ELEMENT);
		
		if(this.getBlockMetadata() == RBMKBase.DIR_NORMAL_LID.ordinal() + RBMKBase.offset)
			spawnDebris(DebrisType.LID);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());
			data.setDouble("enrichment", rod.getEnrichment(slots[0]));
			data.setDouble("xenon", rod.getPoison(slots[0]));
			data.setDouble("c_heat", rod.getHullHeat(slots[0]));
			data.setDouble("c_coreHeat", rod.getCoreHeat(slots[0]));
			data.setDouble("c_maxHeat", rod.meltingPoint);
		}
		
		return data;
	}

	@Override
	public boolean canLoad(ItemStack toLoad) {
		return toLoad != null && slots[0] == null;
	}

	@Override
	public void load(ItemStack toLoad) {
		slots[0] = toLoad.copy();
		this.markDirty();
	}

	@Override
	public boolean canUnload() {
		return slots[0] != null;
	}

	@Override
	public ItemStack provideNext() {
		return slots[0];
	}

	@Override
	public void unload() {
		slots[0] = null;
		this.markDirty();
	}
	
	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_fuel_rod";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluxSlow(Context context, Arguments args) {
		return new Object[] {fluxSlow};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluxFast(Context context, Arguments args) {
		return new Object[] {fluxFast};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getDepletion(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getEnrichment(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getXenonPoison(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getPoison(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoreHeat(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getCoreHeat(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSkinHeat(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getHullHeat(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getType(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {slots[0].getItem().getUnlocalizedName()};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		List<Object> returnValues = new ArrayList<>();
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			returnValues.add(ItemRBMKRod.getHullHeat(slots[0]));
			returnValues.add(ItemRBMKRod.getCoreHeat(slots[0]));
			returnValues.add(ItemRBMKRod.getEnrichment(slots[0]));
			returnValues.add(ItemRBMKRod.getPoison(slots[0]));
			returnValues.add(slots[0].getItem().getUnlocalizedName());
		} else
			for (int i = 0; i < 5; i++)
				returnValues.add("N/A");

		return new Object[] {
				heat, returnValues.get(0), returnValues.get(1),
				fluxSlow, fluxFast, returnValues.get(2), returnValues.get(3), returnValues.get(4),
				((RBMKRod)this.getBlockType()).moderated, xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getModerated(Context context, Arguments args) {
		return new Object[] {((RBMKRod)this.getBlockType()).moderated};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKRod(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKRod(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			data.setDouble(CompatEnergyControl.D_DEPLETION_PERCENT, ((1.0D - ItemRBMKRod.getEnrichment(slots[0])) * 100_000.0D) / 1_000.0D);
			data.setDouble(CompatEnergyControl.D_XENON_PERCENT, ItemRBMKRod.getPoison(slots[0]));
			data.setDouble(CompatEnergyControl.D_SKIN_C, ItemRBMKRod.getHullHeat(slots[0]));
			data.setDouble(CompatEnergyControl.D_CORE_C, ItemRBMKRod.getCoreHeat(slots[0]));
			data.setDouble(CompatEnergyControl.D_MELT_C, ((ItemRBMKRod) slots[0].getItem()).meltingPoint);
		}
	}
}
