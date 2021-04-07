package com.bpapps.xogame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bpapps.xogame.repository.GameRepository;
import com.bpapps.xogame.repository.gameengine.Move;
import com.bpapps.xogame.repository.gameengine.MoveResult;
import com.bpapps.xogame.repository.gameengine.SquareStatus;

public class GameViewModel extends ViewModel {
    private GameRepository mRepository;

    public GameViewModel(GameRepository mRepository) {
        this.mRepository = mRepository;
    }

    public LiveData<SquareStatus[][]> getBoard() {
        return mRepository.getBoard();
    }

    public void startNewGame() {
        mRepository.startNewGame();
    }

    public LiveData<MoveResult> getLastMoveResult() {
        return mRepository.getLastMoveResult();
    }

    public void makeMove(Move move) {
        mRepository.makeMove(move);
    }
}
