package com.hbm.items.weapon;

import java.util.List;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class ItemAmmoHIMARS extends Item {
	
	public static HIMARSRocket[] itemTypes = new HIMARSRocket[ /* >>> */ 2 /* <<< */ ];
	
	public final int SMALL = 0;
	public final int LARGE = 1;
	
	public ItemAmmoHIMARS() {
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.weaponTab);
		this.setTextureName(RefStrings.MODID + ":ammo_rocket");
		init();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, SMALL));
		list.add(new ItemStack(item, 1, LARGE));
	}
	
	public abstract class HIMARSRocket {
		
		String name;
		
		public HIMARSRocket() { }
		
		public HIMARSRocket(String name) {
			this.name = name;
		}
		
		public abstract void onImpact(Entity rocket, MovingObjectPosition mop);
		public void onUpdate(Entity rocket) { }
	}
	
	private void init() {
		/* STANDARD SHELLS */
		this.itemTypes[SMALL] = new HIMARSRocket("ammo_himars") { public void onImpact(Entity rocket, MovingObjectPosition mop) {  }};
		this.itemTypes[LARGE] = new HIMARSRocket("ammo_himars_large") { public void onImpact(Entity rocket, MovingObjectPosition mop) {  }};
	}
}
