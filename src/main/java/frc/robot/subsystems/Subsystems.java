package frc.robot.subsystems;

import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.turret.Claw;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.drive.SparkTankDriveBase;
import frc.robot.subsystems.turret.drive.TankDriveBase;

public class Subsystems {

    public static final TankDriveBase driveBase;
    public static final Turret turret;
    public static final Claw claw;
    public static final AHRS navx;
    public static final LimeLight limeLight;

    static {
        driveBase = SparkTankDriveBase.getInstance();
        turret = Turret.getinstance();
        claw = Claw.getInstance();
        navx = new AHRS(SPI.Port.kMXP);
        limeLight = LimeLight.getInstance();
    }

    public static void setInitialStates() {
        driveBase.resetEncoders();
        driveBase.setHighGear();
    }

}
