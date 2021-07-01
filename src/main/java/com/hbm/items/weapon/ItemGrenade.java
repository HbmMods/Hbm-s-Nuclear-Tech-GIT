package com.hbm.items.weapon;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBase;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBouncyBase;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGascan;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFBouncy;
import com.hbm.entity.grenade.EntityGrenadeIFBrimstone;
import com.hbm.entity.grenade.EntityGrenadeIFConcussion;
import com.hbm.entity.grenade.EntityGrenadeIFGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFHE;
import com.hbm.entity.grenade.EntityGrenadeIFHopwire;
import com.hbm.entity.grenade.EntityGrenadeIFImpact;
import com.hbm.entity.grenade.EntityGrenadeIFIncendiary;
import com.hbm.entity.grenade.EntityGrenadeIFMystery;
import com.hbm.entity.grenade.EntityGrenadeIFNull;
import com.hbm.entity.grenade.EntityGrenadeIFSpark;
import com.hbm.entity.grenade.EntityGrenadeIFSticky;
import com.hbm.entity.grenade.EntityGrenadeIFToxic;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeLunatic;
import com.hbm.entity.grenade.EntityGrenadeMIRV;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePC;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeStunning;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.grenade.EntityWastePearl;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemGrenade extends ItemCustomLore
{
	public int fuse = 4;
	public Class<? extends EntityGrenadeBase> grenadeEntityImpact = null;
	public Class<? extends EntityGrenadeBouncyBase> grenadeEntityBouncy = null;
	/** 
	 * Constructor for impact grenades
	 * @param nade - Grenade class to be used, must extend EntityGrenadeBase
	 */
	public ItemGrenade(Class<? extends EntityGrenadeBase> nade)
	{
		this(-1);
		assert nade != null : "Class cannot be null";
		grenadeEntityImpact = nade;
	}
	/** 
	 * Constructor for "bouncy" grenades
	 * @param fuse - Time in seconds until detonation
	 * @param nade - Grenade class to be used, must extend EntityGrenadeBouncyBase
	 */
	public ItemGrenade(int fuse, Class<? extends EntityGrenadeBouncyBase> nade)
	{
		this(fuse);
		assert nade != null : "Class cannot be null";
		grenadeEntityBouncy = nade;
	}
	/** Special grenades **/
	public ItemGrenade(int fuse)
	{
		maxStackSize = 16;
		this.fuse = fuse;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode)
			--stack.stackSize;

		worldIn.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			try
			{
				if (grenadeEntityBouncy != null)
					worldIn.spawnEntityInWorld(grenadeEntityBouncy.getConstructor(World.class, EntityLivingBase.class).newInstance(worldIn, player));
				if (grenadeEntityImpact != null)
					worldIn.spawnEntityInWorld(grenadeEntityImpact.getConstructor(World.class, EntityLivingBase.class).newInstance(worldIn, player));
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (this == ModItems.grenade_frag)
			{
				EntityGrenadeFrag frag = new EntityGrenadeFrag(worldIn, player);
				frag.shooter = player;
				worldIn.spawnEntityInWorld(frag);
			}
			if (this == ModItems.grenade_fire)
			{
				EntityGrenadeFire fire = new EntityGrenadeFire(worldIn, player);
				fire.shooter = player;
				worldIn.spawnEntityInWorld(fire);
			}
			if (this == ModItems.nuclear_waste_pearl)
				worldIn.spawnEntityInWorld(new EntityWastePearl(worldIn, player));

		}

		return stack;
	}
	
	private String translateFuse()
	{
		if(fuse == -1)
			return I18nUtil.resolveKey("desc.item.grenade.fuseImpact");
		
		if(fuse == 0)
			return I18nUtil.resolveKey("desc.item.grenade.fuseInstant");
		
		return fuse + "s";
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

//		list.add("Fuse: " + translateFuse());
		list.add(I18nUtil.resolveKey("desc.item.grenade.fuse", translateFuse()));
		if (ItemCustomLore.getHasLore(itemstack.getItem(), MainRegistry.polaroidID == 11))
			list.add("");
		super.addInformation(itemstack, player, list, bool);
	}
	
	public static int getFuseTicks(Item grenade)
	{
		return ((ItemGrenade)grenade).fuse * 20;
	}

}
