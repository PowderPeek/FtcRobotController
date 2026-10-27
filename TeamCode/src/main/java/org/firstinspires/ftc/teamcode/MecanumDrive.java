
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class MecanumDrive {

    private final RobotHardware robot;

    private static final double DRIVE_SPEED = 1.0;

    public MecanumDrive(RobotHardware robot) {
        this.robot = robot;
    }

    public void drive(Gamepad gamepad) {

        // DRIVER PREFERENCES
        double forward = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;
        double strafe = gamepad.left_stick_x;

        // Joystick deadzones
        if (Math.abs(forward) < 0.05) forward = 0;
        if (Math.abs(turn) < 0.05) turn = 0;
        if (Math.abs(strafe) < 0.05) strafe = 0;

        // Mecanum normalization
        double denominator = Math.max(
                Math.abs(forward)
                        + Math.abs(strafe)
                        + Math.abs(turn),
                1.0
        );

        double fl = (forward + strafe + turn)
                / denominator;

        double bl = (forward - strafe + turn)
                / denominator;

        double fr = (forward - strafe - turn)
                / denominator;

        double br = (forward + strafe - turn)
                / denominator;

        robot.frontLeft.setPower(fl * DRIVE_SPEED);
        robot.backLeft.setPower(bl * DRIVE_SPEED);
        robot.frontRight.setPower(fr * DRIVE_SPEED);
        robot.backRight.setPower(br * DRIVE_SPEED);
    }

    public void stop() {
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backRight.setPower(0);
    }
}
