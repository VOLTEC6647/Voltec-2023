/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.utils;

import com.team6647.commands.auto.AutonomousPaths;
import com.team6647.commands.auto.TankDriveAutoCommand;
import com.team6647.commands.hybrid.Arm.ArmControl;
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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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
                        case LeaveCommunityCone:
                                return AutonomousPaths.leaveCommunityCones();
                        case LeaveCommunityCube:
                                return AutonomousPaths.leaveCommunityCubes();
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
                                new MoveClaw(claw, 2.8, false)).withTimeout(3);
        }

        /* Middle Grid placement */

        // FINISHED
        protected static Command putConeMid() {
                return Commands.sequence(
                                new RunCommand(() -> arm.changeSetpoint(-110), arm).withTimeout(0.5),
                                Commands.waitSeconds(0.25),
                                new ExtendArm(arm, 0.5).withTimeout(1),
                                new ArmControl(arm, -70),
                                new TankDriveAutoCommand(chassis, 0.4, 0.4).withTimeout(0.5),
                                new ParallelCommandGroup(
                                                new TankDriveAutoCommand(chassis, 0.4, 0.4),
                                                new InstantCommand(() -> claw.CubeSet(), claw)).withTimeout(0.5));

        }

        // FINISHED
        protected static Command putCubeMid() {
                return Commands.sequence(
                                new RunCommand(() -> arm.changeSetpoint(-85), arm).withTimeout(1),
                                new MoveClaw(claw, 3, false)).withTimeout(2);
        }

        /* Top Grid Placement */

        // FINISHED
        protected static Command putCubeTop() {
                return Commands.sequence(
                                new RunCommand(() -> arm.changeSetpoint(-55), arm).withTimeout(1),
                                new MoveClaw(claw, 2.9, true)).withTimeout(4.5);
        }

}
