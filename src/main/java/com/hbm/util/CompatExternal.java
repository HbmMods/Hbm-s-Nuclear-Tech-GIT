package com.hbm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.hbm.blocks.BlockDummyable;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.weapon.ItemCustomMissilePart.WarheadType;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.turret.TileEntityTurretSentry;

import api.hbm.energymk2.IEnergyHandlerMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidUser;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

/**
 * EXTERNAL COMPATIBILITY CLASS - DO NOT CHANGE METHOD NAMES/PARAMS ONCE CREATED
 * Is there a smarter way to do this? Most likely. Is there an easier one? Probably not.
 * @author hbm
 */
public class CompatExternal {

	/**
	 * Gets the tile entity at that pos. If the tile entity is an mk1 or mk2 dummy, it will return the core instead.
	 * This method will be updated in the event that other multiblock systems or dummies are added to retrain the intended functionality.
	 * @return the core tile entity if the given position holds a dummy, the tile entity at that position if it doesn't or null if there is no tile entity
	 */
	public static TileEntity getCoreFromPos(World world, int x, int y, int z) {
		
		Block b = world.getBlock(x, y, z);
		
		//if the block at that pos is a Dummyable, use the mk2's system to find the core
		if(b instanceof BlockDummyable) {
			BlockDummyable dummy = (BlockDummyable) b;
			int[] pos = dummy.findCore(world, x, y, z);
			
			if(pos != null) {
				return world.getTileEntity(pos[0], pos[1], pos[2]);
			}
		}
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		//if the tile at that pos is an old dummy tile, use mk1
		if(tile instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy) tile;
			return world.getTileEntity(dummy.targetX, dummy.targetY, dummy.targetZ);
		}
		
