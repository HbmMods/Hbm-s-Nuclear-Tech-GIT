package com.hbm.packet.toserver;

import java.util.Random;

import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.inventory.gui.GUIScreenBobmazon.Offer;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemBobmazonPacket implements IMessage {

	int offer;

	public ItemBobmazonPacket()
	{
		
	}

	public ItemBobmazonPacket(EntityPlayer player, Offer offer)
	{
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.bobmazon_materials)
			this.offer = BobmazonOfferFactory.materials.indexOf(offer);
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.bobmazon_machines)
			this.offer = BobmazonOfferFactory.machines.indexOf(offer);
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.bobmazon_weapons)
			this.offer = BobmazonOfferFactory.weapons.indexOf(offer);
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.bobmazon_tools)
			this.offer = BobmazonOfferFactory.tools.indexOf(offer);
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.bobmazon_hidden)
			this.offer = BobmazonOfferFactory.special.indexOf(offer);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		offer = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(offer);
	}

	public static class Handler implements IMessageHandler<ItemBobmazonPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ItemBobmazonPacket m, MessageContext ctx) {
			
			EntityPlayerMP p = ctx.getServerHandler().playerEntity;
			World world = p.worldObj;

			Offer offer = null;
			if(p.getHeldItem() != null && p.getHeldItem().getItem() == ModItems.bobmazon_materials)
				offer = BobmazonOfferFactory.materials.get(m.offer);
			if(p.getHeldItem() != null && p.getHeldItem().getItem() == ModItems.bobmazon_machines)
				offer = BobmazonOfferFactory.machines.get(m.offer);
			if(p.getHeldItem() != null && p.getHeldItem().getItem() == ModItems.bobmazon_weapons)
				offer = BobmazonOfferFactory.weapons.get(m.offer);
			if(p.getHeldItem() != null && p.getHeldItem().getItem() == ModItems.bobmazon_tools)
				offer = BobmazonOfferFactory.tools.get(m.offer);
			if(p.getHeldItem() != null && p.getHeldItem().getItem() == ModItems.bobmazon_hidden)
				offer = BobmazonOfferFactory.special.get(m.offer);
			
			if(offer == null) {
				p.addChatMessage(new ChatComponentText("[BOBMAZON] There appears to be a mismatch between the offer you have requested and the offers that exist."));
				p.addChatMessage(new ChatComponentText("[BOBMAZON] Engaging fail-safe..."));
				p.attackEntityFrom(ModDamageSource.nuclearBlast, 1000);
				p.motionY = 2.0D;
				return null;
			}
			
			ItemStack stack = offer.offer;
			
			Achievement req = offer.requirement.achievement;
			
			if(req != null && p.func_147099_x().hasAchievementUnlocked(req) || p.capabilities.isCreativeMode) {
				
				if(countCaps(p) >= offer.cost || p.capabilities.isCreativeMode) {
					
					payCaps(p, offer.cost);
					p.inventoryContainer.detectAndSendChanges();
					
					Random rand = world.rand;
					EntityBobmazon bob = new EntityBobmazon(world);
					bob.posX = p.posX + rand.nextGaussian() * 10;
					bob.posY = 300;
					bob.posZ = p.posZ + rand.nextGaussian() * 10;
					bob.payload = stack.copy();
					
					world.spawnEntityInWorld(bob);
				} else {
					p.addChatMessage(new ChatComponentText("[BOBMAZON] Not enough caps!"));
				}
				
			} else {
				p.addChatMessage(new ChatComponentText("[BOBMAZON] Achievement requirement not met!"));
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
							item == ModItems.cap_sparkle)
						count += stack.stackSize;
					
				}
			}
			
			return count;
		}
		
		private void payCaps(EntityPlayer player, int price) {
			
			if(price == 0)
				return;
			
			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				
				ItemStack stack = player.inventory.getStackInSlot(i);
				
				if(stack != null) {
					
					Item item = stack.getItem();
					
					if(item == ModItems.cap_fritz ||
							item == ModItems.cap_korl ||
							item == ModItems.cap_nuka ||
							item == ModItems.cap_quantum ||
							item == ModItems.cap_rad ||
							item == ModItems.cap_sparkle) {
						
						int size = stack.stackSize;
						for(int j = 0; j < size; j++) {
							
							player.inventory.decrStackSize(i, 1);
							price--;
							
							if(price == 0)
								return;
						}
					}
				}
			}
		}
	}
}
