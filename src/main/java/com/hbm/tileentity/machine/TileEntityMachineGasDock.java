package com.hbm.tileentity.machine;

import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.inventory.container.ContainerSatDock;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUISatDock;
import com.hbm.itempool.ItemPool;
import com.hbm.items.ISatChip;
import com.hbm.lib.Library;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteMiner;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMachineGasDock extends TileEntityMachineBase implements IFluidStandardTransceiver {


    private String customName;
	public FluidTank[] tanks;
	
	public boolean hasRocket = true;
	public int launchTicks = 0;
	
    private AxisAlignedBB renderBoundingBox;

    public TileEntityMachineGasDock() {
    	super(3);
    	tanks[0].setTankType(Fluids.JOOLGAS);
    	tanks[1].setTankType(Fluids.OXYGEN);
    	tanks[2].setTankType(Fluids.HYDROGEN);

    }


    public void setCustomName(String name) {
        this.customName = name;
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		tanks[0].readFromNBT(nbt, "gas");
		tanks[1].readFromNBT(nbt, "f1");
		tanks[2].readFromNBT(nbt, "f2");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		tanks[0].writeToNBT(nbt, "gas");
		tanks[1].writeToNBT(nbt, "f1");
		tanks[2].writeToNBT(nbt, "f2");
	}


    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
    		launchTicks = MathHelper.clamp_int(launchTicks + (hasRocket ? -1 : 1), hasRocket ? -20 : 0, 100);

    		if(worldObj.isRemote && launchTicks > 0 && launchTicks < 100) {
    			ParticleUtil.spawnGasFlame(worldObj, xCoord + 0.5, yCoord + 0.5 + launchTicks, zCoord + 0.5, 0.0, -1.0, 0.0);

    			if(launchTicks < 10) {
    				ExplosionLarge.spawnShock(worldObj, xCoord + 0.5, yCoord, zCoord + 0.5, 1 + worldObj.rand.nextInt(3), 1 + worldObj.rand.nextGaussian());
    			}
    		}
        }
    }


	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			for(int i = 0; i < tanks.length; i++) {
				if(tanks[i].getTankType() != Fluids.NONE) {
					trySubscribe(tanks[i].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.sendFluid(tanks[i], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

				}
			}
		}
	}

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (renderBoundingBox == null) {
            renderBoundingBox = AxisAlignedBB.getBoundingBox(
                    xCoord - 1,
                    yCoord,
                    zCoord - 1,
                    xCoord + 2,
                    yCoord + 1,
                    zCoord + 2
            );
        }

        return renderBoundingBox;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[0]};	
		}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[1], tanks[2]};
	}
	
	
	private DirPos[] conPos;

	
	protected DirPos[] getConPos() {
		if(conPos == null) {
			List<DirPos> list = new ArrayList<>();

			// Below
			for(int x = -1; x <= 1; x++) {
				for(int z = -1; z <= 1; z++) {
					list.add(new DirPos(xCoord + x, yCoord - 1, zCoord + z, Library.NEG_Y));
				}
			}

			// Sides
			for(int i = -1; i <= 1; i++) {
				list.add(new DirPos(xCoord + i, yCoord, zCoord + 2, Library.POS_Z));
				list.add(new DirPos(xCoord + i, yCoord, zCoord - 2, Library.NEG_Z));
				list.add(new DirPos(xCoord + 2, yCoord, zCoord + i, Library.POS_X));
				list.add(new DirPos(xCoord - 2, yCoord, zCoord + i, Library.NEG_X));
			}

			conPos = list.toArray(new DirPos[0]);
		}
		
		return conPos;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
