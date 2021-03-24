package edu.cc.ftc.Tests.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import edu.cc.ftc.HardwareCC.Hardware1;
import edu.cc.ftc.Utilities.STATE;

@TeleOp(name="Servo Test", group="Tests")
//@Disabled
public class Servo_Test extends OpMode {

    /* Declare OpMode members. */
    Hardware1 robot           = new Hardware1();   // Use a Pushbot's hardware

    private double currentServo = 0;
    private double servoPos = 0;
    private double servoChange = .001;
    private STATE down  = STATE.OFF;
    private STATE up = STATE.OFF;

    @Override
    public void init() {
        robot.init(hardwareMap);

    }

    @Override
    public void loop() {
        if(gamepad1.dpad_down && currentServo >= 0 && down == STATE.OFF){
            down = STATE.INPROGRESS;
        }
        else if(!gamepad1.dpad_down && down == STATE.INPROGRESS){
            down = STATE.OFF;
            currentServo -= 1;
        }
        if(gamepad1.dpad_up && currentServo <= 11 && up == STATE.OFF){
            up = STATE.INPROGRESS;
        }
        else if(!gamepad1.dpad_up && up == STATE.INPROGRESS){
            up = STATE.OFF;
            currentServo += 1;
        }


        if(currentServo == 0){
            if(gamepad1.right_trigger > 0){
                robot.Servo0.setPosition(robot.Servo0.getPosition() + servoChange);
                servoPos = robot.Servo0.getPosition();
            }
            else if(gamepad1.left_trigger > 0){
                robot.Servo0.setPosition(robot.Servo0.getPosition() - servoChange);
                servoPos = robot.Servo0.getPosition();
            }
        }
        if(currentServo == 1 || currentServo == 2){
            if(gamepad1.right_trigger > 0){
                robot.Servo1.setPosition(robot.Servo1.getPosition() + servoChange);
                robot.Servo2.setPosition(robot.Servo2.getPosition() - servoChange);
                servoPos = robot.Servo1.getPosition();
            }
            else if(gamepad1.left_trigger > 0){
                robot.Servo1.setPosition(robot.Servo1.getPosition() - servoChange);
                robot.Servo2.setPosition(robot.Servo2.getPosition() + servoChange);
                servoPos = robot.Servo1.getPosition();
            }
        }
        if(currentServo == 3){
            if(gamepad1.right_trigger > 0){
                robot.Servo3.setPosition(robot.Servo3.getPosition() + servoChange);
                servoPos = robot.Servo3.getPosition();
            }
            else if(gamepad1.left_trigger > 0){
                robot.Servo3.setPosition(robot.Servo3.getPosition() - servoChange);
                servoPos = robot.Servo3.getPosition();
            }
        }
        if(currentServo == 4){
            if(gamepad1.right_trigger > 0){
                robot.Servo4.setPosition(robot.Servo4.getPosition() + servoChange);
                servoPos = robot.Servo4.getPosition();
            }
            else if(gamepad1.left_trigger > 0){
                robot.Servo4.setPosition(robot.Servo4.getPosition() - servoChange);
                servoPos = robot.Servo4.getPosition();
            }
        }

        telemetry.addData("Servo Selected:", currentServo);
        telemetry.addData("Servo Position:", servoPos);
    }
}
