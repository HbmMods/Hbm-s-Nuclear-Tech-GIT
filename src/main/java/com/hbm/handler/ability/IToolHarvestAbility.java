package com.hbm.handler.ability;

import java.util.List;

import com.hbm.config.ToolConfig;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.ShredderRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemToolAbility;
import com.hbm.util.EnchantmentUtil;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

public interface IToolHarvestAbility extends IBaseAbility {
	
	public default void preHarvestAll(int level, World world, EntityPlayer player) { }
	public default void postHarvestAll(int level, World world, EntityPlayer player) { }

	// You must call harvestBlock to actually break the block.
	// If you don't, visual glitches ensue
	public default void onHarvestBlock(int level, World world, int x, int y, int z, EntityPlayer player, Block block, int meta) {
		harvestBlock(false, world, x, y, z, player);
	}

	public static void harvestBlock(boolean skipDefaultDrops, World world, int x, int y, int z, EntityPlayer player) {
		if(skipDefaultDrops) {
			// Emulate the block breaking without drops
			world.setBlockToAir(x, y, z);
			player.getHeldItem().damageItem(1, player);
		} else if(player instanceof EntityPlayerMP) {
			// Break the block conventionally
			ItemToolAbility.standardDigPost(world, x, y, z, (EntityPlayerMP) player);
		}
	}

	public final static int SORT_ORDER_BASE = 100;

	// region handlers
	public static final IToolHarvestAbility NONE = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 0;
		}
	};

	public static final IToolHarvestAbility SILK = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "tool.ability.silktouch";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilitySilk;
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 1;
		}

		@Override
		public void preHarvestAll(int level, World world, EntityPlayer player) {
			ItemStack stack = player.getHeldItem();
			EnchantmentUtil.addEnchantment(stack, Enchantment.silkTouch, 1);
		}

		@Override
		public void postHarvestAll(int level, World world, EntityPlayer player) {
			// ToC-ToU mismatch should be impossible
			// because both calls happen on the same tick.
			// Even if can be forced somehow, the player doesn't gain any
			// benefit from it.
			ItemStack stack = player.getHeldItem();
			EnchantmentUtil.removeEnchantment(stack, Enchantment.silkTouch);
		}
	};

	public static final IToolHarvestAbility LUCK = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "tool.ability.luck";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityLuck;
		}

		public final int[] powerAtLevel = { 1, 2, 3, 4, 5, 9 };

		@Override
		public int levels() {
			return powerAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + powerAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 2;
		}

		@Override
		public void preHarvestAll(int level, World world, EntityPlayer player) {
			ItemStack stack = player.getHeldItem();
			EnchantmentUtil.addEnchantment(stack, Enchantment.fortune, powerAtLevel[level]);
		}

		@Override
		public void postHarvestAll(int level, World world, EntityPlayer player) {
			// ToC-ToU mismatch should be impossible
			// because both calls happen on the same tick.
			// Even if can be forced somehow, the player doesn't gain any
			// benefit from it.
			ItemStack stack = player.getHeldItem();
			EnchantmentUtil.removeEnchantment(stack, Enchantment.fortune);
		}
	};

	public static final IToolHarvestAbility SMELTER = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "tool.ability.smelter";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityFurnace;
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 3;
		}

		@Override
		public void onHarvestBlock(int level, World world, int x, int y, int z, EntityPlayer player, Block block, int meta) {
			List<ItemStack> drops = block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);

			boolean doesSmelt = false;

			for(int i = 0; i < drops.size(); i++) {
				ItemStack stack = drops.get(i).copy();
				ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(stack);

				if(result != null) {
					result = result.copy();
					result.stackSize *= stack.stackSize;
					drops.set(i, result);
					doesSmelt = true;
				}
			}

			harvestBlock(doesSmelt, world, x, y, z, player);

			if(doesSmelt) {
				for(ItemStack stack : drops) {
					world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack.copy()));
				}
			}
		}
	};

	public static final IToolHarvestAbility SHREDDER = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "tool.ability.shredder";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityShredder;
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 4;
		}

		@Override
		public void onHarvestBlock(int level, World world, int x, int y, int z, EntityPlayer player, Block block, int meta) {
			// a band-aid on a gaping wound
			if(block == Blocks.lit_redstone_ore)
				block = Blocks.redstone_ore;

			ItemStack stack = new ItemStack(block, 1, meta);
			ItemStack result = ShredderRecipes.getShredderResult(stack);

			boolean doesShred = result != null && result.getItem() != ModItems.scrap;

			harvestBlock(doesShred, world, x, y, z, player);

			if(doesShred) {
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, result.copy()));
			}
		}
	};

	public static final IToolHarvestAbility CENTRIFUGE = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "tool.ability.centrifuge";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityCentrifuge;
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 5;
		}

		@Override
		public void onHarvestBlock(int level, World world, int x, int y, int z, EntityPlayer player, Block block, int meta) {
			// a band-aid on a gaping wound
			if(block == Blocks.lit_redstone_ore)
				block = Blocks.redstone_ore;

			ItemStack stack = new ItemStack(block, 1, meta);
			ItemStack[] result = CentrifugeRecipes.getOutput(stack);

			boolean doesCentrifuge = result != null;

			harvestBlock(doesCentrifuge, world, x, y, z, player);

			if(doesCentrifuge) {
				for(ItemStack st : result) {
					if(st != null) {
						world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, st.copy()));
					}
				}
			}
		}
	};

	public static final IToolHarvestAbility CRYSTALLIZER = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "tool.ability.crystallizer";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityCrystallizer;
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 6;
		}

		@Override
		public void onHarvestBlock(int level, World world, int x, int y, int z, EntityPlayer player, Block block, int meta) {
			// a band-aid on a gaping wound
			if(block == Blocks.lit_redstone_ore)
				block = Blocks.redstone_ore;

			ItemStack stack = new ItemStack(block, 1, meta);
			CrystallizerRecipe result = CrystallizerRecipes.getOutput(stack, Fluids.PEROXIDE);

			boolean doesCrystallize = result != null;

			harvestBlock(doesCrystallize, world, x, y, z, player);

			if(doesCrystallize) {
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, result.output.copy()));
			}
		}
	};

	public static final IToolHarvestAbility MERCURY = new IToolHarvestAbility() {
		@Override
		public String getName() {
			return "tool.ability.mercury";
		}

		@Override
		public boolean isAllowed() {
			return ToolConfig.abilityMercury;
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 7;
		}

		@Override
		public void onHarvestBlock(int level, World world, int x, int y, int z, EntityPlayer player, Block block, int meta) {
			// a band-aid on a gaping wound
			if(block == Blocks.lit_redstone_ore)
				block = Blocks.redstone_ore;

			int mercury = 0;

			if(block == Blocks.redstone_ore)
				mercury = player.getRNG().nextInt(5) + 4;
			if(block == Blocks.redstone_block)
				mercury = player.getRNG().nextInt(7) + 8;

			boolean doesConvert = mercury > 0;

			harvestBlock(doesConvert, world, x, y, z, player);

			if(doesConvert) {
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.ingot_mercury, mercury)));
			}
		}
	};
	// endregion handlers

	static final IToolHarvestAbility[] abilities = { NONE, SILK, LUCK, SMELTER, SHREDDER, CENTRIFUGE, CRYSTALLIZER, MERCURY };

	static IToolHarvestAbility getByName(String name) {
		for(IToolHarvestAbility ability : abilities) {
			if(ability.getName().equals(name))
				return ability;
		}

		return NONE;
	}
}
