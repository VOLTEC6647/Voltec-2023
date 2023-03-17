/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.utils;

import com.team6647.commands.auto.AutonomousPaths;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.claw.MoveClaw;
import com.team6647.robot.TelemetryManager;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.utils.shuffleboard.AutoModeSelector.AutoSelection;
import com.team6647.utils.shuffleboard.GridPlacementSelector.GridPlacement;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class AutoUtils {

        protected static final TelemetryManager telemetryManager = TelemetryManager.getInstance();

        protected static ArmSubsystem arm = ArmSubsystem.getInstance();
        protected static ClawSubsytem claw = ClawSubsytem.getInstance();
        protected static ChassisSubsystem chassis = ChassisSubsystem.getInstance();
        protected static DriveSubsystem drive = DriveSubsystem.getInstance();

        protected static Command getGridPlacement(int piece) {
                GridPlacement placement = telemetryManager.getGridPlacementSelection();

                if (placement == null)
                        return null;

                System.out.println("PP: " + placement);
                switch (placement) {
                        case Bottom:
                                if (piece == 0)
                                        return putConeBottom();
                                if (piece == 1)
                                        return putCubeBottom();
                                break;
                        case Middle:
                                if (piece == 0)
                                        return putConeMid();
                                if (piece == 1)
                                        return putCubeMid();
                                break;
                        case Top:
                                if (piece == 1)
                                        return putCubeTop();
                                break;
                        default:
                                return null;
                }

                return null;
        }

        public static Command getAuto() {
                AutoSelection selection = telemetryManager.getAutoMode();

                if (selection == null)
                        return null;

                switch (selection) {
                        case LeaveCommunity:
                                return AutonomousPaths.leaveCommunity();
                        case BottomAutoCone:
                                return AutonomousPaths.bottomAutoCone();
                        case BottomAutoCube:
                                return AutonomousPaths.bottomAutoCube();
                        case MidAutoCone:
                                return AutonomousPaths.midAutoCone();
                        case MidAutoCube:
                                return AutonomousPaths.midAutoCube();
                        case TopAutoCone:
                                return AutonomousPaths.topAutoCone();
                        case TopAutoCube:
                                return AutonomousPaths.topAutoCube();
                        case EmergencyAutoCone:
                                return AutonomousPaths.emergencyAutoCone();
                        case EmergencyAutoCube:
                                return AutonomousPaths.emergencyAutoCube();
                        case DoNothing:
                                return putCubeBottom();
                        default:
                                return null;
                }
        }

        // * GRID PLACEMENT *//

        /* Bottom Grid Placement */

        // FINISHED
        protected static Command putConeBottom() {
                return Commands.sequence(
                                new RunCommand(() -> arm.changeSetpoint(-90), arm).withTimeout(1),
                                new InstantCommand(() -> claw.CubeSet(), claw));
        }

        // FINISHED
        protected static Command putCubeBottom() {
                return Commands.sequence(
                                new RunCommand(() -> arm.changeSetpoint(-120), arm).withTimeout(1),
                                new MoveClaw(claw, 2.8)).withTimeout(3);
        }

        /* Middle Grid placement */

        // TODO MODIFY
        protected static Command putConeMid() {
                return Commands.sequence(
                                new ExtendArm(arm, 0.5).withTimeout(1),
                                new RunCommand(() -> arm.changeSetpoint(-75), arm).withTimeout(1),
                                new InstantCommand(() -> claw.CubeSet(), claw));
        }

        // FINISHED
        protected static Command putCubeMid() {
                return Commands.sequence(
                                new RunCommand(() -> arm.changeSetpoint(-90), arm).withTimeout(1),
                                new MoveClaw(claw, 3)).withTimeout(3);
        }

        /* Top Grid Placement */

        // FINISHED
        protected static Command putCubeTop() {
                return Commands.sequence(
                                new RunCommand(() -> arm.changeSetpoint(-55), arm).withTimeout(1),
                                new MoveClaw(claw, 3.5)).withTimeout(5.5);
        }

        /**
         * Moves the arm to a desired output, with semi-smooth transitions
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
                                + " Fifth: " + fifth + " Sixth: " + sixth + " Seventh: " + seventh + " Eight: "
                                + eigth);

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
