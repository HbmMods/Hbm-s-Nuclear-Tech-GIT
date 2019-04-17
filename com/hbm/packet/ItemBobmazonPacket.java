package com.hbm.packet;

import java.util.List;
import java.util.Random;

import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.inventory.gui.GUIScreenBobmazon.Offer;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemCassette;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemFluidIdentifier;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemBobmazonPacket implements IMessage {

	int item;
	int stacksize;
	int meta;
	int cost;
	int achievement;

	public ItemBobmazonPacket()
	{
		
	}

	public ItemBobmazonPacket(Offer offer)
	{
		this.item = Item.getIdFromItem(offer.offer.getItem());
		this.stacksize = offer.offer.stackSize;
		this.meta = offer.offer.getItemDamage();
		this.cost = offer.cost;

		if(offer.requirement.achievement == MainRegistry.bobMetalworks)
			this.achievement = 0;
		if(offer.requirement.achievement == MainRegistry.bobAssembly)
			this.achievement = 1;
		if(offer.requirement.achievement == MainRegistry.bobChemistry)
			this.achievement = 2;
		if(offer.requirement.achievement == MainRegistry.bobOil)
			this.achievement = 3;
		if(offer.requirement.achievement == MainRegistry.bobNuclear)
			this.achievement = 4;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		item = buf.readInt();
		stacksize = buf.readInt();
		meta = buf.readInt();
		cost = buf.readInt();
		achievement = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(item);
		buf.writeInt(stacksize);
		buf.writeInt(meta);
		buf.writeInt(cost);
		buf.writeInt(achievement);
	}

	public static class Handler implements IMessageHandler<ItemBobmazonPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ItemBobmazonPacket m, MessageContext ctx) {
			
			EntityPlayerMP p = ctx.getServerHandler().playerEntity;
			World world = p.worldObj;
			ItemStack stack = new ItemStack(Item.getItemById(m.item), m.stacksize, m.meta);
			
			Achievement req = null;

			if(m.achievement == 0)
				req = MainRegistry.bobMetalworks;
			if(m.achievement == 1)
				req = MainRegistry.bobAssembly;
			if(m.achievement == 2)
				req = MainRegistry.bobChemistry;
			if(m.achievement == 3)
				req = MainRegistry.bobOil;
			if(m.achievement == 4)
				req = MainRegistry.bobNuclear;
			
			if(req != null && p.func_147099_x().hasAchievementUnlocked(req)) {
				
				if(countCaps(p) >= m.cost) {
					
					payCaps(p, m.cost);
					
					Random rand = world.rand;
					EntityBobmazon bob = new EntityBobmazon(world);
					bob.posX = p.posX + rand.nextGaussian() * 10;
					bob.posY = 300;
					bob.posZ = p.posZ + rand.nextGaussian() * 10;
					bob.payload = stack;
					
					world.spawnEntityInWorld(bob);
				} else {
					p.addChatMessage(new ChatComponentText("[BOBMAZON] Not enough caps!"));
				}
				
			} else {
				p.addChatMessage(new ChatComponentText("[BOBMAZON] Requirements not met!"));
			}
			
			return null;
		}
		
		private int countCaps(EntityPlayer player) {
			
			int count = 0;
			
			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				
				ItemStack stack = player.inventory.getStackInSlot(i);
				
				if(stack != null) {
					
					Item item = stack.getItem();
					
					if(item == ModItems.cap_fritz ||
							item == ModItems.cap_korl ||
							item == ModItems.cap_nuka ||
							item == ModItems.cap_quantum ||
							item == ModItems.cap_rad ||
							item == ModItems.cap_sparkle ||
							item == ModItems.cap_star ||
							item == ModItems.cap_sunset)
						count += stack.stackSize;
					
				}
			}
			
			return count;
		}
		
		private void payCaps(EntityPlayer player, int price) {
			
			if(price == 0)
				return;

			int count = 0;
			
			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				
				ItemStack stack = player.inventory.getStackInSlot(i);
				
				if(stack != null) {
					
					Item item = stack.getItem();
					
					if(item == ModItems.cap_fritz ||
							item == ModItems.cap_korl ||
							item == ModItems.cap_nuka ||
							item == ModItems.cap_quantum ||
							item == ModItems.cap_rad ||
							item == ModItems.cap_sparkle ||
							item == ModItems.cap_star ||
							item == ModItems.cap_sunset) {
						
						for(int j = 0; j < stack.stackSize; j++) {
							
							player.inventory.decrStackSize(i, 1);
							count++;
							
							if(count == price)
								return;
						}
					}
				}
			}
		}
	}
}
