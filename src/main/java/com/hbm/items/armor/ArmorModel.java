package com.hbm.items.armor;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelCloak;
import com.hbm.render.model.ModelGasMask;
import com.hbm.render.model.ModelGoggles;
import com.hbm.render.model.ModelHat;
import com.hbm.render.model.ModelM65;
import com.hbm.render.model.ModelOxygenMask;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Spaghetti("more ctor stuff, less if/else bullshittery")
//turns out you can't actually pass a model in the ctor because ModelBiped is clientonly...
public class ArmorModel extends ItemArmor {
	
	@SideOnly(Side.CLIENT)
	private ModelGoggles modelGoggles;
	@SideOnly(Side.CLIENT)
	private ModelGasMask modelGas;
	@SideOnly(Side.CLIENT)
	private ModelCloak modelCloak;
	@SideOnly(Side.CLIENT)
	private ModelOxygenMask modelOxy;
	@SideOnly(Side.CLIENT)
	private ModelM65 modelM65;
	@SideOnly(Side.CLIENT)
	private ModelHat modelHat;
	@Spaghetti("replace this garbage with an array")
	private ResourceLocation goggleBlur0 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_0.png");
	private ResourceLocation goggleBlur1 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_1.png");
	private ResourceLocation goggleBlur2 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_2.png");
	private ResourceLocation goggleBlur3 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_3.png");
	private ResourceLocation goggleBlur4 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_4.png");
	private ResourceLocation goggleBlur5 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_5.png");
	private ResourceLocation gasmaskBlur0 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_0.png");
	private ResourceLocation gasmaskBlur1 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_1.png");
	private ResourceLocation gasmaskBlur2 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_2.png");
	private ResourceLocation gasmaskBlur3 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_3.png");
	private ResourceLocation gasmaskBlur4 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_4.png");
	private ResourceLocation gasmaskBlur5 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_5.png");

	public ArmorModel(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
	}

	//there was no reason to override this
	/*@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		if (this == ModItems.goggles) {
			return armorType == 0;
		}
		if (this == ModItems.gas_mask) {
			return armorType == 0;
		}
		if (this == ModItems.gas_mask_m65) {
			return armorType == 0;
		}
		if (this == ModItems.gas_mask_mono) {
			return armorType == 0;
		}
		if (this == ModItems.hat) {
			return armorType == 0;
		}
		if (this == ModItems.hazmat_helmet_red) {
			return armorType == 0;
		}
		if (this == ModItems.hazmat_helmet_grey) {
			return armorType == 0;
		}
		if (this == ModItems.oxy_mask) {
			return armorType == 0;
		}
		if (this == ModItems.cape_test) {
			return armorType == 1;
		}
		if (this == ModItems.cape_radiation) {
			return armorType == 1;
		}
		if (this == ModItems.cape_gasmask) {
			return armorType == 1;
		}
		if (this == ModItems.cape_schrabidium) {
			return armorType == 1;
		}
		return armorType == 0;
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if (this == ModItems.goggles) {
			if (armorSlot == 0) {
				if (this.modelGoggles == null) {
					this.modelGoggles = new ModelGoggles();
				}
				return this.modelGoggles;
			}
		}
		if (this == ModItems.gas_mask) {
			if (armorSlot == 0) {
				if (this.modelGas == null) {
					this.modelGas = new ModelGasMask();
				}
				return this.modelGas;
			}
		}
		if (this == ModItems.gas_mask_m65 || this == ModItems.hazmat_helmet_red || this == ModItems.hazmat_helmet_grey || this == ModItems.gas_mask_mono) {
			if (armorSlot == 0) {
				if (this.modelM65 == null) {
					this.modelM65 = new ModelM65();
				}
				return this.modelM65;
			}
		}
		if (this == ModItems.oxy_mask) {
			if (armorSlot == 0) {
				if (this.modelOxy == null) {
					this.modelOxy = new ModelOxygenMask();
				}
				return this.modelOxy;
			}
		}
		if (this == ModItems.hat) {
			if (armorSlot == 0) {
				if (this.modelHat == null) {
					this.modelHat = new ModelHat(0);
				}
				return this.modelHat;
			}
		}
		if (this == ModItems.cape_test || this == ModItems.cape_radiation || this == ModItems.cape_gasmask || this == ModItems.cape_schrabidium) {
			if (armorSlot == 1) {
				if (this.modelCloak == null) {
					this.modelCloak = new ModelCloak();
				}
				return this.modelCloak;
			}
		}
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.getItem() == ModItems.goggles) {
			return "hbm:textures/models/Goggles.png";
		}
		if (stack.getItem() == ModItems.gas_mask) {
			return "hbm:textures/models/GasMask.png";
		}
		if (stack.getItem() == ModItems.gas_mask_m65) {
			return "hbm:textures/models/ModelM65.png";
		}
		if (stack.getItem() == ModItems.gas_mask_mono) {
			return "hbm:textures/models/ModelM65Mono.png";
		}
		if (stack.getItem() == ModItems.hazmat_helmet_red) {
			return "hbm:textures/models/ModelHazRed.png";
		}
		if (stack.getItem() == ModItems.hazmat_helmet_grey) {
			return "hbm:textures/models/ModelHazGrey.png";
		}
		if (stack.getItem() == ModItems.oxy_mask) {
			return null;
		}
		if (stack.getItem() == ModItems.cape_test) {
			return "hbm:textures/models/TestCape.png";
		}
		if (stack.getItem() == ModItems.cape_radiation) {
			return "hbm:textures/models/capes/CapeRadiation.png";
		}
		if (stack.getItem() == ModItems.cape_gasmask) {
			return "hbm:textures/models/capes/CapeGasMask.png";
		}
		if (stack.getItem() == ModItems.cape_schrabidium) {
			return "hbm:textures/models/capes/CapeSchrabidium.png";
		}
		
		return "hbm:textures/models/capes/CapeUnknown.png";
	}
	
    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY){
    	
    	if(this != ModItems.goggles && this != ModItems.gas_mask && this != ModItems.gas_mask_m65 && this != ModItems.hazmat_helmet_red && this != ModItems.hazmat_helmet_grey)
    		return;

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        
        if(this == ModItems.goggles || this == ModItems.gas_mask_m65 || this == ModItems.hazmat_helmet_red || this == ModItems.hazmat_helmet_grey) {
        	switch((int)((double)stack.getItemDamage() / (double)stack.getMaxDamage() * 6D)) {
        	case 0:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur0); break;
        	case 1:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur1); break;
        	case 2:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur2); break;
        	case 3:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur3); break;
        	case 4:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur4); break;
        	case 5:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur5); break;
        	default:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur5); break;
        	}
        }
        if(this == ModItems.gas_mask) {
        	switch((int)((double)stack.getItemDamage() / (double)stack.getMaxDamage() * 6D)) {
        	case 0:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur0); break;
        	case 1:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur1); break;
        	case 2:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur2); break;
        	case 3:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur3); break;
        	case 4:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur4); break;
        	case 5:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur5); break;
        	default:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur5); break;
        	}
        }
        
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

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.cape_radiation) {
			list.add("Avalible for everyone");
		}
		if(this == ModItems.cape_gasmask) {
			list.add("Avalible for everyone");
		}
		if(this == ModItems.cape_schrabidium) {
			list.add("Avalible for everyone");
		}

		if(this == ModItems.gas_mask || this == ModItems.gas_mask_m65) {
			list.add("Protects against most harmful gasses");
		}
		if(this == ModItems.gas_mask_mono) {
			list.add("Protects against carbon monoxide");
		}
	}
}
