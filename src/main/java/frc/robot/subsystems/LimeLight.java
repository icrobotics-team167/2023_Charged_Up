package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {
    private static LimeLight instance;

    /**
     * Allows only one instance of LimeLight to exist at once.
     * 
     * @return An instance of LimeLight. Will create a new one if it doesn't exist
     *         already
     */
    public static LimeLight getInstance() {
        if (instance == null) {
            instance = new LimeLight();
        }
        return instance;
    }

    private boolean validTargetExists; // Whether a valid AprilTag target is within the FOV of the camera ("tv" in
                                       // LimeLight API)
    private double targetCrosshairDistanceX; // The horizontal distance of the target from the crosshair ("tx" in
                                             // LimeLight API)
    private double targetCrosshairDistanceY; // The vertical distance of the target from the crosshair ("ty" in
                                             // LimeLight API)
    private double targetAreaOfVision; // The percentage of the vision the target is taking up, from 0 (No target) to
                                       // 100 (Target takes up the entire FOV) ("ta" in LimeLight API)
    private double aprilTagID; // The ID of the detected AprilTag ("tid" in LimeLight API)
    private double objectID; // The ID of the detected object ("tclass" in LimeLight API)

    private NetworkTable limeLight;
    private HttpCamera limeLightFeed;

    private boolean cameraMode;

    private LimeLight() {
        limeLight = NetworkTableInstance.getDefault().getTable("limelight");
        limeLightFeed = new HttpCamera("LimeLight", "http://"); // TODO: Figure out static IP address of the limelight
        CameraServer.startAutomaticCapture(limeLightFeed);
        setVisionMode();
        update();
    }

    /**
     * Gets the NetworkTable object that corresponds to the LimeLight
     * 
     * @return The LimeLight's NetworkTable object
     */
    public NetworkTable getLimeLight() {
        return limeLight;
    }

    /**
     * Updates the values for LimeLight outputs
     */
    public void update() {
        // Updates the limeLight values
        validTargetExists = limeLight.getEntry("tv").getBoolean(false);
        targetCrosshairDistanceX = limeLight.getEntry("tx").getDouble(0);
        targetCrosshairDistanceY = limeLight.getEntry("ty").getDouble(0);
        targetAreaOfVision = limeLight.getEntry("ta").getDouble(0);
        aprilTagID = limeLight.getEntry("tid").getDouble(0);
        objectID = limeLight.getEntry("tclass").getDouble(0);
    }

    /**
     * Sets the LimeLight to camera mode
     */
    public void setCameraMode() {
        limeLight.getEntry("camMode").setDouble(1); // Set the LimeLight to camera mode (Disable vision processing and
                                                    // increase exposure)
        limeLight.getEntry("ledMode").setDouble(1); // Turn off the LEDs
        cameraMode = true;
    }

    /**
     * Sets the LimeLight to vision mode
     */
    public void setVisionMode() {
        limeLight.getEntry("camMode").setDouble(0); // Set the LimeLight to vision mode (Enable vision processing and
                                                    // decrease exposure)
        limeLight.getEntry("ledMode").setDouble(0); // Turn on the LEDs
        cameraMode = false;
    }

    /**
     * Toggles between camera mode and vision mode
     */
    public void toggleCameraMode() {
        if (cameraMode) {
            setVisionMode();
        } else {
            setCameraMode();
        }
    }

    /**
     * Gets whether or not a valid target exists.
     * 
     * @return If a valid target exists.
     */
    public boolean getValidTargetExists() {
        return validTargetExists;
    }

    /**
     * If a valid target exists, gets its horizontal distance from the crosshair
     * 
     * @return The target's horizontal distance from the crosshair, in degrees
     */
    public double getTargetCrosshairDistanceX() {
        return targetCrosshairDistanceX;
    }

    /**
     * If a valid target exists, gets its vertical distance from the crosshair
     * 
     * @return The target's vertical distance from the crosshair, in degrees
     */
    public double getTargetCrosshairDistanceY() {
        return targetCrosshairDistanceY;
    }

    /**
     * If a valid target exists, gets its size on the LimeLight's FOV
     * TODO: Figure out if the min/max is 0-1 or 0-100
     * 
     * @return The size of the target, from 0 (No target) to 1/100 (Target takes up
     *         the entire FOV)
     */
    public double getTargetAreaOfVision() {
        return targetAreaOfVision;
    }

    /**
     * If a valid AprilTag is within the FOV of the LimeLight, gets its ID
     * 
     * @return The ID of the AprilTag
     */
    public double getAprilTagID() {
        return aprilTagID;
    }

    /**
     * If a valid trackable object exists, gets its ID
     * 
     * @return The ID of the object
     */
    public double getObjectID() {
        return objectID;
    }
}
