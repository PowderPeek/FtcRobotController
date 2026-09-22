package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class IfPractice extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        double motorSpeed = gamepad1.left_stick_y;

        if (!gamepad1.a) {
            motorSpeed *= 0.5;
            // motorSpeed = motorSpeed * 0.5
        }

        telemetry.addData("Left Stick Value", leftY);
    }
}


/*
 always runs the FIRST true statement
 cannot set a value like (leftY > 0) in an else statement, else is just if everything else doesn't work.

Combinations: multiple ifs and conditions in order for code to run
AND - &&
ex. if (leftY < 0.5 && leftY > 0) {
OR - ||
ex. if (leftY < 0 || rightY < 0) {
NOT - !
ex. if (!clawClosed) {

*/

/*
Practice:
1. make a turbo button. If the a button is NOT pressed, multiply the motor speed
by 0.5, otherwise use the standard speed.
 */