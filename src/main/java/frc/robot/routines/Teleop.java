package frc.robot.routines;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.routines.auto.AutoBalance;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;
import frc.robot.subsystems.turret.*;

public class Teleop {

    private AutoBalance autoBalance;
    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Turret turret;
    private Claw turretClaw;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        turret = Turret.getinstance();
        turretClaw = Claw.getInstance();
    }

    public void init() {
        driveBase.resetEncoders();
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

        turret.setLimitOverride(controls.getLimitOverride());
        turret.move(controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());

        turretClaw.stopSolenoid();
        // if (controls.doOpenClaw()) {
        // turretClaw.stopSolenoid();
        // }
        // else if (controls.doCloseClaw()) {
        // turretClaw.closeClaw();
        // }

        // SmartDashboard.putNumber("turretExtendRetract.posInch",
        // turretExtendRetract.getPositionInches());

    }

}
