package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.CrystallizerRecipes;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineMiningLaser extends TileEntityMachineBase implements IConsumer, IFluidSource {
	
	public long power;
	public int age = 0;
	public static final long maxPower = 100000000;
	public static final int consumption = 10000;
	public FluidTank tank;
	public List<IFluidAcceptor> list = new ArrayList();

	public boolean isOn;
	public int targetX;
	public int targetY;
	public int targetZ;
	public int lastTargetX;
	public int lastTargetY;
	public int lastTargetZ;
	public boolean beam;
	boolean lock = false;
	double breakProgress;

	public TileEntityMachineMiningLaser() {
		
		//slot 0: battery
		//slots 1 - 8: upgrades
		//slots 9 - 29: output
		super(30);
		tank = new FluidTank(FluidType.OIL, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.miningLaser";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				fillFluidInit(tank.getTankType());
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tank.updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
			
			//reset progress if the position changes
			if(lastTargetX != targetX ||
					lastTargetY != targetY ||
					lastTargetZ != targetZ)
				breakProgress = 0;
			
			//set last positions for interpolation and the like
			lastTargetX = targetX;
			lastTargetY = targetY;
			lastTargetZ = targetZ;
			
			double clientBreakProgress = 0;
			
			if(isOn) {
				
				int cycles = getOverdrive();
				int speed = getSpeed();
				int range = getRange();
				int fortune = getFortune();
				int consumption = getConsumption() * speed;
				
				for(int i = 0; i < cycles; i++) {
					
					if(power < consumption) {
						beam = false;
						break;
					}
					
					power -= consumption;
					
					if(targetY <= 0)
						targetY = yCoord - 2;
					
					scan(range);
					
					
					Block block = worldObj.getBlock(targetX, targetY, targetZ);
					
					if(block.getMaterial().isLiquid()) {
						worldObj.setBlockToAir(targetX, targetY, targetZ);
						buildDam();
						continue;
					}
					
					if(beam && canBreak(block, targetX, targetY, targetZ)) {
						
						breakProgress += getBreakSpeed(speed);
						clientBreakProgress = Math.min(breakProgress, 1);
						
						if(breakProgress < 1) {
							worldObj.destroyBlockInWorldPartially(-1, targetX, targetY, targetZ, (int) Math.floor(breakProgress * 10));
						} else {
							breakBlock(fortune);
							buildDam();
						}
					}
				}
			} else {
				targetY = yCoord - 2;
				beam = false;
			}

			this.tryFillContainer(xCoord + 2, yCoord, zCoord);
			this.tryFillContainer(xCoord - 2, yCoord, zCoord);
			this.tryFillContainer(xCoord, yCoord, zCoord + 2);
			this.tryFillContainer(xCoord, yCoord, zCoord - 2);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("lastX", lastTargetX);
			data.setInteger("lastY", lastTargetY);
			data.setInteger("lastZ", lastTargetZ);
			data.setInteger("x", targetX);
			data.setInteger("y", targetY);
			data.setInteger("z", targetZ);
			data.setBoolean("beam", beam);
			data.setBoolean("isOn", isOn);
			data.setDouble("progress", clientBreakProgress);
			
			this.networkPack(data, 250);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getLong("power");
		this.lastTargetX = data.getInteger("lastX");
		this.lastTargetY = data.getInteger("lastY");
		this.lastTargetZ = data.getInteger("lastZ");
		this.targetX = data.getInteger("x");
		this.targetY = data.getInteger("y");
		this.targetZ = data.getInteger("z");
		this.beam = data.getBoolean("beam");
		this.isOn = data.getBoolean("isOn");
		this.breakProgress = data.getDouble("progress");
	}
	
	private void buildDam() {

		if(worldObj.getBlock(targetX + 1, targetY, targetZ).getMaterial().isLiquid())
			worldObj.setBlock(targetX + 1, targetY, targetZ, ModBlocks.barricade);
		if(worldObj.getBlock(targetX - 1, targetY, targetZ).getMaterial().isLiquid())
			worldObj.setBlock(targetX - 1, targetY, targetZ, ModBlocks.barricade);
		if(worldObj.getBlock(targetX, targetY, targetZ + 1).getMaterial().isLiquid())
			worldObj.setBlock(targetX, targetY, targetZ + 1, ModBlocks.barricade);
		if(worldObj.getBlock(targetX, targetY, targetZ - 1).getMaterial().isLiquid())
			worldObj.setBlock(targetX, targetY, targetZ - 1, ModBlocks.barricade);
	}
	
	private void tryFillContainer(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		if(b != Blocks.chest && b != Blocks.trapped_chest && b != ModBlocks.crate_iron &&
				b != ModBlocks.crate_steel && b != ModBlocks.safe && b != Blocks.hopper)
			return;
		
		IInventory inventory = (IInventory)worldObj.getTileEntity(x, y, z);
		if(inventory == null)
			return;
		
		for(int i = 9; i <= 29; i++) {
			
			if(slots[i] != null) {
				int prev = slots[i].stackSize;
				slots[i] = InventoryUtil.tryAddItemToInventory(inventory, 0, inventory.getSizeInventory() - 1, slots[i]);
				
				if(slots[i] == null || slots[i].stackSize < prev)
					return;
			}
		}
	}
	
	private void breakBlock(int fortune) {
		
		Block b = worldObj.getBlock(targetX, targetY, targetZ);
		int meta = worldObj.getBlockMetadata(targetX, targetY, targetZ);
		boolean normal = true;
		
		if(b == Blocks.lit_redstone_ore)
			b = Blocks.redstone_ore;
		
		ItemStack stack = new ItemStack(b, 1, meta);
		
		if(stack != null && stack.getItem() != null) {
			if(hasCrystallizer()) {

				ItemStack result = CrystallizerRecipes.getOutput(stack);
				if(result != null && result.getItem() != ModItems.scrap) {
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, targetX + 0.5, targetY + 0.5, targetZ + 0.5, result.copy()));
					normal = false;
				}
				
			} else if(hasCentrifuge()) {
				
				ItemStack[] result = CentrifugeRecipes.getOutput(stack);
				if(result != null) {
					for(ItemStack sta : result) {
						
						if(sta != null) {
							worldObj.spawnEntityInWorld(new EntityItem(worldObj, targetX + 0.5, targetY + 0.5, targetZ + 0.5, sta.copy()));
							normal = false;
						}
					}
				}
				
			} else if(hasShredder()) {
				
				ItemStack result = ShredderRecipes.getShredderResult(stack);
				if(result != null && result.getItem() != ModItems.scrap) {
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, targetX + 0.5, targetY + 0.5, targetZ + 0.5, result.copy()));
					normal = false;
				}
				
			} else if(hasSmelter()) {
				
				ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(stack);
				if(result != null) {
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, targetX + 0.5, targetY + 0.5, targetZ + 0.5, result.copy()));
					normal = false;
				}
			}
		}
		
		if(normal)
			b.dropBlockAsItem(worldObj, targetX, targetY, targetZ, meta, fortune);
		worldObj.func_147480_a(targetX, targetY, targetZ, false);
		suckDrops();

		if(doesScream()) {
			worldObj.playSoundEffect(targetX + 0.5, targetY + 0.5, targetZ + 0.5, "hbm:block.screm", 2000.0F, 1.0F);
		}
		
		breakProgress = 0;
	}
	
	private static final Set<Item> bad = Sets.newHashSet(new Item[] {
			Item.getItemFromBlock(Blocks.dirt),
			Item.getItemFromBlock(Blocks.stone),
			Item.getItemFromBlock(Blocks.cobblestone),
			Item.getItemFromBlock(Blocks.sand),
			Item.getItemFromBlock(Blocks.sandstone),
			Item.getItemFromBlock(Blocks.gravel),
			Item.getItemFromBlock(ModBlocks.stone_gneiss),
			Items.flint,
			Items.snowball,
			Items.wheat_seeds
			});
	
	//hahahahahahahaha he said "suck"
	private void suckDrops() {
		
		int rangeHor = 3;
		int rangeVer = 1;
		boolean nullifier = hasNullifier();
		
		List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
				targetX + 0.5 - rangeHor,
				targetY + 0.5 - rangeVer,
				targetZ + 0.5 - rangeHor,
				targetX + 0.5 + rangeHor,
				targetY + 0.5 + rangeVer,
				targetZ + 0.5 + rangeHor
				));
		
		for(EntityItem item : items) {
			
			if(nullifier && bad.contains(item.getEntityItem().getItem())) {
				item.setDead();
				continue;
			}
			
			if(item.getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.ore_oil)) {
				
				tank.setTankType(FluidType.OIL); //just to be sure
				
				tank.setFill(tank.getFill() + 500);
				if(tank.getFill() > tank.getMaxFill())
					tank.setFill(tank.getMaxFill());
				
				item.setDead();
				continue;
			}
			
			ItemStack stack = InventoryUtil.tryAddItemToInventory(slots, 9, 29, item.getEntityItem().copy());
			
			if(stack == null)
				item.setDead();
			else
				item.setEntityItemStack(stack.copy()); //copy is not necessary but i'm paranoid due to the kerfuffle of the old drill
		}
		
		List<EntityLivingBase> mobs = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
				targetX + 0.5 - 1,
				targetY + 0.5 - 1,
				targetZ + 0.5 - 1,
				targetX + 0.5 + 1,
				targetY + 0.5 + 1,
				targetZ + 0.5 + 1
				));
		
		for(EntityLivingBase mob : mobs) {
			mob.setFire(5);
		}
	}
	
	public double getBreakSpeed(int speed) {
		
		float hardness = worldObj.getBlock(targetX, targetY, targetZ).getBlockHardness(worldObj, targetX, targetY, targetZ) * 15 / speed;
		
		if(hardness == 0)
			return 1;
		
		return 1 / hardness;
	}
	
	public void scan(int range) {
		
		for(int x = -range; x <= range; x++) {
			for(int z = -range; z <= range; z++) {
				
				if(worldObj.getBlock(x + xCoord, targetY, z + zCoord).getMaterial().isLiquid()) {
					/*targetX = x + xCoord;
					targetZ = z + zCoord;
					worldObj.func_147480_a(x + xCoord, targetY, z + zCoord, false);
					beam = true;*/
					
					continue;
				}
				
				if(canBreak(worldObj.getBlock(x + xCoord, targetY, z + zCoord), x + xCoord, targetY, z + zCoord)) {
					targetX = x + xCoord;
					targetZ = z + zCoord;
					beam = true;
					return;
				}
			}
		}
		
		beam = false;
		targetY--;
	}
	
	private boolean canBreak(Block block, int x, int y, int z) {
		return block != Blocks.air && block.getBlockHardness(worldObj, x, y, z) >= 0 && !block.getMaterial().isLiquid() && block != Blocks.bedrock;
	}
	
	public int getOverdrive() {
		
		int speed = 1;
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_overdrive_1)
					speed += 1;
				else if(slots[i].getItem() == ModItems.upgrade_overdrive_2)
					speed += 2;
				else if(slots[i].getItem() == ModItems.upgrade_overdrive_3)
					speed += 3;
			}
		}
		
		return Math.min(speed, 4);
	}
	
	public int getSpeed() {
		
		int speed = 1;
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_speed_1)
					speed += 2;
				else if(slots[i].getItem() == ModItems.upgrade_speed_2)
					speed += 4;
				else if(slots[i].getItem() == ModItems.upgrade_speed_3)
					speed += 6;
			}
		}
		
		return Math.min(speed, 13);
	}
	
	public int getRange() {
		
		int range = 1;
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_effect_1)
					range += 2;
				else if(slots[i].getItem() == ModItems.upgrade_effect_2)
					range += 4;
				else if(slots[i].getItem() == ModItems.upgrade_effect_3)
					range += 6;
			}
		}
		
		return Math.min(range, 25);
	}
	
	public int getFortune() {
		
		int fortune = 0;
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_fortune_1)
					fortune += 1;
				else if(slots[i].getItem() == ModItems.upgrade_fortune_2)
					fortune += 2;
				else if(slots[i].getItem() == ModItems.upgrade_fortune_3)
					fortune += 3;
			}
		}
		
		return Math.min(fortune, 3);
	}
	
	public boolean hasNullifier() {
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_nullifier)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSmelter() {
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_smelter)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasShredder() {
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_shredder)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasCentrifuge() {
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_centrifuge)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasCrystallizer() {
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_crystallizer)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean doesScream() {
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_screm)
					return true;
			}
		}
		
		return false;
	}
	
	public int getConsumption() {
		
		int consumption = this.consumption;
		
		return consumption;
	}
	
	public int getWidth() {
		
		return 1 + getRange() * 2;
	}

	public int getPowerScaled(int i) {
		return (int)((power * i) / maxPower);
	}

	public int getProgressScaled(int i) {
		return (int) (breakProgress * i);
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
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i >= 9 && i <= 29;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {
		
		int[] slots = new int[21];
		
		for(int i = 0; i < 21; i++) {
			slots[i] = i + 9;
		}
		
		return slots;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 1 && i <= 8 && stack.getItem() instanceof ItemMachineUpgrade)
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
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
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == FluidType.OIL)
			tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return new ArrayList() {{ add(tank); }};
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == FluidType.OIL)
			return tank.getFill();
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {

		fillFluid(xCoord + 2, yCoord, zCoord, this.getTact(), type);
		fillFluid(xCoord - 2, yCoord, zCoord, this.getTact(), type);
		fillFluid(xCoord, yCoord, zCoord + 2, this.getTact(), type);
		fillFluid(xCoord, yCoord, zCoord - 2, this.getTact(), type);
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
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		tank.readFromNBT(nbt, "oil");
		isOn = nbt.getBoolean("isOn");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		tank.writeToNBT(nbt, "oil");
		nbt.setBoolean("isOn", isOn);
	}
}
