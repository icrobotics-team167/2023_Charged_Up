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

    // Turret (Secondary controller)

    // Claw
    public abstract boolean toggleClaw();

    // Arm controls
    public abstract double getArmSwivel();

    public abstract double getArmPivot();

    public abstract double getArmExtend();

    public abstract boolean doLimitOverride();

    public abstract boolean doResetTurret();
}
