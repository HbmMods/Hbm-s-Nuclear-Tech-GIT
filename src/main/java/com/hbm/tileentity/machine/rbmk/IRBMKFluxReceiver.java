package com.hbm.tileentity.machine.rbmk;

public interface IRBMKFluxReceiver {
	
	public enum NType {
		FAST("Fast Neutrons"),
		SLOW("Slow Neutrons"),
		ANY("All Neutrons");	//not to be used for reactor flux calculation, only for the fuel designation
		
		public String localized;
		
		private NType(String loc) {
			this.localized = loc;
		}
	}
	
	public void receiveFlux(NType type, double flux);
}
