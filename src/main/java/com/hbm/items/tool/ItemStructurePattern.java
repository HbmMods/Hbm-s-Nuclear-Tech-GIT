package com.hbm.items.tool;

import java.util.List;

import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemStructurePattern extends ItemStructureTool {
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		super.addInformation(stack, player, list, ext);
		list.add(EnumChatFormatting.YELLOW + "Click to print all <placeBlockAtCurrentPosition>");
		list.add(EnumChatFormatting.YELLOW + "lines for the current selection with blocks and metadata.");
	}

	@Override
	protected boolean dualUse() {
		return true;
	}

	@Override
	protected void doTheThing(ItemStack stack, World world, int x, int y, int z) {
		
		BlockPos pos = this.getAnchor(stack);
		if(pos == null) return;
		
		String message = "";
		int savedX = stack.stackTagCompound.getInteger("x");
		int savedY = stack.stackTagCompound.getInteger("y");
		int savedZ = stack.stackTagCompound.getInteger("z");

		int minX = Math.min(savedX, x) - pos.getX();
		int minY = Math.min(savedY, y) - pos.getY();
		int minZ = Math.min(savedZ, z) - pos.getZ();
		int maxX = Math.max(savedX, x) - pos.getX();
		int maxY = Math.max(savedY, y) - pos.getY();
		int maxZ = Math.max(savedZ, z) - pos.getZ();
		
		for(int ix = minX; ix <= maxX; ix++) {
			for(int iy = minY; iy <= maxY; iy++) {
				for(int iz = minZ; iz <= maxZ; iz++) {
					
					Block b = world.getBlock(ix + pos.getX(), iy + pos.getY(), iz + pos.getZ());
					if(b.isAir(world, ix + pos.getX(), iy + pos.getY(), iz + pos.getZ())) continue;
					
					int meta = world.getBlockMetadata(ix + pos.getX(), iy + pos.getY(), iz + pos.getZ());
					
					message += "placeBlockAtCurrentPosition(world, " + b.getUnlocalizedName() + ", " + meta + ", " + ix + ", " + iy + ", " + iz + ", box);\n";
				}
			}
		}
		
		System.out.print(message);
		writeToFile(message);
	}
}
