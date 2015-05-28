package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ISBAddTote extends CommandGroup {

	private Logger logger = LogControl.getLogger(getClass());
	String caller;
	
    public  ISBAddTote(String caller) {
    	this.caller = caller;
    	
    	addSequential(new ISBReleaseAndLower(caller));
    	addSequential(new ISBGrabAndRaise(caller));
  
    }
    
    
	
	protected void initialize() {
		logger.info("initialize("+caller+")");
	}

	protected void end() {
		logger.info("end("+caller+")");
	}

	protected void interrupted() {
		logger.info("interrupted("+caller+")");
	}
}
