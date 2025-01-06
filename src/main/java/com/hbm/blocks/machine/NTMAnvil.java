package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockFallingNT;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.item.EntityFallingBlockNT;
import com.hbm.inventory.container.ContainerAnvil;
import com.hbm.inventory.gui.GUIAnvil;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class NTMAnvil extends BlockFallingNT implements ITooltipProvider, IGUIProvider {

	public static final int TIER_IRON = 1;
	public static final int TIER_STEEL = 2;
	public static final int TIER_OIL = 3;
	public static final int TIER_NUCLEAR = 4;
	public static final int TIER_RBMK = 5;
	public static final int TIER_FUSION = 6;
	public static final int TIER_PARTICLE = 7;
	public static final int TIER_GERALD = 8;
	
	public final int tier;
	
	public static final HashMap<Integer, List<NTMAnvil>> tierMap = new HashMap();

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public NTMAnvil(Material mat, int tier) {
		super(mat);
		this.setStepSound(Block.soundTypeAnvil);
		this.setHardness(5.0F);
		this.setResistance(100.0F);
		this.tier = tier;
		
		List<NTMAnvil> anvils = tierMap.get((Integer)tier);
		if(anvils == null)
			anvils = new ArrayList();
		anvils.add(this);
		tierMap.put((Integer)tier, anvils);
	}
	
	public static List<ItemStack> getAnvilsFromTier(int tier) {
		List<NTMAnvil> anvils = tierMap.get((Integer)tier);
		
		if(anvils != null) {
			List<ItemStack> stacks = new ArrayList();
			
			for(NTMAnvil anvil : anvils)
				stacks.add(new ItemStack(anvil));
			
			return stacks;
		}
		
		return new ArrayList();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		
		if(this == ModBlocks.anvil_murky) {
			this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":anvil_murky");
		} else {
			this.iconTop = this.blockIcon;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : this.blockIcon;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {

			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == 2 || meta == 3)
			this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 0.75F, 0.75F);
		
		if(meta == 4 || meta == 5)
			this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 0.75F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == 2 || meta == 3)
			this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 0.75F, 0.75F);
		
		if(meta == 4 || meta == 5)
			this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 0.75F, 1.0F);
		
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.GOLD + "Tier " + tier + " Anvil");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerAnvil(player.inventory, ((NTMAnvil)world.getBlock(x, y, z)).tier);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAnvil(player.inventory, ((NTMAnvil)world.getBlock(x, y, z)).tier);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldOverrideRenderer() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void overrideRenderer(EntityFallingBlockNT falling, RenderBlocks renderBlocks, Tessellator tessellator) {

		float rotation = 0;
		if(falling.getMeta() == 2) rotation = 90F / 180F * (float) Math.PI;
		if(falling.getMeta() == 3) rotation = 270F / 180F * (float) Math.PI;
		if(falling.getMeta() == 4) rotation = 180F / 180F * (float)Math.PI;

		tessellator.addTranslation(0F, -0.5F, 0F);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Top", getIcon(1, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Bottom", getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Front", getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Back", getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Left", getIcon(0, 0), tessellator, rotation, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.anvil, "Right", getIcon(0, 0), tessellator, rotation, true);
		tessellator.addTranslation(0F, 0.5F, 0F);
	}
}
