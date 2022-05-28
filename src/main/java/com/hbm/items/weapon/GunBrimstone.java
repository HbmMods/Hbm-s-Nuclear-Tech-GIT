package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.sound.AudioWrapper;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunBrimstone extends ItemGunBase {
	
	private AudioWrapper firingLoop;
	
	public GunBrimstone(GunConfiguration config) {
		super(config);
	}
	

	Random rand = new Random();
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		//Overrides the projectile, spawning bullet and beam
		EntityBulletBase bullet = new EntityBulletBase(world, BulletConfigSyncingUtil.BRIMSTONE_AMMO, player);
		EntityLaser laser = new EntityLaser(world, player);
		world.spawnEntityInWorld(laser);
		world.spawnEntityInWorld(laser);
		world.spawnEntityInWorld(bullet);
		world.playSoundAtEntity(player, "hbm:weapon.brimLoop", 0.75F, 0.85F);
		}
	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Ammo: Depleted Plutonium-240 Ammo (Belt)");
		list.add("Manufacturer: Winchester Arms");
		list.add("Hold Right Mouse Button to use it.");
		list.add("");
		list.add("[LEGENDARY WEAPON]");
	}
	
	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 5, 0));
		return multimap;
		}
}

