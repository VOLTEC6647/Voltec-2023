package com.team6647.commands.auto;

import com.team6647.utils.AutoUtils;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class GrabCone extends AutoUtils{
    
    public static Command grabCone(){
        return Commands.sequence(
            new ParallelCommandGroup(
                new InstantCommand(() -> claw.CubeSet(), claw),
                new RunCommand(() -> chassis.tankDrive(0.3, 0.3), chassis).withTimeout(0.5)
            ),
            new RunCommand(() -> chassis.tankDrive(0.3, 0.3), chassis).withTimeout(1.2),
            new ParallelCommandGroup(
                new RunCommand(() -> chassis.tankDrive(0.3, 0.3), chassis).withTimeout(1.2),
                new InstantCommand(() -> claw.ConeSet(), claw)
            )
        );
    }
}
