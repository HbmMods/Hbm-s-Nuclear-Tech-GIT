package com.hbm.items.weapon;

import org.eclipse.collections.api.factory.primitive.IntSets;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.api.set.primitive.IntSet;

import com.hbm.lib.HbmCollection;

public enum EnumMagazine
{
	/// Revolver speed loaders ///
	S_357				(6, false),
	S_44				(6, false, HbmCollection.m44All),
	S_44_RED			(64, false, HbmCollection.m44All),
	
	/// Pistol magazines ///
	P_UAC				(15, false, HbmCollection.acp45),
	P_DEAGLE			(7, false, HbmCollection.ae50),
	
	/// Rifle(-like) magazines ///
	R_MP40				(32, false, HbmCollection.p9),
	R_THOMPSON			(30, false, HbmCollection.acp45),
	R_UZI				(30, false, HbmCollection.lr22),
	R_LLR				(60, false, HbmCollection.p9),
	R_UAC_SMG			(50, false, HbmCollection.acp45),
	R_UAC_SMG_BIG		(100, false, HbmCollection.acp45),
	R_UAC_DMR			(30, false, HbmCollection.r762),
	R_UAC_HDMR			(30, false),
	R_UAC_HDMR_SNIPER	(15, false),
	R_UAC_CARBINE		(40, false, HbmCollection.r556),
	R_FLECHETTE			(20, false, HbmCollection.r556Flechette),
	R_AR15				(50, false, HbmCollection.bmg50),
	R_MLR				(45, false, HbmCollection.r556),
	
	/// Box magazines ///
	B_CALAMITY			(50, false, true, HbmCollection.bmg50),
	B_UAC_LMG			(75, false, true, HbmCollection.r762),
	
	/// Other magazines ///
	DRUM_M4				(24, false, HbmCollection.g12),
	BOLTER				(30, false, HbmCollection.b75);
	
	public final IntSet bullets;
	public final short capacity;
	public final boolean belt, links;// Links is implicitly true if the mag is a belt, but box magazines should turn it on too
	private EnumMagazine(int capacity, boolean belt, int... bullets)
	{
		this(capacity, belt, belt, bullets);
	}
	
	private EnumMagazine(int capacity, boolean belt, boolean links, int... bullets)
	{
		this.capacity = (short) capacity;
		this.belt = belt;
		this.links = links;
		this.bullets = IntSets.immutable.of(bullets);
	}
	
	private EnumMagazine(int capacity, boolean belt, IntList bullets)
	{
		this(capacity, belt, belt, bullets);
	}
	
	private EnumMagazine(int capacity, boolean belt, boolean links, IntList bullets)
	{
		this.capacity = (short) capacity;
		this.belt = belt;
		this.links = links;
		this.bullets = IntSets.immutable.ofAll(bullets);
	}
}
