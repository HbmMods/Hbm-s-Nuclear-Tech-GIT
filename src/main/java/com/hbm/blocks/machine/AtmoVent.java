package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.config.WorldConfig;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityAtmoVent;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBurner;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.PlanetaryTraitUtil;
import com.hbm.util.PlanetaryTraitUtil.Hospitality;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

public class AtmoVent extends BlockDummyable implements ILookOverlay {

	public AtmoVent(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {

		if(meta >= 12)
			return new TileEntityAtmoVent();

		if(meta >= 8)
			return new TileEntityProxyCombo(false, true, true);

		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 1, 0, 0, 1 };
	}
	

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		ForgeDirection dr2 = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z - dir.offsetZ - dr2.offsetZ);
		this.makeExtra(world, x, y, z - dir.offsetZ - dr2.offsetZ);
		this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityAtmoVent))
			return;

		TileEntityAtmoVent tower = (TileEntityAtmoVent) te;

		List<String> text = new ArrayList();
		if(PlanetaryTraitUtil.isDimensionWithTrait(world, Hospitality.OXYNEG)) {
			text.add(((EnumChatFormatting.RED + "ERROR: ")) + EnumChatFormatting.RESET + I18nUtil.resolveKey("CANNOT COLLECT IN VACUUM"));
		}
		else {
			text.add((tower.power < tower.getMaxPower() / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Power: " + BobMathUtil.getShortNumber(tower.power) + "HE");
			text.add(((EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + tower.tanks.getTankType().getName().toLowerCase()) + ": " + tower.tanks.getFill() + "/" + tower.tanks.getMaxFill() + "mB");
	
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}