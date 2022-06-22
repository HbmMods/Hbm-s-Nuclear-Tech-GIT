package com.hbm.items.weapon;

import java.util.List;

import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;

public class ItemAmmoArty extends Item {

	public static ArtilleryShell[] types = new ArtilleryShell[5];
	public int NORMAL = 0;
	public int CLASSIC = 1;
	public int EXPLOSIVE = 2;
	public int MINI_NUKE = 3;
	public int NUKE = 4;
	
	public ItemAmmoArty() {
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.weaponTab);
		init();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < types.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	private IIcon[] icons = new IIcon[types.length];

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		this.icons = new IIcon[types.length];

		for(int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + types[i].name);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + types[Math.abs(stack.getItemDamage()) % types.length].name;
	}
	
	public static abstract class ArtilleryShell {
		
		String name;
		
		public ArtilleryShell(String name) {
			this.name = name;
		}
		
		public abstract void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop);
	}
	
	private void init() {
		this.types[NORMAL] = new ArtilleryShell("ammo_arty") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.setDead();
			}
		};
		this.types[CLASSIC] = new ArtilleryShell("ammo_arty") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.setDead();
			}
		};
		this.types[EXPLOSIVE] = new ArtilleryShell("ammo_arty") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.setDead();
			}
		};
		this.types[MINI_NUKE] = new ArtilleryShell("ammo_arty") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.setDead();
			}
		};
		this.types[NUKE] = new ArtilleryShell("ammo_arty") {
			@Override public void onImpact(EntityArtilleryShell shell, MovingObjectPosition mop) {
				shell.setDead();
			}
		};
	}
}
