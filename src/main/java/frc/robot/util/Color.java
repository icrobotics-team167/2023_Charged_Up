package frc.robot.util; // Blue Green Red Yellow

public final class Color {

    // Store all colors as RGB values
    public final double red;
    public final double green;
    public final double blue;

    // Construct a color from RGB
    private Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    // Construct a color from RGB
    public static Color fromRGB(double red, double green, double blue) {
        return new Color(red, green, blue);
    }

    // Construct a color from HSV
    public static Color fromHSV(double hue, double saturation, double value) {
        // java.awt.Color color = new java.awt.Color(java.awt.Color.HSBtoRGB((float)hue, (float)saturation, (float)value));
        // return new Color(color.getRed(), color.getGreen(), color.getBlue());
        return null;
    }

    // Construct a color from CMYK
    public static Color fromCMYK(double cyan, double magenta, double yellow, double black) {
        return new Color(255 * (1 - cyan) * (1 - black), 255 * (1 - magenta) * (1 - black), 255 * (1 - yellow) * (1 - black));
    }

    // Find the "distance" between two colors i.e. how similar they are
    private double distanceFrom(Color other) {
        return Math.sqrt(this.distanceFrom(other));
    }

    // Find the "distance" without taking the square root to avoid more expensive computation
    private double distanceSquaredFrom(Color other) {
        return Math.pow(red - other.red, 2) + Math.pow(green - other.green, 2) + Math.pow(blue - other.blue, 2);
    }

    // Check if two colors are identical to each other
    @Override
    public boolean equals(Object other) {
        if (other instanceof Color) {
            if (this.distanceSquaredFrom((Color) other) == 0) {
                return true;
            }
        }
        return false;
    }

    // Figure out which of the four control panel colors best matches this color
    public Colors getBestColorMatch() {
        double blueDistance = this.distanceSquaredFrom(Colors.BLUE.color);
        double greenDistance = this.distanceSquaredFrom(Colors.GREEN.color);
        double redDistance = this.distanceSquaredFrom(Colors.RED.color);
        double yellowDistance = this.distanceFrom(Colors.YELLOW.color);
        double minimumDistance = Math.min(Math.min(blueDistance, greenDistance), Math.min(redDistance, yellowDistance));
        if (minimumDistance == blueDistance) {
            return Colors.BLUE;
        } else if (minimumDistance == greenDistance) {
            return Colors.GREEN;
        } else if (minimumDistance == redDistance) {
            return Colors.RED;
        } else {
            return Colors.YELLOW;
        }
    }

    public static int getClosest(Colors c1, Colors c2) {
        switch (c1) {
            case BLUE:
                if (c2 == Colors.GREEN) {
                    return -1;
                }
                return 1;
            case RED:
                if (c2 == Colors.YELLOW) {
                    return -1;
                }
                return 1;
            case GREEN:
                if (c2 == Colors.RED) {
                    return -1;
                }
                return 1;
            case YELLOW:
                if (c2 == Colors.BLUE) {
                    return -1;
                }
                return 1;
            default: return 0;
        }
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }
}
