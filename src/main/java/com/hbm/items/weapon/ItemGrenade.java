package com.hbm.items.weapon;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.hbm.entity.grenade.EntityGrenadeBase;
import com.hbm.entity.grenade.EntityGrenadeBouncyBase;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		assert EntityGrenadeBase.class.isAssignableFrom(nade) : "Class must extend EntityGrenadeBase";
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
		assert EntityGrenadeBouncyBase.class.isAssignableFrom(nade) : "Class must extend EntityGrenadeBouncyBase";
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
				e.printStackTrace();
			}
			if (this == ModItems.grenade_flare)
				worldIn.spawnEntityInWorld(new EntityGrenadeFlare(worldIn, player));
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
		if (ItemCustomLore.getHasLore(itemstack.getItem()))
			list.add("");
		super.addInformation(itemstack, player, list, bool);
	}
	
	public static int getFuseTicks(Item grenade)
	{
		return ((ItemGrenade)grenade).fuse * 20;
	}

}
