package com.hbm.items.machine;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockPWR;
import com.hbm.blocks.machine.BlockPWR.TileEntityBlockPWR;
import com.hbm.inventory.gui.GUIScreenSlicePrinter;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.TileEntityPWRController;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemPWRPrinter extends Item implements IGUIProvider {

	private static int x1, y1, z1;
	private static int x2, y2, z2;
	private static ForgeDirection dir;
	private static final int MAX_PRINT_AXIS = 128;
	private static final int MAX_PRINT_BLOCKS = 262_144;

	private HashSet<BlockPos> fill = new HashSet<>();
	private static HashSet<Block> whitelist = new HashSet<Block>() {{
		add(ModBlocks.pwr_block);
		add(ModBlocks.pwr_controller);
	}};

	// Piggybacking functions using the bytebuf TE sync
	public static void serialize(PrintData print, ByteBuf buf) {
		buf.writeInt(print.x1);
		buf.writeInt(print.y1);
		buf.writeInt(print.z1);
		buf.writeInt(print.x2);
		buf.writeInt(print.y2);
		buf.writeInt(print.z2);
		buf.writeInt(print.direction.ordinal());

		for(Block block : print.blocks) {
			buf.writeInt(Block.getIdFromBlock(block));
		}
	}

	// idiot box for server crashes: 2
	@SideOnly(Side.CLIENT)
	public static void deserialize(World world, ByteBuf buf) {
		x1 = buf.readInt();
		y1 = buf.readInt();
		z1 = buf.readInt();
		x2 = buf.readInt();
		y2 = buf.readInt();
		z2 = buf.readInt();
		dir = EnumUtil.getEnumOrDefault(ForgeDirection.class, buf.readInt(), ForgeDirection.UNKNOWN);

		long sizeX = (long) x2 - x1 + 1L;
		long sizeY = (long) y2 - y1 + 1L;
		long sizeZ = (long) z2 - z1 + 1L;
		long volume = sizeX * sizeY * sizeZ;
		if(sizeX <= 0 || sizeY <= 0 || sizeZ <= 0 || sizeX > MAX_PRINT_AXIS || sizeY > MAX_PRINT_AXIS || sizeZ > MAX_PRINT_AXIS
				|| volume > MAX_PRINT_BLOCKS || volume * 4L > buf.readableBytes()) {
			throw new DecoderException("Invalid PWR print dimensions: " + sizeX + "x" + sizeY + "x" + sizeZ);
		}

		for(int x = x1; x <= x2; x++) {
			for(int y = y1; y <= y2; y++) {
				for(int z = z1; z <= z2; z++) {
					Block block = Block.getBlockById(buf.readInt());

					TileEntity tile = world.getTileEntity(x, y, z);
					if(!(tile instanceof TileEntityBlockPWR)) continue;
					((TileEntityBlockPWR) tile).block = block;
				}
			}
		}

		// Open the printer GUI on any client players holding the printer
		// yeah it's a shit hack yay weee wooo
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player != null && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPWRPrinter) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, 0, 0, 0);
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(!(tile instanceof TileEntityPWRController)) return false;
		if(world.isRemote) return true;

		TileEntityPWRController pwr = (TileEntityPWRController) tile;
		syncAndScreenshot(world, pwr);

		return true;
	}

	public void syncAndScreenshot(World world, TileEntityPWRController pwr) {
		pwr.pendingPrint = null;
		if(!findBounds(world, pwr)) return;

		int sizeX = x2 - x1 + 1;
		int sizeY = y2 - y1 + 1;
		int sizeZ = z2 - z1 + 1;
		long volume = (long) sizeX * sizeY * sizeZ;
		if(sizeX > MAX_PRINT_AXIS || sizeY > MAX_PRINT_AXIS || sizeZ > MAX_PRINT_AXIS || volume > MAX_PRINT_BLOCKS) return;

		Block[] blockSync = new Block[(int) volume];
		int i = 0;

		for(int x = x1; x <= x2; x++) {
			for(int y = y1; y <= y2; y++) {
				for(int z = z1; z <= z2; z++) {
					blockSync[i] = Blocks.air;
					TileEntity tile = world.getTileEntity(x, y, z);
					if(tile instanceof TileEntityBlockPWR && ((TileEntityBlockPWR) tile).block != null) {
						blockSync[i] = ((TileEntityBlockPWR) tile).block;
					}
					i++;
				}
			}
		}

		pwr.pendingPrint = new PrintData(x1, y1, z1, x2, y2, z2, dir, blockSync);
	}

	public boolean findBounds(World world, TileEntityPWRController pwr) {
		dir = ForgeDirection.getOrientation(world.getBlockMetadata(pwr.xCoord, pwr.yCoord, pwr.zCoord)).getOpposite();

		fill.clear();
		fill.add(new BlockPos(pwr.xCoord, pwr.yCoord, pwr.zCoord));
		x1 = x2 = pwr.xCoord;
		y1 = y2 = pwr.yCoord;
		z1 = z2 = pwr.zCoord;
		return floodFill(world, pwr.xCoord + dir.offsetX, pwr.yCoord, pwr.zCoord + dir.offsetZ);
	}

	public boolean floodFill(World world, int x, int y, int z) {
		ArrayDeque<BlockPos> pending = new ArrayDeque<BlockPos>();
		pending.add(new BlockPos(x, y, z));
		while(!pending.isEmpty()) {
			BlockPos pos = pending.removeFirst();
			if(fill.contains(pos)) continue;
			int px = pos.getX();
			int py = pos.getY();
			int pz = pos.getZ();
			if(!(world.getBlock(px, py, pz) instanceof BlockPWR)) continue;
			if(fill.size() >= MAX_PRINT_BLOCKS) return false;

			fill.add(pos);
			x1 = Math.min(x1, px);
			y1 = Math.min(y1, py);
			z1 = Math.min(z1, pz);
			x2 = Math.max(x2, px);
			y2 = Math.max(y2, py);
			z2 = Math.max(z2, pz);
			if(x2 - x1 + 1 > MAX_PRINT_AXIS || y2 - y1 + 1 > MAX_PRINT_AXIS || z2 - z1 + 1 > MAX_PRINT_AXIS) return false;

			pending.add(pos.add(1, 0, 0));
			pending.add(pos.add(-1, 0, 0));
			pending.add(pos.add(0, 1, 0));
			pending.add(pos.add(0, -1, 0));
			pending.add(pos.add(0, 0, 1));
			pending.add(pos.add(0, 0, -1));
		}
		return true;
	}

	public static class PrintData {
		public final int x1, y1, z1, x2, y2, z2;
		public final ForgeDirection direction;
		public final Block[] blocks;

		public PrintData(int x1, int y1, int z1, int x2, int y2, int z2, ForgeDirection direction, Block[] blocks) {
			this.x1 = x1; this.y1 = y1; this.z1 = z1;
			this.x2 = x2; this.y2 = y2; this.z2 = z2;
			this.direction = direction;
			this.blocks = blocks;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenSlicePrinter(x1, y1, z1, x2, y2, z2, dir, whitelist);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Use on a constructed PWR controller to generate construction diagrams");
	}

}
