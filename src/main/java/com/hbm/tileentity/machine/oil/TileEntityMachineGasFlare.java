package com.hbm.tileentity.machine.oil;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FT_Polluting;
import com.hbm.inventory.fluid.trait.FluidTrait.FluidReleaseType;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.gui.GUIMachineGasFlare;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.I18nUtil;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TileEntityMachineGasFlare extends TileEntityMachineBase implements IEnergyProviderMK2, IFluidStandardReceiver, IControlReceiver, IGUIProvider, IUpgradeInfoProvider, IInfoProviderEC, IFluidCopiable {

	public long power;
	public static final long maxPower = 100000;
	public FluidTank tank;
	public boolean isOn = false;
	public boolean doesBurn = false;
	protected int fluidUsed = 0;
	protected int output = 0;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineGasFlare() {
		super(6);
		tank = new FluidTank(Fluids.GAS, 64000);
	}

	@Override
	public String getName() {
		return "container.gasFlare";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("powerTime");
		tank.readFromNBT(nbt, "gas");
		isOn = nbt.getBoolean("isOn");
		doesBurn = nbt.getBoolean("doesBurn");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "gas");
		nbt.setBoolean("isOn", isOn);
		nbt.setBoolean("doesBurn", doesBurn);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 256;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("valve")) this.isOn = !this.isOn;
		if(data.hasKey("dial")) this.doesBurn = !this.doesBurn;
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.fluidUsed = 0;
			this.output = 0;

			for(DirPos pos : getConPos()) {
				this.tryProvide(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			tank.setType(3, slots);
			tank.loadTank(1, 2, slots);

			int maxVent = 50;
			int maxBurn = 10;

			if(isOn && tank.getFill() > 0) {

				upgradeManager.checkSlots(this, slots, 4, 5);
				int burn = upgradeManager.getLevel(UpgradeType.SPEED);
				int yield = upgradeManager.getLevel(UpgradeType.EFFECT);

				maxVent += maxVent * burn;
				maxBurn += maxBurn * burn;

				if(!doesBurn || !(tank.getTankType().hasTrait(FT_Flammable.class))) {

					if(tank.getTankType().hasTrait(FT_Gaseous.class) || tank.getTankType().hasTrait(FT_Gaseous_ART.class)) {
						int eject = Math.min(maxVent, tank.getFill());
						this.fluidUsed = eject;
						tank.setFill(tank.getFill() - eject);
						tank.getTankType().onFluidRelease(this, tank, eject);

						if(worldObj.getTotalWorldTime() % 7 == 0)
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "random.fizz", getVolume(1.5F), 0.5F);

						if(worldObj.getTotalWorldTime() % 5 == 0 && eject > 0) {
							FT_Polluting.pollute(worldObj, xCoord, yCoord, zCoord, tank.getTankType(), FluidReleaseType.SPILL, eject * 5);
						}
					}
				} else {

					if(tank.getTankType().hasTrait(FT_Flammable.class)) {
						int eject = Math.min(maxBurn, tank.getFill());
						this.fluidUsed = eject;
						tank.setFill(tank.getFill() - eject);

						int penalty = 5;
						if(!tank.getTankType().hasTrait(FT_Gaseous.class) && !tank.getTankType().hasTrait(FT_Gaseous_ART.class))
							penalty = 10;

						long powerProd = tank.getTankType().getTrait(FT_Flammable.class).getHeatEnergy() * eject / 1_000; // divided by 1000 per mB
						powerProd /= penalty;
						powerProd += powerProd * yield / 3;

						this.output = (int) powerProd;
						power += powerProd;

						if(power > maxPower)
							power = maxPower;

						ParticleUtil.spawnGasFlame(worldObj, this.xCoord + 0.5F, this.yCoord + 11.75F, this.zCoord + 0.5F, worldObj.rand.nextGaussian() * 0.15, 0.2, worldObj.rand.nextGaussian() * 0.15);

						List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord + 12, zCoord - 2, xCoord + 2, yCoord + 17, zCoord + 2));
						for(Entity e : list) {
							e.setFire(5);
							e.attackEntityFrom(DamageSource.onFire, 5F);
						}

						if(worldObj.getTotalWorldTime() % 3 == 0)
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "hbm:weapon.flamethrowerShoot", getVolume(1.5F), 0.75F);

						if(worldObj.getTotalWorldTime() % 5 == 0 && eject > 0) {
							FT_Polluting.pollute(worldObj, xCoord, yCoord, zCoord, tank.getTankType(), FluidReleaseType.BURN, eject * 5);
						}
					}
				}
			}

			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);

			this.networkPackNT(50);

		} else {

			if(isOn && tank.getFill() > 0) {

				if((!doesBurn || !(tank.getTankType().hasTrait(FT_Flammable.class))) && (tank.getTankType().hasTrait(FT_Gaseous.class) || tank.getTankType().hasTrait(FT_Gaseous_ART.class))) {

					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "tower");
					data.setFloat("lift", 1F);
					data.setFloat("base", 0.25F);
					data.setFloat("max", 3F);
					data.setInteger("life", 150 + worldObj.rand.nextInt(20));
					data.setInteger("color", tank.getTankType().getColor());

					data.setDouble("posX", xCoord + 0.5);
					data.setDouble("posZ", zCoord + 0.5);
					data.setDouble("posY", yCoord + 11);

					MainRegistry.proxy.effectNT(data);

				}

				if(doesBurn && tank.getTankType().hasTrait(FT_Flammable.class) && MainRegistry.proxy.me().getDistanceSq(xCoord, yCoord + 10, zCoord) <= 1024) {

					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "smoke");
					data.setBoolean("noclip", true);
					data.setInteger("overrideAge", 50);

					if(worldObj.getTotalWorldTime() % 2 == 0) {
						data.setDouble("posX", xCoord + 1.5);
						data.setDouble("posZ", zCoord + 1.5);
						data.setDouble("posY", yCoord + 10.75);
					} else {
						data.setDouble("posX", xCoord + 1.125);
						data.setDouble("posZ", zCoord - 0.5);
						data.setDouble("posY", yCoord + 11.75);
					}

					MainRegistry.proxy.effectNT(data);
				}
			}
		}
	}

	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(this.power);
		buf.writeBoolean(this.isOn);
		buf.writeBoolean(this.doesBurn);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.isOn = buf.readBoolean();
		this.doesBurn = buf.readBoolean();
		tank.deserialize(buf);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineGasFlare(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineGasFlare(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.EFFECT;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_flare));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.EFFECT) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_EFFICIENCY, "+" + (100 * level / 3) + "%"));
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.EFFECT, 3);
		return upgrades;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.fluidUsed > 0);
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, this.fluidUsed);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, this.output);
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray("fluidID", new int[]{tank.getTankType().getID()});
		tag.setBoolean("isOn", isOn);
		tag.setBoolean("doesBurn", doesBurn);
		return tag;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		int id = nbt.getIntArray("fluidID")[index];
		tank.setTankType(Fluids.fromID(id));
		if(nbt.hasKey("isOn")) isOn = nbt.getBoolean("isOn");
		if(nbt.hasKey("doesBurn")) doesBurn = nbt.getBoolean("doesBurn");
	}
}
