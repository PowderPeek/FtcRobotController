
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class Shooter {

    private final RobotHardware robot;

    // INCREASED FLYWHEEL POWER
    private static final double MAX_FLYWHEEL_POWER = 1.0;

    private static final double FLYWHEEL_DIRECTION = -1.0;

    public Shooter(RobotHardware robot) {
        this.robot = robot;
    }

    public void update(Gamepad gamepad) {
        // LEFT TRIGGER: Flywheel
        double power = gamepad.left_trigger * MAX_FLYWHEEL_POWER;
        double clippedPower = Range.clip(power * FLYWHEEL_DIRECTION, -1.0, 1.0);

        // UPDATED: Distribute power to both motors simultaneously
        robot.flywheel.setPower(clippedPower);
        robot.flywheel2.setPower(clippedPower);
    }

    public void stop() {
        robot.flywheel.setPower(0);
        robot.flywheel2.setPower(0);
    }
}
