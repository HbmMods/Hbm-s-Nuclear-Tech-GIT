package com.hbm.packet.toclient;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.IAnimatedItem;
import com.hbm.items.armor.ArmorTrenchmaster;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.render.anim.AnimationEnums.GunAnimation;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HbmAnimationPacket implements IMessage {

	public short type;
	public int receiverIndex;
	public int itemIndex;

	public HbmAnimationPacket() { }

	public HbmAnimationPacket(int type) {
		this.type = (short) type;
		this.receiverIndex = 0;
		this.itemIndex = 0;
	}

	public HbmAnimationPacket(int type, int rec) {
		this.type = (short) type;
		this.receiverIndex = rec;
		this.itemIndex = 0;
	}

	public HbmAnimationPacket(int type, int rec, int gun) {
		this.type = (short) type;
		this.receiverIndex = rec;
		this.itemIndex = gun;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readShort();
		receiverIndex = buf.readInt();
		itemIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(type);
		buf.writeInt(receiverIndex);
		buf.writeInt(itemIndex);
	}

	public static class Handler implements IMessageHandler<HbmAnimationPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(HbmAnimationPacket m, MessageContext ctx) {

			try {

				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				ItemStack stack = player.getHeldItem();
				int slot = player.inventory.currentItem;

				if(stack == null) return null;

				if(stack.getItem() instanceof ItemGunBaseNT) {
					handleSedna(player, stack, slot, GunAnimation.values()[m.type], m.receiverIndex, m.itemIndex);
				} else if(stack.getItem() instanceof IAnimatedItem) {
					handleItem(player, stack, slot, m.type, m.receiverIndex, m.itemIndex);
				}

			} catch(Exception x) { }

			return null;
		}

		public static void handleSedna(EntityPlayer player, ItemStack stack, int slot, GunAnimation type, int receiverIndex, int gunIndex) {
			ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
			GunConfig config = gun.getConfig(stack, gunIndex);

			if(type == GunAnimation.CYCLE) {
				if(gunIndex < gun.lastShot.length) gun.lastShot[gunIndex] = System.currentTimeMillis();
				gun.shotRand = player.worldObj.rand.nextDouble();

				Receiver[] receivers = config.getReceivers(stack);
				if(receiverIndex >= 0 && receiverIndex < receivers.length) {
					Receiver rec = receivers[receiverIndex];
					BiConsumer<ItemStack, LambdaContext> onRecoil= rec.getRecoil(stack);
					if(onRecoil != null) onRecoil.accept(stack, new LambdaContext(config, player, player.inventory, receiverIndex));
				}
			}

			BiFunction<ItemStack, GunAnimation, BusAnimation> anims = config.getAnims(stack);
			BusAnimation animation = anims.apply(stack, type);

			if(animation == null && (type == GunAnimation.ALT_CYCLE || type == GunAnimation.CYCLE_EMPTY)) {
				animation = anims.apply(stack, GunAnimation.CYCLE);
			}

			if(animation != null) {
				Minecraft.getMinecraft().entityRenderer.itemRenderer.resetEquippedProgress();
				Minecraft.getMinecraft().entityRenderer.itemRenderer.itemToRender = stack;
				boolean isReloadAnimation = type == GunAnimation.RELOAD || type == GunAnimation.RELOAD_CYCLE;
				if(isReloadAnimation && ArmorTrenchmaster.isTrenchMaster(player)) animation.setTimeMult(0.5D);
				HbmAnimations.hotbar[slot][gunIndex] = new Animation(stack.getItem().getUnlocalizedName(), System.currentTimeMillis(), animation, isReloadAnimation && config.getReloadAnimSequential(stack));
			}
		}

		public static void handleItem(EntityPlayer player, ItemStack stack, int slot, short type, int receiverIndex, int itemIndex) {
			IAnimatedItem<?> item = (IAnimatedItem<?>) stack.getItem();
			Class<? extends Enum<?>> animClass = item.getEnum();
			BusAnimation animation = item.getAnimation(EnumUtil.grabEnumSafely(animClass, type), stack);

			if(animation != null) {
				HbmAnimations.hotbar[slot][itemIndex] = new Animation(stack.getItem().getUnlocalizedName(), System.currentTimeMillis(), animation);
			}
		}

	}

}
