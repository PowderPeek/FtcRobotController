package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "BioBuzz Auto - Blue or Red", group = "Competition")
public class BioBuzzAutoBlueRed2 extends LinearOpMode {

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

        telemetry.addLine("BIOBUZZ AUTO READY - OPTIMIZED AT 0.82 SHOOTER");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            robot.toggleServo.setPosition(0.9);

            // ==================== 1. INITIAL SCORING PHASE (4.0s) ====================
            // UPDATED: Set left trigger variable vector to 0.82f to force exact power metrics
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


            // ==================== 2. FIRST OUTBOUND DRIVING PATH (4.7s) ====================

            // STEP 1: Move forward one tile
            autoGamepad.left_stick_y = -0.65f;
            drive.drive(autoGamepad);
            sleep(750);
            drive.stop();

            // STEP 2: Turn 90 degrees counterclockwise
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = -0.5f;
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 3: Move forward two tiles
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.65f;
            drive.drive(autoGamepad);
            sleep(1500);
            drive.stop();

            // STEP 4: Turn 90 degrees counterclockwise
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = -0.5f;
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 5: Move forward one tile WHILE RUNNING INTAKE
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.4f;
            autoGamepad.right_trigger = 1.0f;

            timer.reset();
            while (opModeIsActive() && timer.milliseconds() < 900) {
                drive.drive(autoGamepad);
                intake.update(autoGamepad);
                idle();
            }
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_trigger = 0.0f;
            drive.stop();
            intake.stop();


            // ==================== 3. FIRST RETURN PATH (REVERSED) (4.7s) ====================

            // STEP 6: Drive backward one tile
            autoGamepad.left_stick_y = 0.4f;
            drive.drive(autoGamepad);
            sleep(850);
            drive.stop();

            // STEP 7: Turn 90 degrees clockwise
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = 0.5f;
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 8: Drive backward two tiles
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = 0.65f;
            drive.drive(autoGamepad);
            sleep(1500);
            drive.stop();

            // STEP 9: Turn 90 degrees clockwise
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = 0.5f;
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 10: Drive backward one tile into original position
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = 0.65f;
            drive.drive(autoGamepad);
            sleep(750);
            autoGamepad.left_stick_y = 0.0f;
            drive.stop();


            // ==================== 4. SECOND SCORING PHASE (4.0s) ====================
            // UPDATED: Set left trigger variable vector to 0.82f to match opening launch mechanics
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


            // ==================== 5. SECOND NAVIGATION PATH (5.6s) ====================

            // STEP 11: Move forward one tile
            autoGamepad.left_stick_y = -0.65f;
            drive.drive(autoGamepad);
            sleep(750);
            drive.stop();

            // STEP 12: Turn 90 degrees counterclockwise
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = -0.5f;
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 13: Move forward one tile
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.65f;
            drive.drive(autoGamepad);
            sleep(750);
            drive.stop();

            // STEP 14: Turn 90 degrees clockwise
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = 0.5f;
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 15: Move forward three tiles
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.65f;
            drive.drive(autoGamepad);
            sleep(1800);
            drive.stop();

            // STEP 16: Turn 90 degrees counterclockwise
            autoGamepad.left_stick_y = 0.0f;
            autoGamepad.right_stick_x = -0.5f;
            drive.drive(autoGamepad);
            sleep(800);
            drive.stop();

            // STEP 17: Move forward one tile
            autoGamepad.right_stick_x = 0.0f;
            autoGamepad.left_stick_y = -0.65f;
            drive.drive(autoGamepad);
            sleep(750);

            autoGamepad.left_stick_y = 0.0f;
            drive.stop();

            telemetry.addData("Auto Status", "Route Complete!");
            telemetry.update();
        }
    }
}
