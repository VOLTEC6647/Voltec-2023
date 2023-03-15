/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.robot;

import com.team6647.commands.auto.ProtocolCommand;
import com.team6647.commands.hybrid.Arm.StartArm;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.utils.shuffleboard.AutoModeSelector;
import com.team6647.utils.shuffleboard.DriveModeSelector;
import com.team6647.utils.shuffleboard.GridPlacementSelector;
import com.team6647.utils.shuffleboard.ShuffleboardManager;
import com.team6647.utils.shuffleboard.GridPlacementSelector.GridPlacement;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class TelemetryManager {
    private static TelemetryManager instance;

    private ArmSubsystem arm;
    private ChassisSubsystem chassis;
    private ClawSubsytem claw;

    private DriveModeSelector driveSelector;
    private AutoModeSelector autoSelector;
    private GridPlacementSelector gridSelector;
    private ShuffleboardManager interactions;
    private ProtocolCommand protocolCommand;

    private TelemetryManager() {
        arm = ArmSubsystem.getInstance();
        chassis = ChassisSubsystem.getInstance();
        claw = ClawSubsytem.getInstance();
    }

    public static TelemetryManager getInstance() {
        if (instance == null) {
          instance = new TelemetryManager();
        }
        return instance;
      }

    public void initTelemetry() {
        driveSelector = new DriveModeSelector();
        gridSelector = new GridPlacementSelector();
        autoSelector = new AutoModeSelector();
        interactions = ShuffleboardManager.getInstance();
        protocolCommand = new ProtocolCommand(arm, chassis, claw);
    }

    public void updateTelemetry() {
        interactions.updateTelemetry();
    }

    public Command getAutoSelection(){
        return autoSelector.getAutoMode();
    }

    public Command getProtocolSelection(){
        return protocolCommand.getStartCommand();
    }

    public Command getDriveSelection(){
        return driveSelector.getDriveMode();
    }
    
    public GridPlacement getGridPlacementSelection(){
        return gridSelector.getSelection();
    }

    /**
     * Use this to pass the test command to the main {@link Robot} class.
     *
     * @return the command to run in test
     */
    public Command getTestCommand() {
        return Commands.sequence(new StartArm(arm), protocolCommand.getStartCommand());
    }
}
