package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.pile.TileEntityPileBreedingFuel;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphiteBreedingFuel extends BlockGraphiteDrilledTE implements IToolable {
	
	@Override
	public TileEntity createNewTileEntity(World world, int mets) {
		return new TileEntityPileBreedingFuel();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.blockIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_lithium_aluminum");
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(!world.isRemote) {
			
			if(tool == ToolType.SCREWDRIVER) {
	
				int meta = world.getBlockMetadata(x, y, z);
				int cfg = meta & 3;
				
				if(side == cfg * 2 || side == cfg * 2 + 1) {
					world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, meta, 3);
					this.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(ModItems.pile_rod_lithium));
				}
			}
			
			if(tool == ToolType.HAND_DRILL) {
				TileEntityPileBreedingFuel pile = (TileEntityPileBreedingFuel) world.getTileEntity(x, y, z);
				player.addChatComponentMessage(new ChatComponentText("CP1 FUEL ASSEMBLY " + x + " " + y + " " + z).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
				player.addChatComponentMessage(new ChatComponentText("DEPLETION: " + pile.progress + "/" + pile.maxProgress).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
				player.addChatComponentMessage(new ChatComponentText("FLUX: " + pile.lastNeutrons).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			}
		}
		
		return true;
	}
	
	@Override
	protected Item getInsertedItem() {
		return ModItems.pile_rod_lithium;
	}
	
}
