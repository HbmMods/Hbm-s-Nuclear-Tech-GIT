package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.ReactorResearch;
import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerReactorControl;
import com.hbm.inventory.gui.GUIReactorControl;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityReactorControl extends TileEntityMachineBase implements IControlReceiver, IGUIProvider, SimpleComponent, CompatHandler.OCComponent {

	public TileEntityReactorControl() {
		super(1);
	}
	
	@Override
	public String getName() {
		return "container.reactorControl";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		isLinked = nbt.getBoolean("isLinked");
		levelLower = nbt.getDouble("levelLower");
		levelUpper = nbt.getDouble("levelUpper");
		heatLower = nbt.getDouble("heatLower");
		heatUpper = nbt.getDouble("heatUpper");
		function = RodFunction.values()[nbt.getInteger("function")];
		
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		
		nbt.setBoolean("isLinked", isLinked);
		nbt.setDouble("levelLower", levelLower);
		nbt.setDouble("levelUpper", levelUpper);
		nbt.setDouble("heatLower", heatLower);
		nbt.setDouble("heatUpper", heatUpper);
		nbt.setInteger("function", function.ordinal());

		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	public TileEntityReactorResearch reactor;
	
	public boolean isLinked;
	
	public int flux;
	public double level;
	public int heat;
	
	public double levelLower;
	public double levelUpper;
	public double heatLower;
	public double heatUpper;
	public RodFunction function = RodFunction.LINEAR;
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			isLinked = establishLink();
			
			if(isLinked) { 
				
				double fauxLevel = 0;

				double lowerBound = Math.min(this.heatLower, this.heatUpper);
				double upperBound = Math.max(this.heatLower, this.heatUpper);
				
				if(this.heat < lowerBound) {
					fauxLevel = this.levelLower;
					
				} else if(this.heat > upperBound) {
					fauxLevel = this.levelUpper;
					
				} else {
					fauxLevel = getTargetLevel(this.function, this.heat);
				}
				
				double level = MathHelper.clamp_double((fauxLevel * 0.01D), 0D, 1D);
				
				if(level != this.level) {
					reactor.setTarget(level);
				}
			}

			this.networkPackNT(150);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(heat);
		buf.writeDouble(level);
		buf.writeInt(flux);
		buf.writeBoolean(isLinked);
		buf.writeDouble(levelLower);
		buf.writeDouble(levelUpper);
		buf.writeDouble(heatLower);
		buf.writeDouble(heatUpper);
		buf.writeByte(function.ordinal());
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.heat = buf.readInt();
		this.level = buf.readDouble();
		this.flux = buf.readInt();
		isLinked = buf.readBoolean();
		levelLower = buf.readDouble();
		levelUpper = buf.readDouble();
		heatLower = buf.readDouble();
		heatUpper = buf.readDouble();
		function = RodFunction.values()[buf.readByte()];
	}
	
	private boolean establishLink() {
		if(slots[0] != null && slots[0].getItem() == ModItems.reactor_sensor && slots[0].stackTagCompound != null) {
			int xCoord = slots[0].stackTagCompound.getInteger("x");
    		int yCoord = slots[0].stackTagCompound.getInteger("y");
    		int zCoord = slots[0].stackTagCompound.getInteger("z");
    		
    		Block b = worldObj.getBlock(xCoord, yCoord, zCoord);
    		
    		if(b == ModBlocks.reactor_research) {
    			
    			int[] pos = ((ReactorResearch) ModBlocks.reactor_research).findCore(worldObj, xCoord, yCoord, zCoord);
    			
    			if(pos != null) {

					TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);

					if(tile instanceof TileEntityReactorResearch) {
						reactor = (TileEntityReactorResearch) tile;
						
						this.flux = reactor.totalFlux;
						this.level = reactor.level;
						this.heat = reactor.heat;
						
						return true;
					}
				}
    		}
		}
		
		return false;
	}
	
	public double getTargetLevel(RodFunction function, int heat) {
		double fauxLevel = 0;
		
		switch(function) {
		case LINEAR:
			fauxLevel = (heat - this.heatLower) * ((this.levelUpper - this.levelLower) / (this.heatUpper - this.heatLower)) + this.levelLower;
			return fauxLevel;
		case LOG:
			fauxLevel = Math.pow((heat - this.heatUpper) / (this.heatLower - this.heatUpper), 2) * (this.levelLower - this.levelUpper) + this.levelUpper;
			return fauxLevel;
		case QUAD:
			fauxLevel = Math.pow((heat - this.heatLower) / (this.heatUpper - this.heatLower), 2) * (this.levelUpper - this.levelLower) + this.levelLower;
			return fauxLevel;
		default:
			return 0.0D;
		}
	}
	
	public int[] getDisplayData() {
		if(this.isLinked) {
			int[] data = new int[3];
			data[0] = (int) (this.level * 100);
			data[1] = this.flux;
			data[2] = (int) Math.round((this.heat) * 0.00002 * 980 + 20);
			return data;
		} else {
			return new int[] { 0, 0, 0 };
		}
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("function")) {
			this.function = RodFunction.values()[data.getInteger("function")];
		} else {
			this.levelLower = data.getDouble("levelLower");
			this.levelUpper = data.getDouble("levelUpper");
			this.heatLower = data.getDouble("heatLower");
			this.heatUpper = data.getDouble("heatUpper");
		}
		
		this.markDirty();
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}
	
	public enum RodFunction {
		LINEAR,
		QUAD,
		LOG
	}

	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "reactor_control";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isLinked(Context context, Arguments args) {
		return new Object[] {isLinked};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getReactor(Context context, Arguments args) {
		return new Object[] {getDisplayData()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setParams(Context context, Arguments args) { //i hate my life
		int newFunction = args.checkInteger(0);
		double newMaxHeat = args.checkDouble(1);
		double newMinHeat = args.checkDouble(2);
		double newMaxLevel = args.checkDouble(3)/100.0;
		double newMinLevel = args.checkDouble(4)/100.0;
		function = RodFunction.values()[MathHelper.clamp_int(newFunction, 0, 2)];
		heatUpper = MathHelper.clamp_double(newMaxHeat, 0, 9999);
		heatLower = MathHelper.clamp_double(newMinHeat, 0, 9999);
		levelUpper = MathHelper.clamp_double(newMaxLevel, 0, 1);
		levelLower = MathHelper.clamp_double(newMinLevel, 0, 1);
		return new Object[] {};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getParams(Context context, Arguments args) {
		return new Object[] {function.ordinal(), heatUpper, heatLower, levelUpper, levelLower};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerReactorControl(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIReactorControl(player.inventory, this);
	}
}
