package com.hbm.tileentity.machine;

import java.util.Iterator;
import java.util.List;

import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;

public class TileEntityMachineTeleporter extends TileEntityLoadedBase implements IEnergyUser, INBTPacketReceiver {

	public long power = 0;
	public int targetX = -1;
	public int targetY = -1;
	public int targetZ = -1;
	public int targetDim = 0;
	public static final int maxPower = 1_500_000;
	public static final int consumption = 1_000_000;

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

	@Override
	public void updateEntity() {

		boolean b0 = false;
		
		if (!this.worldObj.isRemote) {
			this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);
			
			List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.25, this.yCoord, this.zCoord - 0.25, this.xCoord + 1.5, this.yCoord + 2, this.zCoord + 1.5));
			
			if(!entities.isEmpty()) {
				for(Entity e : entities) {
					teleport(e);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setIntArray("target", new int[] {targetX, targetY, targetZ, targetDim});
			INBTPacketReceiver.networkPack(this, data, 15);
			
		} else {
			
			if(power >= consumption) {
				double x = xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				double y = yCoord + 1 + worldObj.rand.nextDouble() * 2D;
				double z = zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				worldObj.spawnParticle("reddust", x, y, z, 0.4F, 0.8F, 1F);
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		int[] target = nbt.getIntArray("target");
		this.targetX = target[0];
		this.targetX = target[1];
		this.targetX = target[2];
		this.targetDim = target[3];
	}

	public void teleport(Entity entity) {
		
		if(this.power < consumption) return;
		
		worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "mob.endermen.portal", 1.0F, 1.0F);
		
		if((entity instanceof EntityPlayerMP)) {
			
			EntityPlayerMP player = (EntityPlayerMP) entity;
			if(entity.dimension == this.targetDim) {
				player.setPositionAndUpdate(this.targetX + 0.5D, this.targetY + 1D + entity.getYOffset(), this.targetZ + 0.5D);
			} else {
				teleportPlayerInterdimensionally(player, this.targetX + 0.5D, this.targetY + 1D + entity.getYOffset(), this.targetZ + 0.5D, this.targetDim);
			}
			
		} else {
			
			if(entity.dimension == this.targetDim) {
				entity.setPositionAndRotation(this.targetX + 0.5D, this.targetY + 1D + entity.getYOffset(), this.targetZ + 0.5D, entity.rotationYaw, entity.rotationPitch);
			} else {
				teleportEntityInterdimensionally(entity, this.targetX + 0.5D, this.targetY + 1D + entity.getYOffset(), this.targetZ + 0.5D, this.targetDim);
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

		int prevDim = oldEntity.dimension;
		WorldServer prevWorld = minecraftserver.worldServerForDimension(prevDim);
		WorldServer newWorld = minecraftserver.worldServerForDimension(dim);
		
		if(newWorld == null) return false;

		if(dim == 1 && prevDim == 1) {
			newWorld = minecraftserver.worldServerForDimension(0);
			oldEntity.dimension = 0;
		}

		oldEntity.worldObj.removeEntity(oldEntity);
		oldEntity.isDead = false;
		minecraftserver.getConfigurationManager().transferEntityToWorld(oldEntity, prevDim, prevWorld, newWorld);
		Entity entity = EntityList.createEntityByName(EntityList.getEntityString(oldEntity), newWorld);

		if(entity != null) {
			entity.copyDataFrom(oldEntity, true);

			if(dim == 1 && prevDim == 1) {
				entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
			}

			newWorld.spawnEntityInWorld(entity);
		}

		entity.isDead = true;
		prevWorld.resetUpdateEntityTick();
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
