package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.inventory.gui.GUIMachineAssembler;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAssembler extends TileEntityMachineAssemblerBase {
	
	public int recipe = -1;

	Random rand = new Random();
	
	public TileEntityMachineAssembler() {
		super(18);
	}

	@Override
	public String getName() {
		return "container.assembler";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0)
			if(itemStack.getItem() instanceof IBatteryItem)
				return true;
		
		if(i == 1)
			return true;
		
		return false;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			//meta below 12 means that it's an old multiblock configuration
			if(this.getBlockMetadata() < 12) {
				int meta = this.getBlockMetadata();
				if(meta == 2 || meta == 14) meta = 4;
				else if(meta == 4 || meta == 13) meta = 3;
				else if(meta == 3 || meta == 15) meta = 5;
				else if(meta == 5 || meta == 12) meta = 2;
				//get old direction
				ForgeDirection dir = ForgeDirection.getOrientation(meta);
				//remove tile from the world to prevent inventory dropping
				worldObj.removeTileEntity(xCoord, yCoord, zCoord);
				//use fillspace to create a new multiblock configuration
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.machine_assembler, dir.ordinal() + 10, 3);
				MultiblockHandlerXR.fillSpace(worldObj, xCoord, yCoord, zCoord, ((BlockDummyable) ModBlocks.machine_assembler).getDimensions(), ModBlocks.machine_assembler, dir);
				//load the tile data to restore the old values
				NBTTagCompound data = new NBTTagCompound();
				this.writeToNBT(data);
				worldObj.getTileEntity(xCoord, yCoord, zCoord).readFromNBT(data);
				return;
			}
			
			this.updateConnections();

			this.consumption = 100;
			this.speed = 100;
			
			UpgradeManager.eval(slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			speed -= speedLevel * 25;
			consumption += speedLevel * 300;
			speed += powerLevel * 5;
			consumption -= powerLevel * 30;
			speed /= (overLevel + 1);
			consumption *= (overLevel + 1);

			int rec = -1;
			if(AssemblerRecipes.getOutputFromTempate(slots[4]) != null) {
				ComparableStack comp = ItemAssemblyTemplate.readType(slots[4]);
				rec = AssemblerRecipes.recipeList.indexOf(comp);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setIntArray("progress", this.progress);
			data.setIntArray("maxProgress", this.maxProgress);
			data.setBoolean("isProgressing", isProgressing);
			data.setInteger("recipe", rec);
			this.networkPack(data, 150);
		} else {
			
			float volume = this.getVolume(2);

			if(isProgressing && volume > 0) {
				
				if(audio == null) {
					audio = this.createAudioLoop();
					audio.updateVolume(volume);
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
					audio.updateVolume(volume);
				}
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");
		this.maxProgress = nbt.getIntArray("maxProgress");
		this.isProgressing = nbt.getBoolean("isProgressing");
		this.recipe = nbt.getInteger("recipe");
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.assemblerOperate", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F);
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 3,				yCoord,	zCoord + rot.offsetZ * 3,				rot),
				new DirPos(xCoord - rot.offsetX * 2,				yCoord,	zCoord - rot.offsetZ * 2,				rot.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 3 + dir.offsetX,	yCoord,	zCoord + rot.offsetZ * 3 + dir.offsetZ, rot),
				new DirPos(xCoord - rot.offsetX * 2 + dir.offsetX,	yCoord,	zCoord - rot.offsetZ * 2 + dir.offsetZ, rot.getOpposite())
		};
	}

	@Override
	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}
	
	private AudioWrapper audio;

	@Override
	public int getRecipeCount() {
		return 1;
	}

	@Override
	public int getTemplateIndex(int index) {
		return 4;
	}

	@Override
	public int[] getSlotIndicesFromIndex(int index) {
		return new int[] {6, 17, 5};
	}

	@Override
	public ChunkCoordinates[] getInputPositions() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new ChunkCoordinates[] {new ChunkCoordinates(xCoord - dir.offsetX * 3 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 3 + rot.offsetZ)};
	}

	@Override
	public ChunkCoordinates[] getOutputPositions() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		return new ChunkCoordinates[] {new ChunkCoordinates(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2)};
	}

	@Override
	public int getPowerSlot() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return 100_000;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(2, 1, 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public int countMufflers() {
		
		int count = 0;

		for(int x = xCoord - 1; x <= xCoord + 1; x++)
			for(int z = zCoord - 1; z <= zCoord + 1; z++)
				if(worldObj.getBlock(x, yCoord - 1, z) == ModBlocks.muffler)
					count++;
		
		return count;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineAssembler(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineAssembler(player.inventory, this);
	}
}
