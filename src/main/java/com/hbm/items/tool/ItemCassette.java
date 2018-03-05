package com.hbm.items.tool;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.tileentity.conductor.TileEntityFluidDuct;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemCassette extends Item {
	
	IIcon overlayIcon;
	
	public enum TrackType {
		
		NULL(				" ", 						null,												SoundType.SOUND,	0,			0),
		HATCH(				"Hatch Siren", 				new ResourceLocation("hbm:alarm.hatch"),			SoundType.LOOP,		3358839,	250),
		ATUOPILOT(			"Autopilot Disconnected", 	new ResourceLocation("hbm:alarm.autopilot"),		SoundType.LOOP,		11908533,	50),
		AMS_SIREN(			"AMS Siren", 				new ResourceLocation("hbm:alarm.amsSiren"),			SoundType.LOOP,		15055698,	50),
		APC_LOOP(			"APC Siren", 				new ResourceLocation("hbm:alarm.apcLoop"),			SoundType.LOOP,		3565216,	50),
		BANK_ALARM(			"Bank Alarm", 				new ResourceLocation("hbm:alarm.bankAlarm"),		SoundType.LOOP,		3572962,	100),
		BEEP_SIREN(			"Beep Siren", 				new ResourceLocation("hbm:alarm.beepSiren"),		SoundType.LOOP,		13882323,	100),
		CONTAINER_ALARM(	"Container Alarm", 			new ResourceLocation("hbm:alarm.containerAlarm"),	SoundType.LOOP,		14727839,	100),
		SWEEP_SIREN(		"Sweep Siren", 				new ResourceLocation("hbm:alarm.sweepSiren"),		SoundType.LOOP,		15592026,	500),
		APC_PASS(			"APC Pass", 				new ResourceLocation("hbm:alarm.apcPass"),			SoundType.PASS,		3422163,	50),
		RAZORTRAIN(			"Razortrain Horn", 			new ResourceLocation("hbm:alarm.razortrainHorn"),	SoundType.SOUND,	7819501,	250);
		
		//Name of the track shown in GUI
		private String title;
		//Location of the sound
		private ResourceLocation location;
		//Sound type, whether the sound should be repeated or not
		private SoundType type;
		//Color of the cassette
		private int color;
		//Range where the sound can be heard
		private int volume;
		
		private TrackType(String name, ResourceLocation loc, SoundType sound, int msa, int intensity) {
			title = name;
			location = loc;
			type = sound;
			color = msa;
			volume = intensity;
		}
		
		public String getTrackTitle() {
			return title;
		}
		
		public ResourceLocation getSoundLocation() {
			return location;
		}
		
		public SoundType getType() {
			return type;
		}
		
		public int getColor() {
			return color;
		}
		
		public int getVolume() {
			return volume;
		}
		
		public static TrackType getEnum(int i) {
			if(i < TrackType.values().length)
				return TrackType.values()[i];
			else
				return TrackType.NULL;
		}
	};
	
	public enum SoundType {
		LOOP,
		PASS,
		SOUND;
	};

    public ItemCassette()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 1; i < TrackType.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
    	
    	if(!(stack.getItem() instanceof ItemCassette))
    		return;
    	
    	list.add("Siren sound cassette:");
    	list.add("   Name: " + TrackType.getEnum(stack.getItemDamage()).getTrackTitle());
    	list.add("   Type: " + TrackType.getEnum(stack.getItemDamage()).getType().name());
    	list.add("   Volume: " + TrackType.getEnum(stack.getItemDamage()).getVolume());
	}
	
	public static TrackType getType(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof ItemCassette)
			return TrackType.getEnum(stack.getItemDamage());
		else
			return TrackType.NULL;
	}

    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);

        this.overlayIcon = p_94581_1_.registerIcon("hbm:cassette_overlay");
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return p_77618_2_ == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int p_82790_2_)
    {
        if (p_82790_2_ == 0)
        {
            return 16777215;
        }
        else
        {
            int j = TrackType.getEnum(stack.getItemDamage()).getColor();

            if (j < 0)
            {
                j = 16777215;
            }

            return j;
        }
    }
}
