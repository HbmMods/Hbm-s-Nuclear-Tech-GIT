package com.hbm.main;

import codechicken.nei.recipe.TemplateRecipeHandler;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.nei.*;

import java.util.ArrayList;
import java.util.List;

public class NEIRegistry {

	public static List<TemplateRecipeHandler> handlers = new ArrayList();

	public static List<TemplateRecipeHandler> listAllHandlers() {

		if(!handlers.isEmpty()) return handlers;

		handlers.add(new AnvilRecipeHandler());
		handlers.add(new SmithingRecipeHandler());
		handlers.add(new AlloyFurnaceRecipeHandler());
		handlers.add(new ShredderRecipeHandler());
		handlers.add(new PressRecipeHandler());
		handlers.add(new CentrifugeRecipeHandler());
		handlers.add(new GasCentrifugeRecipeHandler());
		handlers.add(new BreederRecipeHandler());
		handlers.add(new CyclotronRecipeHandler());
		handlers.add(new AssemblerRecipeHandler());
		handlers.add(new RefineryRecipeHandler());
		handlers.add(new VacuumRecipeHandler());
		handlers.add(new CrackingHandler());
		handlers.add(new RadiolysisRecipeHandler());
		handlers.add(new ReformingHandler());
		handlers.add(new HydrotreatingHandler());
		handlers.add(new ChemplantRecipeHandler());
		handlers.add(new OreSlopperHandler()); //before acidizing
		handlers.add(new CrystallizerRecipeHandler());
		handlers.add(new BookRecipeHandler());
		handlers.add(new FusionRecipeHandler());
		handlers.add(new HadronRecipeHandler());
		handlers.add(new SILEXRecipeHandler());
		handlers.add(new FuelPoolHandler());
		handlers.add(new CrucibleSmeltingHandler());
		handlers.add(new CrucibleAlloyingHandler());
		handlers.add(new CrucibleCastingHandler());
		handlers.add(new ToolingHandler());
		handlers.add(new ConstructionHandler());

		//universal boyes
		handlers.add(new ZirnoxRecipeHandler());
		if(VersatileConfig.rtgDecay()) handlers.add(new RTGRecipeHandler());
		handlers.add(new LiquefactionHandler());
		handlers.add(new SolidificationHandler());
		handlers.add(new CokingHandler());
		handlers.add(new PyroHandler());
		handlers.add(new FractioningHandler());
		handlers.add(new BoilingHandler());
		handlers.add(new CombinationHandler());
		handlers.add(new SawmillHandler());
		handlers.add(new MixerHandler());
		handlers.add(new OutgasserHandler());
		handlers.add(new ElectrolyserFluidHandler());
		handlers.add(new ElectrolyserMetalHandler());
		handlers.add(new AshpitHandler());
		handlers.add(new ArcWelderHandler());
		handlers.add(new SolderingStationHandler());
		handlers.add(new ExposureChamberHandler());
		handlers.add(new ArcFurnaceSolidHandler());
		handlers.add(new ArcFurnaceFluidHandler());
		handlers.add(new RotaryFurnaceHandler());
		handlers.add(new AmmoPressHandler());
		handlers.add(new CompressorHandler());
		handlers.add(new ParticleAcceleratorHandler());

		//this shit comes last
		handlers.add(new FluidRecipeHandler());

		return handlers;
	}
}
