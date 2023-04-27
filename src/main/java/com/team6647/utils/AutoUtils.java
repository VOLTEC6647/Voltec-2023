/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.utils;

import com.team6647.commands.auto.AutonomousPaths;
import com.team6647.commands.auto.TankDriveAutoCommand;
import com.team6647.commands.hybrid.Arm.ArmControl;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.claw.SpeedClaw;
import com.team6647.robot.TelemetryManager;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.TelescopicArm;
import com.team6647.utils.shuffleboard.AutoModeSelector.AutoSelection;
import com.team6647.utils.shuffleboard.GridPlacementSelector.GridPlacement;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Util functions to be used during auto
 */
public class AutoUtils {

        protected static final TelemetryManager telemetryManager = TelemetryManager.getInstance();

        protected static ArmSubsystem arm = ArmSubsystem.getInstance();
        protected static TelescopicArm teleArm = TelescopicArm.getInstance();
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
                                        return ConeBottom();
                                if (piece == 1)
                                        return CubeBottom();
                                break;
                        case Middle:
                                if (piece == 0)
                                        return ConeMid();
                                if (piece == 1)
                                        return CubeMid();
                                break;
                        case Top:
                                if (piece == 1)
                                        return CubeTop();
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
                                return CubeMid();
                        default:
                                return null;
                }
        }

        // * GRID PLACEMENT *//

        /* Bottom Grid Placement */

        protected static Command ConeBottom() {
                return Commands.sequence(
                                new ArmControl(arm, -90),
                                Commands.waitSeconds(1.5),
                                new InstantCommand(() -> claw.CubeSet(), claw), AutoUtils.storeArm());
        }

        protected static Command CubeBottom() {
                return Commands.sequence(
                                new ArmControl(arm, -120),
                                Commands.waitSeconds(1.5),
                                new SpeedClaw(claw, 2.8, false), AutoUtils.storeArm()).withTimeout(3);
        }

        /* Middle Grid placement */

        protected static Command ConeMid() {
                return Commands.sequence(
                                new ExtendArm(teleArm, -0.8),
                                new ArmControl(arm, -110),
                                Commands.waitSeconds(0.25),
                                new ExtendArm(teleArm, 0.5).withTimeout(1),
                                Commands.waitSeconds(0.25),
                                new ArmControl(arm, -70),
                                Commands.waitSeconds(0.5),
                                new TankDriveAutoCommand(chassis, 0.4, 0.4).withTimeout(0.5),
                                new ParallelCommandGroup(
                                        new TankDriveAutoCommand(chassis, 0.4, 0.4),
                                        new InstantCommand(() -> claw.CubeSet(), claw)
                                ).withTimeout(0.5),
                                storeArm());
        }

        protected static Command CubeMid() {
                return Commands.sequence(
                                new ArmControl(arm, -85),
                                Commands.waitSeconds(1.5),
                                new SpeedClaw(claw, 3, false).withTimeout(0.6), AutoUtils.storeArm()).withTimeout(3);
        }

        /* Top Grid Placement */

        protected static Command CubeTop() {
                return Commands.sequence(
                                new ArmControl(arm, -55),
                                Commands.waitSeconds(1.5),
                                new SpeedClaw(claw, 2.9, true).withTimeout(3), AutoUtils.storeArm());
        }

        public static Command storeArm() {
                return new ParallelCommandGroup(new ArmControl(arm, -145), new ExtendArm(teleArm, -0.7));
        }

}
