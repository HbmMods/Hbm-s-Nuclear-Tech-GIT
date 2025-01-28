package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.container.ContainerPAQuadrupole;
import com.hbm.inventory.gui.GUIPAQuadrupole;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPAQuadrupole extends TileEntityCooledBase implements IGUIProvider, IParticleUser {

	public static final long usage = 100_000;
	public static final int focusGain = 100;
	
	public TileEntityPAQuadrupole() {
		super(2);
	}

	@Override
	public long getMaxPower() {
		return 1_000_000;
	}

	@Override
	public String getName() {
		return "container.paQuadrupole";
	}

	@Override
	public boolean canParticleEnter(Particle particle, ForgeDirection dir, int x, int y, int z) {
		ForgeDirection beamlineDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		BlockPos input = new BlockPos(xCoord, yCoord, zCoord).offset(beamlineDir, -1);
		return input.compare(x, y, z) && beamlineDir == dir;
	}

	@Override
	public void onEnter(Particle particle, ForgeDirection dir) {
		EnumCoilType type = null;
		
		int mult = 1;
		if(slots[1] != null && slots[1].getItem() == ModItems.pa_coil) {
			type = EnumUtil.grabEnumSafely(EnumCoilType.class, slots[1].getItemDamage());
			mult = type.quadMin > particle.momentum ? 5 : 1;
		}

		if(!isCool())											particle.crash(PAState.CRASH_NOCOOL);
		if(this.power < this.usage * mult)						particle.crash(PAState.CRASH_NOPOWER);
		if(type == null)										particle.crash(PAState.CRASH_NOCOIL);
		if(type != null && type.quadMax < particle.momentum)	particle.crash(PAState.CRASH_OVERSPEED);
		
		if(particle.invalid) return;
		
		particle.focus(focusGain);
		this.power -= this.usage * mult;
	}

	@Override
	public BlockPos getExitPos(Particle particle) {
		ForgeDirection beamlineDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		return new BlockPos(xCoord, yCoord, zCoord).offset(beamlineDir, 2);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
		}
		
		super.updateEntity();
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
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		return new DirPos[] {
				new DirPos(xCoord, yCoord + 2, zCoord, Library.POS_Y),
				new DirPos(xCoord, yCoord - 2, zCoord, Library.NEG_Y),
				new DirPos(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite())
		};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPAQuadrupole(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPAQuadrupole(player.inventory, this);
	}
}
