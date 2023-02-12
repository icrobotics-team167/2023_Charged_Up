package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;

public class SingleController extends ControlScheme {

    private Controller primary;

    public SingleController(Controller controller) {
        primary = controller;
    }

    // Drive

    @Override
    public double getTankLeftSpeed() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getTankRightSpeed() {
        double speed = primary.getRightStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getArcadeThrottle() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getArcadeWheel() {
        double wheel = primary.getRightStickX();
        if (Config.Settings.TANK_DEAD_ZONE_ENABLED
                && Math.abs(wheel) < Math.abs(Config.Tolerances.TANK_DEAD_ZONE_SIZE)) {
            wheel = 0;
        }
        return wheel;
    }

    @Override
    public boolean doSwitchHighGear() {
        return primary.getRightBumper();
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getRightTrigger();
    }

    @Override
    public boolean doAutoBalance() {
        return primary.getYButton();
    }

    @Override
    public boolean doOpenClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doCloseClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getArmSwivel() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getArmPivot() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getArmExtend() {
        // TODO Auto-generated method stub
        return 0;
    }
}
