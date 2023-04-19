/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.auto;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.team6647.utils.AutoUtils;
import com.team6647.utils.TrajectoryReader;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * All commands for autonomous modes
 */
public class AutonomousPaths extends AutoUtils {

        /* Bottom */
        private static PathPlannerTrajectory leaveConeBottom = PathPlanner.loadPath("LeaveConeBottom1", new PathConstraints(2, 3));
        private static PathPlannerTrajectory balanceBottom = PathPlanner.loadPath("BalanceBottom", new PathConstraints(1, 2));

        /* Top */
        private static PathPlannerTrajectory LeaveConeTop = PathPlanner.loadPath("LeaveConeTop1", new PathConstraints(2, 3));
        private static PathPlannerTrajectory BalanceTop = PathPlanner.loadPath("BalanceTop", new PathConstraints(1, 2));

        /**
         * Basic {@link Command} to leave the Community
         * 
         * @return Command to leave the Community
         */
        public static Command leaveCommunityCones() {
                System.out.println("Leave community");
                return Commands.sequence(
                                getGridPlacement(0),
                                Commands.waitSeconds(0.5),
                                new TankDriveAutoCommand(chassis, -0.5, -0.5).withTimeout(4.2));
        }

        /**
         * Basic {@link Command} to leave the Community
         * 
         * @return Command to leave the Community
         */
        public static Command leaveCommunityCubes() {
                System.out.println("Leave community");
                return Commands.sequence(
                                getGridPlacement(1),
                                Commands.waitSeconds(0.5),
                                new TankDriveAutoCommand(chassis, -0.5, -0.5).withTimeout(4.2));
        }

        /* Bottom Community */

        /**
         * {@link Command} when robot is in the bottom of the community, leaves a cone,
         * picks a cone and balances.
         * 
         * @return Command for bottom community
         */
        public static Command bottomAutoCone() {
                System.out.println("Bottom auto Cone");

                PathPlannerTrajectory pieceBottom = PathPlanner.loadPath("GoForPieceBottom1", new PathConstraints(2, 3));

                return Commands.sequence(
                                getGridPlacement(0),
                                GrabCone.readyArm(),
                                TrajectoryReader.loadPathTrajectory(pieceBottom, true),
                                GrabCone.grabCone(),
                                TrajectoryReader.loadPathTrajectory(leaveConeBottom, false),
                                ConeMid(),
                                TrajectoryReader.loadPathTrajectory(balanceBottom, false),
                                new AutoBalance(chassis, drive));
        }

        /**
         * {@link Command} when robot is in the bottom of the community, leaves a cube,
         * picks a cone and balances.
         * 
         * @return Command for bottom community
         */
        public static Command bottomAutoCube() {
                System.out.println("Bottom auto cube");

                PathPlannerTrajectory pieceBottom = PathPlanner.loadPath("GoForPieceBottom2", new PathConstraints(2,3));

                return Commands.sequence(
                                getGridPlacement(1),
                                GrabCone.readyArm(),
                                TrajectoryReader.loadPathTrajectory(pieceBottom, true),
                                GrabCone.grabCone(),
                                TrajectoryReader.loadPathTrajectory(leaveConeBottom, false),
                                ConeMid(),
                                TrajectoryReader.loadPathTrajectory(balanceBottom, false),
                                new AutoBalance(chassis, drive));
        }

        /* Middle Community */

        /**
         * {@link Command} when robot is in the middle of the community, leaves a cone
         * and balances.
         * 
         * @return Command for middle community
         */
        public static Command midAutoCone() {
                System.out.println("Mid auto cone");
                return Commands.sequence(
                                getGridPlacement(0),
                                Commands.waitSeconds(0.5),
                                new TankDriveAutoCommand(chassis, 0.6, 0.6).withTimeout(4),
                                new TankDriveAutoCommand(chassis, -0.6, -0.6).withTimeout(2), // Use -0.6 for speeds and
                                                                                            // 2.3 in case of faulty
                                                                                            // charge station
                                new AutoBalance(chassis, drive));
        }

        /**
         * {@link Command} when robot is in the middle of the community, leaves a cube
         * and balances.
         * 
         * @return Command for middle community
         */
        public static Command midAutoCube() {
                System.out.println("Mid auto cube");
                return Commands.sequence(
                                getGridPlacement(1),
                                new TankDriveAutoCommand(chassis, -0.5, -0.5).withTimeout(6),
                                new TankDriveAutoCommand(chassis, 0.5, 0.5).withTimeout(3), // Use -0.6 for speeds and
                                                                                            // 2.3 in case of faulty
                                                                                            // charge station
                                new AutoBalance(chassis, drive));
        }

        /* Top Community */

        /**
         * {@link Command} when robot is in the top of the community, leaves a cone,
         * picks a cone and balances.
         * 
         * @return Command for top community
         */
        public static Command topAutoCone() {
                System.out.println("Top auto cone");

                PathPlannerTrajectory pieceTop = PathPlanner.loadPath("GoForPieceTop1", new PathConstraints(2,3));

                return Commands.sequence(
                                getGridPlacement(0),
                                GrabCone.readyArm(),
                                TrajectoryReader.loadPathTrajectory(pieceTop, true),
                                GrabCone.grabCone(),
                                TrajectoryReader.loadPathTrajectory(LeaveConeTop, false),
                                ConeMid(),
                                TrajectoryReader.loadPathTrajectory(BalanceTop, false),
                                new AutoBalance(chassis, drive));
        }

        /**
         * {@link Command} when robot is in the top of the community, leaves a cube,
         * picks a cone and balances.
         * 
         * @return Command for top community
         */
        public static Command topAutoCube() {
                System.out.println("Top auto cube");

                PathPlannerTrajectory pieceTop = PathPlanner.loadPath("GoForPieceTop2", new PathConstraints(2,3));

                return Commands.sequence(
                                getGridPlacement(1),
                                GrabCone.readyArm(),
                                TrajectoryReader.loadPathTrajectory(pieceTop, true),
                                GrabCone.grabCone(),
                                TrajectoryReader.loadPathTrajectory(LeaveConeTop, false),
                                ConeMid(),
                                TrajectoryReader.loadPathTrajectory(BalanceTop, false),
                                new AutoBalance(chassis, drive));
        }

        /* EMERGENCY AUTO */

        /**
         * Simple {@link Command} tested and verified to work
         * 
         * @return Emergency command
         */
        public static Command emergencyAutoCone() {
                System.out.println("Emergency cone");
                return Commands.sequence(
                                getGridPlacement(0),
                                Commands.waitSeconds(0.5),
                                new RunCommand(() -> chassis.tankDrive(-0.5, -0.5), chassis).withTimeout(3.8),
                                new RunCommand(() -> chassis.setBrake(), chassis));
        }

        /**
         * Simple {@link Command} tested and verified to work
         * 
         * @return Emergency command
         */
        public static Command emergencyAutoCube() {
                System.out.println("Emergency cube");
                return Commands.sequence(
                                getGridPlacement(1),
                                Commands.waitSeconds(0.5),
                                new RunCommand(() -> chassis.tankDrive(-0.5, -0.5), chassis).withTimeout(3.8),
                                new RunCommand(() -> chassis.setBrake(), chassis));
        }

}
