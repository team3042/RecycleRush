package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCanAndBack extends CommandGroup {
		
    public  AutoCanAndBack() {
    	addParallel(new TLAMoveToTop()); //grab the can with the TLA
    	addSequential(new AutoDeadDrive(22, -0.3)); //drive backward into auto zone
    	addSequential(new AutoDeadDrive(10, 10, 0.5, -0.5));
    	addSequential(new AutoDeadDrive(100, -0.35));
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
