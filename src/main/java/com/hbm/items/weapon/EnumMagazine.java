package com.hbm.items.weapon;

import static com.hbm.handler.BulletConfigSyncingUtil.*;
import static com.hbm.lib.HbmCollection.*;

import java.util.Arrays;

import javax.annotation.CheckReturnValue;

import org.eclipse.collections.api.factory.primitive.IntLists;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.api.list.primitive.MutableIntList;

import com.google.common.annotations.Beta;
import com.hbm.handler.BulletConfigSyncingUtil;

import net.minecraft.item.ItemStack;

/*
 * ———————————No magazines?———————————
 * ⠀⣞⢽⢪⢣⢣⢣⢫⡺⡵⣝⡮⣗⢷⢽⢽⢽⣮⡷⡽⣜⣜⢮⢺⣜⢷⢽⢝⡽⣝
 * ⠸⡸⠜⠕⠕⠁⢁⢇⢏⢽⢺⣪⡳⡝⣎⣏⢯⢞⡿⣟⣷⣳⢯⡷⣽⢽⢯⣳⣫⠇
 * ⠀⠀⢀⢀⢄⢬⢪⡪⡎⣆⡈⠚⠜⠕⠇⠗⠝⢕⢯⢫⣞⣯⣿⣻⡽⣏⢗⣗⠏
 * ⠀⠪⡪⡪⣪⢪⢺⢸⢢⢓⢆⢤⢀⠀⠀⠀⠀⠈⢊⢞⡾⣿⡯⣏⢮⠷⠁
 * ⠀⠀⠀⠈⠊⠆⡃⠕⢕⢇⢇⢇⢇⢇⢏⢎⢎⢆⢄⠀⢑⣽⣿⢝⠲⠉
 * ⠀⠀⠀⠀⠀⡿⠂⠠⠀⡇⢇⠕⢈⣀⠀⠁⠡⠣⡣⡫⣂⣿⠯⢪⠰⠂
 * ⠀⠀⠀⠀⡦⡙⡂⢀⢤⢣⠣⡈⣾⡃⠠⠄⠀⡄⢱⣌⣶⢏⢊⠂
 * ⠀⠀⠀⠀⢝⡲⣜⡮⡏⢎⢌⢂⠙⠢⠐⢀⢘⢵⣽⣿⡿⠁⠁
 * ⠀⠀⠀⠀⠨⣺⡺⡕⡕⡱⡑⡆⡕⡅⡕⡜⡼⢽⡻⠏
 * ⠀⠀⠀⠀⣼⣳⣫⣾⣵⣗⡵⡱⡡⢣⢑⢕⢜⢕⡝
 * ⠀⠀⠀⣴⣿⣾⣿⣿⣿⡿⡽⡑⢌⠪⡢⡣⣣⡟
 * ⠀⠀⠀⡟⡾⣿⢿⢿⢵⣽⣾⣼⣘⢸⢸⣞⡟
 * ⠀⠀⠀⠀⠁⠇⠡⠩⡫⢿⣝⡻⡮⣒⢽⠋
 * ————————————————————————————————
 */
@Beta
public enum EnumMagazine
{
	/// Revolver speed loaders ///
	S_357_BASIC			(6, false, STEEL_REVOLVER, IRON_REVOLVER, LEAD_REVOLVER, DESH_REVOLVER),
	S_357_SATURNITE		(6, false, SATURNITE_REVOLVER, DESH_REVOLVER),
	S_357_GOLD			(6, false, GOLD_REVOLVER, STEEL_REVOLVER, IRON_REVOLVER, LEAD_REVOLVER, DESH_REVOLVER),
	S_357_SCHRAB		(6, false, SCHRABIDIUM_REVOLVER, GOLD_REVOLVER, STEEL_REVOLVER, IRON_REVOLVER, LEAD_REVOLVER, DESH_REVOLVER),
	S_357_NIGHTMARE1	(6, false, NIGHT_REVOLVER, DESH_REVOLVER),
	S_357_NIGHTMARE2	(6, false, NIGHT2_REVOLVER),
	S_357_BIO			(6, false, STEEL_HS, GOLD_HS, IRON_HS, LEAD_HS, DESH_HS),
	
