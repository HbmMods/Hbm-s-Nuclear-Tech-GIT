package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerOilburner;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTrait.FluidReleaseType;
import com.hbm.inventory.gui.GUIOilburner;
import com.hbm.lib.Library;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityHeaterOilburner extends TileEntityMachinePolluting implements IGUIProvider, IFluidStandardTransceiver, IHeatSource, IControlReceiver, IFluidCopiable {
	
	public boolean isOn = false;
	public FluidTank tank;
	public int setting = 1;

	public int heatEnergy;
	public static final int maxHeatEnergy = 100_000;

	public TileEntityHeaterOilburner() {
		super(3, 100);
		tank = new FluidTank(Fluids.HEATINGOIL, 16000);
	}

	@Override
	public String getName() {
		return "container.heaterOilburner";
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			tank.loadTank(0, 1, slots);
			tank.setType(2, slots);

			for(DirPos pos : this.getConPos()) {
				this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.sendSmoke(pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			boolean shouldCool = true;
			
			if(this.isOn && this.heatEnergy < maxHeatEnergy) {
				
				if(tank.getTankType().hasTrait(FT_Flammable.class)) {
					FT_Flammable type = tank.getTankType().getTrait(FT_Flammable.class);
					
					int burnRate = setting;
					int toBurn = Math.min(burnRate, tank.getFill());
					
					tank.setFill(tank.getFill() - toBurn);
					
					int heat = (int)(type.getHeatEnergy() / 1000);
					
					this.heatEnergy += heat * toBurn;

					if(worldObj.getTotalWorldTime() % 5 == 0 && toBurn > 0) {
						super.pollute(tank.getTankType(), FluidReleaseType.BURN, toBurn * 5);
					}
					
					shouldCool = false;
				}
			}
			
			if(this.heatEnergy >= maxHeatEnergy)
				shouldCool = false;
			
			if(shouldCool)
				this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
			
			this.networkPackNT(25);
		}
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tank.serialize(buf);
		
		buf.writeBoolean(isOn);
		buf.writeInt(heatEnergy);
		buf.writeByte((byte) this.setting);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tank.deserialize(buf);
		
		isOn = buf.readBoolean();
		heatEnergy = buf.readInt();
		setting = buf.readByte();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "tank");
		isOn = nbt.getBoolean("isOn");
		heatEnergy = nbt.getInteger("heatEnergy");
		setting = nbt.getByte("setting");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "tank");
		nbt.setBoolean("isOn", isOn);
		nbt.setInteger("heatEnergy", heatEnergy);
		nbt.setByte("setting", (byte) this.setting);
	}
	
	public void toggleSetting() {
		setting++;
		
		if(setting > 10)
			setting = 1;
	}

	@Override
	public FluidTank[] getReceivingTanks()  {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerOilburner(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIOilburner(player.inventory, this);
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 256;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.isOn = !this.isOn;
		}
		this.markChanged();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
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
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray("fluidID", new int[]{tank.getTankType().getID()});
		tag.setInteger("burnRate", setting);
		tag.setBoolean("isOn", isOn);
		return tag;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		int id = nbt.getIntArray("fluidID")[index];
		tank.setTankType(Fluids.fromID(id));
		if(nbt.hasKey("isOn")) isOn = nbt.getBoolean("isOn");
		if(nbt.hasKey("burnRate")) setting = nbt.getInteger("burnRate");
	}
}
