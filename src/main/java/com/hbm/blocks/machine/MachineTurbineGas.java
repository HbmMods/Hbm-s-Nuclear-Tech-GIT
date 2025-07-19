package com.hbm.blocks.machine;

import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineTurbineGas;
import com.hbm.util.i18n.I18nUtil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTurbineGas extends BlockDummyable implements ILookOverlay {
	
	public MachineTurbineGas(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) 
			return new TileEntityMachineTurbineGas();
		if(meta >= 6) 
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] { 2, 0, 1, 1, 4, 5 };
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		this.makeExtra(world, x - dir.offsetX * 1 + rot.offsetX, y, z - dir.offsetZ * 1 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 1 + rot.offsetX, y, z + dir.offsetZ * 1 + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 1 + rot.offsetX * -4, y, z - dir.offsetZ * 1 + rot.offsetZ * -4);
		this.makeExtra(world, x + dir.offsetX * 1 + rot.offsetX * -4, y, z + dir.offsetZ * 1 + rot.offsetZ * -4);
		this.makeExtra(world, x + rot.offsetX * 4, y + 1, z + rot.offsetZ * 4);
		this.makeExtra(world, x + rot.offsetX * -5, y + 1, z + rot.offsetZ * -5);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityMachineTurbineGas)) return;
		
		TileEntityMachineTurbineGas turbine = (TileEntityMachineTurbineGas) te;
		
		ForgeDirection dir = ForgeDirection.getOrientation(turbine.getBlockMetadata() - this.offset);
		
		List<String> text = new ArrayList();
		
		if(hitCheck(dir, pos[0], pos[1], pos[2], -1, -1, 0, x, y, z) || hitCheck(dir, pos[0], pos[1], pos[2], 1, -1, 0, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + turbine.tanks[0].getTankType().getLocalizedName());
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + turbine.tanks[1].getTankType().getLocalizedName());
		}
		
		if(hitCheck(dir, pos[0], pos[1], pos[2], -1, 4, 0, x, y, z) || hitCheck(dir, pos[0], pos[1], pos[2], 1, 4, 0, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + turbine.tanks[2].getTankType().getLocalizedName());
		}
		
		if(hitCheck(dir, pos[0], pos[1], pos[2], 0, 5, 1, x, y, z)) {
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + turbine.tanks[3].getTankType().getLocalizedName());
		}
		
		if(hitCheck(dir, pos[0], pos[1], pos[2], 0, -4, 1, x, y, z)) {
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + "Power");
		}
		
		if(!text.isEmpty()) {
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}
	
	protected boolean hitCheck(ForgeDirection dir, int coreX, int coreY, int coreZ, int exDir, int exRot, int exY, int hitX, int hitY, int hitZ) {
		
		ForgeDirection turn = dir.getRotation(ForgeDirection.DOWN);
		
		int iX = coreX + dir.offsetX * exDir + turn.offsetX * exRot;
		int iY = coreY + exY;
		int iZ = coreZ + dir.offsetZ * exDir + turn.offsetZ * exRot;
		
		return iX == hitX && iZ == hitZ && iY == hitY;
	}
}