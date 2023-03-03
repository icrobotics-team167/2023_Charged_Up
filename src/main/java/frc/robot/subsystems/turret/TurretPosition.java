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

    public static final TurretPosition INITIAL = new TurretPosition(60, 0, 3.5);
    public static final TurretPosition HIGH_GOAL = new TurretPosition(35, 0, 39.6);
    public static final TurretPosition MID_GOAL = new TurretPosition(36, 0, 20.1);
    public static final TurretPosition INTAKE = new TurretPosition(-33, 0, 8);
    public static final TurretPosition PLAYER_STATION = new TurretPosition(36, 0, 7); // TODO: Measure angles
}
