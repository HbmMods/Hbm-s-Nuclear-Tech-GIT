package com.hbm.main;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.HTTPHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.gui.GUIArmorTable;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.armor.ArmorFSBPowered;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.items.armor.JetpackBase;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.util.RenderAccessoryUtility;
import com.hbm.render.util.RenderOverhead;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.SoyuzPronter;
import com.hbm.sound.MovingSoundChopper;
import com.hbm.sound.MovingSoundChopperMine;
import com.hbm.sound.MovingSoundCrashing;
import com.hbm.sound.MovingSoundPlayerLoop;
import com.hbm.sound.MovingSoundXVL1456;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.CustomNukeEntry;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.EnumEntryType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.util.I18nUtil;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.hbm.sound.MovingSoundPlayerLoop.EnumHbmSound;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ModEventHandlerClient {
	
	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		/// HANDLE GUN OVERLAYS ///
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemHUD) {
			((IItemHUD)player.getHeldItem().getItem()).renderHUD(event, event.type, player, player.getHeldItem());
		}
		
		/// HANDLE GEIGER COUNTER HUD ///
		if(event.type == ElementType.HOTBAR) {
			
			if(!(ArmorFSB.hasFSBArmor(player) && ((ArmorFSB)player.inventory.armorInventory[2].getItem()).customGeiger)) {
				
				if(player.inventory.hasItem(ModItems.geiger_counter)) {
	
					float rads = HbmLivingProps.getRadiation(player);
					
					RenderScreenOverlay.renderRadCounter(event.resolution, rads, Minecraft.getMinecraft().ingameGUI);
				}
			}
		}

		/// DODD DIAG HOOK FOR RBMK
		if(event.type == ElementType.CROSSHAIRS) {
			TileEntityRBMKBase.diagnosticPrintHook(event);
		}
		
		/// HANLDE ANIMATION BUSES ///
		
		for(int i = 0; i < HbmAnimations.hotbar.length; i++) {
			
			Animation animation = HbmAnimations.hotbar[i];
			
			if(animation == null)
				continue;
			
			long time = System.currentTimeMillis() - animation.startMillis;
			
			if(time > animation.animation.getDuration())
				HbmAnimations.hotbar[i] = null;
		}
			
		if(!ducked && Keyboard.isKeyDown(Keyboard.KEY_O)) {
			
			ducked = true;
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(0, 0, 0, 999, 0));
		}
		
		/// HANDLE FSB HUD ///
		ItemStack helmet = player.inventory.armorInventory[3];
		
		if(helmet != null && helmet.getItem() instanceof ArmorFSB) {
			((ArmorFSB)helmet.getItem()).handleOverlay(event, player);
		}
		
		/// HANDLE ELECTRIC FSB HUD ///
		
		if(!event.isCanceled() && event.type == event.type.ARMOR) {
			
	        int width = event.resolution.getScaledWidth();
	        int height = event.resolution.getScaledHeight();
	        int left = width / 2 - 91;
	        int top = height - GuiIngameForge.left_height - 3;
	        
			Tessellator tess = Tessellator.instance;
			
			if(ArmorFSB.hasFSBArmorIgnoreCharge(player)) {
				ArmorFSB chestplate = (ArmorFSB)player.inventory.armorInventory[2].getItem();
				boolean noHelmet = chestplate.noHelmet;
		        
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tess.startDrawingQuads();
	
				for(int i = 0; i < (noHelmet ? 3 : 4); i++) {
					
					ItemStack stack = player.inventory.armorInventory[i];
					
					if(!(stack != null && stack.getItem() instanceof ArmorFSBPowered))
						break;
					
					float tot = 1F - (float) ((ArmorFSBPowered)stack.getItem()).getDurabilityForDisplay(stack);
					
					tess.setColorOpaque_F(0.25F, 0.25F, 0.25F);
					tess.addVertex(left - 0.5, top - 0.5, 0);
					tess.addVertex(left - 0.5, top + 1.5, 0);
					tess.addVertex(left + 81.5, top + 1.5, 0);
					tess.addVertex(left + 81.5, top - 0.5, 0);
					
					tess.setColorOpaque_F(1F - tot, tot, 0F);
					tess.addVertex(left, top, 0);
					tess.addVertex(left, top + 1, 0);
					tess.addVertex(left + 81 * tot, top + 1, 0);
					tess.addVertex(left + 81 * tot, top, 0);
					
					top -= 2.5;
				}
				
				tess.draw();
				
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
			} else if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof JetpackBase) {
				
				ItemStack stack = player.inventory.armorInventory[2];
				
				float tot = (float) ((JetpackBase)stack.getItem()).getFuel(stack) / (float) ((JetpackBase)stack.getItem()).getMaxFill(stack);
				top -= 3;
				
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				tess.startDrawingQuads();
				tess.setColorOpaque_F(0.25F, 0.25F, 0.25F);
				tess.addVertex(left - 0.5, top - 0.5, 0);
				tess.addVertex(left - 0.5, top + 4.5, 0);
				tess.addVertex(left + 81.5, top + 4.5, 0);
				tess.addVertex(left + 81.5, top - 0.5, 0);
				
				tess.setColorOpaque_F(1F - tot, tot, 0F);
				tess.addVertex(left, top, 0);
				tess.addVertex(left, top + 4, 0);
				tess.addVertex(left + 81 * tot, top + 4, 0);
				tess.addVertex(left + 81 * tot, top, 0);
				tess.draw();
				
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}
		}
	}
	
	public static boolean ducked = false;
	
	@SubscribeEvent
	public void preRenderEvent(RenderPlayerEvent.Pre event) {
		
		RenderPlayer renderer = event.renderer;
		AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;
		
		PotionEffect invis = player.getActivePotionEffect(Potion.invisibility);
		
		if(invis != null && invis.getAmplifier() > 0)
			event.setCanceled(true);

		if(player.getDisplayName().toLowerCase().equals("martmn")) {
			
			event.setCanceled(true);
			
			float pX = (float) (player.prevPosX + (player.posX - player.prevPosX) * (double)event.partialRenderTick);
			float pY = (float) (player.prevPosY + (player.posY - player.prevPosY) * (double)event.partialRenderTick);
			float pZ = (float) (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)event.partialRenderTick);
			EntityPlayer me = Minecraft.getMinecraft().thePlayer;
			float mX = (float) (me.prevPosX + (me.posX - me.prevPosX) * (double)event.partialRenderTick);
			float mY = (float) (me.prevPosY + (me.posY - me.prevPosY) * (double)event.partialRenderTick);
			float mZ = (float) (me.prevPosZ + (me.posZ - me.prevPosZ) * (double)event.partialRenderTick);

			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/particle/fart.png"));
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glTranslatef(pX - mX, pY - mY + 0.75F - (float)player.getYOffset(), pZ - mZ);
			GL11.glRotatef(-me.rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(me.rotationPitch, 1.0F, 0.0F, 0.0F);
			Tessellator t = Tessellator.instance;
			t.startDrawingQuads();
			t.setBrightness(240);
			t.addVertexWithUV(-1, 1, 0, 0, 0);
			t.addVertexWithUV(1, 1, 0, 1, 0);
			t.addVertexWithUV(1, -1, 0, 1, 1);
			t.addVertexWithUV(-1, -1, 0, 0, 1);
			t.draw();
			
			GL11.glEnable(GL11.GL_LIGHTING);
			
			GL11.glPopMatrix();
		}
		
		ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);
		
		if(cloak != null)
			player.func_152121_a(Type.CAPE, cloak);
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IHoldableWeapon) {
			renderer.modelBipedMain.aimedBow = true;
			renderer.modelArmor.aimedBow = true;
			renderer.modelArmorChestplate.aimedBow = true;
		}
	}
	
	//just finish this somewhen i guess
	/*@SubscribeEvent
	public void keybindEvent(RenderPlayerEvent.Pre event) {
		
		HbmPlayerProps props = HbmPlayerProps.getData(Minecraft.getMinecraft().thePlayer);
		
		for(EnumKeybind key : EnumKeybind.values()) {
			
			boolean last = props.getKeyPressed(key);
			boolean current = MainRegistry.proxy.getIsKeyPressed(key);
	
			if(last != current) {
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(key, current));
				props.setKeyPressed(key, current);
			}
		}
	}*/
	
	@SubscribeEvent
	public void onRenderArmorEvent(RenderPlayerEvent.SetArmorModel event) {
		
		EntityPlayer player = event.entityPlayer;
		
		for(int i = 0; i < 4; i++) {
			
			ItemStack armor = player.getCurrentArmor(i);
			
			if(armor != null && ArmorModHandler.hasMods(armor)) {
				
				for(ItemStack mod : ArmorModHandler.pryMods(armor)) {
					
					if(mod != null && mod.getItem() instanceof ItemArmorMod) {
						((ItemArmorMod)mod.getItem()).modRender(event, armor);
					}
				}
			}
			
			//because armor that isn't ItemArmor doesn't render at all
			if(armor != null && armor.getItem() instanceof JetpackBase) {
				((ItemArmorMod)armor.getItem()).modRender(event, armor);
			}
		}
	}

	@SubscribeEvent
	public void clickHandler(MouseEvent event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGunBase) {
			
			if(event.button == 0)
				event.setCanceled(true);
			
			ItemGunBase item = (ItemGunBase)player.getHeldItem().getItem();
			
			if(event.button == 0 && !item.m1 && !item.m2) {
				item.m1 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0));
				item.startActionClient(player.getHeldItem(), player.worldObj, player, true);
			}
			else if(event.button == 1 && !item.m2 && !item.m1) {
				item.m2 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1));
				item.startActionClient(player.getHeldItem(), player.worldObj, player, false);
			}
		}

	}

	@Spaghetti("please get this shit out of my face")
	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 e) {
		ResourceLocation r = e.sound.getPositionedSoundLocation();

		WorldClient wc = Minecraft.getMinecraft().theWorld;
		
		//Alright, alright, I give the fuck up, you've wasted my time enough with this bullshit. You win.
		//A winner is you.
		//Conglaturations.
		//Fuck you.

		if(r.toString().equals("hbm:misc.nullTau") && Library.getClosestPlayerForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityPlayer ent = Library.getClosestPlayerForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundXVL1456(new ResourceLocation("hbm:weapon.tauChargeLoop2"), ent, EnumHbmSound.soundTauLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(0.5F);
			} else {
				if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() < 1.5F)
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() + 0.01F);
			}
		}
		
		if(r.toString().equals("hbm:misc.nullChopper") && Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopper(new ResourceLocation("hbm:entity.chopperFlyingLoop"), ent, EnumHbmSound.soundChopperLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop).setVolume(10.0F);
			}
		}
		
		if(r.toString().equals("hbm:misc.nullCrashing") && Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundCrashing(new ResourceLocation("hbm:entity.chopperCrashingLoop"), ent, EnumHbmSound.soundCrashingLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop).setVolume(10.0F);
			}
		}
		
		if(r.toString().equals("hbm:misc.nullMine") && Library.getClosestMineForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2) != null)
		{
			EntityChopperMine ent = Library.getClosestMineForSound(wc, e.sound.getXPosF(), e.sound.getYPosF(), e.sound.getZPosF(), 2);
			
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopperMine(new ResourceLocation("hbm:entity.chopperMineLoop"), ent, EnumHbmSound.soundMineLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop).setVolume(10.0F);
			}
		}

		for(MovingSoundPlayerLoop sounds : MovingSoundPlayerLoop.globalSoundList)
		{
			if(!sounds.init || sounds.isDonePlaying()) {
				sounds.init = true;
				sounds.setDone(false);
				Minecraft.getMinecraft().getSoundHandler().playSound(sounds);
			}
		}
	}
	
	@SubscribeEvent
	public void drawTooltip(ItemTooltipEvent event) {
		
		ItemStack stack = event.itemStack;
		List<String> list = event.toolTip;
		
		double rad = HazmatRegistry.getResistance(stack);
		
		rad = ((int)(rad * 1000)) / 1000D;
		
		if(rad > 0)
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.radResistance", rad));
		
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		
		CustomNukeEntry entry = TileEntityNukeCustom.entries.get(comp);
		
		if(entry != null) {
			
			if(!list.isEmpty())
				list.add("");
			
			if(entry.entry == EnumEntryType.ADD)
				list.add(EnumChatFormatting.GOLD + "Adds " + entry.value + " to the custom nuke stage " + entry.type);

			if(entry.entry == EnumEntryType.MULT)
				list.add(EnumChatFormatting.GOLD + "Adds multiplier " + entry.value + " to the custom nuke stage " + entry.type);
		}
		
		if(stack.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(stack)) {
			
			if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !(Minecraft.getMinecraft().currentScreen instanceof GUIArmorTable)) {
				
				list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC +"Hold <" +
						EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" +
						EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "> to display installed armor mods");
				
			} else {
				
				list.add(EnumChatFormatting.YELLOW + "Mods:");
				
				ItemStack[] mods = ArmorModHandler.pryMods(stack);
				
				for(int i = 0; i < 8; i++) {
					
					if(mods[i] != null && mods[i].getItem() instanceof ItemArmorMod) {
						
						((ItemArmorMod)mods[i].getItem()).addDesc(list, mods[i], stack);
					}
				}
			}
		}
    }
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
		
		GL11.glPushMatrix();
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		double dx = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
		double dy = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
		double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;
		
		int dist = 300;
		int x = 0;
		int y = 500;
		int z = 0;
		
		Vec3 vec = Vec3.createVectorHelper(x - dx, y - dy, z - dz);
		
		if(vec.lengthVector() < dist) {
			
			GL11.glTranslated(vec.xCoord, vec.yCoord, vec.zCoord);
			
			GL11.glPushMatrix();

            RenderHelper.enableStandardItemLighting();

			GL11.glRotated(80, 0, 0, 1);
			GL11.glRotated(30, 0, 1, 0);
			
	        double sine = Math.sin(System.currentTimeMillis() * 0.0005) * 5;
	        double sin3 = Math.sin(System.currentTimeMillis() * 0.0005 + Math.PI * 0.5) * 5;
	        GL11.glRotated(sine, 0, 0, 1);
	        GL11.glRotated(sin3, 1, 0, 0);
			
			GL11.glTranslated(0, -3, 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 6500F, 30F);
			SoyuzPronter.prontCapsule();
			
			GL11.glRotated(System.currentTimeMillis() * 0.025 % 360, 0, -1, 0);

			String msg = HTTPHandler.capsule;

			GL11.glTranslated(0, 3.75, 0);
			GL11.glRotated(180, 1, 0, 0);
			
			float rot = 0F;
			
			//looks dumb but we'll use this technology for the cyclotron
			for(char c : msg.toCharArray()) {

				GL11.glPushMatrix();
				
				GL11.glRotatef(rot, 0, 1, 0);
				
				float width = Minecraft.getMinecraft().fontRenderer.getStringWidth(msg);
				float scale = 5 / width;
				
				rot -= Minecraft.getMinecraft().fontRenderer.getCharWidth(c) * scale * 50;
				
				GL11.glTranslated(2, 0, 0);
				
				GL11.glRotatef(-90, 0, 1, 0);
				GL11.glScalef(scale, scale, scale);
				GL11.glDisable(GL11.GL_CULL_FACE);
				Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(c), 0, 0, 0xff00ff);
				GL11.glEnable(GL11.GL_CULL_FACE);
	    		GL11.glPopMatrix();
			}
			
            RenderHelper.disableStandardItemLighting();
    		
    		GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
		
		if(ArmorFSB.hasFSBArmor(player)) {
			ItemStack plate = player.inventory.armorInventory[2];
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();
			
			if(chestplate.thermal)
				RenderOverhead.renderThermalSight(event.partialTicks);
		}
	}
	
	@SubscribeEvent
	public void preRenderEvent(RenderLivingEvent.Pre event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(ArmorFSB.hasFSBArmor(player)) {
			ItemStack plate = player.inventory.armorInventory[2];
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();
			
			if(chestplate.vats) {
				
				int count = (int)Math.min(event.entity.getMaxHealth(), 100);
				
				int bars = (int)Math.ceil(event.entity.getHealth() * count / event.entity.getMaxHealth());
				
				String bar = EnumChatFormatting.RED + "";
				
				for(int i = 0; i < count; i++) {
					
					if(i == bars)
						bar += EnumChatFormatting.RESET + "";
					
						bar += "|";
				}
				RenderOverhead.renderTag(event.entity, event.x, event.y, event.z, event.renderer, bar, chestplate.thermal);
			}
		}
	}
	
	public static IIcon particleBase;

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre event) {
		
		if(event.map.getTextureType() == 0)
			particleBase = event.map.registerIcon(RefStrings.MODID + ":particle/particle_base");
	}

	private static final ResourceLocation poster = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/poster.png");
	
	@SubscribeEvent
	public void renderFrame(RenderItemInFrameEvent event) {
		
		if(event.item != null && event.item.getItem() == ModItems.flame_pony) {
			event.setCanceled(true);
			
			double p = 0.0625D;
			double o = p * 2.75D;
			
			GL11.glDisable(GL11.GL_LIGHTING);
			Minecraft.getMinecraft().renderEngine.bindTexture(poster);
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.addVertexWithUV(0.5, 0.5 + o, p * 0.5, 1, 0);
			tess.addVertexWithUV(-0.5, 0.5 + o, p * 0.5, 0, 0);
			tess.addVertexWithUV(-0.5, -0.5 + o, p * 0.5, 0, 1);
			tess.addVertexWithUV(0.5, -0.5 + o, p * 0.5, 1, 1);
			tess.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}
}
