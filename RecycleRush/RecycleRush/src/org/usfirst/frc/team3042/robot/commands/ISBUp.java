package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ISBUp extends Command {

	private Logger logger = LogControl.getLogger(getClass());

	public ISBUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires (Robot.leftISB);
    	requires(Robot.rightISB);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
    	Robot.leftISB.up();
    	Robot.rightISB.up();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("end()");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("interrupted()");
    	Robot.rightISB.setSetpoint(Robot.rightISB.getEncPosition());
    	Robot.leftISB.setSetpoint(Robot.leftISB.getEncPosition());
    }
}
