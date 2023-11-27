package com.hbm.render.item.block.door;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import org.lwjgl.opengl.GL11;

public class ItemRenderQeContainment extends ItemRenderBase {

    @Override
    public void renderInventory() {
        GL11.glTranslated(0, -3.5, 0);
        GL11.glScaled(3.8, 3.8, 3.8);
    }

    @Override
    public void renderCommon() {
        bindTexture(ResourceManager.qe_containment_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.qe_containment.renderAllExcept("decal");
        bindTexture(ResourceManager.qe_containment_decal);
        ResourceManager.qe_containment.renderPart("decal");
        GL11.glShadeModel(GL11.GL_FLAT);
    }
}
