package com.hbm.tileentity.machine;

import java.lang.reflect.Method;
import java.util.*;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.blocks.network.CraneInserter;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineExcavator;
import com.hbm.inventory.recipes.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrillbit;
import com.hbm.items.machine.ItemDrillbit.EnumDrillType;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.special.ItemBedrockOreBase;
import com.hbm.lib.Library;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Compat;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.InventoryUtil;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineExcavator extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiver, IControlReceiver, IGUIProvider, IUpgradeInfoProvider, IFluidCopiable {

	public static final long maxPower = 1_000_000;
	public long power;
	public boolean operational = false;

	public boolean enableDrill = false;
	public boolean enableCrusher = false;
	public boolean enableWalling = false;
	public boolean enableVeinMiner = false;
	public boolean enableSilkTouch = false;

	protected int ticksWorked = 0;
	protected int targetDepth = 0; //0 is the first block below null position
	protected boolean bedrockDrilling = false;

	public float drillRotation = 0F;
	public float prevDrillRotation = 0F;
	public float drillExtension = 0F;
	public float prevDrillExtension = 0F;
	public float crusherRotation = 0F;
	public float prevCrusherRotation = 0F;
	public int chuteTimer = 0;

	public double speed = 1.0D;
	public final long baseConsumption = 10_000L;
	public long consumption = baseConsumption;

	public FluidTank tank;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineExcavator() {
		super(14);
		this.tank = new FluidTank(Fluids.NONE, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineExcavator";
	}

	@Override
	public void updateEntity() {

		//needs to happen on client too for GUI rendering
		upgradeManager.checkSlots(this, slots, 2, 3);
		int speedLevel = upgradeManager.getLevel(UpgradeType.SPEED);
		int powerLevel = upgradeManager.getLevel(UpgradeType.POWER);

		consumption = baseConsumption * (1 + speedLevel);
		consumption /= (1 + powerLevel);

		if(!worldObj.isRemote) {

			this.tank.setType(1, slots);

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				tryEjectBuffer();

				for(DirPos pos : getConPos()) {
					this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}

			if(chuteTimer > 0) chuteTimer--;

			this.power = Library.chargeTEFromItems(slots, 0, this.getPower(), this.getMaxPower());
			this.operational = false;
			int radiusLevel = upgradeManager.getLevel(UpgradeType.EFFECT);

			EnumDrillType type = this.getInstalledDrill();
			if(this.enableDrill && type != null && this.power >= this.getPowerConsumption()) {

				operational = true;
				this.power -= this.getPowerConsumption();

				this.speed = type.speed;
				this.speed *= (1 + speedLevel / 2D);

				int maxDepth = this.yCoord - 4;

				if((bedrockDrilling || targetDepth <= maxDepth) && tryDrill(1 + radiusLevel * 2)) {
					targetDepth++;

					if(targetDepth > maxDepth) {
						this.enableDrill = false;
					}
				}
			} else {
				this.targetDepth = 0;
			}

			this.networkPackNT(150);

		} else {

			this.prevDrillExtension = this.drillExtension;

			if(this.drillExtension != this.targetDepth) {
				float diff = Math.abs(this.drillExtension - this.targetDepth);
				float speed = Math.max(0.15F, diff / 10F);

				if(diff <= speed) {
					this.drillExtension = this.targetDepth;
				} else {
					float sig = Math.signum(this.drillExtension - this.targetDepth);
					this.drillExtension -= sig * speed;
				}
			}

			this.prevDrillRotation = this.drillRotation;
			this.prevCrusherRotation = this.crusherRotation;

			if(this.operational) {
				this.drillRotation += 15F;

				if(this.enableCrusher) {
					this.crusherRotation += 15F;
				}
			}

			if(this.drillRotation >= 360F) {
				this.drillRotation -= 360F;
				this.prevDrillRotation -= 360F;
			}

			if(this.crusherRotation >= 360F) {
				this.crusherRotation -= 360F;
				this.prevCrusherRotation -= 360F;
			}
		}
	}

	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4 + rot.offsetX, yCoord + 1, zCoord + dir.offsetZ * 4 + rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 4 - rot.offsetX, yCoord + 1, zCoord + dir.offsetZ * 4 - rot.offsetZ, dir),
				new DirPos(xCoord + rot.offsetX * 4, yCoord + 1, zCoord + rot.offsetZ * 4, rot),
				new DirPos(xCoord - rot.offsetX * 4, yCoord + 1, zCoord - rot.offsetZ * 4, rot.getOpposite())
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(enableDrill);
		buf.writeBoolean(enableCrusher);
		buf.writeBoolean(enableWalling);
		buf.writeBoolean(enableVeinMiner);
		buf.writeBoolean(enableSilkTouch);
		buf.writeBoolean(operational);
		buf.writeInt(targetDepth);
		buf.writeInt(chuteTimer);
		buf.writeLong(power);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		enableDrill = buf.readBoolean();
		enableCrusher = buf.readBoolean();
		enableWalling = buf.readBoolean();
		enableVeinMiner = buf.readBoolean();
		enableSilkTouch = buf.readBoolean();
		operational = buf.readBoolean();
		targetDepth = buf.readInt();
		chuteTimer = buf.readInt();
		power = buf.readLong();
		tank.deserialize(buf);
	}

	protected int getY() {
		return yCoord - targetDepth - 4;
	}

	/** Works outwards and tries to break a ring, returns true if all rings are broken (or ignorable) and the drill should extend. */
	protected boolean tryDrill(int radius) {
		int y = getY();

		if(targetDepth == 0 || y == 0) {
			radius = 1;
		}

		for(int ring = 1; ring <= radius; ring++) {

			boolean ignoreAll = true;
			float combinedHardness = 0F;
			BlockPos bedrockOre = null;
			bedrockDrilling = false;

			for(int x = xCoord - ring; x <= xCoord + ring; x++) {
				for(int z = zCoord - ring; z <= zCoord + ring; z++) {

					/* Process blocks either if we are in the inner ring (1 = 3x3) or if the target block is on the outer edge */
					if(ring == 1 || (x == xCoord - ring || x == xCoord + ring || z == zCoord - ring || z == zCoord + ring)) {

						Block b = worldObj.getBlock(x, y, z);

						if(b == ModBlocks.ore_bedrock) {
							combinedHardness = 5 * 60 * 20;
							bedrockOre = new BlockPos(x, y, z);
							bedrockDrilling = true;
							enableCrusher = false;
							ignoreAll = false;
							break;
						}

						if(shouldIgnoreBlock(b, x, y ,z)) continue;

						ignoreAll = false;

						combinedHardness += b.getBlockHardness(worldObj, x, y, z);
					}
				}
			}

			if(!ignoreAll) {
				ticksWorked++;

				int ticksToWork = (int) Math.ceil(combinedHardness / this.speed);

				if(ticksWorked >= ticksToWork) {

					if(bedrockOre == null) {
						breakBlocks(ring);
						buildWall(ring + 1, ring == radius && this.enableWalling);
						if(ring == radius) mineOuterOres(ring + 1);
						tryCollect(radius + 1);
					} else {
						collectBedrock(bedrockOre);
					}
					ticksWorked = 0;
				}

				return false;
			} else {
				tryCollect(radius + 1);
			}
		}

		buildWall(radius + 1, this.enableWalling);
		ticksWorked = 0;
		return true;
	}

	protected void collectBedrock(BlockPos pos) {
		TileEntity oreTile = Compat.getTileStandard(worldObj, pos.getX(), pos.getY(), pos.getZ());

		if(oreTile instanceof TileEntityBedrockOre) {
			TileEntityBedrockOre ore = (TileEntityBedrockOre) oreTile;

			if(ore.resource == null) return;
			if(ore.tier > this.getInstalledDrill().tier) return;
			if(ore.acidRequirement != null) {

				if(ore.acidRequirement.type != tank.getTankType() || ore.acidRequirement.fill > tank.getFill()) return;

				tank.setFill(tank.getFill() - ore.acidRequirement.fill);
			}

			ItemStack stack = ore.resource.copy();
			List<ItemStack> stacks = new ArrayList();
			stacks.add(stack);

			if(stack.getItem() == ModItems.bedrock_ore_base) {
				ItemBedrockOreBase.setOreAmount(stack, pos.getX(), pos.getZ());
			}

			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);

			int x = xCoord + dir.offsetX * 4;
			int y = yCoord - 3;
			int z = zCoord + dir.offsetZ * 4;

			/* try to insert into a valid container */
			TileEntity tile = worldObj.getTileEntity(x, y, z);
			if(tile instanceof IInventory) {
				supplyContainer((IInventory) tile, stacks, dir.getOpposite());
			}

			if(stack.stackSize <= 0) return;

			/* try to place on conveyor belt */
			Block b = worldObj.getBlock(x, y, z);
			if(b instanceof IConveyorBelt) {
				supplyConveyor((IConveyorBelt) b, stacks, x, y, z);
			}

			if(stack.stackSize <= 0) return;

			for(int i = 5; i < 14; i++) {

				if(slots[i] != null && slots[i].stackSize < slots[i].getMaxStackSize() && stack.isItemEqual(slots[i]) && ItemStack.areItemStackTagsEqual(stack, slots[i])) {
					int toAdd = Math.min(slots[i].getMaxStackSize() - slots[i].stackSize, stack.stackSize);
					slots[i].stackSize += toAdd;
					stack.stackSize -= toAdd;

					chuteTimer = 40;

					if(stack.stackSize <= 0) {
						return;
					}
				}
			}

			/* add leftovers to empty slots */
			for(int i = 5; i < 14; i++) {

				if(slots[i] == null) {

					chuteTimer = 40;

					slots[i] = stack.copy();
					return;
				}
			}
		}
	}

	/** breaks and drops all blocks in the specified ring */
	protected void breakBlocks(int ring) {
		int y = getY();

		for(int x = xCoord - ring; x <= xCoord + ring; x++) {
			for(int z = zCoord - ring; z <= zCoord + ring; z++) {

				if(ring == 1 || (x == xCoord - ring || x == xCoord + ring || z == zCoord - ring || z == zCoord + ring)) {

					Block b = worldObj.getBlock(x, y, z);

					if(!this.shouldIgnoreBlock(b, x, y, z)) {
						tryMineAtLocation(x, y, z);
					}
				}
			}
		}
	}

	public void tryMineAtLocation(int x ,int y, int z) {

		Block b = worldObj.getBlock(x, y, z);

		if(this.enableVeinMiner && this.getInstalledDrill().vein) {

			if(isOre(x, y, z, b)) {
				minX = x;
				minY = y;
				minZ = z;
				maxX = x;
				maxY = y;
				maxZ = z;
				breakRecursively(x, y, z, 10);
				recursionBrake.clear();

				/* move all excavated items to the last drillable position which is also within collection range */
				List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1));
				for(EntityItem item : items) item.setPosition(x + 0.5, y + 0.5, z + 0.5);

				return;
			}
		}
		breakSingleBlock(b, x, y, z);
	}

	protected boolean isOre(int x ,int y, int z, Block b) {

		/* doing this isn't terribly accurate but just for figuring out if there's OD it works */
		Item blockItem = Item.getItemFromBlock(b);

		if(blockItem != null) {
			List<String> names = ItemStackUtil.getOreDictNames(new ItemStack(blockItem));

			for(String name : names) {
				if(name.startsWith("ore")) {
					return true;
				}
			}
		}

		return false;
	}

	private HashSet<BlockPos> recursionBrake = new HashSet();
	private int minX = 0, minY = 0, minZ = 0, maxX = 0, maxY = 0, maxZ = 0;
	protected void breakRecursively(int x ,int y, int z, int depth) {

		if(depth < 0) return;
		BlockPos pos = new BlockPos(x, y, z);
		if(recursionBrake.contains(pos)) return;
		recursionBrake.add(pos);

		Block b = worldObj.getBlock(x, y, z);

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int ix = x + dir.offsetX;
			int iy = y + dir.offsetY;
			int iz = z + dir.offsetZ;

			if(worldObj.getBlock(ix, iy, iz) == b) {
				breakRecursively(ix, iy, iz, depth - 1);
			}
		}

		breakSingleBlock(b, x, y, z);

		if(x < minX) minX = x;
		if(x > maxX) maxX = x;
		if(y < minY) minY = y;
		if(y > maxY) maxY = y;
		if(z < minZ) minZ = z;
		if(z > maxZ) maxZ = z;

		if(this.enableWalling) {
			worldObj.setBlock(x, y, z, ModBlocks.barricade);
		}
	}

	protected void breakSingleBlock(Block b, int x ,int y, int z) {

		List<ItemStack> items = b.getDrops(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), this.getFortuneLevel());

		if(this.canSilkTouch()) {

			try {
				Method createStackedBlock = ReflectionHelper.findMethod(Block.class, b, new String[] {"createStackedBlock", "func_149644_j"}, int.class);
				ItemStack result = (ItemStack) createStackedBlock.invoke(b, worldObj.getBlockMetadata(x, y, z));

				if(result != null) {
					items.clear();
					items.add(result.copy());
				}
			} catch(Exception ex) { }
		}

		if(this.enableCrusher) {

			List<ItemStack> list = new ArrayList();

			for(ItemStack stack : items) {
				ItemStack crushed = ShredderRecipes.getShredderResult(stack).copy();

				if(crushed.getItem() == ModItems.scrap || crushed.getItem() == ModItems.dust) {
					list.add(stack);
				} else {
					crushed.stackSize *= stack.stackSize;
					list.add(crushed);
				}
			}

			items = list;
		}

		if(b == ModBlocks.barricade)
			items.clear();

		for(ItemStack item : items) {
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, x + 0.5, y + 0.5, z + 0.5, item));
		}

		worldObj.func_147480_a(x, y, z, false);
	}

	/** builds a wall along the specified ring, replacing fluid blocks. if wallEverything is set, it will also wall off replacable blocks like air or grass */
	protected void buildWall(int ring, boolean wallEverything) {
		int y = getY();

		for(int x = xCoord - ring; x <= xCoord + ring; x++) {
			for(int z = zCoord - ring; z <= zCoord + ring; z++) {

				Block b = worldObj.getBlock(x, y, z);

				if(x == xCoord - ring || x == xCoord + ring || z == zCoord - ring || z == zCoord + ring) {

					if(b.isReplaceable(worldObj, x, y, z) && (wallEverything || b.getMaterial().isLiquid())) {
						worldObj.setBlock(x, y, z, ModBlocks.barricade);
					}
				} else {

					if(b.getMaterial().isLiquid()) {
						worldObj.setBlockToAir(x, y, z);
						continue;
					}
				}
			}
		}
	}
	protected void mineOuterOres(int ring) {
		int y = getY();

		for(int x = xCoord - ring; x <= xCoord + ring; x++) {
			for(int z = zCoord - ring; z <= zCoord + ring; z++) {

				if(ring == 1 || (x == xCoord - ring || x == xCoord + ring || z == zCoord - ring || z == zCoord + ring)) {

					Block b = worldObj.getBlock(x, y, z);

					if(!this.shouldIgnoreBlock(b, x, y, z) && this.isOre(x, y, z, b)) {
						tryMineAtLocation(x, y, z);
					}
				}
			}
		}
	}

	protected void tryEjectBuffer() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);

		int x = xCoord + dir.offsetX * 4;
		int y = yCoord - 3;
		int z = zCoord + dir.offsetZ * 4;

		List<ItemStack> items = new ArrayList();

		for(int i = 5; i < 14; i++) {
			ItemStack stack = slots[i];

			if(stack != null) {
				items.add(stack.copy());
			}
		}

		TileEntity tile = worldObj.getTileEntity(x, y, z);
		if(tile instanceof IInventory) {
			supplyContainer((IInventory) tile, items, dir.getOpposite());
		}

		Block b = worldObj.getBlock(x, y, z);
		if(b instanceof IConveyorBelt) {
			supplyConveyor((IConveyorBelt) b, items, x, y, z);
		}

		items.removeIf(i -> i == null || i.stackSize <= 0);

		for(int i = 5; i < 14; i++) {
			int index = i - 5;

			if(items.size() > index) {
				slots[i] = items.get(index).copy();
			} else {
				slots[i] = null;
			}
		}
	}

	/** pulls up an AABB around the drillbit and tries to either conveyor output or buffer collected items */
	protected void tryCollect(int radius) {
		int yLevel = getY();

		List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - radius, yLevel - 1, zCoord - radius, xCoord + radius + 1, yLevel + 2, zCoord + radius + 1));

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);

		int x = xCoord + dir.offsetX * 4;
		int y = yCoord - 3;
		int z = zCoord + dir.offsetZ * 4;

		List<ItemStack> stacks = new ArrayList();
		items.forEach(i -> { if(!i.isDead) stacks.add(i.getEntityItem());});

		/* try to insert into a valid container */
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		if(tile instanceof IInventory) {
			supplyContainer((IInventory) tile, stacks, dir.getOpposite());
		}

		/* try to place on conveyor belt */
		Block b = worldObj.getBlock(x, y, z);
		if(b instanceof IConveyorBelt) {
			supplyConveyor((IConveyorBelt) b, stacks, x, y, z);
		}

		items.removeIf(i -> i.isDead || i.getEntityItem().stackSize <= 0);

		/* collect remaining items in internal buffer */
		outer:
		for(EntityItem item : items) {
			if(item.isDead) continue;

			ItemStack stack = item.getEntityItem();

			/* adding items to existing stacks */
			for(int i = 5; i < 14; i++) {

				if(slots[i] != null && slots[i].stackSize < slots[i].getMaxStackSize() && stack.isItemEqual(slots[i]) && ItemStack.areItemStackTagsEqual(stack, slots[i])) {
					int toAdd = Math.min(slots[i].getMaxStackSize() - slots[i].stackSize, stack.stackSize);
					slots[i].stackSize += toAdd;
					stack.stackSize -= toAdd;

					chuteTimer = 40;

					if(stack.stackSize <= 0) {
						item.setDead();
						continue outer;
					}
				}
			}

			/* add leftovers to empty slots */
			for(int i = 5; i < 14; i++) {

				if(slots[i] == null) {

					chuteTimer = 40;

					slots[i] = stack.copy();
					item.setDead();
					break;
				}
			}
		}
	}

	/** places all items into a connected container, if possible */
	protected void supplyContainer(IInventory inv, List<ItemStack> items, ForgeDirection dir) {

		int side = dir.ordinal();
		int[] access = null;

		if(inv instanceof ISidedInventory) {
			ISidedInventory sided = (ISidedInventory) inv;
			access = InventoryUtil.masquerade(sided, dir.ordinal());
		}

		for(ItemStack item : items) {

			if(item.stackSize <= 0) continue;

			CraneInserter.addToInventory(inv, access, item, side);
			chuteTimer = 40;
		}
	}

	/** moves all items onto a connected conveyor belt */
	protected void supplyConveyor(IConveyorBelt belt, List<ItemStack> items, int x, int y, int z) {

		Random rand = worldObj.rand;

		for(ItemStack item : items) {

			if(item.stackSize <= 0) continue;

			Vec3 base = Vec3.createVectorHelper(x + rand.nextDouble(), y + 0.5, z + rand.nextDouble());
			Vec3 vec = belt.getClosestSnappingPosition(worldObj, x, y, z, base);

			EntityMovingItem moving = new EntityMovingItem(worldObj);
			moving.setPosition(base.xCoord, vec.yCoord, base.zCoord);
			moving.setItemStack(item.copy());
			worldObj.spawnEntityInWorld(moving);
			item.stackSize = 0;

			chuteTimer = 40;
		}
	}

	public long getPowerConsumption() {
		return consumption;
	}

	public int getFortuneLevel() {
		EnumDrillType type = getInstalledDrill();

		if(type != null) return type.fortune;
		return 0;
	}

	public boolean shouldIgnoreBlock(Block block, int x, int y, int z) {
		return block.isAir(worldObj, x, y, z) || block.getMaterial() == ModBlocks.materialGas || block.getBlockHardness(worldObj, x, y, z) < 0 || block.getMaterial().isLiquid() || block == Blocks.bedrock;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("drill")) this.enableDrill = !this.enableDrill;
		if(data.hasKey("crusher")) this.enableCrusher = !this.enableCrusher;
		if(data.hasKey("walling")) this.enableWalling = !this.enableWalling;
		if(data.hasKey("veinminer")) this.enableVeinMiner = !this.enableVeinMiner;
		if(data.hasKey("silktouch")) this.enableSilkTouch = !this.enableSilkTouch;

		this.markChanged();
	}

	public EnumDrillType getInstalledDrill() {
		if(slots[4] != null && slots[4].getItem() instanceof ItemDrillbit) {
			return EnumUtil.grabEnumSafely(EnumDrillType.class, slots[4].getItemDamage());
		}

		return null;
	}

	public boolean canVeinMine() {
		EnumDrillType type = getInstalledDrill();
		return this.enableVeinMiner && type != null && type.vein;
	}

	public boolean canSilkTouch() {
		EnumDrillType type = getInstalledDrill();
		return this.enableSilkTouch && type != null && type.silk;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.enableDrill = nbt.getBoolean("d");
		this.enableCrusher = nbt.getBoolean("c");
		this.enableWalling = nbt.getBoolean("w");
		this.enableVeinMiner = nbt.getBoolean("v");
		this.enableSilkTouch = nbt.getBoolean("s");
		this.targetDepth = nbt.getInteger("t");
		this.power = nbt.getLong("p");
		this.tank.readFromNBT(nbt, "tank");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("d", enableDrill);
		nbt.setBoolean("c", enableCrusher);
		nbt.setBoolean("w", enableWalling);
		nbt.setBoolean("v", enableVeinMiner);
		nbt.setBoolean("s", enableSilkTouch);
		nbt.setInteger("t", targetDepth);
		nbt.setLong("p", power);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineExcavator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineExcavator(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					0,
					zCoord - 3,
					xCoord + 4,
					yCoord + 5,
					zCoord + 4
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
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_excavator));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (100 - 200 / (level + 2)) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (100 - 100 / (level + 1)) + "%"));
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.POWER, 3);
		upgrades.put(UpgradeType.EFFECT, 3);
		return upgrades;
	}

	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}
}
