package frc.robot.routines;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;
import frc.robot.subsystems.turret.*;

public class Teleop {
    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Turret turret;
    // private Swivel swivel;
    private Claw claw;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        turret = Subsystems.turret;
        // swivel = Swivel.getInstance();
        claw = Subsystems.claw;
    }

    public void init() {
        driveBase.resetEncoders();
    }

    public void periodic() {
        if (controls.doSwitchHighGear()) {
            driveBase.setHighGear();
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        }
        if(driveBase.isHighGear()) {
            driveBase.setLowerGear(controls.doLowerGear());
        }
        if (Config.Settings.TANK_DRIVE) {
            driveBase.tankDrive(controls.getTankLeftSpeed(),
                    controls.getTankRightSpeed());
        } else {
                driveBase.arcadeDrive(controls.getArcadeThrottle(),
                        controls.getArcadeWheel());
        }

        turret.setSlowMode(controls.doSlowTurret());
        turret.lockSwivel(controls.doLockSwivel());

        if (controls.doResetTurret()) {
            SmartDashboard.putBoolean("Teleop.turretResetDone", turret.moveTo(TurretPosition.INITIAL));
            SmartDashboard.putBoolean("Teleop.resettingTurret", true);
        // } else if (controls.doAutoHigh()) {
        //     turret.moveTo(TurretPosition.HIGH_GOAL.withSwivel(turret.getPosition().swivelAngle()));
        // }
        // else if (controls.doAutoMid()) {
        //     turret.moveTo(TurretPosition.MID_GOAL.withSwivel(turret.getPosition().swivelAngle()));
        // }
        // else if (controls.doAutoPickup()) {
        //     turret.moveTo(TurretPosition.INTAKE.withSwivel(turret.getPosition().swivelAngle()));
        // }
        // else if (controls.doPlayerStation()) {
        //     turret.moveTo(TurretPosition.PLAYER_STATION.withSwivel(turret.getPosition().swivelAngle()));
        } else if (controls.doSwivel180()) {
            turret.moveTo(turret.getPosition().withSwivel(180));
        } else {
            // turret.setLimitOverride(controls.doLimitOverride();
            turret.move(controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());
            SmartDashboard.putBoolean("Teleop.resettingTurret", false);
        }

        if (controls.openClaw()) {
            claw.openClaw();
        } else if (controls.closeClaw()) {
            claw.closeClaw();
        }

        // PUT DEBUG STATEMENTS HERE
        SmartDashboard.putNumber("Pivot.position", Subsystems.turret.getPosition().pivotAngle());
        SmartDashboard.putNumber("Swivel.position", Subsystems.turret.getPosition().swivelAngle());
        SmartDashboard.putNumber("ExtendRetract.position", Subsystems.turret.getPosition().extensionPosition());
    }
}
