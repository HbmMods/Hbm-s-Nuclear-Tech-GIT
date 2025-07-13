package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.main.ClientProxy;
import com.hbm.particle.ParticleAshes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AshesCreator implements IParticleCreator {
	
	public static void composeEffect(World world, Entity toPulverize, int ashesCount, float ashesScale) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "ashes");
		data.setInteger("entityID", toPulverize.getEntityId());
		data.setInteger("ashesCount", ashesCount);
		data.setFloat("ashesScale", ashesScale);
		IParticleCreator.sendPacket(world, toPulverize.posX, toPulverize.posY, toPulverize.posZ, 100, data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data) {
		
		int entityID = data.getInteger("entityID");
		Entity entity = world.getEntityByID(entityID);
		if(entity == null) return;
		
		ClientProxy.vanish(entityID);
		
		int amount = data.getInteger("ashesCount");
		float scale = data.getFloat("ashesScale");
		
		for(int i = 0; i < amount; i++) {
			ParticleAshes particle = new ParticleAshes(world,
					entity.posX + (entity.width + scale * 2) * (rand.nextDouble() - 0.5),
					entity.posY + entity.height * rand.nextDouble(),
					entity.posZ + (entity.width + scale * 2) * (rand.nextDouble() - 0.5),
					scale);
			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
			EntityFlameFX flame = new EntityFlameFX(world, particle.posX, particle.posY, particle.posZ, 0.0, 0.0, 0.0);
			Minecraft.getMinecraft().effectRenderer.addEffect(flame);
		}
	}
}
