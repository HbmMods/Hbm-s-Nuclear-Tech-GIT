package com.hbm.blocks.generic;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import static com.hbm.blocks.generic.BlockScaffoldDynamic.TileEntityScaffoldDynamic.*;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemBlowtorch;

import api.hbm.block.IToolable;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockScaffoldDynamic extends BlockContainer implements IToolable, ILookOverlay {

	@SideOnly(Side.CLIENT) public IIcon iconPoleTop;
	@SideOnly(Side.CLIENT) public IIcon iconPoleSide;
	@SideOnly(Side.CLIENT) public IIcon iconGrateTop;
	@SideOnly(Side.CLIENT) public IIcon iconGrateSide;
	@SideOnly(Side.CLIENT) public IIcon iconBarTop;
	@SideOnly(Side.CLIENT) public IIcon iconBarSide;
	
	public static int renderMode;

	public BlockScaffoldDynamic() {
		super(Material.iron);
	}

	public static int renderIDScaffold = RenderingRegistry.getNextAvailableRenderId();
	@Override public int getRenderType(){ return renderIDScaffold; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityScaffoldDynamic();
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(renderMode == 0) return side == 0 || side == 1 ? iconPoleTop : iconPoleSide;
		if(renderMode == 1) return side == 0 || side == 1 ? iconGrateTop : iconGrateSide;
		if(renderMode == 2) return side == 0 || side == 1 ? iconBarTop : iconBarSide;
		return this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconPoleTop = reg.registerIcon(RefStrings.MODID + ":scaffold_pole_top");
		this.iconPoleSide = reg.registerIcon(RefStrings.MODID + ":scaffold_pole_side");
		this.iconGrateTop = reg.registerIcon(RefStrings.MODID + ":scaffold_grate_top");
		this.iconGrateSide = reg.registerIcon(RefStrings.MODID + ":scaffold_grate_side");
		this.iconBarTop = reg.registerIcon(RefStrings.MODID + ":scaffold_bar_top");
		this.iconBarSide = reg.registerIcon(RefStrings.MODID + ":scaffold_bar_side");
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		TileEntityScaffoldDynamic tile = (TileEntityScaffoldDynamic) world.getTileEntity(x, y, z);
		if(tool == ToolType.SCREWDRIVER) {
			tile.locked = !tile.locked;
			tile.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
			
		if(tool != ToolType.TORCH) return false;
		
		
		int part = getPartFromCoord(fX, fY, fZ);

		if(part != 0 && tile.canToggle(part)) {
			tile.toggle(part);
			tile.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		return false;
	}
	
	public static int getPartFromCoord(float fX, float fY, float fZ) {
		if(fX < 0.25 && fZ < 0.25) return POLE_NX_NZ;
		if(fX > 0.75 && fZ < 0.25) return POLE_PX_NZ;
		if(fX > 0.75 && fZ > 0.75) return POLE_PX_PZ;
		if(fX < 0.25 && fZ > 0.75) return POLE_NX_PZ;
		
		if(fY == 0 && fX < 0.25) return BAR_LOWER_NEG_X;
		if(fY == 0 && fX > 0.75) return BAR_LOWER_POS_X;
		if(fY == 0 && fZ < 0.25) return BAR_LOWER_NEG_Z;
		if(fY == 0 && fZ > 0.75) return BAR_LOWER_POS_Z;
		if(fY == 1 && fX < 0.25) return BAR_UPPER_NEG_X;
		if(fY == 1 && fX > 0.75) return BAR_UPPER_POS_X;
		if(fY == 1 && fZ < 0.25) return BAR_UPPER_NEG_Z;
		if(fY == 1 && fZ > 0.75) return BAR_UPPER_POS_Z;
		
		if(fY < 0.125) return GRATE_LOWER;
		if(fY > 0.875) return GRATE_UPPER;
		
		if(fX == 0 && fY < 0.5) return BAR_LOWER_NEG_X;
		if(fX == 1 && fY < 0.5) return BAR_LOWER_POS_X;
		if(fZ == 0 && fY < 0.5) return BAR_LOWER_NEG_Z;
		if(fZ == 1 && fY < 0.5) return BAR_LOWER_POS_Z;
		if(fX == 0 && fY > 0.5) return BAR_UPPER_NEG_X;
		if(fX == 1 && fY > 0.5) return BAR_UPPER_POS_X;
		if(fZ == 0 && fY > 0.5) return BAR_UPPER_NEG_Z;
		if(fZ == 1 && fY > 0.5) return BAR_UPPER_POS_Z;
		
		return 0;
	}

	//ttoo lazy to make an itemblock just to provide this in the one method that needs it
	public static float lastFX;
	public static float lastFY;
	public static float lastFZ;
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		lastFX = fX;
		lastFY = fY;
		lastFZ = fZ;

		if(side == Library.POS_X.ordinal()) lastFX = 0;
		if(side == Library.NEG_X.ordinal()) lastFX = 1;
		if(side == Library.POS_Z.ordinal()) lastFZ = 0;
		if(side == Library.NEG_Z.ordinal()) lastFZ = 1;
		
		return side;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		TileEntityScaffoldDynamic tile = (TileEntityScaffoldDynamic) world.getTileEntity(x, y, z);
		
		// DEFAUL: POLES
		if(stack.getItemDamage() == 0) {
			if(lastFX < 0.5 && lastFZ < 0.5) tile.toggle(tile.POLE_NX_NZ);
			if(lastFX >= 0.5 && lastFZ < 0.5) tile.toggle(tile.POLE_PX_NZ);
			if(lastFX < 0.5 && lastFZ >= 0.5) tile.toggle(tile.POLE_NX_PZ);
			if(lastFX >= 0.5 && lastFZ >= 0.5) tile.toggle(tile.POLE_PX_PZ);
		}
	}
	
	public static class TileEntityScaffoldDynamic extends TileEntity {

		public int composite;
		public int prevComposite;
		public boolean locked;
		public static final int BAR_LOWER_POS_X = (1 << 0);
		public static final int BAR_LOWER_NEG_X = (1 << 1);
		public static final int BAR_LOWER_POS_Z = (1 << 2);
		public static final int BAR_LOWER_NEG_Z = (1 << 3);
		public static final int BAR_UPPER_POS_X = (1 << 4);
		public static final int BAR_UPPER_NEG_X = (1 << 5);
		public static final int BAR_UPPER_POS_Z = (1 << 6);
		public static final int BAR_UPPER_NEG_Z = (1 << 7);
		public static final int POLE_PX_PZ = (1 << 8);
		public static final int POLE_PX_NZ = (1 << 9);
		public static final int POLE_NX_PZ = (1 << 10);
		public static final int POLE_NX_NZ = (1 << 11);
		public static final int GRATE_LOWER = (1 << 12);
		public static final int GRATE_UPPER = (1 << 13);
		
		public boolean canToggle(int part) {	return !locked && (composite ^ part) != 0; }
		public void toggle(int part) {			this.composite ^= part; System.out.println("" + this.composite); }
		public boolean has(int part) {			return (this.composite & part) != 0; }
		
		@Override
		public void updateEntity() { }

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}
		
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.composite = nbt.getInteger("c");
			this.prevComposite = nbt.getInteger("p");
			this.locked = nbt.getBoolean("l");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("c", composite);
			nbt.setInteger("p", prevComposite);
			nbt.setBoolean("l", locked);
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {

		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		ItemStack held = player.getHeldItem();
		
		boolean holdsBlowtorch = held != null && held.getItem() instanceof ItemBlowtorch;
		boolean holdScrewdriver = held != null && (held.getItem() == ModItems.screwdriver || held.getItem() == ModItems.screwdriver_desh);

		MovingObjectPosition mop = mc.objectMouseOver;
		
		if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
			float fX = (float) (mop.hitVec.xCoord - x);
			float fY = (float) (mop.hitVec.yCoord - y);
			float fZ = (float) (mop.hitVec.zCoord - z);
			
			TileEntityScaffoldDynamic tile = (TileEntityScaffoldDynamic) world.getTileEntity(x, y, z);
			
			if(tile != null && tile.locked && (holdsBlowtorch || holdScrewdriver)) {
				List<String> text = new ArrayList();
				text.add(EnumChatFormatting.RED + "Locked!");
				ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
				return;
			}
			
			if(holdsBlowtorch) {
				String name = null;
				int part = getPartFromCoord(fX, fY, fZ);
				if(part <= (1 << 3)) name = "Lower Vertical Bar";
				else if(part <= (1 << 7)) name = "Upper Vertical Bar";
				else if(part <= (1 << 10)) name = "Pole";
				else if(part <= (1 << 13)) name = "Grate";
				
				if(name != null) {
					List<String> text = new ArrayList();
					text.add("Toggle:");
					text.add(name);
					ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
				}
			}
		}
	}
}
