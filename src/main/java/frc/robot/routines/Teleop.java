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

    private TurretPosition targetState = null;

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
            driveBase.setNonSlowHighGear();
        } else if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
            driveBase.setNonSlowLowGear();
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

        if(targetState!=null) {
            turret.moveTo(targetState);
        }

        if (controls.doResetTurret()) {
            // claw.closeClaw();
            turret.moveTo(TurretPosition.INITIAL);
        } else if (controls.doSwivelNorth()) {
            turret.moveTo(turret.getPosition().withSwivel(0), 0.5);
        } else if (controls.doSwivelEast()) {
            turret.moveTo(turret.getPosition().withSwivel(90), 0.5);
        } else if (controls.doSwivelSouth()) {
            if(turret.getPosition().swivelAngle() < 0) {
                turret.moveTo(turret.getPosition().withSwivel(-180), 0.5);
            } else {
                turret.moveTo(turret.getPosition().withSwivel(180), 0.5);
            }
        } else if (controls.doSwivelWest()) {
            turret.moveTo(turret.getPosition().withSwivel(-90), 0.5);
        } else if (controls.doAutoPickup()) {
            turret.moveTo(TurretPosition.INTAKE.withSwivel(turret.getPosition().swivelAngle()));
        } else if (controls.doPlayerStation()) {
            claw.openClaw();
            turret.moveTo(TurretPosition.PLAYER_STATION.withSwivel(turret.getPosition().swivelAngle()));
        // Preset positions are done from the perspective of the driver
        } else if (controls.getPreset() == 0) {
            if(controls.doAutoHigh()) {
                turret.moveTo(TurretPosition.HIGH_MID);
            } else if (controls.doAutoMid()) {
                turret.moveTo(TurretPosition.MID_MID);
            } else {
                if(controls.doUnlockSwivel()) {
                    turret.move(controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());
                } else {
                    turret.move(controls.getArmPivot(), 0, controls.getArmExtend());
                }
                
            }
        } else if (controls.getPreset() == 1) {
            if(controls.doAutoHigh()) {
                turret.moveTo(TurretPosition.HIGH_RIGHT);
            } else if(controls.doAutoMid()) {
                turret.moveTo(TurretPosition.MID_RIGHT);
            }
        } else if (controls.getPreset() == -1) {
            if(controls.doAutoHigh()) {
                turret.moveTo(TurretPosition.HIGH_LEFT);
            } else if(controls.doAutoMid()) {
                turret.moveTo(TurretPosition.MID_LEFT);
            } 
        } else {
            turret.stop();
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
        SmartDashboard.putNumber("Navx.yaw", navx.getAngle());
        SmartDashboard.putNumber("Navx.pitch", navx.getPitch());

    }

}
