package com.hbm.blocks.machine;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.rail.IRenderBlock;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;
import com.hbm.tileentity.machine.TileEntityMachineFunnel;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class MachineFunnel extends BlockContainer implements ITooltipProvider, IRenderBlock {

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;

	public MachineFunnel() {
		super(Material.iron);
	}

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":machine_funnel_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_funnel_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":machine_funnel_bottom");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineFunnel();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntity entity = world.getTileEntity(x, y, z);
			if(entity instanceof TileEntityMachineFunnel) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	private final Random rand = new Random();

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		ISidedInventory tile = (ISidedInventory) world.getTileEntity(x, y, z);
		if(tile != null) {
			for(int i1 = 0; i1 < tile.getSizeInventory(); ++i1) {
				ItemStack itemstack = tile.getStackInSlot(i1);
				if(itemstack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
					while(itemstack.stackSize > 0) {
						int j1 = this.rand.nextInt(21) + 10;
						if(j1 > itemstack.stackSize) j1 = itemstack.stackSize;
						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						if(itemstack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						float f3 = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * f3;
						entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventory(Tessellator tessellator, Block block, int metadata) {
		GL11.glTranslatef(0F, -0.5F, 0F);
		tessellator.startDrawingQuads();
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.funnel, "Top", block.getIcon(1, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.funnel, "Bottom", block.getIcon(0, 0), tessellator, 0, false);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.funnel, "Side", block.getIcon(2, 0), tessellator, 0, false);
		tessellator.draw();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderWorld(Tessellator tessellator, Block block, int meta, IBlockAccess world, int x, int y, int z) {
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.funnel, "Top", block.getIcon(1, 0), tessellator, 0, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.funnel, "Bottom", block.getIcon(0, 0), tessellator, 0, true);
		ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.funnel, "Side", block.getIcon(2, 0), tessellator, 0, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
	}
}
