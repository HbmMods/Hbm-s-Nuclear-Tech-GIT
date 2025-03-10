package com.hbm.blocks.network;

import api.hbm.fluidmk2.FluidNetMK2;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.util.I18nUtil;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
		text.add("&[" + duct.getType().getColor() + "&]" + duct.getType().getLocalizedName());
		text.add(String.format(Locale.US, "%,d", duct.deltaTick) + " mB/t");
		text.add(String.format(Locale.US, "%,d", duct.deltaLastSecond) + " mB/s");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
	public static class TileEntityPipeGauge extends TileEntityPipeBaseNT implements SimpleComponent, CompatHandler.OCComponent {

		private long deltaTick = 0;
		private long deltaSecond = 0;
		private long deltaLastSecond = 0;

		@Override
		public void updateEntity() {
			super.updateEntity();

			if(!worldObj.isRemote) {

				if(this.node != null && this.node.net != null && this.getType() != Fluids.NONE) {

					this.deltaTick = ((FluidNetMK2) this.node.net).fluidTracker;
					if(worldObj.getTotalWorldTime() % 20 == 0) {
						this.deltaLastSecond = this.deltaSecond;
						this.deltaSecond = 0;
					}
					this.deltaSecond += deltaTick;
				}

				networkPackNT(25);
			}
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeLong(deltaTick);
			buf.writeLong(deltaLastSecond);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			this.deltaTick = Math.max(buf.readLong(), 0);
			this.deltaLastSecond = Math.max(buf.readLong(), 0);
		}

		@Optional.Method(modid = "OpenComputers")
		public String getComponentName() {
			return "ntm_fluid_gauge";
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getTransfer(Context context, Arguments args) {
			return new Object[] {deltaTick, deltaSecond};
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getFluid(Context context, Arguments args) {
			return new Object[] {getType().getName()};
		}

		@Callback(direct = true)
		@Optional.Method(modid = "OpenComputers")
		public Object[] getInfo(Context context, Arguments args) {
			return new Object[] {deltaTick, deltaSecond, getType().getName(), xCoord, yCoord, zCoord};
		}
	}
}
