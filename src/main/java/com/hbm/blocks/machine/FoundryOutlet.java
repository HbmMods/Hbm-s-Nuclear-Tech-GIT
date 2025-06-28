package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFoundryOutlet;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.block.IToolable;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class FoundryOutlet extends BlockContainer implements ICrucibleAcceptor, ILookOverlay, IToolable {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconSide;
	@SideOnly(Side.CLIENT) public IIcon iconBottom;
	@SideOnly(Side.CLIENT) public IIcon iconInner;
	@SideOnly(Side.CLIENT) public IIcon iconFront;
	@SideOnly(Side.CLIENT) public IIcon iconLock;
	@SideOnly(Side.CLIENT) public IIcon iconFilter;

	public FoundryOutlet() {
		super(Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":foundry_outlet_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":foundry_outlet_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":foundry_outlet_bottom");
		this.iconInner = iconRegister.registerIcon(RefStrings.MODID + ":foundry_outlet_inner");
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":foundry_outlet_front");
		this.iconLock = iconRegister.registerIcon(RefStrings.MODID + ":foundry_outlet_lock");
		this.iconFilter = iconRegister.registerIcon(RefStrings.MODID + ":foundry_outlet_filter");
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFoundryOutlet();
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {
		
		AxisAlignedBB aabb = null;
		int meta = world.getBlockMetadata(x, y, z);

		if(meta == 4) aabb = AxisAlignedBB.getBoundingBox(x + 0.625D, y, z + 0.3125D, x + 1D, y + 0.5D, z + 0.6875D);
		if(meta == 5) aabb = AxisAlignedBB.getBoundingBox(x + 0D, y, z + 0.3125D, x + 0.375D, y + 0.5D, z + 0.6875D);
		if(meta == 2) aabb = AxisAlignedBB.getBoundingBox(x + 0.3125D, y, z + 0.625D, x + 0.6875D, y + 0.5D, z + 1D);
		if(meta == 3) aabb = AxisAlignedBB.getBoundingBox(x + 0.3125D, y, z + 0D, x + 0.6875D, y + 0.5D, z + 0.375D);
		
		if(aabb != null && entityBounding.intersectsWith(aabb)) {
			list.add(aabb);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 4) this.setBlockBounds(0.625F, 0F, 0.3125F, 1F, 0.5F, 0.6875F);
		if(meta == 5) this.setBlockBounds(0F, 0F, 0.3125F, 0.375F, 0.5F, 0.6875F);
		if(meta == 2) this.setBlockBounds(0.3125F, 0F, 0.625F, 0.6875F, 0.5F, 1F);
		if(meta == 3) this.setBlockBounds(0.3125F, 0F, 0F, 0.6875F, 0.5F, 0.375F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		}
		
		if(!player.isSneaking()) {
			TileEntityFoundryOutlet tile = (TileEntityFoundryOutlet) world.getTileEntity(x, y, z);
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.scraps) {
				MaterialStack mat = ItemScraps.getMats(player.getHeldItem());
				if(mat != null) {
					tile.filter = mat.material;
				}
			} else {
				tile.invertRedstone = !tile.invertRedstone;
			}
			tile.markDirty();
			world.markBlockForUpdate(x, y, z);
		}
		
		return true;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool == ToolType.SCREWDRIVER) {
			if(world.isRemote) return true;

			TileEntityFoundryOutlet tile = (TileEntityFoundryOutlet) world.getTileEntity(x, y, z);
			tile.filter = null;
			tile.invertFilter = false;
			tile.markDirty();
			world.markBlockForUpdate(x, y, z);
		}
		
		if(tool == ToolType.HAND_DRILL) {
			if(world.isRemote) return true;

			TileEntityFoundryOutlet tile = (TileEntityFoundryOutlet) world.getTileEntity(x, y, z);
			tile.invertFilter = !tile.invertFilter;
			tile.markDirty();
			world.markBlockForUpdate(x, y, z);
		}
		
		return false;
	}

	@Override public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return stack; }

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).canAcceptPartialFlow(world, x, y, z, side, stack);
	}
	
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) world.getTileEntity(x, y, z)).flow(world, x, y, z, side, stack);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
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
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntityFoundryOutlet outlet = (TileEntityFoundryOutlet) world.getTileEntity(x, y, z);
		List<String> text = new ArrayList();
		
		if(outlet.filter != null) {
			text.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("foundry.filter", outlet.filter.names[0]));
		}
		if(outlet.invertFilter) {
			text.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("foundry.invertFilter"));
		}
		if(outlet.invertRedstone) {
			text.add(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("foundry.inverted"));
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getUnlocalizedName() + ".name"), 0xFF4000, 0x401000, text);
	}
}
