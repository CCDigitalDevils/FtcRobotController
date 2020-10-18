package edu.cc.ftc.Utilities;

import edu.cc.ftc.HardwareCC.Hardware1;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class RPM {
    Hardware1 robot = new Hardware1();


    private double encoderI;
    private double encoderF;
    private double encoder;

    public void startup(){
        encoderI = robot.Drive0.getCurrentPosition();

    }

    public void tpc(){
        encoderF = robot.Drive0.getCurrentPosition();
        encoder = encoderF - encoderI;
        encoderI = encoderF;
        telemetry.addData("Ticks per code cycle = ", encoder);
    }

}
