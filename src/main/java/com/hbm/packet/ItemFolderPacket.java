package com.hbm.packet;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemFluidIdentifier;

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
		
		@Override
		public IMessage onMessage(ItemFolderPacket m, MessageContext ctx) {
			
			//if(!Minecraft.getMinecraft().theWorld.isRemote)
					EntityPlayer p = ctx.getServerHandler().playerEntity;
					ItemStack stack = new ItemStack(Item.getItemById(m.item), 1, m.meta);
					
					if(p.capabilities.isCreativeMode) {
						p.inventory.addItemStackToInventory(stack.copy());
						return null;
					}

					if(stack.getItem() instanceof ItemFluidIdentifier) {
						
						if(p.inventory.hasItem(ModItems.plate_iron) && p.inventory.hasItem(Items.dye)) {
							p.inventory.consumeInventoryItem(ModItems.plate_iron);
							p.inventory.consumeInventoryItem(Items.dye);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() instanceof ItemAssemblyTemplate) {
						
						if(p.inventory.hasItem(Items.paper) && p.inventory.hasItem(Items.dye)) {
							p.inventory.consumeInventoryItem(Items.paper);
							p.inventory.consumeInventoryItem(Items.dye);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() instanceof ItemChemistryTemplate) {
						
						if(p.inventory.hasItem(Items.paper) && p.inventory.hasItem(Items.dye)) {
							p.inventory.consumeInventoryItem(Items.paper);
							p.inventory.consumeInventoryItem(Items.dye);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() instanceof ItemCassette) {
						if(p.inventory.hasItem(ModItems.plate_polymer) && p.inventory.hasItem(ModItems.plate_steel)) {
							p.inventory.consumeInventoryItem(ModItems.plate_polymer);
							p.inventory.consumeInventoryItem(ModItems.plate_steel);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() == ModItems.stamp_stone_plate || 
							stack.getItem() == ModItems.stamp_stone_wire || 
							stack.getItem() == ModItems.stamp_stone_circuit ||
							stack.getItem() == ModItems.stamp_stone_disc) {
						if(p.inventory.hasItem(ModItems.stamp_stone_flat)) {
							p.inventory.consumeInventoryItem(ModItems.stamp_stone_flat);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() == ModItems.stamp_iron_plate || 
							stack.getItem() == ModItems.stamp_iron_wire || 
							stack.getItem() == ModItems.stamp_iron_circuit ||
							stack.getItem() == ModItems.stamp_iron_disc) {
						if(p.inventory.hasItem(ModItems.stamp_iron_flat)) {
							p.inventory.consumeInventoryItem(ModItems.stamp_iron_flat);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() == ModItems.stamp_steel_plate || 
							stack.getItem() == ModItems.stamp_steel_wire || 
							stack.getItem() == ModItems.stamp_steel_circuit ||
							stack.getItem() == ModItems.stamp_steel_disc) {
						if(p.inventory.hasItem(ModItems.stamp_steel_flat)) {
							p.inventory.consumeInventoryItem(ModItems.stamp_steel_flat);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() == ModItems.stamp_titanium_plate || 
							stack.getItem() == ModItems.stamp_titanium_wire || 
							stack.getItem() == ModItems.stamp_titanium_circuit ||
							stack.getItem() == ModItems.stamp_titanium_disc) {
						if(p.inventory.hasItem(ModItems.stamp_titanium_flat)) {
							p.inventory.consumeInventoryItem(ModItems.stamp_titanium_flat);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() == ModItems.stamp_obsidian_plate || 
							stack.getItem() == ModItems.stamp_obsidian_wire || 
							stack.getItem() == ModItems.stamp_obsidian_circuit ||
							stack.getItem() == ModItems.stamp_obsidian_disc) {
						if(p.inventory.hasItem(ModItems.stamp_obsidian_flat)) {
							p.inventory.consumeInventoryItem(ModItems.stamp_obsidian_flat);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if(stack.getItem() == ModItems.stamp_schrabidium_plate || 
							stack.getItem() == ModItems.stamp_schrabidium_wire || 
							stack.getItem() == ModItems.stamp_schrabidium_circuit ||
							stack.getItem() == ModItems.stamp_schrabidium_disc) {
						if(p.inventory.hasItem(ModItems.stamp_schrabidium_flat)) {
							p.inventory.consumeInventoryItem(ModItems.stamp_schrabidium_flat);
							if(!p.inventory.addItemStackToInventory(stack.copy()))
									p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
					if (stack.getItem() == ModItems.stamp_desh_plate ||
							stack.getItem() == ModItems.stamp_desh_wire ||
							stack.getItem() == ModItems.stamp_desh_circuit||
							stack.getItem() == ModItems.stamp_desh_disc)
					{
						if (p.inventory.hasItem(ModItems.stamp_desh_flat))
						{
							p.inventory.consumeInventoryItem(ModItems.stamp_desh_flat);
							if (!p.inventory.addItemStackToInventory(stack.copy()))
								p.dropPlayerItemWithRandomChoice(stack, true);
						}
					}
			//}
			
			return null;
		}
	}
}
