package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "BioBuzz Auto 1", group = "Competition")
public class BioBuzzAutoBlueRed1 extends LinearOpMode {

    @Override
    public void runOpMode() {

        RobotHardware robot = new RobotHardware();
        robot.initDrive(hardwareMap);
        robot.initMechanisms(hardwareMap);

        MecanumDrive drive = new MecanumDrive(robot);
        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);

        Gamepad autoGamepad = new Gamepad();
        ElapsedTime timer = new ElapsedTime();

        telemetry.addLine("BIOBUZZ AUTO READY");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            robot.toggleServo.setPosition(0.9);

            // ==================== 1. INITIAL SCORING PHASE (4.0s) ====================
            autoGamepad.left_trigger = 0.82f;

            timer.reset();
            while (opModeIsActive() && timer.milliseconds() < 2500) {
                shooter.update(autoGamepad);
                drive.drive(autoGamepad);
                idle();
            }

            autoGamepad.right_trigger = 1.0f;
            timer.reset();
            while (opModeIsActive() && timer.milliseconds() < 1500) {
                shooter.update(autoGamepad);
                intake.update(autoGamepad);
                idle();
            }

            autoGamepad.left_trigger = 0.0f;
            autoGamepad.right_trigger = 0.0f;
            shooter.stop();
            intake.stop();


            // ==================== 2. CUSTOM ROUTING PATH ====================

            // STEP 1: Turn 90 degrees counterclockwise
            telemetry.addData("Auto Status", "Step 1: Turn 90 Deg CCW");
            telemetry.update();
            autoGamepad.right_stick_x = -0.5f; // Negative power turns Counterclockwise
            drive.drive(autoGamepad);
            sleep(800);                        // Standard time to clear 90 degrees
            drive.stop();

            // STEP 2: Move forward one tile
            telemetry.addData("Auto Status", "Step 2: Move Forward 1 Tile");
            telemetry.update();
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.65f; // Negative power drives forward
            drive.drive(autoGamepad);
            sleep(750);                        // Clear roughly 1 tile at 65% power
            drive.stop();

            // STEP 3: Turn 90 degrees clockwise
            telemetry.addData("Auto Status", "Step 3: Turn 90 Deg CW");
            telemetry.update();
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = 0.5f;  // Positive power turns Clockwise
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 4: Move forward four tiles
            telemetry.addData("Auto Status", "Step 4: Move Forward 4 Tiles");
            telemetry.update();
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.65f; // Negative power drives forward
            drive.drive(autoGamepad);
            sleep(2600);                       // Scaled to cross 4 full tiles quickly
            drive.stop();

            // STEP 5: Turn 90 degrees counterclockwise
            telemetry.addData("Auto Status", "Step 5: Turn 90 Deg CCW");
            telemetry.update();
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = -0.5f; // Negative power turns Counterclockwise
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 6: Move forward one final tile into the Loading Zone
            telemetry.addData("Auto Status", "Step 6: Final Move to Park");
            telemetry.update();
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.65f; // Negative power drives forward
            drive.drive(autoGamepad);
            sleep(750);                        // Secure final parking points inside the zone

            // Full lock down final safety stop
            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            telemetry.addData("Auto Status", "Path Executed Successfully!");
            telemetry.update();
        }
    }
}


