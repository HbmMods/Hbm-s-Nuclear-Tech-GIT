package com.hbm.tileentity.bomb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.entity.missile.EntityMissileTier4.EntityMissileDoomsdayRusted;
import com.hbm.interfaces.IBomb.BombReturnCode;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerLaunchPadRusted;
import com.hbm.inventory.gui.GUILaunchPadRusted;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.TrackerUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaunchPadRusted extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {

	public int prevRedstonePower;
	public int redstonePower;
	public Set<BlockPos> activatedBlocks = new HashSet<>(4);

	public boolean missileLoaded;

	public TileEntityLaunchPadRusted() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.launchPadRusted";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(this.redstonePower > 0 && this.prevRedstonePower <= 0) {
				this.launch();
			}

			this.prevRedstonePower = this.redstonePower;
			this.networkPackNT(250);
		} else {

			List<EntityMissileBaseNT> entities = worldObj.getEntitiesWithinAABB(EntityMissileBaseNT.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord, zCoord - 0.5, xCoord + 1.5, yCoord + 10, zCoord + 1.5));

			if(!entities.isEmpty()) {
				for(int i = 0; i < 15; i++) {

					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
					if(worldObj.rand.nextBoolean()) dir = dir.getOpposite();
					if(worldObj.rand.nextBoolean()) dir = dir.getRotation(ForgeDirection.UP);
					float moX = (float) (worldObj.rand.nextGaussian() * 0.15F + 0.75) * dir.offsetX;
					float moZ = (float) (worldObj.rand.nextGaussian() * 0.15F + 0.75) * dir.offsetZ;

					NBTTagCompound data = new NBTTagCompound();
					data.setDouble("posX", xCoord + 0.5);
					data.setDouble("posY", yCoord + 0.25);
					data.setDouble("posZ", zCoord + 0.5);
					data.setString("type", "launchSmoke");
					data.setDouble("moX", moX);
					data.setDouble("moY", 0);
					data.setDouble("moZ", moZ);
					MainRegistry.proxy.effectNT(data);

				}
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(this.missileLoaded);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.missileLoaded = buf.readBoolean();
	}

	public BombReturnCode launch() {

		if(slots[1] != null && slots[2] != null && slots[3] != null && this.missileLoaded) {
			if(slots[1].getItem() == ModItems.launch_code && slots[2].getItem() == ModItems.launch_key) {
				if(slots[3] != null && slots[3].getItem() instanceof IDesignatorItem) {
					IDesignatorItem designator = (IDesignatorItem) slots[3].getItem();

					if(!designator.isReady(worldObj, slots[3], xCoord, yCoord, zCoord)) return BombReturnCode.ERROR_MISSING_COMPONENT;

					Vec3 coords = designator.getCoords(worldObj, slots[3], xCoord, yCoord, zCoord);
					int targetX = (int) Math.floor(coords.xCoord);
					int targetZ = (int) Math.floor(coords.zCoord);

					EntityMissileDoomsdayRusted missile = new EntityMissileDoomsdayRusted(worldObj, xCoord + 0.5F, yCoord + 1F, zCoord + 0.5F, targetX, targetZ);
					worldObj.spawnEntityInWorld(missile);
					TrackerUtil.setTrackingRange(worldObj, missile, 500);
					worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "hbm:weapon.missileTakeOff", 2.0F, 1.0F);
					this.missileLoaded = false;
					this.decrStackSize(1, 1);
					this.markDirty();

					return BombReturnCode.LAUNCHED;
				}
			}
		}

		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.missileLoaded = nbt.getBoolean("missileLoaded");

		this.redstonePower = nbt.getInteger("redstonePower");
		this.prevRedstonePower = nbt.getInteger("prevRedstonePower");
		NBTTagCompound activatedBlocks = nbt.getCompoundTag("activatedBlocks");
		this.activatedBlocks.clear();
		for(int i = 0; i < activatedBlocks.func_150296_c().size() / 3; i++) {
			this.activatedBlocks.add(new BlockPos(activatedBlocks.getInteger("x" + i), activatedBlocks.getInteger("y" + i), activatedBlocks.getInteger("z" + i)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("missileLoaded", missileLoaded);

		nbt.setInteger("redstonePower", redstonePower);
		nbt.setInteger("prevRedstonePower", prevRedstonePower);
		NBTTagCompound activatedBlocks = new NBTTagCompound();
		int i = 0;
		for(BlockPos p : this.activatedBlocks) {
			activatedBlocks.setInteger("x" + i, p.getX());
			activatedBlocks.setInteger("y" + i, p.getY());
			activatedBlocks.setInteger("z" + i, p.getZ());
			i++;
		}
		nbt.setTag("activatedBlocks", activatedBlocks);
	}

	public void updateRedstonePower(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		boolean powered = worldObj.isBlockIndirectlyGettingPowered(x, y, z);
		boolean contained = activatedBlocks.contains(pos);
		if(!contained && powered){
			activatedBlocks.add(pos);
			if(redstonePower == -1){
				redstonePower = 0;
			}
			redstonePower++;
		} else if(contained && !powered){
			activatedBlocks.remove(pos);
			redstonePower--;
			if(redstonePower == 0){
				redstonePower = -1;
			}
		}
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 15,
					zCoord + 3
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
		return new ContainerLaunchPadRusted(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILaunchPadRusted(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("release")) {
			if(this.missileLoaded && slots[0] == null) {
				this.missileLoaded = false;
				slots[0] = new ItemStack(ModItems.missile_doomsday_rusted);
				this.markDirty();
			}
		}
	}
}
