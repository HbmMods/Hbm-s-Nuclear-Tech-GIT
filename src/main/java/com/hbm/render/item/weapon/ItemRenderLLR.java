package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderLLR implements IItemRenderer
{
	static Minecraft mc = Minecraft.getMinecraft();
	public ItemRenderLLR() {}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type)
		{
		case FIRST_PERSON_MAP:
			return false;
		default:
			return true;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	static final String body = "Mesh_3";
	static final String magazine = "Mesh_2.001";
	/** Stock, sights, muzzle, sight pins, and barrel **/
	static final String[] misc = {"Mesh_7", "Mesh_2", "Mesh_8"};
	static final String slideHandle = "Mesh_0";
	static final String slide = "Mesh_1";
	
	static final float scale1 = 4.5F;
	static final float scale2 = 3F;
	static final float scale3 = 5F;
	static final float scale4 = 35F;
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		mc.renderEngine.bindTexture(ResourceManager.lunatic_tex_alt);
		EntityPlayer player = mc.thePlayer;
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		
		switch (type)
		{
		case ENTITY:// Dropped item
			GL11.glScalef(scale1, scale1, scale1);
			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glScalef(scale2, scale2, scale2 - scale2 * 2);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
			GL11.glRotatef(50F, 0F, 1F, 0F);
			GL11.glRotatef(90F, 0F, 0F, 1F);
			GL11.glTranslatef(0F, 0.02F, 0.45F);
			break;
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glScalef(scale3, scale3, scale3);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
			GL11.glRotatef(60F, 0F, 1F, 0F);
			GL11.glRotatef(90F, 0F, 0F, 1F);
			byte s = 4;
			if (player.isSneaking())
			{
				GL11.glRotatef(-5F, 0F, 1F, 0F);
				GL11.glRotatef(5.1F, 1F, 0F, 0F);
				GL11.glTranslatef(0.208F, 0.069F, 0.45F);
				s = 5;
			}
			else
				GL11.glTranslatef(0F, 0.02F, 0.45F);
			
			GL11.glTranslated(recoil[0], recoil[1], recoil[2] / s);
			break;
		case INVENTORY:
			GL11.glScalef(scale4, scale4, scale4);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
			GL11.glRotatef(-125F, 0F, 1F, 0F);
			GL11.glRotatef(90F, 0F, 0F, 1F);
			GL11.glTranslatef(0F, -0.021F, -0.11F);
			break;
		default:
			break;
		}
		
		ResourceManager.lunatic_smg.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

}
