package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@Autonomous(name = "BioBuzz AUTO ENCODER - RED", group = "Red") // change for red/blue
public class BioBuzzRedEncoderAuto extends LinearOpMode {

    // Encoder Calculation Constants (Adjust based on your actual motor choice)
    // Example: goBILDA 5203 series 19.2:1 motor has 537.7 ticks per revolution
    private static final double TICKS_PER_REV = 537.7;
    private static final double WHEEL_DIAMETER_INCHES = 3.77953; // 96mm mecanum wheels
    private static final double TICKS_PER_INCH = TICKS_PER_REV / (WHEEL_DIAMETER_INCHES * Math.PI);

    private RobotHardware robot;

    @Override
    public void runOpMode() {
        robot = new RobotHardware();
        robot.initDrive(hardwareMap);
        robot.initMechanisms(hardwareMap);

        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);
        Gamepad autoGamepad = new Gamepad();

        telemetry.addLine("RED ENCODER AUTO READY"); //change for red/blue
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // STEP 1: Spin up the flywheel shooter
            autoGamepad.left_trigger = 0.75f;
            shooter.update(autoGamepad);
            sleep(2000); // We still use sleep for mechanism timing loops

            // STEP 2: Feed Preloads Forward
            autoGamepad.right_trigger = 1.0f;
            intake.update(autoGamepad);
            sleep(1500);

            // Stop Scoring Mechanisms safely
            autoGamepad.left_trigger = 0.0f;
            autoGamepad.right_trigger = 0.0f;
            shooter.stop();
            intake.stop();

            // STEP 3: Drive Forward exactly 24 inches using Encoders
            telemetry.addData("Auto Status", "Encoder Drive: Forward");
            telemetry.update();
            encoderDrive(0.4, 24, 24, 24, 24);

            // STEP 4: Strafe RIGHT exactly 18 inches into Red Parking Zone
            // Mecanum strafe math directions: FL(+), BL(-), FR(-), BR(+)
            telemetry.addData("Auto Status", "Encoder Drive: Strafing Right");
            telemetry.update();
            encoderDrive(0.5, 18, -18, -18, 18);

            telemetry.addLine("Red Encoder Auto Complete!");
            telemetry.update();
        }
    }

    /**
     * Executes targeted spatial movement using internal motor control loops
     */
    public void encoderDrive(double speed, double flInches, double blInches, double frInches, double brInches) {
        int newFLTarget, newBLTarget, newFRTarget, newBRTarget;

        if (opModeIsActive()) {
            // Determine target positions based on tick metrics
            newFLTarget = robot.frontLeft.getCurrentPosition() + (int)(flInches * TICKS_PER_INCH);
            newBLTarget = robot.backLeft.getCurrentPosition() + (int)(blInches * TICKS_PER_INCH);
            newFRTarget = robot.frontRight.getCurrentPosition() + (int)(frInches * TICKS_PER_INCH);
            newBRTarget = robot.backRight.getCurrentPosition() + (int)(brInches * TICKS_PER_INCH);

            robot.frontLeft.setTargetPosition(newFLTarget);
            robot.backLeft.setTargetPosition(newBLTarget);
            robot.frontRight.setTargetPosition(newFRTarget);
            robot.backRight.setTargetPosition(newBRTarget);

            // Turn on RUN_TO_POSITION loop mode
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Set drivetrain velocities
            robot.frontLeft.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));
            robot.frontRight.setPower(Math.abs(speed));
            robot.backRight.setPower(Math.abs(speed));

            // Keep looping until all 4 wheel encoder paths match the target boundaries
            while (opModeIsActive() &&
                    (robot.frontLeft.isBusy() && robot.backLeft.isBusy() &&
                            robot.frontRight.isBusy() && robot.backRight.isBusy())) {

                telemetry.addData("Target Path", "Running to %d : %d", newFLTarget, newFRTarget);
                telemetry.addData("Current Position", "Actual at %d : %d",
                        robot.frontLeft.getCurrentPosition(), robot.frontRight.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion loops
            robot.frontLeft.setPower(0);
            robot.backLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backRight.setPower(0);

            // Reset back to original configuration defaults
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
}

