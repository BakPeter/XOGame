package com.bpapps.xogame.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bpapps.xogame.repository.gameengine.Move;
import com.bpapps.xogame.repository.gameengine.MoveResult;
import com.bpapps.xogame.repository.gameengine.SquareStatus;
import com.bpapps.xogame.repository.gameengine.XOGameEngine;

public class GameRepository {

    private static GameRepository sInstance = null;

    private XOGameEngine mGameEngine;
    private MutableLiveData<SquareStatus[][]> mBoard;
    private MutableLiveData<MoveResult> mLastMoveResult = new MutableLiveData<>();

    public static GameRepository getInstance() {
        if (sInstance == null) {
            sInstance = new GameRepository();
        }

        return sInstance;
    }

    private GameRepository() {
        mGameEngine = new XOGameEngine();
        mBoard = new MutableLiveData<>(mGameEngine.getBoard());
    }

    public LiveData<SquareStatus[][]> getBoard() {
        return mBoard;
    }

    public void startNewGame() {
        mBoard.setValue(mGameEngine.startNewGame());
    }

    public LiveData<MoveResult> getLastMoveResult() {
        return mLastMoveResult;
    }

    public void makeMove(Move move) {
        MoveResult result = mGameEngine.makeMove(move);
        mLastMoveResult.setValue(result);
    }
}
