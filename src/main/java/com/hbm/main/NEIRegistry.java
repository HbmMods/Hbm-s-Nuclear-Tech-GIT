package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.nei.AlloyFurnaceRecipeHandler;
import com.hbm.handler.nei.AnvilRecipeHandler;
import com.hbm.handler.nei.ArcWelderHandler;
import com.hbm.handler.nei.AshpitHandler;
import com.hbm.handler.nei.AssemblerRecipeHandler;
import com.hbm.handler.nei.BoilerRecipeHandler;
import com.hbm.handler.nei.BoilingHandler;
import com.hbm.handler.nei.BookRecipeHandler;
import com.hbm.handler.nei.BreederRecipeHandler;
import com.hbm.handler.nei.CentrifugeRecipeHandler;
import com.hbm.handler.nei.ChemplantRecipeHandler;
import com.hbm.handler.nei.CokingHandler;
import com.hbm.handler.nei.CombinationHandler;
import com.hbm.handler.nei.ConstructionHandler;
import com.hbm.handler.nei.CrackingHandler;
import com.hbm.handler.nei.CrucibleAlloyingHandler;
import com.hbm.handler.nei.CrucibleCastingHandler;
import com.hbm.handler.nei.CrucibleSmeltingHandler;
import com.hbm.handler.nei.CrystallizerRecipeHandler;
import com.hbm.handler.nei.CustomMachineHandler;
import com.hbm.handler.nei.CyclotronRecipeHandler;
import com.hbm.handler.nei.ElectrolyserFluidHandler;
import com.hbm.handler.nei.ElectrolyserMetalHandler;
import com.hbm.handler.nei.ExposureChamberHandler;
import com.hbm.handler.nei.FluidRecipeHandler;
import com.hbm.handler.nei.FractioningHandler;
import com.hbm.handler.nei.FuelPoolHandler;
import com.hbm.handler.nei.FusionRecipeHandler;
import com.hbm.handler.nei.GasCentrifugeRecipeHandler;
import com.hbm.handler.nei.HadronRecipeHandler;
import com.hbm.handler.nei.HydrotreatingHandler;
import com.hbm.handler.nei.LiquefactionHandler;
import com.hbm.handler.nei.MixerHandler;
import com.hbm.handler.nei.OutgasserHandler;
import com.hbm.handler.nei.PressRecipeHandler;
import com.hbm.handler.nei.RTGRecipeHandler;
import com.hbm.handler.nei.RadiolysisRecipeHandler;
import com.hbm.handler.nei.RefineryRecipeHandler;
import com.hbm.handler.nei.ReformingHandler;
import com.hbm.handler.nei.SILEXRecipeHandler;
import com.hbm.handler.nei.SawmillHandler;
import com.hbm.handler.nei.ShredderRecipeHandler;
import com.hbm.handler.nei.SmithingRecipeHandler;
import com.hbm.handler.nei.SolidificationHandler;
import com.hbm.handler.nei.ToolingHandler;
import com.hbm.handler.nei.VacuumRecipeHandler;
import com.hbm.handler.nei.ZirnoxRecipeHandler;

import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEIRegistry {

	public static List<TemplateRecipeHandler> handlers = new ArrayList();
	
	public static List<TemplateRecipeHandler> listAllHandlers() {
		
		if(!handlers.isEmpty()) return handlers;

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
		handlers.add(new ReformingHandler());
		handlers.add(new HydrotreatingHandler());
		handlers.add(new BoilerRecipeHandler());
		handlers.add(new ChemplantRecipeHandler());
		handlers.add(new CrystallizerRecipeHandler());
		handlers.add(new BookRecipeHandler());
		handlers.add(new FusionRecipeHandler());
		handlers.add(new HadronRecipeHandler());
		handlers.add(new SILEXRecipeHandler());
		handlers.add(new SmithingRecipeHandler());
		handlers.add(new AnvilRecipeHandler());
		handlers.add(new FuelPoolHandler());
		handlers.add(new FluidRecipeHandler());
		handlers.add(new RadiolysisRecipeHandler());
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
		handlers.add(new ExposureChamberHandler());

		for(CustomMachineConfigJSON.MachineConfiguration conf : CustomMachineConfigJSON.niceList) handlers.add(new CustomMachineHandler(conf));
		
		return handlers;
	}
}
