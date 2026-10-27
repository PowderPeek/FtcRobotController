
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class Intake {

    private final RobotHardware robot;

    // Motor directions
    private static final double FRONT_DIRECTION = -1.0;
    private static final double BACK_DIRECTION = -1.0;

    // Motor powers
    private static final double FRONT_POWER = 0.85;
    private static final double BACK_POWER = 1.00;

    public Intake(RobotHardware robot) {
        this.robot = robot;
    }

    public void update(Gamepad gamepad) {

        double front = 0.0;
        double back = 0.0;

        if (gamepad.y) {

            // Y: Reverse both intakes (eject)
            front = -FRONT_POWER;
            back = -BACK_POWER;

        } else if (gamepad.right_bumper) {

            // RB: Front intake ONLY
            front = FRONT_POWER;
            back = 0.0;

        } else if (gamepad.right_trigger > 0.05) {

            // RT: Both intakes forward
            front = gamepad.right_trigger * FRONT_POWER;
            back = gamepad.right_trigger * BACK_POWER;
        }

        robot.frontIntake.setPower(
                Range.clip(
                        front * FRONT_DIRECTION, -1.0, 1.0
                )
        );

        robot.backIntake.setPower(
                Range.clip(
                        back * BACK_DIRECTION, -1.0, 1.0
                )
        );
    }

    public void stop() {
        robot.frontIntake.setPower(0);
        robot.backIntake.setPower(0);
    }
}