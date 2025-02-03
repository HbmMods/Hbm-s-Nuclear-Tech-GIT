package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.container.ContainerPARFC;
import com.hbm.inventory.gui.GUIPARFC;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.albion.TileEntityPASource.PAState;
import com.hbm.tileentity.machine.albion.TileEntityPASource.Particle;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPARFC extends TileEntityCooledBase implements IGUIProvider, IParticleUser {
	
	public static final long usage = 250_000;
	public static final int momentumGain = 100;
	public static final int defocusGain = 100;
	
	public TileEntityPARFC() {
		super(1);
	}

	@Override
	public long getMaxPower() {
		return 1_000_000;
	}

	@Override
	public String getName() {
		return "container.paRFC";
	}

	@Override
	public boolean canParticleEnter(Particle particle, ForgeDirection dir, int x, int y, int z) {
		ForgeDirection rfcDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		BlockPos input = new BlockPos(xCoord, yCoord, zCoord).offset(rfcDir, -4);
		return input.compare(x, y, z) && rfcDir == dir;
	}

	@Override
	public void onEnter(Particle particle, ForgeDirection dir) {

		if(!isCool())				particle.crash(PAState.CRASH_NOCOOL);
		if(this.power < this.usage)	particle.crash(PAState.CRASH_NOPOWER);
		
		if(particle.invalid) return;

		particle.addDistance(9);
		particle.momentum += this.momentumGain;
		particle.defocus(defocusGain);
		this.power -= this.usage;
	}

	@Override
	public BlockPos getExitPos(Particle particle) {
		ForgeDirection beamlineDir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.DOWN);
		return new BlockPos(xCoord, yCoord, zCoord).offset(beamlineDir, 5);
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
					xCoord - 4,
					yCoord - 1,
					zCoord - 4,
					xCoord + 5,
					yCoord + 2,
					zCoord + 5
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
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 3, yCoord + 2, zCoord + dir.offsetZ * 3, Library.POS_Y),
				new DirPos(xCoord - dir.offsetX * 3, yCoord + 2, zCoord - dir.offsetZ * 3, Library.POS_Y),
				new DirPos(xCoord, yCoord + 2, zCoord, Library.POS_Y),
				new DirPos(xCoord + dir.offsetX * 3, yCoord - 2, zCoord + dir.offsetZ * 3, Library.NEG_Y),
				new DirPos(xCoord - dir.offsetX * 3, yCoord - 2, zCoord - dir.offsetZ * 3, Library.NEG_Y),
				new DirPos(xCoord, yCoord - 2, zCoord, Library.NEG_Y)
		};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPARFC(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPARFC(player.inventory, this);
	}
}
