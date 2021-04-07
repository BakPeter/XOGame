package com.bpapps.xogame.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bpapps.xogame.R;
import com.bpapps.xogame.repository.gameengine.GameStatus;
import com.bpapps.xogame.repository.gameengine.Move;
import com.bpapps.xogame.repository.gameengine.MoveResult;
import com.bpapps.xogame.repository.gameengine.PlayerType;
import com.bpapps.xogame.repository.gameengine.SquareStatus;
import com.bpapps.xogame.repository.gameengine.WinningStreak;
import com.bpapps.xogame.repository.gameengine.XOGameEngine;
import com.bpapps.xogame.utils.InjectorUtils;
import com.bpapps.xogame.viewmodel.GameViewModel;
import com.bpapps.xogame.viewmodel.GameViewModelFactory;

import java.util.ArrayList;

public class BoardFragment extends Fragment implements View.OnClickListener {
    private GameViewModel mViewModel;

    private ArrayList<ArrayList<AppCompatTextView>> mBoard = new ArrayList<>(XOGameEngine.ROWS);
    private AppCompatTextView mTvCurrPlayer;
    private AppCompatButton mBtnStartNewGame;

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
        mViewModel = new ViewModelProvider(this, factory).get(GameViewModel.class);

        initViews(view);
        setObservers();
    }

    private void setObservers() {
        mViewModel.getBoard().observe(getViewLifecycleOwner(), new Observer<SquareStatus[][]>() {
            @Override
            public void onChanged(SquareStatus[][] board) {
                updateBoard(board);
            }
        });

        mViewModel.getLastMoveResult().observe(getViewLifecycleOwner(), new Observer<MoveResult>() {
            @Override
            public void onChanged(MoveResult moveResult) {
                if (moveResult != null) {
                    if (moveResult.isGameOver()) {
                        updateBoard(moveResult.getBoard());
                        gameOver(moveResult);
                    } else {
                        if (moveResult.isMoveSucceeded()) {
                            setCurrentPlayer(moveResult.getCurrPlayer());
                            updateBoard(moveResult.getBoard());
                        } else {
                            moveError(moveResult.getError());
                        }
                    }
                } else {
                    moveError(new Exception("move result = null"));
                }
            }
        });
    }

    private void moveError(Exception error) {
        showDialog("Error", error.getMessage());
    }

    private void showDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        AlertDialog dialog = builder.setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        dialog.show();
    }

    private void gameOver(MoveResult moveResult) {
        updateBoard(moveResult.getBoard());

        if (moveResult.getGameStatus() == GameStatus.DRAW) {
            showDialog("Game Over", "DRAW");
        } else if (moveResult.getGameStatus() == GameStatus.O) {
            showDialog("Game Over", "O WON");
            shoWinningStreak(moveResult.getWinningStreak());
        } else {
            showDialog("Game Over", "X  WON");
            shoWinningStreak(moveResult.getWinningStreak());
        }
    }

    private void shoWinningStreak(WinningStreak winningStreak) {
        for (Move move :
                winningStreak.getStreak()) {
            mBoard.get(move.getRow()).get(move.getCal()).setBackgroundColor(Color.RED);
        }
    }

    private void updateBoard(SquareStatus[][] board) {
        for (int i = 0; i < XOGameEngine.ROWS; i++) {
            for (int j = 0; j < XOGameEngine.COLS; j++) {
                updateSquareView(i, j, board[i][j]);
            }
        }
    }

    private void updateSquareView(int row, int col, SquareStatus squareStatus) {
//        String shape = getSquareShape(getPlayerType(squareStatus));
        String shape = getSquareShape(squareStatus);

        mBoard.get(row).get(col).setText(shape);
    }

    private String getSquareShape(SquareStatus squareStatus) {
        switch (squareStatus) {
            case O:
                return "O";
            case X:
                return "X";
            default:
                return "";
        }
    }

    private String getSquareShape(PlayerType currentPlayer) {
        switch (currentPlayer) {
            case O:
                return "O";
            case X:
                return "X";
            default:
                return "";
        }
    }

    private void initViews(@NonNull View view) {
        initBoard(view);

        mTvCurrPlayer = view.findViewById(R.id.tvCurrPlayer);
        setCurrentPlayer(PlayerType.X);

        mBtnStartNewGame = view.findViewById(R.id.btnStartNewGame);
        mBtnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
    }

    private void initBoard(@NonNull View view) {
        ArrayList<AppCompatTextView> row = new ArrayList<>(XOGameEngine.COLS);
        row.add(view.findViewById(R.id.tvSquare00));
        row.add(view.findViewById(R.id.tvSquare01));
        row.add(view.findViewById(R.id.tvSquare02));
        mBoard.add(row);

        row = new ArrayList<>(XOGameEngine.COLS);
        row.add(view.findViewById(R.id.tvSquare10));
        row.add(view.findViewById(R.id.tvSquare11));
        row.add(view.findViewById(R.id.tvSquare12));
        mBoard.add(row);

        row = new ArrayList<>(XOGameEngine.COLS);
        row.add(view.findViewById(R.id.tvSquare20));
        row.add(view.findViewById(R.id.tvSquare21));
        row.add(view.findViewById(R.id.tvSquare22));
        mBoard.add(row);

        for (int i = 0; i < mBoard.size(); i++) {
            for (int j = 0; j < mBoard.get(i).size(); j++) {
                mBoard.get(i).get(j).setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i, j;
        for (i = 0; i < mBoard.size(); i++) {
            for (j = 0; j < mBoard.get(i).size(); j++) {
                if (v.getId() == mBoard.get(i).get(j).getId()) {
//                    String str = " : [" + i + "][" + j + "]";
//                    Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show();
                    mViewModel.makeMove(new Move(i, j));
                }
            }
        }
    }

    private void startNewGame() {
//        Toast.makeText(requireContext(), "START NEW GAME", Toast.LENGTH_SHORT).show();
        setCurrentPlayer(PlayerType.X);

        for (int i = 0; i < XOGameEngine.ROWS; i++) {
            for (int j = 0; j < XOGameEngine.COLS; j++) {
                mBoard.get(i).get(j).setBackgroundColor(Color.WHITE);
            }
        }
        mViewModel.startNewGame();
    }

    private void setCurrentPlayer(PlayerType currentPlayer) {
        String currPlayer = getSquareShape(currentPlayer);
        mTvCurrPlayer.setText(getString(R.string.curr_player) + " " + currPlayer);
    }
}