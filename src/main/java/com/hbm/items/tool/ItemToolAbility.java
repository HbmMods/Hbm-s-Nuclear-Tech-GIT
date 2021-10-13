package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.hbm.handler.ToolAbility;
import com.hbm.handler.ToolAbility.*;
import com.hbm.handler.WeaponAbility;

import api.hbm.item.IDepthRockTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class ItemToolAbility extends ItemTool implements IItemAbility, IDepthRockTool {
	
	private EnumToolType toolType;
	private EnumRarity rarity = EnumRarity.common;
	//was there a reason for this to be private?
    protected float damage;
    protected double movement;
    private List<ToolAbility> breakAbility = new ArrayList() {{ add(null); }};
    private List<WeaponAbility> hitAbility = new ArrayList();
	
	public static enum EnumToolType {
		
		PICKAXE(
				Sets.newHashSet(new Material[] { Material.iron, Material.anvil, Material.rock }),
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
				Sets.newHashSet(new Material[] { Material.grass, Material.iron, Material.anvil, Material.rock, Material.clay, Material.sand, Material.ground, Material.snow, Material.craftedSnow })
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

	public ItemToolAbility(float damage, double movement, ToolMaterial material, EnumToolType type) {
		super(0, material, type.blocks);
		this.damage = damage;
		this.movement = movement;
		this.toolType = type;
		this.setHarvestLevel(type.toString().toLowerCase(), material.getHarvestLevel());
	}
	
	public ItemToolAbility addBreakAbility(ToolAbility breakAbility) {
		this.breakAbility.add(breakAbility);
		return this;
	}
	
	public ItemToolAbility addHitAbility(WeaponAbility weaponAbility) {
		this.hitAbility.add(weaponAbility);
		return this;
	}
	
	//<insert obvious Rarity joke here>
	public ItemToolAbility setRarity(EnumRarity rarity) {
		this.rarity = rarity;
		return this;
	}
	
    public EnumRarity getRarity(ItemStack stack) {
        return this.rarity != EnumRarity.common ? this.rarity : super.getRarity(stack);
    }
    
    public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker) {

    	if(!attacker.worldObj.isRemote && !this.hitAbility.isEmpty() && attacker instanceof EntityPlayer && canOperate(stack)) {
    		
    		for(WeaponAbility ability : this.hitAbility) {
				ability.onHit(attacker.worldObj, (EntityPlayer) attacker, victim, this);
    		}
    	}
    	
    	stack.damageItem(2, attacker);
        
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
    	
    	World world = player.worldObj;
    	Block block = world.getBlock(x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(!world.isRemote && canHarvestBlock(block, stack) && this.getCurrentAbility(stack) != null && canOperate(stack))
    		this.getCurrentAbility(stack).onDig(world, x, y, z, player, block, meta, this);
    	
    	return false;
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
    
    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
    	
    	if(!canOperate(stack)) return false;
    	
    	if(this.getCurrentAbility(stack) instanceof SilkAbility)
    		return true;
    	
    	return getDigSpeed(stack, block, 0) > 1;
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
    	
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damage, 0));
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", movement, 1));
        return multimap;
    }
    
    public void breakExtraBlock(World world, int x, int y, int z, EntityPlayer playerEntity, int refX, int refY, int refZ) {
    	
        if (world.isAirBlock(x, y, z))
            return;

        if(!(playerEntity instanceof EntityPlayerMP))
            return;
        
        EntityPlayerMP player = (EntityPlayerMP) playerEntity;
        ItemStack stack = player.getHeldItem();

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if(!canHarvestBlock(block, stack))
            return;

        Block refBlock = world.getBlock(refX, refY, refZ);
        float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
        float strength = ForgeHooks.blockStrength(block, player, world, x,y,z);

        if (!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength/strength > 10f)
            return;

        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x,y,z);
        if(event.isCanceled())
            return;

        if (player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, x, y, z, meta, player);
            if (block.removedByPlayer(world, player, x, y, z, false))
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);

            if (!world.isRemote) {
                player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            }
            return;
        }

        player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);

        if (!world.isRemote) {
        	
            block.onBlockHarvested(world, x,y,z, meta, player);

            if(block.removedByPlayer(world, player, x,y,z, true))
            {
                block.onBlockDestroyedByPlayer( world, x,y,z, meta);
                block.harvestBlock(world, player, x,y,z, meta);
                block.dropXpOnBlockBreak(world, x,y,z, event.getExpToDrop());
            }

            player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            
        } else {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            if(block.removedByPlayer(world, player, x,y,z, true))
            {
                block.onBlockDestroyedByPlayer(world, x,y,z, meta);
            }
            ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack != null)
            {
                itemstack.func_150999_a(world, block, x, y, z, player);

                if (itemstack.stackSize == 0)
                {
                    player.destroyCurrentEquippedItem();
                }
            }

            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
    }

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {

		return getCurrentAbility(stack) != null ? true : super.hasEffect(stack);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		if(this.breakAbility.size() > 1) {
			list.add("Abilities: ");

			for(ToolAbility ability : this.breakAbility) {

				if(ability != null) {

					if(getCurrentAbility(stack) == ability)
						list.add(" >" + EnumChatFormatting.GOLD + ability.getFullName());
					else
						list.add("  " + EnumChatFormatting.GOLD + ability.getFullName());
				}
			}

			list.add("Right click to cycle through abilities!");
			list.add("Sneak-click to turn abilitty off!");
		}

		if(!this.hitAbility.isEmpty()) {

			list.add("Weapon modifiers: ");

			for(WeaponAbility ability : this.hitAbility) {
				list.add("  " + EnumChatFormatting.RED + ability.getFullName());
			}
		}

		if(this.rockBreaker) {
			list.add("");
			list.add(EnumChatFormatting.RED + "Can break depth rock!");
		}
	}
    
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    	
    	if(world.isRemote || this.breakAbility.size() < 2 || !canOperate(stack))
    		return super.onItemRightClick(stack, world, player);
    	
    	int i = getAbility(stack);
    	i++;
    	
    	if(player.isSneaking())
    		i = 0;
    	
    	setAbility(stack, i % this.breakAbility.size());
    	
    	while(getCurrentAbility(stack) != null && !getCurrentAbility(stack).isAllowed()) {
    		
    		player.addChatComponentMessage(
    				new ChatComponentText("[Ability ")
    				.appendSibling(new ChatComponentTranslation(getCurrentAbility(stack).getName(), new Object[0]))
    				.appendSibling(new ChatComponentText(getCurrentAbility(stack).getExtension() + " is blacklisted!]"))
    				.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
    		
        	i++;
        	setAbility(stack, i % this.breakAbility.size());
    	}
    	
    	if(getCurrentAbility(stack) != null) {
    		player.addChatComponentMessage(
    				new ChatComponentText("[Enabled ")
    				.appendSibling(new ChatComponentTranslation(getCurrentAbility(stack).getName(), new Object[0]))
    				.appendSibling(new ChatComponentText(getCurrentAbility(stack).getExtension() + "]"))
    				.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
    	} else {
    		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Tool ability deactivated]"));
    	}

        world.playSoundAtEntity(player, "random.orb", 0.25F, getCurrentAbility(stack) == null ? 0.75F : 1.25F);
    	
    	return stack;
    }
    
    private ToolAbility getCurrentAbility(ItemStack stack) {
    	
    	int ability = getAbility(stack) % this.breakAbility.size();
    	
    	return this.breakAbility.get(ability);
    }
    
    private int getAbility(ItemStack stack) {
    	
    	if(stack.hasTagCompound())
    		return stack.stackTagCompound.getInteger("ability");
    	
    	return 0;
    }
    
	private void setAbility(ItemStack stack, int ability) {

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setInteger("ability", ability);
	}

	protected boolean canOperate(ItemStack stack) {
		return true;
	}

	public ItemToolAbility setDepthRockBreaker() {
		this.rockBreaker = true;
		return this;
	}
	
	private boolean rockBreaker = false;

	@Override
	public boolean canBreakRock(World world, EntityPlayer player, ItemStack tool, Block block, int x, int y, int z) {
		return canOperate(tool) && this.rockBreaker;
	}
}
