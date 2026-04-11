package com.hbm.handler;

import com.hbm.entity.grenade.*;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemFertilizer;
import com.hbm.items.weapon.ItemGenericGrenade;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;

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

		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_universal, new BehaviorDefaultDispenseItem() {
			@Override protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {

				EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
				
				EntityGrenadeUniversal grenade = new EntityGrenadeUniversal(source.getWorld(), stack);
				EnumGrenadeShell shell = grenade.getShell();
				
				// this kinda sucks ass but it works so i'm not complaining
				grenade.setPosition(source.getX() + enumfacing.getFrontOffsetX() * 0.75, source.getY() + enumfacing.getFrontOffsetY() * 0.75, source.getZ() + enumfacing.getFrontOffsetZ() * 0.75);
				grenade.motionX = enumfacing.getFrontOffsetX() * shell.getYeetForce();
				grenade.motionY = enumfacing.getFrontOffsetY() * shell.getYeetForce();
				grenade.motionZ = enumfacing.getFrontOffsetZ() * shell.getYeetForce();
				source.getWorld().spawnEntityInWorld(grenade);
				
				stack.stackSize--;
				return stack;
			}
		});

		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.stick_dynamite, new BehaviorProjectileDispense() {
			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeImpactGeneric(world, position.getX(), position.getY(), position.getZ()).setType((ItemGenericGrenade) ModItems.stick_dynamite);
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
