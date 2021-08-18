package org.tensorflow.lite.examples.detection.tflite;

public class LineInfo {
    public float x1;
    public float x2;
    public float y1;
    public float y2;

    public LineInfo(float xx1, float yy1, float xx2, float yy2){
        x1 = xx1;
        x2 = xx2;
        y1 = yy1;
        y2 = yy2;
    }
}
