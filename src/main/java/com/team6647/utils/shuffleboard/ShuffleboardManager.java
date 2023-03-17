/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.utils.shuffleboard;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.andromedalib.shuffleboard.tabs.DifferentialDriveInfo;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.utils.Constants.ShuffleboardConstants;
import com.team6647.utils.shuffleboard.tabs.DebugTab;
import com.team6647.utils.shuffleboard.tabs.DefaultTab;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShuffleboardManager {

    public static ShuffleboardManager instance;

    static ShuffleboardTabBase[] list;

    public boolean debug = true;

    /**
     * Private Constructor
     */
    private ShuffleboardManager() {
        if (debug) {
            list = new ShuffleboardTabBase[] {
                    new DifferentialDriveInfo(ShuffleboardConstants.kShuffleboardTab, ChassisSubsystem.getInstance()),
                    new DebugTab(ShuffleboardConstants.kShuffleboardTab),
            };
        } else {
            list = new ShuffleboardTabBase[] {
                    new DifferentialDriveInfo(ShuffleboardConstants.kShuffleboardTab, ChassisSubsystem.getInstance()),
                    new DefaultTab(ShuffleboardConstants.kShuffleboardTab),
            };
        }
    }

    /**
     * Initializes a new {@link ShuffleboardManager}
     * 
     * @return {@link ShuffleboardManager} singleton instance
     */
    public static ShuffleboardManager getInstance() {
        if (instance == null) {
            instance = new ShuffleboardManager();
        }
        return instance;
    }

    /**
     * Updates the telemetry of all tabs. Run this manually in the {@link Robot}
     * RobotPeriodic method 
     */
    public void updateTelemetry() {
        if (list != null) {
            for (ShuffleboardTabBase tab : list) {
                tab.updateTelemetry();
            }
        }
        Shuffleboard.update();
        SmartDashboard.updateValues();
    }

}
