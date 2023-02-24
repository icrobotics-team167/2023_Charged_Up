package frc.robot.subsystems.turret;

public class Turret {
    private Pivot pivot;
    private Swivel swivel;
    private ExtendRetract extendRetract;

    private static Turret instance;
    public static Turret getinstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    private Turret() {
        pivot = Pivot.getInstance();
        swivel = Swivel.getInstance();
        extendRetract = ExtendRetract.getInstance();
    }

    public void move(double pivotSpeed, double swivelSpeed, double extendRetractSpeed) {
        pivot.move(pivotSpeed);
        swivel.move(swivelSpeed);
        extendRetract.move(extendRetractSpeed);
    }

    public void stop() {
        pivot.stop();
        swivel.stop();
        extendRetract.stop();
    }
}
