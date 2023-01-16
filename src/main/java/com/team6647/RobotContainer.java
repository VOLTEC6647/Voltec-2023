// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647;

import com.team6647.Constants.OperatorConstants;
import com.team6647.commands.Autos;
import com.team6647.commands.DriveCommand;
import com.team6647.commands.ExampleCommand;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  private ChassisSubsystem chassis;

  /**
   * Initializes every  the subsystems
   */
  public void initSubsystems() {
    chassis = ChassisSubsystem.getInstance();
  }

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  private final CommandXboxController driverController1 = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  private final CommandXboxController driverController2 = new CommandXboxController(
      OperatorConstants.kDriverControllerPort2);

  public RobotContainer() {

  }

  public void configureBindings() {
    chassis.setDefaultCommand(new DriveCommand(chassis, driverController1));

    /*
     * // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
     * new Trigger(m_exampleSubsystem::exampleCondition)
     * .onTrue(new ExampleCommand(m_exampleSubsystem));
     * 
     * // Schedule `exampleMethodCommand` when the Xbox controller's B button is
     * // pressed,
     * // cancelling on release.
     * m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
     */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
