package com.hbm.items.weapon.sedna;

import java.util.function.Function;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.item.ItemStack;

public class GunConfig {
	
	/** List of receivers used by the gun, primary and secondary are usually indices 0 and 1 respectively, if applicable */
	public Receiver[] receivers;
	public float durability;
	/** Lambda function that determines what receiver the gun should use when a keybind is hit */
	public Function<Triplet<ItemStack, EnumKeybind, GunConfig>, Receiver> receiverDecider;
	
	public GunConfig setReceivers(Receiver... receivers) {
		this.receivers = receivers;
		return this;
	}
	
	public Receiver getReceiver(ItemStack stack, EnumKeybind keybind) {
		
		if(receiverDecider != null) {
			return receiverDecider.apply(new Triplet(stack, keybind, this));
		}
		
		return null;
	}
	
	/* Standard implementations for receiver deciders */
	public static Function<Triplet<ItemStack, EnumKeybind, GunConfig>, Receiver> receiverDeciderSingle = (x) -> { return x.getY() == EnumKeybind.GUN_PRIMARY ? x.getZ().receivers[0] : null; };
	public static Function<Triplet<ItemStack, EnumKeybind, GunConfig>, Receiver> receiverDeciderDouble = (x) -> { return x.getY() == EnumKeybind.GUN_PRIMARY ? x.getZ().receivers[0] : x.getY() == EnumKeybind.GUN_SECONDARY ? x.getZ().receivers[1] : null; };
}
