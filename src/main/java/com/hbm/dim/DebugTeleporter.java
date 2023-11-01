package com.hbm.dim;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class DebugTeleporter extends Teleporter {

	private final WorldServer worldServerInstance;





	private double x;

	private double y;

	private double z;





	public DebugTeleporter(WorldServer world, double x, double y, double z) {

	        super(world);

	        this.worldServerInstance = world;

	        this.x=x;

	        this.y=y;

	        this.z=z;



	}



	@Override

	public void placeInPortal(Entity pEntity, double p2, double p3, double p4, float p5) {

	        int i = (int) p2;

	        int j = (int) p3;

	        int k = (int) p4;
	            
	        this.worldServerInstance.getBlock((int)this.x, (int)this.y, (int)this.z);  //dummy load to maybe gen chunk       
	        pEntity.setPosition(this.x, this.y, this.z);
	}

	public static void teleport(EntityPlayer player, int dim, double x, double y, double z)
	{
		MinecraftServer mServer = MinecraftServer.getServer();
		Side sidex = FMLCommonHandler.instance().getEffectiveSide();
		if (sidex == Side.SERVER){
			if (player instanceof EntityPlayerMP){
				WorldServer worldserver = (WorldServer)mServer.worldServerForDimension(dim);
				EntityPlayerMP playerMP = (EntityPlayerMP)player;
				if (player.ridingEntity == null && player instanceof EntityPlayer){
					FMLCommonHandler.instance().getMinecraftServerInstance();
					playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, dim, new com.hbm.dim.DebugTeleporter(mServer.worldServerForDimension(dim), x, y, z));
					}else if (player.ridingEntity != null && player instanceof EntityPlayer) {
						playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, dim, new com.hbm.dim.DebugTeleporter(mServer.worldServerForDimension(dim), x, y, z));

					}
				}
			}
		}
	}	
