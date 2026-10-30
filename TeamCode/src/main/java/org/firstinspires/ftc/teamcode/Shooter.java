
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class Shooter {

    private final RobotHardware robot;

    // CHANGED: Removed static constant, added mutable variable defaulting to 0.75 (75%)
    private double maxFlywheelPower = 0.75;
    private static final double FLYWHEEL_DIRECTION = 1.0;

    public Shooter(RobotHardware robot) {
        this.robot = robot;
    }

    public void update(Gamepad gamepad) {
        // LEFT TRIGGER: Scales dynamically with your adjusted max speed
        double power = gamepad.left_trigger * maxFlywheelPower;
        double clippedPower = Range.clip(power * FLYWHEEL_DIRECTION, -1.0, 1.0);

        robot.flywheel.setPower(clippedPower);
        robot.flywheel2.setPower(clippedPower);
    }

    // ADDED: Method to increase speed limit by 0.01 safely capped at 1.0
    public void incrementSpeed() {
        maxFlywheelPower = Range.clip(maxFlywheelPower + 0.01, 0.0, 1.0);
    }

    // ADDED: Method to decrease speed limit by 0.01 safely floor-capped at 0.0
    public void decrementSpeed() {
        maxFlywheelPower = Range.clip(maxFlywheelPower - 0.01, 0.0, 1.0);
    }

    // ADDED: Getter to display current threshold limit on the driver hub screen
    public double getMaxPowerCap() {
        return maxFlywheelPower;
    }

    public void stop() {
        robot.flywheel.setPower(0);
        robot.flywheel2.setPower(0);
    }
}