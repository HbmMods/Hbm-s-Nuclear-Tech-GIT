package api.hbm.entity;

import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

public interface ITurretTargetOptional {

    /**
     * Specifies if it's a target or not
     */
    boolean isTarget(TileEntityTurretBaseNT turret);

    /**
     * If returned true, continue with other target checks
     */
    boolean ignoreThis(TileEntityTurretBaseNT turret);

}
