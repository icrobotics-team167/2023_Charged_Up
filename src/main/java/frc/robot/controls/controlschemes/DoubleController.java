package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;

public class DoubleController extends ControlScheme {

    private Controller primary;
    private Controller secondary;

    public DoubleController(Controller primary, Controller secondary) {
        this.primary = primary;
        this.secondary = secondary;
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
        return secondary.getXButtonToggled();
    }

    @Override
    public boolean doRetractIntake() {
        return secondary.getAButtonToggled();
    }

    @Override
    public boolean doRunIntake() {
        return secondary.getLeftBumper();
        // return primary.getLeftBumper() || secondary.getLeftBumper();
    }

    @Override
    public boolean doRunIntakeRev() {
        if (!primary.getLeftBumper() || !secondary.getLeftBumper()){
            return secondary.getRightBumper();    
            // return primary.getRightBumper() || secondary.getRightBumper();    
        }
        else{
            return false;
        }
        
    }
    //*********************************************Intake *****************************************//


    


    //*********************************************Shooter ****************************************//
    @Override
    public boolean doToggleShooter() {
        return secondary.getMenuButtonToggled();
    }

    @Override
    public boolean doRunPreShooter() {
        return secondary.getRightTrigger();
    }
    //*********************************************Shooter ****************************************//


    //*********************************************Climber ****************************************//
    @Override
    public boolean doRaiseClimber() {
        return false;
        // return secondary.getRightBumper();
    }

    @Override
    public boolean doLowerClimber() {
        return false;
        // return secondary.getLeftBumper();
    }

    @Override
    public boolean doArmExtend() {
        // return false;
        return secondary.getYButtonToggled();
    }

    @Override
    public boolean doArmRetract() {
        // return true;
        return secondary.getBButtonToggled();
    }
    @Override
    public double  getElevatorSpeed() {
        double speed = secondary.getLeftStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }
    //*********************************************Climber ****************************************//
} 