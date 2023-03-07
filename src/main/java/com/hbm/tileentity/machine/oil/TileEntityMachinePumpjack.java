package com.hbm.tileentity.machine.oil;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachinePumpjack extends TileEntityOilDrillBase {

	protected static int maxPower = 250_000;
	protected static int consumption = 200;
	protected static int delay = 25;
	protected static int oilPerDepsoit = 750;
	protected static int gasPerDepositMin = 50;
	protected static int gasPerDepositMax = 250;
	protected static double drainChance = 0.025D;
	protected static double DunadrainChance = 0.08D; //essentially, duna is supposed to produce weaker oil than the overworld. laythe on the other hand... 
	protected static int oilPerDunaDepsoit = 250;
	
	public float rot = 0;
	public float prevRot = 0;
	public float speed = 0;

	@Override
	public String getName() {
		return "container.pumpjack";
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
				for(int j = 2; j < 6; j++) {
					ForgeDirection dir = ForgeDirection.getOrientation(j);
					if(worldObj.getBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ).isReplaceable(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ)) {
						worldObj.setBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, ModBlocks.gas_radon_dense);
					}
				}
			}
			
			if("oreAsbestos".equals(name)) {
				for(int j = 2; j < 6; j++) {
					ForgeDirection dir = ForgeDirection.getOrientation(j);
					if(worldObj.getBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ).isReplaceable(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ)) {
						worldObj.setBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, ModBlocks.gas_asbestos);
					}
				}
			}
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {

			this.prevRot = rot;
			
			if(this.indicator == 0) {
				this.rot += speed;
			}
			
			if(this.rot >= 360) {
				this.prevRot -= 360;
				this.rot -= 360;
			}
		}
	}
	
	@Override
	public void sendUpdate() {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("indicator", this.indicator);
		data.setFloat("speed", this.indicator == 0 ? (5F + (2F * this.speedLevel)) + (this.overLevel - 1F) * 10: 0F);
		this.networkPack(data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.indicator = nbt.getInteger("indicator");
		this.speed = nbt.getFloat("speed");
	}

	@Override
	public void onSuck(int x, int y, int z) {
		
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
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		int pX2 = xCoord + rot.offsetX * 2;
		int pZ2 = zCoord + rot.offsetZ * 2;
		int pX4 = xCoord + rot.offsetX * 4;
		int pZ4 = zCoord + rot.offsetZ * 4;
		int oX = Math.abs(dir.offsetX) * 2;
		int oZ = Math.abs(dir.offsetZ) * 2;
		
		fillFluid(pX2 + oX, this.yCoord, pZ2 + oZ, getTact(), type);
		fillFluid(pX2 - oX, this.yCoord, pZ2 - oZ, getTact(), type);
		fillFluid(pX4 + oX, this.yCoord, pZ4 + oZ, getTact(), type);
		fillFluid(pX4 - oX, this.yCoord, pZ4 - oZ, getTact(), type);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 7,
					yCoord,
					zCoord - 7,
					xCoord + 8,
					yCoord + 6,
					zCoord + 8
					);
		}
		
		return bb;
	}

	@Override
	public DirPos[] getConPos() {
		this.getBlockMetadata();
		ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
			new DirPos(xCoord + rot.offsetX * 2 + dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2 + dir.offsetZ * 2, dir),
			new DirPos(xCoord + rot.offsetX * 2 + dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 4 - dir.offsetZ * 2, dir.getOpposite()),
			new DirPos(xCoord + rot.offsetX * 4 - dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 4 + dir.offsetZ * 2, dir),
			new DirPos(xCoord + rot.offsetX * 4 - dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2 - dir.offsetZ * 2, dir.getOpposite())
		};
	}

	@Override
	public String getConfigName() {
		return "pumpjack";
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
