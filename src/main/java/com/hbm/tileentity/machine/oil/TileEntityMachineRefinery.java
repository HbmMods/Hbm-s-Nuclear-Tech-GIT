package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.container.ContainerMachineRefinery;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineRefinery;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.*;
import com.hbm.util.ParticleUtil;
import com.hbm.util.Tuple.Quintet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRefinery extends TileEntityMachineBase implements IEnergyReceiverMK2, IOverpressurable, IPersistentNBT, IRepairable, IFluidStandardTransceiver, IGUIProvider, IFluidCopiable {

	public long power = 0;
	public int sulfur = 0;
	public static final int maxSulfur = 100;
	public static final long maxPower = 1000;
	public FluidTank[] tanks;
	
	public boolean hasExploded = false;
	public boolean onFire = false;
	public Explosion lastExplosion = null;
	
	private AudioWrapper audio;
	private int audioTime;
	public boolean isOn;

	private static final int[] slot_access = new int[] {11};
	
	public TileEntityMachineRefinery() {
		super(13);
		tanks = new FluidTank[5];
		tanks[0] = new FluidTank(Fluids.HOTOIL, 64_000);
		tanks[1] = new FluidTank(Fluids.HEAVYOIL, 24_000);
		tanks[2] = new FluidTank(Fluids.NAPHTHA, 24_000);
		tanks[3] = new FluidTank(Fluids.LIGHTOIL, 24_000);
		tanks[4] = new FluidTank(Fluids.PETROLEUM, 24_000);
	}

	@Override
	public String getName() {
		return "container.machineRefinery";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "input");
		tanks[1].readFromNBT(nbt, "heavy");
		tanks[2].readFromNBT(nbt, "naphtha");
		tanks[3].readFromNBT(nbt, "light");
		tanks[4].readFromNBT(nbt, "petroleum");
		sulfur = nbt.getInteger("sulfur");
		hasExploded = nbt.getBoolean("exploded");
		onFire = nbt.getBoolean("onFire");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "input");
		tanks[1].writeToNBT(nbt, "heavy");
		tanks[2].writeToNBT(nbt, "naphtha");
		tanks[3].writeToNBT(nbt, "light");
		tanks[4].writeToNBT(nbt, "petroleum");
		nbt.setInteger("sulfur", sulfur);
		nbt.setBoolean("exploded", hasExploded);
		nbt.setBoolean("onFire", onFire);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_access;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 11;
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.isOn = false;
			
			if(this.getBlockMetadata() < 12) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getRotation(ForgeDirection.DOWN);
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.machine_refinery, dir.ordinal() + 10, 3);
				MultiblockHandlerXR.fillSpace(worldObj, xCoord, yCoord, zCoord, ((BlockDummyable) ModBlocks.machine_refinery).getDimensions(), ModBlocks.machine_refinery, dir);
				NBTTagCompound data = new NBTTagCompound();
				this.writeToNBT(data);
				worldObj.getTileEntity(xCoord, yCoord, zCoord).readFromNBT(data);
				return;
			}
			
			if(!this.hasExploded) {
				
				this.updateConnections();
				
				power = Library.chargeTEFromItems(slots, 0, power, maxPower);
				tanks[0].setType(12, slots);
				tanks[0].loadTank(1, 2, slots);
				
				refine();
	
				tanks[1].unloadTank(3, 4, slots);
				tanks[2].unloadTank(5, 6, slots);
				tanks[3].unloadTank(7, 8, slots);
				tanks[4].unloadTank(9, 10, slots);
				
				for(DirPos pos : getConPos()) {
					for(int i = 1; i < 5; i++) {
						if(tanks[i].getFill() > 0) {
							this.sendFluid(tanks[i], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
						}
					}
				}
			} else if(onFire){
				
				boolean hasFuel = false;
				for(int i = 0; i < 5; i++) {
					if(tanks[i].getFill() > 0) {
						tanks[i].setFill(Math.max(tanks[i].getFill() - 10, 0));
						hasFuel = true;
					}
				}
				
				if(hasFuel) {
					List<Entity> affected = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 1.5, yCoord, zCoord - 1.5, xCoord + 2.5, yCoord + 8, zCoord + 2.5));
					for(Entity e : affected) e.setFire(5);
					Random rand = worldObj.rand;
					ParticleUtil.spawnGasFlame(worldObj, xCoord + rand.nextDouble(), yCoord + 1.5 + rand.nextDouble() * 3, zCoord + rand.nextDouble(), rand.nextGaussian() * 0.05, 0.1, rand.nextGaussian() * 0.05);

					if(worldObj.getTotalWorldTime() % 20 == 0) {
						PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 70);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			for(int i = 0; i < 5; i++) tanks[i].writeToNBT(data, "" + i);
			data.setBoolean("exploded", hasExploded);
			data.setBoolean("onFire", onFire);
			data.setBoolean("isOn", this.isOn);
			this.networkPack(data, 150);
		} else {
			
			if(this.isOn) audioTime = 20;
			
			if(audioTime > 0) {
				
				audioTime--;
				
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.updateVolume(getVolume(1F));
				audio.keepAlive();
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.boiler", xCoord, yCoord, zCoord, 0.25F, 15F, 1.0F, 20);
	}

	@Override
	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		
		this.power = nbt.getLong("power");
		for(int i = 0; i < 5; i++) tanks[i].readFromNBT(nbt, "" + i);
		this.hasExploded = nbt.getBoolean("exploded");
		this.onFire = nbt.getBoolean("onFire");
		this.isOn = nbt.getBoolean("isOn");
	}
	
	private void refine() {
		Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack> refinery = RefineryRecipes.getRefinery(tanks[0].getTankType());
		if(refinery == null) {
			for(int i = 1; i < 5; i++) tanks[i].setTankType(Fluids.NONE);
			return;
		}
		
		FluidStack[] stacks = new FluidStack[] {refinery.getV(), refinery.getW(), refinery.getX(), refinery.getY()};
		
		for(int i = 0; i < stacks.length; i++) tanks[i + 1].setTankType(stacks[i].type);
		
		if(power < 5 || tanks[0].getFill() < 100) return;

		for(int i = 0; i < stacks.length; i++) {
			if(tanks[i + 1].getFill() + stacks[i].fill > tanks[i + 1].getMaxFill()) {
				return;
			}
		}
		
		this.isOn = true;
		tanks[0].setFill(tanks[0].getFill() - 100);

		for(int i = 0; i < stacks.length; i++) tanks[i + 1].setFill(tanks[i + 1].getFill() + stacks[i].fill);
		
		this.sulfur++;
		
		if(this.sulfur >= maxSulfur) {
			this.sulfur -= maxSulfur;
			
			ItemStack out = refinery.getZ();
			
			if(out != null) {
				
				if(slots[11] == null) {
					slots[11] = out.copy();
				} else {
					
					if(out.getItem() == slots[11].getItem() && out.getItemDamage() == slots[11].getItemDamage() && slots[11].stackSize + out.stackSize <= slots[11].getMaxStackSize()) {
						slots[11].stackSize += out.stackSize;
					}
				}
			}
			
			this.markDirty();
		}

		if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 5);
		this.power -= 5;
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public void setPower(long i) {
		power = i;
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
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[1], tanks[2], tanks[3], tanks[4] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		
		if(this.hasExploded) return;
		
		this.hasExploded = true;
		this.onFire = true;
		this.markChanged();
	}

	@Override
	public void tryExtinguish(World world, int x, int y, int z, EnumExtinguishType type) {
		if(!this.hasExploded || !this.onFire) return;
		
		if(type == EnumExtinguishType.FOAM || type == EnumExtinguishType.CO2) {
			this.onFire = false;
			this.markChanged();
			return;
		}
		
		if(type == EnumExtinguishType.WATER) {
			for(FluidTank tank : tanks) {
				if(tank.getFill() > 0) {
					worldObj.newExplosion(null, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, 5F, true, true);
					return;
				}
			}
		}
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

		repair.add(new OreDictStack(OreDictManager.STEEL.plate(), 8));
		repair.add(new ComparableStack(ModItems.ducttape, 4));
		return repair;
	}

	@Override
	public void repair() {
		this.hasExploded = false;
		this.markChanged();
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tanks[0].getFill() == 0 && tanks[1].getFill() == 0 && tanks[2].getFill() == 0 && tanks[3].getFill() == 0 && tanks[4].getFill() == 0 && !this.hasExploded) return;
		NBTTagCompound data = new NBTTagCompound();
		for(int i = 0; i < 5; i++) this.tanks[i].writeToNBT(data, "" + i);
		data.setBoolean("hasExploded", hasExploded);
		data.setBoolean("onFire", onFire);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		for(int i = 0; i < 5; i++) this.tanks[i].readFromNBT(data, "" + i);
		this.hasExploded = data.getBoolean("hasExploded");
		this.onFire = data.getBoolean("onFire");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineRefinery(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineRefinery(player.inventory, this);
	}

}
