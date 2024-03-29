/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.utils;

import java.io.IOException;
import java.nio.file.Path;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.utils.Constants.DriveConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TrajectoryReader {
    static DriveSubsystem drive = DriveSubsystem.getInstance();
    static ChassisSubsystem chassis = ChassisSubsystem.getInstance();

    /**
     * Loads a JSON Path file into a command
     * 
     * @param filename      Path for the trajectory
     * @param resetOdometry Reset odometry status
     * @return JSON Path command
     */
    public static Command loadWPITrajectory(String filename, boolean resetOdometry) {
        String completeFileName = Filesystem.getDeployDirectory() + "/pathplanner/generatedJSON/" + filename;
        Trajectory trajectory;
        try {
            /* Searchs for trajectory in deploy directory */
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(completeFileName);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException e) {
            /* Prints error message */
            DriverStation.reportError("Unable to open trajectory " + filename, e.getStackTrace());
            System.out.println("Unable to read file");
            return new InstantCommand();
        }
        /* The command that runs the path */
        RamseteCommand ramseteCommand = new RamseteCommand(trajectory, drive::getPose,
                new RamseteController(DriveConstants.kramseteB, DriveConstants.kRamseteZeta),
                new SimpleMotorFeedforward(
                        DriveConstants.ksVolts,
                        DriveConstants.kvVoltSecondsPerMeter,
                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDrivekinematics, drive::getWheelSpeeds,
                new PIDController(DriveConstants.kpDriveVelocity, 0, 0),
                new PIDController(DriveConstants.kpDriveVelocity, 0, 0),
                drive::tankDriveVolts, drive, chassis);

        if (resetOdometry) {
            return new SequentialCommandGroup(
                    new InstantCommand(() -> drive.resetOdometry(trajectory.getInitialPose())),
                    ramseteCommand);
        } else {
            return ramseteCommand;
        }
    }

    /**
     * Loads a {@link PathPlannerTrajectory} into a command
     * 
     * @param filename      Path for the trajectory
     * @param resetOdometry Reset odometry status
     * @return Path command
     */
    public static Command loadPathTrajectory(PathPlannerTrajectory trajectory, boolean resetOdometry) {
        return new SequentialCommandGroup(
                new InstantCommand(() -> {
                    if (resetOdometry) {
                        drive.resetOdometry(trajectory.getInitialPose());
                    }
                }),
                new PPRamseteCommand(trajectory, drive::getPose,
                        new RamseteController(DriveConstants.kramseteB, DriveConstants.kRamseteZeta),
                        new SimpleMotorFeedforward(
                                DriveConstants.ksVolts,
                                DriveConstants.kvVoltSecondsPerMeter,
                                DriveConstants.kaVoltSecondsSquaredPerMeter),
                        DriveConstants.kDrivekinematics, drive::getWheelSpeeds,
                        new PIDController(DriveConstants.kpDriveVelocity, 0, 0),
                        new PIDController(DriveConstants.kpDriveVelocity, 0, 0), drive::tankDriveVolts,
                        true,
                        drive,
                        chassis));
    }
}
