
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo; // ADDED

public class RobotHardware {

    // CONTROL HUB
    public DcMotor frontRight, backRight;
    public DcMotor frontLeft, backLeft;
    public Servo toggleServo; // ADDED

    // EXPANSION HUB
    public DcMotorEx flywheel, flywheel2; //UPDATED
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
        flywheel2 = hw.get(DcMotorEx.class, "flywheel2");
        frontIntake = hw.get(DcMotor.class, "frontIntake");
        backIntake = hw.get(DcMotor.class, "backIntake");
        toggleServo = hw.get(Servo.class, "toggleServo"); // ADDED

        // UPDATED: Inverting the secondary shooter motor to match rotation vectors
        flywheel.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        frontIntake.setDirection(DcMotorSimple.Direction.FORWARD);
        backIntake.setDirection(DcMotorSimple.Direction.FORWARD);

        flywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flywheel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        flywheel.setPower(0);
        flywheel2.setPower(0);
        frontIntake.setPower(0);
        backIntake.setPower(0);

        toggleServo.setPosition(0.6);
    }
}