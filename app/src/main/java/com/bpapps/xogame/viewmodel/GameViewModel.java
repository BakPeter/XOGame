package com.bpapps.xogame.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bpapps.xogame.repository.GameRepository;

import java.util.ArrayList;

public class GameViewModel extends ViewModel {
    private GameRepository mRepository;

    public GameViewModel(GameRepository mRepository) {
        this.mRepository = mRepository;
    }

    public LiveData<ArrayList<String>> getMockData() {
        return mRepository.getMockData();
    }

    public void addMockData(String mockDataToAdd) {
        mRepository.addMockData(mockDataToAdd);
    }
}
