package org.tensorflow.lite.examples.detection.tflite;

public class LineInfo {
    float x1;
    float x2;
    float y1;
    float y2;

    public LineInfo(float xx1, float yy1, float xx2, float yy2){
        x1 = xx1;
        x2 = xx2;
        y1 = yy1;
        y2 = yy2;
    }
}
