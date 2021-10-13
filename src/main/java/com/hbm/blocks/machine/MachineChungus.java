package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityChungus;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineChungus extends BlockDummyable {

	public MachineChungus(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityChungus();
		
		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return true;

			TileEntityChungus entity = (TileEntityChungus) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(entity.getBlockMetadata() - this.offset);
				ForgeDirection turn = dir.getRotation(ForgeDirection.DOWN);

				int iX = entity.xCoord + dir.offsetX + turn.offsetX * 2;
				int iX2 = entity.xCoord + dir.offsetX * 2 + turn.offsetX * 2;
				int iZ = entity.zCoord + dir.offsetZ + turn.offsetZ * 2;
				int iZ2 = entity.zCoord + dir.offsetZ * 2 + turn.offsetZ * 2;
				
				if((x == iX || x == iX2) && (z == iZ || z == iZ2) && y < entity.yCoord + 2) {
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:block.chungusLever", 1.5F, 1.0F);
					
					if(!world.isRemote) {
						switch(entity.tanks[0].getTankType()) {
						case STEAM:
							entity.tanks[0].setTankType(FluidType.HOTSTEAM);
							entity.tanks[1].setTankType(FluidType.STEAM);
							entity.tanks[0].setFill(entity.tanks[0].getFill() / 10);
							entity.tanks[1].setFill(0);
							break;
							
						case HOTSTEAM:
							entity.tanks[0].setTankType(FluidType.SUPERHOTSTEAM);
							entity.tanks[1].setTankType(FluidType.HOTSTEAM);
							entity.tanks[0].setFill(entity.tanks[0].getFill() / 10);
							entity.tanks[1].setFill(0);
							break;
							
						case SUPERHOTSTEAM:
							entity.tanks[0].setTankType(FluidType.ULTRAHOTSTEAM);
							entity.tanks[1].setTankType(FluidType.SUPERHOTSTEAM);
							entity.tanks[0].setFill(entity.tanks[0].getFill() / 10);
							entity.tanks[1].setFill(0);
							break;
							
						default:
						case ULTRAHOTSTEAM:
							entity.tanks[0].setTankType(FluidType.STEAM);
							entity.tanks[1].setTankType(FluidType.SPENTSTEAM);
							entity.tanks[0].setFill(Math.min(entity.tanks[0].getFill() * 1000, entity.tanks[0].getMaxFill()));
							entity.tanks[1].setFill(0);
							break;
						}
						
						entity.markDirty();
					}
					
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 0, 3, 2, 2 };
	}

	@Override
	public int getOffset() {
		return 3;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -4, 0, 3, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 6, -1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 10, -7, 1, 1}, this, dir);
		world.setBlock(x + dir.offsetX, y + 2, z + dir.offsetZ, this, dir.ordinal(), 3);

		this.makeExtra(world, x + dir.offsetX, y + 2, z + dir.offsetZ);
		this.makeExtra(world, x + dir.offsetX * (o - 10), y, z + dir.offsetZ * (o - 10));
		ForgeDirection side = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world, x + dir.offsetX * o + side.offsetX * 2 , y, z + dir.offsetZ * o + side.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o - side.offsetX * 2 , y, z + dir.offsetZ * o - side.offsetZ * 2);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 6, -1, 1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 10, -7, 1, 1}, x, y, z, dir)) return false;
		if(!world.getBlock(x + dir.offsetX, y + 2, z + dir.offsetZ).canPlaceBlockAt(world, x + dir.offsetX, y + 2, z + dir.offsetZ)) return false;
		
		return true;
	}
}
