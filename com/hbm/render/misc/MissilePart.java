package com.hbm.render.misc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class MissilePart {
	
	public static List<MissilePart> parts = new ArrayList();

	public Item part;
	public PartType type;
	public double height;
	public IModelCustom model;
	public ResourceLocation texture;
	
	private MissilePart(Item item, PartType type, double height, IModelCustom model, ResourceLocation texture) {
		this.part = item;
		this.type = type;
		this.height = height;
		this.model = model;
		this.texture = texture;
	}
	
	public enum PartType {
		WARHEAD,
		FUSELAGE,
		FINS,
		THRUSTER
	}
	
	public static void registerPart(Item item, PartType type, double height, IModelCustom model, ResourceLocation texture) {
		
		MissilePart part = new MissilePart(item, type, height, model, texture);
		parts.add(part);
	}
	
	public static MissilePart getPart(Item item) {
		
		for(MissilePart part : parts) {
			
			if(part.part == item)
				return part;
		}
		
		return null;
	}

}
