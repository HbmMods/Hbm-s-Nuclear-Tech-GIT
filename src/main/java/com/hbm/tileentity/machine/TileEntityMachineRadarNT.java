package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineRadarNT;
import com.hbm.inventory.gui.GUIMachineRadarNT;
import com.hbm.inventory.gui.GUIMachineRadarNTSlots;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemCoordinateBase;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteHorizons;
import com.hbm.saveddata.satellites.SatelliteLaser;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IRadarCommandReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.world.WorldUtil;

import api.hbm.energy.IEnergyUser;
import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectableNT;
import api.hbm.entity.IRadarDetectableNT.RadarScanParams;
import api.hbm.entity.RadarEntry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Now with SmЯt™ lag-free entity detection! (patent pending)
 * @author hbm
 */
public class TileEntityMachineRadarNT extends TileEntityMachineBase implements IEnergyUser, IGUIProvider, IConfigurableMachine, IControlReceiver {

	public boolean scanMissiles = true;
	public boolean scanShells = true;
	public boolean scanPlayers = true;
	public boolean smartMode = true;
	public boolean redMode = true;
	public boolean showMap = false;
	
	public boolean jammed = false;

	public float prevRotation;
	public float rotation;

	public long power = 0;
	
	protected int pingTimer = 0;
	protected int lastPower;
	protected final static int maxTimer = 80;

	public static int maxPower = 100_000;
	public static int consumption = 500;
	public static int radarRange = 1_000;
	public static int radarBuffer = 30;
	public static int radarAltitude = 55;
	public static int chunkLoadCap = 10;
	public static boolean generateChunks = false;
	
