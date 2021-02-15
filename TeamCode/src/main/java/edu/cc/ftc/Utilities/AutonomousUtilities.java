package edu.cc.ftc.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import edu.cc.ftc.HardwareCC.Hardware1;

public class AutonomousUtilities {
    private Hardware1 robot;
    private LinearOpMode linearOpMode;
    private ElapsedTime runtime;

    public AutonomousUtilities(Hardware1 robot, LinearOpMode linearOpMode, ElapsedTime runtime) {
        this.robot = robot;
        this.linearOpMode = linearOpMode;
        this.runtime = runtime;
    }

    private void strafe(double speed, double angle) {
        angle = angle + 45;
        double lFrR = Math.sin(Math.toRadians(angle)) * speed;
        double rFlR = Math.cos(Math.toRadians(angle)) * speed;
        lFrR = Range.clip(lFrR, -1, 1);
        rFlR = Range.clip(rFlR, -1, 1);
        if (linearOpMode.opModeIsActive()) {
            robot.Drive0.setPower(lFrR);
            robot.Drive1.setPower(rFlR);
            robot.Drive2.setPower(rFlR);
            robot.Drive3.setPower(lFrR);
            System.out.printf("lFrR %s , rFlR %s %n", lFrR, rFlR);

        }
    }

    public void laucherStart(double speed){
        robot.Drive4.setPower(speed);
    }

    public void launcherStop(){
        robot.Drive4.setPower(0);
    }

    public void shoot(){
        robot.Servo0.setPosition(1);
        pause(.4);
        robot.Servo0.setPosition(.75);
        pause(.4);
    }

    public void jiggle(){
        //robot.Drive0.setPower(1);
        //robot.Drive1.setPower(1);
        //robot.Drive2.setPower(1);
        //robot.Drive3.setPower(1);
        robot.Drive0.setPower(-1);
        robot.Drive1.setPower(-1);
        robot.Drive2.setPower(-1);
        robot.Drive3.setPower(-1);
        robot.Drive0.setPower(0);
        robot.Drive1.setPower(0);
        robot.Drive2.setPower(0);
        robot.Drive3.setPower(0);
    }

    public void wobblepos(STATE pos){
        double wpos = 0;
        if (pos == STATE.MID){
            wpos = .25;
        }
        else if (pos == STATE.BOTTOM){
            wpos = 0;
        }
        else if (pos == STATE.RAISED){
            wpos = .35;
        }
        robot.Servo1.setPosition(wpos);
        robot.Servo2.setPosition(1 - wpos);
    }

    public void wobblegrab(STATE pos){
        double gpos = .75;
        if (pos == STATE.OPEN){
            gpos = 0;
        }
        else if (pos == STATE.CLOSED){
            gpos = .4;
        }
        robot.Servo3.setPosition(gpos);
    }

    public void stopMotors() {
        robot.Drive0.setPower(0);
        robot.Drive1.setPower(0);
        robot.Drive2.setPower(0);
        robot.Drive3.setPower(0);
        pause();
    }

    public void strafeTime(double speed, double angle, double time) {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            strafe(speed, angle);
            linearOpMode.telemetry.addData("Path", "Leg: %2.5f S Elapsed", runtime.seconds());
            linearOpMode.telemetry.update();
        }
        stopMotors();
    }


    public void strafeLeft(double speed, double time) {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            robot.Drive0.setPower(-speed);
            robot.Drive1.setPower(speed);
            robot.Drive2.setPower(speed);
            robot.Drive3.setPower(-speed);
            linearOpMode.telemetry.addData("Path", "Leg: %2.5f S Elapsed", runtime.seconds());
            linearOpMode.telemetry.update();
        }
        stopMotors();
    }

    public void strafeRight(double speed, double time) {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            robot.Drive0.setPower(speed);
            robot.Drive1.setPower(-speed);
            robot.Drive2.setPower(-speed);
            robot.Drive3.setPower(speed);
            linearOpMode.telemetry.addData("Path", "Leg: %2.5f S Elapsed", runtime.seconds());
            linearOpMode.telemetry.update();
        }
        stopMotors();
    }


    public void rotate(double speed, STATE state, double time) {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            if (state == STATE.LEFT) {
                robot.Drive0.setPower(-1 * speed);
                robot.Drive1.setPower(1 * speed);
                robot.Drive2.setPower(-1 * speed);
                robot.Drive3.setPower(1 * speed);
            } else if (state == STATE.RIGHT) {
                robot.Drive0.setPower(1 * speed);
                robot.Drive1.setPower(-1 * speed);
                robot.Drive2.setPower(1 * speed);
                robot.Drive3.setPower(-1 * speed);
            }
        }
    }

    public void pause() {
        long goal = System.currentTimeMillis() + 200;
        while(System.currentTimeMillis() < goal){};
    }

    public void pause(double times) {
            times *= 1000;
            long goal = System.currentTimeMillis() + ((long)times );
            while(System.currentTimeMillis() < goal && linearOpMode.opModeIsActive()){
                linearOpMode.telemetry.addData("time left = ", goal - System.currentTimeMillis());
                linearOpMode.telemetry.update();
            }
    }
}