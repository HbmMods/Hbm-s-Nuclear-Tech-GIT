package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerCompressor;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICompressor;
import com.hbm.inventory.recipes.CompressorRecipes;
import com.hbm.inventory.recipes.CompressorRecipes.CompressorRecipe;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.*;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCompressor extends TileEntityMachineBase implements IGUIProvider, IControlReceiver, IEnergyReceiverMK2, IFluidStandardTransceiver, IUpgradeInfoProvider, IFluidCopiable {

	public FluidTank[] tanks;
	public long power;
	public static final long maxPower = 100_000;
	public boolean isOn;
	public int progress;
	public int processTime = 100;
	public static final int processTimeBase = 100;
	public int powerRequirement;
	public static final int powerRequirementBase = 2_500;

	public float fanSpin;
	public float prevFanSpin;
	public float piston;
	public float prevPiston;
	public boolean pistonDir;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineCompressor() {
		super(4);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.NONE, 16_000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 16_000).withPressure(1);
	}

	@Override
	public String getName() {
		return "container.machineCompressor";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				this.updateConnections();
			}

			this.power = Library.chargeTEFromItems(slots, 1, power, maxPower);
			this.tanks[0].setType(0, slots);
			this.setupTanks();

			upgradeManager.checkSlots(this, slots, 1, 3);

			int speedLevel = upgradeManager.getLevel(UpgradeType.SPEED);
			int powerLevel = upgradeManager.getLevel(UpgradeType.POWER);
			int overLevel = upgradeManager.getLevel(UpgradeType.OVERDRIVE);

			CompressorRecipe rec = CompressorRecipes.recipes.get(new Pair(tanks[0].getTankType(), tanks[0].getPressure()));
			int timeBase = this.processTimeBase;
			if(rec != null) timeBase = rec.duration;

			//there is a reason to do this but i'm not telling you
			// ^ a few months later i have to wonder what the fuck this guy was on about, and if i ever see him i will punch him in the nuts
			if(rec == null) this.processTime = speedLevel == 3 ? 10 : speedLevel == 2 ? 20 : speedLevel == 1 ? 60 : timeBase;
			else this.processTime = timeBase / (speedLevel + 1);
			this.powerRequirement = this.powerRequirementBase / (powerLevel + 1);
			this.processTime = this.processTime / (overLevel + 1);
			this.powerRequirement = this.powerRequirement * ((overLevel * 2) + 1);

			if(processTime <= 0) processTime = 1;

			if(canProcess()) {
				this.progress++;
				this.isOn = true;
				this.power -= powerRequirement;

				if(progress >= this.processTime) {
					progress = 0;
					this.process();
					this.markChanged();
				}

			} else {
				this.progress = 0;
				this.isOn = false;
			}

			for(DirPos pos : getConPos()) {
				this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			this.networkPackNT(100);

		} else {

			this.prevFanSpin = this.fanSpin;
			this.prevPiston = this.piston;

			if(this.isOn) {
				this.fanSpin += 15;

				if(this.fanSpin >= 360) {
					this.prevFanSpin -= 360;
					this.fanSpin -= 360;
				}

				if(this.pistonDir) {
					this.piston -= randSpeed;
					if(this.piston <= 0) {
						MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:item.boltgun", this.getVolume(0.5F), 0.75F);
						this.pistonDir = !this.pistonDir;
					}
				} else {
					this.piston += 0.05F;
					if(this.piston >= 1) {
						this.randSpeed = 0.085F + worldObj.rand.nextFloat() * 0.03F;
						this.pistonDir = !this.pistonDir;
					}
				}

				this.piston = MathHelper.clamp_float(this.piston, 0F, 1F);
			}
		}
	}

	private float randSpeed = 0.1F;

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.progress);
		buf.writeInt(this.processTime);
		buf.writeInt(this.powerRequirement);
		buf.writeLong(this.power);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		buf.writeBoolean(this.isOn);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.progress = buf.readInt();
		this.processTime = buf.readInt();
		this.powerRequirement = buf.readInt();
		this.power = buf.readLong();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		this.isOn = buf.readBoolean();
	}

	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord - rot.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
		};
	}

	public boolean canProcess() {

		if(this.power <= powerRequirement) return false;

		CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair(tanks[0].getTankType(), tanks[0].getPressure()));

		if(recipe == null) {
			return tanks[0].getFill() >= 1000 && tanks[1].getFill() + 1000 <= tanks[1].getMaxFill();
		}

		return tanks[0].getFill() > recipe.inputAmount && tanks[1].getFill() + recipe.output.fill <= tanks[1].getMaxFill();
	}

	public void process() {

		CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair(tanks[0].getTankType(), tanks[0].getPressure()));

		if(recipe == null) {
			tanks[0].setFill(tanks[0].getFill() - 1_000);
			tanks[1].setFill(tanks[1].getFill() + 1_000);
		} else {
			tanks[0].setFill(tanks[0].getFill() - recipe.inputAmount);
			tanks[1].setFill(tanks[1].getFill() + recipe.output.fill);
		}
	}

	protected void setupTanks() {

		CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair(tanks[0].getTankType(), tanks[0].getPressure()));

		if(recipe == null) {
			tanks[1].withPressure(tanks[0].getPressure() + 1).setTankType(tanks[0].getTankType());
		} else {
			tanks[1].withPressure(recipe.output.pressure).setTankType(recipe.output.type);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		progress = nbt.getInteger("progress");
		tanks[0].readFromNBT(nbt, "0");
		tanks[1].readFromNBT(nbt, "1");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("progress", progress);
		tanks[0].writeToNBT(nbt, "0");
		tanks[1].writeToNBT(nbt, "1");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCompressor(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICompressor(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		int compression = data.getInteger("compression");

		if(compression != tanks[0].getPressure()) {
			tanks[0].withPressure(compression);

			CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair(tanks[0].getTankType(), compression));

			if(recipe == null) {
				tanks[1].withPressure(compression + 1);
			} else {
				tanks[1].withPressure(recipe.output.pressure).setTankType(recipe.output.type);
			}

			this.markChanged();
		}
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 9,
					zCoord + 3
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_compressor));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + "Generic compression: "+ I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level == 3 ? 90 : level == 2 ? 80 : level == 1 ? 40 : 0) + "%"));
			info.add(EnumChatFormatting.GREEN + "Recipe: "+ I18nUtil.resolveKey(this.KEY_DELAY, "-" + (100 - 100 / (level + 1)) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (100 - 100 / (level + 1)) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.POWER, 3);
		upgrades.put(UpgradeType.OVERDRIVE, 9);
		return upgrades;
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray("fluidID", getFluidIDToCopy());
		tag.setInteger("compression", tanks[0].getPressure());
		return tag;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		if(nbt.hasKey("compression")) {
			int compression = nbt.getInteger("compression");

			if (compression != tanks[0].getPressure()) {
				tanks[0].withPressure(compression);

				CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair(tanks[0].getTankType(), compression));

				if (recipe == null) {
					tanks[1].withPressure(compression + 1);
				} else {
					tanks[1].withPressure(recipe.output.pressure).setTankType(recipe.output.type);
				}

				this.markChanged();
			}
		}
		IFluidCopiable.super.pasteSettings(nbt, index, world, player, x, y, z);
	}
}
