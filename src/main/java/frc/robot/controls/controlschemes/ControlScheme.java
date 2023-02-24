package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    // Driving (Primary controller)

    // Tank Drive
    public abstract double getTankLeftSpeed();

    public abstract double getTankRightSpeed();

    // Arcade Drive
    public abstract double getArcadeThrottle();

    public abstract double getArcadeWheel();

    // Gearing
    public abstract boolean doSwitchLowGear();

    public abstract boolean doSwitchHighGear();
    
    public abstract boolean doLowerGear();

    // Autobalance
    public abstract boolean doAutoBalance();

    public abstract boolean toggleCameraMode();

    // Turret (Secondary controller)

    // Claw
    public abstract boolean doOpenClaw();

    public abstract boolean doCloseClaw();

    // Arm controls
    public abstract double getArmSwivel();

    public abstract double getArmPivot();

    public abstract double getArmExtend();
}
