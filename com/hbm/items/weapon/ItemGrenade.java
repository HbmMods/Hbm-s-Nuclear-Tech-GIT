package com.hbm.items.weapon;

import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends Item {

	public ItemGrenade() {
		this.maxStackSize = 16;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if (!p_77659_3_.capabilities.isCreativeMode) {
			--p_77659_1_.stackSize;
		}

		p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!p_77659_2_.isRemote) {
			if (this == ModItems.grenade_generic) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeGeneric(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_strong) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeStrong(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_frag) {
				EntityGrenadeFrag frag = new EntityGrenadeFrag(p_77659_2_, p_77659_3_);
				frag.shooter = p_77659_3_;
				p_77659_2_.spawnEntityInWorld(frag);
			}
			if (this == ModItems.grenade_fire) {
				EntityGrenadeFire fire = new EntityGrenadeFire(p_77659_2_, p_77659_3_);
				fire.shooter = p_77659_3_;
				p_77659_2_.spawnEntityInWorld(fire);
			}
			if (this == ModItems.grenade_cluster) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeCluster(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_flare) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeFlare(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_electric) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeElectric(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_poison) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadePoison(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_gas) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeGas(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_schrabidium) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeSchrabidium(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_nuke) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeNuke(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_nuclear) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeNuclear(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_pulse) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadePulse(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_plasma) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadePlasma(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_tau) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeTau(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_lemon) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeLemon(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_mk2) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeMk2(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_aschrab) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeASchrab(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_zomg) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeZOMG(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_shrapnel) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeShrapnel(p_77659_2_, p_77659_3_));
			}
		}

		return p_77659_1_;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		if (this == ModItems.grenade_schrabidium || this == ModItems.grenade_aschrab) {
			return EnumRarity.rare;
		}

		if (this == ModItems.grenade_plasma || this == ModItems.grenade_zomg) {
			return EnumRarity.epic;
		}

		if (this == ModItems.grenade_nuke || this == ModItems.grenade_nuclear || this == ModItems.grenade_tau || this == ModItems.grenade_lemon || this == ModItems.grenade_mk2 || this == ModItems.grenade_pulse) {
			return EnumRarity.uncommon;
		}

		return EnumRarity.common;
	}

}
