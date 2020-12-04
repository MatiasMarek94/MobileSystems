//package com.example.kayjaklog.services;
//import java.lang.Math;
//public class ReduceService {
//
//    //reduce data points                                        //import java.lang.Math;
//    private int[] compressed = new int[120];                            //!not necessary, because list will be reduced
//    private double approxError = 0;
//    private double errorTreshold = 10;                                  //tuning parameter, max accepted error
//    private double highestError = 0, xError = 0, yError = 0;
//    private double x = 0, x_ = 0, y = 0, y_ = 0, t = 0;
//    private int beginLineIndex = 0, endLineIndex = 2, end = 0;
//    double[][] filtered = new double[120][3];
//
//    public ReduceService(double[][] coordinate) {
//        this.compressed = new int[coordinate.length];
//        this.approxError = 0.0;
//        this.errorTreshold = 10;
//        this.highestError = 0.0;
//        this.xError = 0.0;
//        this.yError = 0.0;
//        this.x = 0;
//        this.x_ = 0;
//        this.y = 0;
//        this.y_ = 0;
//        this.t = 0;
//        this.beginLineIndex = 0;
//        this.endLineIndex = 2;
//        this.end = 0;
//    }
//
//    public
//
//    //go through whole trajectory
//        for(int i = 0; endLineIndex < length && end == 0; i++) {
//        //extend line as long as error is within threshold
//        for (endLineIndex = beginLineIndex + 2; highestError < errorTreshold; endLineIndex++) {
//            if (endLineIndex >= length - 1)
//                break;
//            //calculate max error of current line
//            for (int k = (beginLineIndex + 1); k < endLineIndex; k++) {
//                x = filtered[k][0];                                            //!replace by coordinate list entry
//                y = filtered[k][1];                                            //!replace by coordinate list entry
//                //t = ((filtered[k][2] - filtered[beginLineIndex][2]) / (filtered[endLineIndex][2] - filtered[beginLineIndex][2]));
//                //!if above one works, use it - otherwise stick with this one
//                t = Double.valueOf(k - beginLineIndex) / Double.valueOf(endLineIndex - beginLineIndex);
//                x_ = filtered[beginLineIndex][0] + t * (filtered[endLineIndex][0] - filtered[beginLineIndex][0]);
//                y_ = filtered[beginLineIndex][1] + t * (filtered[endLineIndex][1] - filtered[beginLineIndex][1]);
//                xError = x - x_;
//                yError = y - y_;
//                approxError = Math.sqrt(Math.pow(xError, 2) + Math.pow(yError, 2));
//
//                if (approxError > highestError)
//                    highestError = approxError;
//            }
//        }
//
//        if(highestError >= errorTreshold)
//            endLineIndex -= 2;
//
//        highestError = 0;
//
//        if (endLineIndex + 1 >= length){
//            endLineIndex = length - 1;
//            end = 1;
//        }
//
//        compressed[beginLineIndex] = 1;
//        compressed[endLineIndex] = 1;              //!delete unnecessary list entries
//
//        for (int k = beginLineIndex + 1; k < endLineIndex - 1; k++) {
//            compressed[k] = 0;
//        }
//
//        if(endLineIndex + 2 < length){
//            beginLineIndex = endLineIndex;
//        }
//    }
//}
