package com.hbm.items.food;

import java.util.Locale;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.ItemEnumMulti;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFlask extends ItemEnumMulti {
	
	public static enum EnumInfusion {	
		SHIELD,
		NITAN 
	}

	public ItemFlask() {
		super(EnumInfusion.class, true, true);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];

		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getIconString() + "_" + num.name().toLowerCase(Locale.US));
		}
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		if(!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if(world.isRemote)
			return stack;
			
		if(stack.getItemDamage() == EnumInfusion.SHIELD.ordinal()) {
			float infusion = 5F;
			props.maxShield = Math.min(props.shieldCap, props.maxShield + infusion);
			props.shield = Math.min(props.shield + infusion, props.getEffectiveMaxShield());
		}
		
		if(stack.getItemDamage() == EnumInfusion.NITAN.ordinal()) {
			props.nitanCount = props.nitanCount + 1;
			player.addPotionEffect(new PotionEffect(HbmPotion.nitan.id, 60 * 20 * 3, 0));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60 * 20 * 3, 3));
			
		}
		
		
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.drink;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(stack.getItemDamage() == EnumInfusion.SHIELD.ordinal() && HbmPlayerProps.getData(player).maxShield >= HbmPlayerProps.shieldCap)
			return stack;
		
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
}
