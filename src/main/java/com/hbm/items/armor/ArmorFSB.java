package com.hbm.items.armor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.Untested;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.MathHelper;
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
	public float damageThreshold = 0;
	public boolean fireproof = false;
	public boolean noHelmet = false;
	public boolean vats = false;
	public boolean thermal = false;
	public double gravity = 0;
	public String step;
	public String jump;
	public String fall;

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
	
	public ArmorFSB setThreshold(float threshold) {
		this.damageThreshold = threshold;
		return this;
	}
	
	public ArmorFSB setBlastProtection(float blastProtection) {
		this.blastProtection = blastProtection;
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
	
	public ArmorFSB enableVATS(boolean vats) {
		this.vats = vats;
		return this;
	}
	
	public ArmorFSB enableThermalSight(boolean thermal) {
		this.thermal = thermal;
		return this;
	}
	
	public ArmorFSB setGravity(double gravity) {
		this.gravity = gravity;
		return this;
	}
	
	public ArmorFSB setStep(String step) {
		this.step = step;
		return this;
	}
	
	public ArmorFSB setJump(String jump) {
		this.jump = jump;
		return this;
	}
	
	public ArmorFSB setFall(String fall) {
		this.fall = fall;
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
		this.damageThreshold = original.damageThreshold;
		this.blastProtection = original.blastProtection;
		this.fireproof = original.fireproof;
		this.noHelmet = original.noHelmet;
		this.vats = original.vats;
		this.thermal = original.thermal;
		this.gravity = original.gravity;
		this.step = original.step;
		this.jump = original.jump;
		this.fall = original.fall;
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
	    		list.add(EnumChatFormatting.AQUA + "  " + I18n.format(Potion.potionTypes[effect.getPotionID()].getName()));
    		}
    	}
    	
    	if(!resistance.isEmpty()) {

        	for(Entry<String, Float> struct : resistance.entrySet()) {
        		
        		if(struct.getValue() != 0)
        			list.add(EnumChatFormatting.YELLOW + "  Damage modifier of " + struct.getValue() + " against " + I18n.format(struct.getKey()));
        		else
        			list.add(EnumChatFormatting.RED + "  Nullifies all damage from " + I18n.format(struct.getKey()));
        	}
    	}
    	
    	if(blastProtection != -1) {

    		list.add(EnumChatFormatting.YELLOW + "  Damage modifier of " + blastProtection + " against explosions");
    	}
    	
    	if(damageCap != -1) {
			list.add(EnumChatFormatting.YELLOW + "  Hard damage cap of " + damageCap);
    	}
    	
    	if(damageMod != -1) {
			list.add(EnumChatFormatting.YELLOW + "  General damage modifier of " + damageMod);
    	}
    	
    	if(damageThreshold > 0) {
			list.add(EnumChatFormatting.YELLOW + "  Damage threshold of " + damageThreshold);
    	}
    	
    	if(fireproof) {
			list.add(EnumChatFormatting.RED + "  Fireproof");
    	}
    	
    	if(vats) {
			list.add(EnumChatFormatting.RED + "  Enemy HUD");
    	}
    	
    	if(thermal) {
			list.add(EnumChatFormatting.RED + "  Thermal Sight");
    	}
    	
    	if(gravity != 0) {
			list.add(EnumChatFormatting.BLUE + "  Gravity modifier of " + gravity);
    	}
    }
    
    public static boolean hasFSBArmor(EntityPlayer player) {
    	
		ItemStack plate = player.inventory.armorInventory[2];
		
		if(plate != null && plate.getItem() instanceof ArmorFSB) {
			
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();
			boolean noHelmet = chestplate.noHelmet;
			
			for(int i = 0; i < (noHelmet ? 3 : 4); i++) {
				
				ItemStack armor = player.inventory.armorInventory[i];
				
				if(armor == null || !(armor.getItem() instanceof ArmorFSB))
					return false;
				
				if(((ArmorFSB)armor.getItem()).getArmorMaterial() != chestplate.getArmorMaterial())
					return false;
				
				if(!((ArmorFSB)armor.getItem()).isArmorEnabled(armor))
					return false;
			}
			
			return true;
		}
		
		return false;
    }
    
    public static boolean hasFSBArmorIgnoreCharge(EntityPlayer player) {
    	
		ItemStack plate = player.inventory.armorInventory[2];
		
		if(plate != null && plate.getItem() instanceof ArmorFSB) {
			
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();
			boolean noHelmet = chestplate.noHelmet;
			
			for(int i = 0; i < (noHelmet ? 3 : 4); i++) {
				
				ItemStack armor = player.inventory.armorInventory[i];
				
				if(armor == null || !(armor.getItem() instanceof ArmorFSB))
					return false;
				
				if(((ArmorFSB)armor.getItem()).getArmorMaterial() != chestplate.getArmorMaterial())
					return false;
			}
			
			return true;
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
				
				if(chestplate.damageThreshold >= event.ammount) {
					event.setCanceled(true);
				}
				
				if(chestplate.fireproof && event.source.isFireDamage()) {
					player.extinguish();
					event.setCanceled(true);
				}
				
				if(chestplate.resistance.get(event.source.getDamageType()) != null &&
						chestplate.resistance.get(event.source.getDamageType()) <= 0) {
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
				
				if(event.ammount < 100) {
					
					event.ammount -= chestplate.damageThreshold;
					
					if(chestplate.damageMod != -1) {
						event.ammount *= chestplate.damageMod;
					}
					
					if(chestplate.resistance.get(event.source.getDamageType()) != null) {
						event.ammount *= chestplate.resistance.get(event.source.getDamageType());
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
    }

	@Untested
    public static void handleTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;

		if(ArmorFSB.hasFSBArmor(player)) {

			ItemStack plate = player.inventory.armorInventory[2];

			ArmorFSB chestplate = (ArmorFSB) plate.getItem();

			if(!chestplate.effects.isEmpty()) {

				for(PotionEffect i : chestplate.effects) {
					player.addPotionEffect(new PotionEffect(i.getPotionID(), i.getDuration(), i.getAmplifier(), i.getIsAmbient()));
				}
			}
			
			if(!player.capabilities.isFlying && !player.isInWater())
				player.motionY -= chestplate.gravity;
			
			if(chestplate.step != null && player.worldObj.isRemote && player.onGround) {

				try {
					Field nextStepDistance = ReflectionHelper.findField(Entity.class, "nextStepDistance", "field_70150_b");
					Field distanceWalkedOnStepModified = ReflectionHelper.findField(Entity.class, "distanceWalkedOnStepModified", "field_82151_R");
					
					if(player.getEntityData().getFloat("hfr_nextStepDistance") == 0) {
						player.getEntityData().setFloat("hfr_nextStepDistance", nextStepDistance.getFloat(player));
					}
					
	                int px = MathHelper.floor_double(player.posX);
	                int py = MathHelper.floor_double(player.posY - 0.2D - (double)player.yOffset);
	                int pz = MathHelper.floor_double(player.posZ);
	                Block block = player.worldObj.getBlock(px, py, pz);
					
					if(block.getMaterial() != Material.air && player.getEntityData().getFloat("hfr_nextStepDistance") <= distanceWalkedOnStepModified.getFloat(player))
						player.playSound(chestplate.step, 1.0F, 1.0F);
					
					player.getEntityData().setFloat("hfr_nextStepDistance", nextStepDistance.getFloat(player));
					
				} catch (Exception x) { }
			}
		}
    }
	
	@Untested
	public static void handleJump(EntityPlayer player) {

		if(ArmorFSB.hasFSBArmor(player)) {

			ArmorFSB chestplate = (ArmorFSB) player.inventory.armorInventory[2].getItem();

			if(chestplate.jump != null)
				player.playSound(chestplate.jump, 1.0F, 1.0F);
		}
	}
	
	@Untested
	public static void handleFall(EntityPlayer player) {

		if(ArmorFSB.hasFSBArmor(player)) {

			ArmorFSB chestplate = (ArmorFSB) player.inventory.armorInventory[2].getItem();

			if(chestplate.fall != null)
				player.playSound(chestplate.fall, 1.0F, 1.0F);
		}
	}
	
	public boolean isArmorEnabled(ItemStack stack) {
		return true;
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
