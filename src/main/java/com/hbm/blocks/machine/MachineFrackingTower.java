package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineFrackingTower;
import com.hbm.util.BobMathUtil;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class MachineFrackingTower extends BlockDummyable implements IPersistentInfoProvider {

	public MachineFrackingTower() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12) return new TileEntityMachineFrackingTower();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x, y + 2, z, new int[] {1, 0, 3, 3, 3, 3}, x, y, z, dir)) return false;

		if(!MultiblockHandlerXR.checkSpace(world, x - 2, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x - 2, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + 3, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + 3, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {10, -4, 2, 2, 2, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {24, -9, 1, 1, 1, 1}, x, y, z, dir)) return false;

		if(!MultiblockHandlerXR.checkSpace(world, x, y + 15, z, new int[] {1, 0, 1, 1, -2, 3}, x, y, z, dir)) return false;

		return super.checkRequirement(world, x, y, z, dir, o);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		MultiblockHandlerXR.fillSpace(world, x, y, z, getDimensions(), this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y + 2, z, new int[] {1, 0, 3, 3, 3, 3}, this, dir);

		MultiblockHandlerXR.fillSpace(world, x - 2, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);
		MultiblockHandlerXR.fillSpace(world, x - 2, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);
		MultiblockHandlerXR.fillSpace(world, x + 3, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);
		MultiblockHandlerXR.fillSpace(world, x + 3, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {10, -4, 2, 2, 2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {24, -9, 1, 1, 1, 1}, this, dir);

		MultiblockHandlerXR.fillSpace(world, x, y + 15, z, new int[] {1, 0, 1, 1, -2, 3}, this, ForgeDirection.WEST);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {

			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
	}

	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GREEN + BobMathUtil.getShortNumber(persistentTag.getLong("power")) + "HE");
		for(int i = 0; i < 2; i++) {
			FluidTank tank = new FluidTank(Fluids.NONE, 0);
			tank.readFromNBT(persistentTag, "t" + i);
			list.add(EnumChatFormatting.YELLOW + "" + tank.getFill() + "/" + tank.getMaxFill() + "mB " + tank.getTankType().getLocalizedName());
		}
	}
}
