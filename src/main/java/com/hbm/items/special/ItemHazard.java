package com.hbm.items.special;

import java.util.List;

import com.hbm.interfaces.IItemHazard;
import com.hbm.main.MainRegistry;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHazard extends ItemCustomLore implements IItemHazard {

	//AU192		            64h		α	500.00Rad/s	2 much spice :(
	//PO210		           138d		α	075.00Rad/s	Spicy
	//AC227			    	22y		α	025.00Rad/s Spicy
	//TH232		14,000,000,000a		α	000.10Rad/s
	//U233		       160,000a		α	005.00Rad/s
	//U235		   700,000,000a		α	001.00Rad/s
	//U238		 4,500,000,000a		α	000.25Rad/s
	//NP237		     2,100,000a		α	002.50Rad/s
	//PU238		            88a		α	010.00Rad/s	Spicy
	//PU239		        24,000a		α	005.00Rad/s
	//PU240		         6,600a		α 	007.50Rad/s
	//TS294				   51ms		α500000.00Rad/s AAAAAAAAAAAAA

	public static final float au198 = MainRegistry.isPolaroid11 ? 567.09F : 500.0F;// is le funi reference I swear
	public static final float po210 = 75.0F;
	public static final float ac227 = 25.0F;
	public static final float th232 = 0.1F;
	public static final float thf = 1.75F;
	public static final float u = 0.35F;
	public static final float u233 = 5.0F;
	public static final float u235 = 1.0F;
	public static final float u238 = 0.25F;
	public static final float uf = 0.5F;
	public static final float np237 = 2.5F;
	public static final float pu = 7.5F;
	public static final float purg = 6.25F;
	public static final float pu238 = 10.0F;
	public static final float pu239 = 5.0F;
	public static final float pu240 = 7.5F;
	public static final float puf = 4.25F;
	public static final float mox = 2.5F;
	public static final float sa326 = 15.0F;
	public static final float sa327 = 17.5F;
	public static final float sa327_drx = 0.00001F;
	public static final float saf = 5.85F;
	public static final float hes = ((np237 * 2) + (sa326 * 5)) * 0.1F;
	public static final float les = ((np237 * 4) + sa326) * 0.1F;
	public static final float ts294 = 100000;

	public static final float sr = sa326 * 0.1F;
	public static final float trx = 25.0F;
	public static final float trn = 0.1F;
	public static final float wst = 15.0F;
	public static final float yc = u;
	public static final float fo = 10F;

	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float powder = ingot;
	public static final float block = 10.0F;
	public static final float crystal = block;
	public static final float billet = 0.5F;
	public static final float rtg = billet * 3;
	public static final float rod = 0.5F;
	public static final float rod_dual = rod * 2;
	public static final float rod_quad = rod * 4;
	
	ItemHazardModule module;
	
	public ItemHazard() {
		this.module = new ItemHazardModule();
	}

	@Override
	public ItemHazardModule getModule() {
		return this.module;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		if(entity instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase) entity, stack.stackSize, i, b);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		super.addInformation(stack, player, list, bool);
		this.module.addInformation(stack, player, list, bool);
	}
	
	/*
	 * DEPRECATED CTORS
	 */
	@Deprecated()
	public ItemHazard(float radiation) {
		this();
		this.module.addRadiation(radiation);
	}

	@Deprecated()
	public ItemHazard(float radiation, boolean fire) {
		this();
		this.module.addRadiation(radiation);
		if(fire) this.module.addFire(5);
	}

	@Deprecated()
	public ItemHazard(float radiation, boolean fire, boolean blinding) {
		this();
		this.module.addRadiation(radiation);
		if(blinding) this.module.addBlinding();
		if(fire) this.module.addFire(5);
	}
	@Deprecated
	public ItemHazard(float radiation, float drx, boolean fire, boolean blinding)
	{
		this();
		this.module.addRadiation(radiation);
		this.module.addDigamma(drx);
		if (blinding)
			this.module.addBlinding();
		if (fire)
			this.module.addFire(5);
	}
	
	@Override
	public ItemHazard setRarity(EnumRarity rarity)
	{
		return (ItemHazard)super.setRarity(rarity);
	}
}
