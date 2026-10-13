package org.firstinspires.ftc.teamcode;
public class ShooterDataPoint {
    public double distance; 	// Distance to hive (e.g., in inches or centimeters)
    public double flywheelVel; 	// Target motor velocity (RPM or ticks/sec) public double hoodPosition;	// Servo position for the hood angle (0.0 to 1.0)

    public ShooterDataPoint(double distance, double flywheelVel, double hoodPosition) {
        this.distance = distance;
        this.flywheelVel = flywheelVel;
        this.hoodPosition = hoodPosition; } }


CONTROL TABLE
// Keep this list sorted by distance from closest to furthest
private final ShooterDataPoint[] controlTable = {
        new ShooterDataPoint(24.0, 1200, 0.25),  // 2 feet away
        new ShooterDataPoint(48.0, 1550, 0.40),  // 4 feet away
        new ShooterDataPoint(72.0, 1900, 0.55),  // 6 feet away
        new ShooterDataPoint(96.0, 2300, 0.70)   // 8 feet away

        IMPLEMENT LINEAR INTERPOLATION (LERP)
        Your robot will rarely stand exactly at 48 or 72 inches. When it stands at 60 inches, your code must calculate the exact mid-point between those two entries.

public ShooterDataPoint getShooterSettings(double currentDistance) {
// 1. Handle out-of-bounds (too close)
    if (currentDistance <= controlTable[0].distance) {
        return controlTable[0];
    }

// 2. Handle out-of-bounds (too far)
    if (currentDistance >= controlTable[controlTable.length - 1].distance) { return controlTable[controlTable.length - 1];
    }
// 3. Search for the two bounding points in the table for (int i = 0; i < controlTable.length - 1; i++) {
    ShooterDataPoint low = controlTable[i];
    ShooterDataPoint high = controlTable[i + 1];

    if (currentDistance >= low.distance && currentDistance <= high.distance) { // Calculate interpolation factor (percentage between low and high)
        double t = (currentDistance - low.distance) / (high.distance - low.distance);

// Interpolate flywheel RPM and hood servo position
        double interpolatedVel = low.flywheelVel + t * (high.flywheelVel - low.flywheelVel);
        double interpolatedHood = low.hoodPosition + t * (high.hoodPosition - low.hoodPosition);

        return new ShooterDataPoint(currentDistance, interpolatedVel, interpolatedHood); }

}
}
        return controlTable[0]; // Fallback
        }

