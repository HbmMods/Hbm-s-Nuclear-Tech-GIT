package com.hbm.items.tool;

import com.hbm.interfaces.IFluidDuct;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.network.TileEntityPylon;

import api.hbm.energy.IEnergyConnector;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemAnalyzer extends Item {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int b, float f0, float f1, float f2)
    {
		Block block = world.getBlock(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(world.isRemote) {
			player.addChatMessage(new ChatComponentText(
					"Block: " + I18n.format(block.getUnlocalizedName() + ".name") + " (" + block.getUnlocalizedName() + ")"
					));
			
			player.addChatMessage(new ChatComponentText(
					"Meta: " + world.getBlockMetadata(x, y, z)
					));
		}
		
		if(!world.isRemote) {
			
			if(te == null) {
				player.addChatMessage(new ChatComponentText(
						"Tile Entity: none"));
			} else {
				
				if(te instanceof TileEntityDummy) {

					player.addChatMessage(new ChatComponentText(
							"Dummy Block, references TE at " + ((TileEntityDummy)te).targetX + " / " + ((TileEntityDummy)te).targetY + " / " + ((TileEntityDummy)te).targetZ));
					
					te = world.getTileEntity(((TileEntityDummy)te).targetX, ((TileEntityDummy)te).targetY, ((TileEntityDummy)te).targetZ);
				}
				
				String[] parts = te.toString().split("\\.");
				
				if(parts.length == 0)
					parts = new String[]{"error"};
				
				String post = parts[parts.length - 1];
				String name = post.split("@")[0];

				player.addChatMessage(new ChatComponentText(
						"Tile Entity: " + name));
				
				if(te instanceof IInventory) {
					
					player.addChatMessage(new ChatComponentText(
							"Slots: " + ((IInventory)te).getSizeInventory()));
				}
				
				if(te instanceof IEnergyConnector) {
					
					player.addChatMessage(new ChatComponentText(
							"Electricity: " + ((IEnergyConnector)te).getPower() + " HE"));
				}
				
				if(te instanceof IFluidDuct) {
					
					player.addChatMessage(new ChatComponentText(
							"Duct Type: " + ((IFluidDuct)te).getType().getLocalizedName()));
				}
				
				if(te instanceof TileEntityPylon) {
					
					/**
					 * this is a smoldering crater
					 */
				}
				
				if(te instanceof TileEntityLockableBase) {
					
					player.addChatMessage(new ChatComponentText(
							"Locked: " + ((TileEntityLockableBase)te).isLocked()));
					
					if(((TileEntityLockableBase)te).isLocked()) {

						//player.addChatMessage(new ChatComponentText(
						//		"Pins: " + ((TileEntityLockableBase)te).getPins()));
						player.addChatMessage(new ChatComponentText(
								"Pick Chance: " + (((TileEntityLockableBase)te).getMod() * 100D) + "%"));
					}
				}
			}

			player.addChatMessage(new ChatComponentText(
					"----------------------------"
					));
		}
		
		return true;
    }

}
