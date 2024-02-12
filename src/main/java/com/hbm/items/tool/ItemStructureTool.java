package com.hbm.items.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public abstract class ItemStructureTool extends Item implements ILookOverlay {
	
	File file = new File(MainRegistry.configHbmDir, "structureOutput.txt");
	FileWriter writer;
	
	public void writeToFile(String message) {
		if(!GeneralConfig.enableDebugMode)
			return;
		
		try {
			if(!file.exists()) file.createNewFile();
			if(writer == null) writer = new FileWriter(file, true);
			
			writer.write(message);
			writer.flush();
		} catch(IOException e) {
			System.out.print("ItemStructureWand encountered an IOException!");
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		BlockPos anchor = this.getAnchor(stack);
		
		if(anchor == null)
			list.add(EnumChatFormatting.RED + "No anchor set! Right click an anchor to get started.");
		
		if(GeneralConfig.enableDebugMode)
			list.add(EnumChatFormatting.GREEN + "Will write to \"structureOutput.txt\" in hbmConfig.");
	}

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
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b == ModBlocks.structure_anchor) {
			this.setAnchor(stack, x, y, z);
			return true;
		}
		
		if(this.getAnchor(stack) == null) {
			return false;
		}
		
		if(!this.dualUse() && world.isRemote) {
			this.doTheThing(stack, world, x, y, z);
		} else {
			
			if(!stack.stackTagCompound.hasKey("x")) {
				stack.stackTagCompound.setInteger("x", x);
				stack.stackTagCompound.setInteger("y", y);
				stack.stackTagCompound.setInteger("z", z);
			} else {
				if(world.isRemote)
					this.doTheThing(stack, world, x, y, z);
				stack.stackTagCompound.removeTag("x");
				stack.stackTagCompound.removeTag("y");
				stack.stackTagCompound.removeTag("z");
			}
		}
		
		return true;
	}
	
	protected boolean dualUse() {
		return false;
	}
	
	protected abstract void doTheThing(ItemStack stack, World world, int x, int y, int z);

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
		List<String> text = new ArrayList();
		
		BlockPos anchor = getAnchor(stack);
		
		if(anchor == null) {
			text.add(EnumChatFormatting.RED + "No Anchor");
		} else {
			
			int dX = x - anchor.getX();
			int dY = y - anchor.getY();
			int dZ = z - anchor.getZ();
			text.add(EnumChatFormatting.YELLOW + "Position: " + dX + " / " + dY + " / " + dZ);
			
			if(this.dualUse() && stack.stackTagCompound.hasKey("x")) {
				int sX = Math.abs(x - stack.stackTagCompound.getInteger("x")) + 1;
				int sY = Math.abs(y - stack.stackTagCompound.getInteger("y")) + 1;
				int sZ = Math.abs(z - stack.stackTagCompound.getInteger("z")) + 1;
				text.add(EnumChatFormatting.GOLD + "Selection: " + sX + " / " + sY + " / " + sZ);
			}
		}
		
		if(Minecraft.getMinecraft().thePlayer.isSneaking())
			text.add("B: " + world.getBlock(x, y, z).getUnlocalizedName() + ", M: " + world.getBlockMetadata(x, y, z));
		
		ILookOverlay.printGeneric(event, this.getItemStackDisplayName(stack), 0xffff00, 0x404000, text);
	}
}
