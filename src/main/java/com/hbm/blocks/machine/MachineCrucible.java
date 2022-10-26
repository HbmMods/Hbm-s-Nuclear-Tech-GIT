package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.machine.ItemScraps;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCrucible;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class MachineCrucible extends BlockDummyable {

	public MachineCrucible() {
		super(Material.rock);

		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5D, 0D, -1.5D, 1.5D, 0.5D, 1.5D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.25D, 0.5D, -1.25D, 1.25D, 1.5D, -1D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.25D, 0.5D, -1.25D, -1D, 1.5D, 1.25D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.25D, 0.5D, 1D, 1.25D, 1.5D, 1.25D));
		this.bounding.add(AxisAlignedBB.getBoundingBox(1D, 0.5D, -1.25D, 1.25D, 1.5D, 1.25D));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityCrucible();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTool && ((ItemTool) player.getHeldItem().getItem()).getToolClasses(player.getHeldItem()).contains("shovel")) {
				TileEntityCrucible crucible = (TileEntityCrucible) world.getTileEntity(pos[0], pos[1], pos[2]);
				List<MaterialStack> stacks = new ArrayList();
				stacks.addAll(crucible.recipeStack);
				stacks.addAll(crucible.wasteStack);
				
				for(MaterialStack stack : stacks) {
					ItemStack scrap = ItemScraps.create(new MaterialStack(stack.material, stack.amount));
					if(!player.inventory.addItemStackToInventory(scrap)) {
						EntityItem item = new EntityItem(world, x + hitX, y + hitY, z + hitZ, scrap);
						world.spawnEntityInWorld(item);
					}
				}
				
				player.inventoryContainer.detectAndSendChanges();
				crucible.recipeStack.clear();
				crucible.wasteStack.clear();
				crucible.markDirty();
				
			} else {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}
			return true;
		} else {
			return true;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityCrucible) {
			TileEntityCrucible crucible = (TileEntityCrucible) te;
			
			List<MaterialStack> stacks = new ArrayList();
			stacks.addAll(crucible.recipeStack);
			stacks.addAll(crucible.wasteStack);
			
			for(MaterialStack stack : stacks) {
				ItemStack scrap = ItemScraps.create(new MaterialStack(stack.material, stack.amount));
				EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, scrap);
				world.spawnEntityInWorld(item);
			}
			
			crucible.recipeStack.clear();
			crucible.wasteStack.clear();
		}
		
		super.breakBlock(world, x, y, z, b, i);
	}
}
