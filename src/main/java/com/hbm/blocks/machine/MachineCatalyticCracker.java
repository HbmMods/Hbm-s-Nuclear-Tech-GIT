package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticCracker;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class MachineCatalyticCracker extends BlockDummyable implements ILookOverlay {

	public MachineCatalyticCracker(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityMachineCatalyticCracker();
		if(meta >= extra)
			return new TileEntityProxyCombo(false, false, true);

		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 3, 3, 2, 3};
	}

	@Override
	public int getOffset() {
		return 3;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(!world.isRemote && !player.isSneaking()) {

			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				int[] pos = this.findCore(world, x, y, z);

				if(pos == null)
					return false;

				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

				if(!(te instanceof TileEntityMachineCatalyticCracker))
					return false;

				TileEntityMachineCatalyticCracker cracker = (TileEntityMachineCatalyticCracker) te;
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, pos[0], pos[1], pos[2], player.getHeldItem());
				cracker.tanks[0].setTankType(type);
				cracker.markDirty();
				player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));

				return true;
			}
			return false;

		} else {
			return true;
		}
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{8, -1, 3, -1, 2, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{13, 0, 0, 3, 2, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{14, -13, -1, 2, 1, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{3, -1, 2, 3, -1, 3}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{8, -1, 3, -1, 2, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{13, 0, 0, 3, 2, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{14, -13, -1, 2, 1, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{3, -1, 2, 3, -1, 3}, this, dir);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 3 + rot.offsetX, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 3 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 3 - rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 3 - rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 3 + rot.offsetX, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 3 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 3 - rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 3 - rot.offsetZ * 2);

		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 2 + rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 2 + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o + dir.offsetX * 2 - rot.offsetX * 3, y + dir.offsetY * o, z + dir.offsetZ * o + dir.offsetZ * 2 - rot.offsetZ * 3);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 2 + rot.offsetX * 2, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 2 + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o - dir.offsetX * 2 - rot.offsetX * 3, y + dir.offsetY * o, z + dir.offsetZ * o - dir.offsetZ * 2 - rot.offsetZ * 3);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityMachineCatalyticCracker))
			return;

		TileEntityMachineCatalyticCracker cracker = (TileEntityMachineCatalyticCracker) te;

		List<String> text = new ArrayList();

		for(int i = 0; i < cracker.tanks.length; i++)
			text.add((i < 2 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + cracker.tanks[i].getTankType().getLocalizedName() + ": " + cracker.tanks[i].getFill() + "/" + cracker.tanks[i].getMaxFill() + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
