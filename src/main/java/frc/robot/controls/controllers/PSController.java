package frc.robot.controls.controllers;

// import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Config;
import frc.robot.util.MathUtils;

public class PSController implements Controller {

    private PS4Controller controller;
    private int port;

    /**
     * Constructs a new PlayStation 4 controller.
     * 
     * @param port The port that the controller is connected to. Configure in the
     *             Driver Station.
     */
    public PSController(int port) {
        controller = new PS4Controller(port);
        this.port = port;
        System.out.println("PS POV Count: " + controller.getPOVCount());
    }

    /**
     * Gets the port of the controller.
     * 
     * @return The port.
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * Gets the type of the controller.
     * 
     * @return The controller type.
     */
    @Override
    public ControllerType getControllerType() {
        return ControllerType.PS;
    }

    /**
     * Gets the analog value of the Left Trigger. (L2)
     * 
     * @return The value. From 0 (Not pressed) to 1. (All the way pressed)
     */
    @Override
    public double getLeftTriggerValue() {
        return controller.getL2Axis();
    }

    /**
     * Gets if the Left Trigger (L2) is pressed.
     * 
     * @return If the trigger is pressed.
     */
    @Override
    public boolean getLeftTrigger() {
        return controller.getL2Button();
    }

    /**
     * Gets if the Left Trigger (L2) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the trigger was pressed down.
     */
    @Override
    public boolean getLeftTriggerPressed() {
        return controller.getL2ButtonPressed();
    }

    /**
     * Gets if the Left Bumper (L1) is pressed.
     * 
     * @return If the bumper is pressed.
     */
    @Override
    public boolean getLeftBumper() {
        return controller.getL1Button();
    }

    /**
     * Gets if the Left Bumper (L1) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the bumper was pressed down.
     */
    @Override
    public boolean getLeftBumperPressed() {
        return controller.getL1ButtonPressed();
    }

    /**
     * Gets the analog value of the Right Trigger. (R2)
     * 
     * @return The value. From 0 (Not pressed) to 1. (All the way pressed)
     */
    @Override
    public double getRightTriggerValue() {
        return controller.getR2Axis();
    }

    /**
     * Gets if the Right Trigger (R2) is pressed.
     * 
     * @return If the trigger is pressed.
     */
    @Override
    public boolean getRightTrigger() {
        return controller.getR2Button();
    }

    /**
     * Gets if the Right Trigger (R2) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the trigger was pressed down.
     */
    @Override
    public boolean getRightTriggerPressed() {
        return controller.getR2ButtonPressed();
    }

    /**
     * Gets if the Right Bumper (R1) is pressed.
     * 
     * @return If the bumper is pressed.
     */
    @Override
    public boolean getRightBumper() {
        return controller.getR1Button();
    }

    /**
     * Gets if the Right Bumper (R2) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the bumper was pressed down.
     */
    @Override
    public boolean getRightBumperToggled() {
        return controller.getR1ButtonPressed();
    }

