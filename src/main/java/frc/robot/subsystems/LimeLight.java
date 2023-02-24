package frc.robot.subsystems;

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
                                       // 100 (Target takes up the entire FOV)
    
}
