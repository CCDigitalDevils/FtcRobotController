package edu.cc.ftc.HardwareCC;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareAuto extends Hardware1{
    @Override
    public void init(HardwareMap ahwMap) {
        super.init(ahwMap);
        Drive0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Drive1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Drive2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Drive3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
