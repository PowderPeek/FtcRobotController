package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

@Autonomous(name = "BioBuzz Auto", group = "Competition")
public class BioBuzzMecanumAuto extends LinearOpMode {

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

        telemetry.addLine("BIOBUZZ AUTO READY");
        telemetry.addLine("Subsystems fully linked.");
        telemetry.update();

        // Wait for the match to begin (Driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {

            // Ensure the servo is initialized at its starting home position
            robot.toggleServo.setPosition(0.0);

            // STEP 1: Spin up the flywheel shooter
            telemetry.addData("Auto Status", "Spinning up Flywheel");
            telemetry.update();
            autoGamepad.left_trigger = 0.75f; // Fires shooter at 75% power (Adjust as needed)
            shooter.update(autoGamepad);
            sleep(2000);                      // Wait 2 seconds for stability

            // STEP 2: Feed the preloaded Pollen by activating both intakes forward
            telemetry.addData("Auto Status", "Scoring Preloads");
            telemetry.update();
            autoGamepad.right_trigger = 1.0f; // Activates both intakes forward via your trigger logic
            intake.update(autoGamepad);
            sleep(1500);                      // Run intake feed for 1.5 seconds

            // STEP 3: Shut down scoring components cleanly
            autoGamepad.left_trigger = 0.0f;
            autoGamepad.right_trigger = 0.0f;
            shooter.stop();
            intake.stop();

            // STEP 3.5: Move Servo to 90 degrees
            telemetry.addData("Auto Status", "Moving Servo to 90 Degrees");
            telemetry.update();
            robot.toggleServo.setPosition(0.5); // Turn 90 degrees
            sleep(500);                         // Short pause to allow mechanical travel time

            // STEP 4: Drive forward out of the starting zone
            telemetry.addData("Auto Status", "Driving Forward");
            telemetry.update();
            autoGamepad.left_stick_y = -0.4f; // Negative translates to positive forward power in your drive class
            drive.drive(autoGamepad);
            sleep(1000);                      // Move forward for 1 second

            // FIX: Clear the sticks and stop the drive before moving to the next action
            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            // STEP 4.5: Return Servo back to original home position
            telemetry.addData("Auto Status", "Returning Servo to Home Position");
            telemetry.update();
            robot.toggleServo.setPosition(0.0); // Reset servo position
            sleep(300);

            // STEP 5: Strafe sideways to align or park
            telemetry.addData("Auto Status", "Strafing Right to Park");
            telemetry.update();
            autoGamepad.left_stick_x = 0.5f;  // Positive = right, Negative = left
            drive.drive(autoGamepad);
            sleep(1200);                      // Strafe for 1.2 seconds

            // STEP 6: Full stop
            autoGamepad.left_stick_x = 0.0f;  // FIX: Reset virtual joystick state completely
            drive.stop();
            telemetry.addData("Auto Status", "Complete!");
            telemetry.update();
        }
    }
}