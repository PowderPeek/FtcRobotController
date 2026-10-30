package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

public class Shooter {

    private final RobotHardware robot;

    // Mutable threshold tracking variable defaulting to 75% power
    private double maxFlywheelPower = 0.75;
    private static final double FLYWHEEL_DIRECTION = 1.0;

    public Shooter(RobotHardware robot) {
        this.robot = robot;
    }

    public void update(Gamepad gamepad) {
        // LEFT TRIGGER: Scales dynamically with your adjusted max speed cap
        double power = gamepad.left_trigger * maxFlywheelPower;
        double clippedPower = Range.clip(power * FLYWHEEL_DIRECTION, -1.0, 1.0);

        // Distribute calculated target vector across both flywheel motors
        robot.flywheel.setPower(clippedPower);
        robot.flywheel2.setPower(clippedPower);
    }

    // Increases speed limit threshold by 0.01 safely capped at a maximum of 1.0
    public void incrementSpeed() {
        maxFlywheelPower = Range.clip(maxFlywheelPower + 0.01, 0.0, 1.0);
    }

    // Decreases speed limit threshold by 0.01 safely floor-capped at a minimum of 0.0
    public void decrementSpeed() {
        maxFlywheelPower = Range.clip(maxFlywheelPower - 0.01, 0.0, 1.0);
    }

    // Getter to display current threshold limit via TeleOp driver hub telemetry
    public double getMaxPowerCap() {
        return maxFlywheelPower;
    }

    public void stop() {
        robot.flywheel.setPower(0);
        robot.flywheel2.setPower(0);
    }
}