package frc.robot.routines.auto;

public enum AutoRoutine {
    FTR1("1", "1"),
    FTR2("2", "2"),
    FTR3("3", "3"),
    FTR4("4b", "4b"),
    FTR5("5b", "5b"),
    RT("Right Turn", "rightTurn"),
    CIRCLE("Circle", "circle"),
    ENEMY_TENCH_RUN("Enemy Trench Run", "ETR"),
    SHOOT_3("Shoot 3", "S3"),
    NULL("Null Auto", "");

    public String name;
    public String trajectoryFile;
    AutoRoutine(String name, String trajectoryFile) {
        this.name = name;
        this.trajectoryFile = trajectoryFile;
    }
}
