package com.hbm.items.machine;

import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockPWR;
import com.hbm.blocks.machine.BlockPWR.TileEntityBlockPWR;
import com.hbm.inventory.gui.GUIScreenSlicePrinter;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.TileEntityPWRController;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
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
	private static Block[] blockSync;
	private static ForgeDirection dir;

	private HashSet<BlockPos> fill = new HashSet<>();
	private static HashSet<Block> whitelist = new HashSet<Block>() {{
		add(ModBlocks.pwr_block);
		add(ModBlocks.pwr_controller);
	}};

	// Piggybacking functions using the bytebuf TE sync
	public static void serialize(World world, ByteBuf buf) {
		buf.writeInt(x1);
		buf.writeInt(y1);
		buf.writeInt(z1);
		buf.writeInt(x2);
		buf.writeInt(y2);
		buf.writeInt(z2);
		buf.writeInt(dir.ordinal());

		for(Block block : blockSync) {
			buf.writeInt(Block.getIdFromBlock(block));
		}

		blockSync = null;
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
		dir = ForgeDirection.values()[buf.readInt()];

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
		findBounds(world, pwr);

		int sizeX = x2 - x1 + 1;
		int sizeY = y2 - y1 + 1;
		int sizeZ = z2 - z1 + 1;

		blockSync = new Block[sizeX * sizeY * sizeZ];
		int i = 0;

		for(int x = x1; x <= x2; x++) {
			for(int y = y1; y <= y2; y++) {
				for(int z = z1; z <= z2; z++) {
					TileEntity tile = world.getTileEntity(x, y, z);
					if(tile instanceof TileEntityBlockPWR) {
						blockSync[i] = ((TileEntityBlockPWR) tile).block;
					}
					i++;
				}
			}
		}

		pwr.isPrinting = true;
	}

	public void findBounds(World world, TileEntityPWRController pwr) {
		dir = ForgeDirection.getOrientation(world.getBlockMetadata(pwr.xCoord, pwr.yCoord, pwr.zCoord)).getOpposite();

		fill.clear();
		fill.add(new BlockPos(pwr.xCoord, pwr.yCoord, pwr.zCoord));
		x1 = x2 = pwr.xCoord;
		y1 = y2 = pwr.yCoord;
		z1 = z2 = pwr.zCoord;
		floodFill(world, pwr.xCoord + dir.offsetX, pwr.yCoord, pwr.zCoord + dir.offsetZ);
	}

	public void floodFill(World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if(fill.contains(pos)) return;

		if(world.getBlock(x, y, z) instanceof BlockPWR) {
			fill.add(pos);

			x1 = Math.min(x1, x);
			y1 = Math.min(y1, y);
			z1 = Math.min(z1, z);
			x2 = Math.max(x2, x);
			y2 = Math.max(y2, y);
			z2 = Math.max(z2, z);

			floodFill(world, x + 1, y, z);
			floodFill(world, x - 1, y, z);
			floodFill(world, x, y + 1, z);
			floodFill(world, x, y - 1, z);
			floodFill(world, x, y, z + 1);
			floodFill(world, x, y, z - 1);
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