	S_44_PIP			(6, false, false, m44Normal, M44_PIP),
	S_44_NOPIP			(6, false, m44Normal),
	S_44_BJ				(5, false, false, m44Normal, M44_BJ),
	S_44_SILVER			(6, false, false, m44Normal, M44_SILVER),
	S_44_RED			(64, false, m44All),
	
	/// Pistol magazines ///
	P_CURSED			(17, false, CURSED_REVOLVER, DESH_REVOLVER),
	P_UAC				(15, false, acp45),
	P_DEAGLE			(7, false, ae50),
	
	/// Rifle(-like) magazines ///
	R_MP40				(32, false, p9),
	R_THOMPSON			(30, false, acp45),
	R_UZI				(32, false, lr22),
	R_BAE				(40, false, r556),
	R_LLR				(60, false, p9),
	R_UAC_SMG			(50, false, acp45),
//	R_UAC_SMG_BIG		(100, false, acp45),
	R_UAC_DMR			(30, false, r762),
	R_UAC_HDMR			(30, false),// TODO Temporary
	R_UAC_HDMR_SNIPER	(15, false),// TODO Temporary
	R_UAC_CARBINE		(40, false, r556),
	R_FLECHETTE			(20, false, r556Flechette),
	R_AR15				(50, false, false, bmg50Flechette, bmg50),
	R_MLR				(45, false, r556),
	
	/// Box magazines ///
	B_CALAMITY			(50, false, true, r762),
	B_UAC_LMG			(75, false, true, r762),
	B_UAC_LMG_MISSILE	(25, false, true, new int[0]),// TODO Temporary
	
	/// Belts ///
	// Most belts are limited to 100 or less if man portable, but we can still stack them.
	// 5mm is an exception since it's smaller than the others.
	B_5MM				(200, true, false, r5),// 5mm is pre-linked.
	B_50BMG				(100, true, bmg50),
	B_762				(100, true, r762),
	
	/// Drum magazines ///
	D_M4				(24, false, g12);
	/// Other magazines ///
//	BOLTER				(30, false, b75);
	
	/*
	 * Duplicates not an issue but not recommended (for obvious reasons), order important.
	 * Performance difference between sets likely marginal with generally small config sizes.
	 * Empty lists not recommended.
	 */
	public final IntList bullets;
	public final short capacity;
	public final boolean belt, links;// Links is implicitly true if the mag is a belt, but box magazines should turn it on too.
	private EnumMagazine(int capacity, boolean belt, int... bullets)
	{
		this(capacity, belt, belt, bullets);
	}
	
	private EnumMagazine(int capacity, boolean belt, boolean links, int... bullets)
	{
		this.capacity = (short) capacity;
		this.belt = belt;
		this.links = links;
		this.bullets = IntLists.immutable.of(bullets);
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
		this.bullets = bullets.toImmutable();// Should return itself if already immutable
	}
	
	private EnumMagazine(int capacity, boolean belt, boolean links, IntList bullets, int... additional)
	{
		this.capacity = (short) capacity;
		this.belt = belt;
		this.links = links;
		
		final MutableIntList tmpList = bullets.toList();
		tmpList.addAll(additional);
		this.bullets = tmpList.asUnmodifiable();
	}
	
	private EnumMagazine(int capacity, boolean belt, boolean links, IntList... lists)
	{
		this.capacity = (short) capacity;
		this.belt = belt;
		this.links = links;
		
		final MutableIntList tmpList = IntLists.mutable.withInitialCapacity(Arrays.stream(lists).mapToInt(IntList::size).sum());// :mokou_pain:
		for (IntList list : lists)
			tmpList.addAll(list);
		this.bullets = tmpList.asUnmodifiable();
	}
	
	public static boolean isValidAmmo(EnumMagazine magazineType, ItemStack stack)
	{
		for (int i = 0; i < magazineType.bullets.size(); i++)
		{
			final int bulletID = magazineType.bullets.get(i);
			if (BulletConfigSyncingUtil.pullConfig(bulletID).ammo.matchesRecipe(stack, true))
				return true;
		}
		return false;
	}
	
	@CheckReturnValue
	public static int getBulletID(EnumMagazine magazineType, ItemStack stack)
	{
		for (int i = 0; i < magazineType.bullets.size(); i++)
		{
			final int bulletID = magazineType.bullets.get(i);
			if (BulletConfigSyncingUtil.pullConfig(bulletID).ammo.matchesRecipe(stack, true))
				return bulletID;
		}
		return -1;
	}
}
