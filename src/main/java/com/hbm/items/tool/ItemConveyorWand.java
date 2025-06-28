package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.network.BlockConveyorBase;
import com.hbm.blocks.network.BlockConveyorBendable;
import com.hbm.blocks.network.BlockCraneBase;
import com.hbm.main.MainRegistry;
import com.hbm.render.util.RenderOverhead;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.WorldInAJar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;

public class ItemConveyorWand extends Item implements ILookOverlay {

	public ItemConveyorWand() {
		setHasSubtypes(true);
	}

	public static enum ConveyorType {
		REGULAR,
		EXPRESS,
		DOUBLE,
		TRIPLE
	}

	public static ConveyorType getType(ItemStack stack) {
		if(stack == null) return ConveyorType.REGULAR;
		return ConveyorType.values()[stack.getItemDamage()];
	}

	public static Block getConveyorBlock(ConveyorType type) {
		switch(type) {
		case EXPRESS: return ModBlocks.conveyor_express;
		case DOUBLE: return ModBlocks.conveyor_double;
		case TRIPLE: return ModBlocks.conveyor_triple;
		default: return ModBlocks.conveyor;
		}
	}

	public static boolean hasSnakesAndLadders(ConveyorType type) {
		return type == ConveyorType.REGULAR;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(ConveyorType type : ConveyorType.values()) {
			list.add(new ItemStack(item, 1, type.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + getType(stack).name().toLowerCase(Locale.US);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			for(String s : I18nUtil.resolveKeyArray(super.getUnlocalizedName(stack) + ".desc")) {
				list.add(EnumChatFormatting.YELLOW + s);
			}
			if(hasSnakesAndLadders(getType(stack))) {
				list.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey(super.getUnlocalizedName(stack) + ".vertical.desc"));
			}
		} else {
			list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "Hold <" + EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" + EnumChatFormatting.DARK_GRAY
					+ "" + EnumChatFormatting.ITALIC + "> to display more info");
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if(player.isSneaking() && !stack.hasTagCompound()) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			Block onBlock = world.getBlock(x, y, z);
			int onMeta = world.getBlockMetadata(x, y, z);
			ConveyorType type = getType(stack);

			if(hasSnakesAndLadders(type) && onBlock == ModBlocks.conveyor && onMeta < 6) {
				if(dir == ForgeDirection.UP) {
					onBlock = ModBlocks.conveyor_lift;
					world.setBlock(x, y, z, onBlock, onMeta, 3);
				} else if(dir == ForgeDirection.DOWN) {
					onBlock = ModBlocks.conveyor_chute;
					world.setBlock(x, y, z, onBlock, onMeta, 3);
				}
			}

			Block toPlace = getConveyorBlock(type);
			if(hasSnakesAndLadders(type)) {
				if(onBlock == ModBlocks.conveyor_lift && dir == ForgeDirection.UP) toPlace = ModBlocks.conveyor_lift;
				if(onBlock == ModBlocks.conveyor_chute && dir == ForgeDirection.DOWN) toPlace = ModBlocks.conveyor_chute;
			}

			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;

			if(world.getBlock(x, y, z).isReplaceable(world, x, y, z)) {
				world.setBlock(x, y, z, toPlace);
				toPlace.onBlockPlacedBy(world, x, y, z, player, stack);
				stack.stackSize--;
			}

			return true;
		}

		// If placing on top of a conveyor block, auto-snap to edge if possible
		// this makes it easier to connect without having to click the small edge of a conveyor
		Block onBlock = world.getBlock(x, y, z);
		if(onBlock instanceof BlockConveyorBendable) {
			BlockConveyorBase bendable = (BlockConveyorBase) onBlock;
			ForgeDirection moveDir = stack.hasTagCompound() ? bendable.getInputDirection(world, x, y, z) : bendable.getOutputDirection(world, x, y, z);

			int ox = x + moveDir.offsetX;
			int oy = y + moveDir.offsetY;
			int oz = z + moveDir.offsetZ;

			if(world.getBlock(ox, oy, oz).isReplaceable(world, ox, oy, oz)) {
				side = moveDir.ordinal();
			}
		}

		if(!stack.hasTagCompound()) {
			// Starting placement
			NBTTagCompound nbt = stack.stackTagCompound = new NBTTagCompound();

			nbt.setInteger("x", x);
			nbt.setInteger("y", y);
			nbt.setInteger("z", z);
			nbt.setInteger("side", side);

			int count = 0;
			if(player.capabilities.isCreativeMode) {
				count = 256;
			} else {
				for(ItemStack inventoryStack : player.inventory.mainInventory) {
					if(inventoryStack != null && inventoryStack.getItem() == this && inventoryStack.getItemDamage() == stack.getItemDamage()) {
						count += inventoryStack.stackSize;
					}
				}
			}

			nbt.setInteger("count", count);
		} else {
			// Constructing conveyor
			NBTTagCompound nbt = stack.stackTagCompound;

			int sx = nbt.getInteger("x");
			int sy = nbt.getInteger("y");
			int sz = nbt.getInteger("z");
			int sSide = nbt.getInteger("side");
			int count = nbt.getInteger("count");

			if(!world.isRemote) {
				ConveyorType type = getType(stack);

				// pretend to construct, if it doesn't fail, actually construct
				int constructCount = construct(world, null, type, player, sx, sy, sz, sSide, x, y, z, side, 0, 0, 0, count);
				if(constructCount > 0) {
					int toRemove = construct(world, world, type, player, sx, sy, sz, sSide, x, y, z, side, 0, 0, 0, count);

					if(!player.capabilities.isCreativeMode) {
						for(ItemStack inventoryStack : player.inventory.mainInventory) {
							if(inventoryStack != null && inventoryStack.getItem() == this && inventoryStack.getItemDamage() == stack.getItemDamage()) {
								int removing = Math.min(toRemove, inventoryStack.stackSize);
								inventoryStack.stackSize -= removing;
								toRemove -= removing;
							}

							if(toRemove <= 0) break;
						}

						player.inventoryContainer.detectAndSendChanges();
					}

					player.addChatMessage(new ChatComponentText("Conveyor built!"));
				} else if(constructCount == 0) {
					player.addChatMessage(new ChatComponentText("Not enough conveyors, build cancelled"));
				} else {
					player.addChatMessage(new ChatComponentText("Conveyor obstructed, build cancelled"));
				}
			} else {
				RenderOverhead.clearActionPreview();
				lastMop = null;
			}

			stack.stackTagCompound = null;
		}

		return true; // always eat interactions
	}

