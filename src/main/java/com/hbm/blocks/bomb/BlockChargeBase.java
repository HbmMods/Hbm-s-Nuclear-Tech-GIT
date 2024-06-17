package com.hbm.blocks.bomb;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockContainerBase;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.entity.item.EntityTNTPrimedBase;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.bomb.TileEntityCharge;

import api.hbm.block.IFuckingExplode;
import api.hbm.block.IToolable;
import net.minecraft.util.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockChargeBase extends BlockContainerBase implements IBomb, IToolable, ITooltipProvider, IFuckingExplode {
	
	public static boolean safe = false;
	
	public BlockChargeBase() {
		super(Material.tnt);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCharge();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return	(dir == DOWN && world.isSideSolid(x, y + 1, z, DOWN)) ||
				(dir == UP && world.isSideSolid(x, y - 1, z, UP)) ||
				(dir == NORTH && world.isSideSolid(x, y, z + 1, NORTH)) ||
				(dir == SOUTH && world.isSideSolid(x, y, z - 1, SOUTH)) ||
				(dir == WEST && world.isSideSolid(x + 1, y, z, WEST)) ||
				(dir == EAST && world.isSideSolid(x - 1, y, z, EAST));
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		
		if(!world.isSideSolid(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir)) {
			world.setBlockToAir(x, y, z);
			//this.explode(world, x, y, z);
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		
		float f = 0.0625F;
		
		switch(world.getBlockMetadata(x, y, z)) {
		case 0: this.setBlockBounds(0.0F, 10 * f, 0.0F, 1.0F, 1.0F, 1.0F); break;
		case 1: this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 6 * f, 1.0F); break;
		case 2: this.setBlockBounds(0.0F, 0.0F, 10 * f, 1.0F, 1.0F, 1.0F); break;
		case 3: this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6 * f); break;
		case 4: this.setBlockBounds(10 * f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); break;
		case 5: this.setBlockBounds(0.0F, 0.0F, 0.0F, 6 * f, 1.0F, 1.0F); break;
		}
	}
	
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.DEFUSER)
			return false;

		TileEntityCharge charge = (TileEntityCharge) world.getTileEntity(x, y, z);
		
		if(charge.started) {
			charge.started = !charge.started;
			world.playSoundEffect(x, y, z, "hbm:weapon.fstbmbStart", 1.0F, 1.0F);
			charge.markDirty();
		} else {
			safe = true;
			this.dismantle(world, x, y, z);
			safe = false;
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		super.breakBlock(world, x, y, z, block, i);
		
		if(!safe)
			explode(world, x, y, z);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		if(!world.isRemote) {
			EntityTNTPrimedBase tntPrimed = new EntityTNTPrimedBase(world, x + 0.5D, y + 0.5D, z + 0.5D, explosion != null ? explosion.getExplosivePlacedBy() : null, this);
			tntPrimed.fuse = 0;
			tntPrimed.detonateOnCollision = false;
			world.spawnEntityInWorld(tntPrimed);
		}
	}

	@Override
	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity) {
		explode(world, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.YELLOW + "Right-click to change timer.");
		list.add(EnumChatFormatting.YELLOW + "Sneak-click to arm.");
		list.add(EnumChatFormatting.RED + "Can only be disarmed and removed with defuser.");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else {

			TileEntityCharge charge = (TileEntityCharge) world.getTileEntity(x, y, z);

			if(!charge.started) {
				
				if(player.isSneaking()) {
					
					if(charge.timer > 0) {
						charge.started = true;
						world.playSoundEffect(x, y, z, "hbm:weapon.fstbmbStart", 1.0F, 1.0F);
					}
				} else {
					
					if(charge.timer == 0) { charge.timer = 100; }
					else if(charge.timer == 100) { charge.timer = 200; }
					else if(charge.timer == 200) { charge.timer = 300; }
					else if(charge.timer == 300) { charge.timer = 600; }
					else if(charge.timer == 600) { charge.timer = 1200; }
					else if(charge.timer == 1200) { charge.timer = 3600; }
					else if(charge.timer == 3600) { charge.timer = 6000; }
					else { charge.timer = 0; }
					
					world.playSoundEffect(x, y, z, "hbm:item.techBoop", 1.0F, 1.0F);
				}
				
				charge.markDirty();
			}
			
			return false;
		}
	}
}
