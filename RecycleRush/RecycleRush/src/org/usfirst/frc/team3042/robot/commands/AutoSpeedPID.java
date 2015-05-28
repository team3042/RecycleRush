package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSpeedPID extends Command {
	private Logger logger = LogControl.getLogger(getClass());

	double leftGoal;
	double rightGoal;
	double leftSpeed = 0.0;
	double rightSpeed = 0.0;
	
	//A speed conversion: the drivetrain expects inches per sec, but we input a voltage percentage
	static final double SPEED_CONVERSION = 100.0;
		
    public AutoSpeedPID(double leftGoal, double rightGoal, double leftSpeed, double rightSpeed) {
    	requires(Robot.leftDrivetrain);
    	requires(Robot.rightDrivetrain);
    	
    	this.leftGoal = Math.abs(leftGoal);
    	this.rightGoal = Math.abs(rightGoal);
    	this.leftSpeed = leftSpeed*SPEED_CONVERSION;
    	this.rightSpeed = rightSpeed*SPEED_CONVERSION;
    }
    
    public AutoSpeedPID(double distance, double speed) {
    	this(distance, distance, speed, speed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
    	
    	Robot.leftDrivetrain.resetEncoder();
    	Robot.rightDrivetrain.resetEncoder();
    	
    	Robot.leftDrivetrain.driveSpeedPID(leftSpeed);
    	Robot.rightDrivetrain.driveSpeedPID(rightSpeed);
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (leftDistance() >= leftGoal){
    		Robot.leftDrivetrain.drive(0.0);
    	}
    	
    	if (rightDistance() >= rightGoal){
    		Robot.rightDrivetrain.drive(0.0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return  ( (leftDistance() >= leftGoal) && (rightDistance() >= rightGoal) );
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
    
    //the distance traveled from the start position
    private double leftDistance(){
    	return Math.abs(Robot.leftDrivetrain.getDistance());
    }
    
    //the distance traveled from the start position
    private double rightDistance(){
    	return Math.abs(Robot.rightDrivetrain.getDistance());
    }
}
