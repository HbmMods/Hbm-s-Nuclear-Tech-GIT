package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IDummy;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityVaultDoor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DummyBlockVault extends BlockContainer implements IDummy, IBomb {

	public static boolean safeBreak = false;

	public DummyBlockVault(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDummy();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		if(!safeBreak) {
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityDummy) {
				int a = ((TileEntityDummy) te).targetX;
				int b = ((TileEntityDummy) te).targetY;
				int c = ((TileEntityDummy) te).targetZ;

				if(!world.isRemote)
					world.func_147480_a(a, b, c, true);
			}
		}
		world.removeTileEntity(x, y, z);
	}

	@Override
	public int getRenderType() {
		return -1;
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
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(ModBlocks.vault_door);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemLock || player.getHeldItem().getItem() == ModItems.key_kit)) {
			return false;

		} else if(!player.isSneaking()) {
			TileEntity til = world.getTileEntity(x, y, z);
			if(til != null && til instanceof TileEntityDummy) {
				int a = ((TileEntityDummy) til).targetX;
				int b = ((TileEntityDummy) til).targetY;
				int c = ((TileEntityDummy) til).targetZ;

				TileEntityVaultDoor entity = (TileEntityVaultDoor) world.getTileEntity(a, b, c);
				if(entity != null) {
					if(entity.canAccess(player))
						entity.tryToggle();
				}
			}

			return true;
		} else {
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityDummy) {
				int a = ((TileEntityDummy) te).targetX;
				int b = ((TileEntityDummy) te).targetY;
				int c = ((TileEntityDummy) te).targetZ;

				TileEntityVaultDoor entity = (TileEntityVaultDoor) world.getTileEntity(a, b, c);
				if(entity != null) {
					entity.type++;
					if(entity.type >= entity.maxTypes)
						entity.type = 0;
				}
			}

			return true;
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityDummy) {
				int a = ((TileEntityDummy) te).targetX;
				int b = ((TileEntityDummy) te).targetY;
				int c = ((TileEntityDummy) te).targetZ;

				TileEntityVaultDoor entity = (TileEntityVaultDoor) world.getTileEntity(a, b, c);
				if(entity != null && !entity.isLocked()) {
					entity.tryToggle();
					return BombReturnCode.TRIGGERED;
				}
			}
			
			return BombReturnCode.ERROR_INCOMPATIBLE;
		}

		return BombReturnCode.UNDEFINED;
	}
}
