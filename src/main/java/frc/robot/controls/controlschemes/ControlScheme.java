package frc.robot.controls.controlschemes;

public abstract class ControlScheme {

    // Drive base
    public abstract double getTankLeftSpeed();

    public abstract double getTankRightSpeed();

    public abstract double getTankThrottle();

    public abstract double getTankWheel();

    // public abstract boolean doStraightDrive();

    // public abstract boolean doSwitchHighGear();

    public abstract boolean doSwitchLowGear();

    // public abstract boolean doToggleGearing();
    
    public abstract boolean doFlipityFlop();
}
