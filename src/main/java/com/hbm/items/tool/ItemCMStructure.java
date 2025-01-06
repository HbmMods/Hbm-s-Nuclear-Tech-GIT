package com.hbm.items.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class ItemCMStructure extends Item implements ILookOverlay {
	
	private static File file = new File(MainRegistry.configHbmDir, "CMstructureOutput.txt");

	public static BlockPos getAnchor(ItemStack stack) {

		if(!stack.hasTagCompound()) {
			return null;
		}

		return new BlockPos(stack.stackTagCompound.getInteger("anchorX"), stack.stackTagCompound.getInteger("anchorY"), stack.stackTagCompound.getInteger("anchorZ"));
	}

	public static void setAnchor(ItemStack stack, int x, int y, int z) {

		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		stack.stackTagCompound.setInteger("anchorX", x);
		stack.stackTagCompound.setInteger("anchorY", y);
		stack.stackTagCompound.setInteger("anchorZ", z);
	}

	public static void writeToFile(File config, ItemStack stack, World world) {
		int anchorX = stack.stackTagCompound.getInteger("anchorX");
		int anchorY = stack.stackTagCompound.getInteger("anchorY");
		int anchorZ = stack.stackTagCompound.getInteger("anchorZ");
		int x1 = stack.stackTagCompound.getInteger("x1");
		int y1 = stack.stackTagCompound.getInteger("y1");
		int z1 = stack.stackTagCompound.getInteger("z1");
		int x2 = stack.stackTagCompound.getInteger("x2");
		int y2 = stack.stackTagCompound.getInteger("y2");
		int z2 = stack.stackTagCompound.getInteger("z2");
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(anchorX, anchorY, anchorZ));
		int minX = Math.min(x1, x2);
		int maxX = Math.max(x1, x2);
		int minY = Math.min(y1, y2);
		int maxY = Math.max(y1, y2);
		int minZ = Math.min(z1, z2);
		int maxZ = Math.max(z1, z2);
		
		try {
			JsonWriter writer = new JsonWriter(new FileWriter(config));
			writer.setIndent("  ");
			writer.beginObject();
			writer.name("components").beginArray();
			
			for(int x = minX; x <= maxX; x++) {
				for(int y = minY; y <= maxY; y++) {
					for(int z = minZ; z <= maxZ; z++) {
						
						int compY = y - anchorY; 
						int compX = 0;
						int compZ = 0;

						if(dir == ForgeDirection.SOUTH) {
							compX = anchorX - x;
							compZ = anchorZ - z;
						}
						if(dir == ForgeDirection.NORTH) {
							compX = x - anchorX;
							compZ = z - anchorZ;
						}

						if(dir == ForgeDirection.WEST) {
							compZ = x - anchorX;
							compX = anchorZ - z;
						}
						if(dir == ForgeDirection.EAST) {
							compZ = anchorX - x;
							compX = z - anchorZ;
						}
						
						if(x == anchorX && y == anchorY && z == anchorZ) continue;
						Block block = world.getBlock(x, y, z);
						int meta = world.getBlockMetadata(x, y, z);
						if(block == Blocks.air) continue;
						
						writer.beginObject().setIndent("");
						writer.name("block").value(Block.blockRegistry.getNameForObject(block));
						writer.name("x").value(compX);
						writer.name("y").value(compY);
						writer.name("z").value(compZ);
						writer.name("metas").beginArray().value(meta).endArray();
						writer.endObject().setIndent("  ");
					}
				}
			}
			writer.endArray();
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block b = world.getBlock(x, y, z);

		if(b == ModBlocks.cm_anchor) {
			this.setAnchor(stack, x, y, z);
			return true;
		}

		if(this.getAnchor(stack) == null) {
			return false;
		}
		if(!stack.stackTagCompound.hasKey("x1")) {
			stack.stackTagCompound.setInteger("x1", x);
			stack.stackTagCompound.setInteger("y1", y);
			stack.stackTagCompound.setInteger("z1", z);
		} else if(!stack.stackTagCompound.hasKey("x2")) {
			stack.stackTagCompound.setInteger("x2", x);
			stack.stackTagCompound.setInteger("y2", y);
			stack.stackTagCompound.setInteger("z2", z);
		} else {
			writeToFile(file, stack, world);
			stack.stackTagCompound.removeTag("x1");
			stack.stackTagCompound.removeTag("y1");
			stack.stackTagCompound.removeTag("z1");
			stack.stackTagCompound.removeTag("x2");
			stack.stackTagCompound.removeTag("y2");
			stack.stackTagCompound.removeTag("z2");
		}
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		super.addInformation(stack, player, list, ext);
		list.add(EnumChatFormatting.YELLOW + "Click Custom Machine Structure Positioning Anchor to");
		list.add(EnumChatFormatting.YELLOW + "Confirm the location of the custom machine core block.");
		list.add(EnumChatFormatting.YELLOW + "Output all blocks between Position1 and Position2 with");
		list.add(EnumChatFormatting.YELLOW + "metadata to \"CMstructureOutput.txt\" in hbmConfig.");
	}

	@Override
	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {
		ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
		List<String> text = new ArrayList();

		BlockPos anchor = getAnchor(stack);

		if(anchor == null) {

			text.add(EnumChatFormatting.RED + "No Anchor");
		} else {
			int anchorX = stack.stackTagCompound.getInteger("anchorX");
			int anchorY = stack.stackTagCompound.getInteger("anchorY");
			int anchorZ = stack.stackTagCompound.getInteger("anchorZ");
			text.add(EnumChatFormatting.GOLD + "Anchor: " + anchorX + " / " + anchorY + " / " + anchorZ);
			if(stack.stackTagCompound.hasKey("x1")) {
				int x1 = stack.stackTagCompound.getInteger("x1");
				int y1 = stack.stackTagCompound.getInteger("y1");
				int z1 = stack.stackTagCompound.getInteger("z1");

				text.add(EnumChatFormatting.YELLOW + "Position1: " + x1 + " / " + y1 + " / " + z1);
			}
			if(stack.stackTagCompound.hasKey("x2")) {
				int x2 = stack.stackTagCompound.getInteger("x2");
				int y2 = stack.stackTagCompound.getInteger("y2");
				int z2 = stack.stackTagCompound.getInteger("z2");
				text.add(EnumChatFormatting.YELLOW + "Position2: " + x2 + " / " + y2 + " / " + z2);
			}
		}

		ILookOverlay.printGeneric(event, this.getItemStackDisplayName(stack), 0xffff00, 0x404000, text);
	}
}
