package edu.cc.ftc.Autonomous;/* Copyright (c) 2017 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import edu.cc.ftc.HardwareCC.Hardware1;
import edu.cc.ftc.Utilities.AutoEncoder;
import edu.cc.ftc.Utilities.AutoPaths;
import edu.cc.ftc.Utilities.AutonomousUtilities;
import edu.cc.ftc.Utilities.GyroUtilities;
import edu.cc.ftc.Utilities.WebcamUtilities;

import static edu.cc.ftc.Utilities.STATE.CLOSED;
import static edu.cc.ftc.Utilities.STATE.DOWN;
import static edu.cc.ftc.Utilities.STATE.MID;
import static edu.cc.ftc.Utilities.STATE.OPEN;
import static edu.cc.ftc.Utilities.STATE.RAISED;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *   - Stop and close the claw.
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Auto Red", group="Red")
//@Disabled
public class AutoRed extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware1 robot   = new Hardware1();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    private AutonomousUtilities au;
    private GyroUtilities gu;
    private AutoEncoder ae;
    private WebcamUtilities wu;
    private AutoPaths path;

    String label = "";

    @Override
    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        au = new AutonomousUtilities(robot, this, runtime);
        gu = new GyroUtilities(robot, this, runtime);
        ae = new AutoEncoder(robot,this,runtime);
        wu = new WebcamUtilities(robot, this, runtime);
        path = new AutoPaths(robot, this, runtime);



        wu.startCamera();
        while(!isStarted()){
            label = wu.getLabel();
            if(label.equals("")){
                label = "none";
            }
            telemetry.addData(">", "Ready");
            telemetry.addData(">", label);
            telemetry.update();
        }


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
       // label = wu.getLabel();
        telemetry.addData(">", label);
        au.laucherStart(.73);

        au.wobblepos(MID);
        au.pause(.5);
        au.wobblegrab(CLOSED);
        au.pause(.5);
        au.wobblepos(RAISED);

        //Shoot 3 discs

        au.pause(.5);
        au.shoot();
        au.pause(.5);
        au.shoot();
        au.pause(.5);
        au.shoot();
        au.launcherStop();

        //Take path based on ring stack
        if(label == "Single"){
            telemetry.addData(">", "Single");
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
        else if(label == "Quad"){
            telemetry.addData(">", "Quad");
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
        else{
            telemetry.addData(">", "null");
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

}
}
