package com.team6647.commands.auto;

import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.ClawConstants;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.Arm.StartArm;
import com.team6647.commands.hybrid.claw.MoveClaw;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public final class ProtocolCommand {
    private ArmSubsystem arm;
    private ChassisSubsystem chassis;
    private ClawSubsytem claw;
    private DriveSubsystem drive;
    private VisionSubsystem vision;

    public ProtocolCommand(ArmSubsystem arm, ChassisSubsystem chassis, ClawSubsytem claw, DriveSubsystem drive,
            VisionSubsystem vision) {
        this.arm = arm;
        this.chassis = chassis;
        this.claw = claw;
        this.drive = drive;
        this.vision = vision;

    }

    public Command getStartCommand() {
        return new SequentialCommandGroup(
                chassisCommand(),
                clawCommand(),
                extendArm());
    }

    private Command chassisCommand() {
        return new SequentialCommandGroup(
                new RunCommand(() -> chassis.tankDrive(0.5, 0.5), chassis).withTimeout(2),
                new RunCommand(() -> chassis.tankDrive(-0.5, -0.5), chassis).withTimeout(2),
                new RunCommand(() -> chassis.tankDrive(0.5, 0), chassis).withTimeout(2),
                new RunCommand(() -> chassis.tankDrive(0, 0.5), chassis).withTimeout(2));
    }

    private Command clawCommand() {
        return new SequentialCommandGroup(
                new MoveClaw(claw, ClawConstants.clawSpeed).withTimeout(2),
                new MoveClaw(claw, -ClawConstants.clawSpeed).withTimeout(2),
                new InstantCommand(() -> claw.cubeSet(), claw).withTimeout(0.5),
                new RunCommand(() -> {
                }, claw).withTimeout(1),
                new InstantCommand(() -> claw.ConeSet(), claw).withTimeout(0.5));
    }

    private Command extendArm() {
        return new SequentialCommandGroup(
                new StartArm(arm),
                new ExtendArm(arm, -ArmConstants.extendSped),
                new ExtendArm(arm, ArmConstants.extendSped));
    }
}
