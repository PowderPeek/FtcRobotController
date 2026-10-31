package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime; // Added for active clock counting

@Autonomous(name = "BioBuzz Auto - Blue (Working Loops)", group = "Competition")
public class UpdatedOriginalAutoBlue extends LinearOpMode {

    @Override
    public void runOpMode() {

        RobotHardware robot = new RobotHardware();
        robot.initDrive(hardwareMap);
        robot.initMechanisms(hardwareMap);

        MecanumDrive drive = new MecanumDrive(robot);
        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);

        Gamepad autoGamepad = new Gamepad();
        ElapsedTime timer = new ElapsedTime(); // Added loop timer object

        telemetry.addLine("BIOBUZZ AUTO READY");
        telemetry.addLine("Dual Flywheels (flywheel + flywheel2) fully linked.");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // FIXED: Set to your standard 0.9 TeleOp home to prevent mechanical binding
            robot.toggleServo.setPosition(0.9);

            // ==================== OUTBOUND PATH ====================

            // STEP 1: Drive forward out of the starting zone
            telemetry.addData("Auto Status", "Driving Forward");
            telemetry.update();
            autoGamepad.left_stick_y = -0.4f;
            drive.drive(autoGamepad);
            sleep(1000);

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            // STEP 2: Strafe sideways across 4 full tiles
            telemetry.addData("Auto Status", "Strafing Across 4 Tiles");
            telemetry.update();
            autoGamepad.left_stick_x = 0.5f;
            drive.drive(autoGamepad);
            sleep(4800);

            autoGamepad.left_stick_x = 0.0f;
            drive.stop();

            // STEP 3: Move forward one square after strafing
            telemetry.addData("Auto Status", "Moving Forward One Square");
            telemetry.update();
            autoGamepad.left_stick_y = -0.4f;
            drive.drive(autoGamepad);
            sleep(1000);

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            // STEP 4: Turn 90 degrees counterclockwise to align with target
            telemetry.addData("Auto Status", "Turning 90 Deg Counterclockwise");
            telemetry.update();
            autoGamepad.right_stick_x = -0.5f;
            drive.drive(autoGamepad);
            sleep(800);

            autoGamepad.right_stick_x = 0.0f;
            drive.stop();

            // ==================== FIXED SCORING PHASE ====================

            // FIXED STEP 5: Spin up the dual flywheel shooter via an ACTIVE while loop
            telemetry.addData("Auto Status", "Spinning up Dual Flywheels");
            telemetry.update();
            autoGamepad.left_trigger = 0.82f;

            timer.reset();
            while (opModeIsActive() && timer.milliseconds() < 3000) {
                shooter.update(autoGamepad);
                drive.drive(autoGamepad); // Hold chassis locked still during spin-up
                idle();
            }

            // FIXED STEP 6: Run BOTH shooter and intake simultaneously so the motor doesn't drop power
            telemetry.addData("Auto Status", "Firing Preloads!");
            telemetry.update();
            autoGamepad.right_trigger = 1.0f;

            timer.reset();
            while (opModeIsActive() && timer.milliseconds() < 2000) {
                shooter.update(autoGamepad);
                intake.update(autoGamepad);
                idle();
            }

            // Clean shutdown cutoffs
            autoGamepad.left_trigger = 0.0f;
            autoGamepad.right_trigger = 0.0f;
            shooter.stop();
            intake.stop();

            // ==================== RETURN PATH (REVERSE ORDER) ====================

            // STEP 7: Turn 90 degrees clockwise to fix alignment orientation
            telemetry.addData("Auto Status", "Return Path: Turning 90 Deg Clockwise");
            telemetry.update();
            autoGamepad.right_stick_x = 0.5f;
            drive.drive(autoGamepad);
            sleep(800);

            autoGamepad.right_stick_x = 0.0f;
            drive.stop();

            // STEP 8: Drive backward one square
            telemetry.addData("Auto Status", "Return Path: Driving Backward One Square");
            telemetry.update();
            autoGamepad.left_stick_y = 0.4f;
            drive.drive(autoGamepad);
            sleep(1000);

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            // STEP 9: Strafe LEFT across 4 full tiles
            telemetry.addData("Auto Status", "Return Path: Strafing Left Across 4 Tiles");
            telemetry.update();
            autoGamepad.left_stick_x = -0.5f;
            drive.drive(autoGamepad);
            sleep(4800);

            autoGamepad.left_stick_x = 0.0f;
            drive.stop();

            // STEP 10: Drive backward into original home tile position
            telemetry.addData("Auto Status", "Return Path: Parking at Start Position");
            telemetry.update();
            autoGamepad.left_stick_y = 0.4f;
            drive.drive(autoGamepad);
            sleep(1500);

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            telemetry.addData("Auto Status", "Complete! Safely Docked.");
            telemetry.update();
        }
    }
}

