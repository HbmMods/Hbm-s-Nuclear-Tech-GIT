package com.hbm.blocks.machine.pile;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.pile.TileEntityPileFuel;

import api.hbm.block.IToolable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphiteFuel extends BlockGraphiteDrilledTE implements IToolable {

	@Override
	public TileEntity createNewTileEntity(World world, int mets) {
		return new TileEntityPileFuel();
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = super.getDrops(world, x, y, z, metadata, fortune);
		drops.add(new ItemStack(ModItems.pile_rod_uranium));
		return drops;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(!world.isRemote) {
			
			if(tool == ToolType.SCREWDRIVER) {
	
				int meta = world.getBlockMetadata(x, y, z) & 3;
				
				if(side == meta * 2 || side == meta * 2 + 1) {
					world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, meta, 3);
					this.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(ModItems.pile_rod_uranium));
				}
			}
			
			if(tool == ToolType.HAND_DRILL) {
				TileEntityPileFuel pile = (TileEntityPileFuel) world.getTileEntity(x, y, z);
				player.addChatComponentMessage(new ChatComponentText("CP1 FUEL ASSEMBLY " + x + " " + y + " " + z).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
				player.addChatComponentMessage(new ChatComponentText("HEAT: " + pile.heat + "/" + pile.maxHeat).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
				player.addChatComponentMessage(new ChatComponentText("DEPLETION: " + pile.progress + "/" + pile.maxProgress).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
				player.addChatComponentMessage(new ChatComponentText("FLUX: " + pile.lastNeutrons).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			}
		}
		
		return true;
	}
}
