package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

@Autonomous(name = "BioBuzz Auto - RED", group = "Red")
public class BioBuzzAutoRed extends LinearOpMode {

    @Override
    public void runOpMode() {

        // 1. Initialize hardware configuration matching your exact config strings
        RobotHardware robot = new RobotHardware();
        robot.initDrive(hardwareMap);
        robot.initMechanisms(hardwareMap);

        // 2. Wrap the hardware in your modular subsystem classes
        MecanumDrive drive = new MecanumDrive(robot);
        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);

        // Create virtual gamepad instances to trigger your TeleOp methods automatically
        Gamepad autoGamepad = new Gamepad();

        telemetry.addLine("RED ALLIANCE AUTO READY");
        telemetry.addLine("Dual Flywheels (flywheel + flywheel2) fully linked.");
        telemetry.update();

        // Wait for the match to begin (Driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {

            // Ensure the servo is initialized at its starting home position
            robot.toggleServo.setPosition(0.0);

            // ==================== OUTBOUND PATH ====================

            // STEP 1: Drive forward out of the starting zone
            telemetry.addData("Auto Status", "Driving Forward");
            telemetry.update();
            autoGamepad.left_stick_y = -0.4f; // Negative translates to forward power
            drive.drive(autoGamepad);
            sleep(1000);                      // Move forward for 1 second

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            // STEP 2: Strafe LEFT across 4 full tiles (Mirrored for Red)
            telemetry.addData("Auto Status", "Strafing Across 4 Tiles Left");
            telemetry.update();
            autoGamepad.left_stick_x = -0.5f; // Negative = left
            drive.drive(autoGamepad);
            sleep(4800);                      // Clears roughly 4 tiles

            autoGamepad.left_stick_x = 0.0f;
            drive.stop();

            // STEP 3: Move forward one square after strafing
            telemetry.addData("Auto Status", "Moving Forward One Square");
            telemetry.update();
            autoGamepad.left_stick_y = -0.4f; // Push stick forward
            drive.drive(autoGamepad);
            sleep(1000);                      // Move forward for 1 second

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            // STEP 4: Turn 90 degrees clockwise to align with target (Mirrored for Red)
            telemetry.addData("Auto Status", "Turning 90 Deg Clockwise");
            telemetry.update();
            autoGamepad.right_stick_x = 0.5f; // Positive turns clockwise
            drive.drive(autoGamepad);
            sleep(800);

            autoGamepad.right_stick_x = 0.0f;
            drive.stop();

            // ==================== SCORING PHASE ====================

            // STEP 5: Spin up the dual flywheel shooter
            telemetry.addData("Auto Status", "Spinning up Dual Flywheels");
            telemetry.update();
            autoGamepad.left_trigger = 0.75f; // Fires shooter at 75% power
            shooter.update(autoGamepad);
            sleep(2000);                      // Wait 2 seconds for motors to stabilize

            // STEP 6: Feed the preloaded Pollen by activating both intakes forward
            telemetry.addData("Auto Status", "Firing Preloads!");
            telemetry.update();
            autoGamepad.right_trigger = 1.0f; // Activates both intakes forward
            intake.update(autoGamepad);
            sleep(1500);                      // Run intake feed for 1.5 seconds

            // Shut down scoring and launcher components cleanly
            autoGamepad.left_trigger = 0.0f;
            autoGamepad.right_trigger = 0.0f;
            shooter.stop();
            intake.stop();

            // ==================== RETURN PATH (REVERSE ORDER) ====================

            // STEP 7: Turn 90 degrees counterclockwise to fix orientation (Mirrored for Red)
            telemetry.addData("Auto Status", "Return Path: Turning 90 Deg Counterclockwise");
            telemetry.update();
            autoGamepad.right_stick_x = -0.5f;  // Negative turns counterclockwise
            drive.drive(autoGamepad);
            sleep(800);

            autoGamepad.right_stick_x = 0.0f;
            drive.stop();

            // STEP 8: Drive backward one square
            telemetry.addData("Auto Status", "Return Path: Driving Backward One Square");
            telemetry.update();
            autoGamepad.left_stick_y = 0.4f;  // Positive translates to backward power
            drive.drive(autoGamepad);
            sleep(1000);

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            // STEP 9: Strafe RIGHT across 4 full tiles (Mirrored for Red)
            telemetry.addData("Auto Status", "Return Path: Strafing Right Across 4 Tiles");
            telemetry.update();
            autoGamepad.left_stick_x = 0.5f; // Positive = right
            drive.drive(autoGamepad);
            sleep(4800);

            autoGamepad.left_stick_x = 0.0f;
            drive.stop();

            // STEP 10: Drive backward into original home tile position
            telemetry.addData("Auto Status", "Return Path: Parking at Start Position");
            telemetry.update();
            autoGamepad.left_stick_y = 0.4f;  // Positive translates to backward power
            drive.drive(autoGamepad);
            sleep(1000);

            // Clear all virtual joysticks for complete final safety
            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            telemetry.addData("Auto Status", "Complete! Safely Docked.");
            telemetry.update();
        }
    }
}
