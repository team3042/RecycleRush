package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ISBMaintain extends Command {

	private Logger logger = LogControl.getLogger(getClass());

	public ISBMaintain() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.rightISB);
    	requires(Robot.leftISB);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
    	Robot.rightISB.maintain();
    	Robot.leftISB.maintain();
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
    }
}
