package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ISBMoveToLift extends Command {

	private Logger logger = LogControl.getLogger(getClass());
	String caller;

	public ISBMoveToLift(String caller) {
		this.caller = caller;
		
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.rightISB);
    	requires(Robot.leftISB);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize("+caller+")");
    	this.setTimeout(2.5);
    	Robot.rightISB.moveToLift();
    	Robot.leftISB.moveToLift();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	logger.fine("execute(): leftEncPos(" + Robot.leftISB.getEncPosition() + "), rightEncPos(" + Robot.rightISB.getEncPosition() + ")");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if ((Robot.leftISB.reachedSetpoint() && Robot.rightISB.reachedSetpoint()) || this.isTimedOut())
    		return true;
    	else
    		return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        logger.info("end("+caller+")");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("interrupted("+caller+")");
    }
}
