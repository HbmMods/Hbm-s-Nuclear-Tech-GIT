package com.hbm.inventory.recipes;

import java.util.HashMap;

import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;

public class CustomNukeRecipes {
	
	//a bit hacky, i'll probably just straight copy that system bob has yet to make
	public static float getQuantity(String key) {
		if(key.startsWith("nugget")) {
			return 1;
		} else if(key.startsWith("billet")) {
			return 6;
		} else if(key.startsWith("ingot")) {
			return 9;
		} else if(key.startsWith("block")) {
			return 81;
		} else {
			return 0;
		}
	}
	
	/**
	 * Checks if the OreDict key fits the material, but not the form
	 * @param frame DictFrame
	 * @param key Input key
	 */
	private static boolean containsMatch(DictFrame frame, String key) {
		String[] mats = frame.getMaterials();
		
		for(String mat : mats) {
			if(key.contains(mat)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if the OreDict key fits the material and the form thereof.
	 * @param mats Ore names
	 * @param key Input key
	 */
	private static boolean containsMatch(String[] mats, String key) {
		
		for(String mat : mats) {
			if(mat.contains(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static float nonPredetonatingFissile(String key) {
		float multiplier = getQuantity(key);
		if(multiplier == 0) {
			return 0;
		}
		
		if(containsMatch(OreDictManager.U233, key)) {
			return 1.05f * multiplier;
		} else if(containsMatch(OreDictManager.U235, key)) {
			return 1.0f * multiplier;
		} else if(containsMatch(OreDictManager.NP237, key)) {
			return 0.95f * multiplier;
		} else if(containsMatch(OreDictManager.SA326, key)) {
			return 10.0f * multiplier;
		}
		
		return 0;
	}
	
}
