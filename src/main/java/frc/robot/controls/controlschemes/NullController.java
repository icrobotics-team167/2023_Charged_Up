package frc.robot.controls.controlschemes;

public class NullController extends ControlScheme {

    //*********************************************Drive ******************************************//
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
    //*********************************************Drive ******************************************//
   
   

    //*********************************************Intake *****************************************//
    @Override
    public boolean doExtendIntake() {
        return false;
    }

    @Override
    public boolean doRetractIntake() {
        return true;
    }

    @Override
    public boolean doRunIntake() {
        return false;
    }

    @Override
    public boolean doRunIntakeRev() {
        return false;
    }
    //*********************************************Intake *****************************************//

    //*********************************************Shooter ****************************************//
    @Override
    public boolean doToggleShooter() {
        return false;
    }

    @Override
    public boolean doRunPreShooter() {
        return false;
    }
    //*********************************************Shooter ****************************************//

    //*********************************************Climber ****************************************//
    @Override
    public boolean doRaiseClimber() {
        return false;
        // return primary.getRightBumper();
    }

    @Override
    public boolean doLowerClimber() {
        return false;
        // return primary.getLeftBumper();
    }

    @Override
    public boolean doArmExtend() {
        return false;
        // return primary.getLeftBumper();
    }

    @Override
    public boolean doArmRetract() {
        return true;
        // return primary.doArmRetract();
    }

    @Override
    public double  getElevatorSpeed() {
        return 0;
    }
    //*********************************************Climber ****************************************//

}




