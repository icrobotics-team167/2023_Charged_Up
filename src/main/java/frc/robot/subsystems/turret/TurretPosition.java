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
    
    //commonly used positions
    public static final TurretPosition INITIAL           = new TurretPosition(60, 0, 3.5);
    public static final TurretPosition MID_GOAL          = new TurretPosition(36, 0, 20.1);
    public static final TurretPosition HIGH_GOAL_CENTER  = new TurretPosition(35,   0, 39.6);
    public static final TurretPosition INTAKE            = new TurretPosition(-33, 0, 8);
    public static final TurretPosition PLAYER_STATION    = new TurretPosition(50, 0, 7); // TODO: Measure angles
    
    // 6 positions for single grid section PLEASE TEST THESE
    public static final TurretPosition HIGH_RIGHT = new TurretPosition(35,  20, 42);
    public static final TurretPosition HIGH_LEFT  = HIGH_RIGHT.flipSwivelSign();
    public static final TurretPosition HIGH_MID   = new TurretPosition(35,   0, 40);
    public static final TurretPosition MID_RIGHT  = new TurretPosition(35,  28, 28);
    public static final TurretPosition MID_LEFT   = MID_RIGHT.flipSwivelSign();
    public static final TurretPosition MID_MID    = new TurretPosition(35,   0, 21);
    
    //max extension can reach 2 more mid scoring positions PLEASE TEST THESE TOO (LOW PRIORITY)
    public static final TurretPosition FAR_MID_RIGHT  = new TurretPosition(35,  46, 42);
    public static final TurretPosition FAR_MID_LEFT   = FAR_MID_RIGHT.flipSwivelSign();
    
    //positions for auto only
    public static final TurretPosition HIGH_GOAL_CUBE_BLUE = HIGH_GOAL_CENTER.withSwivel( 9.6);  // positioning for auto
    public static final TurretPosition HIGH_GOAL_CUBE_RED  = HIGH_GOAL_CUBE_BLUE.flipSwivelSign();  // positioning for auto
    public static final TurretPosition HIGH_GOAL_CONE_BLUE = HIGH_GOAL_CENTER.withSwivel(-12.5); // positioning for auto
    public static final TurretPosition HIGH_GOAL_CONE_RED  = HIGH_GOAL_CONE_BLUE.flipSwivelSign(); // positioning for auto
    
}
