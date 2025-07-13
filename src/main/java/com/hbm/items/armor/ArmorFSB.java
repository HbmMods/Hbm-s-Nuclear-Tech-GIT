package com.hbm.items.armor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.items.ModItems;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ShadyUtil;
import com.hbm.util.i18n.I18nUtil;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

//Armor with full set bonus
public class ArmorFSB extends ItemArmor implements IArmorDisableModel {

	private String texture = "";
	private ResourceLocation overlay = null;
	public List<PotionEffect> effects = new ArrayList<PotionEffect>();
	public boolean noHelmet = false;
	public boolean vats = false;
	public boolean thermal = false;
	public boolean geigerSound = false;
	public boolean customGeiger = false;
	public boolean hardLanding = false;
	public int dashCount = 0;
	public int stepSize = 0;
	public String step;
	public String jump;
	public String fall;

	public ArmorFSB(ArmorMaterial material, int slot, String texture) {
		super(material, 0, slot);
		this.texture = texture;
	}

	public ArmorFSB addEffect(PotionEffect effect) {
		effects.add(effect);
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

	public ArmorFSB setHasGeigerSound(boolean geiger) {
		this.geigerSound = geiger;
		return this;
	}

	public ArmorFSB setHasCustomGeiger(boolean geiger) {
		this.customGeiger = geiger;
		return this;
	}

	public ArmorFSB setHasHardLanding(boolean hardLanding) {
		this.hardLanding = hardLanding;
		return this;
	}
	
	public ArmorFSB setDashCount(int dashCount) {
		this.dashCount = dashCount;
		return this;
	}
	
	public ArmorFSB setStepSize(int stepSize) {
		this.stepSize = stepSize;
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
		this.noHelmet = original.noHelmet;
		this.vats = original.vats;
		this.thermal = original.thermal;
		this.geigerSound = original.geigerSound;
		this.customGeiger = original.customGeiger;
		this.hardLanding = original.hardLanding;
		this.dashCount = original.dashCount;
		this.stepSize = original.stepSize;
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
		
		List toAdd = new ArrayList();

		if(!effects.isEmpty()) {
			List potionList = new ArrayList();
			for(PotionEffect effect : effects) {
				potionList.add(I18nUtil.format(Potion.potionTypes[effect.getPotionID()].getName()));
			}
			
			toAdd.add(EnumChatFormatting.AQUA + String.join(", ", potionList));
		}

		if(geigerSound) toAdd.add(EnumChatFormatting.GOLD + "  " + I18nUtil.resolveKey("armor.geigerSound"));
		if(customGeiger) toAdd.add(EnumChatFormatting.GOLD + "  " + I18nUtil.resolveKey("armor.geigerHUD"));
		if(vats) toAdd.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.vats"));
		if(thermal) toAdd.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.thermal"));
		if(hardLanding) toAdd.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.hardLanding"));
		if(stepSize != 0) toAdd.add(EnumChatFormatting.BLUE + "  " + I18nUtil.resolveKey("armor.stepSize", stepSize));
		if(dashCount > 0) toAdd.add(EnumChatFormatting.AQUA + "  " + I18nUtil.resolveKey("armor.dash", dashCount));

		if(!toAdd.isEmpty()) {
			list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("armor.fullSetBonus"));
			list.addAll(toAdd);
		}
	}

	public static boolean hasFSBArmor(EntityPlayer player) {

		ItemStack plate = player.inventory.armorInventory[2];

		if(plate != null && plate.getItem() instanceof ArmorFSB) {

			ArmorFSB chestplate = (ArmorFSB) plate.getItem();
			boolean noHelmet = chestplate.noHelmet;

			for(int i = 0; i < (noHelmet ? 3 : 4); i++) {

				ItemStack armor = player.inventory.armorInventory[i];

				if(armor == null || !(armor.getItem() instanceof ArmorFSB))
					return false;

				if(((ArmorFSB) armor.getItem()).getArmorMaterial() != chestplate.getArmorMaterial())
					return false;

				if(!((ArmorFSB) armor.getItem()).isArmorEnabled(armor))
					return false;
			}

			return true;
		}

		return false;
	}

	public static boolean hasFSBArmorIgnoreCharge(EntityPlayer player) {

		ItemStack plate = player.inventory.armorInventory[2];

		if(plate != null && plate.getItem() instanceof ArmorFSB) {

			ArmorFSB chestplate = (ArmorFSB) plate.getItem();
			boolean noHelmet = chestplate.noHelmet;

			for(int i = 0; i < (noHelmet ? 3 : 4); i++) {

				ItemStack armor = player.inventory.armorInventory[i];

				if(armor == null || !(armor.getItem() instanceof ArmorFSB))
					return false;

				if(((ArmorFSB) armor.getItem()).getArmorMaterial() != chestplate.getArmorMaterial())
					return false;
			}

			return true;
		}

		return false;
	}

	public void handleTick(TickEvent.PlayerTickEvent event) {

		EntityPlayer player = event.player;
		boolean step = true;
		
		if(player.getUniqueID().equals(ShadyUtil.the_NCR) || player.getUniqueID().equals(ShadyUtil.Barnaby99_x)) {
			step = false;
			
			if(player.worldObj.isRemote && player.onGround) {
				steppy(player, "hbm:step.powered");
			}
		}

		if(ArmorFSB.hasFSBArmor(player)) {

			ItemStack plate = player.inventory.armorInventory[2];
			ArmorFSB chestplate = (ArmorFSB) plate.getItem();

			if(!chestplate.effects.isEmpty()) {
				for(PotionEffect i : chestplate.effects) {
					player.addPotionEffect(new PotionEffect(i.getPotionID(), i.getDuration(), i.getAmplifier(), true));
				}
			}

			if(step == true && chestplate.step != null && player.worldObj.isRemote && player.onGround) {
				steppy(player, chestplate.step);
			}
		}
	}
	
