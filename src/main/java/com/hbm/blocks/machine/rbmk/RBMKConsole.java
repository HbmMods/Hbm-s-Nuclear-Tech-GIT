package com.hbm.blocks.machine.rbmk;

import api.hbm.block.IToolable;
import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKConsole extends BlockDummyable implements IToolable {

	public RBMKConsole() {
		super(Material.iron);
		this.setHardness(3F);
		this.setResistance(30F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= this.offset)
			return new TileEntityRBMKConsole();

		return null;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(!player.isSneaking()) {

			BossSpawnHandler.markFBI(player);

			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return true;

			TileEntityRBMKConsole entity = (TileEntityRBMKConsole) world.getTileEntity(pos[0], pos[1], pos[2]);
			if(entity != null) {

				if(side == 1) {
					Vec3 vec = Vec3.createVectorHelper(1.375D, 0, 0.75D);

					switch(entity.getBlockMetadata() - this.offset) {
					case 2: vec.rotateAroundY((float)Math.toRadians(90)); break;
					case 3: vec.rotateAroundY((float)Math.toRadians(270)); break;
					case 4: vec.rotateAroundY((float)Math.toRadians(180)); break;
					case 5: vec.rotateAroundY((float)Math.toRadians(0)); break;
					}

					float hX = x + hitX;
					float hZ = z + hitZ;
					double rX = entity.xCoord + 0.5D + vec.xCoord;
					double rZ = entity.zCoord + 0.5D + vec.zCoord;
					double size = 0.1875D;

					if(Math.abs(hX - rX) < size && Math.abs(hZ - rZ) < size && !player.inventory.hasItemStack(new ItemStack(ModItems.book_guide, 1, BookType.RBMK.ordinal()))) {
						player.inventory.addItemStackToInventory(new ItemStack(ModItems.book_guide, 1, BookType.RBMK.ordinal()));
						player.inventoryContainer.detectAndSendChanges();
						return true;
					}
				}

				if(world.isRemote)
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}
			return true;

		} else {
			return true;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 2, 2};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y, z + dir.offsetZ * o, new int[] {0, 0, 0, 1, 2, 2}, this, dir);
	}

	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, 0, 1, 2, 2}, x, y, z, dir))
			return false;

		return super.checkRequirement(world, x, y, z, dir, o);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER)
			return false;
		if(!world.isRemote) {
			int[] pos = findCore(world, x, y, z);
			TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
			if (tile instanceof TileEntityRBMKConsole) {
				((TileEntityRBMKConsole) tile).rotate();
			}
		}
		return true;
	}
}
