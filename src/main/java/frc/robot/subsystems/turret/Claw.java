package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Config;

/**
 * Opens and closes the claw.
 */
public class Claw {

    private Solenoid openClaw;

    public static Claw instance;

    private boolean open;

    /**
     * Allows only one instance of Claw to exist at once.
     * 
     * @return An instance of Claw. Creates a new one if it doesn't exist already.
     */
    public static Claw getInstance() {
        if (instance == null) {
            instance = new Claw();
        }
        return instance;
    }

    /**
     * Sets up the pneumatic channels for the claw. 
     */
    private Claw() {
        openClaw = new Solenoid(Config.Ports.SparkTank.PH, PneumaticsModuleType.REVPH, Config.Ports.Arm.CLAW);
    }

    /**
     * Opens the claw
     */
    public void openClaw() {
        openClaw.set(true);
        open = true;
    }

    /**
     * Closes the claw
     */
    public void closeClaw() {
        openClaw.set(false);
        open = false;
    }

    public void toggleClaw() {
        if(open) {
            closeClaw();
        }
        else {
            openClaw();
        }
    }

    public boolean isOpen() {
        return open;
    }
}
