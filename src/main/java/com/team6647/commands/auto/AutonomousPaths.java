/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.auto;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.team6647.commands.hybrid.claw.MoveClaw;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class AutonomousPaths {
    private static ArmSubsystem arm = ArmSubsystem.getInstance();
    private static ClawSubsytem claw = ClawSubsytem.getInstance();
    private static ChassisSubsystem chassis = ChassisSubsystem.getInstance();
    private static DriveSubsystem drive = DriveSubsystem.getInstance();

    private static PathPlannerTrajectory trajectory1 = PathPlanner.generatePath(
            new PathConstraints(1, 2),
            false,
            new PathPoint(new Translation2d(2.22, 3.12), Rotation2d.fromDegrees(90)),
            new PathPoint(new Translation2d(2.33, 4.18), Rotation2d.fromDegrees(68.69)),
            new PathPoint(new Translation2d(3.28, 4.68), Rotation2d.fromDegrees(0)),
            new PathPoint(new Translation2d(4.48, 4.69), Rotation2d.fromDegrees(0)));

    public static PathPlannerTrajectory getLeaveCommunityTop() {
        return trajectory1;
    }

    /**
     * Command for only leaving the community
     * 
     * @param position Begin position String (Top/MidTop/MidBottom/Bottom)
     * @return
     */
    public static Command leaveCommunity(String position) {
        switch (position) {
            case "Top":
                return Commands.sequence(Load.loadPathTrajectory(
                        PathPlanner.loadPath("LeaveCommunityTop", new PathConstraints(1, 2)), true));

            case "Mid Top":
                return Commands.sequence(Load.loadPathTrajectory(
                        PathPlanner.loadPath("MidLeaveCommunityTop", new PathConstraints(1, 2)), true));

            case "Mid Bottom":

                return Commands.sequence(Load.loadPathTrajectory(
                        PathPlanner.loadPath("MidLeaveCommunityDown", new PathConstraints(1, 2)), true));
            case "Bottom":

                return Commands.sequence(Load.loadPathTrajectory(
                        PathPlanner.loadPath("1-CubeLeave", new PathConstraints(1, 2)), true));
            default:
                return null;
        }
    }

    /**
     * Drops a cube, and grabs a cone and places it again.
     * Then, climbs the charge station
     * 
     * @return
     */
    public static Command dropCubeAndCone() {
        return Commands.sequence(
                moveArmAuto(20).withTimeout(1),
                new MoveClaw(claw, -1).withTimeout(2),
                moveArmAuto(-125),
                Load.loadPathTrajectory(PathPlanner.loadPath("1-CubeLeave", new PathConstraints(1, 2)), true),
                Load.loadPathTrajectory(PathPlanner.loadPath("2-GrabPiece", new PathConstraints(1, 2)), false),
                moveArmAuto(-130),
                new InstantCommand(() -> claw.ConeSet(), claw).withTimeout(0.5),
                moveArmAuto(-90),
                Load.loadPathTrajectory(PathPlanner.loadPath("3-LeaveCone", new PathConstraints(1, 2)), false),
                new InstantCommand(() -> claw.cubeSet(), claw).withTimeout(0.5),
                Load.loadPathTrajectory(PathPlanner.loadPath("4-GoToChargeStation", new PathConstraints(1, 2)), false),
                Load.loadPathTrajectory(PathPlanner.loadPath("5-ClimbChargeStation", new PathConstraints(1, 2)), false),
                new AutoBalance(chassis, drive));
    }

    /**
     * Drops a cone, and grabs a cone and places it again.
     * Then, climbs the charge station
     * 
     * @return
     */
    public static Command dropConeAndCone() {
        return Commands.sequence(
                moveArmAuto(30).withTimeout(1),
                new MoveClaw(claw, -1).withTimeout(2),
                moveArmAuto(-125),
                Load.loadPathTrajectory(PathPlanner.loadPath("1-ConeLeave", new PathConstraints(1, 2)), true),
                Load.loadPathTrajectory(PathPlanner.loadPath("2-GrabPiece", new PathConstraints(1, 2)), false),
                moveArmAuto(-130),
                new InstantCommand(() -> claw.ConeSet(), claw).withTimeout(0.5),
                moveArmAuto(-90),
                Load.loadPathTrajectory(PathPlanner.loadPath("3-LeaveCone", new PathConstraints(1, 2)), false),
                new InstantCommand(() -> claw.cubeSet(), claw).withTimeout(0.5),
                Load.loadPathTrajectory(PathPlanner.loadPath("4-GoToChargeStation", new PathConstraints(1, 2)), false),
                Load.loadPathTrajectory(PathPlanner.loadPath("5-ClimbChargeStation", new PathConstraints(1, 2)), false),
                new AutoBalance(chassis, drive));
    }

    /**
     * Moves the arm to a desired output, with smooth transitions
     * 
     * @param position Desired position
     * @return Arm command
     */
    public static Command moveArmAuto(double position) {
        double current = arm.getMeasurement();
        double error = (arm.getMeasurement() - position) * -1;

        double q2 = error / 2;
        double q1 = q2 / 4;

        double first = current += q1;
        double second = current += q1;
        double third = current += q1;
        double fourth = current += q1;
        double fifth = current += q1;
        double sixth = current += q1;
        double seventh = current += q1;
        double eigth = current += q1;

        System.out.println("First: " + first + " Second: " + second + " Third: " + third + " Fourth: " + fourth
                + " Fifth: " + fifth + " Sixth: " + sixth + " Seventh: " + seventh + " Eight: " + eigth);

        return Commands.sequence(
                new RunCommand(() -> arm.changeSetpoint(first),
                        arm).withTimeout(0.2),
                new RunCommand(() -> arm.changeSetpoint(second),
                        arm).withTimeout(0.2),
                new RunCommand(() -> arm.changeSetpoint(third),
                        arm).withTimeout(0.2),
                new RunCommand(() -> arm.changeSetpoint(fourth),
                        arm).withTimeout(0.2),
                new RunCommand(() -> arm.changeSetpoint(fifth),
                        arm).withTimeout(0.2),
                new RunCommand(() -> arm.changeSetpoint(sixth),
                        arm).withTimeout(0.2),
                new RunCommand(() -> arm.changeSetpoint(seventh),
                        arm).withTimeout(0.2),
                new RunCommand(() -> arm.changeSetpoint(eigth),
                        arm).withTimeout(0.2));

    }
}
