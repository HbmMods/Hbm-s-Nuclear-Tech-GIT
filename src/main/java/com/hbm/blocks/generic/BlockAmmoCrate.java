package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAmmoCrate extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;

	public BlockAmmoCrate(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_ammo_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":crate_ammo_bottom");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_ammo_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? this.iconBottom : (side == 1 ? this.iconTop : this.blockIcon);
	}
	
	Random rand = new Random();
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    	
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        ret.add(new ItemStack(ModItems.cap_nuka, 12 + rand.nextInt(21)));
        ret.add(new ItemStack(ModItems.syringe_metal_stimpak, 1 + rand.nextInt(3)));
        
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_22lr, 16 + rand.nextInt(17)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_9mm, 6 + rand.nextInt(13)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_12gauge, 6 + rand.nextInt(4)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_20gauge, 3 + rand.nextInt(4)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.gun_revolver_ammo, 10 + rand.nextInt(11)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.gun_revolver_iron_ammo, 12 + rand.nextInt(15)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_50bmg, 2 + rand.nextInt(7)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_rocket, 1));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_grenade, 1 + rand.nextInt(2)));

        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_12gauge_incendiary, 3));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_20gauge_incendiary, 3));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_20gauge_caustic, 3));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_20gauge_flechette, 3));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_9mm_ap, 7));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_rocket_incendiary, 1));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_rocket_sleek, 1));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_grenade_he, 1));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_grenade_incendiary, 1));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_grenade_sleek, 1));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.syringe_metal_super, 2));
        
        return ret;
    }
}
