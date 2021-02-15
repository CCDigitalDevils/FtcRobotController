package edu.cc.ftc.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import edu.cc.ftc.HardwareCC.Hardware1;

@TeleOp(name="Drive Test", group="Tests")
@Disabled
public class Drive_Test extends OpMode {

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
        if (gamepad1.a || gamepad1.b || gamepad1.x || gamepad1.y) {
            if (gamepad1.a) robot.Drive0.setPower(1);
            else robot.Drive0.setPower(0);
            if (gamepad1.b) robot.Drive1.setPower(1);
            else robot.Drive1.setPower(0);
            if (gamepad1.x) robot.Drive2.setPower(1);
            else robot.Drive2.setPower(0);
            if (gamepad1.y) robot.Drive3.setPower(1);
            else robot.Drive3.setPower(0);
        }
        else {
            drive1 = gamepad1.left_stick_y;
            strafe1 = -gamepad1.left_stick_x;
            turn1 = -gamepad1.right_stick_x;

            drive2 = -gamepad2.left_stick_y;
            strafe2 = gamepad2.left_stick_x;
            turn2 = -gamepad2.right_stick_x;

            drive2 *= .60;
            strafe2 *= .60;
            turn2 *= .60;

            drive = drive1 + drive2;
            strafe = strafe1 + strafe2;
            turn = turn1 + turn2;

            lR = ((-strafe + drive) + turn);
            rR = ((strafe + drive) - turn);
            lF = ((strafe + drive) + turn);
            rF = ((-strafe + drive) - turn);

            lR = Range.clip(lR, -1, 1);
            rR = Range.clip(rR, -1, 1);
            lF = Range.clip(lF, -1, 1);
            rF = Range.clip(rF, -1, 1);

            robot.Drive0.setPower(lF);
            robot.Drive1.setPower(rF);
            robot.Drive2.setPower(lR);
            robot.Drive3.setPower(rR);
        }

        telemetry.addData("LeftFront", robot.Drive0.getPower());
        telemetry.addData("RightFront", robot.Drive1.getPower());
        telemetry.addData("LeftRear", robot.Drive2.getPower());
        telemetry.addData("RightRear", robot.Drive3.getPower());
    }
}
