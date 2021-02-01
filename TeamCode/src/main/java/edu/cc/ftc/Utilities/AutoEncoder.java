package edu.cc.ftc.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


import edu.cc.ftc.HardwareCC.Hardware1;

public class AutoEncoder {
    private Hardware1 robot;
    private LinearOpMode linearOpMode;
    private ElapsedTime runtime;

    public AutoEncoder(Hardware1 robot, LinearOpMode linearOpMode, ElapsedTime runtime) {
        this.robot = robot;
        this.linearOpMode = linearOpMode;
        this.runtime = runtime;
    }

    static final double     COUNTS_PER_MOTOR_REV    =134.4 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 3.5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public void encoderDrive(double speed, double Inches) {
        int newLeftTarget;
        int newRightTarget;


        // Ensure that the opmode is still active
        if (linearOpMode.opModeIsActive()) {

            robot.Drive0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.Drive1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.Drive2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.Drive3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.Drive1.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
            newRightTarget = robot.Drive0.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
            robot.Drive1.setTargetPosition(newLeftTarget);
            robot.Drive0.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.Drive1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Drive0.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            robot.Drive0.setPower(speed);
            robot.Drive1.setPower(speed);
            robot.Drive2.setPower(speed);
            robot.Drive3.setPower(speed);

            while (linearOpMode.opModeIsActive() &&
                    (robot.Drive1.isBusy() && robot.Drive0.isBusy())) {

                // Display it for the driver.
                linearOpMode.telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                linearOpMode.telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Drive1.getCurrentPosition(),
                        robot.Drive0.getCurrentPosition());
                linearOpMode.telemetry.update();
            }

            // Stop all motion;
            robot.Drive0.setPower(0);
            robot.Drive1.setPower(0);
            robot.Drive2.setPower(0);
            robot.Drive3.setPower(0);


            // Turn off RUN_TO_POSITION
            robot.Drive1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.Drive0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
}