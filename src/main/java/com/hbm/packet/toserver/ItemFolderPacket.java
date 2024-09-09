package com.hbm.packet.toserver;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemCrucibleTemplate;
import com.hbm.items.machine.ItemFluidIdentifier;
import com.hbm.util.InventoryUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFolderPacket implements IMessage {

	int item;
	int meta;

	public ItemFolderPacket()
	{
		
	}

	public ItemFolderPacket(ItemStack stack)
	{
		this.item = Item.getIdFromItem(stack.getItem());
		this.meta = stack.getItemDamage();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		item = buf.readInt();
		meta = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(item);
		buf.writeInt(meta);
	}

	public static class Handler implements IMessageHandler<ItemFolderPacket, IMessage> {
		
		/*
		 * While it is still horrible, it is arguably less horrible than it was before.
		 */
		@Override
		public IMessage onMessage(ItemFolderPacket m, MessageContext ctx) {
			
			//if(!Minecraft.getMinecraft().theWorld.isRemote)
					EntityPlayer p = ctx.getServerHandler().playerEntity;
					ItemStack stack = new ItemStack(Item.getItemById(m.item), 1, m.meta);
					
					if(p.capabilities.isCreativeMode) {
						
						if(stack.getItem() == ModItems.assembly_template) {
							ComparableStack out = AssemblerRecipes.recipeList.get(stack.getItemDamage());
							
							if(out != null) {
								stack.setItemDamage(0);
								ItemAssemblyTemplate.writeType(stack, out);
							}
						}
						
						p.inventory.addItemStackToInventory(stack);
						return null;
					}

					if(stack.getItem() instanceof ItemFluidIdentifier) {
						tryMakeItem(p, stack, "plateIron", "dye");
						return null;
					}
					if(stack.getItem() instanceof ItemAssemblyTemplate) {
						tryMakeItem(p, stack, Items.paper, "dye");
						return null;
					}
					if(stack.getItem() instanceof ItemChemistryTemplate) {
						tryMakeItem(p, stack, Items.paper, "dye");
						return null;
					}
					if(stack.getItem() instanceof ItemCrucibleTemplate) {
						tryMakeItem(p, stack, Items.paper, "dye");
						return null;
					}
					if(stack.getItem() instanceof ItemCassette) {
						tryMakeItem(p, stack, ModItems.plate_polymer, "plateSteel");
						return null;
					}
					if(stack.getItem() == ModItems.stamp_stone_plate || 
							stack.getItem() == ModItems.stamp_stone_wire || 
							stack.getItem() == ModItems.stamp_stone_circuit) {
						tryConvert(p, ModItems.stamp_stone_flat, stack.getItem());
						return null;
					}
					if(stack.getItem() == ModItems.stamp_iron_plate || 
							stack.getItem() == ModItems.stamp_iron_wire || 
							stack.getItem() == ModItems.stamp_iron_circuit) {
						tryConvert(p, ModItems.stamp_iron_flat, stack.getItem());
						return null;
					}
					if(stack.getItem() == ModItems.stamp_steel_plate || 
							stack.getItem() == ModItems.stamp_steel_wire || 
							stack.getItem() == ModItems.stamp_steel_circuit) {
						tryConvert(p, ModItems.stamp_steel_flat, stack.getItem());
						return null;
					}
					if(stack.getItem() == ModItems.stamp_titanium_plate || 
							stack.getItem() == ModItems.stamp_titanium_wire || 
							stack.getItem() == ModItems.stamp_titanium_circuit) {
						tryConvert(p, ModItems.stamp_titanium_flat, stack.getItem());
						return null;
					}
					if(stack.getItem() == ModItems.stamp_obsidian_plate || 
							stack.getItem() == ModItems.stamp_obsidian_wire || 
							stack.getItem() == ModItems.stamp_obsidian_circuit) {
						tryConvert(p, ModItems.stamp_obsidian_flat, stack.getItem());
						return null;
					}
					if(stack.getItem() == ModItems.stamp_desh_plate || 
							stack.getItem() == ModItems.stamp_desh_wire || 
							stack.getItem() == ModItems.stamp_desh_circuit) {
						tryConvert(p, ModItems.stamp_desh_flat, stack.getItem());
						return null;
					}
			//}
			
			return null;
		}
		
		private void tryMakeItem(EntityPlayer player, ItemStack output, Object... ingredients) {
			
			//check
			for(Object o : ingredients) {
				
				if(o instanceof Item) {
					if(!player.inventory.hasItem((Item)o))
						return;
				}
				
				if(o instanceof String) {
					if(!InventoryUtil.hasOreDictMatches(player, (String)o, 1))
						return;
				}
			}
			
			//consume
			for(Object o : ingredients) {
				
				if(o instanceof Item) {
					player.inventory.consumeInventoryItem((Item)o);
				}
				
				if(o instanceof String) {
					InventoryUtil.consumeOreDictMatches(player, (String)o, 1);
				}
			}
			
			if(output.getItem() == ModItems.assembly_template) {
				ComparableStack out = AssemblerRecipes.recipeList.get(output.getItemDamage());
				
				if(out != null) {
					output.setItemDamage(0);
					ItemAssemblyTemplate.writeType(output, out);
				}
			}
			
			if(!player.inventory.addItemStackToInventory(output))
				player.dropPlayerItemWithRandomChoice(output, true);
		}
		
		private void tryConvert(EntityPlayer player, Item target, Item result) {
			
			for(int i = 0; i < player.inventory.mainInventory.length; i++) {
				
				ItemStack stack = player.inventory.mainInventory[i];
				
				if(stack != null && stack.getItem() == target) {
					player.inventory.mainInventory[i] = new ItemStack(result, stack.stackSize, stack.getItemDamage());
					return;
				}
			}
		}
	}
}
