/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.auto;

import com.team6647.commands.hybrid.Arm.ArmControl;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.utils.AutoUtils;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/**
 * Command for grabbing a cone from the floor
 */
public class GrabCone extends AutoUtils {

    public static Command grabCone(){
        return Commands.sequence(
            new InstantCommand(() -> claw.CubeSet(), claw),
            new ArmControl(arm, 100),
            new ParallelCommandGroup(
                new ExtendArm(arm, 0.3).withTimeout(0.5),
                new TankDriveAutoCommand(chassis, -0.3, -0.3)
            ).withTimeout(1.2),
            new InstantCommand(() -> claw.ConeSet(), claw),
            Commands.waitSeconds(1),
            new ArmControl(arm, -50)
        );
    }
}
