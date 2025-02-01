package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemStamp;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import com.hbm.util.BufferUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachinePress extends TileEntityMachineBase implements IGUIProvider {

	public int speed = 0; // speed ticks up once (or four times if preheated) when operating
	public static final int maxSpeed = 400; // max speed ticks for acceleration
	public static final int progressAtMax = 25; // max progress speed when hot
	public int burnTime = 0; // burn ticks of the loaded fuel, 200 ticks equal one operation

	public int press; // extension of the press, operation is completed if maxPress is reached
	public double renderPress; // client-side version of the press var, a double for smoother rendering
	public double lastPress; // for interp
	private int syncPress; // for interp
	private int turnProgress; // for interp 3: revenge of the sith
	public final static int maxPress = 200; // max tick count per operation assuming speed is 1
	boolean isRetracting = false; // direction the press is currently going
	private int delay; // delay between direction changes to look a bit more appealing

	public ItemStack syncStack;

	public TileEntityMachinePress() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.press";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			boolean preheated = false;

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == ModBlocks.press_preheater) {
					preheated = true;
					break;
				}
			}

			boolean canProcess = this.canProcess();

			if((canProcess || this.isRetracting) && this.burnTime >= 200) {
				this.speed += preheated ? 4 : 1;

				if(this.speed > this.maxSpeed) {
					this.speed = this.maxSpeed;
				}
			} else {
				this.speed -= 1;
				if(this.speed < 0) {
					this.speed = 0;
				}
			}

			if(delay <= 0) {

				int stampSpeed = speed * progressAtMax / maxSpeed;

				if(this.isRetracting) {
					this.press -= stampSpeed;

					if(this.press <= 0) {
						this.isRetracting = false;
						this.delay = 5;
					}
				} else if(canProcess) {
					this.press += stampSpeed;

					if(this.press >= this.maxPress) {
						this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.pressOperate", getVolume(1.5F), 1.0F);
						ItemStack output = PressRecipes.getOutput(slots[2], slots[1]);
						if(slots[3] == null) {
							slots[3] = output.copy();
						} else {
							slots[3].stackSize += output.stackSize;
						}
						this.decrStackSize(2, 1);

						if(slots[1].getMaxDamage() != 0) {
							slots[1].setItemDamage(slots[1].getItemDamage() + 1);
							if(slots[1].getItemDamage() >= slots[1].getMaxDamage()) {
								slots[1] = null;
							}
						}

						this.isRetracting = true;
						this.delay = 5;
						if(this.burnTime >= 200) {
							this.burnTime -= 200; // only subtract fuel if operation was actually successful
						}

						this.markDirty();
					}
				} else if(this.press > 0){
					this.isRetracting = true;
				}
			} else {
				delay--;
			}

			if(slots[0] != null && burnTime < 200 && TileEntityFurnace.getItemBurnTime(slots[0]) > 0) { // less than one operation stored? burn more fuel!
				burnTime += TileEntityFurnace.getItemBurnTime(slots[0]);

				if(slots[0].stackSize == 1 && slots[0].getItem().hasContainerItem(slots[0])) {
					slots[0] = slots[0].getItem().getContainerItem(slots[0]).copy();
				} else {
					this.decrStackSize(0, 1);
				}
				this.markChanged();
			}

			this.networkPackNT(50);

		} else {

			// approach-based interpolation, GO!
			this.lastPress = this.renderPress;

			if(this.turnProgress > 0) {
				this.renderPress = this.renderPress + ((this.syncPress - this.renderPress) / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.renderPress = this.syncPress;
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.speed);
		buf.writeInt(this.burnTime);
		buf.writeInt(this.press);
		BufferUtil.writeItemStack(buf, slots[2]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.speed = buf.readInt();
		this.burnTime = buf.readInt();
		this.syncPress = buf.readInt();
		this.syncStack = BufferUtil.readItemStack(buf);

		this.turnProgress = 2;
	}

	public boolean canProcess() {
		if(burnTime < 200) return false;
		if(slots[1] == null || slots[2] == null) return false;

		ItemStack output = PressRecipes.getOutput(slots[2], slots[1]);

		if(output == null) return false;

		if(slots[3] == null) return true;
		if(slots[3].stackSize + output.stackSize <= slots[3].getMaxStackSize() && slots[3].getItem() == output.getItem() && slots[3].getItemDamage() == output.getItemDamage()) return true;
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {

		if(stack.getItem() instanceof ItemStamp)
			return i == 1;

		if(TileEntityFurnace.getItemBurnTime(stack) > 0 && i == 0)
			return true;

		return i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		press = nbt.getInteger("press");
		burnTime = nbt.getInteger("burnTime");
		speed = nbt.getInteger("speed");
		isRetracting = nbt.getBoolean("ret");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("press", press);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("speed", speed);
		nbt.setBoolean("ret", isRetracting);
	}

	AxisAlignedBB aabb;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(aabb != null)
			return aabb;

		aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 3, zCoord + 1);
		return aabb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachinePress(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachinePress(player.inventory, this);
	}
}
