package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCryoDistill;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCryoDistill extends BlockDummyable implements ILookOverlay {

	public MachineCryoDistill(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineCryoDistill();
		if(meta >= 6) return new TileEntityProxyCombo().fluid().power();
		return null;
	}

	@Override
	public int[] getDimensions() {
		// Not used in filling, but checks that this whole space is safe
		return new int[] {3, 2, 3, 3, 2, 2};
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int getOffset() {
		return 3;
	}

	@Override
	public int getHeightOffset() {
		return 2;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		// Midpoint plane
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, 3, 3, 2, 2}, this, dir);

		// Each side of the walkway
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 2, 3, 0, 2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 2, -2, 3, 2, 2}, this, dir);
		
		// Top tanks
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {2, 0, 3, -1, 2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, -2, 3, 2, 2}, this, dir);


		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		safeRem = true;

		// Front face outputs
		makeExtra(world, x + dir.offsetX * 0 - rot.offsetX * 2, y - 2, z + dir.offsetZ * 0 - rot.offsetZ * 2);
		makeExtra(world, x + dir.offsetX * 0 - rot.offsetX * 1, y - 2, z + dir.offsetZ * 0 - rot.offsetZ * 1);
		makeExtra(world, x + dir.offsetX * 0 + rot.offsetX * 1, y - 2, z + dir.offsetZ * 0 + rot.offsetZ * 1);
		makeExtra(world, x + dir.offsetX * 0 + rot.offsetX * 2, y - 2, z + dir.offsetZ * 0 + rot.offsetZ * 2);
		
		// Side inputs
		makeExtra(world, x - dir.offsetX * 4 - rot.offsetX * 2, y - 2, z - dir.offsetZ * 4 - rot.offsetZ * 2);
		makeExtra(world, x - dir.offsetX * 5 - rot.offsetX * 2, y - 2, z - dir.offsetZ * 5 - rot.offsetZ * 2);
		
		safeRem = false;
	}
	
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null) return;

		int cx = pos[0];
		int cy = pos[1];
		int cz = pos[2];
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityMachineCryoDistill)) return;
		
		TileEntityMachineCryoDistill distill = (TileEntityMachineCryoDistill) te;
		
		ForgeDirection dir = ForgeDirection.getOrientation(distill.getBlockMetadata() - offset);
		List<String> text = new ArrayList<String>();

		if(hitCheck(dir, cx, cy, cz, -1, -2, -2, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + distill.tanks[0].getTankType().getName().toLowerCase()));
		}
		if(hitCheck(dir, cx, cy, cz, -2, -2, -2, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + "Power");
		}
		
		if(hitCheck(dir, cx, cy, cz, 3, -2, -2, x, y, z)
		|| hitCheck(dir, cx, cy, cz, 3, -1, -2, x, y, z)
		|| hitCheck(dir, cx, cy, cz, 3, 1, -2, x, y, z)
		|| hitCheck(dir, cx, cy, cz, 3, 2, -2, x, y, z)) {
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + distill.tanks[1].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + distill.tanks[2].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + distill.tanks[3].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + distill.tanks[4].getTankType().getName().toLowerCase()));
		}
		
		if(!text.isEmpty()) {
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}
	

	protected boolean hitCheck(ForgeDirection dir, int coreX, int coreY, int coreZ, int exDir, int exRot, int exY, int hitX, int hitY, int hitZ) {
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		int iX = coreX + dir.offsetX * exDir + rot.offsetX * exRot;
		int iY = coreY + exY;
		int iZ = coreZ + dir.offsetZ * exDir + rot.offsetZ * exRot;
		
		return iX == hitX && iZ == hitZ && iY == hitY;
	}

}
