package com.hbm.packet.toclient;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.render.item.weapon.sedna.ItemRenderWeaponBase;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class MuzzleFlashPacket implements IMessage {
	
	private int entityID;
	//private int gunIndex; //e.g. akimbo
	
	public MuzzleFlashPacket() { }
	
	//public MuzzleFlashPacket(EntityLivingBase entity) { this(entity, 0); }
	
	public MuzzleFlashPacket(EntityLivingBase entity) {
		this.entityID = entity.getEntityId();
		//this.gunIndex = index;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		//buf.writeInt(gunIndex);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		//this.gunIndex = buf.readInt();
	}
	
	public static class Handler implements IMessageHandler<MuzzleFlashPacket, IMessage> {
		
		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage(MuzzleFlashPacket m, MessageContext ctx) {
			EntityLivingBase entity = (EntityLivingBase) Minecraft.getMinecraft().theWorld.getEntityByID(m.entityID);
			if(entity == null || entity == Minecraft.getMinecraft().thePlayer) return null; //packets are sent to the player who fired
			ItemStack stack = entity.getHeldItem();
			if(stack == null) return null;
			
			if(stack.getItem() instanceof ItemGunBaseNT) {
				ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
				
				ItemRenderWeaponBase.flashMap.put(entity, System.currentTimeMillis());
			}
			
			return null;
		}
		
	}
}
