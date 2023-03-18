package frc.robot.controls.controlschemes;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.controls.controllers.Controller;

/**
 * Delta is the fourth Greek letter and we have four hypothetical joysticks. Q.E.D.
 */
public class DeltaJoystickController extends ControlScheme {

    private Controller primary;
    private Controller secondary;
    private Controller tertiary;

    public DeltaJoystickController(Controller primary, Controller secondary, Controller tertiary) {
        this.primary = primary;
        this.secondary = secondary;
        this.tertiary = tertiary;
    }

    @Override
    public double getTankLeftSpeed() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getTankRightSpeed() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getArcadeThrottle() {
        return primary.getLeftStickY();
        
    }

    @Override
    public double getArcadeWheel() {
        return -secondary.getLeftStickX();
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getLeftTrigger();
    }

    @Override
    public boolean doSwitchHighGear() {
        return secondary.getLeftTrigger();
    }

    @Override
    public boolean doSlowMode() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean toggleClaw() {
        return tertiary.getLeftTriggerPressed();
    }

    @Override
    public boolean openClaw() {
        return false;
    }

    @Override
    public boolean closeClaw() {
        return false;
    }

    @Override
    public double getArmSwivel() {
        return -tertiary.getLeftStickX();
    }

    @Override
    public double getArmPivot() {
        return -tertiary.getLeftStickY();
    }

    @Override
    public double getArmExtend() {
        if(tertiary.getButtonPressedById(5)){
            return 1;
        } else if(tertiary.getButtonPressedById(3)) {
            return -1;
        } else {
            return 0;
        }
        
    }

    @Override
    public boolean doLimitOverride() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doResetTurret() {
        return tertiary.getButtonPressedById(2);
    }

    @Override
    public boolean doSlowTurret() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doUnlockSwivel() {
        return tertiary.getLeftTrigger();
    }

    @Override
    public boolean doAutoHigh() {
		return tertiary.getButtonPressedById(10);
    }

    @Override
    public boolean doAutoMid() {
		return tertiary.getButtonPressedById(9);
    }

    @Override
    public boolean doAutoPickup() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doPlayerStation() {
        return tertiary.getButtonPressedById(6);
    }

    @Override
    public boolean doSwivelNorth() {
        return tertiary.getDPadUp();
    }

    @Override
    public boolean doSwivelEast() {
        return tertiary.getDPadRight();
    }

    @Override
    public boolean doSwivelSouth() {
        return tertiary.getDPadDown();
    }

    @Override
    public boolean doSwivelWest() {
        return tertiary.getDPadLeft();
    }

    @Override
    public double getPreset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean toggleLimelight() {
        return secondary.getRawButtonPressedById(16);
    }

	@Override
	public boolean doAutoHighLeft() {
		return tertiary.getButtonPressedById(8);
	}

	@Override
	public boolean doAutoMidLeft() {
		return tertiary.getButtonPressedById(7);
	}

	@Override
	public boolean doAutoHighRight() {
		return tertiary.getButtonPressedById(12);
	}

	@Override
	public boolean doAutoMidRight() {
		return tertiary.getButtonPressedById(11);
	}

    
    
}
