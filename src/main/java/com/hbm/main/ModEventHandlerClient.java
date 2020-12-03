package com.hbm.main;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.handler.HTTPHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.GunButtonPacket;
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
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
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
			
			if(player.inventory.hasItem(ModItems.geiger_counter)) {

				float rads = 0;

				rads = player.getEntityData().getFloat("hfr_radiation");
				
				RenderScreenOverlay.renderRadCounter(event.resolution, rads, Minecraft.getMinecraft().ingameGUI);
			}
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
	}
	
	public static boolean ducked = false;
	
	@SubscribeEvent
	public void preRenderEvent(RenderPlayerEvent.Pre event) {
		
		RenderPlayer renderer = event.renderer;
		AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;
		
		PotionEffect invis = player.getActivePotionEffect(Potion.invisibility);
		
		if(invis != null && invis.getAmplifier() > 0)
			event.setCanceled(true);
		
		ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);
		
		if(cloak != null)
			player.func_152121_a(Type.CAPE, cloak);
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IHoldableWeapon) {
			renderer.modelBipedMain.aimedBow = true;
			renderer.modelArmor.aimedBow = true;
			renderer.modelArmorChestplate.aimedBow = true;
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
		
		float rad = HazmatRegistry.getResistance(stack);
		
		rad = ((int)(rad * 100)) / 100F;
		
		if(rad > 0)
			list.add(EnumChatFormatting.YELLOW + "Radiation resistance: " + rad);
		
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
}
