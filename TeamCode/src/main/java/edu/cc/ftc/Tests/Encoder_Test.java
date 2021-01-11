package edu.cc.ftc.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import edu.cc.ftc.HardwareCC.Hardware1;

@TeleOp(name="Encoder Test", group="Tests")
//@Disabled
public class Encoder_Test extends OpMode {

    /* Declare OpMode members. */
    Hardware1 robot           = new Hardware1();   // Use a Pushbot's hardware

    private double drive;
    private double strafe;
    private double turn;
    private double drive1;
    private double strafe1;
    private double turn1;
    private double drive2;
    private double strafe2;
    private double turn2;
    private double lF;
    private double rF;
    private double lR;
    private double rR;
    private double spin;
    @Override
    public void init() {
        robot.init(hardwareMap);

    }

    @Override
    public void loop() {
        telemetry.addData("Right Front", robot.Drive2.getCurrentPosition());
        telemetry.addData("Left Front", robot.Drive3.getCurrentPosition());

    }
}
