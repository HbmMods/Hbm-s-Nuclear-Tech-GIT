package com.hbm.saveddata.satellites;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsSatellite;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.HashMap;

public class SatelliteMiner extends SatelliteBase {
	
	// maps satellite types to item pool names
	private static final HashMap<Class<? extends SatelliteMiner>, String> CARGO = new HashMap<>();
	
	public double progress;
	public static final double SPEED = 1D / (15 * 60 * 20); // 15 minutes

	public SatelliteMiner() { }

	@Override public String getType() { return "ASTEROID_MINER"; }
	
	@Override
	public IChatComponent[] getInfo(World world) {
		int seconds = (int) Math.ceil(Math.max(0D, 1D - this.progress) / SPEED / 20D);

		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.MINER_ASTRO.ordinal())) + ".name"),
				this.requestableSlots.length > 0 ? new ChatComponentTranslation("satellite.ready") : new ChatComponentTranslation("satellite.cooldown", (seconds / 60) + "m" + (seconds % 60) + "s")
		};
	}

	@Override
	public void onUpdateTick(World world) {
		
		if(this.requestableSlots.length <= 0) {
			this.progress += SPEED;
			
			if(this.progress >= 1D) {
				this.progress = 0D;
				
				WeightedRandomChestContent[] pool = ItemPool.getPool(CARGO.get(this.getClass()));
				
				int itemAmount = 10 + world.rand.nextInt(6); // 10-15
				this.requestableSlots = new ItemStack[itemAmount];
				
				for(int i = 0; i < itemAmount; i++) {
					this.requestableSlots[i] = ItemPool.getStack(pool, world.rand);
				}
				
				this.markDirty();
			}
			
			if(world.getTotalWorldTime() % 1200 == 0) this.markDirty(); // force a save every minute
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("progress", progress);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.progress = nbt.getDouble("progress");
	}

	/**
	 * Replaces cargo of the satellite.
	 * @param cargo - Array of {@link WeightedRandomObject} representing the loot that will be delivered.
	 */
	public static void registerCargo(Class<? extends SatelliteMiner> minerSatelliteClass, String cargo) {
		CARGO.put(minerSatelliteClass, cargo);
	}

	/**
	 * Gets items the satellite can deliver.
	 * @return - Array of {@link WeightedRandomObject} of satellite loot.
	 */
	public String getCargo() {
		return CARGO.get(getClass());
	}

	/**
	 * Gets the cargo key for the satellite item. If the item is not a miner satellite null is returned.
	 * @param satelliteItem - Satellite item
	 * @return - Returns {@link com.hbm.itempool.ItemPool} key or null if the item is not a mining satellite.
	 */
	public static String getCargoForItem(ComparableStack satelliteItem) {
		Class<? extends SatelliteBase> satelliteClass = XSatelliteRegistry.itemToClass.getOrDefault(satelliteItem, null);
		return satelliteClass != null ? CARGO.getOrDefault(satelliteClass, null) : null;
	}

	static {
		registerCargo(SatelliteMiner.class, ItemPoolsSatellite.POOL_SAT_MINER);
	}
}
