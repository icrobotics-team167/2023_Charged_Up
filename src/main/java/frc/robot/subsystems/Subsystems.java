package frc.robot.subsystems;

import frc.robot.subsystems.drive.SparkTankDriveBase;
import frc.robot.subsystems.drive.TankDriveBase;
import frc.robot.subsystems.turret.Claw;
import frc.robot.subsystems.turret.Turret;

public class Subsystems {

    public static final TankDriveBase driveBase;
    public static final Turret turret;
    public static final Claw claw;

    static {
        driveBase = SparkTankDriveBase.getInstance();
        turret = Turret.getinstance();
        claw = Claw.getInstance();
    }

    public static void setInitialStates() {
        driveBase.resetEncoders();
        driveBase.setHighGear();
    }

}
