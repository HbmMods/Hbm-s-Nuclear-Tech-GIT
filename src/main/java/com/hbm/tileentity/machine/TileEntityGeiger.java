package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.CompatHandler;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.ContaminationUtil;

import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityGeiger extends TileEntity implements SimpleComponent, IInfoProviderEC, CompatHandler.OCComponent {

	int timer = 0;
	int ticker = 0;

	@Override
	public void updateEntity() {

		timer++;

		if(timer == 10) {
			timer = 0;
			ticker = check();
		}

		if(timer % 5 == 0) {
			if(ticker > 0) {
				List<Integer> list = new ArrayList<Integer>();

				if(ticker < 1)
					list.add(0);
				if(ticker < 5)
					list.add(0);
				if(ticker < 10)
					list.add(1);
				if(ticker > 5 && ticker < 15)
					list.add(2);
				if(ticker > 10 && ticker < 20)
					list.add(3);
				if(ticker > 15 && ticker < 25)
					list.add(4);
				if(ticker > 20 && ticker < 30)
					list.add(5);
				if(ticker > 25)
					list.add(6);

				int r = list.get(worldObj.rand.nextInt(list.size()));

				if(r > 0)
		        	worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:item.geiger" + r, 1.0F, 1.0F);
			} else if(worldObj.rand.nextInt(50) == 0) {
				worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:item.geiger"+ (1 + worldObj.rand.nextInt(1)), 1.0F, 1.0F);
			}
		}

	}

	public int check() {
		int rads = (int)Math.ceil(ChunkRadiationManager.proxy.getRadiation(worldObj, xCoord, yCoord, zCoord));
		return rads;
	}
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_geiger";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getRads(Context context, Arguments args) {
		return new Object[] {check()};
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		int rads = check();
		String chunkPrefix = ContaminationUtil.getPreffixFromRad(rads);
		data.setString(CompatEnergyControl.S_CHUNKRAD, chunkPrefix + rads + " RAD/s");

	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
