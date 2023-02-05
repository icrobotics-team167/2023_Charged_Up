package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.DriverStation;

import java.io.IOException;
import java.nio.file.Path;

public class FollowPath extends Action {

    private String trajectoryFileName;
    private Trajectory trajectory;

    private PeriodicTimer timer;
    private RamseteController follower;
    private DifferentialDriveKinematics kinematics;
    private DifferentialDriveOdometry odometry;

    public FollowPath(AutoRoutine routine) {
        trajectoryFileName = "paths/" + routine.trajectoryFile + ".wpilib.json";

        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryFileName);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            setState(AutoState.INIT);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryFileName, ex.getStackTrace());
            setState(AutoState.EXIT);
        }

        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        Subsystems.driveBase.resetEncoders();
        timer.reset();

        follower = new RamseteController();
        // Track width calculated with robot characterization
        kinematics = new DifferentialDriveKinematics(0.678673198);
        odometry = new DifferentialDriveOdometry(Subsystems.driveBase.getGyroHeading(), 0, 0,
                trajectory.getInitialPose());// 0 are temporary values
    }

    @Override
    public void periodic() {

        // System.out.println("Gyro heading (radians): " +
        // Subsystems.driveBase.getGyroHeading());
        // System.out.println("Left encoder (meters): " +
        // Subsystems.driveBase.getLeftEncoderPosition());
        // System.out.println("Right encoder (meters): " +
        // Subsystems.driveBase.getRightEncoderPosition());

        odometry.update(Subsystems.driveBase.getGyroHeading(), Subsystems.driveBase.getLeftEncoderPosition(),
                Subsystems.driveBase.getRightEncoderPosition());

        DifferentialDriveWheelSpeeds targetWheelSpeeds = kinematics.toWheelSpeeds(follower.calculate(
                odometry.getPoseMeters(),
                trajectory.sample(timer.get())));

        // System.out.println("Left target (m/s): " +
        // targetWheelSpeeds.leftMetersPerSecond);
        // System.out.println("Right target (m/s): " +
        // targetWheelSpeeds.rightMetersPerSecond + "\n");

        System.out.println("Left SetSpeed: " + targetWheelSpeeds.leftMetersPerSecond);
        System.out.println("Right SetSpeed: " + targetWheelSpeeds.rightMetersPerSecond);
        Subsystems.driveBase.setReferences(targetWheelSpeeds.leftMetersPerSecond,
                targetWheelSpeeds.rightMetersPerSecond);
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(trajectory.getTotalTimeSeconds());
    }

    @Override
    public void done() {

    }

}
