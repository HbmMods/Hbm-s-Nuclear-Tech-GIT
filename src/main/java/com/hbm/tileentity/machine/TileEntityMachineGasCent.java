package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineGasCent;
import com.hbm.inventory.recipes.GasCentrifugeRecipes;
import com.hbm.inventory.recipes.GasCentrifugeRecipes.PseudoFluidType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

//epic!
public class TileEntityMachineGasCent extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiver, IGUIProvider, IInfoProviderEC {

	public long power;
	public int progress;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 150;

	public FluidTank tank;
	public PseudoFluidTank inputTank;
	public PseudoFluidTank outputTank;

	private int audioDuration = 0;
	private AudioWrapper audio;

	private static final int[] slots_io = new int[] { 0, 1, 2, 3 };

	public TileEntityMachineGasCent() {
		super(7);
		tank = new FluidTank(Fluids.UF6, 2000);
		inputTank = new PseudoFluidTank(PseudoFluidType.NUF6, 8000);
		outputTank = new PseudoFluidTank(PseudoFluidType.LEUF6, 8000);
	}

	@Override
	public String getName() {
		return "container.gasCentrifuge";
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i < 4;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_io;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		progress = nbt.getShort("progress");
		tank.readFromNBT(nbt, "tank");
		inputTank.readFromNBT(nbt, "inputTank");
		outputTank.readFromNBT(nbt, "outputTank");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setShort("progress", (short) progress);
		tank.writeToNBT(nbt, "tank");
		inputTank.writeToNBT(nbt, "inputTank");
		outputTank.writeToNBT(nbt, "outputTank");
	}

	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / getProcessingSpeed();
	}

	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}

	private boolean canEnrich() {
		if(power > 0 && this.inputTank.getFill() >= inputTank.getTankType().getFluidConsumed() && this.outputTank.getFill() + this.inputTank.getTankType().getFluidProduced() <= outputTank.getMaxFill()) {

			ItemStack[] list = inputTank.getTankType().getOutput();

			if(this.inputTank.getTankType().getIfHighSpeed())
				if(!(slots[6] != null && slots[6].getItem() == ModItems.upgrade_gc_speed))
					return false;

			if(list == null)
				return false;

			if(list.length < 1)
				return false;

			if(InventoryUtil.doesArrayHaveSpace(slots, 0, 3, list))
				return true;
		}

		return false;
	}

	private void enrich() {
		ItemStack[] output = inputTank.getTankType().getOutput();

		this.progress = 0;
		inputTank.setFill(inputTank.getFill() - inputTank.getTankType().getFluidConsumed());
		outputTank.setFill(outputTank.getFill() + inputTank.getTankType().getFluidProduced());

		for(byte i = 0; i < output.length; i++)
			InventoryUtil.tryAddItemToInventory(slots, 0, 3, output[i].copy()); //reference types almost got me again
	}

	private void attemptConversion() {
		if(inputTank.getFill() < inputTank.getMaxFill() && tank.getFill() > 0) {
			int fill = Math.min(inputTank.getMaxFill() - inputTank.getFill(), tank.getFill());

			tank.setFill(tank.getFill() - fill);
			inputTank.setFill(inputTank.getFill() + fill);
		}
	}

	private boolean attemptTransfer(TileEntity te) {
		if(te instanceof TileEntityMachineGasCent) {
			TileEntityMachineGasCent cent = (TileEntityMachineGasCent) te;

			if(cent.tank.getFill() == 0 && cent.tank.getTankType() == tank.getTankType()) {
				if(cent.inputTank.getTankType() != outputTank.getTankType() && outputTank.getTankType() != PseudoFluidType.NONE) {
					cent.inputTank.setTankType(outputTank.getTankType());
					cent.outputTank.setTankType(outputTank.getTankType().getOutputType());
				}

				//God, why did I forget about the entirety of the fucking math library?
				if(cent.inputTank.getFill() < cent.inputTank.getMaxFill() && outputTank.getFill() > 0) {
					int fill = Math.min(cent.inputTank.getMaxFill() - cent.inputTank.getFill(), outputTank.getFill());

					outputTank.setFill(outputTank.getFill() - fill);
					cent.inputTank.setFill(cent.inputTank.getFill() + fill);
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			updateConnections();

			power = Library.chargeTEFromItems(slots, 4, power, maxPower);
			setTankType(5);

			if(GasCentrifugeRecipes.fluidConversions.containsValue(inputTank.getTankType())) {
				attemptConversion();
			}

			if(canEnrich()) {

				isProgressing = true;
				this.progress++;

				if(slots[6] != null && slots[6].getItem() == ModItems.upgrade_gc_speed)
					this.power -= 300;
				else
					this.power -= 200;

				if(this.power < 0) {
					power = 0;
					this.progress = 0;
				}

				if(progress >= getProcessingSpeed())
					enrich();

			} else {
				isProgressing = false;
				this.progress = 0;
			}

			if(worldObj.getTotalWorldTime() % 10 == 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				TileEntity te = worldObj.getTileEntity(this.xCoord - dir.offsetX, this.yCoord, this.zCoord - dir.offsetZ);

				//*AT THE MOMENT*, there's not really any need for a dedicated method for this. Yet.
				if(!attemptTransfer(te) && this.inputTank.getTankType() == PseudoFluidType.LEUF6) {
					ItemStack[] converted = new ItemStack[] { new ItemStack(ModItems.nugget_uranium_fuel, 6), new ItemStack(ModItems.fluorite) };

					if(this.outputTank.getFill() >= 600 && InventoryUtil.doesArrayHaveSpace(slots, 0, 3, converted)) {
						this.outputTank.setFill(this.outputTank.getFill() - 600);
						for(ItemStack stack : converted)
							InventoryUtil.tryAddItemToInventory(slots, 0, 3, stack);
					}
				}
			}

			this.networkPackNT(50);

		} else {

			if(isProgressing) {
				audioDuration += 2;
			} else {
				audioDuration -= 3;
			}

			audioDuration = MathHelper.clamp_int(audioDuration, 0, 60);

			if(audioDuration > 10) {

				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.updateVolume(getVolume(1F));
				audio.updatePitch((audioDuration - 10) / 100F + 0.5F);

			} else {

				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}

	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.centrifugeOperate", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeInt(progress);
		buf.writeBoolean(isProgressing);
		//pseudofluids can be refactored another day
		buf.writeInt(inputTank.getFill());
		buf.writeInt(outputTank.getFill());
		BufferUtil.writeString(buf, inputTank.getTankType().name); //cough cough
		BufferUtil.writeString(buf, outputTank.getTankType().name);

		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		progress = buf.readInt();
		isProgressing = buf.readBoolean();

		inputTank.setFill(buf.readInt());
		outputTank.setFill(buf.readInt());
		inputTank.setTankType(PseudoFluidType.types.get(BufferUtil.readString(buf)));
		outputTank.setTankType(PseudoFluidType.types.get(BufferUtil.readString(buf)));

		tank.deserialize(buf);
	}

	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

			if(GasCentrifugeRecipes.fluidConversions.containsValue(inputTank.getTankType())) {
				this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
	}

	private DirPos[] getConPos() {
		return new DirPos[] {
			new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
			new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
			new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
			new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
			new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z)
		};
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;

	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	public int getProcessingSpeed() {
		if(slots[6] != null && slots[6].getItem() == ModItems.upgrade_gc_speed) {
			return processingSpeed - 70;
		}
		return processingSpeed;
	}

	public void setTankType(int in) {

		if(slots[in] != null && slots[in].getItem() instanceof IItemFluidIdentifier) {
			IItemFluidIdentifier id = (IItemFluidIdentifier) slots[in].getItem();
			FluidType newType = id.getType(worldObj, xCoord, yCoord, zCoord, slots[in]);

			if(tank.getTankType() != newType) {
				PseudoFluidType pseudo = GasCentrifugeRecipes.fluidConversions.get(newType);

				if(pseudo != null) {
					inputTank.setTankType(pseudo);
					outputTank.setTankType(pseudo.getOutputType());
					tank.setTankType(newType);
				}
			}

		}
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 5, zCoord + 1);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	public class PseudoFluidTank {
		PseudoFluidType type;
		int fluid;
		int maxFluid;

		public PseudoFluidTank(PseudoFluidType type, int maxFluid) {
			this.type = type;
			this.maxFluid = maxFluid;
		}

		public void setFill(int i) {
			fluid = i;
		}

		public void setTankType(PseudoFluidType type) {

			if(this.type.equals(type))
				return;

			if(type == null)
				this.type = PseudoFluidType.NONE;
			else
				this.type = type;

			this.setFill(0);
		}

		public PseudoFluidType getTankType() {
			return type;
		}

		public int getFill() {
			return fluid;
		}

		public int getMaxFill() {
			return maxFluid;
		}

		//Called by TE to save fillstate
		public void writeToNBT(NBTTagCompound nbt, String s) {
			nbt.setInteger(s, fluid);
			nbt.setInteger(s + "_max", maxFluid);
			nbt.setString(s + "_type", type.name);
		}

		//Called by TE to load fillstate
		public void readFromNBT(NBTTagCompound nbt, String s) {
			fluid = nbt.getInteger(s);
			int max = nbt.getInteger(s + "_max");
			if(max > 0) maxFluid = nbt.getInteger(s + "_max");
			type = PseudoFluidType.types.get(nbt.getString(s + "_type"));
			if(type == null) type = PseudoFluidType.NONE;
		}

		/*        ______      ______
		 *       _I____I_    _I____I_
		 *      /      \\\  /      \\\
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{    || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{	   || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{	   || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *    _|_______||_||_______||_|_
		 *   |                          |
		 *   |                          |
		 *   |       |==========|       |
		 *   |       |NESTED    |       |
		 *   |       |IF  (:    |       |
		 *   |       |STATEMENTS|       |
		 *   |       |==========|       |
		 *   |                          |
		 *   |                          |
		 *   ----------------------------
		 */
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineGasCent(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineGasCent(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.progress > 0);
		data.setInteger(CompatEnergyControl.I_PROGRESS, this.progress);
	}
}
