package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveToZone extends CommandGroup {
		
    public  AutoDriveToZone() {
    	addSequential(new AutoDeadDrive(47, 0.3)); //drive forward into auto zone
    	addSequential(new DriveStop());    
    }
    
	private Logger logger = LogControl.getLogger(getClass());
    
    protected void initialize() {
		logger.info("initialize()");
	}

	protected void end() {
		logger.info("end()");
	}

	protected void interrupted() {
		logger.info("interrupted()");
	}
}