	public byte[] map = new byte[40_000];
	public boolean clearFlag = false;
	
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
		chunkLoadCap = IConfigurableMachine.grab(obj, "I:chunkLoadCap", chunkLoadCap);
		generateChunks = IConfigurableMachine.grab(obj, "B:generateChunks", generateChunks);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:powerCap").value(maxPower);
		writer.name("L:consumption").value(consumption);
		writer.name("I:radarRange").value(radarRange);
		writer.name("I:radarBuffer").value(radarBuffer);
		writer.name("I:radarAltitude").value(radarAltitude);
		writer.name("B:generateChunks").value(generateChunks);
	}

	public TileEntityMachineRadarNT() {
		super(10);
	}

	@Override
	public String getName() {
		return "container.radar";
	}
	
	public int getRange() {
		return radarRange;
	}

	@Override
	public void updateEntity() {
		
		if(this.map == null || this.map.length != 40_000) this.map = new byte[40_000];
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 9, power, maxPower);

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			this.jammed = false;
			allocateTargets();
			
			if(this.lastPower != getRedPower()) {
				this.markChanged();
				for(DirPos pos : getConPos()) this.updateRedstoneConnection(pos);
			}
			lastPower = getRedPower();
			
			if(!this.muffled) {
				
				pingTimer++;
				
				if(power > 0 && pingTimer >= maxTimer) {
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.sonarPing", 5.0F, 1.0F);
					pingTimer = 0;
				}
			}
			
			if(this.showMap) {
				int chunkLoads = 0;
				for(int i = 0; i < 100; i++) {
					int index = (int) (worldObj.getTotalWorldTime() % 400) * 100 + i;
					int iX = (index % 200) * getRange() * 2 / 200;
					int iZ = index / 200 * getRange() * 2 / 200;
					
					int x = xCoord - getRange() + iX;
					int z = zCoord - getRange() + iZ;
					
					if(worldObj.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
						this.map[index] = (byte) MathHelper.clamp_int(worldObj.getHeightValue(x, z), 50, 128);
					} else {
						if(this.map[index] == 0 && chunkLoads < chunkLoadCap) {
							if(this.generateChunks) {
								worldObj.getChunkFromChunkCoords(x >> 4, z >> 4);
								this.map[index] = (byte) MathHelper.clamp_int(worldObj.getHeightValue(x, z), 50, 128);
								chunkLoads++;
							} else {
								WorldUtil.provideChunk((WorldServer) worldObj, x >> 4, z >> 4);
								this.map[index] = (byte) MathHelper.clamp_int(worldObj.getHeightValue(x, z), 50, 128);
								if(worldObj.getChunkProvider().chunkExists(x >> 4, z >> 4)) chunkLoads++;
							}
						}
					}
				}
			}
			
			if(slots[8] != null && slots[8].getItem() == ModItems.radar_linker) {
				BlockPos pos = ItemCoordinateBase.getPosition(slots[8]);
				if(pos != null) {
					TileEntity tile = worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
					if(tile instanceof TileEntityMachineRadarScreen) {
						TileEntityMachineRadarScreen screen = (TileEntityMachineRadarScreen) tile;
						screen.entries.clear();
						screen.entries.addAll(this.entries);
						screen.refX = xCoord;
						screen.refY = yCoord;
						screen.refZ = zCoord;
						screen.range = this.getRange();
						screen.linked = true;
					}
				}
			}
			
			this.networkPackNT(50);
			if(this.clearFlag) {
				this.map = new byte[40_000];
				this.clearFlag = false;
			}
		} else {
			prevRotation = rotation;
			if(power > 0) rotation += 5F;
			
			if(rotation >= 360) {
				rotation -= 360F;
				prevRotation -= 360F;
			}
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z),
		};
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(this.power);
		buf.writeBoolean(this.scanMissiles);
		buf.writeBoolean(this.scanShells);
		buf.writeBoolean(this.scanPlayers);
		buf.writeBoolean(this.smartMode);
		buf.writeBoolean(this.redMode);
		buf.writeBoolean(this.showMap);
		buf.writeBoolean(this.jammed);
		buf.writeInt(entries.size());
		for(RadarEntry entry : entries) entry.toBytes(buf);
		if(this.clearFlag) {
			buf.writeBoolean(true);
		} else {
			buf.writeBoolean(false);
			if(this.showMap) {
				buf.writeBoolean(true);
				short index = (short) (worldObj.getTotalWorldTime() % 400);
				buf.writeShort(index);
				for(int i = index * 100; i < (index + 1) * 100; i++) {
					buf.writeByte(this.map[i]);
				}
			} else {
				buf.writeBoolean(false);
			}
		}
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.scanMissiles = buf.readBoolean();
		this.scanShells = buf.readBoolean();
		this.scanPlayers = buf.readBoolean();
		this.smartMode = buf.readBoolean();
		this.redMode = buf.readBoolean();
		this.showMap = buf.readBoolean();
		this.jammed = buf.readBoolean();
		int count = buf.readInt();
		this.entries.clear();
		for(int i = 0; i < count; i++) {
			RadarEntry entry = new RadarEntry();
			entry.fromBytes(buf);
			this.entries.add(entry);
		}
		if(buf.readBoolean()) { // clear flag
			this.map = new byte[40_000];
		} else {
			if(buf.readBoolean()) { // map enabled
				int index = buf.readShort();
				for(int i = index * 100; i < (index + 1) * 100; i++) {
					this.map[i] = buf.readByte();
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.scanMissiles = nbt.getBoolean("scanMissiles");
		this.scanShells = nbt.getBoolean("scanShells");
		this.scanPlayers = nbt.getBoolean("scanPlayers");
		this.smartMode = nbt.getBoolean("smartMode");
		this.redMode = nbt.getBoolean("redMode");
		this.showMap = nbt.getBoolean("showMap");
		if(nbt.hasKey("map")) this.map = nbt.getByteArray("map");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setBoolean("scanMissiles", scanMissiles);
		nbt.setBoolean("scanShells", scanShells);
		nbt.setBoolean("scanPlayers", scanPlayers);
		nbt.setBoolean("smartMode", smartMode);
		nbt.setBoolean("redMode", redMode);
		nbt.setBoolean("showMap", showMap);
		nbt.setByteArray("map", map);
	}
	
	protected void allocateTargets() {
		this.entries.clear();
		
		if(this.yCoord < radarAltitude) return;
		if(this.power < consumption) return;
		this.power -= consumption;
		
		int scan = this.getRange();
		
		RadarScanParams params = new RadarScanParams(this.scanMissiles, this.scanShells, this.scanPlayers, this.smartMode);
		
		for(Entity e : matchingEntities) {
			
			if(e.dimension == worldObj.provider.dimensionId && Math.abs(e.posX - (xCoord + 0.5)) <= scan && Math.abs(e.posZ - (zCoord + 0.5)) <= scan && e.posY - yCoord > radarBuffer) {
				
				if(e instanceof EntityLivingBase && HbmLivingProps.getDigamma((EntityLivingBase) e) > 0.001) {
					this.jammed = true;
					entries.clear();
					return;
				}
				
				for(Function<Triplet<Entity, Object, RadarScanParams>, RadarEntry> converter : converters) {
					
					RadarEntry entry = converter.apply(new Triplet(e, this, params));
					if(entry != null) {
						this.entries.add(entry);
						break;
					}
				}
			}
		}
	}
	
	public int getRedPower() {
		
		if(!entries.isEmpty()) {
			
			/// PROXIMITY ///
			if(redMode) {
				
				double maxRange = this.getRange() * Math.sqrt(2D);
				int power = 0;
				
				for(int i = 0; i < entries.size(); i++) {
					RadarEntry e = entries.get(i);
					if(!e.redstone) continue;
					double dist = Math.sqrt(Math.pow(e.posX - xCoord, 2) + Math.pow(e.posZ - zCoord, 2));
					int p = 15 - (int)Math.floor(dist / maxRange * 15);
					
					if(p > power) power = p;
				}
				
				return power;
				
			/// TIER ///
			} else {
				
				int power = 0;
				
				for(int i = 0; i < entries.size(); i++) {
					RadarEntry e = entries.get(i);
					if(!e.redstone) continue;
					if(e.blipLevel + 1 > power) {
						power = e.blipLevel + 1;
					}
				}
				
				return power;
			}
		}
		
		return 0;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override public void receiveControl(NBTTagCompound data) { }
	
	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		
		if(data.hasKey("missiles")) this.scanMissiles = !this.scanMissiles;
		if(data.hasKey("shells")) this.scanShells = !this.scanShells;
		if(data.hasKey("players")) this.scanPlayers = !this.scanPlayers;
		if(data.hasKey("smart")) this.smartMode = !this.smartMode;
		if(data.hasKey("red")) this.redMode = !this.redMode;
		if(data.hasKey("map")) this.showMap = !this.showMap;
		if(data.hasKey("clear")) this.clearFlag = true;

		if(data.hasKey("gui1")) FMLNetworkHandler.openGui(player, MainRegistry.instance, 1, worldObj, xCoord, yCoord, zCoord);
		
		if(data.hasKey("link")) {
			int id = data.getInteger("link");
			ItemStack link = slots[id];
			
			if(link != null && link.getItem() == ModItems.sat_relay) {
				World world = player.getEntityWorld();
				Satellite sat = SatelliteSavedData.getData(world).getSatFromFreq(ISatChip.getFreqS(link));
				if(sat instanceof SatelliteLaser) {
					if(data.hasKey("launchPosX")) {
						int x = data.getInteger("launchPosX");
						int z = data.getInteger("launchPosZ");
						worldObj.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
						sat.onClick(world, x, z);
					}
				}
				if(sat instanceof SatelliteHorizons) {
					if(data.hasKey("launchPosX")) {
						int x = data.getInteger("launchPosX");
						int z = data.getInteger("launchPosZ");
						int y = 60; //one day I will make radars transmit Y coordinate as well and you will be butchered alhamdulila
						worldObj.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
						sat.onCoordAction(world, player, x, y, z);
					}
				}
			}
			if(link != null && link.getItem() == ModItems.radar_linker) {
				BlockPos pos = ItemCoordinateBase.getPosition(link);
				
				if(pos != null) {
					TileEntity tile = worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
					if(tile instanceof IRadarCommandReceiver) {
						IRadarCommandReceiver rec = (IRadarCommandReceiver) tile;
						
						if(data.hasKey("launchEntity")) {
							Entity entity = worldObj.getEntityByID(data.getInteger("launchEntity"));
							if(entity != null) {
								if(rec.sendCommandEntity(entity)) {
									worldObj.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
								}
							}
						} else if(data.hasKey("launchPosX")) {
							int x = data.getInteger("launchPosX");
							int z = data.getInteger("launchPosZ");
							if(rec.sendCommandPosition(x, yCoord, z)) {
								worldObj.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
							}
						}
					}
				}
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 3,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistance(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 1) return new ContainerMachineRadarNT(player.inventory, this);
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) return new GUIMachineRadarNT(this);
		if(ID == 1) return new GUIMachineRadarNTSlots(player.inventory, this);
		return null;
	}
	
	/** List of lambdas that are supplied a Pair with the entity and radar in question to generate a RadarEntry
	The converters coming first have the highest priority */
	public static List<Function<Triplet<Entity, Object, RadarScanParams>, RadarEntry>> converters = new ArrayList();
	public static List<Class> classes = new ArrayList();
	public static List<Entity> matchingEntities = new ArrayList();
	
	/**
	 * Iterates over every entity in the world and add them to the matchingEntities list if the class is in the detectable list
	 * From this compiled list, radars can easily grab the required entities since we can assume that the total amount of detectable entities is comparatively low
	 */
	public static void updateSystem() {
		matchingEntities.clear();
		
		for(WorldServer world : MinecraftServer.getServer().worldServers) {
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
			Entity e = x.getX();
			if(e instanceof IRadarDetectableNT) {
				IRadarDetectableNT detectable = (IRadarDetectableNT) e;
				if(detectable.canBeSeenBy(x.getY()) && detectable.paramsApplicable(x.getZ())) return new RadarEntry(detectable, e, detectable.suppliesRedstone(x.getZ()));
			}
			return null;
		});
		//IRadarDetectable, Legacy
		converters.add(x -> {
			Entity e = x.getX();
			RadarScanParams params = x.getZ();
			if(e instanceof IRadarDetectable && params.scanMissiles) {
				return new RadarEntry((IRadarDetectable) e, e);
			}
			return null;
		});
		//Players
		converters.add(x -> {
			if(x.getX() instanceof EntityPlayer && x.getZ().scanPlayers) return new RadarEntry((EntityPlayer) x.getX());
			return null;
		});
	}
}
