package com.bpapps.xogame.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bpapps.xogame.R;
import com.bpapps.xogame.utils.InjectorUtils;
import com.bpapps.xogame.viewmodel.GameViewModel;
import com.bpapps.xogame.viewmodel.GameViewModelFactory;

import java.util.ArrayList;

public class BoardFragment extends Fragment implements View.OnClickListener {
    private GameViewModel viewModel;

    private ArrayList<ArrayList<AppCompatTextView>> board = new ArrayList<>(3);
    private AppCompatTextView tvCurrPlayer;
    private AppCompatButton btnStartNewGame;

    public BoardFragment() {
        // Required empty public constructor
    }

    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GameViewModelFactory factory = InjectorUtils.provideGameViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(GameViewModel.class);

        initBoard(view);

        tvCurrPlayer = view.findViewById(R.id.tvCurrPlayer);

        btnStartNewGame = view.findViewById(R.id.btnStartNewGame);
        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "START NEW GAME", Toast.LENGTH_SHORT).show();
                tvCurrPlayer.setText("Curr Player X");
            }
        });
    }

    private void initBoard(@NonNull View view) {
        ArrayList<AppCompatTextView> row = new ArrayList<>(3);
        row.add(view.findViewById(R.id.tvSquare00));
        row.add(view.findViewById(R.id.tvSquare01));
        row.add(view.findViewById(R.id.tvSquare02));
        board.add(row);

        row = new ArrayList<>(3);
        row.add(view.findViewById(R.id.tvSquare10));
        row.add(view.findViewById(R.id.tvSquare11));
        row.add(view.findViewById(R.id.tvSquare12));
        board.add(row);

        row = new ArrayList<>(3);
        row.add(view.findViewById(R.id.tvSquare20));
        row.add(view.findViewById(R.id.tvSquare21));
        row.add(view.findViewById(R.id.tvSquare22));
        board.add(row);

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                board.get(i).get(j).setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i, j;
        for (i = 0; i < board.size(); i++) {
            for (j = 0; j < board.get(i).size(); j++) {
                if (v.getId() == board.get(i).get(j).getId()) {
                    String str = "[" + i + "][" + j + "]";
//                    Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show();
                    tvCurrPlayer.setText("Curr Player : " + str);
                }
            }
        }
    }
}