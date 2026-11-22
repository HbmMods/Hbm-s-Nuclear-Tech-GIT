package com.hbm.items.armor;

import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class JetpackPart extends ItemEnumMulti {

	public JetpackPart() {
		super(EnumJetpackPart.class, true, true);
	}

	public static enum EnumJetpackPart {
		TANK_COALGAS(Fluids.COALGAS, 1_000),
		TANK_KEROSENE(Fluids.KEROSENE, 2_000),
		TANK_JETFUEL(Fluids.KEROSENE_REFORM, 4000),
		EXTENSION_TANKS(6_000),
		THRUSTERS(0.1),
		THRUSTER_LARGE(0.3),
		STABILIZER,
		WINGS;
		
		public FluidType tankType;
		public int tankSize;
		public double velocity;
		
		private EnumJetpackPart() { }
		private EnumJetpackPart(int tankSize) { this(null, tankSize); }
		private EnumJetpackPart(double velocity) { this.velocity = velocity; }

		private EnumJetpackPart(FluidType tankType, int tankSize) {
			this.tankType = tankType;
			this.tankSize = tankSize;
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote) return stack;
		ItemStack jetpack = getWornJetpack(player);
		if(jetpack == null) return stack;
		
		EnumJetpackPart part = EnumUtil.grabEnumSafely(EnumJetpackPart.class, stack.getItemDamage());
		
		// can't install anything that's already installed
		if(JetpackModular.isPartInstalled(jetpack, part)) return stack;
		
		// tank
		if(part.tankType != null) {
			EnumJetpackPart existingTank = JetpackModular.getInstalledTank(jetpack);
			ejectAndReplace(stack, jetpack, player, existingTank, part);
			return stack;
		}
		
		// flight model
		if(part == EnumJetpackPart.STABILIZER || part == EnumJetpackPart.WINGS) {
			EnumJetpackPart existingModel = JetpackModular.getInstalledFlightModel(jetpack);
			ejectAndReplace(stack, jetpack, player, existingModel, part);
			return stack;
		}
		
		// thruster
		if(part.velocity > 0) {
			EnumJetpackPart existingThruster = JetpackModular.getInstalledThruster(jetpack);
			ejectAndReplace(stack, jetpack, player, existingThruster, part);
			return stack;
		}
		
		// extension tanks can't be installed without having tanks first
		if(part == EnumJetpackPart.EXTENSION_TANKS && JetpackModular.getInstalledTank(jetpack) == null) {
			return stack;
		}
		
		JetpackModular.togglePart(jetpack, part, true);
		stack.stackSize--;
		return stack;
	}
	
	/** If there supplied existing part is installed, eject it, finally install the new part */
	public static void ejectAndReplace(ItemStack stack, ItemStack jetpack, EntityPlayer player, EnumJetpackPart existing, EnumJetpackPart newPart) {
		if(existing != null) {
			ejectPart(player, existing);
			JetpackModular.togglePart(jetpack, existing, false);
		}
		JetpackModular.togglePart(jetpack, newPart, true);
		stack.stackSize--;
	}
	
	/** Tries to give the player that part, if that fails the part is dropped */
	public static void ejectPart(EntityPlayer player, EnumJetpackPart part) {
		ItemStack stack = new ItemStack(ModItems.jetpack_part, 1, part.ordinal());
		if(!player.inventory.addItemStackToInventory(stack)) {
			player.entityDropItem(stack, 0F);
		}
	}
	
	public static ItemStack getWornJetpack(EntityPlayer player) {
		ItemStack stack = player.inventory.armorInventory[2];
		
		if(stack != null) {
			
			if(stack.getItem() == ModItems.jetpack_modular) {
				return stack;
			}
			
			ItemStack mod = ArmorModHandler.pryMods(stack)[ArmorModHandler.plate_only];
			
			if(mod != null && mod.getItem() == ModItems.jetpack_modular) {
				return mod;
			}
		}
		
		return null;
	}
}
