package frc.robot.subsystems;

import frc.robot.subsystems.drive.SparkTankDriveBase;
import frc.robot.subsystems.drive.TankDriveBase;

public class Subsystems {

    public static final TankDriveBase driveBase;

    static {
        driveBase = SparkTankDriveBase.getInstance();
    }

    public static void setInitialStates() {
        driveBase.resetEncoders();
        driveBase.setHighGear();
    }

}
