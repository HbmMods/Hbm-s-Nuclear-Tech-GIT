package com.hbm.util;

import com.hbm.interfaces.NotableComments;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * This absolute fucking mess of a class is the direct consequence of mojank's terrible entity tracker that allows for 0 flexibility with how entities are synced.
 *
 * @author hbm
 */
@NotableComments
public class TrackerUtil {

	/** Grabs the tracker entry from the given entity */
	public static EntityTrackerEntry getTrackerEntry(WorldServer world, int entityId) {
		EntityTracker entitytracker = world.getEntityTracker();
		IntHashMap map = ReflectionHelper.getPrivateValue(EntityTracker.class, entitytracker, "trackedEntityIDs", "field_72794_c");
		EntityTrackerEntry entry = (EntityTrackerEntry) map.lookup(entityId);
		return entry;
	}

	/** Force-teleports the given entity using the tracker, resetting the tick count to 0 to prevent movement during this tick */
	public static void sendTeleport(World world, Entity e) {

		if(world instanceof WorldServer) {
			WorldServer server = (WorldServer) world;
			EntityTrackerEntry entry = getTrackerEntry(server, e.getEntityId());
			int xScaled = e.myEntitySize.multiplyBy32AndRound(e.posX);
			int yScaled = MathHelper.floor_double(e.posY * 32.0D);
			int zScaled = e.myEntitySize.multiplyBy32AndRound(e.posZ);
			int yaw = MathHelper.floor_float(e.rotationYaw * 256.0F / 360.0F);
			int pitch = MathHelper.floor_float(e.rotationPitch * 256.0F / 360.0F);
			entry.func_151259_a(new S18PacketEntityTeleport(e.getEntityId(), xScaled, yScaled, zScaled, (byte)yaw, (byte)pitch));
			//this prevents the tracker from sending movement updates in the same tick
			entry.ticks = 0;
		}
	}

	public static void setTrackingRange(World world, Entity e, int range) {

		if(world instanceof WorldServer) {
			WorldServer server = (WorldServer) world;
			EntityTrackerEntry entry = getTrackerEntry(server, e.getEntityId());
			if(entry != null) entry.blocksDistanceThreshold = range;
		}
	}
}
