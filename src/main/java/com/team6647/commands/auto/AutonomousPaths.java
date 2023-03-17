/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.auto;

import com.team6647.utils.AutoUtils;
import com.team6647.utils.Load;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;

/* Load.loadWPITrajectory(Filesystem.getDeployDirectory() + "/pathplanner/generatedJSON/Test1.wpilib.json", true),
                                new AutoBalance(chassis, drive) */

public class AutonomousPaths extends AutoUtils {

        /**
         * Basic {@link Command} to leave the Community
         * 
         * @return Command to leave the Community
         */
        public static Command leaveCommunity() {
                System.out.println("Leave community");
                return new RunCommand(() -> chassis.tankDrive(-0.4, -0.4), chassis).withTimeout(3.2);
        }

        /* Bottom Community */

        /**
         * {@link Command} when robot is in the bottom of the community, leaves a cone, picks a cone and balances.
         * 
         * @return Command for bottom community
         */
        public static Command bottomAutoCone(){
                System.out.println("Bottom auto Cone");
                return Commands.sequence(
                        getGridPlacement(0),
                        Load.loadWPITrajectory("GoForPieceBottom1.wpilib.json", true),
                        GrabCone.grabCone(),
                        Load.loadWPITrajectory("LeaveConeBottom1.wpilib.json", false),
                        //ADD LIMELIGHT AIM
                        putConeMid(),
                        Load.loadWPITrajectory("BalanceBottom.wpilib.json", false),
                        new AutoBalance(chassis, drive)
                );
        }

        /**
         * {@link Command} when robot is in the bottom of the community, leaves a cube, picks a cone and balances.
         * 
         * @return Command for bottom community
         */
        public static Command bottomAutoCube(){
                System.out.println("Bottom auto cube");
                return Commands.sequence(
                        getGridPlacement(1),
                        Load.loadWPITrajectory("GoForPieceBottom2.wpilib.json", true),
                        GrabCone.grabCone(),
                        Load.loadWPITrajectory("LeaveConeBottom1.wpilib.json", false),
                        //ADD LIMELIGHT AIM
                        putConeMid(),
                        Load.loadWPITrajectory("BalanceBottom.wpilib.json", false),
                        new AutoBalance(chassis, drive)
                );
        }

        /* Middle Community */

        /**
         * {@link Command} when robot is in the middle of the community, leaves a cone and balances.
         * 
         * @return Command for middle community
         */
        public static Command midAutoCone() {
                System.out.println("Mid auto cone");
                return Commands.sequence(
                                getGridPlacement(0),
                                Commands.waitSeconds(0.5),
                                new RunCommand(() -> chassis.tankDrive(-0.5, -0.5), chassis).withTimeout(3),
                                new AutoBalance(chassis, drive));
        }

        /**
         * {@link Command} when robot is in the middle of the community, leaves a cube and balances.
         * 
         * @return Command for middle community
         */
        public static Command midAutoCube() {
                System.out.println("Mid auto cube");
                return Commands.sequence(
                                getGridPlacement(1),
                                new RunCommand(() -> chassis.tankDrive(-0.5, -0.5), chassis).withTimeout(3),
                                new AutoBalance(chassis, drive));
        }

        /* Top Community */

        /**
         * {@link Command} when robot is in the top of the community, leaves a cone, picks a cone and balances.
         * 
         * @return Command for top community
         */
        public static Command topAutoCone(){
                System.out.println("top auto cone");
                return Commands.sequence(
                        getGridPlacement(0),
                        Load.loadWPITrajectory("GoForPieceTop1.wpilib.json", true),
                        GrabCone.grabCone(),
                        Load.loadWPITrajectory("LeaveConeTop1.wpilib.json", false),
                        //ADD LIMELIGHT AIM
                        putConeMid(),
                        Load.loadWPITrajectory("BalanceTop.wpilib.json", false),
                        new AutoBalance(chassis, drive)
                );
        }

        /**
         * {@link Command} when robot is in the top of the community, leaves a cube, picks a cone and balances.
         * 
         * @return Command for top community
         */
        public static Command topAutoCube(){
                System.out.println("Top auto cube");
                return Commands.sequence(
                        getGridPlacement(1),
                        Load.loadWPITrajectory("GoForPieceTop2.wpilib.json", true),
                        GrabCone.grabCone(),
                        Load.loadWPITrajectory("LeaveConeTop1.wpilib.json", false),
                        //ADD LIMELIGHT AIM
                        putConeMid(),
                        Load.loadWPITrajectory("BalanceTop.wpilib.json", false),
                        new AutoBalance(chassis, drive)
                );
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
                                putConeBottom(),
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
                                putCubeBottom(),
                                Commands.waitSeconds(0.5),
                                new RunCommand(() -> chassis.tankDrive(-0.5, -0.5), chassis).withTimeout(3.8),
                                new RunCommand(() -> chassis.setBrake(), chassis));
        }

}
