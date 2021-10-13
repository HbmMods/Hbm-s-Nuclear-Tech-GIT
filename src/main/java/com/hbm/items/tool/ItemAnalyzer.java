package com.hbm.items.tool;

import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.conductor.TileEntityPylonRedWire;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityLockableBase;

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
				
				if(te instanceof IConsumer) {
					
					player.addChatMessage(new ChatComponentText(
							"Electricity: " + ((IConsumer)te).getPower() + " HE"));
				} else if(te instanceof ISource) {
					
					player.addChatMessage(new ChatComponentText(
							"Electricity: " + ((ISource)te).getSPower() + " HE"));
				}
				
				if(te instanceof IFluidContainer) {
					
					player.addChatMessage(new ChatComponentText(
							"Fluid Tanks:"));
					
					List<FluidTank> tanks = ((IFluidContainer)te).getTanks();
					
					for(int i = 0; i < tanks.size(); i++) {
						player.addChatMessage(new ChatComponentText(
								" *Tank " + (i + 1) + ": " + tanks.get(i).getFill() + "mB " + I18n.format(tanks.get(i).getTankType().getUnlocalizedName())));
					}
				}
				
				if(te instanceof IFluidDuct) {
					
					player.addChatMessage(new ChatComponentText(
							"Duct Type: " + I18n.format(((IFluidDuct)te).getType().getUnlocalizedName())));
				}
				
				if(te instanceof TileEntityPylonRedWire) {
					
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
