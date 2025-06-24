package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineRotaryFurnace;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.util.i18n.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class MachineRotaryFurnace extends BlockDummyable  implements ILookOverlay {

	public MachineRotaryFurnace(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineRotaryFurnace();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().fluid();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 1, 1, 2, 2};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		//back
		for(int i = -2; i <= 2; i++) {
			this.makeExtra(world, x - dir.offsetX + rot.offsetX * i, y, z - dir.offsetZ + rot.offsetZ * i);
		}
		//side fluid
		this.makeExtra(world, x + dir.offsetX + rot.offsetX * 2, y, z + dir.offsetZ + rot.offsetZ * 2);
		//exhaust
		this.makeExtra(world, x + rot.offsetX, y + 4, z + rot.offsetZ);
		//solid fuel
		this.makeExtra(world, x + dir.offsetX + rot.offsetX, y, z + dir.offsetZ + rot.offsetZ);
	}

	@Override
	public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null) return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityMachineRotaryFurnace)) return;

		TileEntityMachineRotaryFurnace furnace = (TileEntityMachineRotaryFurnace) te;

		ForgeDirection dir = ForgeDirection.getOrientation(furnace.getBlockMetadata() - offset);

		List<String> text = new ArrayList<>();

		//steam
		if(hitCheck(dir, pos[0], pos[1], pos[2], -1, -1, 0, x, y, z) || hitCheck(dir, pos[0], pos[1], pos[2], -1, -2, 0, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + furnace.tanks[1].getTankType().getLocalizedName());
			text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + furnace.tanks[2].getTankType().getLocalizedName());
		}

		//fluids
		if(hitCheck(dir, pos[0], pos[1], pos[2], 1, 2, 0, x, y, z) || hitCheck(dir, pos[0], pos[1], pos[2], -1, 2, 0, x, y, z)) {
			text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + furnace.tanks[0].getTankType().getLocalizedName());
		}

		if(hitCheck(dir, pos[0], pos[1], pos[2], 1, 1, 0, x, y, z)) {
			text.add(EnumChatFormatting.YELLOW + "-> " + EnumChatFormatting.RESET + "Fuel");
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
