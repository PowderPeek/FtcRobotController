package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

// 1. Tell the Driver Station this is an Autonomous OpMode
@Autonomous(name = "Autonomous", group = "Autonomous")
public class BioBuzzAutonomous extends LinearOpMode {

    // 2. Declare your working TeleOp subsystem classes
// (Replace these types with your actual class names)
    private MecanumDrive drive;
    private IntakeClass intake;
    private ShooterClass shooter;

    @Override
    public void runOpMode() throws InterruptedException {

// 3. Initialize your existing classes using the hardwareMap
// Pass 'hardwareMap' into your existing class constructors/init methods
        drive = new MecanumDrive(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

// 4. Wait for the driver to press the PLAY button
        waitForStart();

// Make sure the match hasn't been abruptly stopped
        if (opModeIsActive()) {

// --- STEP 1: Move away from the wall toward the basket area ---
            telemetry.addData("Auto Step", "Driving Forward");
            telemetry.update();

// Call your existing drive method (e.g., driveMecanum(forward, strafe, turn))
            drive.driveMecanum(0.5, 0.0, 0.0);
            sleep(1200); // Drive for 1.2 seconds
            drive.driveMecanum(0.0, 0.0, 0.0); // Stop moving

// --- STEP 2: Spin up the shooter to launch your preloaded Pollen ---
            telemetry.addData("Auto Step", "Running Shooter");
            telemetry.update();

            shooter.setShooterPower(0.85); // Adjust power as needed for target basket height
            sleep(1500); // Allow 1.5 seconds to reach full speed

// --- STEP 3: Actuate intake/indexer to feed the Pollen into the shooter ---
            telemetry.addData("Auto Step", "Scoring Pollen");
            telemetry.update();

            intake.setIntakePower(0.6); // Run intake forward to push the game piece
            sleep(1000); // Spend 1 second feeding and scoring

// --- STEP 4: Shutdown all mechanisms ---
            telemetry.addData("Auto Step", "Cleaning Up");
            telemetry.update();

            shooter.setShooterPower(0.0);
            intake.setIntakePower(0.0);

// --- STEP 5: Strafe or drive to park for extra points ---
            drive.driveMecanum(0.0, 0.5, 0.0); // Strafe right toward parking zone
            sleep(1000);
            drive.driveMecanum(0.0, 0.0, 0.0); // Final absolute stop

            telemetry.addData("Status", "Autonomous Complete!");
            telemetry.update();
        }
    }
}