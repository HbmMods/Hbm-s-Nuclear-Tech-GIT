package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.fluid.IFluidUser;
import api.hbm.fluid.IPipeNet;
import api.hbm.tile.IInfoProviderEC;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerRBMKGeneric;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIRBMKBoiler;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.fauxpointtwelve.DirPos;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKBoiler extends TileEntityRBMKSlottedBase implements IControlReceiver, IFluidStandardTransceiver, SimpleComponent, IInfoProviderEC, CompatHandler.OCComponent {
	
	public FluidTank feed;
	public FluidTank steam;
	protected int consumption;
	protected int output;
	
	public TileEntityRBMKBoiler() {
		super(0);

		feed = new FluidTank(Fluids.WATER, 10000);
		steam = new FluidTank(Fluids.STEAM, 1000000);
	}

	@Override
	public String getName() {
		return "container.rbmkBoiler";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.consumption = 0;
			this.output = 0;
			
			double heatCap = this.getHeatFromSteam(steam.getTankType());
			double heatProvided = this.heat - heatCap;
			
			if(heatProvided > 0) {
				double HEAT_PER_MB_WATER = RBMKDials.getBoilerHeatConsumption(worldObj);
				double steamFactor = getFactorFromSteam(steam.getTankType());
				int waterUsed;
				int steamProduced;
				
				if(steam.getTankType() == Fluids.ULTRAHOTSTEAM) {
					steamProduced = (int)Math.floor((heatProvided / HEAT_PER_MB_WATER) * 100D / steamFactor);
					waterUsed = (int)Math.floor(steamProduced / 100D * steamFactor);
					
					if(feed.getFill() < waterUsed) {
						steamProduced = (int)Math.floor(feed.getFill() * 100D / steamFactor);
						waterUsed = (int)Math.floor(steamProduced / 100D * steamFactor);
					}
				} else {
					waterUsed = (int)Math.floor(heatProvided / HEAT_PER_MB_WATER);
					waterUsed = Math.min(waterUsed, feed.getFill());
					steamProduced = (int)Math.floor((waterUsed * 100D) / steamFactor);
				}
				
				this.consumption = waterUsed;
				this.output = steamProduced;
				
				feed.setFill(feed.getFill() - waterUsed);
				steam.setFill(steam.getFill() + steamProduced);
				
				if(steam.getFill() > steam.getMaxFill())
					steam.setFill(steam.getMaxFill());
				
				this.heat -= waterUsed * HEAT_PER_MB_WATER;
			}
			
			this.trySubscribe(feed.getTankType(), worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);
			for(DirPos pos : getOutputPos()) {
				if(this.steam.getFill() > 0) this.sendFluid(steam, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
		
		super.updateEntity();
	}
	
	public static double getHeatFromSteam(FluidType type) {
		if(type == Fluids.STEAM) return 100D;
		if(type == Fluids.HOTSTEAM) return 300D;
		if(type == Fluids.SUPERHOTSTEAM) return 450D;
		if(type == Fluids.ULTRAHOTSTEAM) return 600D;
		return 0D;
	}
	
	public static double getFactorFromSteam(FluidType type) {
		if(type == Fluids.STEAM) return 1D;
		if(type == Fluids.HOTSTEAM) return 10D;
		if(type == Fluids.SUPERHOTSTEAM) return 100D;
		if(type == Fluids.ULTRAHOTSTEAM) return 1000D;
		return 0D;
	}
	
	protected DirPos[] getOutputPos() {
		
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 1, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 1, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord, Library.NEG_Y)
			};
		} else if(worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 2, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 2, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 3, this.zCoord, Library.NEG_Y)
			};
		} else {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y)
			};
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		feed.readFromNBT(nbt, "feed");
		steam.readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		feed.writeToNBT(nbt, "feed");
		steam.writeToNBT(nbt, "steam");
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		steam.serialize(buf);
		feed.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.steam.deserialize(buf);
		this.feed.deserialize(buf);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("compression")) {
			this.cyceCompressor();
		}
	}
	
	public void cyceCompressor() {
		
		FluidType type = steam.getTankType();
		if(type == Fluids.STEAM) {			steam.setTankType(Fluids.HOTSTEAM);			steam.setFill(steam.getFill() / 10); }
		if(type == Fluids.HOTSTEAM) {		steam.setTankType(Fluids.SUPERHOTSTEAM);	steam.setFill(steam.getFill() / 10); }
		if(type == Fluids.SUPERHOTSTEAM) {	steam.setTankType(Fluids.ULTRAHOTSTEAM);	steam.setFill(steam.getFill() / 10); }
		if(type == Fluids.ULTRAHOTSTEAM) {	steam.setTankType(Fluids.STEAM);			steam.setFill(Math.min(steam.getFill() * 1000, steam.getMaxFill())); }
		
		this.markDirty();
	}
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 1 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		if(RBMKDials.getOverpressure(worldObj)) {
			for(DirPos pos : getOutputPos()) {
				IPipeNet net = IFluidUser.getPipeNet(worldObj, pos.getX(), pos.getY(), pos.getZ(), steam.getTankType());
				if(net != null) {
					this.pipes.add(net);
				}
			}
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.BOILER;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("water", this.feed.getFill());
		data.setInteger("maxWater", this.feed.getMaxFill());
		data.setInteger("steam", this.steam.getFill());
		data.setInteger("maxSteam", this.steam.getMaxFill());
		data.setShort("type", (short)this.steam.getTankType().getID());
		return data;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {feed, steam};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {steam};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {feed};
	}
	
	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_boiler";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {heat};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteam(Context context, Arguments args) {
		return new Object[] {steam.getFill()};
	}
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteamMax(Context context, Arguments args) {
		return new Object[] {steam.getMaxFill()};
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWater(Context context, Arguments args) {
		return new Object[] {feed.getFill()};
	}
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWaterMax(Context context, Arguments args) {
		return new Object[] {feed.getMaxFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		int type_1 = (int) CompatHandler.steamTypeToInt(steam.getTankType())[0];
		return new Object[] {heat, steam.getFill(), steam.getMaxFill(), feed.getFill(), feed.getMaxFill(), type_1, xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteamType(Context context, Arguments args) {
		return CompatHandler.steamTypeToInt(steam.getTankType());
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setSteamType(Context context, Arguments args) {
		int type = args.checkInteger(0);
		steam.setTankType(CompatHandler.intToSteamType(type));
		return new Object[] {true};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKGeneric(player.inventory);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKBoiler(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, consumption);
		data.setDouble(CompatEnergyControl.D_OUTPUT_MB, output);
	}
}
