package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.neutron.NeutronNodeWorld;
import com.hbm.handler.neutron.NeutronNodeWorld.StreamWorld;
import com.hbm.handler.neutron.RBMKNeutronHandler;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.handler.neutron.RBMKNeutronHandler.RBMKNeutronNode;
import com.hbm.handler.neutron.NeutronStream;
import com.hbm.inventory.container.ContainerRBMKRod;
import com.hbm.inventory.gui.GUIRBMKRod;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.BufferUtil;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.ParticleUtil;

import api.hbm.tile.IInfoProviderEC;
import com.hbm.util.fauxpointtwelve.BlockPos;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver, IRBMKLoadable, IInfoProviderEC, SimpleComponent, CompatHandler.OCComponent {

	// New system!!
	// Used for receiving flux (calculating outbound flux/burning rods)
	public double fluxFastRatio;
	public double fluxQuantity;
	public double lastFluxQuantity;
	public double lastFluxRatio;

	public boolean hasRod;

	// Fuel rod item data client sync
	private String fuelYield;
	private String fuelXenon;
	private String fuelHeat;

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

	@Override
	public void receiveFlux(NeutronStream stream) {
		double fastFlux = this.fluxQuantity * this.fluxFastRatio;
		double fastFluxIn = stream.fluxQuantity * stream.fluxRatio;

		this.fluxQuantity += stream.fluxQuantity;
		fluxFastRatio = (fastFlux + fastFluxIn) / fluxQuantity;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {

				ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());

				double fluxRatioOut;
				double fluxQuantityOut;

				// Experimental flux ratio curve rods!
				// Again, nothing really uses this so its just idle code at the moment.
				if(rod.specialFluxCurve) {

					fluxRatioOut = rod.fluxRatioOut(this.fluxFastRatio, ItemRBMKRod.getEnrichment(slots[0]));

					double fluxIn;

					fluxIn = rod.fluxFromRatio(this.fluxQuantity, this.fluxFastRatio);

					fluxQuantityOut = rod.burn(worldObj, slots[0], fluxIn);
				} else {
					NType rType = rod.rType;
					if(rType == NType.SLOW)
						fluxRatioOut = 0;
					else
						fluxRatioOut = 1;

					double fluxIn = fluxFromType(rod.nType);
					fluxQuantityOut = rod.burn(worldObj, slots[0], fluxIn);
				}

				rod.updateHeat(worldObj, slots[0], 1.0D);
				this.heat += rod.provideHeat(worldObj, slots[0], heat, 1.0D);

				if(!this.hasLid()) {
					ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, (float) (this.fluxQuantity * 0.05F));
				}

				super.updateEntity();

				if(this.heat > this.maxHeat()) {

					if(RBMKDials.getMeltdownsDisabled(worldObj)) {
						ParticleUtil.spawnGasFlame(worldObj, xCoord + 0.5, yCoord + RBMKDials.getColumnHeight(worldObj) + 0.5, zCoord + 0.5, 0, 0.2, 0);
					} else {
						this.meltdown();
					}
					this.lastFluxRatio = 0;
					this.lastFluxQuantity = 0;
					this.fluxQuantity = 0;
					return;
				}

				if(this.heat > 10_000) this.heat = 10_000;

				//for spreading, we want the buffered flux to be 0 because we want to know exactly how much gets reflected back

				this.lastFluxQuantity = this.fluxQuantity;
				this.lastFluxRatio = this.fluxFastRatio;

				this.fluxQuantity = 0;
				this.fluxFastRatio = 0;

				spreadFlux(fluxQuantityOut, fluxRatioOut);

				hasRod = true;

			} else {

				this.lastFluxRatio = 0;
				this.lastFluxQuantity = 0;
				this.fluxQuantity = 0;
				this.fluxFastRatio = 0;

				hasRod = false;

				super.updateEntity();
			}
		}
	}

	private double fluxFromType(NType type) {

		double fastFlux = this.fluxQuantity * this.fluxFastRatio;
		double slowFlux = this.fluxQuantity * (1 - this.fluxFastRatio);

		switch(type) {
		case SLOW: return slowFlux + fastFlux * 0.5;
		case FAST: return fastFlux + slowFlux * 0.3;
		case ANY: return this.fluxQuantity;
		}

		return 0.0D;
	}

	public static final ForgeDirection[] fluxDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};

	private BlockPos pos;

	public void spreadFlux(double flux, double ratio) {

		if(pos == null)
			pos = new BlockPos(this);

		if(flux == 0) {
			// simple way to remove the node from the cache when no flux is going into it!
			NeutronNodeWorld.removeNode(worldObj, pos);
			return;
		}

		StreamWorld streamWorld = NeutronNodeWorld.getOrAddWorld(worldObj);
		RBMKNeutronNode node = (RBMKNeutronNode) streamWorld.getNode(pos);

		if(node == null) {
			node = RBMKNeutronHandler.makeNode(streamWorld, this);
			streamWorld.addNode(node);
		}

		for(ForgeDirection dir : fluxDirs) {

			Vec3 neutronVector = Vec3.createVectorHelper(dir.offsetX, dir.offsetY, dir.offsetZ);

			// Create new neutron streams
			new RBMKNeutronHandler.RBMKNeutronStream(node, neutronVector, flux, ratio);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		if(nbt.hasKey("fluxFast") || nbt.hasKey("fluxSlow")) {
			// recalculate new values to keep stable operations
			this.fluxQuantity = nbt.getDouble("fluxFast") + nbt.getDouble("fluxSlow");
			if(this.fluxQuantity > 0)
				this.fluxFastRatio = nbt.getDouble("fluxFast") / fluxQuantity;
			else
				this.fluxFastRatio = 0;
		} else {
			this.fluxQuantity = nbt.getDouble("fluxQuantity");
			this.fluxFastRatio = nbt.getDouble("fluxMod");
		}
		this.hasRod = nbt.getBoolean("hasRod");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if(!diag) {
			nbt.setDouble("fluxQuantity", this.lastFluxQuantity);
			nbt.setDouble("fluxMod", this.lastFluxRatio);
		} else {
			nbt.setDouble("fluxSlow", this.fluxQuantity * (1 - fluxFastRatio));
			nbt.setDouble("fluxFast", this.fluxQuantity * fluxFastRatio);
		}
		nbt.setBoolean("hasRod", this.hasRod);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(this.lastFluxQuantity);
		buf.writeDouble(this.lastFluxRatio);
		buf.writeBoolean(this.hasRod);
		if(this.hasRod) {
			ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());
			BufferUtil.writeString(buf, ItemRBMKRod.getYield(slots[0]) + " / " + rod.yield + " (" + (ItemRBMKRod.getEnrichment(slots[0]) * 100) + "%)");
			BufferUtil.writeString(buf, ItemRBMKRod.getPoison(slots[0]) + "%");
			BufferUtil.writeString(buf, ItemRBMKRod.getCoreHeat(slots[0]) + " / " + ItemRBMKRod.getHullHeat(slots[0])  + " / " + rod.meltingPoint);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.fluxQuantity = buf.readDouble();
		this.fluxFastRatio = buf.readDouble();
		this.hasRod = buf.readBoolean();
		if(this.hasRod) {
			fuelYield = BufferUtil.readString(buf);
			fuelXenon = BufferUtil.readString(buf);
			fuelHeat = BufferUtil.readString(buf);
		} else {
			fuelYield = fuelXenon = fuelHeat = null;
		}
	}

	public void getDiagData(NBTTagCompound nbt) {
		diag = true;
		this.writeToNBT(nbt);
		diag = false;

		if(fuelYield != null && fuelXenon != null && fuelHeat != null) {
			nbt.setString("f_yield", fuelYield);
			nbt.setString("f_xenon", fuelXenon);
			nbt.setString("f_heat", fuelHeat);
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
	public RBMKNeutronHandler.RBMKType getRBMKType() {
		return RBMKNeutronHandler.RBMKType.ROD;
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
			data.setDouble("enrichment", ItemRBMKRod.getEnrichment(slots[0]));
			data.setDouble("xenon", ItemRBMKRod.getPoison(slots[0]));
			data.setDouble("c_heat", ItemRBMKRod.getHullHeat(slots[0]));
			data.setDouble("c_coreHeat", ItemRBMKRod.getCoreHeat(slots[0]));
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
	public Object[] getFluxQuantity(Context context, Arguments args) {
		return new Object[] {fluxQuantity};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluxRatio(Context context, Arguments args) {
		return new Object[] {fluxFastRatio};
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
		} else {
			for(int i = 0; i < 5; i++) returnValues.add("N/A");
		}

		return new Object[] {
			heat, returnValues.get(0), returnValues.get(1),
			fluxQuantity, fluxFastRatio, returnValues.get(2), returnValues.get(3), returnValues.get(4),
			((RBMKRod)this.getBlockType()).moderated, xCoord, yCoord, zCoord
		};
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
