package frc.robot.controls.controllers;

import edu.wpi.first.wpilibj.Joystick;

//TODO: Implement deadzones for these controllers
public class ThrustMasterController implements Controller {
    Joystick joystick;
    private int port;

    public ThrustMasterController(int port) {
        joystick = new Joystick(port);
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public ControllerType getControllerType() {
        return ControllerType.JOYSTICK;
    }

    @Override
    public double getLeftTriggerValue() {

        return 0;
    }

    @Override
    public boolean getLeftTrigger() {
        return joystick.getTrigger();
    }

    @Override
    public boolean getLeftTriggerPressed() {
        return joystick.getTriggerPressed();
    }

    @Override
    public boolean getLeftBumper() {

        return false;
    }

    @Override
    public boolean getLeftBumperPressed() {

        return false;
    }

    @Override
    public double getRightTriggerValue() {

        return 0;
    }

    @Override
    public boolean getRightTrigger() {

        return false;
    }

    @Override
    public boolean getRightTriggerPressed() {

        return false;
    }

    @Override
    public boolean getRightBumper() {
        return false;
    }

    @Override
    public boolean getRightBumperToggled() {

        return false;
    }

    @Override
    public double getLeftStickX() {
        return -joystick.getX();
    }

    @Override
    public double getLeftStickY() {

        return -joystick.getY();
    }

    @Override
    public double getLeftStickZ() {
        return joystick.getZ();
    }

    @Override
    public boolean getLeftStickButton() {

        return false;
    }

    @Override
    public boolean getLeftStickButtonPressed() {

        return false;
    }

    @Override
    public double getRightStickX() {
        throw new UnsupportedOperationException("no right stick on a joystick :-)");
    }

    @Override
    public double getRightStickY() {

        return 0;
    }

    @Override
    public boolean getRightStickButton() {

        return false;
    }

    @Override
    public boolean getRightStickButtonPressed() {

        return false;
    }

    @Override
    public boolean getAButton() {

        return false;
    }

    @Override
    public boolean getAButtonPressed() {

        return false;
    }

    @Override
    public boolean getBButton() {

        return false;
    }

    @Override
    public boolean getBButtonPressed() {

        return false;
    }

    @Override
    public boolean getXButton() {

        return false;
    }

    @Override
    public boolean getXButtonPressed() {

        return false;
    }

    @Override
    public boolean getYButton() {

        return false;
    }

    @Override
    public boolean getYButtonPressed() {

        return false;
    }

    @Override
    public boolean getBackButton() {

        return false;
    }

    @Override
    public boolean getBackButtonPressed() {

        return false;
    }

    @Override
    public boolean getStartButton() {

        return false;
    }

    @Override
    public boolean getStartButtonPressed() {

        return false;
    }

    @Override
    public boolean getDPadUp() {
        return joystick.getPOV() == 0;
    }

    @Override
    public boolean getDPadRight() {
        return joystick.getPOV() == 90;
    }

    @Override
    public boolean getDPadDown() {
        return joystick.getPOV() == 180;
    }

    @Override
    public boolean getDPadLeft() {
        return joystick.getPOV() == 270;
    }

    @Override
    public boolean getButtonById(int buttonId) {
        return joystick.getRawButton(buttonId);
    }

    @Override
    public boolean getButtonPressedById(int buttonId) {
        return joystick.getRawButtonPressed(buttonId);
    }

    @Override 
    public boolean getButtonReleasedById(int buttonId) {
        return joystick.getRawButtonReleased(buttonId);
    }

}
