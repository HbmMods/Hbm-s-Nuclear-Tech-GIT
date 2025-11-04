package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.lib.RefStrings;
import com.hbm.util.InventoryUtil;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemAmmoContainer extends Item {

	@SideOnly(Side.CLIENT) protected IIcon altIcon;
	
	public ItemAmmoContainer() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon) {
		super.registerIcons(icon);
		this.altIcon = icon.registerIcon(RefStrings.MODID + ":ammo_container_alt");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta == 1 ? this.altIcon : this.itemIcon;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		boolean makeshift = stack.getItemDamage() == 1;
		
		List<ItemStack> stacks = new ArrayList();
		
		for(ItemStack inv : player.inventory.mainInventory) {
			if(inv != null && inv.getItem() instanceof ItemGunBaseNT) {
				ItemGunBaseNT gun = (ItemGunBaseNT) inv.getItem();
				if(gun.defaultAmmo != null && !(makeshift && gun.isDefaultExpensive)) stacks.add(inv);
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
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		String[] lines = I18nUtil.resolveKeyArray(this.getUnlocalizedName() + (stack.getItemDamage() == 1 ? ".1" : "") + ".desc");
		for(String line : lines) list.add(EnumChatFormatting.YELLOW + line);
	}
}
