package com.hbm.render.loader;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;

@Deprecated
public class HbmGroupObject {
	
    public String name;
    public ArrayList<HbmFace> faces = new ArrayList<HbmFace>();
    public int glDrawingMode;

    public HbmGroupObject()
    {
        this("");
    }

    public HbmGroupObject(String name)
    {
        this(name, -1);
    }

    public HbmGroupObject(String name, int glDrawingMode)
    {
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    @SideOnly(Side.CLIENT)
    public void render(float currentTime)
    {
        if (faces.size() > 0)
        {
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(glDrawingMode);
            render(currentTime, tessellator);
            tessellator.draw();
        }
    }

    @SideOnly(Side.CLIENT)
    public void render(float currentTime, Tessellator tessellator)
    {
        if (faces.size() > 0)
        {
            for (HbmFace face : faces)
            {
                face.addFaceForRender(currentTime, tessellator);
            }
        }
    }
}
