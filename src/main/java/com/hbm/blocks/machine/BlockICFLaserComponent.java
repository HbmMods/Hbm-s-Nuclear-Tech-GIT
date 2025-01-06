package com.hbm.blocks.machine;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockICFLaserComponent extends BlockEnumMulti {

	protected IIcon[] iconsTop;

	public BlockICFLaserComponent() {
		super(Material.iron, EnumICFPart.class, true, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {

		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		this.iconsTop = new IIcon[enums.length];

		this.icons[0] = this.iconsTop[0] = reg.registerIcon(RefStrings.MODID + ":icf_casing");
		this.icons[1] = this.iconsTop[1] = reg.registerIcon(RefStrings.MODID + ":icf_port");
		this.icons[2] = this.iconsTop[2] = reg.registerIcon(RefStrings.MODID + ":icf_cell");
		this.icons[3] = this.iconsTop[3] = reg.registerIcon(RefStrings.MODID + ":icf_emitter");
		this.icons[4] = reg.registerIcon(RefStrings.MODID + ":icf_capacitor_side");
		this.icons[5] = reg.registerIcon(RefStrings.MODID + ":icf_turbocharger");
		this.iconsTop[4] = this.iconsTop[5] = reg.registerIcon(RefStrings.MODID + ":icf_capacitor_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? this.iconsTop[meta % this.iconsTop.length] : this.icons[meta % this.icons.length];
	}

	@Override
	public int getSubCount() {
		return EnumICFPart.values().length;
	}

	public static enum EnumICFPart {
		CASING,
		PORT,
		CELL,
		EMITTER,
		CAPACITOR,
		TURBO
	}
}
