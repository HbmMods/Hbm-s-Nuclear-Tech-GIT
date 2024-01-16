package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ModItems;

import api.hbm.entity.IRadarDetectableNT;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileStealth extends EntityMissileBaseNT {

	public EntityMissileStealth(World world) { super(world); }
	public EntityMissileStealth(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.bolt, 4, Mats.MAT_STEEL.id));
		return list;
	}

	@Override public String getUnlocalizedName() { return "radar.target.tier1"; }
	@Override public int getBlipLevel() { return IRadarDetectableNT.TIER1; }
	@Override public boolean canBeSeenBy(Object radar) { return false; }
	
	@Override public void onImpact() { this.explodeStandard(20F, 24, false, true); }
	@Override public ItemStack getDebrisRareDrop() { return DictFrame.fromOne(ModItems.powder_ash, EnumAshType.MISC); }
}
