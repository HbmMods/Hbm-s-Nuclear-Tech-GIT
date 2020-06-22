package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.WeaponAbility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class ItemSwordAbility extends ItemSword implements IItemAbility {
	
	private EnumRarity rarity = EnumRarity.common;
	//was there a reason for this to be private?
    protected float damage;
    protected double movement;
    private List<WeaponAbility> hitAbility = new ArrayList();

	public ItemSwordAbility(float damage, double movement, ToolMaterial material) {
		super(material);
		this.damage = damage;
		this.movement = movement;
	}
	
	public ItemSwordAbility addHitAbility(WeaponAbility weaponAbility) {
		this.hitAbility.add(weaponAbility);
		return this;
	}
	
	//<insert obvious Rarity joke here>
	public ItemSwordAbility setRarity(EnumRarity rarity) {
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
        
        return true;
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
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
    	
    	if(!this.hitAbility.isEmpty()) {
    		
    		list.add("Weapon modifiers: ");
    		
    		for(WeaponAbility ability : this.hitAbility) {
				list.add("  " + EnumChatFormatting.RED + ability.getFullName());
    		}
    	}
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
}
