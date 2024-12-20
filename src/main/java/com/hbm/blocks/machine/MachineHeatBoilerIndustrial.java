package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeatBoilerIndustrial;
import com.hbm.util.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MachineHeatBoilerIndustrial extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public MachineHeatBoilerIndustrial() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12) return new TileEntityHeatBoilerIndustrial();
		if(meta >= extra) return new TileEntityProxyCombo().fluid();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(!world.isRemote && !player.isSneaking()) {

			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
				int[] pos = this.findCore(world, x, y, z);

				if(pos == null)
					return false;

				TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

				if(!(te instanceof TileEntityHeatBoilerIndustrial))
					return false;

				TileEntityHeatBoilerIndustrial boiler = (TileEntityHeatBoilerIndustrial) te;

				FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, pos[0], pos[1], pos[2], player.getHeldItem());

				if(type.hasTrait(FT_Heatable.class) && type.getTrait(FT_Heatable.class).getEfficiency(HeatingType.BOILER) > 0) {
					boiler.tanks[0].setTankType(type);
					boiler.markDirty();
					player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
				}
				return true;
			}
			return false;

		} else {
			return true;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		this.makeExtra(world, x - dir.offsetX + 1, y, z - dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX - 1, y, z - dir.offsetZ);
		this.makeExtra(world, x - dir.offsetX, y, z - dir.offsetZ + 1);
		this.makeExtra(world, x - dir.offsetX, y, z - dir.offsetZ - 1);
		this.makeExtra(world, x - dir.offsetX, y + 4, z - dir.offsetZ);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityHeatBoilerIndustrial))
			return;

		TileEntityHeatBoilerIndustrial boiler = (TileEntityHeatBoilerIndustrial) te;

		List<String> text = new ArrayList();
		text.add(String.format(Locale.US, "%,d", boiler.heat) + "TU");
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + boiler.tanks[0].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", boiler.tanks[0].getFill()) + " / " + String.format(Locale.US, "%,d", boiler.tanks[0].getMaxFill()) + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + boiler.tanks[1].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", boiler.tanks[1].getFill()) + " / " + String.format(Locale.US, "%,d", boiler.tanks[1].getMaxFill()) + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
