package frc.robot.subsystems.turret;

public record TurretPosition(double pivotAngle, double swivelAngle, double extensionPosition) {
    public TurretPosition withPivot(double pivot) {
        return new TurretPosition(pivot, this.swivelAngle, this.extensionPosition);
    }

    public TurretPosition withSwivel(double swivel) {
        return new TurretPosition(this.pivotAngle, swivel, this.extensionPosition);
    }

    public TurretPosition withExtension(double extension) {
        return new TurretPosition(this.pivotAngle, this.swivelAngle, extension);
    }

    public TurretPosition flipSwivelSign() {
        return new TurretPosition(this.pivotAngle, -this.swivelAngle, this.extensionPosition);
    }

    // commonly used positions
    public static final TurretPosition INITIAL = new TurretPosition(60, 0, 17);
    // public static final TurretPosition MID_GOAL = new TurretPosition(36, 0, 20.1);
    // public static final TurretPosition HIGH_GOAL_CENTER = new TurretPosition(35, 0, 39.6);
    public static final TurretPosition INTAKE = new TurretPosition(-33, 0, 17);
    public static final TurretPosition PLAYER_STATION = new TurretPosition(42, 0, 20);

    public static final TurretPosition HIGH_RIGHT = new TurretPosition(30, 15.19, 56);
    public static final TurretPosition HIGH_LEFT = HIGH_RIGHT.withSwivel(-18);
    public static final TurretPosition HIGH_MID = new TurretPosition(31, 0, 51.3);
    public static final TurretPosition MID_RIGHT = new TurretPosition(24.3, 21.2, 35);
    public static final TurretPosition MID_LEFT = MID_RIGHT.withPivot(28.7).withExtension(38.5).withSwivel(-28);
    public static final TurretPosition MID_MID = new TurretPosition(17.3, 0,30.2);

    // max extension can reach 2 more mid scoring positions PLEASE TEST THESE TOO
    // (LOW PRIORITY)
    public static final TurretPosition FAR_MID_RIGHT = new TurretPosition(35, 46, 42);
    public static final TurretPosition FAR_MID_LEFT = FAR_MID_RIGHT.flipSwivelSign();

    // positions for auto only
    public static final TurretPosition HIGH_GOAL_CUBE_BLUE = HIGH_MID.withSwivel(-9.6); // positioning for auto
    public static final TurretPosition HIGH_GOAL_CUBE_RED = HIGH_GOAL_CUBE_BLUE.flipSwivelSign(); // positioning for
                                                                                                  // auto
    public static final TurretPosition HIGH_GOAL_CONE_BLUE = HIGH_MID.withSwivel(12.5); // positioning for auto
    public static final TurretPosition HIGH_GOAL_CONE_RED = HIGH_GOAL_CONE_BLUE.flipSwivelSign(); // positioning for
                                                                                                  // auto

}
