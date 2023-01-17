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

    // Intake
    public abstract boolean doRunIntake();
    public abstract boolean doRunIntakeRev();
    public abstract boolean doExtendIntake();
    public abstract boolean doRetractIntake();


    // Indexer
    // public abstract boolean doRunIndexer();
    
    // Shooter
    public abstract boolean doToggleShooter();
    public abstract boolean doRunPreShooter();

    // Climb
    // public abstract boolean doToggleClimbMode();
    public abstract boolean doRaiseClimber();
    public abstract boolean doLowerClimber();
    public abstract boolean doArmExtend();
    public abstract boolean doArmRetract();
    public abstract double getElevatorSpeed();


}
