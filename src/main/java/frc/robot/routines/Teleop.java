package frc.robot.routines;

import com.kauailabs.navx.frc.AHRS;

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
    private LimeLight limeLight;
    private AHRS navx = Subsystems.navx;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        turret = Subsystems.turret;
        // swivel = Swivel.getInstance();
        claw = Subsystems.claw;
        limeLight = LimeLight.getInstance();
    }

    public void init() {
        driveBase.resetEncoders();
    }

    public void periodic() {
        if (controls.doSwitchHighGear()) {
            driveBase.setHighGear();
            driveBase.setNonSlowHighGear(true);
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
            driveBase.setNonSlowHighGear(false);
        }
        driveBase.setLowerGear(controls.doSlowMode());
        if (Config.Settings.TANK_DRIVE) {
            driveBase.tankDrive(controls.getTankLeftSpeed(),
                    controls.getTankRightSpeed());
        } else {
            driveBase.arcadeDrive(controls.getArcadeThrottle(),
                    controls.getArcadeWheel());
        }

        turret.setSlowMode(controls.doSlowTurret());

        if (controls.doResetTurret()) {
            claw.closeClaw();
            turret.moveTo(TurretPosition.INITIAL);
        } else if (controls.doSwivel180()) {
            turret.moveTo(turret.getPosition().withSwivel(180), 0.5);
        } else if (controls.doAutoMid()) {
            turret.moveTo(TurretPosition.MID_GOAL.withSwivel(turret.getPosition().swivelAngle()));
        } else if (controls.doAutoPickup()) {
            turret.moveTo(TurretPosition.INTAKE.withSwivel(turret.getPosition().swivelAngle()));
        } else if (controls.doPlayerStation()) {
            turret.moveTo(TurretPosition.PLAYER_STATION.withSwivel(turret.getPosition().swivelAngle()));
        } else {
            // turret.setLimitOverride(controls.doLimitOverride());
            turret.move(controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());
        }

        if (controls.openClaw()) {
            claw.openClaw();
        } else if (controls.closeClaw()) {
            claw.closeClaw();
        }

        if (controls.toggleLimelightMode()) {
            limeLight.toggleMode();
        }
        // PUT DEBUG STATEMENTS HERE
        SmartDashboard.putNumber("Pivot.position", Subsystems.turret.getPosition().pivotAngle());
        SmartDashboard.putNumber("Swivel.position", Subsystems.turret.getPosition().swivelAngle());
        SmartDashboard.putNumber("ExtendRetract.position", Subsystems.turret.getPosition().extensionPosition());
        SmartDashboard.putNumber("Navx.yaw", navx.getAngle());
        SmartDashboard.putNumber("Navx.pitch", navx.getPitch());

    }
}
