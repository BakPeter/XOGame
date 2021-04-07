package com.bpapps.xogame.repository.gameengine;

public class Move {
    private int mRow;
    private int mCal;

    public Move(int row, int cal) {
        setRow(row);
        setCal(cal);
    }

    public int getRow() {
        return mRow;
    }

    private void setRow(int mRow) {
        this.mRow = mRow;
    }

    public int getCal() {
        return mCal;
    }

    private void setCal(int mCal) {
        this.mCal = mCal;
    }
}
