package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderNI4NI extends ItemRenderWeaponBase {

    @Override
    public void setupFirstPerson(ItemStack stack) {
        GL11.glTranslated(0, 0, 0.9);
    }

    @Override
    public void renderFirstPerson(ItemStack stack) {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.n_i_4_n_i_tex);
        ResourceManager.n_i_4_n_i.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }

    @Override
    public void renderOther(ItemStack stack, ItemRenderType type, Object... data) {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.n_i_4_n_i_tex);
        ResourceManager.n_i_4_n_i.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }
}

