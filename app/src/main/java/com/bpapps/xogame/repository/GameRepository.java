package com.bpapps.xogame.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bpapps.xogame.repository.gameengine.XOGameEngine;

import java.util.ArrayList;

public class GameRepository {

    private static GameRepository sInstance = null;

    private XOGameEngine mGameEngine = new XOGameEngine();
    private MutableLiveData<ArrayList<String>> mMockLiveData = new MutableLiveData<>();

    public static GameRepository getInstance() {
        if (sInstance == null) {
            sInstance = new GameRepository();
        }

        return sInstance;
    }

    public GameRepository() {
        mMockLiveData.setValue(mGameEngine.mMockData);
    }

    public LiveData<ArrayList<String>> getMockData() {
        return mMockLiveData;
    }

    public void addMockData(String mockDataToAdd) {
        mMockLiveData.setValue(mGameEngine.addMockData(mockDataToAdd));
    }
}
