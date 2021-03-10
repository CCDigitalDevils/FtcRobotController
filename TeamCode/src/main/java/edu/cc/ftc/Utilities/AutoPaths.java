package edu.cc.ftc.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.cc.ftc.HardwareCC.Hardware1;

import static edu.cc.ftc.Utilities.STATE.CLOSED;
import static edu.cc.ftc.Utilities.STATE.DOWN;
import static edu.cc.ftc.Utilities.STATE.MID;
import static edu.cc.ftc.Utilities.STATE.OPEN;
import static edu.cc.ftc.Utilities.STATE.RAISED;

public class AutoPaths {
    private Hardware1 robot;
    private LinearOpMode linearOpMode;
    private ElapsedTime runtime;

    private AutonomousUtilities au;
    private GyroUtilities gu;
    private AutoEncoder ae;
    private WebcamUtilities wu;

    public AutoPaths(Hardware1 robot, LinearOpMode linearOpMode, ElapsedTime runtime) {
        this.robot = robot;
        this.linearOpMode = linearOpMode;
        this.runtime = runtime;
    }

    public void shoot3(){
        au.shoot();
        au.pause(.5);
        au.shoot();
        au.pause(.5);
        au.shoot();
    }

    public void grabWobble(){
        au.wobblepos(MID);
        au.pause(.5);
        au.wobblegrab(CLOSED);
        au.pause(.5);
        au.wobblepos(RAISED);
    }

    public void dropWobble(){
        au.wobblegrab(OPEN);
        au.pause(.3);
        au.wobblepos(DOWN);
        au.pause(.3);
    }

    public void zero1(double driveSpeed, double turnSpeed){
        ae.encoderDrive(driveSpeed, 68);
        au.pause(.3);
        gu.gyroTurn(turnSpeed, -90);
        au.pause(.3);
        ae.encoderDrive(driveSpeed, 33);
        dropWobble();
    }

    public void single1(double driveSpeed, double turnSpeed){
        au.strafeTime(driveSpeed, -90, 1.25);
        gu.gyroTurn(turnSpeed, 0, 1);
        au.pause(.3);
        ae.encoderDrive(driveSpeed, 95);
        au.pause(.3);
        gu.gyroTurn(turnSpeed, -90);
        au.pause(.3);
        ae.encoderDrive(driveSpeed, 13);
        dropWobble();
    }

    public void quad1(double driveSpeed, double turnSpeed){
        au.strafeTime(driveSpeed, -90, 1.25);
        gu.gyroTurn(turnSpeed, 0, 1);
        au.pause(.3);
        ae.encoderDrive(driveSpeed, 126);
        au.pause(.3);
        gu.gyroTurn(turnSpeed, -90);
        au.pause(.3);
        ae.encoderDrive(driveSpeed, 43);
        dropWobble();
    }

    public void zero1Park(double driveSpeed, double turnSpeed){
        zero1(driveSpeed, turnSpeed);
        au.strafeTime(driveSpeed, 90, .5);
        gu.gyroTurn(turnSpeed, 270, 1);
        au.pause(.3);
        ae.encoderDrive(-driveSpeed, -35);
        au.pause(.3);
        au.strafeTime(driveSpeed,-90,1.25);
    }

    public void single1Park(double driveSpeed, double turnSpeed){
        single1(driveSpeed, turnSpeed);
        au.strafeTime(driveSpeed, 90, 1.5);
    }

    public void quad1Park(double driveSpeed, double turnSpeed){
        quad1(driveSpeed, turnSpeed);
        au.strafeTime(driveSpeed, 90, 1);
        au.pause(.3);
        gu.gyroTurn(turnSpeed, 0);
        au.pause(.3);
        ae.encoderDrive(-driveSpeed, -33);
    }

    public void zero2(double driveSpeed, double turnSpeed){
        zero1(driveSpeed, turnSpeed);
        gu.gyroTurn(turnSpeed, 0, 1);
        au.strafeTime(-driveSpeed, -170, 8);
        au.strafeTime(driveSpeed, -90, 1);
        grabWobble();
        ae.encoderDrive(driveSpeed, 68);
        gu.gyroTurn(turnSpeed, 270);
        dropWobble();
    }

    public void zero2Park(double driveSpeed, double turnSpeed){
        zero2(driveSpeed, turnSpeed);
        au.strafeTime(driveSpeed, 90, .5);
        gu.gyroTurn(turnSpeed, 270, 1);
        au.pause(.3);
        ae.encoderDrive(-driveSpeed, -35);
        au.pause(.3);
        au.strafeTime(driveSpeed,-90,1.25);
    }

}
