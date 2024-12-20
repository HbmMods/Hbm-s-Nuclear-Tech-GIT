package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class ActorVillager implements ISpecialActor {

	EntityVillager villager = new EntityVillager(Minecraft.getMinecraft().theWorld);
	NBTTagCompound data = new NBTTagCompound();

	public ActorVillager() { }

	public ActorVillager(NBTTagCompound data) {
		this.data = data;
	}

	@Override
	public void drawForegroundComponent(int w, int h, int ticks, float interp) { }

	@Override
	public void drawBackgroundComponent(WorldInAJar world, int ticks, float interp) {
		double x = data.getDouble("x");
		double y = data.getDouble("y");
		double z = data.getDouble("z");
		double yaw = data.getDouble("yaw");
		GL11.glTranslated(x, y, z);
		GL11.glRotated(yaw, 0, 1, 0);
		RenderManager.instance.renderEntityWithPosYaw(villager, 0D, 0D, 0D, 0F, interp);
	}

	@Override
	public void updateActor(JarScene scene) {
		villager.limbSwingAmount += (1F - villager.limbSwingAmount) * 0.4F;
		villager.limbSwing += villager.limbSwingAmount;
	}

	@Override
	public void setActorData(NBTTagCompound data) {

	}

	@Override
	public void setDataPoint(String tag, Object o) {

	}
}
