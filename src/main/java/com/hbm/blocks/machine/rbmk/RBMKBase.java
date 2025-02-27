package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.neutron.NeutronNodeWorld;
import com.hbm.handler.neutron.RBMKNeutronHandler.RBMKNeutronNode;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKLid;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;

import api.hbm.block.IToolable;
import com.hbm.util.fauxpointtwelve.BlockPos;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class RBMKBase extends BlockDummyable implements IToolable, ILookOverlay {

	public static boolean dropLids = true;
	public static boolean digamma = false;
	public ResourceLocation coverTexture;

	protected RBMKBase() {
		super(Material.iron);
		this.setHardness(3F);
		this.setResistance(30F);
	}

	@Override
	public Block setBlockTextureName(String texture) {
		this.textureName = texture;
		this.coverTexture = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + (texture.split(":")[1]) + ".png");
		return this;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	public boolean openInv(World world, int x, int y, int z, EntityPlayer player) {

		if(world.isRemote) {
			return true;
		}

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return false;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityRBMKBase))
			return false;

		TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;

		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRBMKLid) {

			if(!rbmk.hasLid())
				return false;
		}

		if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		float height = 0.0F;

		int[] pos = this.findCore(world, x, y, z);

		if(pos != null) {
			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(te instanceof TileEntityRBMKBase) {

				TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;

				if(rbmk.hasLid()) {
					height += 0.25F;
				}
			}
		}

		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY + height, z + this.maxZ);
	}

	/*
	 * NORTH: no cover
	 * EAST: concrete cover
	 * SOUTH: lead glass cover
	 * WEST: UNUSED
	 */

	public static final ForgeDirection DIR_NO_LID = ForgeDirection.NORTH;
	public static final ForgeDirection DIR_NORMAL_LID = ForgeDirection.EAST;
	public static final ForgeDirection DIR_GLASS_LID = ForgeDirection.SOUTH;

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(world), this, dir);
		this.makeExtra(world, x, y + RBMKDials.getColumnHeight(world), z);
	}

	@Override
	protected ForgeDirection getDirModified(ForgeDirection dir) {
		return DIR_NO_LID;
	}

	public int[] getDimensions(World world) {
		return new int[] {RBMKDials.getColumnHeight(world), 0, 0, 0, 0, 0};
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {

		if(!world.isRemote && dropLids) {

			if(i == DIR_NORMAL_LID.ordinal() + offset) {
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5 + RBMKDials.getColumnHeight(world), z + 0.5, new ItemStack(ModItems.rbmk_lid)));
			}
			if(i == DIR_GLASS_LID.ordinal() + offset) {
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5 + RBMKDials.getColumnHeight(world), z + 0.5, new ItemStack(ModItems.rbmk_lid_glass)));
			}
		}

		super.breakBlock(world, x, y, z, b, i);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		if(tool != ToolType.SCREWDRIVER)
			return false;

		int[] pos = this.findCore(world, x, y, z);

		if(pos != null) {
			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(te instanceof TileEntityRBMKBase) {

				TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
				int i = rbmk.getBlockMetadata();

				if(rbmk.hasLid() && rbmk.isLidRemovable()) {

					RBMKNeutronNode node = (RBMKNeutronNode) NeutronNodeWorld.getNode(world, new BlockPos(te));
					if(node != null)
						node.removeLid();

					if(!world.isRemote) {
						if(i == DIR_NORMAL_LID.ordinal() + offset) {
							world.spawnEntityInWorld(new EntityItem(world, pos[0] + 0.5, pos[1] + 0.5 + RBMKDials.getColumnHeight(world), pos[2] + 0.5, new ItemStack(ModItems.rbmk_lid)));
						}
						if(i == DIR_GLASS_LID.ordinal() + offset) {
							world.spawnEntityInWorld(new EntityItem(world, pos[0] + 0.5, pos[1] + 0.5 + RBMKDials.getColumnHeight(world), pos[2] + 0.5, new ItemStack(ModItems.rbmk_lid_glass)));
						}

						world.setBlockMetadataWithNotify(pos[0], pos[1], pos[2], DIR_NO_LID.ordinal() + offset, 3);
					}

					return true;
				}
			}
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntityRBMKBase.diagnosticPrintHook(event, world, x, y, z);
	}

	public static int renderIDRods = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDPassive = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDControl = RenderingRegistry.getNextAvailableRenderId();
}
