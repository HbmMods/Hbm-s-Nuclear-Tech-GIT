package api.hbm.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IToolable {
	
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool);
	
	public static enum ToolType {
		SCREWDRIVER,
		HAND_DRILL,
		DEFUSER,
		WRENCH,
		TORCH,
		BOLT;
		
		public List<ItemStack> stacksForDisplay = new ArrayList();
		private static HashMap<ComparableStack, ToolType> map = new HashMap();
		
		public void register(ItemStack stack) {
			stacksForDisplay.add(stack);
		}
		
		public static ToolType getType(ItemStack stack) {
			
			if(!map.isEmpty()) {
				return map.get(new ComparableStack(stack));
			}
			
			for(ToolType type : ToolType.values()) {
				for(ItemStack tool : type.stacksForDisplay) {
					map.put(new ComparableStack(tool), type);
				}
			}
			
			return map.get(new ComparableStack(stack));
		}
	}
}
