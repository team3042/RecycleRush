package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.ISBMaintain;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class ISB extends PIDSubsystem {
		
		public static enum SIDE {LEFT, RIGHT;}
	
		//Motor Controllers
		CANTalon talon;
		
		//Digital IO
		static DigitalInput photoEye = new DigitalInput(RobotMap.PHOTO_EYE_PORT);
		static boolean photoEyeOn = false;
		
		//The constants for the PID loop
		private static final double Kp = 0.0020; //Original value
		private static final double Lp = 0.0023; 
		private static final double Rp = 0.0020;
		private static final double Ki = 0.0;
		private static final double Kd = 0.00085;

		//Setpoints
		private static final double GRAB_POSITION = 0.0;
		private static final double DRIVE_POSITION = (RobotMap.isLittle()) ? 750.0 : -750.0;
		public static final double DROP_POSITION = (RobotMap.isLittle()) ? 5000.0 : -5000.0; 
		private static final double LIFT_POSITION = (RobotMap.isLittle()) ? 7300.0 : -7300.0;
	
		//Motor input limits
		static final double MAX_MOTOR = 1.0;
		static final double MIN_MOTOR = -1.0;
		public static final double SPEED = 0.8;
		
		//Encoder setpoint tolerance
		static final double SETPOINT_TOLERANCE = 144.0;
		
		/**
		 * Constructor
		 * 
		 * Creates instances of the motors, the solenoid.
		 * 
		 */
		public ISB(int talonID, SIDE side){
				super("ISB", (side == SIDE.LEFT)? Lp : Rp, Ki, Kd);

			//Instantiate the talon, and solenoid
			talon = new CANTalon(talonID);
			talon.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
									
			//Reset the encoder value
			//	currently assumes the robot starts in the zero position
			resetEncoder();
		}
		
		/**
		 *Set the default command for the subsystem
		 */
	    public void initDefaultCommand() {
	    	setDefaultCommand(new ISBMaintain());
	    }
	    
	    /**
	     * Set the motor values
	     * Limit the differential
	     * Perform a safety check
	     * @param motorValue Desired motor value
	     */
	    public void setMotor(double motorValue){
	    	motorValue = safetyCheck(motorValue);
	    	talon.set(motorValue);
	    }
	    
	    /**
	     * Make sure the motor values to not exceed the bounds of the motor controller
	     * @param input Ensures motor speed is in range [-1, 1]
	     * @return Checked value
	     */
	    public double safetyCheck(double input){
	    	if(input > MAX_MOTOR) input = MAX_MOTOR;
	    	if(input < MIN_MOTOR) input = MIN_MOTOR;
	    	return input;
	    }
	    
	    public void up(){
	    	up(SPEED);
	    }
	    
	    /**
	     * Moves the carriages up
	     */
	    public void up(double speed){
	    	disablePID();
	    	if (getEncPosition() < LIFT_POSITION) {
	    		setMotor(speed);
	    	}
	    	else {
	    		setMotor(0.0);
	    	}
	    }
	    /**
	     * Moves the carriages down
	     */
	    public void down(){
	    	disablePID();
	    	if (getEncPosition() > GRAB_POSITION) {
	    		setMotor(-SPEED);
	    	}
	    	else {
	    		setMotor(0.0);
	    	}
	    }
	    
	    /**
	     * Temporary for testing test bed ISB; stops the carriages
	     */
	    public void stop(){
	    	disablePID();
	    	setMotor(0.0);
	    }
	    
	    /**
	     * Sets the ISB to the low position; ready to accept totes
	     */
	    public void moveToGrab(){
	    	setSetpoint(GRAB_POSITION);
	    	enablePID();
	    }
	    
	    /**
	     * Sets the ISB to the low position; ready to accept totes
	     */
	    public void moveToDrop(){
	    	setSetpoint(DROP_POSITION);
	    	enablePID();
	    }
	    
	    /**
	     * Sets the ISB to the high position; tote is now laying on one way hinges
	     */
	    public void moveToLift(){
	    	setSetpoint(LIFT_POSITION);
	    	enablePID();
	    }
	    
	    public void moveToDrive(){
	    	setSetpoint(DRIVE_POSITION);
	    	enablePID();
	    }
	    
	    public boolean isInDropZone(){
	    	return ( Math.abs(getEncPosition()) < Math.abs(DROP_POSITION) );
	    }
	    
	    /**
	     * determine if the photoeye has detected something
	     * @return
	     */
	    public static boolean photoEye(){
	    	return (!photoEye.get() && photoEyeOn);
	    	
	    }
	    
	    /**
	     * Turn the photoeye on
	     */
	    public static void PhotoEyeOn(){
	    	photoEyeOn = true;
	    }
	    
	    /**
	     * Turn the photoeye off
	     */
	    public static void PhotoEyeOff(){
	    	photoEyeOn = false;
	    }
	    
	    /**
	     * Reset the encoder values
	     */
	    public void resetEncoder(){
	    	talon.setPosition(0.0);
	    	this.setSetpoint(0.0);
	    }
	    
	    /**
	     * Get the encoder position
	     */
	    public double getEncPosition(){
	    	return talon.getEncPosition();
	    }
	    
	    public void maintain(){
	    	enablePID();
	    }
	    
	    public boolean reachedSetpoint(){
	    	if(Math.abs(this.getEncPosition() - this.getSetpoint()) < SETPOINT_TOLERANCE)
	    		return true;
	    	else
	    		return false;
	    }
	    
	    /**
	     *The sensor input value for the PID loop
	     */
	    protected double returnPIDInput() {
	     	return getEncPosition();
	    }
	    
	    /**
	     *The output of the PID loop, used to set the motor value.
	     */
	    public void usePIDOutput(double output) {	    	
	    	setMotor(output);
	    }
	    
	    /**
	     * Enable PID loop, if it is not already running
	     */
	    private void enablePID(){
	    	if (!getPIDController().isEnable()) enable();
	    }
	    /**
	     * Disable PID loop if it is running
	     */
	    private void disablePID(){
	    	if (getPIDController().isEnable()) disable();
	    }	    
	}
