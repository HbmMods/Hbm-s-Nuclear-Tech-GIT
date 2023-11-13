package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectableNT;
import api.hbm.entity.RadarEntry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

/**
 * Now with SmЯt™ lag-free entity detection! (patent pending)
 * @author hbm
 */
public class TileEntityMachineRadarNT extends TileEntityMachineBase implements IConfigurableMachine {
	
	public boolean scanMissiles = true;
	public boolean scanPlayers = true;
	public boolean smartMode = true;
	public boolean redMode = true;
	
	public boolean jammed = false;

	public float prevRotation;
	public float rotation;

	public long power = 0;

	public static int maxPower = 100_000;
	public static int consumption = 500;
	public static int radarRange = 1_000;
	public static int radarBuffer = 30;
	public static int radarAltitude = 55;
	
	public List<RadarEntry> entries = new ArrayList();

	@Override
	public String getConfigName() {
		return "radar";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		maxPower = IConfigurableMachine.grab(obj, "L:powerCap", maxPower);
		consumption = IConfigurableMachine.grab(obj, "L:consumption", consumption);
		radarRange = IConfigurableMachine.grab(obj, "I:radarRange", radarRange);
		radarBuffer = IConfigurableMachine.grab(obj, "I:radarBuffer", radarBuffer);
		radarAltitude = IConfigurableMachine.grab(obj, "I:radarAltitude", radarAltitude);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:powerCap").value(maxPower);
		writer.name("L:consumption").value(consumption);
		writer.name("I:radarRange").value(radarRange);
		writer.name("I:radarBuffer").value(radarBuffer);
		writer.name("I:radarAltitude").value(radarAltitude);
	}

	public TileEntityMachineRadarNT() {
		super(1);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			this.jammed = false;
			allocateTargets();
			
			this.networkPackNT(25);
		}
	}
	
	protected void allocateTargets() {
		this.entries.clear();
		
		if(this.yCoord < radarAltitude) return;
		if(this.power <= consumption) return;
		this.power -= consumption;
		
		int scan = this.scanRange();
		
		for(Entity e : matchingEntities) {
			
			if(e.dimension == worldObj.provider.dimensionId && Math.abs(e.posX - (xCoord + 0.5)) <= scan && Math.abs(e.posZ - (zCoord + 0.5)) <= scan && e.posY - yCoord < radarBuffer) {
				
				if(e instanceof EntityLivingBase && HbmLivingProps.getDigamma((EntityLivingBase) e) > 0.001) {
					this.jammed = true;
					entries.clear();
					return;
				}
				
				for(Function<Pair<Entity, Object>, RadarEntry> converter : converters) {
					
					RadarEntry entry = converter.apply(new Pair(e, this));
					if(entry != null) {
						this.entries.add(entry);
						break;
					}
				}
			}
		}
	}
	
	protected int scanRange() {
		return radarRange;
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		buf.writeLong(this.power);
		buf.writeBoolean(this.scanMissiles);
		buf.writeBoolean(this.scanPlayers);
		buf.writeBoolean(this.smartMode);
		buf.writeBoolean(this.redMode);
		buf.writeBoolean(this.jammed);
		buf.writeInt(entries.size());
		for(RadarEntry entry : entries) entry.toBytes(buf);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		this.power = buf.readLong();
		this.scanMissiles = buf.readBoolean();
		this.scanPlayers = buf.readBoolean();
		this.smartMode = buf.readBoolean();
		this.redMode = buf.readBoolean();
		this.jammed = buf.readBoolean();
		int count = buf.readInt();
		for(int i = 0; i < count; i++) {
			RadarEntry entry = new RadarEntry();
			entry.fromBytes(buf);
			this.entries.add(entry);
		}
	}
	
	/** List of lambdas that are supplied a Pair with the entity and radar in question to generate a RadarEntry
	The converters coming first have the highest priority */
	public static List<Function<Pair<Entity, Object>, RadarEntry>> converters = new ArrayList();
	public static List<Class> classes = new ArrayList();
	public static List<Entity> matchingEntities = new ArrayList();
	
	/**
	 * Iterates over every entity in the world and add them to the matchingEntities list if the class is in the detectable list
	 * From this compiled list, radars can easily grab the required entities since we can assume that the total amount of detectable entities is comparatively low
	 */
	public static void updateSystem() {
		matchingEntities.clear();
		
		for(WorldServer world : Minecraft.getMinecraft().getIntegratedServer().worldServers) {
			for(Object entity : world.loadedEntityList) {
				for(Class clazz : classes) {
					if(clazz.isAssignableFrom(entity.getClass())) {
						matchingEntities.add((Entity) entity);
						break;
					}
				}
			}
		}
	}

	/** Registers a class that if an entity inherits that class, it is picked up by the system */
	public static void registerEntityClasses() {
		classes.add(IRadarDetectableNT.class);
		classes.add(IRadarDetectable.class);
		classes.add(EntityPlayer.class);
	}
	
	/** Registers converters. Converters are used to go over the list of detected entities and turn them into a RadarEntry using the entity instance and the radar's instance. */
	public static void registerConverters() {
		//IRadarDetectableNT
		converters.add(x -> {
			Entity e = x.getKey();
			if(e instanceof IRadarDetectableNT) {
				IRadarDetectableNT detectable = (IRadarDetectableNT) e;
				if(detectable.canBeSeenBy(x.getValue())) return new RadarEntry(detectable, e);
			}
			return null;
		});
		//IRadarDetectable, Legacy
		converters.add(x -> {
			if(x.getKey() instanceof IRadarDetectable) return new RadarEntry((IRadarDetectable) x.getKey(), x.getKey());
			return null;
		});
		//Players
		converters.add(x -> {
			if(x.getKey() instanceof EntityPlayer) return new RadarEntry((EntityPlayer) x.getKey());
			return null;
		});
	}
}
