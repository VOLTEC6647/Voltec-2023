/**
 * Written by Juan Pablo Gutierrez
 */
package com.team6647.utils.shuffleboard;

import com.team6647.utils.Constants.ShuffleboardConstants;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Manages Grid piece placement during auto
 */
public class GridPlacementSelector {

    public enum GridPlacement {
        Bottom,
        Middle,
        Top
    }

    private SendableChooser<GridPlacement> gridSelector = new SendableChooser<>();

    public GridPlacementSelector() {
        gridSelector.setDefaultOption("Bottom", GridPlacement.Bottom);
        gridSelector.addOption("Middle", GridPlacement.Middle);
        gridSelector.addOption("Top", GridPlacement.Top);

        ShuffleboardConstants.kShuffleboardTab.add("Grid Placement", gridSelector).withPosition(7, 0);
    }

    public GridPlacement getSelection(){
        Shuffleboard.update();
        SmartDashboard.updateValues();
        return gridSelector.getSelected();
    }
}