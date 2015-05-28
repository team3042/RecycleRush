package org.usfirst.frc.team3042.robot.triggers;

import org.usfirst.frc.team3042.robot.subsystems.ISB;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class PhotoEye extends Trigger {
    
    public boolean get() {    	
        return ISB.photoEye();
    }
}
