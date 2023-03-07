package com.hbm.tileentity.machine.oil;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineOilWell extends TileEntityOilDrillBase {

	protected static int maxPower = 100_000;
	protected static int consumption = 100;
	protected static int delay = 50;
	protected static int oilPerDepsoit = 500;
	protected static int oilPerDunaDepsoit = 100;
	protected static int gasPerDepositMin = 100;
	protected static int gasPerDepositMax = 500;
	protected static double drainChance = 0.05D;
	protected static double DunadrainChance = 0.1D;

	@Override
	public String getName() {
		return "container.oilWell";
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public int getPowerReq() {
		return consumption;
	}

	@Override
	public int getDelay() {
		return delay;
	}

	@Override
	public void onDrill(int y) {
		Block b = worldObj.getBlock(xCoord, y, zCoord);
		ItemStack stack = new ItemStack(b);
		int[] ids = OreDictionary.getOreIDs(stack);
		for(Integer i : ids) {
			String name = OreDictionary.getOreName(i);
			
			if("oreUranium".equals(name)) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						if(worldObj.getBlock(xCoord + j, yCoord + 7, zCoord + j).isReplaceable(worldObj, xCoord + j, yCoord + 7, zCoord + k)) {
							worldObj.setBlock(xCoord + k, yCoord + 7, zCoord + k, ModBlocks.gas_radon_dense);
						}
					}
				}
			}
			
			if("oreAsbestos".equals(name)) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						if(worldObj.getBlock(xCoord + j, yCoord + 7, zCoord + j).isReplaceable(worldObj, xCoord + j, yCoord + 7, zCoord + k)) {
							worldObj.setBlock(xCoord + k, yCoord + 7, zCoord + k, ModBlocks.gas_asbestos);
						}
					}
				}
			}
		}
	}

	@Override
    public void onSuck(int x, int y, int z) {

        ExplosionLarge.spawnOilSpills(worldObj, xCoord + 0.5F, yCoord + 5.5F, zCoord + 0.5F, 3);
        worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 2.0F, 0.5F);
        if(worldObj.getBlock(x, y, z) == ModBlocks.duna_oil) {
            this.tanks[0].setFill(this.tanks[0].getFill() + oilPerDunaDepsoit);
            if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(tanks[0].getMaxFill());
        }
        if(worldObj.getBlock(x, y, z) == ModBlocks.ore_oil) {
            this.tanks[0].setFill(this.tanks[0].getFill() + oilPerDepsoit);
            if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(tanks[0].getMaxFill());
            this.tanks[1].setFill(this.tanks[1].getFill() + (gasPerDepositMin + worldObj.rand.nextInt((gasPerDepositMax - gasPerDepositMin + 1))));
            if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(tanks[1].getMaxFill());
           
        }
        if(worldObj.getBlock(x, y, z)== ModBlocks.ore_oil) {
        if(worldObj.rand.nextDouble() < drainChance) {
        	worldObj.setBlock(x, y, z, ModBlocks.ore_oil_empty);
        	}
        }
        
        if(worldObj.getBlock(x, y, z)== ModBlocks.duna_oil) {
        	if(worldObj.rand.nextDouble() < DunadrainChance) {
	    		worldObj.setBlock(x, y, z, ModBlocks.duna_oil_empty);
	    		//return; 
		 }
	}
}
	
	

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 2, getTact(), type);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 7,
					zCoord + 2
					);
		}
		
		return bb;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public String getConfigName() {
		return "derrick";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		maxPower = IConfigurableMachine.grab(obj, "I:powerCap", maxPower);
		consumption = IConfigurableMachine.grab(obj, "I:consumption", consumption);
		delay = IConfigurableMachine.grab(obj, "I:delay", delay);
		oilPerDepsoit = IConfigurableMachine.grab(obj, "I:oilPerDeposit", oilPerDepsoit);
		gasPerDepositMin = IConfigurableMachine.grab(obj, "I:gasPerDepositMin", gasPerDepositMin);
		gasPerDepositMax = IConfigurableMachine.grab(obj, "I:gasPerDepositMax", gasPerDepositMax);
		drainChance = IConfigurableMachine.grab(obj, "D:drainChance", drainChance);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:powerCap").value(maxPower);
		writer.name("I:consumption").value(consumption);
		writer.name("I:delay").value(delay);
		writer.name("I:oilPerDeposit").value(oilPerDepsoit);
		writer.name("I:gasPerDepositMin").value(gasPerDepositMin);
		writer.name("I:gasPerDepositMax").value(gasPerDepositMax);
		writer.name("D:drainChance").value(drainChance);
	}
	
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineOilWell(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineOilWell(player.inventory, this);
	}
}
