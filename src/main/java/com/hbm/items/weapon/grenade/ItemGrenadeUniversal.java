package com.hbm.items.weapon.grenade;

import java.util.List;
import java.util.Locale;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.grenade.ItemGrenadeFilling.EnumGrenadeFilling;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemGrenadeUniversal extends Item implements IEquipReceiver {
	
	/*
	 *  __________
	 * | ________ | ______ SHELL - determines what filling can be used, various bonuses like throw distance and fragmentation, max stack size
	 * ||        ||
	 * ||       __________ FILLING - the bang - high explosive, fragmentation, incendiary, etc
	 * ||________||
	 *  \   /\   /
	 *   \_ || _/
	 *    | || |
	 *    | |_____________ FUZE - what triggers the explosive, timed, impact, or airburst
	 *    | || |
	 *    | || |
	 *    | || |
	 *    | || |
	 *    | || |
	 *    | || |
	 *    / || \
	 *   |__||__|
	 *     {__}
	 */

	public static final String KEY_SHELL = "shell";
	public static final String KEY_FILLING = "filling";
	public static final String KEY_FUZE = "fuze";
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return getShell(stack).getStackLimit();
	}

	@Override
	public void onEquip(EntityPlayer player, ItemStack stack) {
		HbmPlayerProps.getData(player).grenadeDeployment = 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		EnumGrenadeShell shell = getShell(stack);
		if(HbmPlayerProps.getData(player).grenadeDeployment >= shell.getDrawDuration()) {
			if(!world.isRemote) {
				EntityGrenadeUniversal grenade = new EntityGrenadeUniversal(world, player, stack);
				world.spawnEntityInWorld(grenade);
			}
			stack.stackSize--;
			if(stack.stackSize > 0) this.onEquip(player, stack);
		}
		
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {

		if(!(entity instanceof EntityLivingBase)) return;
		EntityPlayer player = entity instanceof EntityPlayer ? (EntityPlayer) entity : null;

		if(player != null) {
			boolean wasHeld = ItemGunBaseNT.getIsEquipped(stack);

			if(!wasHeld && isHeld) {
				this.onEquip(player, stack);
			} else if(isHeld) {
				HbmPlayerProps.getData(player).grenadeDeployment++;
			}
		}

		ItemGunBaseNT.setIsEquipped(stack, isHeld);
	}
	
	public static EnumGrenadeShell getShell(ItemStack stack) {
		if(stack == null || !stack.hasTagCompound()) return EnumGrenadeShell.FRAG;
		int i = stack.stackTagCompound.getInteger(KEY_SHELL);
		return EnumUtil.grabEnumSafely(EnumGrenadeShell.class, i);
	}
	
	public static EnumGrenadeFilling getFilling(ItemStack stack) {
		if(stack == null || !stack.hasTagCompound()) return EnumGrenadeFilling.HE;
		int i = stack.stackTagCompound.getInteger(KEY_FILLING);
		return EnumUtil.grabEnumSafely(EnumGrenadeFilling.class, i);
	}
	
	public static EnumGrenadeFuze getFuze(ItemStack stack) {
		if(stack == null || !stack.hasTagCompound()) return EnumGrenadeFuze.S3;
		int i = stack.stackTagCompound.getInteger(KEY_FUZE);
		return EnumUtil.grabEnumSafely(EnumGrenadeFuze.class, i);
	}
	
	public static ItemStack make(EnumGrenadeShell shell, EnumGrenadeFilling filling, EnumGrenadeFuze fuze) {
		return make(shell, filling, fuze, 1);
	}
	
	public static ItemStack make(EnumGrenadeShell shell, EnumGrenadeFilling filling, EnumGrenadeFuze fuze, int amount) {
		ItemStack stack = new ItemStack(ModItems.grenade_universal, amount);
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger(KEY_SHELL, shell.ordinal());
		stack.stackTagCompound.setInteger(KEY_FILLING, filling.ordinal());
		stack.stackTagCompound.setInteger(KEY_FUZE, fuze.ordinal());
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(EnumGrenadeShell shell : EnumGrenadeShell.values()) for(EnumGrenadeFilling filling : EnumGrenadeFilling.values()) {
			if(filling.compatibleShells.contains(shell)) for(EnumGrenadeFuze fuze : EnumGrenadeFuze.values()) list.add(make(shell, filling, fuze));
		}
		
		list.add(make(EnumGrenadeShell.TECH, EnumGrenadeFilling.HE, EnumGrenadeFuze.S3));
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add("item.grenade_universal.shell." + this.getShell(stack).name().toLowerCase(Locale.US));
		list.add("item.grenade_universal.filling." + this.getFilling(stack).name().toLowerCase(Locale.US));
		list.add("item.grenade_universal.fuze." + this.getFuze(stack).name().toLowerCase(Locale.US));
	}
}
