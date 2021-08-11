package org.tensorflow.lite.examples.detection.tflite;

import android.graphics.Color;

public class Marker {
    public float x;
    public float y;
    public int timer;
    public Marker buddy;
    public Color color;
    public Marker(float xLoc, float yLoc){
        x = xLoc;
        y = yLoc;
        timer = 0;
        buddy = null;
    }
}
