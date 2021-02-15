/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package edu.cc.ftc.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import edu.cc.ftc.HardwareCC.Hardware1;
import edu.cc.ftc.Utilities.RPM;
import edu.cc.ftc.Utilities.STATE;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Drive Main", group="TeleOp")
//@Disabled
public class DriveMain extends OpMode{

    /* Declare OpMode members. */
    Hardware1 robot = new Hardware1(); // use the class created to define a Pushbot's hardware
    private RPM rpm;

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
    private double pos;
    private double pushOut;
    private double correction;
    private double speed;
    private double i;
    private double j;
    private double maintain;
    private double timeI;
    private double timeF;
    private double time;
    private double encoderI;
    private double encoderF;
    private double encoder;
    private double TPC;
    private double liftmid;
    private double lifttop;
    private double liftmid2;
    private double wobblepos;

    STATE buttonA1 = STATE.OFF;
    STATE buttonA2 = STATE.OFF;
    STATE buttonB1 = STATE.OFF;
    STATE buttonX1 = STATE.OFF;
    STATE buttonX2 = STATE.OFF;
    STATE buttonY1 = STATE.OFF;
    STATE grab = STATE.OFF;
    STATE pusher = STATE.OFF;
    STATE launcher = STATE.OFF;
    STATE wobble = STATE.DOWN;
    STATE trigger2 = STATE.OFF;
    STATE power = STATE.OFF;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        correction = .72;
        maintain = 13;
        pushOut = 1;
        liftmid = .25;
        lifttop = .65;
        liftmid2 = .42;

        robot.Servo1.setPosition(-1);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        rpm = new RPM(robot, 5.0);
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        encoderI = robot.Drive4.getCurrentPosition();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        drive1  = gamepad1.left_stick_y;
        strafe1 = -gamepad1.left_stick_x;
        turn1   = -gamepad1.right_stick_x;

        drive2  = -gamepad2.left_stick_y;
        strafe2 = gamepad2.left_stick_x;
        turn2   = -gamepad2.right_stick_x;

        if(gamepad2.left_bumper){turn2 = .35;}
        else if(gamepad2.right_bumper){turn2 = -.35;}
        if (gamepad2.dpad_down){drive2 = -.35;}
        else if (gamepad2.dpad_up){drive2 = .35;}
        if (gamepad2.dpad_left){strafe2 = -.45;}
        else if (gamepad2.dpad_right){strafe2 = .45;}

        drive1 *= .75;
        strafe1 *= .75;
        turn2 *= .75;

        drive2 *= .50;
        strafe2 *= .50;
        turn2 *= .50;

        drive = drive1 + drive2;
        strafe = strafe1 + strafe2;
        turn = turn1 + turn2;

        lR = ((-strafe + drive) + turn);
        rR = ((strafe  + drive) - turn);
        lF = ((strafe  + drive) + turn);
        rF = ((-strafe + drive) - turn);

        lR = Range.clip(lR, -1, 1);
        rR = Range.clip(rR, -1, 1);
        lF = Range.clip(lF, -1, 1);
        rF = Range.clip(rF, -1, 1);

        robot.Drive0.setPower(lF);
        robot.Drive1.setPower(rF);
        robot.Drive2.setPower(lR);
        robot.Drive3.setPower(rR);



        //Loading controls
        if(gamepad1.right_trigger >= .10){
            robot.Drive5.setPower(1);
        }
        else if (gamepad1.left_trigger >= .10){
            robot.Drive5.setPower(-1);
        }
        else{
            robot.Drive5.setPower(0);
        }



        // launcher on off controls
        if (gamepad2.a && launcher == STATE.OFF && buttonA2 == STATE.OFF){
            buttonA2 = STATE.INPROGRESS;
        }
        else if (!gamepad2.a && buttonA2 == STATE.INPROGRESS && launcher == STATE.OFF ){
            launcher = STATE.ON;
            power = STATE.OFF;
            buttonA2 = STATE.OFF;
        }
        if (gamepad2.a && launcher == STATE.ON && buttonA2 == STATE.OFF){
            buttonA2 = STATE.INPROGRESS;
        }
        else if (!gamepad2.a && buttonA2 == STATE.INPROGRESS && launcher == STATE.ON){
            launcher = STATE.OFF;
            buttonA2 = STATE.OFF;
        }

        if (gamepad2.x && power == STATE.OFF && buttonX2 == STATE.OFF){
            buttonX2 = STATE.INPROGRESS;
        }
        else if (!gamepad2.x && buttonX2 == STATE.INPROGRESS && power == STATE.OFF ){
            launcher = STATE.OFF;
            power = STATE.ON;
            buttonX2 = STATE.OFF;
        }
        if (gamepad2.x && power == STATE.ON && buttonX2 == STATE.OFF){
            buttonX2 = STATE.INPROGRESS;
        }
        else if (!gamepad2.x && buttonX2 == STATE.INPROGRESS && power == STATE.ON){
            power = STATE.OFF;
            buttonX2 = STATE.OFF;
        }




