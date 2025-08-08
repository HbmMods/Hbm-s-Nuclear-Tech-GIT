package com.hbm.packet.toclient;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.armor.ArmorTrenchmaster;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
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

	public short type;
	public int receiverIndex;
	public int gunIndex;

	public GunAnimationPacket() { }

	public GunAnimationPacket(int type) {
		this.type = (short) type;
		this.receiverIndex = 0;
		this.gunIndex = 0;
	}

	public GunAnimationPacket(int type, int rec) {
		this.type = (short) type;
		this.receiverIndex = rec;
		this.gunIndex = 0;
	}

	public GunAnimationPacket(int type, int rec, int gun) {
		this.type = (short) type;
		this.receiverIndex = rec;
		this.gunIndex = gun;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readShort();
		receiverIndex = buf.readInt();
		gunIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(type);
		buf.writeInt(receiverIndex);
		buf.writeInt(gunIndex);
	}

	public static class Handler implements IMessageHandler<GunAnimationPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(GunAnimationPacket m, MessageContext ctx) {
			
			try {

				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				ItemStack stack = player.getHeldItem();
				int slot = player.inventory.currentItem;
				
				if(stack == null) return null;
				
				if(stack.getItem() instanceof ItemGunBaseNT) {
					handleSedna(player, stack, slot, AnimType.values()[m.type], m.receiverIndex, m.gunIndex);
				}
				
			} catch(Exception x) { }
			
			return null;
		}
		
		public static void handleSedna(EntityPlayer player, ItemStack stack, int slot, AnimType type, int receiverIndex, int gunIndex) {
			ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
			GunConfig config = gun.getConfig(stack, gunIndex);
			
			if(type == AnimType.CYCLE) {
				if(gunIndex < gun.lastShot.length) gun.lastShot[gunIndex] = System.currentTimeMillis();
				gun.shotRand = player.worldObj.rand.nextDouble();

				Receiver[] receivers = config.getReceivers(stack);
				if(receiverIndex >= 0 && receiverIndex < receivers.length) {
					Receiver rec = receivers[receiverIndex];
					BiConsumer<ItemStack, LambdaContext> onRecoil= rec.getRecoil(stack);
					if(onRecoil != null) onRecoil.accept(stack, new LambdaContext(config, player, player.inventory, receiverIndex));
				}
			}
			
			BiFunction<ItemStack, AnimType, BusAnimation> anims = config.getAnims(stack);
			BusAnimation animation = anims.apply(stack, type);
			
			if(animation == null && type == AnimType.RELOAD_EMPTY) {
				animation = anims.apply(stack, AnimType.RELOAD);
			}
			if(animation == null && (type == AnimType.ALT_CYCLE || type == AnimType.CYCLE_EMPTY)) {
				animation = anims.apply(stack, AnimType.CYCLE);
			}
			
			if(animation != null) {
				Minecraft.getMinecraft().entityRenderer.itemRenderer.resetEquippedProgress();
				Minecraft.getMinecraft().entityRenderer.itemRenderer.itemToRender = stack;
				boolean isReloadAnimation = type == AnimType.RELOAD || type == AnimType.RELOAD_CYCLE || type == AnimType.RELOAD_EMPTY;
				if(isReloadAnimation && ArmorTrenchmaster.isTrenchMaster(player)) animation.setTimeMult(0.5D);
				HbmAnimations.hotbar[slot][gunIndex] = new Animation(stack.getItem().getUnlocalizedName(), System.currentTimeMillis(), animation, type, isReloadAnimation && config.getReloadAnimSequential(stack));
			}
		}
	}
}
