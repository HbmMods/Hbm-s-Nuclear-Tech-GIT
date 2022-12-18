package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.inventory.gui.GUIMachineExcavator;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineExcavator extends TileEntityMachineBase implements IControlReceiver, IGUIProvider {

	public boolean enableDrill = false;
	public boolean enableCrusher = false;
	public boolean enableWalling = false;
	public boolean enableVeinMiner = false;
	public boolean enableSilkTouch = false;

	public float drillRotation = 0F;
	public float prevDrillRotation = 0F;
	public float drillExtension = 0F;
	public float prevDrillExtension = 0F;

	public TileEntityMachineExcavator() {
		super(14);
	}

	@Override
	public String getName() {
		return "container.machineExcavator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("drill", enableDrill);
			data.setBoolean("crusher", enableCrusher);
			data.setBoolean("walling", enableWalling);
			data.setBoolean("veinminer", enableVeinMiner);
			data.setBoolean("silktouch", enableSilkTouch);
			this.networkPack(data, 150);
			
		} else {
			this.prevDrillExtension = this.drillExtension;
			//this.drillExtension += 0.05F;
			
			this.prevDrillRotation = this.drillRotation;
			this.drillRotation += 15F;
			
			if(this.drillRotation >= 360F) {
				this.drillRotation -= 360F;
				this.prevDrillRotation -= 360F;
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.enableDrill = nbt.getBoolean("drill");
		this.enableCrusher = nbt.getBoolean("crusher");
		this.enableWalling = nbt.getBoolean("walling");
		this.enableVeinMiner = nbt.getBoolean("veinminer");
		this.enableSilkTouch = nbt.getBoolean("silktouch");
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("drill")) this.enableDrill = !this.enableDrill;
		if(data.hasKey("crusher")) this.enableCrusher = !this.enableCrusher;
		if(data.hasKey("walling")) this.enableWalling = !this.enableWalling;
		if(data.hasKey("veinminer")) this.enableVeinMiner = !this.enableVeinMiner;
		if(data.hasKey("silktouch")) this.enableSilkTouch = !this.enableSilkTouch;
		
		this.markChanged();
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineExcavator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineExcavator(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					0,
					zCoord - 3,
					xCoord + 4,
					yCoord + 5,
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
}
