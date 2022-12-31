package com.hbm.items.tool;

import java.util.List;

import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemStructureSolid extends ItemStructureTool {
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		super.addInformation(stack, player, list, ext);
		list.add(EnumChatFormatting.YELLOW + "Click to print a <fillWithMetadataBlocks> or <fillWithBlocks>");
		list.add(EnumChatFormatting.YELLOW + "line with wildcard block and metadata.");
	}

	@Override
	protected boolean dualUse() {
		return true;
	}

	@Override
	protected void doTheThing(ItemStack stack, World world, int x, int y, int z) {
		
		BlockPos pos = this.getAnchor(stack);
		if(pos == null) return;

		int savedX = stack.stackTagCompound.getInteger("x");
		int savedY = stack.stackTagCompound.getInteger("y");
		int savedZ = stack.stackTagCompound.getInteger("z");

		int minX = Math.min(savedX, x) - pos.getX();
		int minY = Math.min(savedY, y) - pos.getY();
		int minZ = Math.min(savedZ, z) - pos.getZ();
		int maxX = Math.max(savedX, x) - pos.getX();
		int maxY = Math.max(savedY, y) - pos.getY();
		int maxZ = Math.max(savedZ, z) - pos.getZ();
		//Assumes the last selected block is the one that all of them are.
		Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		String line;
		if(meta > 0)
			line = "fillWithMetadataBlocks(world, box, " + minX + ", " + minY + ", " + minZ + ", " + maxX + ", " + maxY + ", " + maxZ + ", " + b.getUnlocalizedName() +", " + meta +");\n";
		else
			line = "fillWithBlocks(world, box, " + minX + ", " + minY + ", " + minZ + ", " + maxX + ", " + maxY + ", " + maxZ + ", " + b.getUnlocalizedName() +");\n";
		
		System.out.print(line);
		writeToFile(line);
	}
}
