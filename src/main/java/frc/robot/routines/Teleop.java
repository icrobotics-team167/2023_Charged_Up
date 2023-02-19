package frc.robot.routines;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.routines.auto.AutoBalance;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;
import frc.robot.subsystems.turret.Swivel;

public class Teleop {

    AHRS navx;
    private AutoBalance autoBalance;
    private Compressor phCompressor;
    private ControlScheme controls;
    private TankDriveBase driveBase;
    // private Swivel turretSwivel;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
    }

    public void init() {
        driveBase.setLowGear();
        // driveBase.setCoast();
        driveBase.resetEncoders();

        try {
            phCompressor = new Compressor(2, PneumaticsModuleType.REVPH);
            phCompressor.enableAnalog(60, 65);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating compressor: " + ex.getMessage(), true);
        }
        try {
            /***********************************************************************
             * navX-MXP:
             * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.
             * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
             * 
             * navX-Micro:
             * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
             * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
             * 
             * Multiple navX-model devices on a single robot are supported.
             ************************************************************************/
            navx = new AHRS(SPI.Port.kMXP);
            DriverStation.reportError("Not really an error, successfully loaded navX", true);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        autoBalance = new AutoBalance(true, controls);
        // turretSwivel = new Swivel();
    }

    public void periodic() {
        if (controls.doSwitchHighGear()) {
            driveBase.setHighGear();
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        }

        SmartDashboard.putBoolean("High Gear", driveBase.isHighGear());
        SmartDashboard.putNumber("IMU_Pitch", navx.getPitch());

        driveBase.setLowerGear(controls.doLowerGear());
        if (controls.doAutoBalance()) {
            autoBalance.exec();
        } else {
            if (Config.Settings.TANK_DRIVE) {
                driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
            } else {
                driveBase.arcadeDrive(controls.getArcadeThrottle(), controls.getArcadeWheel());
            }
        }

    }

}
