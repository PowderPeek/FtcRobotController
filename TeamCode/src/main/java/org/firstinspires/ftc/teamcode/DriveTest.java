package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz Drive Test", group = "Testing")
public class DriveTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        RobotHardware robot = new RobotHardware();

        robot.initDrive(hardwareMap);

        MecanumDrive drive = new MecanumDrive(robot);

        telemetry.addLine("DRIVE TEST NOT READY");
        telemetry.addLine("Expansion Hub NOT required");
        telemetry.update();

        waitForStart();

        try {
            while (opModeIsActive()) {

                drive.drive(gamepad1);

                telemetry.addData(
                        "Front Left", robot.frontLeft.getPower());
                telemetry.addData(
                        "Front Right", robot.frontRight.getPower());
                telemetry.addData(
                        "Back Left", robot.backLeft.getPower());
                telemetry.addData(
                        "Back Right", robot.backRight.getPower());

                telemetry.update();
                idle();
            }
        } finally {
            drive.stop();
        }
    }
}
