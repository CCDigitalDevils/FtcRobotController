package edu.cc.ftc.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import edu.cc.ftc.HardwareCC.Hardware1;

@TeleOp(name="Gyro Test", group="Tests")
@Disabled
public class Gyro_Test extends OpMode {

    /* Declare OpMode members. */
    Hardware1 robot           = new Hardware1();   // Use a Pushbot's hardware

    @Override
    public void init() {
        robot.init(hardwareMap);

    }

    @Override
    public void loop() {
        Orientation o = robot.imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        telemetry.addData("x axis", o.firstAngle);
        telemetry.addData("y axis", o.secondAngle);
        telemetry.addData("z axis", o.thirdAngle);
        telemetry.update();


    }
}
