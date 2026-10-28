
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BioBuzz TeleOp", group = "Competition")
public class BioBuzzTeleOp extends LinearOpMode {

    // Toggle Tracking Variables
    private boolean servoExtended = false;
    private boolean lastButtonState = false;

    @Override
    public void runOpMode() {

        // Initialize hardware
        RobotHardware robot = new RobotHardware();

        // Control Hub: four drivetrain motors
        robot.initDrive(hardwareMap);

        // Expansion Hub: flywheel and intakes
        robot.initMechanisms(hardwareMap);

        // Initialize subsystems
        MecanumDrive drive = new MecanumDrive(robot);
        Intake intake = new Intake(robot);
        Shooter shooter = new Shooter(robot);

        telemetry.addLine("BIOBUZZ TELEOP READY");
        telemetry.addLine("1 Gamepad | 8 Motors"); // UPDATED: Reflects the addition of flywheel2
        telemetry.update();

        waitForStart();

        try {
            while (opModeIsActive()) {

                // DRIVETRAIN
                drive.drive(gamepad1);

                // FRONT + BACK INTAKES
                intake.update(gamepad1);

                // FLYWHEEL
                shooter.update(gamepad1);

                // SERVO TOGGLE LOGIC (using Gamepad 1 Button B)
                if (gamepad1.b && !lastButtonState) {
                    servoExtended = !servoExtended; // Swap true/false state
                    if (servoExtended) {
                        robot.toggleServo.setPosition(0.5); // ~90 Degrees
                    } else {
                        robot.toggleServo.setPosition(0.0); // Home position
                    }
                }
                lastButtonState = gamepad1.b; // Remember button state for next loop iteration

                // TELEMETRY
                telemetry.addLine("=== BIOBUZZ ===");

                telemetry.addData(
                        "Forward",
                        -gamepad1.left_stick_y
                );

                telemetry.addData(
                        "Turn",
                        gamepad1.right_stick_x
                );

                telemetry.addData(
                        "Strafe",
                        gamepad1.left_stick_x
                );

                telemetry.addData(
                        "Flywheel Power",
                        robot.flywheel.getPower()
                );

                // ADDED: Real-time telemetry monitoring for your secondary shooter motor
                telemetry.addData(
                        "Flywheel 2 Power",
                        robot.flywheel2.getPower()
                );

                telemetry.addData(
                        "Front Intake",
                        robot.frontIntake.getPower()
                );

                telemetry.addData(
                        "Back Intake",
                        robot.backIntake.getPower()
                );

                telemetry.addData(
                        "Eject",
                        gamepad1.y ? "ON" : "OFF"
                );

                telemetry.update();
                idle();
            }
        } finally {

            // Stop everything when TeleOp ends
            drive.stop();
            intake.stop();
            shooter.stop();
        }
    }
}