package frc.robot.controls.controllers;

import java.util.Set;

// import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Config;
import frc.robot.util.MathUtils;

public class PSController implements Controller {

    private PS4Controller controller;
    private int port;

    public PSController(int port) {
        controller = new PS4Controller(port);
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
        return controller.getShareButton();
    }

    private boolean leftTriggerPrevious = false;

    private boolean getShareButtonPressed() {
        boolean result = !leftTriggerPrevious && getLeftTrigger();
        leftTriggerPrevious = getLeftTrigger();
        return result;
    }

    private boolean leftTriggerToggle = false;

    @Override
    public boolean getLeftTriggerToggled() {
        if (getShareButtonPressed()) {
            leftTriggerToggle = !leftTriggerToggle;
        }
        return leftTriggerToggle;
    }

    @Override
    public boolean getLeftBumper() {
        return controller.getL1Button();
    }

    private boolean leftBumperToggle = false;

    @Override
    public boolean getLeftBumperToggled() {
        if (controller.getL1ButtonPressed()) {
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
        return controller.getOptionsButton();
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
        return controller.getR1Button();
    }

    private boolean rightBumperToggle = false;

    @Override
    public boolean getRightBumperToggled() {
        if (controller.getR1ButtonPressed()) {
            rightBumperToggle = !rightBumperToggle;
        }
        return rightBumperToggle;
    }

    @Override
    public double getLeftStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(controller.getRawAxis(0), 2) * MathUtils.getSign(controller.getRawAxis(0));
        }
        return controller.getRawAxis(0);
    }

    @Override
    public double getLeftStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(controller.getRawAxis(1), 2) * MathUtils.getSign(controller.getRawAxis(1));
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
            return Math.pow(controller.getRawAxis(2), 2) * MathUtils.getSign(controller.getRawAxis(2));
        }
        return controller.getRawAxis(2);
    }

    @Override
    public double getRightStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(controller.getRawAxis(5), 2) * MathUtils.getSign(controller.getRawAxis(5));
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
        return controller.getCircleButton();
    }

    @Override
    public boolean getAButtonToggled() {
        return controller.getCircleButtonPressed();
    }

    @Override
    public boolean getBButton() {
        return controller.getSquareButton();
    }

    @Override
    public boolean getBButtonToggled() {
        return controller.getSquareButtonPressed();
    }

    @Override
    public boolean getXButton() {
        return controller.getCrossButton();
    }

    @Override
    public boolean getXButtonToggled() {
        return controller.getCrossButtonPressed();
    }

    @Override
    public boolean getYButton() {
        return controller.getTriangleButton();
    }

    @Override
    public boolean getYButtonToggled() {
        return controller.getTriangleButtonPressed();
    }

    @Override
    public boolean getBackButton() {
        return controller.getShareButton();
    }

    @Override
    public boolean getBackButtonToggled() {
        return controller.getShareButtonPressed();
    }

    @Override
    public boolean getStartButton() {
        return controller.getOptionsButton();
    }

    @Override
    public boolean getStartButtonToggled() {
        return controller.getR3ButtonPressed();
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

    @Override
    public boolean getDPadUp() {
        return controller.getPOV() == 0;
    }

    @Override
    public boolean getDPadRight() {
        return controller.getPOV() == 90;
    }

    @Override
    public boolean getDPadDown() {
        return controller.getPOV() == 180;
    }

    @Override
    public boolean getDPadLeft() {
        return controller.getPOV() == 270;
    }

}
