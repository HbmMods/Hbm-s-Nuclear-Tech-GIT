package api.hbm.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class RadarEntry {

	public String unlocalizedName;
	public int blipLevel;
	public int posX;
	public int posY;
	public int posZ;
	public int dim;
	
	public RadarEntry(String name, int level, int x, int y, int z, int dim) {
		this.unlocalizedName = name;
		this.blipLevel = level;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.dim = dim;
	}
	
	public RadarEntry(IRadarDetectableNT detectable, Entity entity) {
		this(detectable.getUnlocalizedName(), detectable.getBlipLevel(), (int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ), entity.dimension);
	}
	
	public RadarEntry(IRadarDetectable detectable, Entity entity) {
		this(detectable.getTargetType().name, detectable.getTargetType().ordinal(), (int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ), entity.dimension);
	}
	
	public RadarEntry(EntityPlayer player) {
		this(player.getDisplayName(), IRadarDetectableNT.PLAYER, (int) Math.floor(player.posX), (int) Math.floor(player.posY), (int) Math.floor(player.posZ), player.dimension);
	}
}
