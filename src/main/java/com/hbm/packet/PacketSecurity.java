package com.hbm.packet;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

/** Shared trust-boundary checks for client-to-server messages. */
public final class PacketSecurity {

	public static final double MAX_TILE_DISTANCE_SQ = 128.0D;
	private static final long RATE_WINDOW_NANOS = 1_000_000_000L;
	private static final Map<EntityPlayer, Map<String, RateWindow>> rateWindows = new WeakHashMap<>();
	private static final Map<Class<?>, Field[]> containerTileFields = new HashMap<>();

	private PacketSecurity() { }

	public static boolean canAccessTile(EntityPlayer player, TileEntity tile) {
		if(player == null || player.worldObj == null || tile == null || tile.getWorldObj() != player.worldObj) return false;
		if(tile.yCoord < 0 || tile.yCoord >= player.worldObj.getHeight()) return false;
		if(player.worldObj.getTileEntity(tile.xCoord, tile.yCoord, tile.zCoord) != tile) return false;
		return player.getDistanceSq(tile.xCoord + 0.5D, tile.yCoord + 0.5D, tile.zCoord + 0.5D) <= MAX_TILE_DISTANCE_SQ;
	}

	public static boolean allow(EntityPlayer player, String operation, int maxPerSecond) {
		if(player == null || maxPerSecond <= 0) return false;
		long now = System.nanoTime();
		Map<String, RateWindow> playerWindows = rateWindows.get(player);
		if(playerWindows == null) {
			playerWindows = new HashMap<>();
			rateWindows.put(player, playerWindows);
		}

		RateWindow window = playerWindows.get(operation);
		if(window == null || now - window.startedAt >= RATE_WINDOW_NANOS) {
			playerWindows.put(operation, new RateWindow(now));
			return true;
		}

		if(window.count >= maxPerSecond) return false;
		window.count++;
		return true;
	}

	public static boolean containerTargetsTile(Container container, TileEntity tile) {
		if(container == null || tile == null) return false;
		Field[] fields = containerTileFields.get(container.getClass());
		if(fields == null) {
			List<Field> discovered = new ArrayList<>();
			for(Class<?> type = container.getClass(); type != null && Container.class.isAssignableFrom(type); type = type.getSuperclass()) {
				for(Field field : type.getDeclaredFields()) {
					if(Modifier.isStatic(field.getModifiers())) continue;
					if(!TileEntity.class.isAssignableFrom(field.getType()) && !IInventory.class.isAssignableFrom(field.getType())) continue;
					try {
						field.setAccessible(true);
						discovered.add(field);
					} catch(SecurityException ignored) { }
				}
			}
			fields = discovered.toArray(new Field[discovered.size()]);
			containerTileFields.put(container.getClass(), fields);
		}

		for(Field field : fields) {
			try {
				if(field.get(container) == tile) return true;
			} catch(IllegalAccessException ignored) { }
		}
		return false;
	}

	private static final class RateWindow {
		private final long startedAt;
		private int count = 1;

		private RateWindow(long startedAt) {
			this.startedAt = startedAt;
		}
	}
}
