package com.bpapps.xogame.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bpapps.xogame.repository.GameRepository;
import com.bpapps.xogame.viewmodel.GameViewModel;

public class GameViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private GameRepository mRepository;

    public GameViewModelFactory(GameRepository repository) {
        super();
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GameViewModel(mRepository);
    }
}
