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
import frc.robot.subsystems.turret.ExtendRetract;
import frc.robot.subsystems.turret.Pivot;
import frc.robot.subsystems.turret.Swivel;

public class Teleop {

    private AHRS navx;
    private AutoBalance autoBalance;
    private Compressor phCompressor;
    private ControlScheme controls;
    private TankDriveBase driveBase;
    private ExtendRetract turretExtendRetract;
    private Pivot turretPivot;
    private Swivel turretSwivel;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        turretExtendRetract = ExtendRetract.getInstance();
        turretPivot = Pivot.getInstance();
        turretSwivel = Swivel.getInstance();
    }

    public void init() {
        driveBase.setLowGear();
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
    }

    public void periodic() {
        if (controls.doSwitchHighGear()) {
            driveBase.setHighGear();
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        }

        driveBase.setLowerGear(controls.doLowerGear());
        if (controls.doAutoBalance()) {
            autoBalance.exec();
        } else {
            if (Config.Settings.TANK_DRIVE) {
                driveBase.tankDrive(controls.getTankLeftSpeed(),
                        controls.getTankRightSpeed());
            } else {
                driveBase.arcadeDrive(controls.getArcadeThrottle(),
                        controls.getArcadeWheel());
            }
        }

        if (Math.abs(controls.getArmExtend()) > Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE) {
            turretExtendRetract.move(controls.getArmExtend());
        } else {
            turretExtendRetract.move(0);
        }
        if (Math.abs(controls.getArmPivot()) > Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE) {
            turretPivot.move(controls.getArmPivot());
        } else {
            turretPivot.move(0);
        }
        if (Math.abs(controls.getArmSwivel()) > Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE) {
            turretSwivel.move(controls.getArmSwivel());
        } else {
            turretSwivel.move(0);
        }
    }

}
