/**
 * Written by Juan Pablo Gutierrez
 */
package com.team6647.utils.shuffleboard;

import com.team6647.utils.Constants.ShuffleboardConstants;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * Manages Grid piece placement during auto
 */
public class GridPlacementSelector {

    private enum GridPlacement {
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

    public String getSelection(){
        return gridSelector.getSelected().toString();
    }
}