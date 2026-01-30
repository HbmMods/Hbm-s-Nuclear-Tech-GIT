package com.hbm.render.loader;

import java.io.IOException;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class HFRModelReloader implements IResourceManagerReloadListener {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		
		for(HFRWavefrontObject obj : HFRWavefrontObject.allModels) {
			try {
				obj.destroy();
				IResource  resource = Minecraft.getMinecraft().getResourceManager().getResource(obj.resource);
				obj.loadObjModel(resource.getInputStream());
				// MainRegistry.logger.info("Reloading OBJ " + obj.resource.getResourcePath());
			} catch(IOException e) { }
		}
		
		for(Entry<HFRWavefrontObjectVBO, HFRWavefrontObject> entry : HFRWavefrontObject.allVBOs.entrySet()) {
			HFRWavefrontObjectVBO vbo = entry.getKey();
			HFRWavefrontObject obj = entry.getValue();
			
			vbo.destroy();
			vbo.load(obj);
			// MainRegistry.logger.info("Reloading VBO " + obj.resource.getResourcePath());
		}
	}
}
