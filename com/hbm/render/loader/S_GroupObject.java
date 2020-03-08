package com.hbm.render.loader;

import java.util.ArrayList;

import net.minecraft.client.renderer.Tessellator;

public class S_GroupObject {
	public String name;
	public ArrayList<S_Face> faces = new ArrayList();
	public int glDrawingMode;

	public S_GroupObject() {
		this("");
	}

	public S_GroupObject(String name) {
		this(name, -1);
	}

	public S_GroupObject(String name, int glDrawingMode) {
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}

	public void render() {
		if (this.faces.size() > 0) {
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawing(this.glDrawingMode);
			render(tessellator);
			tessellator.draw();
		}
	}

	public void render(Tessellator tessellator) {
		if (this.faces.size() > 0) {
			for (S_Face face : this.faces) {
				face.addFaceForRender(tessellator);
			}
		}
	}
}
