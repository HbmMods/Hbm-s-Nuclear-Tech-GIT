package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.EnumGUI;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NTMAnvil extends BlockFalling implements ITooltipProvider {
	
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

			FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.ANVIL.ordinal(), world, x, y, z);
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
}
