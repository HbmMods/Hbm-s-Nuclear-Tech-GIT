package com.hbm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.TileMappings;

public class MachineDynConfig {

	public static final Gson gson = new Gson();
	
	public static void initialize() {
		File dir = new File(MainRegistry.configDir.getAbsolutePath() + File.separatorChar + "hbmConfig");
		
		if(!dir.exists()) {
			if(!dir.mkdir()) {
				throw new IllegalStateException("Unable to make recipe directory " + dir.getAbsolutePath());
			}
		}

		//it's a lit of dummy tile entity instances that are only used once in order to make the init work 
		//not exactly a great solution but this little smear of ugliness carries all the good parts on its back so i will allow it
		List<IConfigurableMachine> dummies = new ArrayList();
		TileMappings.configurables.forEach(x -> { try { dummies.add(x.newInstance()); } catch(Exception ex) {} }); // <- lambda comes with a hidden little try/catch block hidden inside, like a kinder surprise egg that is filled with shit
		File file = new File(dir.getAbsolutePath() + File.separatorChar + "hbmMachines.json");
		
		//dummies.forEach(x -> x.initDefaults());
		
		//and now for the good part
		try { // <- useless overarching try/catch to make the reader shut up
			
			if(file.exists()) {
				JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
				
				for(IConfigurableMachine dummy : dummies) {
					
					try {
						JsonElement element = json.get(dummy.getConfigName());
						JsonObject obj = element != null ? element.getAsJsonObject() : new JsonObject();
						
						//defaults usually already exist at this point, if not we can declare them before the actual reading part
						dummy.readIfPresent(obj);
						
					} catch(Exception ex) { } // <- individual try/catch blocks so a single config breaking doesn't affect other machines. we only got a few dozen of these and it only happens once on startup so who the hell cares
				}
			}

			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");
			writer.beginObject();
			
			for(IConfigurableMachine dummy : dummies) {
				
				try {
					writer.name(dummy.getConfigName()).beginObject();
					dummy.writeConfig(writer);
					writer.endObject();
					
				} catch(Exception ex) { } // <- more looped try/catch goodness because i hate myself
			}
			
			writer.endObject();
			writer.close();
			
			//and that was the entire magic, in a mere 50 lines
			
		} catch(Exception ex) { }
	}
}
