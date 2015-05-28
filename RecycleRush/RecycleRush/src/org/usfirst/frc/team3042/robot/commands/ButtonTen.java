package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ButtonTen extends Command {

	private static Command buttonTen = new ISBAddTote("ButtonTen");
	private Logger logger = LogControl.getLogger(getClass());
	String caller;
	
    public ButtonTen(String caller) {
    	this.caller = caller;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize("+caller+")");
    	buttonTen.cancel();
    	buttonTen.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return true;
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
