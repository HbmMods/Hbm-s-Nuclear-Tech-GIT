package com.hbm.tileentity.machine.storage;

import api.hbm.fluid.IFluidStandardTransceiver;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.container.ContainerMachineFluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Amat;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Liquid;
import com.hbm.inventory.gui.GUIMachineFluidTank;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IOverpressurable;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.IRepairable;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityMachineFluidTank extends TileEntityMachineBase implements IFluidContainer, SimpleComponent, IFluidSource, IFluidAcceptor, IFluidStandardTransceiver, IPersistentNBT, IOverpressurable, IGUIProvider, IRepairable {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public boolean hasExploded = false;
	protected boolean sendingBrake = false;
	public boolean onFire = false;
	
	public Explosion lastExplosion = null;
	
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public TileEntityMachineFluidTank() {
		super(6);
		tank = new FluidTank(Fluids.NONE, 256000);
	}

	@Override
	public String getName() {
		return "container.fluidtank";
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			//meta below 12 means that it's an old multiblock configuration
			if(this.getBlockMetadata() < 12) {
				//get old direction
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getRotation(ForgeDirection.DOWN);
				//remove tile from the world to prevent inventory dropping
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
				//use fillspace to create a new multiblock configuration
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.machine_fluidtank, dir.ordinal() + 10, 3);
				MultiblockHandlerXR.fillSpace(worldObj, xCoord, yCoord, zCoord, ((BlockDummyable) ModBlocks.machine_fluidtank).getDimensions(), ModBlocks.machine_fluidtank, dir);
				//load the tile data to restore the old values
				NBTTagCompound data = new NBTTagCompound();
				this.writeToNBT(data);
				worldObj.getTileEntity(xCoord, yCoord, zCoord).readFromNBT(data);
				return;
			}
			
			if(!hasExploded) {
				age++;
				
				if(age >= 20)
					age = 0;
				
				this.sendingBrake = true;
				tank.setFill(TileEntityBarrel.transmitFluidFairly(worldObj, tank, this, tank.getFill(), this.mode == 0 || this.mode == 1, this.mode == 1 || this.mode == 2, getConPos()));
				this.sendingBrake = false;
				
				if((mode == 1 || mode == 2) && (age == 9 || age == 19))
					fillFluidInit(tank.getTankType());
				
				tank.loadTank(2, 3, slots);
				tank.setType(0, 1, slots);
			}
			
			if(tank.getFill() > 0) {
				if(tank.getTankType().isAntimatter()) {
					new ExplosionVNT(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 5F).makeAmat().setBlockAllocator(null).setBlockProcessor(null).explode();
					this.explode();
					this.tank.setFill(0);
				}
				
				if(tank.getTankType().hasTrait(FT_Corrosive.class) && tank.getTankType().getTrait(FT_Corrosive.class).isHighlyCorrosive()) {
					this.explode();
				}
				
				if(this.hasExploded) {

					int leaking = 0;
					if(tank.getTankType().isAntimatter()) {
						leaking = tank.getFill();
					} else if(tank.getTankType().hasTrait(FT_Gaseous.class) || tank.getTankType().hasTrait(FT_Gaseous_ART.class)) {
						leaking = Math.min(tank.getFill(), tank.getMaxFill() / 100);
					} else {
						leaking = Math.min(tank.getFill(), tank.getMaxFill() / 10000);
					}
					
					updateLeak(leaking);
				}
			}
			
			tank.unloadTank(4, 5, slots);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", mode);
			data.setBoolean("hasExploded", hasExploded);
			this.tank.writeToNBT(data, "t");
			this.networkPack(data, 150);
		}
	}
	
	/** called when the tank breaks due to hazardous materials or external force, can be used to quickly void part of the tank or spawn a mushroom cloud */
	public void explode() {
		this.hasExploded = true;
		this.onFire = tank.getTankType().hasTrait(FT_Flammable.class);
		this.markChanged();
	}
	
	/** called every tick post explosion, used for leaking fluid and spawning particles */
	public void updateLeak(int amount) {
		if(!hasExploded) return;
		if(amount <= 0) return;
		
		this.tank.getTankType().onFluidRelease(this, tank, amount);
		this.tank.setFill(Math.max(0, this.tank.getFill() - amount));
		
		FluidType type = tank.getTankType();
		
		if(type.hasTrait(FT_Amat.class)) {
			new ExplosionVNT(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 5F).makeAmat().setBlockAllocator(null).setBlockProcessor(null).explode();
			
		} else if(type.hasTrait(FT_Flammable.class) && onFire) {
			List<Entity> affected = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 1.5, yCoord, zCoord - 1.5, xCoord + 2.5, yCoord + 5, zCoord + 2.5));
			for(Entity e : affected) e.setFire(5);
			Random rand = worldObj.rand;
			ParticleUtil.spawnGasFlame(worldObj, xCoord + rand.nextDouble(), yCoord + 0.5 + rand.nextDouble(), zCoord + rand.nextDouble(), rand.nextGaussian() * 0.2, 0.1, rand.nextGaussian() * 0.2);
			
		} else if(type.hasTrait(FT_Gaseous.class) || type.hasTrait(FT_Gaseous_ART.class)) {
			
			if(worldObj.getTotalWorldTime() % 5 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 1F);
				data.setFloat("base", 1F);
				data.setFloat("max", 5F);
				data.setInteger("life", 100 + worldObj.rand.nextInt(20));
				data.setInteger("color", tank.getTankType().getColor());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.5, yCoord + 1, zCoord + 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			}
		}
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		
		if(this.hasExploded) return;
		this.onFire = tank.getTankType().hasTrait(FT_Flammable.class);
		this.hasExploded = true;
		this.markChanged();
	}

	@Override
	public void tryExtinguish(World world, int x, int y, int z, EnumExtinguishType type) {
		if(!this.hasExploded || !this.onFire) return;
		
		if(type == EnumExtinguishType.WATER) {
			if(tank.getTankType().hasTrait(FT_Liquid.class)) { // extinguishing oil with water is a terrible idea!
				worldObj.newExplosion(null, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 5F, true, true);
			} else {
				this.onFire = false;
				this.markChanged();
				return;
			}
		}
		
		if(type == EnumExtinguishType.FOAM || type == EnumExtinguishType.CO2) {
			this.onFire = false;
			this.markChanged();
		}
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.mode = data.getShort("mode");
		this.hasExploded = data.getBoolean("hasExploded");
		this.tank.readFromNBT(data, "t");
	}
	
	public void handleButtonPacket(int value, int meta) {
		mode = (short) ((mode + 1) % modes);
		this.markChanged();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 3,
					zCoord + 3
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
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(mode == 2 || mode == 3 || this.sendingBrake)
			return 0;
		
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		mode = nbt.getShort("mode");
		tank.readFromNBT(nbt, "tank");
		hasExploded = nbt.getBoolean("exploded");
		onFire = nbt.getBoolean("onFire");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", mode);
		tank.writeToNBT(nbt, "tank");
		nbt.setBoolean("exploded", hasExploded);
		nbt.setBoolean("onFire", onFire);
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		long toTransfer = Math.min(getDemand(type, pressure), fluid);
		tank.setFill(tank.getFill() + (int) toTransfer);
		return fluid - toTransfer;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		
		if(this.mode == 2 || this.mode == 3 || this.sendingBrake)
			return 0;
		
		if(tank.getPressure() != pressure) return 0;
		
		return type == tank.getTankType() ? tank.getMaxFill() - tank.getFill() : 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tank.getFill() == 0 && !this.hasExploded) return;
		NBTTagCompound data = new NBTTagCompound();
		this.tank.writeToNBT(data, "tank");
		data.setShort("mode", mode);
		data.setBoolean("hasExploded", hasExploded);
		data.setBoolean("onFire", onFire);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		this.tank.readFromNBT(data, "tank");
		this.mode = data.getShort("mode");
		this.hasExploded = data.getBoolean("hasExploded");
		this.onFire = data.getBoolean("onFire");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		if(this.hasExploded) return new FluidTank[0];
		return (mode == 1 || mode == 2) ? new FluidTank[] {tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		if(this.hasExploded || this.sendingBrake) return new FluidTank[0];
		return (mode == 0 || mode == 1) ? new FluidTank[] {tank} : new FluidTank[0];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) world.getTileEntity(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) world.getTileEntity(x, y, z));
	}

	@Override
	public boolean isDamaged() {
		return this.hasExploded;
	}
	
	List<AStack> repair = new ArrayList();
	@Override
	public List<AStack> getRepairMaterials() {
		
		if(!repair.isEmpty())
			return repair;
		
		repair.add(new OreDictStack(OreDictManager.STEEL.plate(), 6));
		return repair;
	}

	@Override
	public void repair() {
		this.hasExploded = false;
		this.markChanged();
	}

	@Override
	public String getComponentName() {
		return "ntm_tank";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluidStored(Context context, Arguments args) {
		return new Object[] {tank.getFill()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxStored(Context context, Arguments args) {
		return new Object[] {tank.getMaxFill()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTypeStored(Context context, Arguments args) {
		return new Object[] {tank.getTankType().getName()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[]{tank.getFill(), tank.getMaxFill(), tank.getTankType().getName()};
	}
}