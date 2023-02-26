package frc.robot.controls.controllers;

// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Config;
import frc.robot.util.MathUtils;

public class PSController implements Controller {

    private XboxController controller;
    private int port;

    public PSController(int port) {
        controller = new XboxController(port);
        this.port = port;
        System.out.println("PS POV Count: " + controller.getPOVCount());
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isXBController() {
        return false;
    }

    @Override
    public boolean isPSController() {
        return true;
    }

    @Override
    public double getLeftTriggerValue() {
        return (controller.getRawAxis(3) + 1) / 2;
    }

    @Override
    public boolean getLeftTrigger() {
        return controller.getBackButton();
    }

    private boolean leftTriggerPrevious = false;

    private boolean getBackButtonPressed() {
        boolean result = !leftTriggerPrevious && getLeftTrigger();
        leftTriggerPrevious = getLeftTrigger();
        return result;
    }

    private boolean leftTriggerToggle = false;

    @Override
    public boolean getLeftTriggerToggled() {
        if (getBackButtonPressed()) {
            leftTriggerToggle = !leftTriggerToggle;
        }
        return leftTriggerToggle;
    }

    @Override
    public boolean getLeftBumper() {
        return controller.getLeftBumper();
    }

    private boolean leftBumperToggle = false;

    @Override
    public boolean getLeftBumperToggled() {
        if (controller.getLeftBumperPressed()) {
            leftBumperToggle = !leftBumperToggle;
        }
        return leftBumperToggle;
    }

    @Override
    public double getRightTriggerValue() {
        return (controller.getRawAxis(4) + 1) / 2;
    }

    @Override
    public boolean getRightTrigger() {
        return controller.getStartButton();
    }

    private boolean rightTriggerPrevious = false;

    private boolean getRightTriggerPressed() {
        boolean result = !rightTriggerPrevious && getRightTrigger();
        rightTriggerPrevious = getRightTrigger();
        return result;
    }

    private boolean rightTriggerToggle = false;

    @Override
    public boolean getRightTriggerToggled() {
        if (getRightTriggerPressed()) {
            rightTriggerToggle = !rightTriggerToggle;
        }
        return rightTriggerToggle;
    }

    @Override
    public boolean getRightBumper() {
        return controller.getRightBumper();
    }

    private boolean rightBumperToggle = false;

    @Override
    public boolean getRightBumperToggled() {
        if (controller.getRightBumperPressed()) {
            rightBumperToggle = !rightBumperToggle;
        }
        return rightBumperToggle;
    }

    @Override
    public double getLeftStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(controller.getRawAxis(0),2) * MathUtils.getSign(controller.getRawAxis(0));
        }
        return controller.getRawAxis(0);
    }

    @Override
    public double getLeftStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(controller.getRawAxis(1),2) * MathUtils.getSign(controller.getRawAxis(1));
        }
        return -controller.getRawAxis(1);
    }

    @Override
    public boolean getLeftStickButton() {
        return controller.getRawButton(11);
    }

    private boolean leftStickButtonToggle = false;

    @Override
    public boolean getLeftStickButtonToggled() {
        if (controller.getRawButtonPressed(11)) {
            leftStickButtonToggle = !leftStickButtonToggle;
        }
        return leftStickButtonToggle;
    }

    @Override
    public double getRightStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(controller.getRawAxis(2),2) * MathUtils.getSign(controller.getRawAxis(2));
        }
        return controller.getRawAxis(2);
    }

    @Override
    public double getRightStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(controller.getRawAxis(5),2) * MathUtils.getSign(controller.getRawAxis(5));
        }
        return -controller.getRawAxis(5);
    }

    @Override
    public boolean getRightStickButton() {
        return controller.getRawButton(12);
    }

    private boolean rightStickButtonToggle = false;

    @Override
    public boolean getRightStickButtonToggled() {
        if (controller.getRawButtonPressed(12)) {
            rightStickButtonToggle = !rightStickButtonToggle;
        }
        return rightStickButtonToggle;
    }

    @Override
    public boolean getAButton() {
        return controller.getBButton();
    }

    @Override
    public boolean getAButtonToggled() {
        return controller.getBButtonPressed();
    }

    @Override
    public boolean getBButton() {
        return controller.getXButton();
    }

    @Override
    public boolean getBButtonToggled() {
        return controller.getXButtonPressed();
    }

    @Override
    public boolean getXButton() {
        return controller.getAButton();
    }

    @Override
    public boolean getXButtonToggled() {
        return controller.getAButtonPressed();
    }

    @Override
    public boolean getYButton() {
        return controller.getYButton();
    }

    @Override
    public boolean getYButtonToggled() {
        return controller.getYButtonPressed();
    }

    @Override
    public boolean getViewButton() {
        return controller.getLeftStickButton();
    }

    @Override
    public boolean getViewButtonToggled() {
        return controller.getLeftStickButtonPressed();
    }

    @Override
    public boolean getMenuButton() {
        return controller.getRightStickButton();
    }

    @Override
    public boolean getMenuButtonToggled() {
        return controller.getRightStickButtonPressed();
    }

    public boolean getPSButton() {
        return controller.getRawButton(13);
    }

    public boolean getPSButtonToggled() {
        return controller.getRawButtonPressed(13);
    }

    public boolean getTouchpadButton() {
        return controller.getRawButton(14);
    }

    private boolean TouchpadButtonToggle = false;

    public boolean getTouchpadButtonToggled() {
        if (controller.getRawButtonPressed(14)) {
            TouchpadButtonToggle = !TouchpadButtonToggle;
        }
        return TouchpadButtonToggle;
    }

}
