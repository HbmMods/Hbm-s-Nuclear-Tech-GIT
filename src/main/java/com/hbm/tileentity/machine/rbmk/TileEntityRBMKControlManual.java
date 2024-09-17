package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.machine.rbmk.RBMKControl;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerRBMKControl;
import com.hbm.inventory.gui.GUIRBMKControl;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityRBMKControlManual extends TileEntityRBMKControl implements IControlReceiver {

	public RBMKColor color;
	public double startingLevel;
	
	public TileEntityRBMKControlManual() {
		super();
	}

	@Override
	public String getName() {
		return "container.rbmkControl";
	}
	
	@Override
	public boolean isModerated() {
		return ((RBMKControl)this.getBlockType()).moderated;
	}

	@Override
	public void setTarget(double target) {
		this.targetLevel = target;
		this.startingLevel = this.level;
	}
	
	@Override
	public double getMult() {
		
		double surge = 0;
		
		if(this.targetLevel < this.startingLevel && Math.abs(this.level - this.targetLevel) > 0.01D) {
			surge = Math.sin(Math.pow((1D - this.level), 15) * Math.PI) * (this.startingLevel - this.targetLevel) * RBMKDials.getSurgeMod(worldObj);
			
		}
		
		return this.level + surge;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("level")) {
			this.setTarget(data.getDouble("level"));
		}
		
		if(data.hasKey("color")) {
			int c = Math.abs(data.getInteger("color")) % RBMKColor.values().length; //to stop naughty kids from sending packets that crash the server
			
			RBMKColor newCol = RBMKColor.values()[c];
			
			if(newCol == this.color) {
				this.color = null;
			} else {
				this.color = newCol;
			}
		}
		
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		if(nbt.hasKey("startingLevel"))
			this.startingLevel = nbt.getDouble("startingLevel");
		
		if(nbt.hasKey("color"))
			this.color = RBMKColor.values()[nbt.getInteger("color")];
		else
			this.color = null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("startingLevel", this.startingLevel);
		nbt.setDouble("mult", this.getMult());
		
		if(color != null)
			nbt.setInteger("color", color.ordinal());
	}
	
	public static enum RBMKColor {
		RED,
		YELLOW,
		GREEN,
		BLUE,
		PURPLE
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.CONTROL;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = super.getNBTForConsole();
		
		if(this.color != null)
			data.setShort("color", (short)this.color.ordinal());
		else
			data.setShort("color", (short)-1);
		
		return data;
	}
	
	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getColor(Context context, Arguments args) {
		return new Object[] {this.color.ordinal()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setColor(Context context, Arguments args) {
		int colorI = args.checkInteger(0);
		colorI = MathHelper.clamp_int(colorI, 0, 4);
		this.color = RBMKColor.values()[colorI];
		return new Object[] {true};
	}


	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKControl(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKControl(player.inventory, this);
	}	
}
