package edu.cc.ftc.Utilities;

import edu.cc.ftc.HardwareCC.Hardware1;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class RPM {
    private Hardware1 robot;


    private double encoderI;
    private double encoderF;
    private double encoder;

    private double m = 0.0;

    public RPM(Hardware1 robot, double maintain)
    {
        this.robot = robot;
        this.m = maintain;
    }

    public void updateMaintain(double maintain)
    {
        m = maintain;
    }
    public void startup(){
        encoderI = robot.Drive0.getCurrentPosition();

    }

    public double calcCorrection()
    {
        double c = 0.0;
        //do whate ever



        return c;
    }
    public void tpc(){
        encoderF = robot.Drive0.getCurrentPosition();
        encoder = encoderF - encoderI;
        encoderI = encoderF;
        telemetry.addData("Ticks per code cycle = ", encoder);
    }

}
