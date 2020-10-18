package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.Untested;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

//Armor with full set bonus
public class ArmorFSB extends ItemArmor {

	private String texture = "";
	private ResourceLocation overlay = null;
	public List<PotionEffect> effects = new ArrayList();
	public HashMap<String, Float> resistance = new HashMap();
	public float blastProtection = -1;
	public float damageCap = -1;
	public float damageMod = -1;
	public boolean fireproof = false;
	public boolean noHelmet = false;

	public ArmorFSB(ArmorMaterial material, int layer, int slot, String texture) {
		super(material, layer, slot);
		this.texture = texture;
	}
	
	public ArmorFSB addEffect(PotionEffect effect) {
		effects.add(effect);
		return this;
	}
	
	public ArmorFSB addResistance(String damage, float mod) {
		resistance.put(damage, mod);
		return this;
	}
	
	public ArmorFSB setCap(float cap) {
		this.damageCap = cap;
		return this;
	}
	
	public ArmorFSB setMod(float mod) {
		this.damageMod = mod;
		return this;
	}
	
	public ArmorFSB setFireproof(boolean fire) {
		this.fireproof = fire;
		return this;
	}
	
	public ArmorFSB setNoHelmet(boolean noHelmet) {
		this.noHelmet = noHelmet;
		return this;
	}
	
	public ArmorFSB setOverlay(String path) {
		this.overlay = new ResourceLocation(path);
		return this;
	}
	
	public ArmorFSB cloneStats(ArmorFSB original) {
		
		//lists aren't being modified after instantiation, so there's no need to dereference
		this.effects = original.effects;
		this.resistance = original.resistance;
		this.damageCap = original.damageCap;
		this.damageMod = original.damageMod;
		this.fireproof = original.fireproof;
		this.noHelmet = original.noHelmet;
		//overlay doesn't need to be copied because it's helmet exclusive
		return this;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		return texture;
	}
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	
    	list.add(EnumChatFormatting.GOLD + "Full set bonus:");
    	
    	if(!effects.isEmpty()) {
    		
    		for(PotionEffect effect : effects) {
	    		list.add("  " + I18n.format(Potion.potionTypes[effect.getPotionID()].getName()));
    		}
    	}
    	
    	if(!resistance.isEmpty()) {

        	for(Entry<String, Float> struct : resistance.entrySet()) {
        		list.add("  Damage modifier of " + struct.getValue() + " against " + I18n.format(struct.getKey()));
        	}
    	}
    	
    	if(blastProtection != -1) {

    		list.add("  Damage modifier of " + blastProtection + " against explosions");
    	}
    	
    	if(damageCap != -1) {
			list.add("  Hard damage cap of " + damageCap);
    	}
    	
    	if(damageMod != -1) {
			list.add("  General damage modifier of " + damageMod);
    	}
    	
    	if(fireproof) {
			list.add("  Fireproof");
    	}
    }
    
    public static boolean hasFSBArmor(EntityPlayer player) {
    	
		ItemStack helmet = player.inventory.armorInventory[3];
		ItemStack plate = player.inventory.armorInventory[2];
		ItemStack legs = player.inventory.armorInventory[1];
		ItemStack boots = player.inventory.armorInventory[0];
		
		if(plate != null && plate.getItem() instanceof ArmorFSB) {
			
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();
			
			boolean noHelmet = chestplate.noHelmet;
		
			if((helmet != null || noHelmet) && plate != null && legs != null && boots != null) {
				
				if((noHelmet || chestplate.getArmorMaterial() == ((ItemArmor)helmet.getItem()).getArmorMaterial()) &&
					chestplate.getArmorMaterial() == ((ItemArmor)legs.getItem()).getArmorMaterial() &&
					chestplate.getArmorMaterial() == ((ItemArmor)boots.getItem()).getArmorMaterial()) {
					return true;
				}
			}
		}
		
		return false;
    }

	@Untested
    public static void handleAttack(LivingAttackEvent event) {
    	
		EntityLivingBase e = event.entityLiving;
		
		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e;
			
			if(ArmorFSB.hasFSBArmor(player)) {
				
				ItemStack plate = player.inventory.armorInventory[2];
				
				ArmorFSB chestplate = (ArmorFSB)plate.getItem();
				
				if(chestplate.fireproof && event.source.isFireDamage()) {
					player.extinguish();
					event.setCanceled(true);
				}
			}
		}
    }

	@Untested
    public static void handleHurt(LivingHurtEvent event) {
    	
		EntityLivingBase e = event.entityLiving;
		
		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e;
			
			if(ArmorFSB.hasFSBArmor(player)) {
				
				ArmorFSB chestplate = (ArmorFSB)player.inventory.armorInventory[2].getItem();
				
				if(chestplate.damageMod != -1) {
					event.ammount *= chestplate.damageMod;
				}
				
				if(chestplate.resistance.get(event.source.getDamageType()) != null) {
					event.ammount *= chestplate.resistance.get(event.source);
				}
				
				if(chestplate.blastProtection != -1 && event.source.isExplosion()) {
					event.ammount *= chestplate.blastProtection;
				}
				
				if(chestplate.damageCap != -1) {
					event.ammount = Math.min(event.ammount, chestplate.damageCap);
				}
			}
		}
    }
	
    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY){
    	
    	if(overlay == null)
    		return;

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        Minecraft.getMinecraft().getTextureManager().bindTexture(overlay);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, (double)resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV((double)resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
