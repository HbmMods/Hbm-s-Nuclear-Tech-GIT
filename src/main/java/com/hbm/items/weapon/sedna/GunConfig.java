package com.hbm.items.weapon.sedna;

import java.util.function.BiConsumer;

import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.item.ItemStack;

public class GunConfig {
	
	/** List of receivers used by the gun, primary and secondary are usually indices 0 and 1 respectively, if applicable */
	public Receiver[] receivers;
	public float durability;
	public int drawDuration = 0;
	public Crosshair crosshair;
	/** Lambda function that determines what receiver the gun should use when a keybind is hit */
	//public Function<Triplet<ItemStack, EnumKeybind, GunConfig>, Receiver> receiverDecider;
	/** Lambda functions for clicking shit */
	public BiConsumer<ItemStack, GunConfig> onPressPrimary;
	public BiConsumer<ItemStack, GunConfig> onPressSecondary;
	public BiConsumer<ItemStack, GunConfig> onPressTertiary;
	public BiConsumer<ItemStack, GunConfig> onPressReload;
	/** Lambda functions for releasing the aforementioned shit */
	public BiConsumer<ItemStack, GunConfig> onReleasePrimary;
	public BiConsumer<ItemStack, GunConfig> onReleaseSecondary;
	public BiConsumer<ItemStack, GunConfig> onReleaseTertiary;
	public BiConsumer<ItemStack, GunConfig> onReleaseReload;
	
	public float getDurability(ItemStack stack) {
		return durability;
	}
	
	public GunConfig setReceivers(Receiver... receivers) {
		this.receivers = receivers;
		return this;
	}
}
