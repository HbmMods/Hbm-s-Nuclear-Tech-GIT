package com.hbm.qmaw;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hbm.interfaces.NotableComments;
import com.hbm.main.MainRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.launchwrapper.Launch;

@NotableComments
public class QMAWLoader implements IResourceManagerReloadListener {

	public static final HashSet<File> registeredSexOffenders = new HashSet();
	public static final Gson gson = new Gson();
	public static final JsonParser parser = new JsonParser();
	public static HashMap<String, QuickManualAndWiki> qmaw = new HashMap();

	@Override
	public void onResourceManagerReload(IResourceManager resMan) {
		MainRegistry.logger.info("[QMAW] Reloading manual...");
		init();
		MainRegistry.logger.info("[QMAW] Loaded " + qmaw.size() + " manual entries!");
	}
	
	/** For the like 2 people who might consider making an NTM addon and want to include manual pages */
	public static void registerModFileURL(File file) {
		registeredSexOffenders.add(file);
	}

	/** Searches the asset folder for QMAW format JSON files and adds entries based on that */
	public static void init() {

		//the mod's file, assuming the mod is a file (not the case in a dev env, fuck!)
		//no fucking null check, if this fails then the entire game will sink along with the ship
		registerModFileURL(new File(QMAWLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath())); // i am going to shit myself
		
		qmaw.clear();
		agonyEngine();
		
		//dynamic file discovery over all resource domains inside the fucking jar is fucking hard
	}
	
	/** "digital equivalent to holywater" yielded few results on google, if only i had the answer i would drown this entire class in it */
	public static void agonyEngine() {
		
		for(File modFile : registeredSexOffenders) dissectZip(modFile);
		
		File devEnvManualFolder = new File(Launch.minecraftHome /* TODO: this is null, get a new source, shitass */, "/src/main/resources/assets/manual");
		MainRegistry.logger.info("[QMAW] Exploring " + devEnvManualFolder.getPath());
		if(devEnvManualFolder.exists() && devEnvManualFolder.isDirectory()) dissectManualFolder(devEnvManualFolder);

		ResourcePackRepository repo = Minecraft.getMinecraft().getResourcePackRepository();
		
		for(Object o : repo.getRepositoryEntries()) {
			ResourcePackRepository.Entry entry = (ResourcePackRepository.Entry) o;
			IResourcePack pack = entry.getResourcePack();

			logPackAttempt(pack.getPackName());
			
			if(pack instanceof FileResourcePack) {
				dissectZip(((FileResourcePack) pack).resourcePackFile);
			}
			
			if(pack instanceof FolderResourcePack) {
				dissectFolder(((FolderResourcePack) pack).resourcePackFile);
			}
		}
	}
	
	public static void logPackAttempt(String name) { MainRegistry.logger.info("[QMAW] Desecrating Corpse of " + name); }
	
	public static void dissectZip(File zipFile) {
		
		if(zipFile == null) {
			MainRegistry.logger.info("[QMAW] Pack file does not exist!");
			return;
		}
		
		ZipFile zip = null;
		
		try {
			zip = new ZipFile(zipFile);
			
			while(zip.entries().hasMoreElements()) {
				ZipEntry entry = zip.entries().nextElement();
				String name = entry.getName();
				MainRegistry.logger.info("[QMAW] Found " + name);
				if(name.startsWith("assets/hbm/manual/") && entry.getName().endsWith(".json")) {
					InputStream fileStream = zip.getInputStream(entry);
					InputStreamReader reader = new InputStreamReader(fileStream);
					JsonObject obj = (JsonObject) parser.parse(reader);
					//TBI
					reader.close();
					MainRegistry.logger.info("[QMAW] Found manual " + name);
				}
			}
			
		} catch(Exception ex) {
			MainRegistry.logger.info("Ball explosion " + ex);
		} finally {
			try {
				if(zip != null) zip.close();
			} catch(Exception ex) { }
		}
	}
	
	public static void dissectFolder(File folder) {
		File manualFolder = new File(folder, "/assets/manual");
		if(manualFolder.exists() && manualFolder.isDirectory()) dissectManualFolder(manualFolder);
	}
	
	public static void dissectManualFolder(File folder) {
		for(File file : folder.listFiles()) {
			MainRegistry.logger.info("[QMAW] Found " + file.getName());
		}
	}
}
