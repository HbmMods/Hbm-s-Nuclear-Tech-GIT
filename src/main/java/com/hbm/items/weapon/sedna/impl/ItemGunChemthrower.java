package com.hbm.items.weapon.sedna.impl;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.entity.projectile.EntityChemical;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.mags.MagazineFluid;
import com.hbm.render.anim.HbmAnimations.AnimType;

import api.hbm.fluidmk2.IFillableItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class ItemGunChemthrower extends ItemGunBaseNT implements IFillableItem {
	
	public static final int CONSUMPTION = 3;

	public ItemGunChemthrower(WeaponQuality quality, GunConfig... cfg) {
		super(quality, cfg);
	}
	
	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return getFluidType(stack) == type || this.getMagCount(stack) == 0;
	}
	
	public static final int transferSpeed = 50;

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		
		if(!acceptsFluid(type, stack)) return amount;
		if(this.getMagCount(stack) == 0) this.setMagType(stack, type.getID());
		
		int fill = this.getMagCount(stack);
		int req = this.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getCapacity(stack) - fill;
		int toFill = Math.min(amount, req);
		toFill = Math.min(toFill, transferSpeed);
		this.setMagCount(stack, fill + toFill);
		
		return amount - toFill;
	}
	
	public FluidType getFluidType(ItemStack stack) {
		return Fluids.fromID(this.getMagType(stack));
	}

	@Override
	public boolean providesFluid(FluidType type, ItemStack stack) {
		return getFluidType(stack) == type;
	}

	@Override
	public int tryEmpty(FluidType type, int amount, ItemStack stack) {
		int fill = this.getMagCount(stack);
		int toUnload = Math.min(fill, amount);
		toUnload = Math.min(toUnload, transferSpeed);
		this.setMagCount(stack, fill - toUnload);
		return toUnload;
	}

	@Override public FluidType getFirstFluidType(ItemStack stack) { return Fluids.fromID(this.getMagType(stack)); }
	@Override public int getFill(ItemStack stack) { return this.getMagCount(stack); }
	
	public static int getMagType(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, MagazineFluid.KEY_MAG_TYPE + 0); }
	public static void setMagType(ItemStack stack, int value) { ItemGunBaseNT.setValueInt(stack, MagazineFluid.KEY_MAG_TYPE + 0, value); }
	public static int getMagCount(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, MagazineFluid.KEY_MAG_COUNT + 0); }
	public static void setMagCount(ItemStack stack, int value) { ItemGunBaseNT.setValueInt(stack, MagazineFluid.KEY_MAG_COUNT + 0, value); }
	
	public static BiFunction<ItemStack, LambdaContext, Boolean> LAMBDA_CAN_FIRE = (stack, ctx) -> { return ctx.config.getReceivers(stack)[0].getMagazine(stack).getAmount(stack, ctx.inventory) >= CONSUMPTION; };

	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_FIRE = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		EntityPlayer player = ctx.getPlayer();
		int index = ctx.configIndex;
		ItemGunBaseNT.playAnimation(player, stack, AnimType.CYCLE, ctx.configIndex);
		
		Receiver primary = ctx.config.getReceivers(stack)[0];
		IMagazine mag = primary.getMagazine(stack);
		
		Vec3 offset = primary.getProjectileOffset(stack);
		double forwardOffset = offset.xCoord;
		double heightOffset = offset.yCoord;
		double sideOffset = offset.zCoord;
		
		EntityChemical chem = new EntityChemical(entity.worldObj, entity, sideOffset, heightOffset, forwardOffset);
		chem.setFluid((FluidType) mag.getType(stack, ctx.inventory));
		entity.worldObj.spawnEntityInWorld(chem);
		
		mag.useUpAmmo(stack, ctx.inventory, CONSUMPTION);
		ItemGunBaseNT.setWear(stack, index, Math.min(ItemGunBaseNT.getWear(stack, index) + 1F, ctx.config.getDurability(stack)));
	};
}
