package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.machine.ItemScraps;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineArcFurnaceLarge extends BlockDummyable {

	public MachineArcFurnaceLarge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineArcFurnaceLarge();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().power();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 2, 2, 2, 2};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		if(!super.checkRequirement(world, x, y, z, dir, o)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y, z + dir.offsetZ * o, new int[] {4, 0, 3, -2, 1, 1}, x, y, z, dir)) return false;
		return true;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y, z + dir.offsetZ * o, new int[] {4, 0, 3, -2, 1, 1}, this, dir);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + dir.offsetX * 2 + rot.offsetX, y, z + dir.offsetZ * 2 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 2 - rot.offsetX, y, z + dir.offsetZ * 2 - rot.offsetZ);
		this.makeExtra(world, x + rot.offsetX * 2 + dir.offsetX, y, z + rot.offsetZ * 2 + dir.offsetZ);
		this.makeExtra(world, x + rot.offsetX * 2 - dir.offsetX, y, z + rot.offsetZ * 2 - dir.offsetZ);
		this.makeExtra(world, x - rot.offsetX * 2 + dir.offsetX, y, z - rot.offsetZ * 2 + dir.offsetZ);
		this.makeExtra(world, x - rot.offsetX * 2 - dir.offsetX, y, z - rot.offsetZ * 2 - dir.offsetZ);
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
				TileEntityMachineArcFurnaceLarge crucible = (TileEntityMachineArcFurnaceLarge) world.getTileEntity(pos[0], pos[1], pos[2]);

				for(MaterialStack stack : crucible.liquids) {
					ItemStack scrap = ItemScraps.create(new MaterialStack(stack.material, stack.amount));
					if(!player.inventory.addItemStackToInventory(scrap)) {
						EntityItem item = new EntityItem(world, x + hitX, y + hitY, z + hitZ, scrap);
						world.spawnEntityInWorld(item);
					}
				}

				player.inventoryContainer.detectAndSendChanges();
				crucible.liquids.clear();
				crucible.markDirty();

			} else {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}
			return true;
		} else {
			return true;
		}
	}
}
