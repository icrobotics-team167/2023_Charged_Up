/*
 * See where the joystick is
 * Move a motor with that value
 * 
 * If a button is clicked switch to a motor
 * When clicked again switch back or to another motor 
 * 
 * Ideas:
 * Have all the motors be part of an array?
 * When the button to switch motors is clicked add one to a value until it exceeds the value of the last index of the array
 */
 

package frc.robot.routines;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.subsystems.*;
import frc.robot.subsystems.turret.*;
import frc.robot.subsystems.turret.drive.TankDriveBase;
import frc.robot.util.MathUtils;

public class Teleop {
    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Turret turret;
    // private Swivel swivel;
    private Claw claw;
    private LimeLight limeLight;
    private AHRS navx = Subsystems.navx;
    private int pressedCheck;
    private int pressCount;

    private TurretPosition targetState = null;
    private TurretPosition holdState;

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
        holdState = turret.getPosition();
    }

    public void periodic() {
        /*
         * This code has been modifidied for the freshman to learn to code the robot.
         * Commit before this is the last safe commit.
         */


        // pressedCheck makes sure that rightTrigger became false before becoming true again so it doesn't add to pressedCount every tick
        // pressCount is how many times the rightTrigger has been pressed unless it becomes 2.
        boolean isRightTriggerPressed = controls.doSwitchHighGear();

        // If right trigger is pressed add 1 to pressedCheck
        if(isRightTriggerPressed == true) {
            pressedCheck++;
        } else { // If right trigger isn't pressed reset pressedCheck
            pressedCheck = 0;
        } // If pressedCheck is 1 add one to pressCount

        if(pressedCheck==1) {
            pressCount++;
        } // If pressCount is 2 reset pressCount back to 0 as press count should only be 1 or 0

        if(pressCount == 2) {
            pressCount = 0;
        } else if(pressCount == 1) { // If pressCount is 1 only move the left side of the robot
            driveBase.tankDrive(controls.getTankLeftSpeed(), 0);
        } else if(pressCount == 0) { // If pressCount is 0 only move the right side of the robot
            driveBase.tankDrive(0, controls.getTankRightSpeed());
        }

        double leftJoystickPosition = controls.getTankLeftSpeed(); //  Value of where the left joystick is
        SmartDashboard.putNumber("getTankLeftSpeed", leftJoystickPosition);
        SmartDashboard.putNumber("Press count", pressCount);










        // if (Config.Settings.TANK_DRIVE) {
        //     driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
        // } else {
        //     SmartDashboard.putNumber("Speed", controls.getSpeed());
        //     driveBase.arcadeDrive(controls.getArcadeThrottle() * MathUtils.clamp(controls.getSpeed(), Config.Settings.MINIMUM_SPEED, 1.0) * Config.Settings.DRIVE_SPEED,
        //             controls.getArcadeWheel() * MathUtils.clamp(controls.getSpeed(), Config.Settings.MINIMUM_SPEED, 1.0) * Config.Settings.TURN_SPEED);
        // }

        // if (controls.toggleLimelight()) {
        //     limeLight.toggleMode();
        // }
        // // turret.setSlowMode(controls.doSlowTurret());

        // // Code so that u only have to press one button and it will automatically go to
        // // it.

        // int swivelOffset = controls.getPositionOffset();
        // if (controls.doResetTurret()) {
        //     targetState = TurretPosition.INITIAL;
        // }
        // } else if (controls.doAutoPickup()) {
        // targetState =
        // TurretPosition.INTAKE.withSwivel(turret.getPosition().swivelAngle()).addSwivelOffset(-swivelOffset);
        // } else if (controls.doPlayerStation()) {
        // targetState =
        // TurretPosition.PLAYER_STATION.withSwivel(turret.getPosition().swivelAngle()).addSwivelOffset(-swivelOffset);
        // } else if (controls.doAutoHigh()) {
        // targetState = TurretPosition.HIGH_MID;
        // } else if (controls.doAutoMid()) {
        // targetState = TurretPosition.MID_MID;
        // } else if (controls.doAutoHighRight()) {
        // targetState = TurretPosition.HIGH_RIGHT;
        // } else if (controls.doAutoMidRight()) {
        // targetState = TurretPosition.MID_RIGHT;
        // } else if (controls.doAutoHighLeft()) {
        // targetState = TurretPosition.HIGH_LEFT;
        // } else if (controls.doAutoMidLeft()) {
        // targetState = TurretPosition.MID_LEFT;
        // } else if (controls.offsetUpdated()) {
        // targetState = TurretPosition.INITIAL;
        // }
        // double swivel = controls.doUnlockSwivel() ? controls.getArmSwivel() : 0;
        // double swivel = controls.getArmSwivel();
        // if (Math.abs(controls.getArmPivot()) > 0 || Math.abs(swivel) > 0 ||
        // Math.abs(controls.getArmExtend()) > 0) {
        // targetState = null;
        // turret.move(controls.getArmPivot(), swivel, controls.getArmExtend());
        // holdState = turret.getPosition();
        // } else {
        // if (targetState != null) {
        // turret.moveTo(targetState.addSwivelOffset(swivelOffset));
        // } else {
        // turret.moveTo(holdState);
        // }
        // }

        // if (controls.intake()) {
        //     claw.intake();
        // } else if (controls.outtake()) {
        //     claw.outtake();
        // } else {
        //     claw.stop();
        // }

        // // PUT DEBUG STATEMENTS HERE
        // SmartDashboard.putNumber("Pivot.position", Subsystems.turret.getPosition().pivotAngle());
        // SmartDashboard.putNumber("Swivel.position", Subsystems.turret.getPosition().swivelAngle());
        // SmartDashboard.putNumber("ExtendRetract.position", Subsystems.turret.getPosition().extensionPosition());
        // SmartDashboard.putNumber("Navx.yaw", navx.getAngle());
        // SmartDashboard.putNumber("Navx.pitch", navx.getPitch());

    }

}
