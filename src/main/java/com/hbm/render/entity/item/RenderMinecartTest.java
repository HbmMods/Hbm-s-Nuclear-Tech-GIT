package com.hbm.render.entity.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.item.EntityMinecartTest;
import com.hbm.lib.RefStrings;

public class RenderMinecartTest extends RenderMinecart
{
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/LilBoy1.obj");
	private IModelCustom boyModel;
    private ResourceLocation boyTexture;
    public RenderMinecartTest() {
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/LilBoy2.png");
    }
    @Override
	public void doRender(EntityMinecart p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        GL11.glPushMatrix();
        this.bindEntityTexture(p_76986_1_);
        long i = p_76986_1_.getEntityId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f2 = (((i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f3 = (((i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f4 = (((i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        GL11.glTranslatef(f2, f3, f4);
        double d3 = p_76986_1_.lastTickPosX + (p_76986_1_.posX - p_76986_1_.lastTickPosX) * p_76986_9_;
        double d4 = p_76986_1_.lastTickPosY + (p_76986_1_.posY - p_76986_1_.lastTickPosY) * p_76986_9_;
        double d5 = p_76986_1_.lastTickPosZ + (p_76986_1_.posZ - p_76986_1_.lastTickPosZ) * p_76986_9_;
        double d6 = 0.30000001192092896D;
        Vec3 vec3 = p_76986_1_.func_70489_a(d3, d4, d5);
        float f5 = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;

        if (vec3 != null)
        {
            Vec3 vec31 = p_76986_1_.func_70495_a(d3, d4, d5, d6);
            Vec3 vec32 = p_76986_1_.func_70495_a(d3, d4, d5, -d6);

            if (vec31 == null)
            {
                vec31 = vec3;
            }

            if (vec32 == null)
            {
                vec32 = vec3;
            }

            p_76986_2_ += vec3.xCoord - d3;
            p_76986_4_ += (vec31.yCoord + vec32.yCoord) / 2.0D - d4;
            p_76986_6_ += vec3.zCoord - d5;
            Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);

            if (vec33.lengthVector() != 0.0D)
            {
                vec33 = vec33.normalize();
                p_76986_8_ = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
                f5 = (float)(Math.atan(vec33.yCoord) * 73.0D);
            }
        }

        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
        
        //
        if(p_76986_1_.getDataWatcher().getWatchableObjectInt(23) < 0 || p_76986_1_.getDataWatcher().getWatchableObjectInt(24) < 0)
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        //
        
        GL11.glRotatef(-f5, 0.0F, 0.0F, 1.0F);
        float f7 = p_76986_1_.getRollingAmplitude() - p_76986_9_;
        float f8 = p_76986_1_.getDamage() - p_76986_9_;

        if (f8 < 0.0F)
        {
            f8 = 0.0F;
        }

        if (f7 > 0.0F)
        {
            GL11.glRotatef(MathHelper.sin(f7) * f7 * f8 / 10.0F * p_76986_1_.getRollingDirection(), 1.0F, 0.0F, 0.0F);
        }

        int k = p_76986_1_.getDisplayTileOffset();
        Block block = p_76986_1_.func_145820_n();
        int j = p_76986_1_.getDisplayTileData();

        if (block.getRenderType() != -1)
        {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float f6 = 0.75F;
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.0F, k / 16.0F, 0.0F);
            this.func_147910_a(p_76986_1_, p_76986_9_, block, j);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindEntityTexture(p_76986_1_);
        }

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.modelMinecart.render(p_76986_1_, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    protected void func_147910_a(EntityMinecartTest p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_)
    {
        int j = p_147910_1_.func_94104_d();

        if (j > -1 && j - p_147910_2_ + 1.0F < 10.0F)
        {
            float f1 = 1.0F - (j - p_147910_2_ + 1.0F) / 10.0F;

            if (f1 < 0.0F)
            {
                f1 = 0.0F;
            }

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            f1 *= f1;
            f1 *= f1;
            float f2 = 1.0F + f1 * 0.3F;
            GL11.glScalef(f2, f2, f2);
        }

        GL11.glPushMatrix();
        //this.field_94145_f.renderBlockAsItem(ModBlocks.crate, 0, 1.0F);
        bindTexture(boyTexture);
        boyModel.renderAll();
        GL11.glPopMatrix();
        //super.func_147910_a(p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);

        if (j > -1 && j / 5 % 2 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, (1.0F - (j - p_147910_2_ + 1.0F) / 100.0F) * 0.8F);
            GL11.glPushMatrix();
            //this.field_94145_f.renderBlockAsItem(ModBlocks.crate, 0, 1.0F);
            bindTexture(boyTexture);
            boyModel.renderAll();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    @Override
	protected void func_147910_a(EntityMinecart p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_)
    {
        this.func_147910_a((EntityMinecartTest)p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
    }
}