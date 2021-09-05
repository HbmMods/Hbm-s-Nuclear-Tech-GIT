package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.handler.guncfg.Gun12GaugeFactory;
import com.hbm.handler.guncfg.Gun50BMGFactory;
import com.hbm.handler.guncfg.Gun556mmFactory;
import com.hbm.handler.guncfg.Gun9mmFactory;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TestGun extends ItemGunBase
{
	private GunConfiguration[] configs;
	public TestGun(GunConfiguration... config)
	{
		super(config[0]);
		setHasSubtypes(true);
		configs = config;
	}
	
	public void setConfig(ItemStack stack)
	{
		mainConfig = configs[MathHelper.clamp_int(stack.getItemDamage(), 0, configs.length)];
	}
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem)
	{
		super.onUpdate(stack, world, entity, slot, isCurrentItem);
		setConfig(stack);
	}
}
