package com.hbm.packet;

import com.hbm.items.weapon.ItemGunBase;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.anim.HbmAnimations.Animation;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GunAnimationPacket implements IMessage {

	int type;

	public GunAnimationPacket() { }

	public GunAnimationPacket(int type) {
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<GunAnimationPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(GunAnimationPacket m, MessageContext ctx) {
			
			try {

				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				ItemStack stack = player.getHeldItem();
				int slot = player.inventory.currentItem;
				
				if(stack == null)
					return null;
				
				if(!(stack.getItem() instanceof ItemGunBase))
					return null;
				
				if(m.type < 0 || m.type >= AnimType.values().length)
					return null;
				
				AnimType type = AnimType.values()[m.type];
				ItemGunBase base = (ItemGunBase) stack.getItem();

				BusAnimation animation = base.getAnimation(stack, type);

				// Fallback to regular reload if no empty reload animation
				if(animation == null && type == AnimType.RELOAD_EMPTY) {
					animation = base.getAnimation(stack, AnimType.RELOAD);
				}

				// Fallback to regular CYCLE if no ALT_CYCLE (or CYCLE_EMPTY) exists
				if(animation == null && (type == AnimType.ALT_CYCLE || type == AnimType.CYCLE_EMPTY)) {
					animation = base.getAnimation(stack, AnimType.CYCLE);
				}
				
				if(animation != null) {
					boolean isReloadAnimation = type == AnimType.RELOAD || type == AnimType.RELOAD_CYCLE || type == AnimType.RELOAD_EMPTY;
					HbmAnimations.hotbar[slot] = new Animation(stack.getItem().getUnlocalizedName(), System.currentTimeMillis(), animation, isReloadAnimation && base.mainConfig.reloadAnimationsSequential);
				}
				
			} catch(Exception x) { }
			
			return null;
		}
	}
}
