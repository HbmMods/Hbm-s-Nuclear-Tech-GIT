package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineRockMill;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineRockMill;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.module.machine.ModuleMachineRockMill;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Vec3NT;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityMachineRockMill extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiverMK2, IControlReceiver, IGUIProvider {

	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;
	
	public long power;
	public long maxPower = 2_500;
	public boolean didProcess = false;
	
	public float rotation;
	public float prevRotation;
	
	public float rotationSpeed = 0F;
	public static final float ACCELERATION = 0.1F;
	public static final float MAX_SPEED = 15F;
	
	public boolean frame = false;

	public ModuleMachineRockMill rockMillModule;

	public TileEntityMachineRockMill() {
		super(8);
		
		this.inputTanks = new FluidTank[1];
		this.outputTanks = new FluidTank[1];
		
		this.inputTanks[0] = new FluidTank(Fluids.NONE, 4_000);
		this.outputTanks[0] = new FluidTank(Fluids.NONE, 4_000);
		
		this.rockMillModule = new ModuleMachineRockMill(0, this, slots)
				.itemInput(2).itemOutput(5)
				.fluidInput(inputTanks[0]).fluidOutput(outputTanks[0]);
	}

	@Override
	public String getName() {
		return "container.machineRockMill";
	}

	@Override
	public void updateEntity() {
		
		if(maxPower <= 0) this.maxPower = 2_500;
		
		if(!worldObj.isRemote) {
			
			GenericRecipe recipe = rockMillModule.getRecipe();
			if(recipe != null) {
				this.maxPower = recipe.power * 100;
			}
			
			this.maxPower = BobMathUtil.max(this.power, this.maxPower, 2_500);
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				for(FluidTank tank : inputTanks) if(tank.getTankType() != Fluids.NONE) this.trySubscribe(tank.getTankType(), worldObj, pos);
				for(FluidTank tank : outputTanks) if(tank.getFill() > 0) this.tryProvide(tank, worldObj, pos);
			}
			
			this.rockMillModule.update(1D, 1D, true, slots[1]);
			this.didProcess = this.rockMillModule.didProcess;
			if(this.rockMillModule.markDirty) this.markDirty();
				
			if(this.didProcess && (worldObj.getTotalWorldTime() + BlockPos.getIdentity(xCoord, yCoord, zCoord)) % 3 == 0) {
				String sound = Blocks.stone.stepSound.getStepResourcePath();
				
				if(recipe != null && recipe.getIcon().getItem() instanceof ItemBlock && ((ItemBlock) recipe.getIcon().getItem()).field_150939_a != null) {
					sound = ((ItemBlock) recipe.getIcon().getItem()).field_150939_a.stepSound.getStepResourcePath();
				}
				
				worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, sound, this.getVolume(1.0F), 0.75F);
			}
			
			this.networkPackNT(100);
			
		} else {
			
			this.prevRotation = this.rotation;
			
			this.rotationSpeed += this.ACCELERATION * (this.didProcess ? 1 : -1);
			this.rotationSpeed = MathHelper.clamp_float(this.rotationSpeed, 0F, MAX_SPEED);
			
			this.rotation += this.rotationSpeed;
			
			if(this.rotation >= 360F) {
				this.prevRotation -= 360F;
				this.rotation -= 360F;
			}

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				frame = !worldObj.getBlock(xCoord, yCoord + 3, zCoord).isAir(worldObj, xCoord, yCoord + 3, zCoord);
			}
			
			if(this.didProcess && MainRegistry.proxy.me().getDistanceSq(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5) < 35 * 35) {
				
				GenericRecipe recipe = rockMillModule.getRecipe();
				Block block = Blocks.gravel;
				int meta = 0;
				
				if(recipe != null) {
					if(recipe.getIcon().getItem() instanceof ItemBlock) {
						block = Block.getBlockFromItem(recipe.getIcon().getItem());
						meta = recipe.getIcon().getItemDamage();
					}
				}
				
				Vec3NT vec = new Vec3NT(1, 0, 0);
				vec.rotateAroundYDeg(worldObj.rand.nextDouble() * 360);
				
				double speed = 0.125D;
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "blockdust");
				data.setInteger("block", Block.getIdFromBlock(block));
				data.setByte("meta", (byte) meta);
				data.setDouble("mX", vec.xCoord * speed);
				data.setDouble("mY", vec.yCoord * speed - 0.1D);
				data.setDouble("mZ", vec.zCoord * speed);
				data.setDouble("posX", xCoord + 0.5 + vec.xCoord * 2.25);
				data.setDouble("posY", yCoord + 1.5);
				data.setDouble("posZ", zCoord + 0.5 + vec.zCoord * 2.25);
				MainRegistry.proxy.effectNT(data);
			}
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {

				new DirPos(xCoord + 3, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 3, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 3, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 3, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 3, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 3, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 3, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 3, Library.NEG_Z),
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(FluidTank tank : inputTanks) tank.serialize(buf);
		for(FluidTank tank : outputTanks) tank.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeBoolean(didProcess);
		this.rockMillModule.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(FluidTank tank : inputTanks) tank.deserialize(buf);
		for(FluidTank tank : outputTanks) tank.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.didProcess = buf.readBoolean();
		this.rockMillModule.deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.inputTanks[0].readFromNBT(nbt, "i" + 0);
		this.outputTanks[0].readFromNBT(nbt, "o" + 0);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.rockMillModule.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		this.inputTanks[0].writeToNBT(nbt, "i" + 0);
		this.outputTanks[0].writeToNBT(nbt, "o" + 0);

		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		this.rockMillModule.writeToNBT(nbt);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return stack.getItem() instanceof IBatteryItem; // battery
		if(slot == 1 && stack.getItem() == ModItems.blueprints) return true;
		if(this.rockMillModule.isItemValid(slot, stack)) return true; // recipe input crap
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return (i >= 5 && i <= 7) || this.rockMillModule.isSlotClogged(i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {2, 3, 4, 5, 6, 7};
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return inputTanks; }
	@Override public FluidTank[] getSendingTanks() { return outputTanks; }
	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {inputTanks[0], outputTanks[0]}; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineRockMill(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineRockMill(player.inventory, this); }

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index == 0) {
				this.rockMillModule.setRecipe(selection, false);
				this.markChanged();
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 3, zCoord + 3);
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
