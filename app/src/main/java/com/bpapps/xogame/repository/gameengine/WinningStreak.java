package com.bpapps.xogame.repository.gameengine;

import java.util.ArrayList;

public class WinningStreak {
    private ArrayList<Move> mStreak = new ArrayList<>();

    public WinningStreak(Move move1, Move move2, Move move3) {
        mStreak.add(move1);
        mStreak.add(move2);
        mStreak.add(move3);
    }

    public ArrayList<Move> getStreak() {
        return mStreak;
    }
}
