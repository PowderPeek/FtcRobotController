package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz TeleOp", group = "Competition")
public class BioBuzzTeleOp extends LinearOpMode {

    // Toggle Tracking Variables
    private boolean servoExtended = false;
    private boolean lastButtonState = false;

    // Edge-detection state trackers for gamepad2 dpad inputs
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;

    @Override
    public void runOpMode() {

        // Initialize hardware mapping container
        RobotHardware robot = new RobotHardware();

        // Control Hub: four drivetrain motors
        robot.initDrive(hardwareMap);

        // Expansion Hub: flywheel, intakes, and toggle servo
        robot.initMechanisms(hardwareMap);

        // Initialize object-oriented subsystems
        MecanumDrive drive = new MecanumDrive(robot);
        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);

        telemetry.addLine("BIOBUZZ TELEOP READY");
        telemetry.addLine("2 Gamepads Active | 8 Motors");
        telemetry.update();

        waitForStart();

        try {
            while (opModeIsActive()) {

                // --- GAMEPAD 1: DRIVER CONTROLS ---

                // DRIVETRAIN
                drive.drive(gamepad1);

                // FRONT + BACK INTAKES
                intake.update(gamepad1);

                // FLYWHEEL TRIGGER
                shooter.update(gamepad1);

                // SERVO TOGGLE LOGIC (using Gamepad 1 Button B)
                if (gamepad1.b && !lastButtonState) {
                    servoExtended = !servoExtended; // Swap true/false state
                    if (servoExtended) {
                        robot.toggleServo.setPosition(0.6); // ~90 Degrees / Extended
                    } else {
                        robot.toggleServo.setPosition(0.9); // Home position
                    }
                }
                lastButtonState = gamepad1.b; // Remember button state for next loop iteration


                // --- GAMEPAD 2: CO-DRIVER TUNING CONTROLS ---

                // Shooter Speed Tuning Logic via Gamepad 2 Dpad Up
                if (gamepad2.dpad_up && !lastDpadUp) {
                    shooter.incrementSpeed();
                }
                lastDpadUp = gamepad2.dpad_up;

                // Shooter Speed Tuning Logic via Gamepad 2 Dpad Down
                if (gamepad2.dpad_down && !lastDpadDown) {
                    shooter.decrementSpeed();
                }
                lastDpadDown = gamepad2.dpad_down;


                // --- TELEMETRY ---
                telemetry.addLine("=== BIOBUZZ ===");

                telemetry.addData("Forward", -gamepad1.left_stick_y);
                telemetry.addData("Turn", gamepad1.right_stick_x);
                telemetry.addData("Strafe", gamepad1.left_stick_x);

                // Tuning Feedback
                telemetry.addData("Shooter Max Speed Cap (G2)", "%.3f", shooter.getMaxPowerCap());
                telemetry.addData("Flywheel Power", robot.flywheel.getPower());
                telemetry.addData("Flywheel 2 Power", robot.flywheel2.getPower());

                telemetry.addData("Front Intake", robot.frontIntake.getPower());
                telemetry.addData("Back Intake", robot.backIntake.getPower());
                telemetry.addData("Eject", gamepad1.y ? "ON" : "OFF");

                telemetry.update();
                idle();
            }
        } finally {
            // Stop everything safely when TeleOp ends or exception hits
            drive.stop();
            intake.stop();
            shooter.stop();
        }
    }
}