	public static void steppy(EntityPlayer player, String sound) {

		try {
			Field nextStepDistance = ReflectionHelper.findField(Entity.class, "nextStepDistance", "field_70150_b");
			Field distanceWalkedOnStepModified = ReflectionHelper.findField(Entity.class, "distanceWalkedOnStepModified", "field_82151_R");

			if(player.getEntityData().getFloat("hfr_nextStepDistance") == 0) {
				player.getEntityData().setFloat("hfr_nextStepDistance", nextStepDistance.getFloat(player));
			}

			int px = MathHelper.floor_double(player.posX);
			int py = MathHelper.floor_double(player.posY - 0.2D - (double) player.yOffset);
			int pz = MathHelper.floor_double(player.posZ);
			Block block = player.worldObj.getBlock(px, py, pz);

			if(block.getMaterial() != Material.air && player.getEntityData().getFloat("hfr_nextStepDistance") <= distanceWalkedOnStepModified.getFloat(player))
				player.playSound(sound, 1.0F, 1.0F);

			player.getEntityData().setFloat("hfr_nextStepDistance", nextStepDistance.getFloat(player));

		} catch(Exception x) {
		}
	}

	public void handleJump(EntityPlayer player) {

		if(ArmorFSB.hasFSBArmor(player)) {

			ArmorFSB chestplate = (ArmorFSB) player.inventory.armorInventory[2].getItem();

			if(chestplate.jump != null)
				player.playSound(chestplate.jump, 1.0F, 1.0F);
		}
	}

	public void handleFall(EntityPlayer player) {

		if(ArmorFSB.hasFSBArmor(player)) {

			ArmorFSB chestplate = (ArmorFSB) player.inventory.armorInventory[2].getItem();

			if(chestplate.hardLanding && player.fallDistance > 10) {

				// player.playSound(Block.soundTypeAnvil.func_150496_b(), 2.0F,
				// 0.5F);

				List<Entity> entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(3, 0, 3));

				for(Entity e : entities) {
					
					if(e instanceof EntityItem)
						continue;

					Vec3 vec = Vec3.createVectorHelper(player.posX - e.posX, 0, player.posZ - e.posZ);

					if(vec.lengthVector() < 3) {

						double intensity = 3 - vec.lengthVector();
						e.motionX += vec.xCoord * intensity * -2;
						e.motionY += 0.1D * intensity;
						e.motionZ += vec.zCoord * intensity * -2;

						e.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), (float) (intensity * 10));
					}
				}
				// return;
			}

			if(chestplate.fall != null)
				player.playSound(chestplate.fall, 1.0F, 1.0F);
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer entity, ItemStack stack) {

		if(this.armorType != 1) return;
		if(!hasFSBArmor(entity) || !this.geigerSound) return;
		if(entity.inventory.hasItem(ModItems.geiger_counter) || entity.inventory.hasItem(ModItems.dosimeter)) return;

		if(world.getTotalWorldTime() % 5 == 0) {

			// Armor piece dosimeters indicate radiation dosage inside the armor, so reduce the counts by the effective protection
			float mod = ContaminationUtil.calculateRadiationMod(entity);
			float x = HbmLivingProps.getRadBuf(entity) * mod;
			
			if(x > 1E-5) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 1) list.add(0);
				if(x < 5) list.add(0);
				if(x < 10) list.add(1);
				if(x > 5 && x < 15) list.add(2);
				if(x > 10 && x < 20) list.add(3);
				if(x > 15 && x < 25) list.add(4);
				if(x > 20 && x < 30) list.add(5);
				if(x > 25) list.add(6);

				int r = list.get(world.rand.nextInt(list.size()));

				if(r > 0) world.playSoundAtEntity(entity, "hbm:item.geiger" + r, 1.0F, 1.0F);
			}
		}
	}

	public static int check(World world, int x, int y, int z) {
		int rads = (int) Math.ceil(ChunkRadiationManager.proxy.getRadiation(world, x, y, z));
		return rads;
	}

	// For crazier stuff not possible without hooking the event
	@SideOnly(Side.CLIENT) public void handleOverlay(RenderGameOverlayEvent.Pre event, EntityPlayer player) { }

	public boolean isArmorEnabled(ItemStack stack) { return true; }

	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {

		if(overlay == null)
			return;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		Minecraft.getMinecraft().getTextureManager().bindTexture(overlay);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, (double) resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV((double) resolution.getScaledWidth(), (double) resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV((double) resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private HashSet<EnumPlayerPart> hidden = new HashSet<EnumPlayerPart>();
	private boolean needsFullSet = false;
	
	public ArmorFSB hides(EnumPlayerPart... parts) {
		Collections.addAll(hidden, parts);
		return this;
	}
	
	public ArmorFSB setFullSetForHide() {
		needsFullSet = true;
		return this;
	}
	
	@Override
	public boolean disablesPart(EntityPlayer player, ItemStack stack, EnumPlayerPart part) {
		return hidden.contains(part) && (!needsFullSet || hasFSBArmorIgnoreCharge(player));
	}

	public void handleAttack(LivingAttackEvent event) { }
	public void handleHurt(LivingHurtEvent event) { }
}
