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

        if (controls.toggleLimelight()) {
            limeLight.toggleMode();
        }
        // turret.setSlowMode(controls.doSlowTurret());

        // Code so that u only have to press one button and it will automatically go to it. Do not commit until tested

        if (controls.doResetTurret()) {
            claw.closeClaw();
            targetState = TurretPosition.INITIAL;
        } else if (controls.doSwivelNorth()) {
            targetState = turret.getPosition().withSwivel(0);
        } else if (controls.doSwivelEast()) {
            targetState = turret.getPosition().withSwivel(90);
        } else if (controls.doSwivelSouth()) {
            if(turret.getPosition().swivelAngle() < 0) {
                targetState = turret.getPosition().withSwivel(-180);
            } else {
                targetState = turret.getPosition().withSwivel(180);
            }
        } else if (controls.doSwivelWest()) {
            targetState = turret.getPosition().withSwivel(-90);
        } else if (controls.doAutoPickup()) {
            targetState = TurretPosition.INTAKE.withSwivel(turret.getPosition().swivelAngle());
        } else if (controls.doPlayerStation()) {
            claw.openClaw();
            targetState = TurretPosition.PLAYER_STATION.withSwivel(turret.getPosition().swivelAngle());
        // Preset positions are done from the perspective of the driver
        } else if(controls.doAutoHigh()) {
            targetState = TurretPosition.HIGH_MID;
        } else if (controls.doAutoMid()) {
            targetState = TurretPosition.MID_MID;
        } else if(controls.doAutoHighRight()) {
            targetState = TurretPosition.HIGH_RIGHT;
        } else if(controls.doAutoMidRight()) {
            targetState = TurretPosition.MID_RIGHT;
        } else if(controls.doAutoHighLeft()) {
            targetState = TurretPosition.HIGH_LEFT;
        } else if(controls.doAutoMidLeft()) {
            targetState = TurretPosition.MID_LEFT; 
        } else {
            if(Math.abs(controls.getArmPivot()) > 0.1 || Math.abs(controls.getArmSwivel()) > 0.1 || Math.abs(controls.getArmExtend()) > 0.1) {
                targetState = null;
                if(controls.doUnlockSwivel()) {
                    turret.move(controls.getArmPivot(), controls.getArmSwivel(), controls.getArmExtend());
                } else {
                    turret.move(controls.getArmPivot(), 0, controls.getArmExtend());
                }          
            } else {
                if(targetState!=null) {
                    turret.moveTo(targetState);
                } else {
                    turret.stop();
                }
            }
        }
        
        if (controls.openClaw()) {
            claw.openClaw();
        } else if (controls.closeClaw()) {
            claw.closeClaw();
        } else if (controls.toggleClaw()) {
            claw.toggleClaw();
        }

        // PUT DEBUG STATEMENTS HERE
        SmartDashboard.putNumber("Pivot.position", Subsystems.turret.getPosition().pivotAngle());
        SmartDashboard.putNumber("Swivel.position", Subsystems.turret.getPosition().swivelAngle());
        SmartDashboard.putNumber("ExtendRetract.position", Subsystems.turret.getPosition().extensionPosition());
        SmartDashboard.putNumber("Navx.yaw", navx.getAngle());
        SmartDashboard.putNumber("Navx.pitch", navx.getPitch());

    }

}
