package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineCryoDistill;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
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
		return new int[] {4, 0, 2, 1, 4, 3};
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		//		world.setBlock( x + dir.offsetX - rot.offsetX * 2, y, z + rot.offsetZ * 3 + dir.offsetZ, ModBlocks.ntm_dirt);

		this.safeRem = true;
		
		this.makeExtra(world, x + dir.offsetX - rot.offsetX * 2, y, z + rot.offsetZ * 3 - dir.offsetZ *2);
		this.makeExtra(world, x + dir.offsetX - rot.offsetX * -3, y, z + rot.offsetZ * -2 - dir.offsetZ *2);
		this.makeExtra(world, x + dir.offsetX - rot.offsetX * -2, y, z + rot.offsetZ * -1 - dir.offsetZ *2);

		this.makeExtra(world, x - dir.offsetX * 2 - rot.offsetX * 2, y, z + rot.offsetZ * 3 + dir.offsetZ * 1);
		this.makeExtra(world, x - dir.offsetX * 2 - rot.offsetX * -2, y, z + rot.offsetZ * -1 + dir.offsetZ * 1);
		this.makeExtra(world, x - dir.offsetX * 2 - rot.offsetX * -3, y, z + rot.offsetZ * -2 + dir.offsetZ * 1);
		//world.setBlock(x - dir.offsetX * 2 - rot.offsetX * -2, y, z + rot.offsetZ * -1 + dir.offsetZ * 1,  ModBlocks.ntm_dirt);
		//world.setBlock( x + dir.offsetX - rot.offsetX * -2, y, z + rot.offsetZ * -1 - dir.offsetZ *2, ModBlocks.basalt_asbestos);
		
		this.safeRem = false;
	}
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityMachineCryoDistill)) return;
		
		TileEntityMachineCryoDistill turbine = (TileEntityMachineCryoDistill) te;
		
		ForgeDirection dir = ForgeDirection.getOrientation(turbine.getBlockMetadata() - this.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		List<String> text = new ArrayList();
		
		if(hitCheck(dir, pos[0], pos[1], pos[2], 1, 2, 3, 2, 0, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[0].getTankType().getName().toLowerCase()));
		}
		if(hitCheck(dir, pos[0], pos[1], pos[2], 1, -3, -2, 2, 0, x, y, z) || (hitCheck(dir, pos[0], pos[1], pos[2], 1, -2, -1, 2, 0, x, y, z))) {
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[1].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[2].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[3].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[4].getTankType().getName().toLowerCase()));
		}
		
		if(shitCheck(dir, pos[0], pos[1], pos[2], 2, -2, -1, 1, 0, x, y, z) ||(shitCheck(dir, pos[0], pos[1], pos[2], 2, -3, -2, 1, 0, x, y, z)))  {
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[1].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[2].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[3].getTankType().getName().toLowerCase()));
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + turbine.tanks[4].getTankType().getName().toLowerCase()));
		}
		
		if(shitCheck(dir, pos[0], pos[1], pos[2], 2, 2, 3, 1, 0, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + "Power");
		}
		
		if(!text.isEmpty()) {
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}
	
	protected boolean hitCheck(ForgeDirection dir, int coreX, int coreY, int coreZ, int exDir, int exRot, int exDirZ, int exRotZ, int exY, int hitX, int hitY, int hitZ) {
		
		ForgeDirection turn = dir.getRotation(ForgeDirection.UP);
		

		
		int iX = coreX + dir.offsetX * exDir - turn.offsetX * exRot;
		int iY = coreY + exY;
		int iZ = coreZ + turn.offsetZ * exDirZ - dir.offsetZ * exRotZ;
		
		
		return iX == hitX && iZ == hitZ && iY == hitY;
	}
	protected boolean shitCheck(ForgeDirection dir, int coreX, int coreY, int coreZ, int exDir, int exRot, int exDirZ, int exRotZ, int exY, int hitX, int hitY, int hitZ) { //i cannot for the fucking life of me figure this shit out somedays.
		
		ForgeDirection turn = dir.getRotation(ForgeDirection.UP);
		

		
		int iX = coreX - dir.offsetX * exDir - turn.offsetX * exRot;
		int iY = coreY + exY;
		int iZ = coreZ + turn.offsetZ * exDirZ + dir.offsetZ * exRotZ;
		

		
		return iX == hitX && iZ == hitZ && iY == hitY;
	}
}
