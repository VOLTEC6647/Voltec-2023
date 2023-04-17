package com.team6647.utils;

import com.team6647.commands.hybrid.Arm.ArmControl;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.claw.WristControl;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class TeleopUitls extends AutoUtils {

        public static Command storeArm = Commands.parallel(new ArmControl(arm, -145), new WristControl(wrist, 114),
                        new ExtendArm(teleArm, -0.5));
        /* Done */
        public static Command midstartSequence = Commands.sequence(new ArmControl(arm, -70), new WristControl(wrist, 30));

        public static Command wristCommand = Commands.sequence(new WristControl(wrist, -70),
                        new InstantCommand(() -> claw.CubeSet()), Commands.waitSeconds(1),
                        new ExtendArm(teleArm, -0.5));

        /* Done Inverted */
        public static Command midinvertedStartSequence = Commands.sequence(new ArmControl(arm, 60),
                        new WristControl(wrist, -50));

        public static Command invertedWristCommand = Commands.sequence(new WristControl(wrist, 30),
                        new InstantCommand(() -> claw.CubeSet()), Commands.waitSeconds(0.5),
                        new ExtendArm(teleArm, -0.5));

        /* Done Cube */
        public static Command cubeCommand = Commands.sequence(new WristControl(wrist, -10));

        public static Command invertedCubeCommand = Commands.sequence(new WristControl(wrist, 20));

        public static Command topStartSequence = Commands.sequence(new ArmControl(arm, -55), new WristControl(wrist, -10));

}
