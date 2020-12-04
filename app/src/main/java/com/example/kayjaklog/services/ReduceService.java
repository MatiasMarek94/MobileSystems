package com.example.kayjaklog.services;
import java.lang.Math;
public class ReduceService {
    //mean filter
    double[][] coordinates = new double[120][3];                //!this is my "list"
        for (int i = 0; i < 120; i++) {
        coordinates[i][0] = i;
        coordinates[i][1] = i;
    }

    double[][] filtered = new double[120][3];                   //!this is my filtered trajectory "list"
    int length = 120;                                           //!int length = sizeof(coordinate list);
    int slidingWindow = 5;                                      //median calculated over slidingWindow
    double xCounter = 0, yCounter = 0, denominator = 0;

        for (int i = 0; i < length; i++) {
        if (i < (slidingWindow - 1) / 2 + 1) {
            for (int j = 0; j <= i; j++) {
                xCounter = xCounter + coordinates[j][0];        //!replace coordinates[j][0] with x coordinate of list entry
                yCounter = yCounter + coordinates[j][1];        //!replace coordinates[j][1] with y coordinate of list entry
                denominator++;
            }
        } else {
            for (int j = i - ((slidingWindow - 1) / 2); j <= i; j++) {
                xCounter = xCounter + coordinates[j][0];        //!replace coordinates[j][0] with x coordinate of list entry
                yCounter = yCounter + coordinates[j][1];        //!replace coordinates[j][1] with y coordinate of list entry
                denominator++;
            }
        }
        if (length - i < ((slidingWindow - 1) / 2 + 1)) {
            for (int j = i + 1; j < length; j++) {
                xCounter = xCounter + coordinates[j][0];        //!replace coordinates[j][0] with x coordinate of list entry
                yCounter = yCounter + coordinates[j][1];        //!replace coordinates[j][1] with y coordinate of list entry
                denominator++;
            }
        } else {
            for (int j = i + 1; j <= i + ((slidingWindow - 1) / 2); j++) {
                xCounter = xCounter + coordinates[j][0];        //!replace coordinates[j][0] with x coordinate of list entry
                yCounter = yCounter + coordinates[j][1];        //!replace coordinates[j][1] with y coordinate of list entry
                denominator++;
            }
        }
        filtered[i][0] = xCounter / denominator;                //!replace filtered[][] with list of filtered trajectory
        filtered[i][1] = yCounter / denominator;                //!replace filtered[][] with list of filtered trajectory

        xCounter = yCounter = denominator = 0;
    }

    //reduce data points                                        //import java.lang.Math;
    int[] compressed = new int[120];                            //!not necessary, because list will be reduced
    double approxError = 0;
    double errorTreshold = 10;                                  //tuning parameter, max accepted error
    double highestError = 0, xError = 0, yError = 0;
    double x = 0, x_ = 0, y = 0, y_ = 0, t = 0;
    int beginLineIndex = 0, endLineIndex = 2, end = 0;

    //go through whole trajectory
        for(int ii = 0; endLineIndex < length && end == 0; ii++) {
        //extend line as long as error is within threshold
        for (endLineIndex = beginLineIndex + 2; highestError < errorTreshold; endLineIndex++) {
            if (endLineIndex >= length - 1)
                break;
            //calculate max error of current line
            for (int k = (beginLineIndex + 1); k < endLineIndex; k++) {
                x = filtered[k][0];                                            //!replace by coordinate list entry
                y = filtered[k][1];                                            //!replace by coordinate list entry
                //t = ((filtered[k][2] - filtered[beginLineIndex][2]) / (filtered[endLineIndex][2] - filtered[beginLineIndex][2]));
                //!if above one works, use it - otherwise stick with this one
                t = Double.valueOf(k - beginLineIndex) / Double.valueOf(endLineIndex - beginLineIndex);
                x_ = filtered[beginLineIndex][0] + t * (filtered[endLineIndex][0] - filtered[beginLineIndex][0]);
                y_ = filtered[beginLineIndex][1] + t * (filtered[endLineIndex][1] - filtered[beginLineIndex][1]);
                xError = x - x_;
                yError = y - y_;
                approxError = Math.sqrt(Math.pow(xError, 2) + Math.pow(yError, 2));

                if (approxError > highestError)
                    highestError = approxError;
            }
        }

        if(highestError >= errorTreshold)
            endLineIndex -= 2;

        highestError = 0;

        if (endLineIndex + 1 >= length){
            endLineIndex = length - 1;
            end = 1;
        }

        compressed[beginLineIndex] = 1;
        compressed[endLineIndex] = 1;              //!delete unnecessary list entries
        for (int k = beginLineIndex + 1; k < endLineIndex - 1; k++) {
            compressed[k] = 0;
        }

        if(endLineIndex + 2 < length){
            beginLineIndex = endLineIndex;
        }
    }
}
