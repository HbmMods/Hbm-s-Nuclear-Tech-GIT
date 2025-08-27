package com.hbm.handler;

import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeDynamite;
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
import com.hbm.entity.grenade.EntityGrenadeImpactGeneric;
import com.hbm.entity.grenade.EntityGrenadeLemon;
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
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.grenade.EntityWastePearl;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemFertilizer;
import com.hbm.items.weapon.ItemGenericGrenade;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DispenserBehaviorHandler {

	public static void init() {

		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_generic, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeGeneric(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_strong, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeStrong(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_frag, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeFrag(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_fire, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeFire(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_cluster, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeCluster(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_flare, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeFlare(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_electric, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeElectric(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_poison, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadePoison(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_gas, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeGas(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_schrabidium, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeSchrabidium(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_nuke, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeNuke(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_nuclear, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeNuclear(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_pulse, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadePulse(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_plasma, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadePlasma(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_tau, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeTau(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_lemon, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeLemon(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_mk2, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeMk2(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_aschrab, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeASchrab(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_zomg, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeZOMG(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_shrapnel, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeShrapnel(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_black_hole, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeBlackHole(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_gascan, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeGascan(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_cloud, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeCloud(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_pink_cloud, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadePC(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_smart, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeSmart(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_mirv, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeMIRV(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_breach, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeBreach(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_burst, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeBurst(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_generic, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFGeneric(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_he, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFHE(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_bouncy, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFBouncy(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_sticky, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFSticky(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_impact, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFImpact(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_incendiary, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFIncendiary(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_toxic, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFToxic(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_concussion, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFConcussion(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_brimstone, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFBrimstone(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_mystery, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFMystery(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_spark, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFSpark(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_hopwire, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFHopwire(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_null, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeIFNull(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.nuclear_waste_pearl, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityWastePearl(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.stick_dynamite, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeDynamite(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_kyiv, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeImpactGeneric(world, position.getX(), position.getY(), position.getZ()).setType((ItemGenericGrenade) ModItems.grenade_kyiv);
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.stick_dynamite_fishing, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeImpactGeneric(world, position.getX(), position.getY(), position.getZ()).setType((ItemGenericGrenade) ModItems.stick_dynamite_fishing);
			}
		});
		
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.powder_fertilizer, new BehaviorDefaultDispenseItem() {

			private boolean dispenseSound = true;
			@Override protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {

				EnumFacing facing = BlockDispenser.func_149937_b(source.getBlockMetadata());
				World world = source.getWorld();
				int x = source.getXInt() + facing.getFrontOffsetX();
				int y = source.getYInt() + facing.getFrontOffsetY();
				int z = source.getZInt() + facing.getFrontOffsetZ();
				this.dispenseSound = ItemFertilizer.useFertillizer(stack, world, x, y, z);
				return stack;
			}
			@Override protected void playDispenseSound(IBlockSource source) {
				if(this.dispenseSound) {
					source.getWorld().playAuxSFX(1000, source.getXInt(), source.getYInt(), source.getZInt(), 0);
				} else {
					source.getWorld().playAuxSFX(1001, source.getXInt(), source.getYInt(), source.getZInt(), 0);
				}
			}
		});
	}
}
