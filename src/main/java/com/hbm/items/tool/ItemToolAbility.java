package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.hbm.inventory.gui.GUIScreenToolAbility;
import com.hbm.items.IItemControlReceiver;
import com.hbm.handler.HbmKeybinds;
import com.hbm.blocks.ModBlocks;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ability.AvailableAbilities;
import com.hbm.handler.ability.IBaseAbility;
import com.hbm.handler.ability.IToolAreaAbility;
import com.hbm.handler.ability.IToolHarvestAbility;
import com.hbm.handler.ability.ToolPreset;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.tileentity.IGUIProvider;

import api.hbm.item.IDepthRockTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.world.BlockEvent;

public class ItemToolAbility extends ItemTool implements IDepthRockTool, IGUIProvider, IItemControlReceiver {
	
	protected boolean isShears = false;
	protected EnumToolType toolType;
	protected EnumRarity rarity = EnumRarity.common;
	//was there a reason for this to be private?
	protected float damage;
	protected double movement;
	protected AvailableAbilities availableAbilities = new AvailableAbilities().addToolAbilities();
	protected boolean rockBreaker = false;

	public static enum EnumToolType {
		
		PICKAXE(
				Sets.newHashSet(new Material[] { Material.iron, Material.anvil, Material.rock, Material.glass }),
				Sets.newHashSet(new Block[] { Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail })
		),
		AXE(
				Sets.newHashSet(new Material[] { Material.wood, Material.plants, Material.vine }),
				Sets.newHashSet(new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin })
		),
		SHOVEL(
				Sets.newHashSet(new Material[] { Material.clay, Material.sand, Material.ground, Material.snow, Material.craftedSnow }),
				Sets.newHashSet(new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium })
		),
		MINER(
				Sets.newHashSet(new Material[] { Material.grass, Material.iron, Material.anvil, Material.glass, Material.rock, Material.clay, Material.sand, Material.ground, Material.snow, Material.craftedSnow })
		);

		private EnumToolType(Set<Material> materials) {
			this.materials = materials;
		}

		private EnumToolType(Set<Material> materials, Set<Block> blocks) {
			this.materials = materials;
			this.blocks = blocks;
		}

		public Set<Material> materials = new HashSet();
		public Set<Block> blocks = new HashSet();
	}
	
	public ItemToolAbility setShears() {
		this.isShears = true;
		return this;
	}

	public ItemToolAbility(float damage, double movement, ToolMaterial material, EnumToolType type) {
		super(0, material, type.blocks);
		this.damage = damage;
		this.movement = movement;
		this.toolType = type;
		
		// hacky workaround, might be good to rethink this entire system
		if(type == EnumToolType.MINER) {
			this.setHarvestLevel("pickaxe", material.getHarvestLevel());
			this.setHarvestLevel("shovel", material.getHarvestLevel());
		} else {
			this.setHarvestLevel(type.toString().toLowerCase(Locale.US), material.getHarvestLevel());
		}
	}

	public ItemToolAbility addAbility(IBaseAbility ability, int level) {
		this.availableAbilities.addAbility(ability, level);

		return this;
	}

	public ItemToolAbility setDepthRockBreaker() {
		this.rockBreaker = true;
		return this;
	}

	// <insert obvious Rarity joke here>
	public ItemToolAbility setRarity(EnumRarity rarity) {
		this.rarity = rarity;
		return this;
	}

	public EnumRarity getRarity(ItemStack stack) {
		return this.rarity != EnumRarity.common ? this.rarity : super.getRarity(stack);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker) {

		if(!attacker.worldObj.isRemote && attacker instanceof EntityPlayer && canOperate(stack)) {

			this.availableAbilities.getWeaponAbilities().forEach((ability, level) -> {
				ability.onHit(level, attacker.worldObj, (EntityPlayer) attacker, victim, this);
			});
		}

		stack.damageItem(1, attacker);

		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {

		World world = player.worldObj;
		Block block = world.getBlock(x, y, z);

		if(!world.isRemote && (canHarvestBlock(block, stack) || canShearBlock(block, stack, world, x, y, z)) && canOperate(stack)) {
			Configuration config = getConfiguration(stack);
			ToolPreset preset = config.getActivePreset();

			preset.harvestAbility.preHarvestAll(preset.harvestAbilityLevel, world, player);

			boolean skipRef = preset.areaAbility.onDig(preset.areaAbilityLevel, world, x, y, z, player, this);
		
			if (!skipRef) {
				breakExtraBlock(world, x, y, z, player, x, y, z);
			}

			preset.harvestAbility.postHarvestAll(preset.harvestAbilityLevel, world, player);

			return true;
		}

		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase player) {
		return super.onBlockDestroyed(stack, world, block, x, y, z, player);
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {

		if(!canOperate(stack))
			return 1;

		if(toolType == null)
			return super.getDigSpeed(stack, block, meta);

		if(toolType.blocks.contains(block) || toolType.materials.contains(block.getMaterial()))
			return this.efficiencyOnProperMaterial;

		return super.getDigSpeed(stack, block, meta);
	}

	public boolean canOperate(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {

		if(!canOperate(stack))
			return false;

		if(this.getConfiguration(stack).getActivePreset().harvestAbility == IToolHarvestAbility.SILK)
			return true;

		return getDigSpeed(stack, block, 0) > 1;
	}

	@Override
	public boolean canBreakRock(World world, EntityPlayer player, ItemStack tool, Block block, int x, int y, int z) {
		return canOperate(tool) && this.rockBreaker;
	}

	public boolean canShearBlock(Block block, ItemStack stack, World world, int x, int y, int z) {
		return this.isShears(stack) && block instanceof IShearable && ((IShearable) block).isShearable(stack, world, x, y, z);
	}

	public boolean isShears(ItemStack stack) {
		return this.isShears;
	}

	@Override
	public Multimap getItemAttributeModifiers() {

		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double) this.damage, 0));
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", movement, 1));
		return multimap;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return stack.isItemEnchanted() || !getConfiguration(stack).getActivePreset().isNone();
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		availableAbilities.addInformation(list);

		if(this.rockBreaker) {
			list.add("");
			list.add(EnumChatFormatting.RED + "Can break depth rock!");
		}
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!canOperate(stack))
			return super.onItemRightClick(stack, world, player);

		if(HbmPlayerProps.getData(player).getKeyPressed(HbmKeybinds.EnumKeybind.TOOL_ALT)) {
			if(world.isRemote) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
			return stack;
		}
		
		Configuration config = getConfiguration(stack);

		if(config.presets.size() < 2 || world.isRemote)
			return super.onItemRightClick(stack, world, player);


		if(player.isSneaking()) {
			config.currentPreset = 0;
		} else {
			config.currentPreset = (config.currentPreset + 1) % config.presets.size();
		}

		setConfiguration(stack, config);

		PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(config.getActivePreset().getMessage(), MainRegistry.proxy.ID_TOOLABILITY), (EntityPlayerMP) player);

		world.playSoundAtEntity(player, "random.orb", 0.25F, config.getActivePreset().isNone() ? 0.75F : 1.25F);
		
		return stack;
	}

	public void breakExtraBlock(World world, int x, int y, int z, EntityPlayer playerEntity, int refX, int refY, int refZ) {

		if(world.isAirBlock(x, y, z))
			return;

		if(!(playerEntity instanceof EntityPlayerMP))
			return;

		EntityPlayerMP player = (EntityPlayerMP) playerEntity;
		ItemStack stack = player.getHeldItem();

		if (stack == null) {
			return;
		}

		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if(!(canHarvestBlock(block, stack) || canShearBlock(block, stack, world, x, y, z)) || block == Blocks.bedrock || block == ModBlocks.stone_keyhole)
			return;

		Block refBlock = world.getBlock(refX, refY, refZ);
		float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
		float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

		if(!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f || refBlock.getBlockHardness(world, refX, refY, refZ) < 0)
			return;

		BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
		if(event.isCanceled())
			return;

		Configuration config = getConfiguration(stack);
		ToolPreset preset = config.getActivePreset();

		preset.harvestAbility.onHarvestBlock(preset.harvestAbilityLevel, world, x, y, z, player, block, meta);
	}

	/** Assumes a canShearBlock check has passed, will most likely crash otherwise! */
	public static void shearBlock(World world, int x, int y, int z, Block block, EntityPlayer player) {
		
		ItemStack held = player.getHeldItem();

		IShearable target = (IShearable) block;
		if(target.isShearable(held, player.worldObj, x, y, z)) {
			ArrayList<ItemStack> drops = target.onSheared(held, player.worldObj, x, y, z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, held));
			Random rand = new Random();

			for(ItemStack stack : drops) {
				float f = 0.7F;
				double d = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(player.worldObj, (double) x + d, (double) y + d1, (double) z + d2, stack);
				entityitem.delayBeforeCanPickup = 10;
				player.worldObj.spawnEntityInWorld(entityitem);
			}

			held.damageItem(1, player);
			player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);
		}
	}

	public static void standardDigPost(World world, int x, int y, int z, EntityPlayerMP player) {

		Block block = world.getBlock(x, y, z);
		int l = world.getBlockMetadata(x, y, z);
		world.playAuxSFXAtEntity(player, 2001, x, y, z, Block.getIdFromBlock(block) + (world.getBlockMetadata(x, y, z) << 12));
		boolean removedByPlayer = false;

		if(player.capabilities.isCreativeMode) {
			removedByPlayer = removeBlock(world, x, y, z, false, player);
			player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
		} else {
			ItemStack itemstack = player.getCurrentEquippedItem();
			boolean canHarvest = block.canHarvestBlock(player, l);

			removedByPlayer = removeBlock(world, x, y, z, canHarvest, player);

			if(itemstack != null) {
				itemstack.func_150999_a(world, block, x, y, z, player);

				if(itemstack.stackSize == 0) {
					player.destroyCurrentEquippedItem();
				}
			}
			
			if(removedByPlayer && canHarvest) {
				block.harvestBlock(world, player, x, y, z, l);
			}
		}

		// Why was this commented out?
		// Drop experience
		// if (!player.capabilities.isCreativeMode && flag && event != null) {
		// 	block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
		// }
	}

	public static boolean removeBlock(World world, int x, int y, int z, boolean canHarvest, EntityPlayerMP player) {
		Block block = world.getBlock(x, y, z);
		int l = world.getBlockMetadata(x, y, z);
		block.onBlockHarvested(world, x, y, z, l, player);
		boolean flag = block.removedByPlayer(world, player, x, y, z, canHarvest);

		if(flag) {
			block.onBlockDestroyedByPlayer(world, x, y, z, l);
		}

		return flag;
	}

	public static class Configuration {
		public List<ToolPreset> presets;
		public int currentPreset;

		public Configuration() {
			this.presets = null;
			this.currentPreset = 0;
		}

		public Configuration(List<ToolPreset> presets, int currentPreset) {
			this.presets = presets;
			this.currentPreset = currentPreset;
		}

		public void writeToNBT(NBTTagCompound nbt) {
			nbt.setInteger("ability", currentPreset);

			NBTTagList nbtPresets = new NBTTagList();

			for(ToolPreset preset : presets) {
				NBTTagCompound nbtPreset = new NBTTagCompound();
				preset.writeToNBT(nbtPreset);
				nbtPresets.appendTag(nbtPreset);
			}

			nbt.setTag("abilityPresets", nbtPresets);
		}

		public void readFromNBT(NBTTagCompound nbt) {
			currentPreset = nbt.getInteger("ability");

			NBTTagList nbtPresets = nbt.getTagList("abilityPresets", 10);
			int numPresets = Math.min(nbtPresets.tagCount(), 99);
			
			presets = new ArrayList<ToolPreset>(numPresets);
			
			for(int i = 0; i < numPresets; i++) {
				NBTTagCompound nbtPreset = nbtPresets.getCompoundTagAt(i);
				ToolPreset preset = new ToolPreset();
				preset.readFromNBT(nbtPreset);
				presets.add(preset);
			}

			currentPreset = Math.max(0, Math.min(currentPreset, presets.size() - 1));
		}

		public void reset(AvailableAbilities availableAbilities) {
			currentPreset = 0;

			presets = new ArrayList<ToolPreset>(availableAbilities.size());
			presets.add(new ToolPreset());

			availableAbilities.getToolAreaAbilities().forEach((ability, level) -> {
				if (ability == IToolAreaAbility.NONE)
					return;
				presets.add(new ToolPreset(ability, level, IToolHarvestAbility.NONE, 0));
			});

			availableAbilities.getToolHarvestAbilities().forEach((ability, level) -> {
				if (ability == IToolHarvestAbility.NONE)
					return;
				presets.add(new ToolPreset(IToolAreaAbility.NONE, 0, ability, level));
			});

			presets.sort(
				Comparator
					.comparing((ToolPreset p) -> p.harvestAbility)
					.thenComparingInt(p -> p.harvestAbilityLevel)
					.thenComparing(p -> p.areaAbility)
					.thenComparingInt(p -> p.areaAbilityLevel)
			);
		}

		public void restrictTo(AvailableAbilities availableAbilities) {
			for (ToolPreset preset : presets) {
				preset.restrictTo(availableAbilities);
			}
		}

		public ToolPreset getActivePreset() {
			return presets.get(currentPreset);
		}
	}

	public Configuration getConfiguration(ItemStack stack) {
		Configuration config = new Configuration();

		if(stack == null || !stack.hasTagCompound() || !stack.stackTagCompound.hasKey("ability") || !stack.stackTagCompound.hasKey("abilityPresets")) {
			config.reset(availableAbilities);
			return config;
		}

		config.readFromNBT(stack.stackTagCompound);
		config.restrictTo(availableAbilities);
		return config;
	}

	public void setConfiguration(ItemStack stack, Configuration config) {
		if (stack == null) {
			return;
		}

		if (!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		config.writeToNBT(stack.stackTagCompound);
	}

	@Override
	public void receiveControl(ItemStack stack, NBTTagCompound data) {
		Configuration config = new Configuration();
		config.readFromNBT(data);
		config.restrictTo(availableAbilities);
		setConfiguration(stack, config);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenToolAbility(this.availableAbilities);
	}
}