    /**
     * Gets the horizontal axis of the Left Joystick.
     * 
     * @return The horizontal position. From -1 (All the way left) to 1. (All the
     *         way right)
     */
    @Override
    public double getLeftStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(Math.abs(controller.getLeftX()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getLeftX());
        }
        return controller.getLeftX();
    }

    /**
     * Gets the vertical axis of the Left Joystick.
     * 
     * @return The vertical position. From -1 (All the way down) to 1. (All the way
     *         up)
     */
    @Override
    public double getLeftStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(Math.abs(controller.getLeftY()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getLeftY());
        }
        return -controller.getLeftY();
    }

    @Override
    public double getLeftStickZ() {
        return 0;
    }

    /**
     * Gets if the Left Stick Button (L3) is pressed.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getLeftStickButton() {
        return controller.getL3Button();
    }

    /**
     * Gets if the Left Stick Button (L3) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getLeftStickButtonPressed() {
        return controller.getL3ButtonPressed();
    }

    /**
     * Gets the horizontal axis of the Right Stick.
     * 
     * @return The horizontal position. From -1 (All the way left) to 1. (All the
     *         way right)
     */
    @Override
    public double getRightStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(Math.abs(controller.getRightX()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getRightX());
        }
        return controller.getRightX();
    }

    /**
     * Gets the vertical axis of the Right Stick.
     * 
     * @return The vertical position. From -1 (All the way down) to 1. (All the way
     *         up)
     */
    @Override
    public double getRightStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(Math.abs(controller.getRightY()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getRightY());
        }
        return -controller.getRightY();
    }

    /**
     * Gets if the Right Stick Button (R3) is pressed.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getRightStickButton() {
        return controller.getR3Button();
    }

    /**
     * Gets if the Right Stick Button (R3) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getRightStickButtonPressed() {
        return controller.getR3ButtonPressed();
    }

    /**
     * Gets if the Cross button is pressed. Corresponds to the A button on
     * Xbox controllers or the B button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getAButton() {
        return controller.getCrossButton();
    }

    /**
     * Gets if the Cross button was pressed down. Corresponds to the A button on
     * Xbox controllers or the B button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getAButtonPressed() {
        return controller.getCrossButtonPressed();
    }

    /**
     * Gets if the Circle button is pressed. Corresponds to the B button on Xbox
     * controllers or the A button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getBButton() {
        return controller.getCircleButton();
    }

    /**
     * Gets if the Circle button was pressed down. Corresponds to the B button on
     * Xbox controllers or the A button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getBButtonPressed() {
        return controller.getCircleButtonPressed();
    }

    /**
     * Gets if the Square button is pressed. Corresponds to the X button on Xbox
     * controllers or the Y button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getXButton() {
        return controller.getSquareButton();
    }

    /**
     * Gets if the Square button was pressed down. Corresponds to the X button on
     * Xbox controllers or the Y button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getXButtonPressed() {
        return controller.getSquareButtonPressed();
    }

    /**
     * Gets if the Triangle button is pressed. Corresponds to the Y button on Xbox
     * controllers or the X button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getYButton() {
        return controller.getTriangleButton();
    }

    /**
     * Gets if the Triangle button was pressed down. Corresponds to the Y button on
     * Xbox controllers or the X button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getYButtonPressed() {
        return controller.getTriangleButtonPressed();
    }

    /**
     * Gets if the Share button is pressed. Corresponds to the Back button on Xbox
     * controllers or the Minus button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getBackButton() {
        return controller.getShareButton();
    }

    /**
     * Gets if the Share button was pressed down. Corresponds to the Back button on
     * Xbox
     * controllers or the Minus button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getBackButtonPressed() {
        return controller.getShareButtonPressed();
    }

    /**
     * Gets if the Options button is pressed. Corresponds to the Start button on
     * Xbox
     * controllers or the Plus button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getStartButton() {
        return controller.getOptionsButton();
    }

    /**
     * Gets if the Options button was pressed down. Corresponds to the Start button
     * on Xbox controllers or the Plus button on Nintendo controller. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getStartButtonPressed() {
        return controller.getOptionsButtonPressed();
    }

    /**
     * Gets if the Playstation logo button is pressed.
     * 
     * @return If the button is pressed.
     */
    public boolean getPSButton() {
        return controller.getPSButton();
    }

    /**
     * Gets if the Playstation logo button was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the button was pressed down.
     */
    public boolean getPSButtonToggled() {
        return controller.getPSButtonPressed();
    }

    /**
     * Gets if the Touchpad is pressed.
     * 
     * @return If the Touchpad is pressed.
     */
    public boolean getTouchpadButton() {
        return controller.getTouchpad();
    }

    /**
     * Gets if the Touchpad was pressed down. Useful for rising-type toggles
     * 
     * @return If the Touchpad was pressed down.
     */
    public boolean getTouchpadButtonToggled() {
        return controller.getTouchpadPressed();
    }

    /**
     * Gets if D-Pad Up is pressed.
     * 
     * @return If D-Pad Up is pressed.
     */
    @Override
    public boolean getDPadUp() {
        return controller.getPOV() == 0;
    }

    /**
     * Gets if D-Pad Right is pressed.
     * 
     * @return If D-Pad Right is pressed.
     */
    @Override
    public boolean getDPadRight() {
        return controller.getPOV() == 90;
    }

    /**
     * Gets if D-Pad Down is pressed.
     * 
     * @return If D-Pad Down is pressed.
     */
    @Override
    public boolean getDPadDown() {
        return controller.getPOV() == 180;
    }

    /**
     * Gets if D-Pad Left is pressed.
     * 
     * @return If D-Pad Left is pressed.
     */
    @Override
    public boolean getDPadLeft() {
        return controller.getPOV() == 270;
    }

    @Override
    public boolean getButtonById(int buttonId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getButtonPressedById(int buttonId) {
        // TODO Auto-generated method stub
        return false;
    }

}
