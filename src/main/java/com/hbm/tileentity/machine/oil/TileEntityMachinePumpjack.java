package com.hbm.tileentity.machine.oil;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
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
import net.minecraft.util.EnumChatFormatting;
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
		super.networkUnpack(nbt);
		
		this.speed = nbt.getFloat("speed");
	}

	@Override
	public void onSuck(int x, int y, int z) {
		
		this.tanks[0].setFill(this.tanks[0].getFill() + oilPerDepsoit);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + (gasPerDepositMin + worldObj.rand.nextInt((gasPerDepositMax - gasPerDepositMin + 1))));
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(tanks[1].getMaxFill());
		
		if(worldObj.rand.nextDouble() < drainChance) {
			worldObj.setBlock(x, y, z, ModBlocks.ore_oil_empty);
		}
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

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_pumpjack));
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
