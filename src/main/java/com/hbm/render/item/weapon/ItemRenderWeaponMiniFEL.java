package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfiguration;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;


public class ItemRenderWeaponMiniFEL implements IItemRenderer {

    public ItemRenderWeaponMiniFEL() {}

        @Override
        public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type){
            switch (type) {
                case EQUIPPED:
                case EQUIPPED_FIRST_PERSON:
                case ENTITY:
                case INVENTORY:
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean shouldUseRenderHelper (IItemRenderer.ItemRenderType type, ItemStack
        item, IItemRenderer.ItemRendererHelper helper){

            return type == IItemRenderer.ItemRenderType.ENTITY && (helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION || helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING);
        }

        @Override
        public void renderItem (IItemRenderer.ItemRenderType type, ItemStack item, Object...data){

            GL11.glPushMatrix();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_CULL_FACE);


            switch (type) {

                case EQUIPPED_FIRST_PERSON:

                    double s0 = 0.5D;
                    GL11.glRotated(25, 0, 0, 1);
                    GL11.glTranslated(2.25, 0.0, 0.125);
                    GL11.glRotatef(-10, 0, 1, 0);
                    GL11.glScaled(s0, s0, s0);

                    break;

                case EQUIPPED:

                    double scale = 0.25D;
                    GL11.glScaled(-scale, scale, scale);
                    GL11.glRotatef(20F, -3.0F, -0.75F, -1.0F);
                    GL11.glRotatef(-170, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-30F, 2.0F, -1F, -5.0F);
                    GL11.glTranslatef(5F, -0.35F, 0.25F);

                    break;

                case ENTITY:

                    double s1 = 0.5D;
                    GL11.glScaled(s1, s1, s1);

                    break;

                case INVENTORY:

                    double s = 1.65D;
                    GL11.glTranslatef(8F, 8F, 0F);
                    GL11.glRotated(90, 0, 0, 1);
                    GL11.glRotated(135, 0, 0, 1);
                    GL11.glScaled(s, s, s);

                    break;

                default:
                    break;
            }

        }

    }
