package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.handler.guncfg.Gun12GaugeFactory;
import com.hbm.handler.guncfg.Gun50BMGFactory;
import com.hbm.handler.guncfg.Gun556mmFactory;
import com.hbm.handler.guncfg.Gun9mmFactory;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TestGun extends ItemGunBase
{

	public TestGun(GunConfiguration config)
	{
		super(config);
		setHasSubtypes(true);
	}
	
	public void setConfig(ItemStack stack)
	{
		switch(stack.getItemDamage())
		{
		case 1:
			mainConfig = Gun9mmFactory.getLLRConfig();
			break;
		case 2:
			mainConfig = Gun50BMGFactory.getLunaticMarksman();
			break;
		case 3:
			mainConfig = Gun12GaugeFactory.getBenelliConfig();
			break;
		default:
			mainConfig = Gun556mmFactory.getMLRConfig();
			break;
		}
	}
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem)
	{
		super.onUpdate(stack, world, entity, slot, isCurrentItem);
		setConfig(stack);
	}
}
