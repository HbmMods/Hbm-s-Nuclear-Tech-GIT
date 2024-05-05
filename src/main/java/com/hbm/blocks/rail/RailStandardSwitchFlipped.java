package com.hbm.blocks.rail;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.rail.RailStandardSwitch.TileEntityRailSwitch;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

public class RailStandardSwitchFlipped extends BlockRailWaypointSystem implements IRenderBlock {

	@SideOnly(Side.CLIENT) private IIcon iconSign;

	public RailStandardSwitchFlipped() {
		super(Material.iron);
		
		RailDef main = new RailDef("main");
		RailDef side = new RailDef("side");
		railDefs.add(main);
		railDefs.add(side);

		main.nodes.add(Vec3.createVectorHelper(-8.5, 0.1875, 0.5));
		main.nodes.add(Vec3.createVectorHelper(-7.5, 0.1875, 0.5));
		main.nodes.add(Vec3.createVectorHelper(6.5, 0.1875, 0.5));
		main.nodes.add(Vec3.createVectorHelper(7.5, 0.1875, 0.5));
		main.nodes.add(Vec3.createVectorHelper(8.5, 0.1875, 0.5));

		side.nodes.add(Vec3.createVectorHelper(-8.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-7.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-6.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-5.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-4.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-3.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-2.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-1.5, 0.1875, -3.5));
		side.nodes.add(Vec3.createVectorHelper(-0.5, 0.1875, -3.25));
		side.nodes.add(Vec3.createVectorHelper(0.5, 0.1875, -2.9375));
		side.nodes.add(Vec3.createVectorHelper(1.5, 0.1875, -2.375));
		side.nodes.add(Vec3.createVectorHelper(2.5, 0.1875, -1.4625));
		side.nodes.add(Vec3.createVectorHelper(3.5, 0.1875, -0.75));
		side.nodes.add(Vec3.createVectorHelper(4.5, 0.1875, -0.1875));
		side.nodes.add(Vec3.createVectorHelper(5.5, 0.1875, 0.175));
		side.nodes.add(Vec3.createVectorHelper(6.5, 0.1875, 0.375));
		side.nodes.add(Vec3.createVectorHelper(7.5, 0.1875, 0.5));
		side.nodes.add(Vec3.createVectorHelper(8.5, 0.1875, 0.5));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconSign = iconRegister.registerIcon(RefStrings.MODID + ":rail_switch_sign_flipped");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRailSwitch();
	}

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 7, 7, 1, 0};
	}

	@Override
	public int getOffset() {
		return 7;
	}

	@Override
	public TrackGauge getGauge(World world, int x, int y, int z) {
		return TrackGauge.STANDARD;
	}

	@Override
	public boolean canCross(World world, int x, int y, int z, Vec3 from, Vec3 to, RailDef def) {
		TileEntityRailSwitch tile = (TileEntityRailSwitch) world.getTileEntity(x, y, z);
		if(tile == null) return true;
		
		ForgeDirection dir = ForgeDirection.getOrientation(tile.getBlockMetadata() - 10);

		if(dir == Library.POS_X) if(from.xCoord < to.xCoord) return true;
		if(dir == Library.NEG_X) if(from.xCoord > to.xCoord) return true;
		if(dir == Library.POS_Z) if(from.zCoord < to.zCoord) return true;
		if(dir == Library.NEG_Z) if(from.zCoord > to.zCoord) return true;
		
		if(dir == Library.POS_X) if(to.xCoord < x + 0.5 + 7) return true;
		if(dir == Library.NEG_X) if(to.xCoord > x + 0.5 - 7) return true;
		if(dir == Library.POS_Z) if(to.zCoord < z + 0.5 + 7) return true;
		if(dir == Library.NEG_Z) if(to.zCoord > z + 0.5 - 7) return true;
		
		if(tile.isSwitched) {
			if("side".equals(def.name)) return true;
		} else {
			if("main".equals(def.name)) return true;
		}
		
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.train) return false;
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos != null) {
			TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
			
			if(tile instanceof TileEntityRailSwitch) {
				TileEntityRailSwitch sw = (TileEntityRailSwitch) tile;
				sw.isSwitched = !sw.isSwitched;
				sw.markDirty();
				world.markBlockForUpdate(pos[0], pos[1], pos[2]);
			}
		}
		
		return true;
	}
	
	@Override
	public Vec3 snapAndMove(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info) {
		return super.snapAndMove(world, x, y, z, trainX, trainY, trainZ, motionX, motionY, motionZ, speed, info);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		if(!super.checkRequirement(world, x, y, z, dir, o)) return false;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		dir = dir.getOpposite();

		int dX = dir.offsetX;
		int dZ = dir.offsetZ;
		int rX = rot.offsetX;
		int rZ = rot.offsetZ;

		for(int i = 0; i < 4; i++) if(!world.getBlock(x + dX * (2 + i) + rX * 2, y, z + dZ * (2 + i) + rZ * 2).isReplaceable(world, x + dX * (2 + i) + rX * 2, y, z + dZ * (2 + i) + rZ * 2)) return false;
		for(int i = 0; i < 2; i++) if(!world.getBlock(x + dX * (4 + i) + rX * 3, y, z + dZ * (4 + i) + rZ * 3).isReplaceable(world, x + dX * (4 + i) + rX * 3, y, z + dZ * (4 + i) + rZ * 3)) return false;
		if(!world.getBlock(x + dX * 5 + rX * 4, y, z + dZ * 5 + rZ * 4).isReplaceable(world, x + dX * 5 + rX * 4, y, z + dZ * 5 + rZ * 4)) return false;
		for(int j = 0; j < 2; j++) for(int i = 0; i < 2; i++) if(!world.getBlock(x + dX * (6 + j) + rX * (3 + i), y, z + dZ * (6 + j) + rZ * (3 + i)).isReplaceable(world, x + dX * (6 + j) + rX * (3 + i), y, z + dZ * (6 + j) + rZ * (3 + i))) return false;
		if(!world.getBlock(x + dX * 7 + rX * 5, y, z + dZ * 7 + rZ * 5).isReplaceable(world, x + dX * 7 + rX * 5, y, z + dZ * 7 + rZ * 5)) return false;
		for(int j = 0; j < 7; j++) for(int i = 0; i < 2; i++) if(!world.getBlock(x + dX * (8 + j) + rX * (4 + i), y, z + dZ * (8 + j) + rZ * (4 + i)).isReplaceable(world, x + dX * (8 + j) + rX * (4 + i), y, z + dZ * (8 + j) + rZ * (4 + i))) return false;
		
		return true;
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		BlockDummyable.safeRem = true;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		dir = dir.getOpposite();

		int dX = dir.offsetX;
		int dZ = dir.offsetZ;
		int rX = rot.offsetX;
		int rZ = rot.offsetZ;

		for(int i = 0; i < 4; i++) world.setBlock(x + dX * (2 + i) + rX * 1, y, z + dZ * (2 + i) + rZ * 1, this, rot.ordinal(), 3);
		for(int i = 0; i < 2; i++) world.setBlock(x + dX * (4 + i) + rX * 2, y, z + dZ * (4 + i) + rZ * 2, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 5 + rX * 3, y, z + dZ * 5 + rZ * 3, this, rot.ordinal(), 3);
		for(int j = 0; j < 2; j++) for(int i = 0; i < 2; i++) world.setBlock(x + dX * (6 + j) + rX * (2 + i), y, z + dZ * (6 + j) + rZ * (2 + i), this, dir.ordinal(), 3);
		world.setBlock(x + dX * 7 + rX * 4, y, z + dZ * 7 + rZ * 4, this, rot.ordinal(), 3);
		for(int j = 0; j < 7; j++) for(int i = 0; i < 2; i++) world.setBlock(x + dX * (8 + j) + rX * (3 + i), y, z + dZ * (8 + j) + rZ * (3 + i), this, dir.ordinal(), 3);
		
		BlockDummyable.safeRem = false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventory(Tessellator tessellator, Block block, int metadata) {
		GL11.glTranslated(0, -0.0625, 0);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(0.1, 0.1, 0.1);
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.rail_standard_switch_flipped, "Rail", this.blockIcon, tessellator, 0, false);
		tessellator.draw();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderWorld(Tessellator tessellator, Block block, int meta, IBlockAccess world, int x, int y, int z) {
		if(meta < 12) return;
		float rotation = 0;
		if(meta == 15) rotation = 90F / 180F * (float) Math.PI;
		if(meta == 12) rotation = 180F / 180F * (float) Math.PI;
		if(meta == 14) rotation = 270F / 180F * (float) Math.PI;
		if(meta == 12) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, -0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, 0.5F);
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.rail_standard_switch_flipped, "Rail", this.blockIcon, tessellator, rotation, true);
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityRailSwitch) {
			TileEntityRailSwitch sw = (TileEntityRailSwitch) tile;
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.rail_standard_switch_flipped, sw.isSwitched ? "SignTurn" : "SignStraight", this.iconSign, tessellator, rotation, true);
		}
		
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
		if(meta == 12) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, 0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, -0.5F);
	}
}
