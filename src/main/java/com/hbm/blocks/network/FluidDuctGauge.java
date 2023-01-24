package com.hbm.blocks.network;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.util.I18nUtil;

import api.hbm.fluid.IPipeNet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class FluidDuctGauge extends FluidDuctBase implements IBlockMultiPass, ILookOverlay, ITooltipProvider {

	@SideOnly(Side.CLIENT) protected IIcon overlay;
	@SideOnly(Side.CLIENT) protected IIcon overlayGauge;

	public FluidDuctGauge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPipeGauge();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":deco_steel");
		this.overlay = reg.registerIcon(RefStrings.MODID + ":fluid_duct_paintable_overlay");
		this.overlayGauge = reg.registerIcon(RefStrings.MODID + ":pipe_gauge");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		if(RenderBlockMultipass.currentPass == 0) {
			return blockIcon;
		}
		
		return side == world.getBlockMetadata(x, y, z) ? this.overlayGauge : this.overlay;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public int getPasses() {
		return 2;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityPipeBaseNT))
			return;
		
		TileEntityPipeGauge duct = (TileEntityPipeGauge) te;
		
		List<String> text = new ArrayList();
		text.add("&[" + duct.getType().getColor() + "&]" +I18nUtil.resolveKey(duct.getType().getUnlocalizedName()));
		text.add(String.format("%,d", duct.deltaTick) + " mB/t");
		text.add(String.format("%,d", duct.deltaLastSecond) + " mB/s");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}
	
	public static class TileEntityPipeGauge extends TileEntityPipeBaseNT implements INBTPacketReceiver {

		private BigInteger lastMeasurement = BigInteger.valueOf(10);
		private long deltaTick = 0;
		private long deltaSecond = 0;
		private long deltaLastSecond = 0;
		
		@Override
		public void updateEntity() {
			super.updateEntity();

			if(!worldObj.isRemote) {

				IPipeNet net = this.getPipeNet(this.getType());
				
				if(net != null && this.getType() != Fluids.NONE) {
					BigInteger total = net.getTotalTransfer();
					BigInteger delta = total.subtract(this.lastMeasurement);
					this.lastMeasurement = total;
					
					try {
						this.deltaTick = delta.longValueExact();
						if(worldObj.getTotalWorldTime() % 20 == 0) {
							this.deltaLastSecond = this.deltaSecond;
							this.deltaSecond = 0;
						}
						this.deltaSecond += deltaTick;
						
					} catch(Exception ex) { }
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setLong("deltaT", deltaTick);
				data.setLong("deltaS", deltaLastSecond);
				INBTPacketReceiver.networkPack(this, data, 25);
			}
		}

		@Override
		public void networkUnpack(NBTTagCompound nbt) {
			this.deltaTick = Math.max(nbt.getLong("deltaT"), 0);
			this.deltaLastSecond = Math.max(nbt.getLong("deltaS"), 0);
		}
	}
}
