/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.auto;

import com.team6647.commands.hybrid.Arm.ArmControl;
import com.team6647.commands.hybrid.claw.ParallelWrist;
import com.team6647.utils.AutoUtils;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

/**
 * Command for grabbing a cone from the floor
 */
public class GrabCone extends AutoUtils {

    public static Command grabCone() {
        return new TankDriveAutoCommand(chassis, 0.3, 0).withTimeout(1)
                .andThen(Commands.runOnce(() -> claw.ConeSet(), claw)).andThen(AutoUtils.defaultArmPosition());
    }

    /**
     * Sets the arm to be ready for pickup
     * 
     * @return Arm Command
     */
    public static Command readyArm() {
        return Commands.sequence(
                new ArmControl(arm, -120),
                Commands.runOnce(() -> claw.CubeSet(), claw),
                Commands.waitSeconds(1),
                new ParallelWrist(wrist));
    }
}
