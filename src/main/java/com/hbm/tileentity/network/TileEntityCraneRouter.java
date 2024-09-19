package com.hbm.tileentity.network;

import com.hbm.inventory.container.ContainerCraneRouter;
import com.hbm.inventory.gui.GUICraneRouter;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IControlReceiverFilter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import com.hbm.util.BufferUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityCraneRouter extends TileEntityMachineBase implements IGUIProvider, IControlReceiverFilter {
	
	public ModulePatternMatcher[] patterns = new ModulePatternMatcher[6]; //why did i make six matchers???
	public int[] modes = new int[6];
	public static final int MODE_NONE = 0;
	public static final int MODE_WHITELIST = 1;
	public static final int MODE_BLACKLIST = 2;
	public static final int MODE_WILDCARD = 3;

	public TileEntityCraneRouter() {
		super(5 * 6);
		
		for(int i = 0; i < patterns.length; i++) {
			patterns[i] = new ModulePatternMatcher(5);
		}
	}

	@Override
	public String getName() {
		return "container.craneRouter";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.networkPackNT(15);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for (ModulePatternMatcher pattern : patterns) {
			pattern.serialize(buf);
		}

		BufferUtil.writeIntArray(buf, this.modes);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for (ModulePatternMatcher pattern : patterns) {
			pattern.deserialize(buf);
		}

		this.modes = BufferUtil.readIntArray(buf);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneRouter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneRouter(player.inventory, this);
	}
	@Override
	public void nextMode(int index) {
		
		int matcher = index / 5;
		int mIndex = index % 5;
		
		this.patterns[matcher].nextMode(worldObj, slots[index], mIndex);
	}

	public void initPattern(ItemStack stack, int index) {
		
		int matcher = index / 5;
		int mIndex = index % 5;
		
		this.patterns[matcher].initPatternSmart(worldObj, stack, mIndex);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < patterns.length; i++) {
			NBTTagCompound compound = nbt.getCompoundTag("pattern" + i);
			patterns[i].readFromNBT(compound);
		}
		this.modes = nbt.getIntArray("modes");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < patterns.length; i++) {
			NBTTagCompound compound = new NBTTagCompound();
			patterns[i].writeToNBT(compound);
			nbt.setTag("pattern" + i, compound);
		}
		nbt.setIntArray("modes", this.modes);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}
	@Override
	public int[] getFilterSlots() {
		return new int[]{0, slots.length};
	}
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			int i = data.getInteger("toggle");
			modes[i]++;
			if (modes[i] > 3)
				modes[i] = 0;
		}
		if(data.hasKey("slot")){
			setFilterContents(data);
		}
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		IInventory inv = (IInventory) this;
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList tags = new NBTTagList();

		int count = 0;
		for (int i = getFilterSlots()[0]; i < getFilterSlots()[1]; i++) {
			NBTTagCompound slotNBT = new NBTTagCompound();
			if (inv.getStackInSlot(i) != null) {
				slotNBT.setByte("slot", (byte) count);
				inv.getStackInSlot(i).writeToNBT(slotNBT);
				tags.appendTag(slotNBT);
			}
			count++;
		}

		nbt.setTag("items", tags);
		nbt.setIntArray("modes", modes);

		return nbt;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {

		NBTTagList items = nbt.getTagList("items", 10);
		int listSize = items.tagCount();

		if(listSize > 0 && nbt.hasKey("modes")) {
			for (int i = 0; i < listSize; i++) {
				NBTTagCompound slotNBT = items.getCompoundTagAt(i);
				byte slot = slotNBT.getByte("slot");
				ItemStack loadedStack = ItemStack.loadItemStackFromNBT(slotNBT);

				if (loadedStack != null && slot > index * 5 && slot < Math.min(index * 5 + 5, 30)) {
					this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(slotNBT));
					nextMode(slot);
					this.getWorldObj().markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
				}
			}
			modes = nbt.getIntArray("modes");
		} else {
			IControlReceiverFilter.super.pasteSettings(nbt, index, world, player, x, y, z);
		}
	}

	@Override
	public String[] infoForDisplay(World world, int x, int y, int z) {
		String[] options = new String[patterns.length];
		for (int i = 0; i < options.length; i++) {
			options[i] = "copytool.pattern" + i;
		}
		return options;
	}
}
