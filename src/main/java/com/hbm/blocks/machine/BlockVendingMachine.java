package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IBlockMulti;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsVendingMachine;
import com.hbm.items.ModItems;
import com.hbm.main.NTMSounds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockVendingMachine extends BlockDummyable implements IBlockMulti {

	public BlockVendingMachine() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityVendingMachine();
		return null;
	}

	@Override public int getSubCount() { return 2; }

	@Override public int[] getDimensions() { return new int[] {1, 0, 0, 0, 0, 0}; }
	@Override public int getOffset() { return 0; }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.coin_token) {
			player.getHeldItem().stackSize--;
			
			if(world.isRemote) return true;
			
			int dmg = 0;
			int meta = 0;
			int[] pos = this.findCore(world, x, y, z);
			if(pos != null) {
				meta = world.getBlockMetadata(pos[0], pos[1], pos[2]);
				if(world.getBlockMetadata(pos[0], pos[1] + 1, pos[2]) >= 6) dmg = 1;
				
				ItemStack toDrop = null;
				
				if(dmg == 0) toDrop = ItemPool.getStack(ItemPoolsVendingMachine.POOL_SODA, world.rand);
				if(dmg == 1) toDrop = ItemPool.getStack(ItemPoolsVendingMachine.POOL_SNACKS, world.rand);
				
				if(toDrop != null) {
					ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
					EntityItem item = new EntityItem(world, x + 0.5 + dir.offsetX * 0.75, pos[1] + 0.25, z + 0.5 + dir.offsetZ * 0.75, toDrop);
					world.spawnEntityInWorld(item);
				}
				
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, NTMSounds.GUN_BOLT_OPEN, 1F, 0.75F);
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, player, stack);
		
		if(stack.getItemDamage() % 2 == 1 && world.getBlock(x, y + 1, z) == this) {
			this.makeExtra(world, x, y + 1, z);
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int[] pos = this.findCore(world, x, y, z);
		int dmg = 0;
		if(pos != null) {
			if(world.getBlockMetadata(pos[0], pos[1] + 1, pos[2]) >= 6) dmg = 1;
		}
		ret.add(new ItemStack(this, 1, dmg));
		return ret;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) list.add(new ItemStack(item, 1, i));
	}
	
	public static class TileEntityVendingMachine extends TileEntity {
		
		AxisAlignedBB bb = null;
		
		@Override
		public AxisAlignedBB getRenderBoundingBox() {
			if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1);
			return bb;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}
	}
}
