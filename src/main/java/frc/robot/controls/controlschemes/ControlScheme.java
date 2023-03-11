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

    public abstract boolean doSlowMode();

    // Turret (Secondary controller)

    // Claw
    public abstract boolean openClaw();

    public abstract boolean closeClaw();

    // Arm controls
    public abstract double getArmSwivel();

    public abstract double getArmPivot();

    public abstract double getArmExtend();

    public abstract boolean doLimitOverride();

    public abstract boolean doResetTurret();

    public abstract boolean doSlowTurret();

    public abstract boolean doLockSwivel();
    
    public abstract boolean doAutoHigh();

    public abstract boolean doAutoMid();

    public abstract boolean doAutoPickup();

    public abstract boolean doPlayerStation();

    public abstract boolean doSwivel180();

}
