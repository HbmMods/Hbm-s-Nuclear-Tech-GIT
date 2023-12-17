package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.*;

import net.minecraft.world.World;

public abstract class FluidTrait {
	
	public static HashBiMap<String, Class<? extends FluidTrait>> traitNameMap = HashBiMap.create();
	
	static {
		traitNameMap.put("combustible", FT_Combustible.class);		// x
		traitNameMap.put("coolable", FT_Coolable.class);			// x
		traitNameMap.put("corrosive", FT_Corrosive.class);			// x
		traitNameMap.put("flammable", FT_Flammable.class);			// x
		traitNameMap.put("heatable", FT_Heatable.class);			// x
		traitNameMap.put("poison", FT_Poison.class);				// x
		traitNameMap.put("toxin", FT_Toxin.class);					// x
		traitNameMap.put("ventradiation", FT_VentRadiation.class);	// x
		traitNameMap.put("pwrmoderator", FT_PWRModerator.class);	// x

		traitNameMap.put("gaseous", FT_Gaseous.class);
		traitNameMap.put("gaseous_art", FT_Gaseous_ART.class);
		traitNameMap.put("liquid", FT_Liquid.class);
		traitNameMap.put("viscous", FT_Viscous.class);
		traitNameMap.put("plasma", FT_Plasma.class);
		traitNameMap.put("amat", FT_Amat.class);
		traitNameMap.put("leadcontainer", FT_LeadContainer.class);
		traitNameMap.put("delicious", FT_Delicious.class);
		traitNameMap.put("leaded", FT_Leaded.class);
		traitNameMap.put("pheromone", FT_Pheromone.class);
		traitNameMap.put("noid", FT_NoID.class);
		traitNameMap.put("nocontainer", FT_NoContainer.class);
	}

	/** Important information that should always be displayed */
	public void addInfo(List<String> info) { }
	/* General names of simple traits which are displayed when holding shift */
	public void addInfoHidden(List<String> info) { }
	
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount) { }

	public void serializeJSON(JsonWriter writer) throws IOException { }
	public void deserializeJSON(JsonObject obj) { }
}
