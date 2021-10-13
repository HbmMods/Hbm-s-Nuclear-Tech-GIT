package com.hbm.blocks.test;

import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.logic.EntityNukeExplosionNT;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityTestBombAdvanced;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//Testblock zum Rendern/�ndern der visuellen Eigenschaften eines Tiles (Blockes)

//"extends BlockContainer" um ein TileEntity zu erschaffen

public class TestBombAdvanced extends BlockContainer {
	
	//Lil' Boyy :3
	
	int bombStartStrength = 40;
	int bombStrengthA = 10;
	protected int timer1 = 20;
	int timer2 = timer1;

	//Normaler Matrial-Constructor
	public TestBombAdvanced(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	//Nicht verf�gbarer Rendertyp, setzt den Switch auf "Default" und erm�glicht einen Customrenderer
	@Override
	public int getRenderType(){
		return -1;
	}
	
	//Ob der Block transparent ist (Glas, Glowstone, Wasser, etc)
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	//"Default" beim Switch zum Rendern erm�glicht die Abfrage "renderAsNormalBlock", "true" um es als einen normalen Block rendern zu lassen, "false" f�r einen Customrenderer
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	//Erstellen eines TileEntitys
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTestBombAdvanced();
	}
	
	//GUI Blocktextur muss f�r Custommodel-Blocke nachtr�glich ge�ndert werden
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(RefStrings.MODID + ":test_render");
	}
	
	//Setzt die Blockkollisionsbox (xMin, yMin, zMin, xMax, yMax, zMax)
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        float f = 0.0625F;
        this.setBlockBounds(4*f, 0.0F, 0.0F, 12*f, 8*f, 1.0F);
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
            world.setBlockToAir(x, y, z);
            soos(world, x, y, z, bombStartStrength, bombStrengthA);
        }
    }
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
            this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
            p_149695_1_.setBlockToAir(x, y, z);
            soos(p_149695_1_, x, y, z, bombStartStrength, bombStrengthA);
        }
    }
	
	public boolean soos(World world, int x, int y, int z, int bombStartStrength, int bombStrengthA) //    <---Ettib�r taught me this
	{
		if (!world.isRemote)
		explode(world,x,y,z, bombStartStrength, bombStrengthA);
		world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0); //spawns a huge explosion particle
		world.playSoundEffect(x, y, z, "random.explode", 1.0f, world.rand.nextFloat() * 0.1F + 0.9F); //x,y,z,sound,volume,pitch
		return false;
	}
	
	public void explode(World world, int x, int y, int z, int bombStartStrength, int bombStrengthA)
	{
		/*int r = bombStartStrength; //radius of explosion (change this to bigger numbers for more epicness)
		int r2 = r*r; //radius^2, for faster distance checks. (No sqrt needed for pythagoras)
		int r22 = r2/2; //half of r^2, calculations outside the loop only get called once. Always pull out as many things from the loop as possible.
		for (int xx = -r; xx < r; xx++)
		{
			int X = xx+x; //x coordinate we are working on
			int XX = xx*xx; //more stuff for a faster distance check
			for (int yy = -r; yy < r; yy++)
			{
				int Y = yy+y; //y coord
				int YY = XX+yy*yy*3;
				for (int zz = -r; zz < r; zz++)
				{
					int Z = zz+z; //z coord
					int ZZ = YY+zz*zz; //final= x*x+y*y+z*z. remind you of anything?
					if (ZZ<r22+world.rand.nextInt(r22)) //and the distance check. x*x+y*y+z*z < radius^2 is the same as sqrt(x*x+y*y+z*z) < radius, the distance formula. Here we use a random number between r^2 and r^2/2 for a jagged explosion.
					{ //but since sqrt is slow we optimize the algorithm for super fast explosions! Yay!
						//faster explosions means more explosions per second! 
						world.setBlock(X, Y, Z, Blocks.air, 0, 3); //destroy the block if its within the radius
					} //you can change the if statement to if (ZZ<r2) for a smoother explosion crater.
				}
			}
		}*/
		
		world.setBlock(x, y, z, Blocks.air);
		world.spawnEntityInWorld(EntityNukeExplosionNT.statFacMKV(world, x + 0.5, y + 0.5, z + 0.5, 100));
		//world.spawnEntityInWorld(EntityNukeExplosionMK4.statFacNoRad(world, 50, x, y, z));
	}
}
