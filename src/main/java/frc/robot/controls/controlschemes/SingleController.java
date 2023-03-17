package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;

public class SingleController extends ControlScheme {

    private Controller primary;

    public SingleController(Controller controller) {
        primary = controller;
    }

    // Drive

    @Override
    public double getTankLeftSpeed() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getTankRightSpeed() {
        double speed = primary.getRightStickY();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getArcadeThrottle() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getArcadeWheel() {
        double wheel = primary.getRightStickX();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(wheel) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            wheel = 0;
        }
        return wheel;
    }

    @Override
    public boolean doSwitchHighGear() {
        return primary.getRightTrigger();
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getRightBumper();
    }

    @Override
    public boolean doSlowMode() {
        return primary.getLeftTrigger();
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

    @Override
    public boolean doLimitOverride() {
        return false;
    }

    @Override
    public boolean doResetTurret() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean openClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean closeClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSlowTurret() {
        // TODO Auto-generated method stub
        return false;
    }

    // @Override
    // public boolean doLockSwivel() {
    //     // TODO Auto-generated method stub
    //     return false;
    // }

    @Override
    public boolean doAutoHigh() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoMid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoPickup() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doPlayerStation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivelNorth() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivelEast() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivelSouth() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivelWest() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getPreset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean doUnlockSwivel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean toggleClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean toggleLimelight() {
        // TODO Auto-generated method stub
        return false;
    }

	@Override
	public boolean doAutoHighLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doAutoMidLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doAutoHighRight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doAutoMidRight() {
		// TODO Auto-generated method stub
		return false;
	}
}
