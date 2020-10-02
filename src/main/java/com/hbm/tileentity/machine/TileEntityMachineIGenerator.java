package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityMachineIGenerator extends TileEntityMachineBase implements ISource {

	public TileEntityMachineIGenerator() {
		super(15);
	}

	@Override
	public String getName() {
		return "container.iGenerator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
		}
	}

	@Override
	public void ffgeuaInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getSPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSPower(long i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IConsumer> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearList() {
		// TODO Auto-generated method stub
		
	}
}
