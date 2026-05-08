package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderDrill extends ItemRenderWeaponBase {

    @Override
    public void setupFirstPerson(ItemStack stack) {
        GL11.glTranslated(0, 0, 0.9);
    }

    @Override
    public void renderFirstPerson(ItemStack stack) {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.drill_tex);
        ResourceManager.drill.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }

    @Override
    public void renderOther(ItemStack stack, ItemRenderType type, Object... data) {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.drill_tex);
        ResourceManager.drill.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
    }
}

