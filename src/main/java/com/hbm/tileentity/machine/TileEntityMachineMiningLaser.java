package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMiningLaser;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMiningLaser;
import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.inventory.recipes.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.InventoryUtil;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardSender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineMiningLaser extends TileEntityMachineBase implements IEnergyReceiverMK2, IMiningDrill, IFluidStandardSender, IGUIProvider, IUpgradeInfoProvider {

	public long power;
	public int age = 0;
	public static final long maxPower = 100000000;
	public static final int consumption = 10000;
	public FluidTank tank;

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

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineMiningLaser() {

		//slot 0: battery
		//slots 1 - 8: upgrades
		//slots 9 - 29: output
		super(30);
		tank = new FluidTank(Fluids.OIL, 64_000);
	}

	@Override
	public String getName() {
		return "container.miningLaser";
	}

	private double clientBreakProgress;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.updateConnections();

			this.sendFluid(tank, worldObj, xCoord + 2, yCoord, zCoord, Library.POS_X);
			this.sendFluid(tank, worldObj, xCoord - 2, yCoord, zCoord, Library.NEG_X);
			this.sendFluid(tank, worldObj, xCoord, yCoord + 2, zCoord, Library.POS_Z);
			this.sendFluid(tank, worldObj, xCoord, yCoord - 2, zCoord, Library.NEG_Z);

			power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			//reset progress if the position changes
			if(lastTargetX != targetX ||
					lastTargetY != targetY ||
					lastTargetZ != targetZ)
				breakProgress = 0;

			//set last positions for interpolation and the like
			lastTargetX = targetX;
			lastTargetY = targetY;
			lastTargetZ = targetZ;

			if(isOn) {

				upgradeManager.checkSlots(this, slots, 1, 8);
				int cycles = 1 + upgradeManager.getLevel(UpgradeType.OVERDRIVE);
				int speed = 1 + upgradeManager.getLevel(UpgradeType.SPEED);
				int range = 1 + upgradeManager.getLevel(UpgradeType.EFFECT) * 2;
				int fortune = upgradeManager.getLevel(UpgradeType.FORTUNE);
				int consumption = this.consumption
						- (this.consumption * upgradeManager.getLevel(UpgradeType.POWER) / 16)
						+ (this.consumption * upgradeManager.getLevel(UpgradeType.SPEED) / 16);

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

			this.networkPackNT(250);
		}
	}

	private void updateConnections() {
		this.trySubscribe(worldObj, xCoord, yCoord + 2, zCoord, ForgeDirection.UP);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(this.power);
		buf.writeInt(this.lastTargetX);
		buf.writeInt(this.lastTargetY);
		buf.writeInt(this.lastTargetZ);
		buf.writeInt(this.targetX);
		buf.writeInt(this.targetY);
		buf.writeInt(this.targetZ);
		buf.writeBoolean(this.beam);
		buf.writeBoolean(this.isOn);
		buf.writeDouble(this.clientBreakProgress);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.lastTargetX = buf.readInt();
		this.lastTargetY = buf.readInt();
		this.lastTargetZ = buf.readInt();
		this.targetX = buf.readInt();
		this.targetY = buf.readInt();
		this.targetZ = buf.readInt();
		this.beam = buf.readBoolean();
		this.isOn = buf.readBoolean();
		this.breakProgress = buf.readDouble();
		tank.deserialize(buf);
	}

	private void buildDam() {

		if(worldObj.getBlock(targetX + 1, targetY, targetZ).getMaterial().isLiquid()) worldObj.setBlock(targetX + 1, targetY, targetZ, ModBlocks.barricade);
		if(worldObj.getBlock(targetX - 1, targetY, targetZ).getMaterial().isLiquid()) worldObj.setBlock(targetX - 1, targetY, targetZ, ModBlocks.barricade);
		if(worldObj.getBlock(targetX, targetY, targetZ + 1).getMaterial().isLiquid()) worldObj.setBlock(targetX, targetY, targetZ + 1, ModBlocks.barricade);
		if(worldObj.getBlock(targetX, targetY, targetZ - 1).getMaterial().isLiquid()) worldObj.setBlock(targetX, targetY, targetZ - 1, ModBlocks.barricade);
	}

	private void tryFillContainer(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);
		if(b != Blocks.chest && b != Blocks.trapped_chest && b != ModBlocks.crate_iron && b != ModBlocks.crate_desh &&
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
		boolean doesBreak = true;

		if(b == Blocks.lit_redstone_ore)
			b = Blocks.redstone_ore;

		ItemStack stack = new ItemStack(b, 1, meta);

		if(stack != null && stack.getItem() != null) {
			if(hasCrystallizer()) {

				CrystallizerRecipe result = CrystallizerRecipes.getOutput(stack, Fluids.PEROXIDE);
				if(result == null) result = CrystallizerRecipes.getOutput(stack, Fluids.SULFURIC_ACID);

				if(result != null) {
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, targetX + 0.5, targetY + 0.5, targetZ + 0.5, result.output.copy()));
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

		if(normal && b instanceof IDrillInteraction) {
			IDrillInteraction in = (IDrillInteraction) b;
			ItemStack drop = in.extractResource(worldObj, targetX, targetY, targetZ, meta, this);

			if(drop != null) {
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, targetX + 0.5, targetY + 0.5, targetZ + 0.5, drop.copy()));
			}

			doesBreak = in.canBreak(worldObj, targetX, targetY, targetZ, meta, this);
		}

		if(doesBreak) {
			if(normal) b.dropBlockAsItem(worldObj, targetX, targetY, targetZ, meta, fortune);
			worldObj.func_147480_a(targetX, targetY, targetZ, false);
		}

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
			Item.getItemFromBlock(ModBlocks.basalt),
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

			if(item.isDead) continue;

			if(nullifier && bad.contains(item.getEntityItem().getItem())) {
				item.setDead();
				continue;
			}

			if(item.getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.ore_oil)) {

				tank.setTankType(Fluids.OIL); //just to be sure

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
		return !block.isAir(worldObj, x, y, z) && block.getBlockHardness(worldObj, x, y, z) >= 0 && !block.getMaterial().isLiquid() && block != Blocks.bedrock;
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
		return this.consumption;
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

	@Override
	public DrillType getDrillTier() {
		return DrillType.HITECH;
	}

	@Override
	public int getDrillRating() {
		return 100;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMiningLaser(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMiningLaser(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE || type == UpgradeType.EFFECT || type == UpgradeType.FORTUNE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_mining_laser));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (100 - 100 / (level + 1)) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (100 * level / 16) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (100 * level / 16) + "%"));
		}
		if(type == UpgradeType.EFFECT) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_RANGE, "+" + (2 * level) + "m"));
		}
		if(type == UpgradeType.FORTUNE) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_FORTUNE, "+" + level));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 12);
		upgrades.put(UpgradeType.POWER, 12);
		upgrades.put(UpgradeType.EFFECT, 12);
		upgrades.put(UpgradeType.FORTUNE, 3);
		upgrades.put(UpgradeType.OVERDRIVE, 9);
		return upgrades;
	}
}
