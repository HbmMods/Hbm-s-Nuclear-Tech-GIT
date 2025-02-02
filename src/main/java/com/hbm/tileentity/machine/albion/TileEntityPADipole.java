package com.hbm.tileentity.machine.albion;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPADipole;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.gui.GUIPADipole;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPACoil.EnumCoilType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.albion.TileEntityPASource.PAState;
import com.hbm.tileentity.machine.albion.TileEntityPASource.Particle;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPADipole extends TileEntityCooledBase implements IGUIProvider, IControlReceiver, IParticleUser {

	public int dirLower;
	public int dirUpper;
	public int dirRedstone;
	public int threshold;
	
	public static final long usage = 100_000;
	
	public TileEntityPADipole() {
		super(2);
	}

	@Override
	public long getMaxPower() {
		return 5_000_000;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
	}

	@Override
	public String getName() {
		return "container.paDipole";
	}

	@Override
	public boolean canParticleEnter(Particle particle, ForgeDirection dir, int x, int y, int z) {
		return this.yCoord == y && (this.xCoord == x || this.zCoord == z);
	}

	@Override
	public void onEnter(Particle particle, ForgeDirection dir) {
		EnumCoilType type = null;
		
		int mult = 1;
		if(slots[1] != null && slots[1].getItem() == ModItems.pa_coil) {
			type = EnumUtil.grabEnumSafely(EnumCoilType.class, slots[1].getItemDamage());
			if(type.diMin > particle.momentum) mult *= 5;
			if(type.diDistMin > particle.distanceTraveled) mult *= 5;
		}

		if(!isCool())										particle.crash(PAState.CRASH_NOCOOL);
		if(this.power < this.usage * mult)					particle.crash(PAState.CRASH_NOPOWER);
		if(type == null)									particle.crash(PAState.CRASH_NOCOIL);
		if(type != null && type.diMax < particle.momentum)	particle.crash(PAState.CRASH_OVERSPEED);
		
		if(particle.invalid) return;
		
		particle.resetDistance();
		this.power -= this.usage * mult;
	}

	@Override
	public BlockPos getExitPos(Particle particle) {
		if(particle.momentum >= this.threshold) {
			ForgeDirection dir = this.ditToForgeDir(checkRedstone() ? dirRedstone : dirUpper);
			particle.dir = dir;
			return new BlockPos(xCoord, yCoord, zCoord).offset(dir, 2);
		}
		ForgeDirection dir = this.ditToForgeDir(dirLower);
		particle.dir = dir;
		return new BlockPos(xCoord, yCoord, zCoord).offset(dir, 2);
	}
	
	public boolean checkRedstone() {
		for(DirPos pos : getConPos()) {
			if(worldObj.isBlockIndirectlyGettingPowered(pos.getX(), pos.getY(), pos.getZ())) return true;
		}
		return false;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
		}
		
		super.updateEntity();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(dirLower);
		buf.writeInt(dirUpper);
		buf.writeInt(dirRedstone);
		buf.writeInt(threshold);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		dirLower = buf.readInt();
		dirUpper = buf.readInt();
		dirRedstone = buf.readInt();
		threshold = buf.readInt();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		dirLower = nbt.getInteger("dirLower");
		dirUpper = nbt.getInteger("dirUpper");
		dirRedstone = nbt.getInteger("dirRedstone");
		threshold = nbt.getInteger("threshold");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("dirLower", dirLower);
		nbt.setInteger("dirUpper", dirUpper);
		nbt.setInteger("dirRedstone", dirRedstone);
		nbt.setInteger("threshold", threshold);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord - 1,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
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
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 1, yCoord + 2, zCoord, Library.POS_Y),
				new DirPos(xCoord - 1, yCoord + 2, zCoord, Library.POS_Y),
				new DirPos(xCoord, yCoord + 2, zCoord + 1, Library.POS_Y),
				new DirPos(xCoord, yCoord + 2, zCoord - 1, Library.POS_Y),
				new DirPos(xCoord + 1, yCoord - 2, zCoord, Library.NEG_Y),
				new DirPos(xCoord - 1, yCoord - 2, zCoord, Library.NEG_Y),
				new DirPos(xCoord, yCoord - 2, zCoord + 1, Library.NEG_Y),
				new DirPos(xCoord, yCoord - 2, zCoord - 1, Library.NEG_Y)
		};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPADipole(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPADipole(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("lower")) this.dirLower++;
		if(data.hasKey("upper")) this.dirUpper++;
		if(data.hasKey("redstone")) this.dirRedstone++;
		if(data.hasKey("threshold")) this.threshold = data.getInteger("threshold");

		if(this.dirLower > 3) this.dirLower -= 4;
		if(this.dirUpper > 3) this.dirUpper -= 4;
		if(this.dirRedstone > 3) this.dirRedstone -= 4;
		
		this.threshold = MathHelper.clamp_int(threshold, 0, 999_999_999);
	}
	
	public static ForgeDirection ditToForgeDir(int dir) {
		if(dir == 1) return ForgeDirection.EAST;
		if(dir == 2) return ForgeDirection.SOUTH;
		if(dir == 3) return ForgeDirection.WEST;
		return ForgeDirection.NORTH;
	}
}
