package com.hbm.tileentity.machine.oil;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.world.feature.OilSpot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TileEntityMachineFrackingTower extends TileEntityOilDrillBase {

	protected static int maxPower = 5_000_000;
	protected static int consumption = 5000;
	protected static int solutionRequired = 10;
	protected static int delay = 20;
	protected static int oilPerDepsoit = 1000;
	protected static int gasPerDepositMin = 100;
	protected static int gasPerDepositMax = 500;
	protected static double drainChance = 0.02D;
	protected static int oilPerBedrockDepsoit = 100;
	protected static int gasPerBedrockDepositMin = 10;
	protected static int gasPerBedrockDepositMax = 50;
	protected static int destructionRange = 75;

	public TileEntityMachineFrackingTower() {
		super();
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.OIL, 64_000);
		tanks[1] = new FluidTank(Fluids.GAS, 64_000);
		tanks[2] = new FluidTank(Fluids.FRACKSOL, 64_000);
	}

	@Override
	public String getName() {
		return "container.frackingTower";
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
	public int getDrillDepth() {
		return 0;
	}

	@Override
	public boolean canPump() {
		boolean b = this.tanks[2].getFill() >= solutionRequired;
		
		if(!b) {
			this.indicator = 3;
		}
		
		return b;
	}

	@Override
	public boolean canSuckBlock(Block b) {
		return super.canSuckBlock(b) || b == ModBlocks.ore_bedrock_oil;
	}

	@Override
	public void doSuck(int x, int y, int z) {
		super.doSuck(x, y, z);
		
		if(worldObj.getBlock(x, y, z) == ModBlocks.ore_bedrock_oil) {
			onSuck(x, y, z);
		}
	}

	@Override
	public void onSuck(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		
		int oil = 0;
		int gas = 0;

		if(b == ModBlocks.ore_oil) {
			oil = oilPerDepsoit;
			gas = gasPerDepositMin + worldObj.rand.nextInt(gasPerDepositMax - gasPerDepositMin + 1);
			
			if(worldObj.rand.nextDouble() < drainChance) {
				worldObj.setBlock(x, y, z, ModBlocks.ore_oil_empty);
			}
		}
		if(b == ModBlocks.ore_bedrock_oil) {
			oil = oilPerBedrockDepsoit;
			gas = gasPerBedrockDepositMin + worldObj.rand.nextInt(gasPerBedrockDepositMax - gasPerBedrockDepositMin + 1);
		}
		
		this.tanks[0].setFill(this.tanks[0].getFill() + oil);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + gas);
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(tanks[1].getMaxFill());
		
		this.tanks[2].setFill(tanks[2].getFill() - solutionRequired);

		OilSpot.generateOilSpot(worldObj, xCoord, zCoord, destructionRange, 10, false);
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[0], tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[2] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z)
		};
	}

	@Override
	protected void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[2].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	@Override
	public String getConfigName() {
		return "frackingtower";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		maxPower = IConfigurableMachine.grab(obj, "I:powerCap", maxPower);
		consumption = IConfigurableMachine.grab(obj, "I:consumption", consumption);
		solutionRequired = IConfigurableMachine.grab(obj, "I:solutionRequired", solutionRequired);
		delay = IConfigurableMachine.grab(obj, "I:delay", delay);
		oilPerDepsoit = IConfigurableMachine.grab(obj, "I:oilPerDeposit", oilPerDepsoit);
		gasPerDepositMin = IConfigurableMachine.grab(obj, "I:gasPerDepositMin", gasPerDepositMin);
		gasPerDepositMax = IConfigurableMachine.grab(obj, "I:gasPerDepositMax", gasPerDepositMax);
		drainChance = IConfigurableMachine.grab(obj, "D:drainChance", drainChance);
		oilPerBedrockDepsoit = IConfigurableMachine.grab(obj, "I:oilPerBedrockDeposit", oilPerBedrockDepsoit);
		gasPerBedrockDepositMin = IConfigurableMachine.grab(obj, "I:gasPerBedrockDepositMin", gasPerBedrockDepositMin);
		gasPerBedrockDepositMax = IConfigurableMachine.grab(obj, "I:gasPerBedrockDepositMax", gasPerBedrockDepositMax);
		destructionRange = IConfigurableMachine.grab(obj, "I:destructionRange", destructionRange);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:powerCap").value(maxPower);
		writer.name("I:consumption").value(consumption);
		writer.name("I:solutionRequired").value(solutionRequired);
		writer.name("I:delay").value(delay);
		writer.name("I:oilPerDeposit").value(oilPerDepsoit);
		writer.name("I:gasPerDepositMin").value(gasPerDepositMin);
		writer.name("I:gasPerDepositMax").value(gasPerDepositMax);
		writer.name("D:drainChance").value(drainChance);
		writer.name("I:oilPerBedrockDeposit").value(oilPerBedrockDepsoit);
		writer.name("I:gasPerBedrockDepositMin").value(gasPerBedrockDepositMin);
		writer.name("I:gasPerBedrockDepositMax").value(gasPerBedrockDepositMax);
		writer.name("I:destructionRange").value(destructionRange);
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

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_fracking_tower));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 25) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (level * 10) + "%"));
		}
		if(type == UpgradeType.AFTERBURN) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_BURN, level * 10, level * 50));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}
}
