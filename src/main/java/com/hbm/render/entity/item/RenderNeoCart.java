package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.cart.EntityMinecartNTM;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderNeoCart extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float rot, float interp) {
		this.doRender((EntityMinecartNTM) entity, x, y, z, rot, interp);
	}

	public void doRender(EntityMinecartNTM cart, double x, double y, double z, float rot, float interp) {
		
		GL11.glPushMatrix();
		this.bindEntityTexture(cart);
		long rand = (long) cart.getEntityId() * 493286711L;
		rand = rand * rand * 4392167121L + rand * 98761L;
		float randX = (((float) (rand >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float randY = (((float) (rand >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float randZ = (((float) (rand >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		GL11.glTranslatef(randX, randY, randZ);
		double interpX = cart.lastTickPosX + (cart.posX - cart.lastTickPosX) * (double) interp;
		double interpY = cart.lastTickPosY + (cart.posY - cart.lastTickPosY) * (double) interp;
		double interpZ = cart.lastTickPosZ + (cart.posZ - cart.lastTickPosZ) * (double) interp;
		double mult = 0.3;
		Vec3 vec3 = cart.func_70489_a(interpX, interpY, interpZ);
		float interpPitch = cart.prevRotationPitch + (cart.rotationPitch - cart.prevRotationPitch) * interp;

		if(vec3 != null) {
			Vec3 vec31 = cart.func_70495_a(interpX, interpY, interpZ, mult);
			Vec3 vec32 = cart.func_70495_a(interpX, interpY, interpZ, -mult);

			if(vec31 == null) {
				vec31 = vec3;
			}

			if(vec32 == null) {
				vec32 = vec3;
			}

			x += vec3.xCoord - interpX;
			y += (vec31.yCoord + vec32.yCoord) / 2.0D - interpY;
			z += vec3.zCoord - interpZ;
			Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);

			if(vec33.lengthVector() != 0.0D) {
				vec33 = vec33.normalize();
				rot = (float) (Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
				interpPitch = (float) (Math.atan(vec33.yCoord) * 73.0D);
			}
		}

		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - rot, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-interpPitch, 0.0F, 0.0F, 1.0F);
		float interpRoll = (float) cart.getRollingAmplitude() - interp;
		float interpDamage = cart.getDamage() - interp;

		if(interpDamage < 0.0F) {
			interpDamage = 0.0F;
		}

		GL11.glTranslatef(0, -0.4375F, 0);
		GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
		ResourceManager.cart.renderPart("Carriage");

		if(interpRoll > 0.0F) {
			GL11.glTranslatef(0, 0.75F, 0);
			GL11.glRotatef(MathHelper.sin(interpRoll) * interpRoll * interpDamage / 10.0F * (float) cart.getRollingDirection(), 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0, -0.75F, 0);
		}

		ResourceManager.cart.renderPart("Bucket");
		cart.renderSpecialContent(this);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return getEntityTexture((EntityMinecartNTM) entity);
	}
	
	protected ResourceLocation getEntityTexture(EntityMinecartNTM entity) {
		switch(entity.getBase()) {
		case PAINTED: return ResourceManager.cart_metal;
		default:
		case STEEL: return ResourceManager.cart_blank;
		case WOOD: return ResourceManager.cart_wood;
		}
	}

	//"NoT vIsIbLe" how about you kiss my fucking ass
	@Override
	public void bindEntityTexture(Entity entity) {
		this.bindTexture(this.getEntityTexture(entity));
	}

	@Override
	public void bindTexture(ResourceLocation loc) {
		this.renderManager.renderEngine.bindTexture(loc);
	}
}
