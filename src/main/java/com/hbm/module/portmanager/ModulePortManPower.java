package com.hbm.module.portmanager;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TilePort;
import com.hbm.tileentity.TilePort.PortDef;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.energymk2.Nodespace;
import net.minecraft.world.World;

/**
 * Module for managing power ports with a fixed shape.
 * Saves a reference of the PortDef[] instance for defining the shape, so that
 * if the shape changes, the system triggers a rebuild.
 * 
 * @author hbm
 */
public class ModulePortManPower {
	
	protected TileEntityLoadedBase owner;
	protected PortDef[] portReference;
	
	public TilePort[] powerPorts;
	public boolean enabled[];
	public boolean input = true;
	public boolean output = true;
	
	public ModulePortManPower(TileEntityLoadedBase owner) {
		this.owner = owner;
	}
	
	public ModulePortManPower onlyInput() {
		this.input = true;
		this.output = false;
		return this;
	}
	
	public ModulePortManPower onlyOutput() {
		this.input = false;
		this.output = true;
		return this;
	}
	
	public void destroy() {
		if(powerPorts != null) for(TilePort port : powerPorts) port.disableIfPresent(owner.getWorldObj());
	}

	public void update(PortDef[] ports) {
		
		World world = owner.getWorldObj();
		
		// shape has changed, nuke all ports and run init again
		if(portReference != ports) {
			portReference = ports;
			
			if(this.powerPorts != null) for(TilePort port : this.powerPorts) {
				if(port != null) port.disableIfPresent(world);
			}
			
			this.powerPorts = TilePort.manyToMany(owner, ports);
			
			for(TilePort port : this.powerPorts) {
				port.setupType(Nodespace.THE_POWER_PROVIDER);
			}
		}
		
		if(this.powerPorts != null) {
			
			for(TilePort port : this.powerPorts) {
				if(port == null) continue;
				port.update(world);
				// dynamic, there's basically almost no machine that's both
				if(input && owner instanceof IEnergyReceiverMK2) port.checkSubscribe(world);
				if(output && owner instanceof IEnergyProviderMK2) port.checkProvide(world);
			}
		}
	}
}
