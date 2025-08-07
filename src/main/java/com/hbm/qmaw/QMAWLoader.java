package com.hbm.qmaw;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.item.ItemStack;

@NotableComments
public class QMAWLoader implements IResourceManagerReloadListener {

	public static final HashSet<File> registeredModFiles = new HashSet();
	public static final Gson gson = new Gson();
	public static final JsonParser parser = new JsonParser();
	public static HashMap<String, QuickManualAndWiki> qmaw = new HashMap();
	public static HashMap<ComparableStack, QuickManualAndWiki> triggers = new HashMap();

	@Override
	public void onResourceManagerReload(IResourceManager resMan) {
		long timestamp = System.currentTimeMillis();
		MainRegistry.logger.info("[QMAW] Reloading manual...");
		init();
		MainRegistry.logger.info("[QMAW] Loaded " + qmaw.size() + " manual entries! (" + (System.currentTimeMillis() - timestamp) + "ms)");
	}
	
	/** For the like 2 people who might consider making an NTM addon and want to include manual pages */
	public static void registerModFileURL(File file) {
		registeredModFiles.add(file);
	}

	/** Searches the asset folder for QMAW format JSON files and adds entries based on that */
	public static void init() {

		//the mod's file, assuming the mod is a file (not the case in a dev env, fuck!)
		//no fucking null check, if this fails then the entire game will sink along with the ship
		String path = QMAWLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		// exclude .class in the case of a dev env
		if(!path.endsWith(".class")) registerModFileURL(new File(path)); // i am going to shit myself
		
		qmaw.clear();
		triggers.clear();
		agonyEngine();
	}
	
	/** "digital equivalent to holywater" yielded few results on google, if only i had the answer i would drown this entire class in it <br><br>
	 * This affront to god can load QMAW definition files from four different sources:<br>
	 * * Any mod's jar that has registered itself to include QMAW files<br>
	 * * The dev environment, because "the mod file" would in this case be this very class file, and that's incorrect<br>
	 * * ZIP-based resource packs<br>
	 * * Folder-based resource packs
	 * */
	public static void agonyEngine() {
		
		for(File modFile : registeredModFiles) dissectZip(modFile);
		
		File devEnvManualFolder = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath().replace("/eclipse/.", "") + "/src/main/resources/assets/hbm/manual");
		if(devEnvManualFolder.exists() && devEnvManualFolder.isDirectory()) {
			MainRegistry.logger.info("[QMAW] Exploring " + devEnvManualFolder.getAbsolutePath());
			dissectManualFolder(devEnvManualFolder);
		}

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

	public static void logPackAttempt(String name) { MainRegistry.logger.info("[QMAW] Dissecting resource " + name); }
	public static void logFoundManual(String name) { MainRegistry.logger.info("[QMAW] Found manual " + name); }
	
	/** You put your white gloves on, you get your hand in there, and then you iterate OVER THE ENTIRE FUCKING ZIP until we find things we deem usable */
	public static void dissectZip(File zipFile) {
		
		if(zipFile == null) {
			MainRegistry.logger.info("[QMAW] Pack file does not exist!");
			return;
		}
		
		ZipFile zip = null;
		
		try {
			zip = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> enumerator = zip.entries();
			
			while(enumerator.hasMoreElements()) {
				ZipEntry entry = enumerator.nextElement();
				String name = entry.getName();
				if(name.startsWith("assets/hbm/manual/") && name.endsWith(".json")) {
					InputStream fileStream = zip.getInputStream(entry);
					InputStreamReader reader = new InputStreamReader(fileStream);
					try {
						JsonObject obj = (JsonObject) parser.parse(reader);
						String manName = name.replace("assets/hbm/manual/", "");
						registerJson(manName, obj);
						reader.close();
						logFoundManual(manName);
					} catch(Exception ex) {
						MainRegistry.logger.info("[QMAW] Error reading manual " + name + ": " + ex);
					}
				}
			}
			
		} catch(Exception ex) {
			MainRegistry.logger.info("[QMAW] Error dissecting zip " + zipFile.getName() + ": " + ex);
		} finally {
			try {
				if(zip != null) zip.close();
			} catch(Exception ex) { }
		}
	}
	
	/** Opens a resource pack folder, skips to the manual folder, then tries to dissect that */
	public static void dissectFolder(File folder) {
		File manualFolder = new File(folder, "/assets/manual");
		if(manualFolder.exists() && manualFolder.isDirectory()) dissectManualFolder(manualFolder);
	}
	
	/** Anal bleeding */
	public static void dissectManualFolder(File folder) {

		File[] files = folder.listFiles();
		for(File file : files) {
			String name = file.getName();
			if(file.isFile() && name.endsWith(".json")) {
				try {
					FileReader reader = new FileReader(file);
					JsonObject obj = (JsonObject) parser.parse(reader);
					registerJson(name, obj);
					logFoundManual(name);
				} catch(Exception ex) {
					MainRegistry.logger.info("[QMAW] Error reading manual " + name + ": " + ex);
				}
			}
		}
	}
	
	/** Extracts all the info from a json file's main object to add a QMAW to the system. Very barebones, only handles name, icon and the localized text. */
	public static void registerJson(String name, JsonObject json) {
		QuickManualAndWiki qmaw = new QuickManualAndWiki(name);
		
		if(json.has("icon")) {
			qmaw.setIcon(SerializableRecipe.readItemStack(json.get("icon").getAsJsonArray()));
		}
		
		JsonObject title = json.get("title").getAsJsonObject();
		for(Entry<String, JsonElement> part : title.entrySet()) {
			qmaw.addTitle(part.getKey(), part.getValue().getAsString());
		}
		
		JsonObject content = json.get("content").getAsJsonObject();
		for(Entry<String, JsonElement> part : content.entrySet()) {
			qmaw.addLang(part.getKey(), part.getValue().getAsString());
		}
		
		JsonArray triggers = json.get("trigger").getAsJsonArray();
		
		for(JsonElement element : triggers) {
			ItemStack trigger = SerializableRecipe.readItemStack(element.getAsJsonArray());
			// items get renamed and removed all the time, so we add some more debug goodness for those cases
			if(trigger == null || trigger.getItem() == ModItems.nothing) {
				MainRegistry.logger.info("[QMAW] Manual " + name + " references nonexistant trigger " + element.toString());
			} else {
				QMAWLoader.triggers.put(new ComparableStack(trigger).makeSingular(), qmaw);
			}
		}
		
		if(!qmaw.contents.isEmpty()) {
			QMAWLoader.qmaw.put(name, qmaw);
		}
	}
}
