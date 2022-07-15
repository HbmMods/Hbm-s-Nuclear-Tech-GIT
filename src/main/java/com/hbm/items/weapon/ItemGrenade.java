package com.hbm.items.weapon;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.hbm.items.ItemCustomLore;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import com.hbm.util.Tuple.Pair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends ItemCustomLore
{
	private static final ArrayList<Pair<Item, Class<? extends IProjectile>>> allGrenades = new ArrayList<>();
	final int fuse;
	private final Class<? extends IProjectile> grenadeEntity;
//	public Optional<Class<? extends EntityGrenadeBouncyBase>> grenadeEntityBouncy = Optional.empty();
	/** 
	 * Constructor for impact grenades
	 * @param nade - Grenade class to be used, must extend EntityGrenadeBase
	 */
	public ItemGrenade(Class<? extends IProjectile> nade)
	{
		this(-1, nade);
		assert nade != null : "Class cannot be null";
		allGrenades.add(new Pair<Item, Class<? extends IProjectile>>(this, nade));
	}
	/** 
	 * Constructor for "bouncy" grenades
	 * @param fuse - Time in seconds until detonation
	 * @param nade - Grenade class to be used, must extend EntityGrenadeBouncyBase
	 */
//	public ItemGrenade(int fuse, Class<? extends EntityGrenadeBouncyBase> nade)
//	{
//		this(fuse);
//		assert nade != null : "Class cannot be null";
//		allGrenades.add(new Pair<Item, Class<? extends IProjectile>>(this, nade));
//		grenadeEntity = nade;
//	}
	public ItemGrenade(int fuse, Class<? extends IProjectile> nade)
	{
		maxStackSize = 16;
		grenadeEntity = nade;
		this.fuse = fuse;
		setCreativeTab(MainRegistry.weaponTab);
	}

	public static List<Pair<Item, Class<? extends IProjectile>>> getAllGrenades()
	{
		return ImmutableList.copyOf(allGrenades);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode)
			--stack.stackSize;

		worldIn.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			try
			{
				worldIn.spawnEntityInWorld((Entity) grenadeEntity.getConstructor(World.class, EntityLivingBase.class).newInstance(worldIn, player));
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				e.printStackTrace();
			}
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
