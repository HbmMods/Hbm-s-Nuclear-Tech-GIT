package com.hbm.dim;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class DebugTeleporter extends Teleporter {

	private final WorldServer sourceServer;
	private final WorldServer targetServer;

	private double x;
	private double y;
	private double z;

	private boolean grounded; // Should we be placed directly on the first ground block below?

	private EntityPlayerMP playerMP;

	public DebugTeleporter(WorldServer sourceServer, WorldServer targetServer, EntityPlayerMP playerMP, double x, double y, double z, boolean grounded) {
		super(targetServer);
		this.sourceServer = sourceServer;
		this.targetServer = targetServer;
		this.playerMP = playerMP;
		this.x = x;
		this.y = y;
		this.z = z;
		this.grounded = grounded;
	}

	@Override

	public void placeInPortal(Entity pEntity, double p2, double p3, double p4, float p5) {
		int ix = (int)x;
		int iy = (int)y;
		int iz = (int)z;

		if(grounded) {
			for(int i = targetServer.getHeight(); i > 0; i--) {
				if(targetServer.getBlock(ix, i, iz) != Blocks.air) {
					y = i + 5;
					break;
				}
			}			
		} else {
			targetServer.getBlock(ix, MathHelper.clamp_int(iy, 1, 255), iz); // dummy load to maybe gen chunk
		}

		pEntity.setPosition(x, y, z);
	}

	private void runTeleport() {
		ServerConfigurationManager manager = playerMP.mcServer.getConfigurationManager();

		// Store these since they change after transfer
		int fromDimension = playerMP.dimension;
		Entity ridingEntity = playerMP.ridingEntity;

		manager.transferPlayerToDimension(playerMP, targetServer.provider.dimensionId, this);

		if(ridingEntity != null && !ridingEntity.isDead) {
			ridingEntity.dimension = fromDimension;
			ridingEntity.worldObj.removeEntity(ridingEntity);
			ridingEntity.isDead = false;

			manager.transferEntityToWorld(ridingEntity, fromDimension, sourceServer, targetServer, this);

			Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(ridingEntity), targetServer);
			if (newEntity != null) {
				newEntity.copyDataFrom(ridingEntity, true);
				targetServer.spawnEntityInWorld(newEntity);
			}

			ridingEntity.isDead = true;
			sourceServer.resetUpdateEntityTick();
			targetServer.resetUpdateEntityTick();

			playerMP.mountEntity(newEntity);
		}
	}

	public static void runQueuedTeleport() {
		if(queuedTeleport == null) return;

		queuedTeleport.runTeleport();

		queuedTeleport = null;
	}

	private static DebugTeleporter queuedTeleport;

	public static void teleport(EntityPlayer player, int dim, double x, double y, double z, boolean grounded) {
		if(player.dimension == dim) return; // ignore if we're teleporting to the same place

		MinecraftServer mServer = MinecraftServer.getServer();
		Side sidex = FMLCommonHandler.instance().getEffectiveSide();
		if (sidex == Side.SERVER) {
			if (player instanceof EntityPlayerMP) {
				EntityPlayerMP playerMP = (EntityPlayerMP) player;
				WorldServer sourceServer = playerMP.getServerForPlayer();
				WorldServer targetServer = (WorldServer) mServer.worldServerForDimension(dim);

				queuedTeleport = new DebugTeleporter(sourceServer, targetServer, playerMP, x, y, z, grounded);
			}
		}
	}

}
