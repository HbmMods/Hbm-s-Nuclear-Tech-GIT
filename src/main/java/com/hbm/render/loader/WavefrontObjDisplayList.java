package com.hbm.render.loader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class WavefrontObjDisplayList implements IModelCustom {

	public List<Pair<String, Integer>> nameToCallList = new ArrayList<>();
	
	public WavefrontObjDisplayList(WavefrontObject obj) {
		Tessellator tes = Tessellator.instance;
		for(GroupObject g : obj.groupObjects){
			int list = GL11.glGenLists(1);
			GL11.glNewList(list, GL11.GL_COMPILE);
			tes.startDrawing(g.glDrawingMode);
			g.render(tes);
			tes.draw();
			GL11.glEndList();
			nameToCallList.add(Pair.of(g.name, list));
		}
		
	}
	
	public WavefrontObjDisplayList(HFRWavefrontObject obj) {
		for(S_GroupObject g : obj.groupObjects){
			int list = GL11.glGenLists(1);
			GL11.glNewList(list, GL11.GL_COMPILE);
			g.render();
			GL11.glEndList();
			nameToCallList.add(Pair.of(g.name, list));
		}
	}

	public int getListForName(String name){
		for(Pair<String, Integer> p : nameToCallList){
			if(p.getLeft().equalsIgnoreCase(name)){
				return p.getRight();
			}
		}
		return 0;
	}

	@Override
	public String getType() {
		return "obj_list";
	}

	@Override
	public void renderAll() {
		for(Pair<String, Integer> p : nameToCallList)
			callList(p.getRight());
	}

	@Override
	public void renderOnly(String... groupNames) {
		for(Pair<String, Integer> p : nameToCallList){
			for(String name : groupNames){
				if(p.getLeft().equalsIgnoreCase(name)){
					callList(p.getRight());
					break;
				}
			}
		}
	}

	@Override
	public void renderPart(String partName) {
		for(Pair<String, Integer> p : nameToCallList){
			if(p.getLeft().equalsIgnoreCase(partName)){
				callList(p.getRight());
			}
		}
	}

	@Override
	public void renderAllExcept(String... excludedGroupNames) {
		for(Pair<String, Integer> p : nameToCallList){
			boolean skip = false;
			for(String name : excludedGroupNames){
				if(p.getLeft().equalsIgnoreCase(name)){
					skip = true;
					break;
				}
			}
			if(!skip){
				callList(p.getRight());
			}
		}
	}
	
	protected static void callList(int i) {
		boolean prevBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		GL11.glCallList(i);
		boolean newBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		if(prevBlend && !newBlend) GL11.glEnable(GL11.GL_BLEND);
		if(!prevBlend && newBlend) GL11.glDisable(GL11.GL_BLEND);
	}
}