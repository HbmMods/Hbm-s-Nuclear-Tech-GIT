package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.IBlockMulti;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockAbsorber extends BlockEnumMulti implements IBlockMulti {

    // Enum for tiers they are in order of meta data, 0, 1, 2, 3 for Base, Red, Green, Pink
    public static enum EnumAbsorberTier {
        
        BASE("Radiation Absorber", 2.5F, "absorber"),
        RED("Enhanced Radiation Absorber", 10F, "absorber_red"),
        GREEN("Advanced Radiation Absorber", 100F, "absorber_green"),
        PINK("Elite Radiation Absorber", 10000F, "absorber_pink");
        
        public final String displayName;
        public final float absorbAmount;
        public final String textureName;

        private EnumAbsorberTier(String name, float absorb, String texture) {
            this.displayName = name;
            this.absorbAmount = absorb;
            this.textureName = texture;
        }
    }

    private IIcon[] icons;

    public BlockAbsorber(Material mat) {
        super(mat, EnumAbsorberTier.class, true, true);
        this.setTickRandomly(true);
        this.setBlockName("rad_absorber");
    }

    //More Enum stuff I copied from the other enum blocks, I have no idea how this really works tbh

    public EnumAbsorberTier getTier(int meta) {
        return EnumAbsorberTier.values()[rectify(meta)];
    }

    @Override
    public int getSubCount() {
        return EnumAbsorberTier.values().length;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    //HARDEST PART WORK GODDAMMIT WORK

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        EnumAbsorberTier tier = getTier(stack.getItemDamage());
        return "tile.rad_absorber." + tier.name().toLowerCase();
    }

    @Override
    public String getOverrideDisplayName(ItemStack stack) {
        return StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name");
    }

    //Creative tab && textures

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < EnumAbsorberTier.values().length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(net.minecraft.client.renderer.texture.IIconRegister reg) {
        icons = new IIcon[EnumAbsorberTier.values().length];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = reg.registerIcon(RefStrings.MODID + ":" + EnumAbsorberTier.values()[i].textureName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[rectify(meta)];
    }

   //All that rad math shit that was on there already, did not touch this -Wolf

    @Override
    public int tickRate(World world) {
        return 10;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        EnumAbsorberTier tier = getTier(world.getBlockMetadata(x, y, z));
        ChunkRadiationManager.proxy.decrementRad(world, x, y, z, tier.absorbAmount);
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }

	//Block wanted these or no work, probably a better way to do it but I dont know it.

    @Override
    public BlockAbsorber setBlockName(String name) {
        super.setBlockName(name);
        return this;
    }

    @Override
    public BlockAbsorber setHardness(float hardness) {
        super.setHardness(hardness);
        return this;
    }

    @Override
    public BlockAbsorber setResistance(float resistance) {
        super.setResistance(resistance);
        return this;
    }

    @Override
    public BlockAbsorber setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
