package com.hbm.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemToolAbility;
import com.hbm.main.MainRegistry;
import com.hbm.util.EnchantmentUtil;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class ToolAbility {
	
	public abstract void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool);
	public abstract String getName();
	public abstract String getFullName();
	
	public static class RecursionAbility extends ToolAbility {
		
		int radius;
		
		public RecursionAbility(int radius) {
			this.radius = radius;
		}
		
		private Set<ThreeInts> pos = new HashSet();

		@Override
		public void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool) {
			
			Block b = world.getBlock(x, y, z);

			if(b == Blocks.stone && !MainRegistry.recursiveStone)
				return;
			if(b == Blocks.netherrack && !MainRegistry.recursiveNetherrack)
				return;
			
			List<Integer> indices = Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 5});
			Collections.shuffle(indices);
			
			pos.clear();
			
			for(Integer i : indices) {
				switch(i) {
				case 0: breakExtra(world, x + 1, y, z, x, y, z, player, tool, 0); break;
				case 1: breakExtra(world, x - 1, y, z, x, y, z, player, tool, 0); break;
				case 2: breakExtra(world, x, y + 1, z, x, y, z, player, tool, 0); break;
				case 3: breakExtra(world, x, y - 1, z, x, y, z, player, tool, 0); break;
				case 4: breakExtra(world, x, y, z + 1, x, y, z, player, tool, 0); break;
				case 5: breakExtra(world, x, y, z - 1, x, y, z, player, tool, 0); break;
				}
			}
		}
		
		private void breakExtra(World world, int x, int y, int z, int refX, int refY, int refZ, EntityPlayer player, ItemToolAbility tool, int depth) {
			
			if(pos.contains(new ThreeInts(x, y, z)))
				return;
			
			depth += 1;
			
			if(depth > MainRegistry.recursionDepth)
				return;
			
			pos.add(new ThreeInts(x, y, z));
			
			//don't lose the ref block just yet
			if(x == refX && y == refY && z == refZ)
				return;
			
			if(Vec3.createVectorHelper(x - refX, y - refY, z - refZ).lengthVector() > radius)
				return;
			
			Block b = world.getBlock(x, y, z);
			Block ref = world.getBlock(refX, refY, refZ);
			int meta = world.getBlockMetadata(x, y, z);
			int refMeta = world.getBlockMetadata(refX, refY, refZ);
			
			if(b != ref)
				return;
			
			if(meta != refMeta)
				return;
			
			if(player.getHeldItem() == null)
				return;
			
			tool.breakExtraBlock(world, x, y, z, player, refX, refY, refZ);
			
			List<Integer> indices = Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 5});
			Collections.shuffle(indices);
			
			for(Integer i : indices) {
				switch(i) {
				case 0: breakExtra(world, x + 1, y, z, refX, refY, refZ, player, tool, depth); break;
				case 1: breakExtra(world, x - 1, y, z, refX, refY, refZ, player, tool, depth); break;
				case 2: breakExtra(world, x, y + 1, z, refX, refY, refZ, player, tool, depth); break;
				case 3: breakExtra(world, x, y - 1, z, refX, refY, refZ, player, tool, depth); break;
				case 4: breakExtra(world, x, y, z + 1, refX, refY, refZ, player, tool, depth); break;
				case 5: breakExtra(world, x, y, z - 1, refX, refY, refZ, player, tool, depth); break;
				}
			}
		}

		@Override
		public String getName() {
			return "tool.ability.recursion";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + radius + ")";
		}
		
	}

	public static class HammerAbility extends ToolAbility {

		int range;
		
		public HammerAbility(int range) {
			this.range = range;
		}
		
		@Override
		public void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool) {
			
			for(int a = x - range; a <= x + range; a++) {
				for(int b = y - range; b <= y + range; b++) {
					for(int c = z - range; c <= z + range; c++) {
						
						if(a == x && b == y && c == z)
							continue;
						
						tool.breakExtraBlock(world, a, b ,c, player, x, y, z);
					}
				}
			}
		}

		@Override
		public String getName() {
			return "tool.ability.hammer";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + range + ")";
		}
	}

	public static class SilkAbility extends ToolAbility {

		@Override
		public void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool) {
			
			//if the tool is already enchanted, do nothing
			if(EnchantmentHelper.getSilkTouchModifier(player) || player.getHeldItem() == null)
				return;
			
			//add enchantment
			ItemStack stack = player.getHeldItem();
			
			EnchantmentUtil.addEnchantment(stack, Enchantment.silkTouch, 1);
			block.harvestBlock(world, player, x, y, z, meta);
			EnchantmentUtil.removeEnchantment(stack, Enchantment.silkTouch);
			
			world.setBlockToAir(x, y, z);
		}

		@Override
		public String getName() {
			return "tool.ability.silktouch";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName());
		}
	}

	public static class LuckAbility extends ToolAbility {
		
		int luck;
		
		public LuckAbility(int luck) {
			this.luck = luck;
		}

		@Override
		public void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool) {
			
			//if the tool is already enchanted, do nothing
			if(EnchantmentHelper.getFortuneModifier(player) > 0 || player.getHeldItem() == null)
				return;
			
			//add enchantment
			ItemStack stack = player.getHeldItem();
			
			EnchantmentUtil.addEnchantment(stack, Enchantment.fortune, luck);
			block.harvestBlock(world, player, x, y, z, meta);
			EnchantmentUtil.removeEnchantment(stack, Enchantment.fortune);
			
			world.setBlockToAir(x, y, z);
		}

		@Override
		public String getName() {
			return "tool.ability.luck";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + luck + ")";
		}
	}

	public static class SmelterAbility extends ToolAbility {

		@Override
		public void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool) {
			
			//a band-aid on a gaping wound
			if(block == Blocks.lit_redstone_ore)
				block = Blocks.redstone_ore;
			
			ItemStack stack = new ItemStack(block, 1, meta);
			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(stack);
			
			if(result != null) {
				world.setBlockToAir(x, y, z);
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, result.copy()));
			}
		}

		@Override
		public String getName() {
			return "tool.ability.smelter";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName());
		}
	}
	
	public static class ShredderAbility extends ToolAbility {

		@Override
		public void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool) {
			
			//a band-aid on a gaping wound
			if(block == Blocks.lit_redstone_ore)
				block = Blocks.redstone_ore;
			
			ItemStack stack = new ItemStack(block, 1, meta);
			ItemStack result = ShredderRecipes.getShredderResult(stack);
			
			if(result != null && result.getItem() != ModItems.scrap) {
				world.setBlockToAir(x, y, z);
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, result.copy()));
			}
		}

		@Override
		public String getName() {
			return "tool.ability.shredder";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName());
		}
	}
	
	public static class CentrifugeAbility extends ToolAbility {

		@Override
		public void onDig(World world, int x, int y, int z, EntityPlayer player, Block block, int meta, ItemToolAbility tool) {
			
			//a band-aid on a gaping wound
			if(block == Blocks.lit_redstone_ore)
				block = Blocks.redstone_ore;
			
			ItemStack stack = new ItemStack(block, 1, meta);
			ItemStack[] result = CentrifugeRecipes.getOutput(stack);
			
			if(result != null) {
				world.setBlockToAir(x, y, z);
				
				for(ItemStack st : result) {
					if(st != null)
						world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, st.copy()));
				}
			}
		}

		@Override
		public String getName() {
			return "tool.ability.centrifuge";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName());
		}
	}
}