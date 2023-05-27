package com.hbm.tileentity.machine;

import api.hbm.block.ILaserable;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import com.hbm.inventory.container.ContainerCoreEmitter;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICoreEmitter;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreEmitter extends TileEntityMachineBase implements IEnergyUser, ILaserable, IFluidStandardReceiver, SimpleComponent, IGUIProvider {
	
	public long power;
	public static final long maxPower = 1000000000L;
	public int watts;
	public int beam;
	public long joules;
	public boolean isOn;
	public FluidTank tank;
	public long prev;
	
	public static final int range = 50;

	public TileEntityCoreEmitter() {
		super(0);
		tank = new FluidTank(Fluids.CRYOGEL, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.dfcEmitter";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);
			this.subscribeToAllAround(tank.getTankType(), this);
			
			watts = MathHelper.clamp_int(watts, 1, 100);
			long demand = maxPower * watts / 2000;
			
			beam = 0;
			
			if(joules > 0 || prev > 0) {

				if(tank.getFill() >= 20) {
					tank.setFill(tank.getFill() - 20);
				} else {
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.flowing_lava);
					return;
				}
			}
			
			if(isOn) {
				
				//i.e. 50,000,000 HE = 10,000 SPK
				//1 SPK = 5,000HE
				
				if(power >= demand) {
					power -= demand;
					long add = watts * 100;
					joules += add;
				}
				prev = joules;
				
				if(joules > 0) {
					
					long out = joules * 98 / 100;
					
					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
					for(int i = 1; i <= range; i++) {
						
						beam = i;
		
						int x = xCoord + dir.offsetX * i;
						int y = yCoord + dir.offsetY * i;
						int z = zCoord + dir.offsetZ * i;
						
						Block block = worldObj.getBlock(x, y, z);
						TileEntity te = worldObj.getTileEntity(x, y, z);
						
						if(block instanceof ILaserable) {
							
							((ILaserable)block).addEnergy(worldObj, x, y, z, out * 98 / 100, dir);
							break;
						}
						
						if(te instanceof ILaserable) {
							
							((ILaserable)te).addEnergy(worldObj, x, y, z, out * 98 / 100, dir);
							break;
						}
						
						if(te instanceof TileEntityCore) {
							out = ((TileEntityCore)te).burn(out);
							continue;
						}
						
						Block b = worldObj.getBlock(x, y, z);
						
						if(b != Blocks.air) {
							
							if(b.getMaterial().isLiquid()) {
								worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 1.0F, 1.0F);
								worldObj.setBlockToAir(x, y, z);
								break;
							}
							
							float hardness = b.getExplosionResistance(null);
							if(hardness < 6000 && worldObj.rand.nextInt(20) == 0) {
								worldObj.func_147480_a(x, y, z, false);
							}
							
							break;
						}
					}
					
					
					joules = 0;
		
					double blx = Math.min(xCoord, xCoord + dir.offsetX * beam) + 0.2;
					double bux = Math.max(xCoord, xCoord + dir.offsetX * beam) + 0.8;
					double bly = Math.min(yCoord, yCoord + dir.offsetY * beam) + 0.2;
					double buy = Math.max(yCoord, yCoord + dir.offsetY * beam) + 0.8;
					double blz = Math.min(zCoord, zCoord + dir.offsetZ * beam) + 0.2;
					double buz = Math.max(zCoord, zCoord + dir.offsetZ * beam) + 0.8;
					
					List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(blx, bly, blz, bux, buy, buz));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.amsCore, 50);
						e.setFire(10);
					}
				}
			} else {
				joules = 0;
				prev = 0;
			}
			
			this.markDirty();

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("watts", watts);
			data.setLong("prev", prev);
			data.setInteger("beam", beam);
			data.setBoolean("isOn", isOn);
			tank.writeToNBT(data, "tank");
			this.networkPack(data, 250);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {

		power = data.getLong("power");
		watts = data.getInteger("watts");
		prev = data.getLong("prev");
		beam = data.getInteger("beam");
		isOn = data.getBoolean("isOn");
		tank.readFromNBT(data, "tank");
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (watts * i) / 100;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		
		//do not accept lasers from the front
		if(dir.getOpposite().ordinal() != this.getBlockMetadata())
			joules += energy;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		watts = nbt.getInteger("watts");
		joules = nbt.getLong("joules");
		prev = nbt.getLong("prev");
		isOn = nbt.getBoolean("isOn");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("watts", watts);
		nbt.setLong("joules", joules);
		nbt.setLong("prev", prev);
		nbt.setBoolean("isOn", isOn);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "dfc_emitter";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyStored(Context context, Arguments args) {
		return new Object[] {getPower()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxEnergy(Context context, Arguments args) {
		return new Object[] {getMaxPower()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryogel(Context context, Arguments args) {
		return new Object[] {tank.getFill()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInput(Context context, Arguments args) {
		return new Object[] {watts};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower(), tank.getFill(), watts, isOn};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isActive(Context context, Arguments args) {
		return new Object[] {isOn};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setActive(Context context, Arguments args) {
		isOn = args.checkBoolean(0);
		return new Object[] {};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setInput(Context context, Arguments args) {
		int newOutput = args.checkInteger(0);
		if (newOutput > 100) {
			newOutput = 100;
		} else if (newOutput < 0) {
			newOutput = 0;
		}
		watts = newOutput;
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreEmitter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreEmitter(player.inventory, this);
	}
}
