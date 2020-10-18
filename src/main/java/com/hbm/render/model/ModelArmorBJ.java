package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.util.MathHelper;

public class ModelArmorBJ extends ModelBiped {
	
	int type;

	ModelRendererObj head;
	ModelRendererObj body;
	ModelRendererObj leftArm;
	ModelRendererObj rightArm;
	ModelRendererObj leftLeg;
	ModelRendererObj rightLeg;
	
	public ModelArmorBJ(int type) {
		this.type = type;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {

		head = new ModelRendererObj(ResourceManager.armor_bj, "Head");
		body = new ModelRendererObj(ResourceManager.armor_bj, "Body");
		leftArm = new ModelRendererObj(ResourceManager.armor_bj, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(ResourceManager.armor_bj, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(ResourceManager.armor_bj, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(ResourceManager.armor_bj, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);

		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		
		if(type == 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_eyepatch);
			head.render(par7);
		}
		if(type == 1) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_chest);
			body.render(par7);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_arm);
			leftArm.render(par7);
			rightArm.render(par7);
		}
		if(type == 2) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_leg);
			leftLeg.render(par7);
			rightLeg.render(par7);
		}
		
		GL11.glPopMatrix();
	}
	
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity) {
    	
        head.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        rightArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 2.0F * p_78087_2_ * 0.5F;
        leftArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
        rightArm.rotateAngleZ = 0.0F;
        leftArm.rotateAngleZ = 0.0F;
        rightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
        leftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
        rightLeg.rotateAngleY = 0.0F;
        leftLeg.rotateAngleY = 0.0F;
    	
    	if(entity instanceof EntityPlayer) {
    		EntityPlayer player = (EntityPlayer)entity;
    		
    		this.aimedBow = false;

    		if(player.getHeldItem() != null) {
    			
    			int hold = 1;
    			
    			if(player.getItemInUseCount() > 0) {
	    			
	                EnumAction action = player.getHeldItem().getItemUseAction();
	
	                if(action == EnumAction.block)
	                	hold = 3;
	
	                if(action == EnumAction.bow)
	                	this.aimedBow = true;
    			}
    			
    			if(player.getHeldItem().getItem() instanceof IHoldableWeapon)
                	this.aimedBow = true;
    			
    			rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * hold;
    		}
    		
    		this.isSneak = player.isSneaking();
    		this.isRiding = player.isRiding();
    	}

        if (this.isRiding)
        {
            rightArm.rotateAngleX += -((float)Math.PI / 5F);
            leftArm.rotateAngleX += -((float)Math.PI / 5F);
            rightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            leftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            rightLeg.rotateAngleY = ((float)Math.PI / 10F);
            leftLeg.rotateAngleY = -((float)Math.PI / 10F);
        }

        if (this.heldItemLeft != 0)
        {
            leftArm.rotateAngleX = leftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
        }

        if (this.heldItemRight != 0)
        {
            rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
        }

        rightArm.rotateAngleY = 0.0F;
        leftArm.rotateAngleY = 0.0F;
        float f6;
        float f7;

        if (this.onGround > -9990.0F)
        {
            f6 = this.onGround;
            body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
            rightArm.rotationPointZ = MathHelper.sin(body.rotateAngleY) * 5.0F;
            rightArm.rotationPointX = -MathHelper.cos(body.rotateAngleY) * 5.0F;
            leftArm.rotationPointZ = -MathHelper.sin(body.rotateAngleY) * 5.0F;
            leftArm.rotationPointX = MathHelper.cos(body.rotateAngleY) * 5.0F;
            rightArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleX += body.rotateAngleY;
            f6 = 1.0F - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(this.onGround * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
            rightArm.rotateAngleX = (float)((double)rightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            rightArm.rotateAngleY += body.rotateAngleY * 2.0F;
            rightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
        }

        if (this.isSneak)
        {
            body.rotateAngleX = 0.5F;
            rightArm.rotateAngleX += 0.4F;
            leftArm.rotateAngleX += 0.4F;
            rightLeg.offsetZ = 4.0F;
            leftLeg.offsetZ = 4.0F;
            rightLeg.offsetY = -3.0F;
            leftLeg.offsetY = -3.0F;
            head.offsetY = 1.0F;
        }
        else
        {
            body.rotateAngleX = 0.0F;
            rightLeg.rotationPointZ = 0.1F;
            leftLeg.rotationPointZ = 0.1F;
            rightLeg.rotationPointY = 12.0F;
            leftLeg.rotationPointY = 12.0F;
            head.rotationPointY = 0.0F;
        }

        rightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        leftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        rightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
        leftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;

        if (this.aimedBow)
        {
            f6 = 0.0F;
            f7 = 0.0F;
            rightArm.rotateAngleZ = 0.0F;
            leftArm.rotateAngleZ = 0.0F;
            rightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + head.rotateAngleY;
            leftArm.rotateAngleY = 0.1F - f6 * 0.6F + head.rotateAngleY + 0.4F;
            rightArm.rotateAngleX = -((float)Math.PI / 2F) + head.rotateAngleX;
            leftArm.rotateAngleX = -((float)Math.PI / 2F) + head.rotateAngleX;
            rightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            leftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            rightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
            leftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
            rightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
            leftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
        }
    }

}
