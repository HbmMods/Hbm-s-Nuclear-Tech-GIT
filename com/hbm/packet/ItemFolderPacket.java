package com.hbm.packet;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemFluidIdentifier;
import com.hbm.tileentity.TileEntityMachineAssembler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

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
			//}
			
			return null;
		}
	}
}
