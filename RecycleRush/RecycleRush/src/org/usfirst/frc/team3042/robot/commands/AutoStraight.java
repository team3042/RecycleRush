package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoStraight extends Command {

	private Logger logger = LogControl.getLogger(getClass());

	//The PID values for the control loop
	static double kP = 0.000288;
	static double kI = 0.0;
	static double kD = 0.0001;
	static final double DASHBOARD_SCALE = 1000.0;
	Boolean test = false; // if true, get PID values from the SmartDashboard

	//PID error tracking
	double oldError = 0.0; //Previous tick's error
	double cumulativeError = 0.0; //Cumulative error over the cycle of the PID loop
	
	//The distance goal for driving, and the requested speed
	double goal = 0.0;  //distance given in inches for the scaled encoder reading
	
	//Starting motor values
	double leftStartingSpeed, rightStartingSpeed;

	//The current motor values set on the talons
	double leftMotorValue = 0.0;
	double rightMotorValue = 0.0;
	
	//The direction of the speed we want to go
	double leftDirection = 1.0;
	double rightDirection = 1.0;
	
	/**
	 * Main constructor for driving straight
	 * @param goal
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public AutoStraight(double goal, double leftSpeed, double rightSpeed, Boolean test){
    	requires(Robot.leftDrivetrain);
    	requires(Robot.rightDrivetrain);

    	//Put PID values on the SmartDashboard
    	this.test = test;
    	if (test) {
    		SmartDashboard.putNumber("PID Scaling", DASHBOARD_SCALE);
    		SmartDashboard.putNumber("kP", kP*DASHBOARD_SCALE);
    		SmartDashboard.putNumber("kI", kI*DASHBOARD_SCALE);
    		SmartDashboard.putNumber("kD", kD*DASHBOARD_SCALE);
    	}
    	
    	leftStartingSpeed = leftSpeed;
    	rightStartingSpeed = rightSpeed;
    	
    	leftDirection = Math.signum(leftSpeed);
    	rightDirection = Math.signum(rightSpeed);
    	
    	this.goal = Math.abs(goal);	
	}
	
    public AutoStraight(double goal, double leftSpeed, double rightSpeed) {
    	this(goal, leftSpeed, rightSpeed, false);
    }

    public AutoStraight(double goal, double speed) {
    	this(goal, speed, speed, false);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
		logger.info("initialize()");
    	
    	//Get PID coefficients from the smart dashboard
    	if (test) {
    		kP = SmartDashboard.getNumber("kP")/DASHBOARD_SCALE;
    		kI = SmartDashboard.getNumber("kI")/DASHBOARD_SCALE;
    		kD = SmartDashboard.getNumber("kD")/DASHBOARD_SCALE;
        	logger.info("kP="+kP+" kI="+kI+" kD="+kD); //verify we got the right numbers
    	}
    	
		cumulativeError = 0.0;
		oldError = 0.0;
		leftMotorValue = leftStartingSpeed;
		rightMotorValue = rightStartingSpeed;
    	
    	//Reset the drivetrain encoders
    	Robot.leftDrivetrain.resetEncoder();
    	Robot.rightDrivetrain.resetEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Determine the error between the two sides encoder values
    	double newError = leftDistance() - rightDistance();
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
    	leftMotorValue -= leftDirection * correction;
    	rightMotorValue += rightDirection * correction;
    	
    	//Update the drivetrains with the new requested motor values
    	Robot.leftDrivetrain.setTalonValues(leftMotorValue); 
    	Robot.rightDrivetrain.setTalonValues(rightMotorValue);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return  ( (leftDistance() >= goal) && (rightDistance() >= goal) );
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
