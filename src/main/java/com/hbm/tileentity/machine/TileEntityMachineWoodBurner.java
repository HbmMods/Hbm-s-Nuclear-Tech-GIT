package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.container.ContainerMachineWoodBurner;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.gui.GUIMachineWoodBurner;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.lib.Library;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineWoodBurner extends TileEntityMachineBase implements IFluidStandardReceiver, IControlReceiver, IEnergyProviderMK2, IGUIProvider, IInfoProviderEC, IFluidCopiable {
	
	public long power;
	public static final long maxPower = 100_000;
	public int burnTime;
	public int maxBurnTime;
	public boolean liquidBurn = false;
	public boolean isOn = false;
	protected int powerGen = 0;
	
	public FluidTank tank;
	
	public static ModuleBurnTime burnModule = new ModuleBurnTime().setLogTimeMod(4).setWoodTimeMod(2);

	public int ashLevelWood;
	public int ashLevelCoal;
	public int ashLevelMisc;

	public TileEntityMachineWoodBurner() {
		super(6);
		this.tank = new FluidTank(Fluids.WOODOIL, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineWoodBurner";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			powerGen = 0;
			
			this.tank.setType(2, slots);
			this.tank.loadTank(3, 4, slots);
			this.power = Library.chargeItemsFromTE(slots, 5, power, maxPower);
			
			for(DirPos pos : getConPos()) {
				if(power > 0) this.tryProvide(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(worldObj.getTotalWorldTime() % 20 == 0) this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(!liquidBurn) {
				
				if(this.burnTime <= 0) {
					
					if(slots[0] != null) {
						int burn = this.burnModule.getBurnTime(slots[0]);
						if(burn > 0) {
							EnumAshType type = TileEntityFireboxBase.getAshFromFuel(slots[0]);
							if(type == EnumAshType.WOOD) ashLevelWood += burn;
							if(type == EnumAshType.COAL) ashLevelCoal += burn;
							if(type == EnumAshType.MISC) ashLevelMisc += burn;
							int threshold = 2000;
							if(processAsh(ashLevelWood, EnumAshType.WOOD, threshold)) ashLevelWood -= threshold;
							if(processAsh(ashLevelCoal, EnumAshType.COAL, threshold)) ashLevelCoal -= threshold;
							if(processAsh(ashLevelMisc, EnumAshType.MISC, threshold)) ashLevelMisc -= threshold;
							
							this.maxBurnTime = this.burnTime = burn;
							ItemStack container = slots[0].getItem().getContainerItem(slots[0]);
							this.decrStackSize(0, 1);
							if(slots[0] == null && container != null) slots[0] = container.copy();
							this.markChanged();
						}
					}
					
				} else if(this.power < this.maxPower && isOn){
					this.burnTime--;
					this.powerGen += 100;
					if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
				}
				
			} else {
				
				if(this.power < this.maxPower && tank.getFill() > 0 && isOn) {
					FT_Flammable trait = tank.getTankType().getTrait(FT_Flammable.class);
					
					if(trait != null) {
						
						int toBurn = Math.min(tank.getFill(), 2);
						
						if(toBurn > 0) {
							this.powerGen += trait.getHeatEnergy() * toBurn / 2_000L;
							this.tank.setFill(this.tank.getFill() - toBurn);
							if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * toBurn / 2F);
						}
					}
				}
			}
			
			this.power += this.powerGen;
			if(this.power > this.maxPower) this.power = this.maxPower;
			
			this.networkPackNT(25);
		} else {
			
			if(powerGen > 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				worldObj.spawnParticle("smoke", xCoord + 0.5 - dir.offsetX + rot.offsetX, yCoord + 4, zCoord + 0.5 - dir.offsetZ + rot.offsetZ, 0, 0.05, 0);
			}
		}
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeInt(burnTime);
		buf.writeInt(powerGen);
		buf.writeInt(maxBurnTime);
		buf.writeBoolean(isOn);
		buf.writeBoolean(liquidBurn);
		
		tank.serialize(buf);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		burnTime = buf.readInt();
		powerGen = buf.readInt();
		maxBurnTime = buf.readInt();
		isOn = buf.readBoolean();
		liquidBurn = buf.readBoolean();
		
		tank.deserialize(buf);
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite())
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.burnTime = nbt.getInteger("burnTime");
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.isOn = nbt.getBoolean("isOn");
		this.liquidBurn = nbt.getBoolean("liquidBurn");
		tank.readFromNBT(nbt, "t");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("maxBurnTime", maxBurnTime);
		nbt.setBoolean("isOn", isOn);
		nbt.setBoolean("liquidBurn", liquidBurn);
		tank.writeToNBT(nbt, "t");
	}
	
	protected boolean processAsh(int level, EnumAshType type, int threshold) {
		
		if(level >= threshold) {
			if(slots[1] == null) {
				slots[1] = DictFrame.fromOne(ModItems.powder_ash, type);
				return true;
			} else if(slots[1].stackSize < slots[1].getMaxStackSize() && slots[1].getItem() == ModItems.powder_ash && slots[1].getItemDamage() == type.ordinal()) {
				slots[1].stackSize++;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.isOn = !this.isOn;
			this.markChanged();
		}
		if(data.hasKey("switch")) {
			this.liquidBurn = !this.liquidBurn;
			this.markChanged();
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineWoodBurner(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineWoodBurner(player.inventory, this);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && burnModule.getBurnTime(itemStack) > 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot == 1;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		ForgeDirection rot = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		return dir == rot.getOpposite();
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		ForgeDirection rot = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		return dir == rot.getOpposite();
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
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
					yCoord + 6,
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
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, isOn);
		if(this.liquidBurn) data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, 1D);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, power);
	}

	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}
}
