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

    public static final TurretPosition INITIAL = new TurretPosition(65, 0, 0);
}
