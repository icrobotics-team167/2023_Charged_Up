package frc.robot.subsystems;

import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.drive.SparkTankDriveBase;
import frc.robot.subsystems.drive.TankDriveBase;
import frc.robot.subsystems.turret.Claw;
import frc.robot.subsystems.turret.Turret;

public class Subsystems {

    public static final TankDriveBase driveBase;
    public static final Turret turret;
    public static final Claw claw;
    public static final AHRS navx;

    static {
        driveBase = SparkTankDriveBase.getInstance();
        turret = Turret.getinstance();
        claw = Claw.getInstance();
        navx = new AHRS(SPI.Port.kMXP);
    }

    public static void setInitialStates() {
        driveBase.resetEncoders();
        driveBase.setHighGear();
    }

}
