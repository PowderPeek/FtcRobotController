

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Vision {

    private Limelight3A limelight;

    public void init(HardwareMap hw) {
        limelight = hw.get(
                Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
    }

    public void start() {
        limelight.start();
    }

    public void stop() {
        limelight.stop();
    }

    public boolean isConnected() {
        return limelight.isConnected();
    }

    public boolean hasTarget() {
        LLResult result = limelight.getLatestResult();
        return result != null && result.isValid();
    }

    public double getTargetX() {
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            return result.getTx();
        }

        return Double.NaN;
    }
}