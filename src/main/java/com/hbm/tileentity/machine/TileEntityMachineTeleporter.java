package com.hbm.tileentity.machine;

import java.util.Iterator;
import java.util.List;

import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import com.hbm.util.BufferUtil;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineTeleporter extends TileEntityLoadedBase implements IEnergyReceiverMK2, IBufPacketReceiver {

	public long power = 0;
	public int targetX = -1;
	public int targetY = -1;
	public int targetZ = -1;
	public int targetDim = 0;
	public static final int maxPower = 1_500_000;
	public static final int consumption = 1_000_000;

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

			if(this.targetY != -1) {
				List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord + 0.25, this.yCoord, this.zCoord + 0.25, this.xCoord + 0.75, this.yCoord + 2, this.zCoord + 0.75));

				if(!entities.isEmpty()) {
					for(Entity e : entities) {
						teleport(e);
					}
				}
			}

			networkPackNT(15);

		} else {

			if(this.targetY != -1 && power >= consumption) {
				double x = xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				double y = yCoord + 1 + worldObj.rand.nextDouble() * 2D;
				double z = zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				worldObj.spawnParticle("reddust", x, y, z, 0.4F, 0.8F, 1F);
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeLong(power);
		BufferUtil.writeIntArray(buf, new int[] {targetX, targetY, targetZ, targetDim});
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.power = buf.readLong();
		int[] target = BufferUtil.readIntArray(buf);
		this.targetX = target[0];
		this.targetY = target[1];
		this.targetZ = target[2];
		this.targetDim = target[3];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		targetX = nbt.getInteger("x1");
		targetY = nbt.getInteger("y1");
		targetZ = nbt.getInteger("z1");
		targetDim = nbt.getInteger("dim");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setInteger("x1", targetX);
		nbt.setInteger("y1", targetY);
		nbt.setInteger("z1", targetZ);
		nbt.setInteger("dim", targetDim);
	}

	public void teleport(Entity entity) {

		if(this.power < consumption) return;

		worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "mob.endermen.portal", 1.0F, 1.0F);

		if((entity instanceof EntityPlayerMP)) {

			EntityPlayerMP player = (EntityPlayerMP) entity;
			if(entity.dimension == this.targetDim) {
				player.setPositionAndUpdate(this.targetX + 0.5D, this.targetY + 1.5D + entity.getYOffset(), this.targetZ + 0.5D);
			} else {
				teleportPlayerInterdimensionally(player, this.targetX + 0.5D, this.targetY + 1.5D + entity.getYOffset(), this.targetZ + 0.5D, this.targetDim);
			}

		} else {

			if(entity.dimension == this.targetDim) {
				entity.setPositionAndRotation(this.targetX + 0.5D, this.targetY + 1.5D + entity.getYOffset(), this.targetZ + 0.5D, entity.rotationYaw, entity.rotationPitch);

				try {
					EntityTracker entitytracker = ((WorldServer)worldObj).getEntityTracker();
					IntHashMap map = ReflectionHelper.getPrivateValue(EntityTracker.class, entitytracker, "trackedEntityIDs", "field_72794_c");
					EntityTrackerEntry entry = (EntityTrackerEntry) map.lookup(entity.getEntityId());
					int yawByte = MathHelper.floor_float(entity.rotationYaw * 256.0F / 360.0F);
					int pitchByte = MathHelper.floor_float(entity.rotationPitch * 256.0F / 360.0F);
					int x32 = entity.myEntitySize.multiplyBy32AndRound(entity.posX);
					int y32 = MathHelper.floor_double(entity.posY * 32.0D);
					int z32 = entity.myEntitySize.multiplyBy32AndRound(entity.posZ);
					entry.func_151259_a(new S18PacketEntityTeleport(entity.getEntityId(), x32, y32, z32, (byte)yawByte, (byte)pitchByte));
				} catch(Exception ex) { }
			} else {
				teleportEntityInterdimensionally(entity, this.targetX + 0.5D, this.targetY + 1.5D + entity.getYOffset(), this.targetZ + 0.5D, this.targetDim);
			}
		}

		worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.0F, 1.0F);

		this.power -= consumption;
		this.markDirty();
	}

	/** Teleports a player to a different dimension, gracefully copied from ServerConfigurationManager */
	public static boolean teleportPlayerInterdimensionally(EntityPlayerMP player, double x, double y, double z, int dim) {

		int prevDim = player.dimension;
		WorldServer prevWorld = player.mcServer.worldServerForDimension(prevDim);
		WorldServer newWorld = player.mcServer.worldServerForDimension(dim);
		player.dimension = dim;

		if(newWorld == null) return false;

		ServerConfigurationManager man = player.mcServer.getConfigurationManager();
		NetHandlerPlayServer net = player.playerNetServerHandler;
		net.sendPacket(new S07PacketRespawn(player.dimension, player.worldObj.difficultySetting, newWorld.getWorldInfo().getTerrainType(), player.theItemInWorldManager.getGameType()));
		prevWorld.removePlayerEntityDangerously(player);
		player.isDead = false;

		if(player.isEntityAlive()) {
			player.setLocationAndAngles(x, y, z, player.rotationYaw, player.rotationPitch);
			newWorld.spawnEntityInWorld(player);
			newWorld.updateEntityWithOptionalForce(player, false);
		}

		player.setWorld(newWorld);

		man.func_72375_a(player, prevWorld);
		net.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
		player.theItemInWorldManager.setWorld(newWorld);
		man.updateTimeAndWeatherForPlayer(player, newWorld);
		man.syncPlayerInventory(player);
		Iterator iterator = player.getActivePotionEffects().iterator();

		while(iterator.hasNext()) {
			PotionEffect potioneffect = (PotionEffect) iterator.next();
			player.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(player.getEntityId(), potioneffect));
		}
		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, prevDim, dim);
		return true;
	}

	/** Teleports non-player entities to different dimensions, gracefully copied from Entity */
	public static boolean teleportEntityInterdimensionally(Entity oldEntity, double x, double y, double z, int dim) {

		MinecraftServer minecraftserver = MinecraftServer.getServer();
		WorldServer newWorld = minecraftserver.worldServerForDimension(dim);

		if(newWorld == null) return false;

		oldEntity.worldObj.removeEntity(oldEntity);
		oldEntity.isDead = false;

		Entity entity = EntityList.createEntityByName(EntityList.getEntityString(oldEntity), newWorld);

		if(entity != null) {
			entity.copyDataFrom(oldEntity, true);
			entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
			newWorld.updateEntityWithOptionalForce(entity, false);
			entity.setWorld(newWorld);

			IChunkProvider provider = newWorld.getChunkProvider();
			provider.loadChunk(((int) Math.floor(x)) >> 4, ((int) Math.floor(z)) >> 4);
			newWorld.spawnEntityInWorld(entity);
		}

		oldEntity.isDead = true;
		newWorld.resetUpdateEntityTick();

		return true;
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
}