		//otherwise, return the tile at that position whihc could be null
		return tile;
	}
	
	/**
	 * Returns the numeric value of the buffered energy held by that tile entity. Current implementation relies on IEnergyUser.
	 * @param tile
	 * @return power
	 */
	public static long getBufferedPowerFromTile(TileEntity tile) {
		
		if(tile instanceof IEnergyHandlerMK2) {
			return ((IEnergyHandlerMK2) tile).getPower();
		}
		
		return 0L;
	}
	
	/**
	 * Returns the numeric value of the energy capacity of this tile entity. Current implementation relies on IEnergyUser.
	 * @param tile
	 * @return max power
	 */
	public static long getMaxPowerFromTile(TileEntity tile) {
		
		if(tile instanceof IEnergyHandlerMK2) {
			return ((IEnergyHandlerMK2) tile).getMaxPower();
		}
		
		return 0L;
	}
	
	/**
	 * Returns the ordinal of the energy priority from the supplied tile entity. 0 = low, 1 = normal, 2 = high. Returns -1 if not applicable.
	 * @param tile
	 * @return priority
	 */
	public static int getEnergyPriorityFromTile(TileEntity tile) {
		
		if(tile instanceof IEnergyReceiverMK2) {
			return ((IEnergyReceiverMK2) tile).getPriority().ordinal();
		}
		
		return -1;
	}
	
	/**
	 * Returns a list of tank definitions from the supplied tile entity. Uses IFluidUser, if the tile is incompatible it returns an empty list.
	 * @param tile
	 * @return an ArrayList of Object arrays with each array representing a fluid tank.<br>
	 * [0]: STRING - unlocalized name of the fluid, simply use I18n to get the translated name<br>
	 * [1]: INT - the unique ID of this fluid<br>
	 * [2]: INT - the hexadecimal color of this fluid<br>
	 * [3]: INT - the amount of fluid in this tank in millibuckets<br>
	 * [4]: INT - the capacity of this tank in millibuckets
	 */
	public static ArrayList<Object[]> getFluidInfoFromTile(TileEntity tile) {
		ArrayList<Object[]> list = new ArrayList();
		
		if(!(tile instanceof IFluidUser)) {
			return list;
		}
		
		IFluidUser container = (IFluidUser) tile;
		
		for(FluidTank tank : container.getAllTanks()) {
			FluidType type = tank.getTankType();
			list.add(new Object[] {
					type.getConditionalName(),
					type.getID(),
					type.getColor(),
					tank.getFill(),
					tank.getMaxFill()
			});
		}
		
		return list;
	}

	public static Set<Class> turretTargetPlayer = new HashSet();
	public static Set<Class> turretTargetFriendly = new HashSet();
	public static Set<Class> turretTargetHostile = new HashSet();
	public static Set<Class> turretTargetMachine = new HashSet();
	
	/**
	 * Registers a class for turret targeting
	 * @param clazz is the class that should be targeted.
	 * @param type determines what setting the turret needs to have enabled to target this class. 0 is player, 1 is friendly, 2 is hostile and 3 is machine.
	 */
	public static void registerTurretTargetSimple(Class clazz, int type) {
		
		switch(type) {
		case 0: turretTargetPlayer.add(clazz); break;
		case 1: turretTargetFriendly.add(clazz); break;
		case 2: turretTargetHostile.add(clazz); break;
		case 3: turretTargetMachine.add(clazz); break;
		}
	}
	
	public static Set<Class> turretTargetBlacklist = new HashSet();
	
	/**
	 * Registers a class to be fully ignored by turrets
	 * @param clazz is the class that should be ignored.
	 */
	public static void registerTurretTargetBlacklist(Class clazz) {
		turretTargetBlacklist.add(clazz);
	}
	
	public static HashMap<Class, BiFunction<Entity, Object, Integer>> turretTargetCondition = new HashMap();
	
	/**
	 * Registers a BiFunction lambda for more complex targeting compatibility
	 * @param clazz is the class that this rule should apply to
	 * @param bi is the lambda. The function should return 0 to continue with other targeting checks (i.e. do nothing), -1 to ignore this entity or 1 to target it.
	 * The params for this lambda are the entity and the turret in question. The type for the turret is omitted on purpose as to not require any reference of the tile entity
	 * class on the side of whoever is adding compat, allowing the compat class to be used entirely with reflection.
	 */
	public static void registerTurretTargetingCondition(Class clazz, BiFunction<Entity, Object, Integer> bi) {
		turretTargetCondition.put(clazz, bi);
	}

	public static void setWarheadLabel(WarheadType type, String label) { type.labelCustom = label; }
	public static void setWarheadImpact(WarheadType type, Consumer<EntityMissileCustom> impact) { type.impactCustom = impact; }
	public static void setWarheadUpdate(WarheadType type, Consumer<EntityMissileCustom> update) { type.updateCustom = update; }
	
	public static void compatExamples() {
		// Makes all cows be targeted by turrets if player mode is active in addition to the existing rules. Applies to all entities that inherit EntityCow.
		CompatExternal.registerTurretTargetSimple(EntityCow.class, 0);
		// Makes all chickens ignored by turrets, also applies to entities that inherit EntityChicken like ducks.
		CompatExternal.registerTurretTargetBlacklist(EntityChicken.class);
		// An example for more complex turret behavior. Turrets will always target players named "Target Practice", and sentry turrets will never target players.
		CompatExternal.registerTurretTargetingCondition(EntityPlayer.class, (entity, turret) -> {
			if(entity.getCommandSenderName().equals("Target Practice")) return 1;
			if(turret instanceof TileEntityTurretSentry) return -1;
			return 0;
		});
		//configures CUSTOM0 to have a custom label and impact effect
		CompatExternal.setWarheadLabel(WarheadType.CUSTOM0, EnumChatFormatting.YELLOW + "Micro Nuke");
		CompatExternal.setWarheadImpact(WarheadType.CUSTOM0, (missile) -> {
			ExplosionNukeSmall.explode(missile.worldObj, missile.posX, missile.posY + 0.5, missile.posZ, ExplosionNukeSmall.PARAMS_MEDIUM);
		});
	}
}
