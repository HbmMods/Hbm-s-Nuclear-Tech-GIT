package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.pile.TileEntityPileSource;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGraphiteSource extends BlockGraphiteDrilledTE implements IToolable {

	@Override
	public TileEntity createNewTileEntity(World world, int mets) {
		return new TileEntityPileSource();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		if(this == ModBlocks.block_graphite_plutonium)
			this.blockIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_plutonium_aluminum");
		else if(this == ModBlocks.block_graphite_source)
			this.blockIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_source_aluminum");
	}
	
	@Override
	protected Item getInsertedItem() {
		return this == ModBlocks.block_graphite_plutonium ? ModItems.pile_rod_plutonium : ModItems.pile_rod_source;
	}
}
