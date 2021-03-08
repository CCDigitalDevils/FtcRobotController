package edu.cc.ftc.Utilities;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;



import edu.cc.ftc.HardwareCC.Hardware1;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class WebcamUtilities{

    private Hardware1 robot   = new Hardware1();   // Use a Pushbot's hardware
    private LinearOpMode linearOpMode;
    private ElapsedTime runtime;

    public WebcamUtilities(Hardware1 robot, LinearOpMode linearOpMode, ElapsedTime runtime) {
        this.robot = robot;
        this.linearOpMode = linearOpMode;
        this.runtime = runtime;
    }



    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AW3SFZz/////AAABmfm0Idsow0BlkNTda252oCAOx8LKumPxxoWzVRIIoEZVPVV7Y560ew6W8xfByTxxByVET+8B0g+TkcSH43UAW4f08fjS+UOZpAR0leWV7f2+TpDTSgK+ngTw8lbReRmMbMXVNMgl9WiT51LBFWxDzXrdh8jp/2VKV1Q6FxW9iAvc4BbowjrkGB2qbqwd07vzuPhGIhlezg7/EfH2a2YdijJxVf1kmJCaWbzd3Quknbwbv72NtWUV+G3/FFNNTRa5IgfdgbxpRk28NqgyOJIqGk1Bd293gyTwLYf1b4yQgVOyfLstC7gTtWnQ9zVh30vs0tqhvI23hbx0lhebB8OavTSqvJTF9xr5MGRg7pMzrXOn";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = robot.Webcam1;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = robot.tfodMonitorViewId;
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }



    public void startCamera(){
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
        }
        tfod.setZoom(2, 1.78);

    }

    public String getLabel(){
        String label = new String("");
        for(int j = 0; j <10; j++) {
            if (!linearOpMode.isStopRequested()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> Recognitions = tfod.getRecognitions();
                    //if (Recognitions != null) {
                        //telemetry.addData("# Object Detected", Recognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : Recognitions) {
                            //telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            //telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            //        recognition.getLeft(), recognition.getTop());
                            //telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            //        recognition.getRight(), recognition.getBottom());
                            label = recognition.getLabel();
                        }
                        //telemetry.update();
                    //}
                }
            }
        }
        return label;
    }

}