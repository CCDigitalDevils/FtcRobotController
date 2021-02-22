package edu.cc.ftc.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


import edu.cc.ftc.HardwareCC.Hardware1;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class RPM {
    private Hardware1 robot;
    private LinearOpMode linearOpMode;
    private ElapsedTime runtime;


    private double encoderI;
    private double encoderF;
    private double encoder;
    private double timeI;
    private double timeF;
    private double time;

    private double m = 0.0;

    public RPM(Hardware1 robot, LinearOpMode linearOpMode, ElapsedTime runtime) {
        this.robot = robot;
        this.linearOpMode = linearOpMode;
        this.runtime = runtime;
    }

    public void Startup(){
        encoderI = robot.Drive4.getCurrentPosition();
        timeI = System.currentTimeMillis();
    }



}
