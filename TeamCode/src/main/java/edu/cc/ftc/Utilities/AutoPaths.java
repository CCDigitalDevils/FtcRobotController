package edu.cc.ftc.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.cc.ftc.HardwareCC.Hardware1;

import static edu.cc.ftc.Utilities.STATE.DOWN;
import static edu.cc.ftc.Utilities.STATE.OPEN;

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

    public void zero1(){
        ae.encoderDrive(.5, 68);
        au.pause(.3);
        gu.gyroTurn(.4, -90);
        au.pause(.3);
        ae.encoderDrive(.5, 33);
        au.wobblegrab(OPEN);
        au.wobblepos(DOWN);
        au.pause(.3);
        au.strafeTime(.5, 90, .5);
        gu.gyroTurn(.4, 270, 1);
        au.pause(.3);
        ae.encoderDrive(-.5, -35);
        au.pause(.3);
        au.strafeTime(.5,-90,1.25);
    }

    public void single1(){
        au.strafeTime(.5, -90, 1.25);
        gu.gyroTurn(.4, 0, 1);
        au.pause(.3);
        ae.encoderDrive(.5, 95);
        au.pause(.3);
        gu.gyroTurn(.4, -90);
        au.pause(.3);
        ae.encoderDrive(.5, 13);
        au.wobblegrab(OPEN);
        au.wobblepos(DOWN);
        au.pause(.3);
        au.strafeTime(.5, 90, 1.5);
    }

    public void quad1(){
        au.strafeTime(.5, -90, 1.25);
        gu.gyroTurn(.4, 0, 1);
        au.pause(.3);
        ae.encoderDrive(.5, 126);
        au.pause(.3);
        gu.gyroTurn(.4, -90);
        au.pause(.3);
        ae.encoderDrive(.5, 43);
        au.wobblegrab(OPEN);
        au.wobblepos(DOWN);
        au.pause(.3);
        au.strafeTime(.5, 90, 1);
        au.pause(.3);
        gu.gyroTurn(.4, 0);
        au.pause(.3);
        ae.encoderDrive(-.5, -33);
    }

}
