package com.hbm.items.food;

import java.util.List;

import com.hbm.entity.effect.EntityVortex;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemLemon extends ItemFood {

	public ItemLemon(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
		
		if(this == ModItems.med_ipecac || this == ModItems.med_ptsd) {
			this.setAlwaysEdible();
		}
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.lemon) {
			list.add("Eh, good enough.");
		}
		
		if(this == ModItems.definitelyfood) {
			list.add("A'right, I got sick and tired of");
			list.add("having to go out, kill things just");
			list.add("to get food and not die, so here is ");
			list.add("my absolutely genius solution:");
			list.add("");
			list.add("Have some edible dirt.");
		}
		
		if(this == ModItems.med_ipecac) {
			list.add("Bitter juice that will cause your stomach");
			list.add("to forcefully eject its contents.");
		}
		
		if(this == ModItems.med_ptsd) {
			list.add("This isn't even PTSD mediaction, it's just");
			list.add("Ipecac in a different bottle!");
		}
		
		if(this == ModItems.med_schizophrenia) {
			list.add("Makes the voices go away. Just for a while.");
			list.add("");
			list.add("...");
			list.add("Better not take it.");
		}
		
		if(this == ModItems.med_schizophrenia) {
			list.add("Makes the voices go away. Just for a while.");
			list.add("");
			list.add("...");
			list.add("Better not take it.");
		}
		
		if(this == ModItems.loops) {
			list.add("Brøther, may I have some lööps?");
		}
		
		if(this == ModItems.loop_stew) {
			list.add("A very, very healthy breakfast.");
		}
		
		if(this == ModItems.twinkie) {
			list.add("Expired 600 years ago!");
		}

		if(this == ModItems.canned_beef) {
			list.add("A few centuries ago, a cow died for this.");
		}

		if(this == ModItems.canned_tuna) {
			list.add("I can't tell if that's actually tuna or dried cement.");
		}

		if(this == ModItems.canned_mystery) {
			list.add("What's inside? Only one way to find out!");
		}

		if(this == ModItems.canned_pashtet) {
			list.add("услуги перевода недоступны!");
		}

		if(this == ModItems.canned_cheese) {
			list.add("Is it cheese? Is it rubber cement? Who knows, who cares.");
		}

		if(this == ModItems.canned_milk) {
			list.add("Milk 2: More solid than ever before!");
		}

		if(this == ModItems.canned_ass) {
			list.add("100% quality donkey meat!*");
		}

		if(this == ModItems.canned_pizza) {
			list.add("A crime against humanity.");
		}

		if(this == ModItems.canned_tube) {
			list.add("Tasty mush.");
		}

		if(this == ModItems.canned_tomato) {
			list.add("Who wants some thick red paste?");
		}

		if(this == ModItems.canned_asbestos) {
			list.add("TASTE the asbestosis!");
		}

		if(this == ModItems.canned_bhole) {
			list.add("Singularity is yum yum in my tum tum!");
		}

		if(this == ModItems.canned_jizz) {
			list.add("Wait wh-");
		}

		if(this == ModItems.canned_hotdogs) {
			list.add("Not to be confused with cool cats.");
		}

		if(this == ModItems.canned_leftovers) {
			list.add("ur 2 slow");
		}

		if(this == ModItems.canned_yogurt) {
			list.add("Probably spoiled, but whatever.");
		}

		if(this == ModItems.canned_stew) {
			list.add("...");
		}

		if(this == ModItems.canned_chinese) {
			list.add("In China, Chinese food is just called food.");
		}

		if(this == ModItems.canned_oil) {
			list.add("It makes motors go, so why not humans?");
		}

		if(this == ModItems.canned_fist) {
			list.add("Yowser!");
		}

		if(this == ModItems.canned_spam) {
			list.add("The three-and-a-half-minute sketch is set in the fictional Green Midget Cafe in Bromley.");
			list.add("An argument develops between the waitress, who recites a menu in which nearly");
			list.add("every dish contains Spam, and Mrs. Bun, who does not like Spam. She asks for a");
			list.add("dish without Spam, much to the amazement of her Spam-loving husband. The waitress");
			list.add("responds to this request with disgust. Mr. Bun offers to take her Spam instead,");
			list.add("and asks for a dish containing a lot of Spam and baked beans. The waitress says");
			list.add("no since they are out of baked beans; when Mr. Bun asks for a substitution of Spam,");
			list.add("the waitress again responds with disgust. At several points, a group of Vikings in");
			list.add("the restaurant interrupts conversation by loudly singing about Spam.");
			list.add("The irate waitress orders them to shut up, but they resume singing more loudly.");
			list.add("A Hungarian tourist comes to the counter, trying to order by using a wholly");
			list.add("inaccurate Hungarian/English phrasebook (a reference to a previous sketch).");
			list.add("He is rapidly escorted away by a police constable. The sketch abruptly cuts to a");
			list.add("historian in a television studio talking about the origin of the Vikings in the café.");
			list.add("As he goes on, he begins to increasingly insert the word \"Spam\" into every");
			list.add("sentence, and the backdrop is raised to reveal the restaurant set behind.");
			list.add("The historian joins the Vikings in their song, and Mr. and Mrs. Bun are lifted by");
			list.add("wires out of the scene while the singing continues. In the original televised performance,");
			list.add("the closing credits begin to scroll with the singing still audible in the background.");
		}

		if(this == ModItems.canned_fried) {
			list.add("Even the can is deep fried!");
		}

		if(this == ModItems.canned_napalm) {
			list.add("I love the smell of old memes in the morning!");
		}

		if(this == ModItems.canned_diesel) {
			list.add("I'm slowly running out of jokes for these.");
		}

		if(this == ModItems.canned_kerosene) {
			list.add("Just imagine a witty line here.");
		}

		if(this == ModItems.canned_recursion) {
			list.add("Canned Recursion");
		}

		if(this == ModItems.canned_bark) {
			list.add("Extra cronchy!");
		}

		if(this == ModItems.pudding) {
			list.add("What if he did?");
			list.add("What if he didn't?");
			list.add("What if the world was made of pudding?");
		}

		if(this == ModItems.ingot_semtex) {
			list.add("Semtex H Plastic Explosive");
			list.add("Performant explosive for many applications.");
			list.add("Edible");
		}

		if(this == ModItems.peas) {
			list.add("He accepts your offering.");
		}
	}


    @Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
		if(this == ModItems.med_ipecac || this == ModItems.med_ptsd) {
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 50, 49));
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("type", "vomit");
			nbt.setInteger("entity", player.getEntityId());
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(nbt, 0, 0, 0),  new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
			
			world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:entity.vomit", 1.0F, 1.0F);
		}
		
		if(this == ModItems.med_schizophrenia) {
		}
		
		if(this == ModItems.loop_stew) {
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20 * 20, 1));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60 * 20, 2));
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 1));
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 20 * 20, 2));
		}
		
		if(this == ModItems.canned_beef || 
				this == ModItems.canned_tuna || 
				this == ModItems.canned_mystery || 
				this == ModItems.canned_pashtet || 
				this == ModItems.canned_cheese || 
				this == ModItems.canned_jizz || 
				this == ModItems.canned_milk || 
				this == ModItems.canned_ass || 
				this == ModItems.canned_pizza || 
				this == ModItems.canned_tomato || 
				this == ModItems.canned_asbestos || 
				this == ModItems.canned_bhole || 
				this == ModItems.canned_hotdogs || 
				this == ModItems.canned_yogurt || 
				this == ModItems.canned_stew || 
				this == ModItems.canned_chinese || 
				this == ModItems.canned_oil || 
				this == ModItems.canned_fist || 
				this == ModItems.canned_spam || 
				this == ModItems.canned_fried || 
				this == ModItems.canned_napalm || 
				this == ModItems.canned_diesel || 
				this == ModItems.canned_kerosene || 
				this == ModItems.canned_recursion || 
				this == ModItems.canned_bark)
        	player.inventory.addItemStackToInventory(new ItemStack(ModItems.can_key));
		
		if(this == ModItems.canned_recursion && world.rand.nextInt(10) > 0)
        	player.inventory.addItemStackToInventory(new ItemStack(ModItems.canned_recursion));
    }

    public ItemStack onEaten(ItemStack stack, World worldObj, EntityPlayer player)
    {
        ItemStack sta = super.onEaten(stack, worldObj, player);
        
        if(this == ModItems.loop_stew)
        	return new ItemStack(Items.bowl);
        

    	
        if (this == ModItems.canned_bhole && !worldObj.isRemote) {
    		EntityVortex vortex = new EntityVortex(worldObj, 0.5F);
    		vortex.posX = player.posX;
    		vortex.posY = player.posY;
    		vortex.posZ = player.posZ;
    		worldObj.spawnEntityInWorld(vortex);
        }
        
        return sta;
        
    }

}
