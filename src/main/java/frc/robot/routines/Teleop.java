package frc.robot.routines;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.routines.auto.PIDAutoBalance;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;
import frc.robot.subsystems.turret.*;

public class Teleop {

    private PIDAutoBalance autoBalance;
    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Turret turret;
    private Claw claw;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        turret = Subsystems.turret;
        claw = Subsystems.claw;
    }

    public void init() {
        driveBase.resetEncoders();
        autoBalance = new PIDAutoBalance(true, controls);
    }

    public void periodic() {
        Subsystems.driveBase.setBrake(); // this doesn't work :(

        if (controls.getBrake()) {
            Subsystems.driveBase.setCoast();
            SmartDashboard.putBoolean("test", true);
        } else {
            SmartDashboard.putBoolean("test", false);

        }


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
        turret.setLimitOverride(controls.getLimitOverride());
        turret.move(controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());

        claw.stopSolenoid();
        // if (controls.doOpenClaw()) {
        // claw.stopSolenoid();
        // }
        // else if (controls.doCloseClaw()) {
        // claw.closeClaw();
        // }

        // SmartDashboard.putNumber("turretExtendRetract.posInch",
        // turretExtendRetract.getPositionInches());

        SmartDashboard.putBoolean("turret.swivelCentered", turret.isSwivelCentered());
        SmartDashboard.putBoolean("turret.isRetracted", turret.isFullyRetracted());
    }

}