	private static MovingObjectPosition lastMop;
	private static int lastSide;
	private static float lastYaw;

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean inHand) {
		if(!(entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) entity;

		if(!inHand && stack.hasTagCompound()) {
			ItemStack held = player.getHeldItem();
			if(held == null || held.getItem() != this || held.getItemDamage() != stack.getItemDamage()) {
				stack.stackTagCompound = null;
				if(world.isRemote) {
					RenderOverhead.clearActionPreview();
					lastMop = null;
				}
			}
		}

		// clientside prediction only
		if(world.isRemote && inHand) {
			if(!stack.hasTagCompound()) {
				RenderOverhead.clearActionPreview();
				lastMop = null;
				return;
			}

			MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
			if(mop == null || mop.typeOfHit != MovingObjectType.BLOCK) {
				RenderOverhead.clearActionPreview();
				lastMop = null;
				return;
			}

			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			int side = mop.sideHit;

			Block onBlock = world.getBlock(x, y, z);
			if(onBlock instanceof BlockConveyorBendable) {
				BlockConveyorBase bendable = (BlockConveyorBase) onBlock;
				ForgeDirection moveDir = bendable.getInputDirection(world, x, y, z);

				int ox = x + moveDir.offsetX;
				int oy = y + moveDir.offsetY;
				int oz = z + moveDir.offsetZ;

				if(world.getBlock(ox, oy, oz).isReplaceable(world, ox, oy, oz)) {
					side = moveDir.ordinal();
				}
			}

			if(lastMop != null && mop.blockX == lastMop.blockX && mop.blockY == lastMop.blockY && mop.blockZ == lastMop.blockZ && side == lastSide && Math.abs(lastYaw - player.rotationYaw) < 15) return;
			lastMop = mop;
			lastYaw = player.rotationYaw;
			lastSide = side;

			NBTTagCompound nbt = stack.stackTagCompound;

			int sx = nbt.getInteger("x");
			int sy = nbt.getInteger("y");
			int sz = nbt.getInteger("z");
			int sSide = nbt.getInteger("side");
			int count = nbt.getInteger("count");

			// Size has a one block buffer on both sides, for overshooting conveyors
			int sizeX = Math.abs(sx - x) + 3;
			int sizeY = Math.abs(sy - y) + 3;
			int sizeZ = Math.abs(sz - z) + 3;

			int minX = Math.min(sx, x) - 1;
			int minY = Math.min(sy, y) - 1;
			int minZ = Math.min(sz, z) - 1;

			WorldInAJar wiaj = new WorldInAJar(sizeX, sizeY, sizeZ);
			boolean pathSuccess = construct(world, wiaj, getType(stack), player, sx, sy, sz, sSide, x, y, z, side, minX, minY, minZ, count) > 0;

			RenderOverhead.setActionPreview(wiaj, minX, minY, minZ, pathSuccess);
		}
	}

	// In creative, auto delete connected conveyors
	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer playerEntity) {
		if(!playerEntity.isSneaking()) return false;

		World world = playerEntity.worldObj;
		Block block = world.getBlock(x, y, z);

		if(!playerEntity.capabilities.isCreativeMode) return false;
		if(!(playerEntity instanceof EntityPlayerMP)) return false;

		EntityPlayerMP player = (EntityPlayerMP) playerEntity;

		if(!world.isRemote && block instanceof BlockConveyorBase) {
			BlockConveyorBase conveyor = (BlockConveyorBase) block;
			ForgeDirection input = conveyor.getInputDirection(world, x, y, z);
			ForgeDirection output = conveyor.getOutputDirection(world, x, y, z);
			breakExtra(world, player, x + input.offsetX, y + input.offsetY, z + input.offsetZ, 32);
			breakExtra(world, player, x + output.offsetX, y + output.offsetY, z + output.offsetZ, 32);
		}

		return false;
	}

	private void breakExtra(World world, EntityPlayerMP player, int x, int y, int z, int depth) {
		depth--;
		if(depth <= 0) return;

		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(!(block instanceof BlockConveyorBase)) return;

		BlockConveyorBase conveyor = (BlockConveyorBase) block;
		ForgeDirection input = conveyor.getInputDirection(world, x, y, z);
		ForgeDirection output = conveyor.getOutputDirection(world, x, y, z);

		BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
		if(event.isCanceled())
			return;

		block.onBlockHarvested(world, x, y, z, meta, player);
		if(block.removedByPlayer(world, player, x, y, z, false)) {
			block.onBlockDestroyedByPlayer(world, x, y, z, meta);
		}

		player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
		breakExtra(world, player, x + input.offsetX, y + input.offsetY, z + input.offsetZ, depth);
		breakExtra(world, player, x + output.offsetX, y + output.offsetY, z + output.offsetZ, depth);
	}

	// attempts to construct a conveyor between two points, including bends, lifts, and chutes
	private static int construct(World routeWorld, IBlockAccess buildWorld, ConveyorType type, EntityPlayer player, int x1, int y1, int z1, int side1, int x2, int y2, int z2, int side2, int box, int boy, int boz, int max) {
		ForgeDirection dir = ForgeDirection.getOrientation(side1);
		ForgeDirection targetDir = ForgeDirection.getOrientation(side2);

		// if placing within a single block, we have to handle rotation specially, treating it like a manual placement with player facing
		if(x1 == x2 && y1 == y2 && z1 == z2 && side1 == side2 && (dir == ForgeDirection.UP || dir == ForgeDirection.DOWN)) {
			int meta = getFacingMeta(player);

			y1 += dir.offsetY;

			if(!routeWorld.getBlock(x1, y1, z1).isReplaceable(routeWorld, x1, y1, z1)) return -1;

			Block block = getConveyorBlock(type);
			if(buildWorld instanceof World) {
				((World) buildWorld).setBlock(x1 - box, y1 - boy, z1 - boz, block, meta, 3);
			} else if(buildWorld instanceof WorldInAJar) {
				((WorldInAJar) buildWorld).setBlock(x1 - box, y1 - boy, z1 - boz, block, meta);
			}

			return 1;
		}

		boolean hasVertical = hasSnakesAndLadders(type);

		int tx = x2 + targetDir.offsetX;
		int ty = y2 + targetDir.offsetY;
		int tz = z2 + targetDir.offsetZ;

		int x = x1 + dir.offsetX;
		int y = y1 + dir.offsetY;
		int z = z1 + dir.offsetZ;

		if(dir == ForgeDirection.UP || dir == ForgeDirection.DOWN) {
			dir = getTargetDirection(x, y, z, x2, y2, z2, hasVertical);
		}

		Block targetBlock = routeWorld.getBlock(x2, y2, z2);
		boolean isTargetHorizontal = targetDir != ForgeDirection.UP && targetDir != ForgeDirection.DOWN;
		boolean shouldTurnToTarget = isTargetHorizontal || targetBlock instanceof BlockCraneBase || targetBlock == ModBlocks.conveyor_lift || targetBlock == ModBlocks.conveyor_chute;

		ForgeDirection horDir = dir == ForgeDirection.UP || dir == ForgeDirection.DOWN ? ForgeDirection.getOrientation(getFacingMeta(player)).getOpposite() : dir;

		// Initial dropdown to floor level, if possible
		if(hasVertical && y > ty) {
			if(routeWorld.getBlock(x, y - 1, z).isReplaceable(routeWorld, x, y - 1, z)) {
				dir = ForgeDirection.DOWN;
			}
		}

		for(int loopDepth = 1; loopDepth <= max; loopDepth++) {
			if(!routeWorld.getBlock(x, y, z).isReplaceable(routeWorld, x, y, z)) return -1;

			Block block = getConveyorForDirection(type, dir);
			int meta = getConveyorMetaForDirection(block, dir, targetDir, horDir);

			int ox = x + dir.offsetX;
			int oy = y + dir.offsetY;
			int oz = z + dir.offsetZ;

			// check if we should turn before continuing
			int fromDistance = taxiDistance(x, y, z, tx, ty, tz);
			int toDistance = taxiDistance(ox, oy, oz, tx, ty, tz);
			int finalDistance = taxiDistance(ox, oy, oz, x2, y2, z2);
			boolean notAtTarget = (shouldTurnToTarget ? finalDistance : fromDistance) > 0;
			boolean willBeObstructed = notAtTarget && !routeWorld.getBlock(ox, oy, oz).isReplaceable(routeWorld, ox, oy, oz);
			boolean shouldTurn = (toDistance >= fromDistance && notAtTarget) || willBeObstructed;

			if(shouldTurn) {
				ForgeDirection newDir = getTargetDirection(x, y, z, shouldTurnToTarget ? x2 : tx, shouldTurnToTarget ? y2 : ty, shouldTurnToTarget ? z2 : tz, tx, ty, tz, dir, willBeObstructed, hasVertical);

				if(newDir == ForgeDirection.UP) {
					block = ModBlocks.conveyor_lift;
				} else if(newDir == ForgeDirection.DOWN) {
					block = ModBlocks.conveyor_chute;
				} else if(dir.getRotation(ForgeDirection.UP) == newDir) {
					meta += 8;
				} else if(dir.getRotation(ForgeDirection.DOWN) == newDir) {
					meta += 4;
				}

				dir = newDir;
				if(dir != ForgeDirection.UP && dir != ForgeDirection.DOWN) horDir = dir;
			}

			if(buildWorld instanceof World) {
				((World) buildWorld).setBlock(x - box, y - boy, z - boz, block, meta, 3);
			} else if(buildWorld instanceof WorldInAJar) {
				((WorldInAJar) buildWorld).setBlock(x - box, y - boy, z - boz, block, meta);
			}

			if(x == tx && y == ty && z == tz) return loopDepth;

			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
		}

		return 0;
	}

	private static int getFacingMeta(EntityPlayer player) {
		int meta = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		switch(meta) {
			case 0: return 2;
			case 1: return 5;
			case 2: return 3;
			case 3: return 4;
		}
		return 2;
	}

	private static int getConveyorMetaForDirection(Block block, ForgeDirection dir, ForgeDirection targetDir, ForgeDirection horDir) {
		if(block != ModBlocks.conveyor_chute && block != ModBlocks.conveyor_lift) return dir.getOpposite().ordinal();
		if(targetDir == ForgeDirection.UP || targetDir == ForgeDirection.DOWN) return horDir.getOpposite().ordinal();
		return targetDir.ordinal();
	}

	private static Block getConveyorForDirection(ConveyorType type, ForgeDirection dir) {
		if(dir == ForgeDirection.UP) return ModBlocks.conveyor_lift;
		if(dir == ForgeDirection.DOWN) return ModBlocks.conveyor_chute;
		return getConveyorBlock(type);
	}

	private static ForgeDirection getTargetDirection(int x1, int y1, int z1, int x2, int y2, int z2, boolean hasVertical) {
		return getTargetDirection(x1, y1, z1, x2, y2, z2, x2, y2, z2, null, false, hasVertical);
	}

	private static ForgeDirection getTargetDirection(int x1, int y1, int z1, int x2, int y2, int z2, int tx, int ty, int tz, ForgeDirection heading, boolean willBeObstructed, boolean hasVertical) {
		if(hasVertical && (y1 != y2 || y1 != ty) && (willBeObstructed || (x1 == x2 && z1 == z2) || (x1 == tx && z1 == tz))) return y1 > y2 ? ForgeDirection.DOWN : ForgeDirection.UP;

		if(Math.abs(x1 - x2) > Math.abs(z1 - z2)) {
			if(heading == ForgeDirection.EAST || heading == ForgeDirection.WEST) return z1 > z2 ? ForgeDirection.NORTH : ForgeDirection.SOUTH;
			return x1 > x2 ? ForgeDirection.WEST : ForgeDirection.EAST;
		} else {
			if(heading == ForgeDirection.NORTH || heading == ForgeDirection.SOUTH) return x1 > x2 ? ForgeDirection.WEST : ForgeDirection.EAST;
			return z1 > z2 ? ForgeDirection.NORTH : ForgeDirection.SOUTH;
		}
	}

	private static int taxiDistance(int x1, int y1, int z1, int x2, int y2, int z2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2) + Math.abs(z1 - z2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		EntityPlayer player = MainRegistry.proxy.me();
		if(player == null || !player.isSneaking() || !player.capabilities.isCreativeMode) return;

		Block block = world.getBlock(x, y, z);
		if(block instanceof BlockConveyorBase) {
			List<String> text = new ArrayList<>();
			text.add("Break whole conveyor line");
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(block.getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

}