package frc.robot.routines;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.routines.auto.AutoBalance;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;
import frc.robot.subsystems.turret.Claw;
import frc.robot.subsystems.turret.ExtendRetract;
import frc.robot.subsystems.turret.Pivot;
import frc.robot.subsystems.turret.Swivel;

public class Teleop {

    private AutoBalance autoBalance;
    private ControlScheme controls;
    private TankDriveBase driveBase;
    private ExtendRetract turretExtendRetract;
    private Pivot turretPivot;
    private Swivel turretSwivel;
    private Claw turretClaw;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        turretExtendRetract = ExtendRetract.getInstance();
        turretPivot = Pivot.getInstance();
        turretSwivel = Swivel.getInstance();
        turretClaw = Claw.getInstance();
    }

    public void init() {
        driveBase.resetEncoders();

        try {
            phCompressor = new Compressor(2, PneumaticsModuleType.REVPH);
            phCompressor.enableAnalog(60, 65);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating compressor: " + ex.getMessage(), true);
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
            turretExtendRetract.stop();
        }
        if (Math.abs(controls.getArmPivot()) > Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE) {
            turretPivot.move(controls.getArmPivot());
        } else {
            turretPivot.stop();
        }
        if (Math.abs(controls.getArmSwivel()) > Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE) {
            turretSwivel.move(controls.getArmSwivel());
        } else {
            turretSwivel.stop();
        }

        if (controls.doOpenClaw()) {
            turretClaw.openClaw();
        }
        else if (controls.doCloseClaw()) {
            turretClaw.closeClaw();
        }
        
        // SmartDashboard.putNumber("turretExtendRetract.posInch", turretExtendRetract.getPositionInches());
        SmartDashboard.putNumber("turretPivot.posDegrees", turretPivot.getPositionDegrees());
        SmartDashboard.putNumber("turretSwivel.posDegrees", turretSwivel.getPositionDegrees());
    }

}
