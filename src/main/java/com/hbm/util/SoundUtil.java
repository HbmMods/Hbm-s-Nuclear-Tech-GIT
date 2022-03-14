package com.hbm.util;

import java.util.Map;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.util.EnumHelper;

public class SoundUtil {

	public static void addSoundCategory(String name) {
		
		try {
			SoundCategory category = EnumHelper.addEnum(SoundCategory.class, name.toUpperCase(), new Class[] { String.class, int.class }, new Object[] { name, SoundCategory.values().length });

			Map nameMapping = (Map) ReflectionHelper.findField(SoundCategory.class, "field_147168_j").get(null);
			Map idMapping = (Map) ReflectionHelper.findField(SoundCategory.class, "field_147169_k").get(null);
			
			Map mapSoundLevelsOrig = (Map) ReflectionHelper.findField(GameSettings.class, "mapSoundLevels", "field_151446_aD").get(Minecraft.getMinecraft().gameSettings);
			Map mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
			
			nameMapping.put(category.getCategoryName(), category);
			idMapping.put(Integer.valueOf(category.getCategoryId()), category);
			
			//we have to copy the new map before putting it in the settings because otherwise the EnumMap fucking dies
			for(Object o : mapSoundLevelsOrig.keySet()) {
				mapSoundLevels.put(o, mapSoundLevelsOrig.get(o));
			}
			
			mapSoundLevels.put(category, 1F);

			//ReflectionHelper.setPrivateValue(SoundCategory.class, null, nameMapping, "field_147168_j");
			//ReflectionHelper.setPrivateValue(SoundCategory.class, null, idMapping, "field_147169_k");
			ReflectionHelper.setPrivateValue(GameSettings.class, Minecraft.getMinecraft().gameSettings, mapSoundLevels, "mapSoundLevels", "field_151446_aD");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
