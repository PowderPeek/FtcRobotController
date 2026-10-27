
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotHardware {

    // CONTROL HUB
    public DcMotor frontRight, backRight;
    public DcMotor frontLeft, backLeft;

    // EXPANSION HUB
    public DcMotorEx flywheel;
    public DcMotor frontIntake, backIntake;

    public void initDrive(HardwareMap hw) {

        frontRight = hw.get(DcMotor.class, "frontRight");
        backRight = hw.get(DcMotor.class, "backRight");
        frontLeft = hw.get(DcMotor.class, "frontLeft");
        backLeft = hw.get(DcMotor.class, "backLeft");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        for (DcMotor motor : new DcMotor[]{
                frontRight, backRight, frontLeft, backLeft
        }) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(
                    DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setPower(0);
        }
    }

    public void initMechanisms(HardwareMap hw) {

        flywheel = hw.get(DcMotorEx.class, "flywheel");
        frontIntake = hw.get(DcMotor.class, "frontIntake");
        backIntake = hw.get(DcMotor.class, "backIntake");

        flywheel.setDirection(DcMotorSimple.Direction.FORWARD);
        frontIntake.setDirection(DcMotorSimple.Direction.FORWARD);
        backIntake.setDirection(DcMotorSimple.Direction.FORWARD);

        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flywheel.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.FLOAT);

        frontIntake.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);
        backIntake.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        flywheel.setPower(0);
        frontIntake.setPower(0);
        backIntake.setPower(0);
    }
}
