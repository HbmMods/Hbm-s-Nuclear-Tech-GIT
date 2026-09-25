package com.hbm.module.portmanager;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TilePort;
import com.hbm.tileentity.TilePort.PortDef;

import net.minecraft.world.World;

/**
 * Module for managing a fluid port group with a fixed shape.
 * Saves a reference of the PortDef[] instance for defining the shape, so that
 * if the shape changes, the system triggers a rebuild. Automatically adjusts
 * node types depending on the cached tanks' types.
 * 
 * Comes with a handy manual toggle for ports based on the tank references.
 * 
 * @author hbm
 */
public class ModulePortManFluidAdaptive {
	
	protected TileEntityLoadedBase owner;
	protected PortDef[] portReference;
	
	public TilePort[] inPorts;
	public FluidTank[] inTanks;
	public boolean inEnabled[];
	public TilePort[] outPorts;
	public FluidTank[] outTanks;
	public boolean outEnabled[];
	
	public ModulePortManFluidAdaptive(TileEntityLoadedBase owner) {
		this.owner = owner;
	}
	
	public ModulePortManFluidAdaptive setInputTanks(FluidTank... tanks) {
		inPorts = new TilePort[tanks.length];
		inTanks = tanks;
		inEnabled = new boolean[tanks.length];
		for(int i = 0; i < inEnabled.length; i++) inEnabled[i] = true;
		return this;
	}
	
	public void toggleInput(FluidTank input, boolean enabled) {
		for(int i = 0; i < inEnabled.length; i++) {
			if(inTanks[i] == input) {
				inEnabled[i] = enabled;
				return;
			}
		}
	}
	
	public ModulePortManFluidAdaptive setOutputTanks(FluidTank... tanks) {
		outPorts = new TilePort[tanks.length];
		outTanks = tanks;
		outEnabled = new boolean[tanks.length];
		for(int i = 0; i < outEnabled.length; i++) outEnabled[i] = true;
		return this;
	}
	
	public void toggleOutput(FluidTank output, boolean enabled) {
		for(int i = 0; i < outEnabled.length; i++) {
			if(outTanks[i] == output) {
				outEnabled[i] = enabled;
				return;
			}
		}
	}

	public void update(PortDef[] ports) {
		
		World world = owner.getWorldObj();
		
		// shape has changed, nuke all ports and run init again
		if(portReference != ports) {
			portReference = ports;
			
			PortDef unified = PortDef.combine(ports);
			if(inPorts != null) {
				for(int i = 0; i < inPorts.length; i++) {
					TilePort port = inPorts[i];
					if(port != null) port.disableIfPresent(owner.getWorldObj());
					inPorts[i] = new TilePort().setupOwner(owner).setupType(inTanks[i].getTankType().getNetworkProvider())
							.setupPositions(unified.portPositions).setupConnections(unified.portConnections);
				}
			}
			if(outPorts != null) {
				for(int i = 0; i < outPorts.length; i++) {
					TilePort port = outPorts[i];
					if(port != null) port.disableIfPresent(owner.getWorldObj());
					outPorts[i] = new TilePort().setupOwner(owner).setupType(inTanks[i].getTankType().getNetworkProvider())
							.setupPositions(unified.portPositions).setupConnections(unified.portConnections);
				}
			}
		}
		
		if(inPorts != null) {
			for(int i = 0; i < inPorts.length; i++) {
				TilePort port = inPorts[i];
				if(port == null) continue;
				port.setupType(inTanks[i].getTankType().getNetworkProvider());
				if(inEnabled[i]) port.enable(); else port.disable();
				port.update(world);
				port.checkSubscribe(world);
			}
		}
		
		if(outPorts != null) {
			for(int i = 0; i < outPorts.length; i++) {
				TilePort port = outPorts[i];
				if(port == null) continue;
				port.setupType(outTanks[i].getTankType().getNetworkProvider());
				if(outEnabled[i]) port.enable(); else port.disable();
				port.update(world);
				port.checkProvide(world);
			}
		}
	}
}
