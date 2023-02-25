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
        if (Config.Settings.TANK_DRIVE) {
            driveBase.tankDrive(controls.getTankLeftSpeed(),
                    controls.getTankRightSpeed());
        } else {
            driveBase.arcadeDrive(controls.getArcadeThrottle(),
                    controls.getArcadeWheel());
        }
        if (controls.doResetTurret()) {
            turret.moveTo(TurretPosition.INITIAL);
        } else {
            turret.setLimitOverride(controls.doLimitOverride());
            turret.move(controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());
        }
        turret.setLimitOverride(controls.doLimitOverride());
        turret.move(-controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());

        if (controls.doOpenClaw()) {
        claw.openClaw();
        }
        else if (controls.doCloseClaw()) {
        claw.closeClaw();
        }

        // PUT DEBUG STATEMENTS HERE
        SmartDashboard.putNumber("Pivot.position",Subsystems.turret.getPosition().pivotAngle());
        SmartDashboard.putNumber("Swivel.position", Subsystems.turret.getPosition().swivelAngle());
        SmartDashboard.putNumber("ExtendRetract.position", Subsystems.turret.getPosition().extensionPosition());
    }

}
