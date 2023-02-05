package frc.robot.controls.controlschemes;

public class NullController extends ControlScheme {

    // Drive
    
    @Override
    public double getTankLeftSpeed() {
        return 0;
    }

    @Override
    public double getTankRightSpeed() {
        return 0;
    }

    @Override
    public double getTankThrottle() {
        return 0;
    }

    @Override
    public double getTankWheel() {
        return 0;
    }

    @Override
    public boolean doSwitchLowGear() {
        return false;
    }

    @Override
    public boolean doFlipityFlop() {
        return false;
    }

    // Drive
}
