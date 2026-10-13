package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "strykepractice (Blocks to Java)")
public class strykepractice extends LinearOpMode {

    private DcMotor frontleft;
    private DcMotor backright;
    private DcMotor frontright;
    private DcMotor backleft;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        float forward;
        float turn;
        float strafe;

        frontleft = hardwareMap.get(DcMotor.class, "front left");
        backright = hardwareMap.get(DcMotor.class, "back right");
        frontright = hardwareMap.get(DcMotor.class, "front right");
        backleft = hardwareMap.get(DcMotor.class, "back left");

        // Put initialization blocks here.
        backright.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.addData("Time Passed", getRuntime());
                forward = gamepad1.left_stick_y;
                strafe = gamepad1.left_stick_x;
                turn = gamepad1.right_stick_x;
                frontleft.setPower(forward + strafe + turn);
                backleft.setPower(forward - (strafe + turn));
                frontright.setPower(forward - (strafe - turn));
                backright.setPower(forward + (strafe - turn));
                telemetry.update();
            }
        }
    }
}



