/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.robot;

import com.team6647.commands.hybrid.Arm.StartArm;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.utils.shuffleboard.AutoModeSelector;
import com.team6647.utils.shuffleboard.DriveModeSelector;
import com.team6647.utils.shuffleboard.GridPlacementSelector;
import com.team6647.utils.shuffleboard.ShuffleboardManager;
import com.team6647.utils.shuffleboard.AutoModeSelector.AutoSelection;
import com.team6647.utils.shuffleboard.GridPlacementSelector.GridPlacement;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class TelemetryManager {
    private static TelemetryManager instance;

    private DriveModeSelector driveSelector;
    private AutoModeSelector autoSelector;
    private GridPlacementSelector gridSelector;
    private ShuffleboardManager interactions;

    private TelemetryManager() {
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
    }

    public void updateTelemetry() {
        interactions.updateTelemetry();
        Shuffleboard.update();
        SmartDashboard.updateValues();
    }

    public AutoSelection getAutoMode(){
        return autoSelector.getAutoMode();
    }

    public Command getDriveSelection(){
        return driveSelector.getDriveMode();
    }
    
    public GridPlacement getGridPlacementSelection(){
        return gridSelector.getSelection();
    }
}
