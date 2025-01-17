package com.hbm.qmaw;

import java.util.HashMap;

import com.google.gson.Gson;
import com.hbm.main.MainRegistry;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class QMAWLoader implements IResourceManagerReloadListener {

	public static final Gson gson = new Gson();
	public static HashMap<String, QuickManualAndWiki> qmaw = new HashMap();

	@Override
	public void onResourceManagerReload(IResourceManager resMan) {
		MainRegistry.logger.info("[QMAW] Reloading manual...");
		init();
		MainRegistry.logger.info("[QMAW] Loaded " + qmaw.size() + " manual entries!");
	}

	/** Searches the asset folder for QMAW format JSON files and adds entries based on that */
	public static void init() {
		qmaw.clear();
		
		//dynamic file discovery over all resource domains inside the fucking jar is fucking hard
	}
}
