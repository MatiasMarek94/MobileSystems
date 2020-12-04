package com.example.kayjaklog.services;

public class FilterService implements IFilterService {
    private double yCounter;
    private double xCounter;
    private int denominator;
    private int slidingWindow;
    private int length;
    private double[][] coordinates;


    public FilterService(double[][] coordinate, int slidingWindow ) {
        this.yCounter = 0.0;
        this.xCounter = 0.0;
        this.denominator = 0;
        this.length = coordinate.length;
        this.coordinates = coordinate;

        if (slidingWindow >=0) {
            this.slidingWindow = slidingWindow;
        }
        else
            this.slidingWindow = 0;
    }

    public double[][] applyMeanFilter(){
        double[][] filtered = new double[0][];
        for (int i = 0; i < length; i++) {
            determineSlidingWindowThenFilter(i,slidingWindow);
            filtered[i][0] = xCounter / denominator;                //!replace filtered[][] with list of filtered trajectory
            filtered[i][1] = yCounter / denominator;                //!replace filtered[][] with list of filtered trajectory
            xCounter = yCounter = denominator = 0;
        }
        return filtered;
    }

    private void determineSlidingWindowThenFilter(int index, int slidingWindow){
        if (itIsBeginning(index,slidingWindow)){
            calcFirstSlidingWindow(index);
        }
        else {
            calcLeftWindow(index);
        }

        if (itIsTheEnd(index,slidingWindow)){
            calcLastSlidingWindow(index);
        }
        else {
            calcRightWindow(index);
        }

    }

    private boolean itIsBeginning(int index, int slidingWindow){
        if (index < (slidingWindow - 1) / 2+1) {
            return true;
        }
        return false;
    }

    private void calcFirstSlidingWindow(int index){
        for (int j = 0; j <= index; j++) {
            filter(j);
        }
    }

    private void calcLeftWindow(int index){
        for (int j = index + 1; j <= index + ((slidingWindow - 1) / 2); j++){
            filter(j);
        }
    }

    private void calcRightWindow(int index){
        for (int j = index + 1; j <= index + ((slidingWindow - 1) / 2); j++) {
            filter(j);
        }
    }

    private void calcLastSlidingWindow(int index){
        for (int j = index + 1; j < length; j++) {
            filter(index);
        }
    }

    private boolean itIsTheEnd(int index, int slidingWindow){
        if (length - index < ((slidingWindow - 1) / 2 + 1)){
            return true;
        }
        return false;
    }

    private void filter(int index){
        this.xCounter = xCounter + coordinates[index][0];
        this.yCounter = yCounter + coordinates[index][1];
        this.denominator++;
    }

    public void setSlidingWindow(int slidingWindow) {
        if (slidingWindow >= 0){
            this.slidingWindow = slidingWindow;
        }
        return;
    }

    public void setCoordinates(double[][] coordinates) {
        this.coordinates = coordinates;
    }


}
