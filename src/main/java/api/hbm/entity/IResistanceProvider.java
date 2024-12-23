package api.hbm.entity;

import net.minecraft.util.DamageSource;

/**
 * Allows custom entities to specify threshold and resistance values based on incoming damage, type and piercing values, along with whatever other internal stats
 * the entity has (like glyphid armor). Obviously only works for our own custom entities implementing this, everything else will have to work with the less powerful
 * (but still very useful) entity stats in the DamageResistanceHandler.
 * 
 * @author hbm
 */
public interface IResistanceProvider {

	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce);
}
