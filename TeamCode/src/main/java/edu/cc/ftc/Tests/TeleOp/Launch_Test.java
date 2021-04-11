package edu.cc.ftc.Tests.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import edu.cc.ftc.HardwareCC.Hardware1;
import edu.cc.ftc.Utilities.STATE;

import static edu.cc.ftc.HardwareCC.Hardware1.fixer0;
import static edu.cc.ftc.HardwareCC.Hardware1.fixer1;
import static edu.cc.ftc.HardwareCC.Hardware1.shooter0;
import static edu.cc.ftc.HardwareCC.Hardware1.shooter1;

@TeleOp(name="Launch Power Test", group="Tests")
//@Disabled
public class Launch_Test extends OpMode {

    /* Declare OpMode members. */
    Hardware1 robot           = new Hardware1();   // Use a Pushbot's hardware

    private double correction;
    private double speed;
    private double targetTime;
    private double change;

    STATE buttonA2 = STATE.OFF;
    STATE trigger2 = STATE.OFF;
    STATE pusher = STATE.OFF;
    STATE launcher = STATE.OFF;
    STATE powerShot = STATE.OFF;
    STATE fixer = STATE.OFF;



    @Override
    public void init() {
        robot.init(hardwareMap);

        correction = .5;
        change = .001;
    }

    @Override
    public void loop() {

        if(gamepad2.dpad_up){
            correction += change;
        }
        else if(gamepad2.dpad_down){
            correction -= change;
        }


        if (gamepad2.a && launcher == STATE.OFF && buttonA2 == STATE.OFF) {
            buttonA2 = STATE.INPROGRESS;
        } else if (!gamepad2.a && buttonA2 == STATE.INPROGRESS && launcher == STATE.OFF) {
            launcher = STATE.ON;
            powerShot = STATE.OFF;
            buttonA2 = STATE.OFF;
        }
        if (gamepad2.a && launcher == STATE.ON && buttonA2 == STATE.OFF) {
            buttonA2 = STATE.INPROGRESS;
        } else if (!gamepad2.a && buttonA2 == STATE.INPROGRESS && launcher == STATE.ON) {
            launcher = STATE.OFF;
            buttonA2 = STATE.OFF;
        }

        if (gamepad2.right_trigger > .10 && pusher == STATE.OFF && trigger2 == STATE.OFF && (launcher == STATE.ON || powerShot == STATE.ON)) {
            trigger2 = STATE.INPROGRESS;
        } else if (gamepad2.right_trigger < .10 && trigger2 == STATE.INPROGRESS && pusher == STATE.OFF) {
            pusher = STATE.ON;
            trigger2 = STATE.OFF;
            robot.Servo0.setPosition(shooter1);
        } else if (pusher == STATE.ON && robot.Servo0.getPosition() >= shooter1 && targetTime < System.currentTimeMillis() - 100){
            targetTime = System.currentTimeMillis() + 175;
        } else if (pusher == STATE.ON && robot.Servo0.getPosition() >= shooter1 && System.currentTimeMillis() > targetTime){
            robot.Servo0.setPosition(shooter0);
            targetTime = System.currentTimeMillis() + 100;
            robot.Servo4.setPosition(fixer1);
        } else if (pusher == STATE.ON && fixer == STATE.OFF && robot.Servo0.getPosition() <= shooter0 && System.currentTimeMillis() > targetTime){
            targetTime = System.currentTimeMillis() + 100;
            telemetry.addData(">", "1");
            fixer = STATE.ON;
        }else if (pusher == STATE.ON && fixer == STATE.ON && robot.Servo4.getPosition() >= fixer1 && System.currentTimeMillis() > targetTime){
            pusher = STATE.OFF;
            fixer = STATE.OFF;
            robot.Servo4.setPosition(fixer0);
            telemetry.addData(">", "2");
        }

        correction = Range.clip(correction, 0, 1);

        if(launcher == STATE.ON && powerShot == STATE.ON){
            powerShot = STATE.OFF;
            launcher = STATE.OFF;
        }

        if (launcher == STATE.ON){
            speed = correction;
        }
        else{
            speed = 0;
        }

        robot.Drive4.setPower(speed);
        telemetry.addData("Correction >", correction);
    }
}