        //Shooting trigger controls
        if (gamepad2.right_trigger > .25 && pusher == STATE.OFF && trigger2 == STATE.OFF  && (launcher == STATE.ON || power == STATE.ON) ){
            trigger2 = STATE.INPROGRESS;
        }
        else if (gamepad2.right_trigger < .25 && trigger2 == STATE.INPROGRESS && pusher == STATE.OFF ){
            pusher = STATE.ON;
            trigger2 = STATE.OFF;
            robot.Servo0.setPosition(1);
        }
        else if(pusher == STATE.ON && robot.Servo0.getPosition() == 1 && j >= 75){
            pusher = STATE.OFF;
            robot.Servo0.setPosition(.75);
            j = 0;
        }
        else if(pusher == STATE.ON && robot.Servo0.getPosition() == 1){
            j++;
        }


        //Close wobble grabber
        if (gamepad1.a && grab == STATE.OFF && buttonA1 == STATE.OFF){
            buttonA1 = STATE.INPROGRESS;
        }
        else if (!gamepad1.a && buttonA1 == STATE.INPROGRESS && grab == STATE.OFF ){
            grab = STATE.ON;
            buttonA1 = STATE.OFF;
            robot.Servo3.setPosition(.4);
        }
        if (gamepad1.a && grab == STATE.ON && buttonA1 == STATE.OFF){
            buttonA1 = STATE.INPROGRESS;
        }
        else if (!gamepad1.a && buttonA1 == STATE.INPROGRESS && grab == STATE.ON){
            grab = STATE.OFF;
            buttonA1 = STATE.OFF;
            robot.Servo3.setPosition(0);
        }


        // Move wobble lifter to mid if up or down
        // Move wobble lifter to down if mid
        if (gamepad1.x && buttonX1 == STATE.OFF){
            buttonX1 = STATE.INPROGRESS;
        }
        else if (!gamepad1.x && buttonX1 == STATE.INPROGRESS && wobble == STATE.MID){
            wobble = STATE.DOWN;
            buttonX1 = STATE.OFF;
            wobblepos = 0;
        }
        else if (!gamepad1.x && buttonX1 == STATE.INPROGRESS){
            wobble = STATE.MID;
            buttonX1 = STATE.OFF;
            wobblepos = liftmid;
        }


        // Move wobble lifter to up if mid or mid2
        // Move wobble lifter to mid if up
        if (gamepad1.b && (wobble == STATE.MID || wobble == STATE.MID2) && buttonB1 == STATE.OFF){
            buttonB1 = STATE.INPROGRESS;
        }
        else if (!gamepad1.b && buttonB1 == STATE.INPROGRESS && (wobble == STATE.MID || wobble == STATE.MID2) ){
            wobble = STATE.UP;
            buttonB1 = STATE.OFF;
            wobblepos = lifttop;
        }
        if (gamepad1.b && wobble == STATE.UP && buttonB1 == STATE.OFF){
            buttonB1 = STATE.INPROGRESS;
        }
        else if (!gamepad1.b && buttonB1 == STATE.INPROGRESS && wobble == STATE.UP){
            wobble = STATE.MID;
            buttonB1 = STATE.OFF;
            wobblepos = liftmid;
        }

        // Move wobble lifter to mid2 if up
        // Move wobble lifter to up if mid2
        if (gamepad1.y && wobble == STATE.UP && buttonY1 == STATE.OFF){
            buttonY1 = STATE.INPROGRESS;
        }
        else if (!gamepad1.y && buttonY1 == STATE.INPROGRESS && wobble == STATE.UP ){
            wobble = STATE.MID2;
            buttonY1 = STATE.OFF;
            wobblepos = liftmid2;
            grab = STATE.OFF;
            robot.Servo3.setPosition(0);
        }
        if (gamepad1.y && wobble == STATE.MID2 && buttonY1 == STATE.OFF){
            buttonY1 = STATE.INPROGRESS;
        }
        else if (!gamepad1.y && buttonY1 == STATE.INPROGRESS && wobble == STATE.MID2){
            wobble = STATE.UP;
            buttonY1 = STATE.OFF;
            wobblepos = lifttop;
        }


        telemetry.addData("pusher", pusher);
        telemetry.addData("buttonX", trigger2);
        telemetry.addData("push pos", robot.Servo0.getPosition() );

        pos = robot.Drive4.getCurrentPosition();

        telemetry.addData("spin speed", spin);
        telemetry.addData("position", pos);

        encoderF = robot.Drive4.getCurrentPosition();
        encoder = encoderF - encoderI;
        telemetry.addData("Ticks encoderI = ", encoderI);
        encoderI = encoderF;
        telemetry.addData("Ticks encoderF = ", encoderF);


        telemetry.addData("Ticks per code cycle = ", encoder);
/*
        if (launcher == STATE.ON && encoder > maintain + 1){
            correction = correction - .0005;
        }
        if (launcher == STATE.ON && encoder < maintain - 1 && i > 500){
            correction = correction + .0005;
        }
        if (launcher == STATE.ON){
            i++;
        }
        else{
            i = 0;
        }
*/
        telemetry.addData("correction = ", correction);

        correction = Range.clip(correction, 0, 1);

        if(launcher == STATE.ON && power == STATE.ON){
            power = STATE.OFF;
            launcher = STATE.OFF;
        }

        if (launcher == STATE.ON){
            speed = correction;
        }
        else if (power == STATE.ON){
            speed = correction - .09;
        }
        else{
            speed = 0;
        }

        robot.Drive4.setPower(speed);
        robot.Servo1.setPosition(wobblepos);
        robot.Servo2.setPosition(1 - wobblepos);







    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
