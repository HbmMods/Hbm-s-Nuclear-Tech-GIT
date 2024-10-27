package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.particle.ParticleExplosionSmall;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ExplosionSmallCreator implements IParticleCreator {
	
	public static final double speedOfSound = (17.15D) * 0.5;
	
	public static void composeEffect(World world, double x, double y, double z, int cloudCount, float cloudScale, float cloudSpeedMult) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "explosionSmall");
		data.setInteger("cloudCount", cloudCount);
		data.setFloat("cloudScale", cloudScale);
		data.setFloat("cloudSpeedMult", cloudSpeedMult);
		data.setInteger("debris", 15);
		IParticleCreator.sendPacket(world, x, y, z, 200, data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data) {

		int cloudCount = data.getInteger("cloudCount");
		float cloudScale = data.getFloat("cloudScale");
		float cloudSpeedMult = data.getFloat("cloudSpeedMult");
		int debris = data.getInteger("debris");
		
		float dist = (float) player.getDistance(x, y, z);
		float soundRange = 200F;

		if(dist <= soundRange) {
			String sound = dist <= soundRange * 0.4 ? "hbm:weapon.explosionSmallNear" : "hbm:weapon.explosionSmallFar";
			PositionedSoundRecord positionedsoundrecord = new PositionedSoundRecord(new ResourceLocation(sound), 100F, 0.9F + rand.nextFloat() * 0.2F, (float) x, (float) y, (float) z);
			Minecraft.getMinecraft().getSoundHandler().playDelayedSound(positionedsoundrecord, (int) (dist / speedOfSound));
		}
		
		for(int i = 0; i < cloudCount; i++) {
			ParticleExplosionSmall particle = new ParticleExplosionSmall(world, x, y, z, cloudScale, cloudSpeedMult);
			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
		}
		
		Block b = Blocks.air;
		int meta = 0;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			b = world.getBlock((int) Math.floor(x) + dir.offsetX, (int) Math.floor(y) + dir.offsetY, (int) Math.floor(z) + dir.offsetZ);
			meta = world.getBlockMetadata((int) Math.floor(x) + dir.offsetX, (int) Math.floor(y) + dir.offsetY, (int) Math.floor(z) + dir.offsetZ);
			if(b != Blocks.air) break;
		}
		
		if(b != Blocks.air) for(int i = 0; i < debris; i++) {
			EntityBlockDustFX fx = new EntityBlockDustFX(world, x, y + 0.1, z, world.rand.nextGaussian() * 0.2, 0.5F + world.rand.nextDouble() * 0.7, world.rand.nextGaussian() * 0.2, b, meta);
			fx.multipleParticleScaleBy(2);
			ReflectionHelper.setPrivateValue(EntityFX.class, fx, 50 + rand.nextInt(20), "particleMaxAge", "field_70547_e");
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}
}
