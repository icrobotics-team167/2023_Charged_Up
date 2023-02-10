package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

public interface DriveForwardsCondition {
    public boolean call(AHRS navx);
}