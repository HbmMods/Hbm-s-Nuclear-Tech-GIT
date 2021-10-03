package com.hbm.items.special;

import java.util.List;

import com.hbm.config.RadiationConfig;
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

	//AU198 	            64h		α	500.00Rad/s	2 much spice :(
	//PO210		           138d		α	075.00Rad/s	Spicy
	//AT209		             5h		β+	like 2k or sth idk bruv
	//AC227			    	22y		α	025.00Rad/s Spicy
	//TH232		14,000,000,000a		α	000.10Rad/s
	//U233		       160,000a		α	005.00Rad/s
	//U235		   700,000,000a		α	001.00Rad/s
	//U238		 4,500,000,000a		α	000.25Rad/s
	//NP237		     2,100,000a		α	002.50Rad/s
	//PU238		            88a		α	010.00Rad/s	Spicy
	//PU239		        24,000a		α	005.00Rad/s
	//PU240		         6,600a		α 	007.50Rad/s
	//TS294				   51ms		α1000000.00Rad/s AAAAAAAAAAAAA
	private static final boolean realisticRads = RadiationConfig.realisticRads;
	// Half-life: >1 year
	private static final float mod1 = 10000f;
	// Half-life: <1 year
	private static final float mod2 = 1000000f;
	// Testing the values
	private static final float sa326HL = 100000;
	private static final float sa327HL = sa326HL * 0.75f;
	public static final float co60 = realisticRads ? (mod1 / 5.3F) * 2 : 30F;
	public static final float sr90 = realisticRads ? mod1 / 28F : 15F;
	public static final float i131 = realisticRads ? mod2 / 0.021917808219178F : 150F;
	public static final float xe135 = realisticRads ? mod2 / 0.0010388127853881F : 1250F;
	public static final float cs137 = realisticRads ? mod2 / 30F : 20F;
	public static final float ta182 = realisticRads ? mod2 / 0.31232876712329F : 85F;
	public static final float au198 = realisticRads ? mod2 / 0.0073059360730594F : MainRegistry.isPolaroid11 ? 567.09F : 500.0F;// is le funi reference I swear
	public static final float bi209 = mod1 / 2.01E19F;
	public static final float po210 = realisticRads ? mod2 / 0.37808219178082F : 75.0F;
	public static final float at209 = realisticRads ? mod2 / 0.00022831050228311F : 2000.0F;
	public static final float ra226 = realisticRads ? mod1 / 16000 : 7.5F;
	public static final float ac227 = realisticRads ? mod1 / 22F : 25.0F;
	public static final float th232 = realisticRads ? mod1 / 14000000000L : 0.1F;
	public static final float pa233 = realisticRads ? mod2 / 0.073972602739726F : 100F;
	public static final float u233 = realisticRads ? mod1 / 160000F : 5.0F;
	public static final float u234 = realisticRads ? mod1 / 246000 : 2.5F;
	public static final float u235 = realisticRads ? mod1 / 700000000F : 1.0F;
	public static final float u238 = realisticRads ? mod1 / 4500000000F : 0.25F;
	public static final float thf = realisticRads ? (((th232 * 6) + (u233 * 3)) * 0.1F) : 1.75F;
	public static final float tha = ((th232 * 6) + (pa233 * 3)) * 0.1F;
	public static final float u = realisticRads ? ((u238 * 8 + u235) * 0.1F) : 0.35F;
	public static final float uf = realisticRads ? (u238 * 6F + u235 * 3F) / 10F : 0.5F;
	public static final float np237 = realisticRads ? mod1 / 2100000F : 2.5F;
	public static final float pu238 = realisticRads ? mod1 / 88F : 10.0F;
	public static final float pu239 = realisticRads ? mod1 / 24000F : 5.0F;
	public static final float pu240 = realisticRads ? mod1 / 6600F : 7.5F;
	public static final float pu241 = realisticRads ? mod1 / 14F : 20F;
	public static final float pu = realisticRads ? (((pu238 * 3F) + (pu239 * 4F) + (pu240 * 2F)) * 0.1F) : 7.5F;
	public static final float purg = realisticRads ?(((pu239 * 6F) + (pu240 * 3F)) * 0.1F) : 6.25F;
	public static final float puf = realisticRads ? (((purg * 2F) + u238) * 0.1F) : 4.25F;
	public static final float mox = realisticRads ? ((u238 + purg + u235) * 0.1F) : 2.5F;
	public static final float am241 = realisticRads ? mod1 / 432F : 8.5F;
	public static final float am242m = realisticRads ? mod1 / 141F : 9.5F;
	public static final float amrg = realisticRads ? ((am241 * 3) + (am242m * 6)) * 0.1F : 9F;
	public static final float amf = realisticRads ? ((u238 * 6) + (amrg * 3)) * 0.1F : 19F / 8F;
	public static final float cm242 = mod2 / 0.43835616438356F;// TODO No non-realistic RAD/s rates
	public static final float cm243 = mod1 / 29.1F;
	public static final float cm244 = mod1 / 18;
	public static final float cm245 = mod1 / 8500;
	public static final float cm246 = mod1 / 4730;
	public static final float cm247 = mod1 / 1.56e7F;
	public static final float cm248 = mod1 / 3.40e5F;
	public static final float cm250 = mod1 / 9000;
	public static final float bk247 = mod1 / 1380;
	public static final float bk249 = mod2 / 0.9F;
	public static final float cf249 = mod1 / 351;
	public static final float cf251 = mod1 / 900;
	public static final float cf252 = mod1 / 2.6F;
	public static final float es254 = mod2 / 0.73972602739726F;
	public static final float md258 = mod2 / 0.13972602739726F;
	public static final float cn285 = mod2 / 9.1958396752917E-7F;
	public static final float cn286 = mod2 / 2.5367833587012E-7F;
	public static final float ts294 = realisticRads ? mod1 / 1.617199391172E-9F : 1000000;
	public static final float sa326 = realisticRads ? (mod1 / sa326HL) * 10 : 15.0F;
	public static final float sa327 = realisticRads ? (mod1 / sa327HL) * 10 : 17.5F;
	public static final float sa327_drx = 0.00001F;
	public static final float saf = realisticRads ? ((np237 * 3) + (sa326 * 3)) * 0.1F : 5.85F;
	public static final float hes = ((np237 * 2) + (sa326 * 5)) * 0.1F;
	public static final float les = ((np237 * 4) + sa326) * 0.1F;

	public static final float sr = sa326 * 0.1F;
	public static final float trx = 25.0F;
	public static final float trn = 0.1F;
	public static final float wst = 15.0F;
	public static final float yc = u;
	public static final float fo = 10F;

	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float powder = ingot * 3;
	public static final float block = 10.0F;
	public static final float crystal = block;
	public static final float billet = 0.5F;
	public static final float rtg = billet * 3;
	public static final float rod = billet;
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
