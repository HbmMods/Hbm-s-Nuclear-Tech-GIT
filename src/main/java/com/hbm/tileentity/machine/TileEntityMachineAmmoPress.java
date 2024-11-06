package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineAmmoPress;
import com.hbm.inventory.gui.GUIMachineAmmoPress;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityMachineAmmoPress extends TileEntityMachineBase implements IControlReceiver, IGUIProvider {
	
	public int selectedRecipe = -1;

	public TileEntityMachineAmmoPress() {
		super(10);
	}

	@Override
	public String getName() {
		return "container.machineAmmoPress";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.networkPackNT(25);
		}
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.selectedRecipe);
		
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.selectedRecipe = buf.readInt();
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineAmmoPress(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineAmmoPress(player.inventory, this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		int newRecipe = data.getInteger("selection");
		if(newRecipe == selectedRecipe) this.selectedRecipe = -1;
		else this.selectedRecipe = newRecipe;
		this.markDirty();
	}
}
