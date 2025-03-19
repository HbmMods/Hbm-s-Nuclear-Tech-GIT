package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerChemfac;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIChemfac;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineChemfac extends TileEntityMachineChemplantBase implements IUpgradeInfoProvider, IFluidCopiable {

	float rotSpeed;
	public float rot;
	public float prevRot;

	public FluidTank water;
	public FluidTank steam;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineChemfac() {
		super(77);

		water = new FluidTank(Fluids.WATER, 64_000);
		steam = new FluidTank(Fluids.SPENTSTEAM, 64_000);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);

		if(stack != null && i >= 1 && i <= 4 && stack.getItem() instanceof ItemMachineUpgrade) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(!worldObj.isRemote) {

			if(worldObj.getTotalWorldTime() % 60 == 0) {

				for(DirPos pos : getConPos()) {
					this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

					for(FluidTank tank : inTanks()) {
						if(tank.getTankType() != Fluids.NONE) {
							this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
						}
					}
				}
			}

			for(DirPos pos : getConPos()) for(FluidTank tank : outTanks()) {
				if(tank.getTankType() != Fluids.NONE && tank.getFill() > 0) {
					this.tryProvide(tank, worldObj, pos);
				}
			}

			this.speed = 100;
			this.consumption = 100;

			upgradeManager.checkSlots(this, slots, 1, 4);

			int speedLevel = upgradeManager.getLevel(UpgradeType.SPEED);
			int powerLevel = upgradeManager.getLevel(UpgradeType.POWER);
			int overLevel = upgradeManager.getLevel(UpgradeType.OVERDRIVE);

			this.speed -= speedLevel * 15;
			this.consumption += speedLevel * 300;
			this.speed += powerLevel * 5;
			this.consumption -= powerLevel * 20;
			this.speed /= (overLevel + 1);
			this.consumption *= (overLevel + 1);

			if(this.speed <= 0) {
				this.speed = 1;
			}

			this.networkPackNT(150);
		} else {

			float maxSpeed = 30F;

			if(isProgressing) {

				rotSpeed += 0.1;

				if(rotSpeed > maxSpeed)
					rotSpeed = maxSpeed;

				if(rotSpeed == maxSpeed && this.worldObj.getTotalWorldTime() % 5 == 0) {

					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
					ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
					Random rand = worldObj.rand;

					double x = xCoord + 0.5 - rot.offsetX * 0.5;
					double y = yCoord + 3;
					double z = zCoord + 0.5 - rot.offsetZ * 0.5;

					worldObj.spawnParticle("cloud", x + dir.offsetX * 1.5 + rand.nextGaussian() * 0.15, y, z + dir.offsetZ * 1.5 + rand.nextGaussian() * 0.15, 0.0, 0.15, 0.0);
					worldObj.spawnParticle("cloud", x - dir.offsetX * 0.5 + rand.nextGaussian() * 0.15, y, z - dir.offsetZ * 0.5 + rand.nextGaussian() * 0.15, 0.0, 0.15, 0.0);
				}
			} else {

				rotSpeed -= 0.1;

				if(rotSpeed < 0)
					rotSpeed = 0;
			}

			prevRot = rot;

			rot += rotSpeed;

			if(rot >= 360) {
				rot -= 360;
				prevRot -= 360;
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		for(int i = 0; i < getRecipeCount(); i++) {
			buf.writeInt(progress[i]);
			buf.writeInt(maxProgress[i]);
		}

		buf.writeBoolean(isProgressing);

		for(int i = 0; i < tanks.length; i++) tanks[i].serialize(buf);

		water.serialize(buf);
		steam.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		for(int i = 0; i < getRecipeCount(); i++) {
			progress[i] = buf.readInt();
			maxProgress[i] = buf.readInt();
		}

		isProgressing = buf.readBoolean();

		for(int i = 0; i < tanks.length; i++) tanks[i].deserialize(buf);

		water.deserialize(buf);
		steam.deserialize(buf);
	}

	private int getWaterRequired() {
		return 1000 / this.speed;
	}

	@Override
	protected boolean canProcess(int index) {
		return super.canProcess(index) && this.water.getFill() >= getWaterRequired() && this.steam.getFill() + getWaterRequired() <= this.steam.getMaxFill();
	}


	@Override
	protected void process(int index) {
		super.process(index);
		this.water.setFill(this.water.getFill() - getWaterRequired());
		this.steam.setFill(this.steam.getFill() + getWaterRequired());
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	protected List<DirPos> conPos;

	protected List<DirPos> getConPos() {

		if(conPos != null && !conPos.isEmpty())
			return conPos;

		conPos = new ArrayList();

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = 0; i < 6; i++) {
			conPos.add(new DirPos(xCoord + dir.offsetX * (3 - i) + rot.offsetX * 3, yCoord + 4, zCoord + dir.offsetZ * (3 - i) + rot.offsetZ * 3, Library.POS_Y));
			conPos.add(new DirPos(xCoord + dir.offsetX * (3 - i) - rot.offsetX * 2, yCoord + 4, zCoord + dir.offsetZ * (3 - i) - rot.offsetZ * 2, Library.POS_Y));

			for(int j = 0; j < 2; j++) {
				conPos.add(new DirPos(xCoord + dir.offsetX * (3 - i) + rot.offsetX * 5, yCoord + 1 + j, zCoord + dir.offsetZ * (3 - i) + rot.offsetZ * 5, rot));
				conPos.add(new DirPos(xCoord + dir.offsetX * (3 - i) - rot.offsetX * 4, yCoord + 1 + j, zCoord + dir.offsetZ * (3 - i) - rot.offsetZ * 4, rot.getOpposite()));
			}
		}

		return conPos;
	}

	@Override
	public int getRecipeCount() {
		return 8;
	}

	@Override
	public int getTankCapacity() {
		return 32_000;
	}

	@Override
	public int getTemplateIndex(int index) {
		return 13 + index * 9;
	}

	@Override
	public int[] getSlotIndicesFromIndex(int index) {
		return new int[] {5 + index * 9, 8 + index * 9, 9 + index * 9, 12 + index * 9};
	}

	DirPos[] inpos;
	DirPos[] outpos;

	@Override
	public DirPos[] getInputPositions() {

		if(inpos != null)
			return inpos;

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		inpos = new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4 - rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 1, dir),
				new DirPos(xCoord - dir.offsetX * 5 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 5 + rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 1 + rot.offsetX * 5, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ * 5, rot)
		};

		return inpos;
	}

	@Override
	public DirPos[] getOutputPositions() {

		if(outpos != null)
			return outpos;

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		outpos = new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 5 - rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 5 - rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 1 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 1 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 5, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 5, rot)
		};

		return outpos;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		water.readFromNBT(nbt, "w");
		steam.readFromNBT(nbt, "s");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		water.writeToNBT(nbt, "w");
		steam.writeToNBT(nbt, "s");
	}

	@Override
	public String getName() {
		return "container.machineChemFac";
	}

	@Override
	protected List<FluidTank> inTanks() {

		List<FluidTank> inTanks = super.inTanks();
		inTanks.add(water);

		return inTanks;
	}

	@Override
	protected List<FluidTank> outTanks() {

		List<FluidTank> outTanks = super.outTanks();
		outTanks.add(steam);

		return outTanks;
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 5,
					yCoord + 4,
					zCoord + 5
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerChemfac(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIChemfac(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_chemfac));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 15) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 300) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 30) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (level * 5) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 6);
		upgrades.put(UpgradeType.POWER, 3);
		upgrades.put(UpgradeType.OVERDRIVE, 12);
		return upgrades;
	}

	@Override
	public FluidTank getTankToPaste() {
		return null;
	}
}
