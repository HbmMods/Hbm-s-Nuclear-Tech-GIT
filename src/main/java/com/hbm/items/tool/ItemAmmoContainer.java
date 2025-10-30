package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.util.InventoryUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemAmmoContainer extends Item {
	
	public ItemAmmoContainer() {
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		List<ItemStack> stacks = new ArrayList();
		
		for(ItemStack inv : player.inventory.mainInventory) {
			if(inv != null && inv.getItem() instanceof ItemGunBaseNT) {
				ItemGunBaseNT gun = (ItemGunBaseNT) inv.getItem();
				if(gun.defaultAmmo != null) stacks.add(inv);
			}
		}
		
		if(stacks.size() <= 0) return stack;
		
		Collections.shuffle(stacks);
		
		int maxGunCount = 3;
		
		for(int i = 0; i < maxGunCount && i < stacks.size(); i++) {
			ItemStack gunStack = stacks.get(i);
			ItemGunBaseNT gun = (ItemGunBaseNT) gunStack.getItem();
			ItemStack remainder = InventoryUtil.tryAddItemToInventory(player.inventory.mainInventory, gun.defaultAmmo.copy());
			if(remainder != null && remainder.stackSize > 0) player.dropPlayerItemWithRandomChoice(remainder, false);
		}

		world.playSoundAtEntity(player, "hbm:item.unpack", 1.0F, 1.0F);
		player.inventoryContainer.detectAndSendChanges();
		stack.stackSize--;
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		String[] lines = I18nUtil.resolveKeyArray(this.getUnlocalizedName() + ".desc");
		for(String line : lines) list.add(EnumChatFormatting.YELLOW + line);
	}
}
