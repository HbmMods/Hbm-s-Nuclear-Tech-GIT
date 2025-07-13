package api.hbm.fluidmk2;

/*

It's rather shrimple: the shiny new energy system using universal nodespace, but hit with a hammer until it works with fluids.
Has a few extra bits and pieces for handling, but the concept is basically the same.
Sounds good?

*/

/*

Quick explanation for implementing new fluids via addon:
Fluids are subject to /ntmreload so they get wiped and rebuilt using the init function in Fluids, which means that if fluids
are simply added externally during startup, they are removed permanently until the game restarts. Same concept as with recipes, really.
To fix this we need to make sure that externally registered fluids are re-registered during reload, for that purpose we have
IFluidRegisterListener, a simple interface with a small method that runs whenever the fluid list is reloaded. IFluidRegisterListeners
need to be registered with CompatExternal.registerFluidRegisterListener to be used, make sure to do this during PreInit.
Inside the IFluidRegisterListener, fluids can be added using CompatFluidRegistry.registerFluid, which will generate a Fluid instance
using the supplied arguments and automatically register it. Do note that like with custom fluids, fluids need numeric IDs assigned manually.
To prevent collisions with stock fluids when NTM updates, make sure to choose a high starting ID (e.g. 10,000).
The fluid created by registerFluid can have traits added to them, just like how NTM does it with its stock fluids.

*/