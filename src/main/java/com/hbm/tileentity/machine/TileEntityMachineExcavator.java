package com.hbm.tileentity.machine;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.network.CraneInserter;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineExcavator;
import com.hbm.items.machine.ItemDrillbit;
import com.hbm.items.machine.ItemDrillbit.EnumDrillType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EnumUtil;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineExcavator extends TileEntityMachineBase implements IEnergyUser, IFluidStandardReceiver, IControlReceiver, IGUIProvider {

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

	public float drillRotation = 0F;
	public float prevDrillRotation = 0F;
	public float drillExtension = 0F;
	public float prevDrillExtension = 0F;
	
	public FluidTank tank;

	public TileEntityMachineExcavator() {
		super(14);
		this.tank = new FluidTank(Fluids.SULFURIC_ACID, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineExcavator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 0, this.getPower(), this.getMaxPower());
			this.operational = false;
			
			if(this.enableDrill && this.getInstalledDrill() != null && this.power >= this.getPowerConsumption()) {
				
				operational = true;
				
				if(targetDepth < this.yCoord - 4 && tryDrill(5)) {
					targetDepth++;
				}
			} else {
				this.targetDepth = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("d", enableDrill);
			data.setBoolean("c", enableCrusher);
			data.setBoolean("w", enableWalling);
			data.setBoolean("v", enableVeinMiner);
			data.setBoolean("s", enableSilkTouch);
			data.setBoolean("o", operational);
			data.setInteger("t", targetDepth);
			data.setLong("p", power);
			this.networkPack(data, 150);
			
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
			
			if(this.operational)
				this.drillRotation += 15F;
			
			if(this.drillRotation >= 360F) {
				this.drillRotation -= 360F;
				this.prevDrillRotation -= 360F;
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.enableDrill = nbt.getBoolean("d");
		this.enableCrusher = nbt.getBoolean("c");
		this.enableWalling = nbt.getBoolean("w");
		this.enableVeinMiner = nbt.getBoolean("v");
		this.enableSilkTouch = nbt.getBoolean("s");
		this.operational = nbt.getBoolean("o");
		this.targetDepth = nbt.getInteger("t");
		this.power = nbt.getLong("p");
	}
	
	protected int getY() {
		return yCoord - targetDepth - 4;
	}
	
	/** Works outwards and tries to break a ring, returns true if all rings are broken (or ignorable) and the drill should extend. */
	protected boolean tryDrill(int radius) {
		int y = getY();
		
		if(targetDepth == 0) {
			radius = 1;
		}
		
		for(int ring = 1; ring <= radius; ring++) {
			
			boolean ignoreAll = true;
			float combinedHardness = 0F;
			
			for(int x = xCoord - ring; x <= xCoord + ring; x++) {
				for(int z = zCoord - ring; z <= zCoord + ring; z++) {
					
					/* Process blocks either if we are in the inner ring (1 = 3x3) or if the target block is on the outer edge */
					if(ring == 1 || (x == xCoord - ring || x == xCoord + ring || z == zCoord - ring || z == zCoord + ring)) {
						
						Block b = worldObj.getBlock(x, y, z);
						
						if(shouldIgnoreBlock(b, x, y ,z)) continue;
						
						ignoreAll = false;
						
						combinedHardness += b.getBlockHardness(worldObj, x, y, z);
					}
				}
			}
			
			if(!ignoreAll) {
				ticksWorked++;
				
				int ticksToWork = (int) Math.ceil(combinedHardness);
				
				if(ticksWorked >= ticksToWork) {
					breakBlocks(ring);
					buildWall(ring + 1, ring == radius && this.enableWalling);
					tryCollect(radius);
					ticksWorked = 0;
				}
				
				return false;
			}
		}

		buildWall(radius + 1, this.enableWalling);
		ticksWorked = 0;
		return true;
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
		
		if(this.enableVeinMiner && this.getInstalledDrill().vein) {
			
			/* doing this isn't terribly accurate but just for figuring out if there's OD it works */
			Item blockItem = Item.getItemFromBlock(worldObj.getBlock(x, y, z));
			
			if(blockItem != null) {
				List<String> names = ItemStackUtil.getOreDictNames(new ItemStack(blockItem));
				
				for(String name : names) {
					if(name.startsWith("ore")) {
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
			}
		}

		Block b = worldObj.getBlock(x, y, z);
		breakSingleBlock(b, x, y, z);
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
		b.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 0 /* fortune */);
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
	
	/** pulls up an AABB around the drillbit and tries to either conveyor output or buffer collected items */
	protected void tryCollect(int radius) {
		int yLevel = getY();
		
		List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord - radius, yLevel - 1, zCoord - radius, xCoord + radius + 1, yLevel + 2, zCoord + radius + 1));
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);

		int x = xCoord + dir.offsetX * 4;
		int y = yCoord - 3;
		int z = zCoord + dir.offsetZ * 4;
		
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		if(tile instanceof IInventory) {
			supplyContainer((IInventory) tile, items, dir.getOpposite());
		}
		
		Block b = worldObj.getBlock(x, y, z);
		if(b instanceof IConveyorBelt) {
			supplyConveyor((IConveyorBelt) b, items, x, y, z);
		}
	}
	
	/** places all items into a connected container, if possible */
	protected void supplyContainer(IInventory inv, List<EntityItem> items, ForgeDirection dir) {
		
		int side = dir.ordinal();
		int[] access = null;
		
		if(inv instanceof ISidedInventory) {
			ISidedInventory sided = (ISidedInventory) inv;
			access = CraneInserter.masquerade(sided, dir.ordinal());
		}
		
		for(EntityItem item : items) {
			
			if(item.isDead) continue;
			
			ItemStack stack = CraneInserter.addToInventory(inv, access, item.getEntityItem(), side);
			
			if(stack == null || stack.stackSize == 0) {
				item.setDead();
			}
		}
	}
	
	/** moves all items onto a connected conveyor belt */
	protected void supplyConveyor(IConveyorBelt belt, List<EntityItem> items, int x, int y, int z) {
		
		Random rand = worldObj.rand;
		
		for(EntityItem item : items) {
			
			if(item.isDead) continue;
			
			Vec3 base = Vec3.createVectorHelper(x + rand.nextDouble(), y + 0.5, z + rand.nextDouble());
			Vec3 vec = belt.getClosestSnappingPosition(worldObj, x, y, z, base);
			
			EntityMovingItem moving = new EntityMovingItem(worldObj);
			moving.setPosition(base.xCoord, vec.yCoord, base.zCoord);
			moving.setItemStack(item.getEntityItem().copy());
			worldObj.spawnEntityInWorld(moving);
			item.setDead();
		}
	}
	
	public int getPowerConsumption() {
		return 10_000;
	}
	
	public boolean shouldIgnoreBlock(Block block, int x, int y, int z) {
		return block.isAir(worldObj, x, y, z) || block.getBlockHardness(worldObj, x, y, z) < 0 || block.getMaterial().isLiquid() || block == Blocks.bedrock;
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
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineExcavator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
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
}
