package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;
import org.usfirst.frc.team3042.robot.subsystems.ISB;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ISBLiftMatch extends Command {

	private Logger logger = LogControl.getLogger(getClass());

	//The PID values for the control loop
	static double kP = 0.001;
	static double kI = 0.0;
	static double kD = 0.0;
	
	double cumulativeError, oldError;
	
	double startSpeed = ISB.SPEED;
	double leftSpeed = 0.0;
	double rightSpeed = 0.0;

	public ISBLiftMatch() {
    	requires(Robot.rightISB);
    	requires(Robot.leftISB);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
		cumulativeError = 0.0;
		oldError = 0.0;
    	leftSpeed = startSpeed;
    	rightSpeed = startSpeed;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double newError = Robot.leftISB.getEncPosition() - Robot.rightISB.getPosition();
    	cumulativeError += newError;
    	double deltaError = newError - oldError;
    	oldError = newError; // Update the previous tick's error now
        	
    	//Find the PID correction values
    	double Pcorrection = kP * newError;
    	double Icorrection = kI * cumulativeError;
    	double Dcorrection = kD * deltaError;
    	Dcorrection = Math.copySign(Dcorrection, -Pcorrection);
    	double correction = Pcorrection + Icorrection + Dcorrection;
    	
    	//Adjust the motor values to compensate for any error in the driving
    	//This is a single PID loop controlling both sides of the drivetrain
    	leftSpeed -= correction;
    	rightSpeed += correction;
    	
    	Robot.leftISB.up(leftSpeed);
    	Robot.rightISB.up(rightSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	logger.info("isFinished()");
    	return (Robot.leftISB.reachedSetpoint() && Robot.rightISB.reachedSetpoint());
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("end()");
    	Robot.rightISB.moveToLift();
    	Robot.leftISB.moveToLift();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("interrupted()");
    	Robot.rightISB.moveToLift();
    	Robot.leftISB.moveToLift();
    }
}
