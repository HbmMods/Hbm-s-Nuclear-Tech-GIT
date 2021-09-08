package com.hbm.entity.mob.siege;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class SiegeTier {
	
	public static SiegeTier[] tiers = new SiegeTier[100];
	private static int nextID = 0;
	
	public static SiegeTier DEFAULT_BUFF;
	public static SiegeTier CLAY;
	public static SiegeTier IRON;
	public static SiegeTier STONE;
	public static SiegeTier SILVER;
	public static SiegeTier GOLD;
	public static SiegeTier DESH;
	public static SiegeTier SCHRAB;
	public static SiegeTier DNT;
	
	public static void registerTiers() {
		DEFAULT_BUFF =	new SiegeTier(20)	.setDR(0.2F)										.setDMG(2F);
		CLAY =			new SiegeTier(30)	.setDR(0.2F)										.setDMG(3F);
		STONE =			new SiegeTier(40)	.setDR(0.3F)	.setDT(1F)				.setFP()	.setDMG(5F);
		IRON =			new SiegeTier(50)	.setDR(0.3F)	.setDT(2F)				.setFP()	.setDMG(7.5F);
		SILVER =		new SiegeTier(70)	.setDR(0.5F)	.setDT(3F)	.setNF()	.setFP()	.setDMG(10F)	.setSP(1.5F);
		GOLD =			new SiegeTier(100)	.setDR(0.5F)	.setDT(5F)	.setNF()	.setFP()	.setDMG(15F)	.setSP(1.5F);
		DESH =			new SiegeTier(150)	.setDR(0.7F)	.setDT(7F)	.setNF()	.setFP()	.setDMG(25F)	.setSP(1.5F);
		SCHRAB =		new SiegeTier(250)	.setDR(0.7F)	.setDT(10F)	.setNF()	.setFP()	.setDMG(50F)	.setSP(2F);
		DNT =			new SiegeTier(500)	.setDR(0.9F)	.setDT(20F)	.setNF()	.setFP()	.setDMG(100F)	.setSP(2F);
	}

	public int id;
	public float dt = 0F;
	public float dr = 0F;
	public float health = 20F;
	public float damageMod = 1F;
	public float speedMod = 1F;
	public boolean fireProof = false;
	public boolean noFall = false;
	public ItemStack dropItem = null;
	
	//so this is basically delegates but in java? or like, uh, storing lambdas? i don't know what it is but i feel like playing god. i like it.
	public Consumer<EntityLivingBase> delegate;
	
	public SiegeTier(float baseHealth) {
		this.id = nextID;
		SiegeTier.tiers[this.id] = this;
		nextID++;
		
		this.health = baseHealth;
	}
	
	private SiegeTier setDT(float dt) {
		this.dt = dt;
		return this;
	}
	
	private SiegeTier setDR(float dr) {
		this.dr = dr;
		return this;
	}
	
	private SiegeTier setDMG(float dmg) {
		this.damageMod = dmg;
		return this;
	}
	
	private SiegeTier setSP(float sp) {
		this.speedMod = sp;
		return this;
	}
	
	private SiegeTier setFP() {
		this.fireProof = true;
		return this;
	}
	
	private SiegeTier setNF() {
		this.noFall = true;
		return this;
	}
	
	private SiegeTier setDrop(Item drop) {
		this.dropItem = new ItemStack(drop);
		return this;
	}
	
	private SiegeTier setDrop(ItemStack drop) {
		this.dropItem = drop;
		return this;
	}
	
	private SiegeTier setAura(int range, PotionEffect... effects) {
		this.daisyChain(x -> SiegeTier.doAura(x, range, effects)); //HOLY SHIT THAT ACTUALLY WORKS!!
		return this;
	}
	
	private SiegeTier daisyChain(Consumer<EntityLivingBase> link) {
		
		if(this.delegate == null)
			this.delegate = link;
		else
			this.delegate.andThen(link); //HOLY FUCK!
		
		return this;
	}
	
	public void runDelegate(EntityLivingBase entity) {
		if(this.delegate != null) this.delegate.accept(entity);
	}
	
	/*
	 * DELEGATIONS
	 */
	private static void doAura(EntityLivingBase entity, int range, PotionEffect... effects) {
		
		List<EntityPlayer> players = entity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, entity.boundingBox.expand(range, range, range));
		
		for(EntityPlayer player : players) {
			
			if(player.getDistanceSqToEntity(entity) < range * range) {
				
				for(PotionEffect e : effects) {
					player.addPotionEffect(new PotionEffect(e));
				}
			}
		}
	}
}
