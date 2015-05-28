package org.usfirst.frc.team3042.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAdjust extends CommandGroup {
	private double firstRev;
	private double secondRev;
	private double firstRevSp;
	private double secondRevSp;
    
    public  DriveAdjust(boolean left) {
		firstRev = left ? 7 : 22;
		secondRev = left ? 22 : 7;
		firstRevSp = left ? -0.4 : -0.6;
		secondRevSp = left ? -0.6 : -0.4;
		
        addSequential(new AutoDeadDrive(firstRev, secondRev, firstRevSp, secondRevSp));
        addSequential(new DriveStop());
        addSequential(new AutoDeadDrive(secondRev, firstRev, secondRevSp, firstRevSp));
        addSequential(new DriveStop());
        
        addSequential(new AutoDeadDrive(44, 44, 0.35, 0.35));
        addSequential(new DriveStop());
    }
}
