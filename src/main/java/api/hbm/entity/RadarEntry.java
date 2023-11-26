package api.hbm.entity;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class RadarEntry {

	/** Name use for radar display, uses I18n for lookup */
	public String unlocalizedName;
	/** The type of dot to show on the radar as well as the redstone level in tier mode */
	public int blipLevel;
	public int posX;
	public int posY;
	public int posZ;
	public int dim;
	public int entityID;
	/** Whether this radar entry should be counted for the redstone output */
	public boolean redstone;
	
	public RadarEntry() { } //blank ctor for packets
	
	public RadarEntry(String name, int level, int x, int y, int z, int dim, int entityID, boolean redstone) {
		this.unlocalizedName = name;
		this.blipLevel = level;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.dim = dim;
		this.entityID = entityID;
		this.redstone = redstone;
	}
	
	public RadarEntry(IRadarDetectableNT detectable, Entity entity, boolean redstone) {
		this(detectable.getUnlocalizedName(), detectable.getBlipLevel(), (int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ), entity.dimension, entity.getEntityId(), redstone);
	}
	
	public RadarEntry(IRadarDetectable detectable, Entity entity) {
		this(detectable.getTargetType().name, detectable.getTargetType().ordinal(), (int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ), entity.dimension, entity.getEntityId(), entity.motionY < 0);
	}
	
	public RadarEntry(EntityPlayer player) {
		this(player.getDisplayName(), IRadarDetectableNT.PLAYER, (int) Math.floor(player.posX), (int) Math.floor(player.posY), (int) Math.floor(player.posZ), player.dimension, player.getEntityId(), true);
	}
	
	public void fromBytes(ByteBuf buf) {
		this.unlocalizedName = ByteBufUtils.readUTF8String(buf);
		this.blipLevel = buf.readShort();
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();
		this.dim = buf.readShort();
		this.entityID = buf.readInt();
	}
	
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.unlocalizedName);
		buf.writeShort(this.blipLevel);
		buf.writeInt(this.posX);
		buf.writeInt(this.posY);
		buf.writeInt(this.posZ);
		buf.writeShort(this.dim);
		buf.writeInt(this.entityID);
	}
}
