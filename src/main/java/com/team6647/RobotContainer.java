// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647;

import com.andromedalib.shuffleboard.MotorInfoTab;
import com.andromedalib.shuffleboard.ShuffleboardInteractions;
import com.team6647.Constants.OperatorConstants;
import com.team6647.Constants.ShuffleboardConstants;
import com.team6647.commands.TankDriveCommand;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.VisionSubsystem;
import com.team6647.utils.DriveModeSelector;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  private DriveModeSelector selector;
  private ShuffleboardInteractions interactions;
  private MotorInfoTab motorTab;

  private ChassisSubsystem chassis;
  private VisionSubsystem vision;
  private ArmSubsystem arm;

  private final CommandXboxController driverController1 = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  private final CommandXboxController driverController2 = new CommandXboxController(
      OperatorConstants.kDriverControllerPort2);

  public RobotContainer() {
  }

  /**
   * Initializes every  the subsystems. Call this function from the
   * {@link RobotContainer} class
   */
  public void initSubsystems() {
    chassis = ChassisSubsystem.getInstance();

    /*
     * arm = ArmSubsystem.getInstance();
     * vision = VisionSubsystem.getInstance("AprilCamera");
     */
  }

  /**
   * Initializes the sending of telemetry
   */
  public void initTelemetry() {
    interactions = ShuffleboardInteractions.getInstance(ShuffleboardConstants.kShuffleboardTab);
    motorTab = MotorInfoTab.getInstance(ShuffleboardConstants.kMotorDebugTab);
    selector = new DriveModeSelector();
  }

  public void configureBindings() {
    chassis.setDefaultCommand(new TankDriveCommand(chassis, driverController1));

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
    return null;
    // return Autos.exampleAuto(m_exampleSubsystem);
  }
}
