package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;

public class SingleController extends ControlScheme {

    private Controller primary;

    public SingleController(Controller controller) {
        primary = controller;
    }


    //*********************************************Drive ******************************************//
    @Override
    public double getTankLeftSpeed() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getTankRightSpeed() {
        double speed = primary.getRightStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }


    @Override
    public double getTankThrottle(){
        double speed = primary.getLeftStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getTankWheel(){
        double wheel = primary.getRightStickX();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED && Math.abs(wheel) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            wheel = 0;
        }
        return wheel;
    }


    @Override
    public boolean doSwitchLowGear() {
        return primary.getRightTrigger();
    }
    
    @Override
    public boolean doFlipityFlop() {
        return primary.getLeftTrigger();
    }
    //*********************************************Drive ******************************************//



    //*********************************************Intake *****************************************//
    @Override
    public boolean doExtendIntake() {
        return false;
    }

    @Override
    public boolean doRetractIntake() {
        return primary.getAButtonToggled();
    }

    @Override
    public boolean doRunIntake() {
        return primary.getLeftBumper();
    }

    @Override
    public boolean doRunIntakeRev() {
        return primary.getRightBumper();
    }
    //*********************************************Intake *****************************************//





    //*********************************************Shooter ****************************************//
    @Override
    public boolean doToggleShooter() {
        return primary.getMenuButtonToggled();
    }

    @Override
    public boolean doRunPreShooter() {
        return primary.getXButton();
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
