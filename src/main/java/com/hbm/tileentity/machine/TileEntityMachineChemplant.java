package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineChemplant;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineChemplant extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidAcceptor, IFluidStandardTransceiver, IGUIProvider {

	public long power;
	public static final long maxPower = 100000;
	public int progress;
	public int maxProgress = 100;
	public boolean isProgressing;
	
	private AudioWrapper audio;
	
	public FluidTank[] tanks;
	
	//upgraded stats
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineChemplant() {
		super(21);
		/*
		 * 0 Battery
		 * 1-3 Upgrades
		 * 4 Schematic
		 * 5-8 Output
		 * 9-10 FOut In
		 * 11-12 FOut Out
		 * 13-16 Input
		 * 17-18 FIn In
		 * 19-20 FIn Out
		 */
		
		tanks = new FluidTank[4];
		for(int i = 0; i < 4; i++) {
			tanks[i] = new FluidTank(Fluids.NONE, 24_000, i);
		}
	}

	@Override
	public String getName() {
		return "container.chemplant";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.speed = 100;
			this.consumption = 100;
			
			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			if(!tanks[0].loadTank(17, 19, slots) && (slots[17] == null || slots[17].getItem() != ModItems.fluid_barrel_infinite)) tanks[0].unloadTank(17, 19, slots);
			if(!tanks[1].loadTank(18, 20, slots) && (slots[18] == null || slots[18].getItem() != ModItems.fluid_barrel_infinite)) tanks[1].unloadTank(18, 20, slots);
			
			tanks[2].unloadTank(9, 11, slots);
			tanks[3].unloadTank(10, 12, slots);
			
			loadItems();
			unloadItems();
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				this.fillFluidInit(tanks[2].getTankType());
				this.fillFluidInit(tanks[3].getTankType());
			}
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				this.updateConnections();
			}
			
			for(DirPos pos : getConPos()) {
				if(tanks[2].getFill() > 0) this.sendFluid(tanks[2], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[3].getFill() > 0) this.sendFluid(tanks[3], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			UpgradeManager.eval(slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			this.speed -= speedLevel * 25;
			this.consumption += speedLevel * 300;
			this.speed += powerLevel * 5;
			this.consumption -= powerLevel * 30;
			this.speed /= (overLevel + 1);
			this.consumption *= (overLevel + 1);
			
			if(this.speed <= 0) {
				this.speed = 1;
			}
			
			if(!canProcess()) {
				this.progress = 0;
			} else {
				isProgressing = true;
				process();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setInteger("maxProgress", this.maxProgress);
			data.setBoolean("isProgressing", isProgressing);
			
			for(int i = 0; i < tanks.length; i++) {
				tanks[i].writeToNBT(data, "t" + i);
			}
			
			this.networkPack(data, 150);
		} else {
			
			if(isProgressing && this.worldObj.getTotalWorldTime() % 3 == 0) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				double x = xCoord + 0.5 + dir.offsetX * 1.125 + rot.offsetX * 0.125;
				double y = yCoord + 3;
				double z = zCoord + 0.5 + dir.offsetZ * 1.125 + rot.offsetZ * 0.125;
				worldObj.spawnParticle("cloud", x, y, z, 0.0, 0.1, 0.0);
			}
			
			float volume = 1;//this.getVolume(2);
			
			if(isProgressing && volume > 0) {
				
				if(audio == null) {
					audio = this.createAudioLoop();
					audio.updateVolume(volume);
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
					audio.updateVolume(volume);
				}
				
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
		return MainRegistry.proxy.getLoopedSound("hbm:block.chemplantOperate", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.maxProgress = nbt.getInteger("maxProgress");
		this.isProgressing = nbt.getBoolean("isProgressing");

		for(int i = 0; i < tanks.length; i++) {
			tanks[i].readFromNBT(nbt, "t" + i);
		}
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
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 3,				yCoord,	zCoord + rot.offsetZ * 3,				rot),
				new DirPos(xCoord - rot.offsetX * 2,				yCoord,	zCoord - rot.offsetZ * 2,				rot.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 3 + dir.offsetX,	yCoord,	zCoord + rot.offsetZ * 3 + dir.offsetZ, rot),
				new DirPos(xCoord - rot.offsetX * 2 + dir.offsetX,	yCoord,	zCoord - rot.offsetZ * 2 + dir.offsetZ, rot.getOpposite())
		};
	}
	
	private boolean canProcess() {
		
		if(slots[4] == null || slots[4].getItem() != ModItems.chemistry_template)
			return false;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[4].getItemDamage());
		
		if(recipe == null)
			return false;
		
		setupTanks(recipe);
		
		if(this.power < this.consumption) return false;
		if(!hasRequiredFluids(recipe)) return false;
		if(!hasSpaceForFluids(recipe)) return false;
		if(!hasRequiredItems(recipe)) return false;
		if(!hasSpaceForItems(recipe)) return false;
		
		return true;
	}
	
	private void setupTanks(ChemRecipe recipe) {
		if(recipe.inputFluids[0] != null) tanks[0].withPressure(recipe.inputFluids[0].pressure).setTankType(recipe.inputFluids[0].type);	else tanks[0].setTankType(Fluids.NONE);
		if(recipe.inputFluids[1] != null) tanks[1].withPressure(recipe.inputFluids[1].pressure).setTankType(recipe.inputFluids[1].type);	else tanks[1].setTankType(Fluids.NONE);
		if(recipe.outputFluids[0] != null) tanks[2].withPressure(recipe.outputFluids[0].pressure).setTankType(recipe.outputFluids[0].type);	else tanks[2].setTankType(Fluids.NONE);
		if(recipe.outputFluids[1] != null) tanks[3].withPressure(recipe.outputFluids[1].pressure).setTankType(recipe.outputFluids[1].type);	else tanks[3].setTankType(Fluids.NONE);
	}
	
	private boolean hasRequiredFluids(ChemRecipe recipe) {
		if(recipe.inputFluids[0] != null && tanks[0].getFill() < recipe.inputFluids[0].fill) return false;
		if(recipe.inputFluids[1] != null && tanks[1].getFill() < recipe.inputFluids[1].fill) return false;
		return true;
	}
	
	private boolean hasSpaceForFluids(ChemRecipe recipe) {
		if(recipe.outputFluids[0] != null && tanks[2].getFill() + recipe.outputFluids[0].fill > tanks[2].getMaxFill()) return false;
		if(recipe.outputFluids[1] != null && tanks[3].getFill() + recipe.outputFluids[1].fill > tanks[3].getMaxFill()) return false;
		return true;
	}
	
	private boolean hasRequiredItems(ChemRecipe recipe) {
		return InventoryUtil.doesArrayHaveIngredients(slots, 13, 16, recipe.inputs);
	}
	
	private boolean hasSpaceForItems(ChemRecipe recipe) {
		return InventoryUtil.doesArrayHaveSpace(slots, 5, 8, recipe.outputs);
	}
	
	private void process() {
		
		this.power -= this.consumption;
		this.progress++;
		
		if(slots[0] != null && slots[0].getItem() == ModItems.meteorite_sword_machined)
			slots[0] = new ItemStack(ModItems.meteorite_sword_treated); //fisfndmoivndlmgindgifgjfdnblfm
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[4].getItemDamage());
		
		this.maxProgress = recipe.getDuration() * this.speed / 100;
		
		if(maxProgress <= 0) maxProgress = 1;
		
		if(this.progress >= this.maxProgress) {
			consumeFluids(recipe);
			produceFluids(recipe);
			consumeItems(recipe);
			produceItems(recipe);
			this.progress = 0;
			this.markDirty();
		}
	}
	
	private void consumeFluids(ChemRecipe recipe) {
		if(recipe.inputFluids[0] != null) tanks[0].setFill(tanks[0].getFill() - recipe.inputFluids[0].fill);
		if(recipe.inputFluids[1] != null) tanks[1].setFill(tanks[1].getFill() - recipe.inputFluids[1].fill);
	}
	
	private void produceFluids(ChemRecipe recipe) {
		if(recipe.outputFluids[0] != null) tanks[2].setFill(tanks[2].getFill() + recipe.outputFluids[0].fill);
		if(recipe.outputFluids[1] != null) tanks[3].setFill(tanks[3].getFill() + recipe.outputFluids[1].fill);
	}
	
	private void consumeItems(ChemRecipe recipe) {
		
		for(AStack in : recipe.inputs) {
			if(in != null)
				InventoryUtil.tryConsumeAStack(slots, 13, 16, in);
		}
	}
	
	private void produceItems(ChemRecipe recipe) {
		
		for(ItemStack out : recipe.outputs) {
			if(out != null)
				InventoryUtil.tryAddItemToInventory(slots, 5, 8, out.copy());
		}
	}
	
	//TODO: move this into a util class
	private void loadItems() {
		
		if(slots[4] == null || slots[4].getItem() != ModItems.chemistry_template)
			return;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(slots[4].getItemDamage());
		
		if(recipe != null) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();

			int x = xCoord - dir.offsetX * 2;
			int z = zCoord - dir.offsetZ * 2;
			
			TileEntity te = worldObj.getTileEntity(x, yCoord, z);
			
			if(te instanceof IInventory) {
				
				IInventory inv = (IInventory) te;
				ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
				
				for(AStack ingredient : recipe.inputs) {
					
					outer:
					while(!InventoryUtil.doesArrayHaveIngredients(slots, 13, 16, ingredient)) {
						
						boolean found = false;
						
						for(int i = 0; i < inv.getSizeInventory(); i++) {
							
							ItemStack stack = inv.getStackInSlot(i);
							if(ingredient.matchesRecipe(stack, true) && (sided == null || sided.canExtractItem(i, stack, 0))) {
								
								for(int j = 13; j <= 16; j++) {
									
									if(slots[j] != null && slots[j].stackSize < slots[j].getMaxStackSize() & InventoryUtil.doesStackDataMatch(slots[j], stack)) {
										inv.decrStackSize(i, 1);
										slots[j].stackSize++;
										continue outer;
									}
								}
								
								for(int j = 13; j <= 16; j++) {
									
									if(slots[j] == null) {
										slots[j] = stack.copy();
										slots[j].stackSize = 1;
										inv.decrStackSize(i, 1);
										continue outer;
									}
								}
							}
						}
						
						if(!found) return;
					}
				}
			}
		}
	}
	
	private void unloadItems() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		int x = xCoord + dir.offsetX * 3 + rot.offsetX;
		int z = zCoord + dir.offsetZ * 3 + rot.offsetZ;
		
		TileEntity te = worldObj.getTileEntity(x, yCoord, z);
		
		if(te instanceof IInventory) {
			
			IInventory inv = (IInventory) te;
			
			for(int i = 5; i <= 8; i++) {
				
				ItemStack out = slots[i];
				
				if(out != null) {
					
					for(int j = 0; j < inv.getSizeInventory(); j++) {
						
						if(!inv.isItemValidForSlot(j, out))
							continue;
							
						ItemStack target = inv.getStackInSlot(j);
						
						if(InventoryUtil.doesStackDataMatch(out, target) && target.stackSize < target.getMaxStackSize()) {
							this.decrStackSize(i, 1);
							target.stackSize++;
							return;
						}
					}
					
					for(int j = 0; j < inv.getSizeInventory(); j++) {
						
						if(!inv.isItemValidForSlot(j, out))
							continue;
						
						if(inv.getStackInSlot(j) == null && inv.isItemValidForSlot(j, out)) {
							ItemStack copy = out.copy();
							copy.stackSize = 1;
							inv.setInventorySlotContents(j, copy);
							this.decrStackSize(i, 1);
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index >= 0 && index < tanks.length) tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
				return;
			}
		}
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index >= 0 && index < tanks.length) tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(FluidTank tank : tanks) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		for(int i = 0; i < 2; i++) {
			if(tanks[i].getTankType() == type) {
				return tanks[i].getMaxFill();
			}
		}
		
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		/*
		 *  ####
		 * X####X
		 * X##O#X
		 *  ####
		 */
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		fillFluid(xCoord + rot.offsetX * 3,					yCoord,	zCoord + rot.offsetZ * 3,				this.getTact(), type);
		fillFluid(xCoord - rot.offsetX * 2,					yCoord,	zCoord - rot.offsetZ * 2,				this.getTact(), type);
		fillFluid(xCoord + rot.offsetX * 3 + dir.offsetX,	yCoord,	zCoord + rot.offsetZ * 3 + dir.offsetZ,	this.getTact(), type);
		fillFluid(xCoord - rot.offsetX * 2 + dir.offsetX,	yCoord,	zCoord - rot.offsetZ * 2 + dir.offsetZ,	this.getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}
	
	List<IFluidAcceptor>[] lists = new List[] {
		new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList()
	};

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		
		for(int i = 0; i < tanks.length; i++) {
			if(tanks[i].getTankType() == type) {
				return lists[i];
			}
		}
		
		return new ArrayList();
	}

	@Override
	public void clearFluidList(FluidType type) {
		
		for(int i = 0; i < tanks.length; i++) {
			if(tanks[i].getTankType() == type) {
				lists[i].clear();
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].readFromNBT(nbt, "t" + i);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("progress", progress);
		
		for(int i = 0; i < tanks.length; i++) {
			tanks[i].writeToNBT(nbt, "t" + i);
		}
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
					yCoord + 4,
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
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[2], tanks[3]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0], tanks[1]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineChemplant(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineChemplant(player.inventory, this);
	}
}
