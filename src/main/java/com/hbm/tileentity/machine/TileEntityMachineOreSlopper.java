package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerOreSlopper;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIOreSlopper;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.special.ItemBedrockOreBase;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineOreSlopper extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver, IGUIProvider, IUpgradeInfoProvider, IFluidCopiable {

	public long power;
	public static final long maxPower = 100_000;

	public static final int waterUsedBase = 1_000;
	public int waterUsed = waterUsedBase;
	public static final long consumptionBase = 200;
	public long consumption = consumptionBase;

	public float progress;
	public boolean processing;

	public SlopperAnimation animation = SlopperAnimation.LOWERING;
	public float slider;
	public float prevSlider;
	public float bucket;
	public float prevBucket;
	public float blades;
	public float prevBlades;
	public float fan;
	public float prevFan;
	public int delay;

	public FluidTank[] tanks;
	public double[] ores = new double[BedrockOreType.values().length];

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityMachineOreSlopper() {
		super(11);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.WATER, 16_000);
		tanks[1] = new FluidTank(Fluids.SLOP, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineOreSlopper";
	}

	public static enum SlopperAnimation {
		LOWERING, LIFTING, MOVE_SHREDDER, DUMPING, MOVE_BUCKET
	}

	@Override
	public void updateEntity() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);

		if(!worldObj.isRemote) {

			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			tanks[0].setType(1, slots);
			FluidType conversion = this.getFluidOutput(tanks[0].getTankType());
			if(conversion != null) tanks[1].setTankType(conversion);

			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[1].getFill() > 0) this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			this.processing = false;

			upgradeManager.checkSlots(this, slots, 9, 10);
			int speed = upgradeManager.getLevel(UpgradeType.SPEED);
			int efficiency = upgradeManager.getLevel(UpgradeType.EFFECT);

			this.consumption = this.consumptionBase + (this.consumptionBase * speed) / 2 + (this.consumptionBase * efficiency);

			if(canSlop()) {
				this.power -= this.consumption;
				this.progress += 1F / (600 - speed * 150);
				this.processing = true;
				boolean markDirty = false;

				while(progress >= 1F && canSlop()) {
					progress -= 1F;

					for(BedrockOreType type : BedrockOreType.values()) {
						ores[type.ordinal()] += (ItemBedrockOreBase.getOreAmount(slots[2], type) * (1D + efficiency * 0.1));
					}

					this.decrStackSize(2, 1);
					this.tanks[0].setFill(this.tanks[0].getFill() - waterUsed);
					this.tanks[1].setFill(this.tanks[1].getFill() + waterUsed);
					markDirty = true;
				}

				if(markDirty) this.markDirty();

				List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord + 1, zCoord - 0.5, xCoord + 1.5, yCoord + 3, zCoord + 1.5).offset(dir.offsetX, 0, dir.offsetZ));

				for(Entity e : entities) {
					e.attackEntityFrom(ModDamageSource.turbofan, 1000F);

					if(!e.isEntityAlive() && e instanceof EntityLivingBase) {
						NBTTagCompound vdat = new NBTTagCompound();
						vdat.setString("type", "giblets");
						vdat.setInteger("ent", e.getEntityId());
						vdat.setInteger("cDiv", 5);
						PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(vdat, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY + e.height * 0.5, e.posZ, 150));

						worldObj.playSoundEffect(e.posX, e.posY, e.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
					}
				}

			} else {
				this.progress = 0;
			}

			for(BedrockOreType type : BedrockOreType.values()) {
				ItemStack output = ItemBedrockOreNew.make(BedrockOreGrade.BASE, type);
				outer: while(ores[type.ordinal()] >= 1) {
					for(int i = 3; i <= 8; i++) if(slots[i] != null && slots[i].getItem() == output.getItem() && slots[i].getItemDamage() == output.getItemDamage() && slots[i].stackSize < output.getMaxStackSize()) {
						slots[i].stackSize++; ores[type.ordinal()] -= 1F; continue outer;
					}
					for(int i = 3; i <= 8; i++) if(slots[i] == null) {
						slots[i] = output; ores[type.ordinal()] -= 1F; continue outer;
					}
					break outer;
				}
			}

			this.networkPackNT(150);

		} else {

			this.prevSlider = this.slider;
			this.prevBucket = this.bucket;
			this.prevBlades = this.blades;
			this.prevFan = this.fan;

			if(this.processing) {

				this.blades += 15F;
				this.fan += 35F;

				if(blades >= 360) {
					blades -= 360;
					prevBlades -= 360;
				}

				if(fan >= 360) {
					fan -= 360;
					prevFan -= 360;
				}

				if(animation == animation.DUMPING && MainRegistry.proxy.me().getDistance(xCoord + 0.5, yCoord + 4, zCoord + 0.5) <= 50) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(Blocks.iron_block));
					data.setDouble("posX", xCoord + 0.5 + dir.offsetX + worldObj.rand.nextGaussian() * 0.25);
					data.setDouble("posY", yCoord + 4.25);
					data.setDouble("posZ", zCoord + 0.5 + dir.offsetZ + worldObj.rand.nextGaussian() * 0.25);
					data.setDouble("mY", -0.2D);
					MainRegistry.proxy.effectNT(data);
				}

				if(delay > 0) {
					delay--;
					return;
				}

				switch(animation) {
				case LOWERING:
					this.bucket += 1F/40F;
					if(bucket >= 1F) {
						bucket = 1F;
						animation = SlopperAnimation.LIFTING;
						delay = 20;
					}
					break;
				case LIFTING:
					this.bucket -= 1F/40F;
					if(bucket <= 0) {
						bucket = 0F;
						animation = SlopperAnimation.MOVE_SHREDDER;
						delay = 10;
					}
					break;
				case MOVE_SHREDDER:
					this.slider += 1/50F;
					if(slider >= 1F) {
						slider = 1F;
						animation = SlopperAnimation.DUMPING;
						delay = 60;
					}
					break;
				case DUMPING:
					animation = SlopperAnimation.MOVE_BUCKET;
					break;
				case MOVE_BUCKET:
					this.slider -= 1/50F;
					if(slider <= 0F) {
						animation = SlopperAnimation.LOWERING;
						delay = 10;
					}
					break;
				}
			}
		}
	}

	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4, yCoord, zCoord + dir.offsetZ * 4, dir),
				new DirPos(xCoord - dir.offsetX * 4, yCoord, zCoord - dir.offsetZ * 4, dir.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord - rot.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 2, rot),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 2, rot),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 2, rot.getOpposite())
		};
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == 2 && stack.getItem() == ModItems.bedrock_ore_base;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return i >= 3 && i <= 8;
	}

	private static final int[] slot_access = new int[] {2, 3, 4, 5, 6, 7, 8};

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_access;
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(consumption);
		buf.writeFloat(progress);
		buf.writeBoolean(processing);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}

	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.consumption = buf.readLong();
		this.progress = buf.readFloat();
		this.processing = buf.readBoolean();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.progress = nbt.getFloat("progress");
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "slop");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setFloat("progress", progress);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "slop");
	}

	public boolean canSlop() {
		if(this.getFluidOutput(tanks[0].getTankType()) == null) return false;
		if(tanks[0].getFill() < waterUsed) return false;
		if(tanks[1].getFill() + waterUsed > tanks[1].getMaxFill()) return false;
		if(power < consumption) return false;

		return slots[2] != null && slots[2].getItem() == ModItems.bedrock_ore_base;
	}

	public FluidType getFluidOutput(FluidType input) {
		if(input == Fluids.WATER) return Fluids.SLOP;
		return null;
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getAllTanks() { return tanks; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[1]}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0]}; }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 7,
					zCoord + 4
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
		return new ContainerOreSlopper(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIOreSlopper(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.EFFECT;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_ore_slopper));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 50) + "%"));
		}
		if(type == UpgradeType.EFFECT) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_EFFICIENCY, "+" + (level * 10) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.EFFECT, 3);
		return upgrades;
	}
}
