package com.bpapps.xogame.utils;

import com.bpapps.xogame.repository.GameRepository;
import com.bpapps.xogame.viewmodel.GameViewModelFactory;

public class InjectorUtils {
    public static GameViewModelFactory provideGameViewModelFactory() {
        GameRepository repository = GameRepository.getInstance();

        return new GameViewModelFactory(repository);
    }
}
