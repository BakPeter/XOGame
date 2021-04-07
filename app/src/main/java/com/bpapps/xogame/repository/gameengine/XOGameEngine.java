package com.bpapps.xogame.repository.gameengine;

import android.util.Log;

import java.util.ArrayList;

public class XOGameEngine {
    private static final String TAG = "TAG.XOGameEngine";

    public static final int ROWS = 3;
    public static final int COLS = 3;

    private SquareStatus[][] mBoard = new SquareStatus[ROWS][COLS];
    private PlayerType mCurrentPlayer = PlayerType.X;
    private GameStatus mGameStatus = GameStatus.NOUN;
    private WinningStreak mWinningStreak = null;

    public XOGameEngine() {
        startNewGame();
    }

    public SquareStatus[][] startNewGame() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                mBoard[i][j] = SquareStatus.EMPTY;
            }
        }

        mCurrentPlayer = PlayerType.X;
        setGameStatus(GameStatus.NOUN);
        setWinningStreak(null);

        printBoardToLog();

        return mBoard;
    }

    public SquareStatus[][] getBoard() {
        return mBoard;
    }


    public MoveResult makeMove(Move move) {
        MoveResult result;
        if (gameOver()) {
            //game already over
            result = getResult(move, new XOGameException("The Game Is Already Over"), SquareStatus.X);
        } else {
            if (put(move)) {
                //square updated
                WinningStreak ws = checkWinningStreak();

                if (ws == null) {
                    if (isBoardFull()) {
                        //draw
                        setGameStatus(GameStatus.DRAW);
                    } else {
                        changePlayers();
                    }
                    result = getResult(move,
                            null,
                            mCurrentPlayer == PlayerType.X ? SquareStatus.X : SquareStatus.O);
                } else {
                    //current player victories
                    setGameStatus(mCurrentPlayer == PlayerType.X ? GameStatus.X : GameStatus.O);
                    result = getResult(move, null, mCurrentPlayer == PlayerType.X ? SquareStatus.X : SquareStatus.O);
                }
            } else {
                //square is not empty
                result = getResult(move, new XOGameException("The Square is not empty"), mCurrentPlayer == PlayerType.X ? SquareStatus.X : SquareStatus.O);
            }
        }

        return result;
    }

    private MoveResult getResult(Move move, XOGameException error, SquareStatus squareStatus) {
        printBoardToLog();
        return new MoveResult(mBoard, move, error, squareStatus, mCurrentPlayer, mWinningStreak, mGameStatus);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (mBoard[i][j] == SquareStatus.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean gameOver() {
        if (getGameStatus() == GameStatus.NOUN) {
            return false;
        } else {
            return true;
        }
    }

    private WinningStreak checkWinningStreak() {
        ArrayList<WinningStreak> streaks = getPossibleWinningStreaks();

        for (WinningStreak streak : streaks) {
            ArrayList<Move> squares = streak.getStreak();
            Move square0 = squares.get(0);
            Move square1 = squares.get(1);
            Move square2 = squares.get(2);

            if (mBoard[square0.getRow()][square0.getCal()] == SquareStatus.EMPTY
                    || mBoard[square1.getRow()][square1.getCal()] == SquareStatus.EMPTY
                    || mBoard[square2.getRow()][square2.getCal()] == SquareStatus.EMPTY) {
                continue;
            }

            if (mBoard[square0.getRow()][square0.getCal()] == mBoard[square1.getRow()][square1.getCal()]
                    && mBoard[square1.getRow()][square1.getCal()] == mBoard[square2.getRow()][square2.getCal()]) {

                setWinningStreak(streak);

                return streak;
            }
        }

        return null;
    }

    private ArrayList<WinningStreak> getPossibleWinningStreaks() {
        ArrayList<WinningStreak> retVal = new ArrayList<>();

        //up-down
        retVal.add(new WinningStreak(new Move(0, 0), new Move(0, 1), new Move(0, 2)));
        retVal.add(new WinningStreak(new Move(1, 0), new Move(1, 1), new Move(1, 2)));
        retVal.add(new WinningStreak(new Move(2, 0), new Move(2, 1), new Move(2, 2)));

        //left-right
        retVal.add(new WinningStreak(new Move(0, 0), new Move(1, 0), new Move(2, 0)));
        retVal.add(new WinningStreak(new Move(0, 1), new Move(1, 1), new Move(2, 1)));
        retVal.add(new WinningStreak(new Move(0, 2), new Move(1, 2), new Move(2, 2)));

        //diagonal
        retVal.add(new WinningStreak(new Move(0, 2), new Move(1, 1), new Move(2, 0)));
        retVal.add(new WinningStreak(new Move(0, 0), new Move(1, 1), new Move(2, 2)));

        return retVal;
    }

    private void setGameStatus(GameStatus gameStatus) {
        this.mGameStatus = gameStatus;
    }

    private GameStatus getGameStatus() {
        return mGameStatus;
    }

    public WinningStreak getWinningStreak() {
        return mWinningStreak;
    }

    private void setWinningStreak(WinningStreak mWinningStreak) {
        this.mWinningStreak = mWinningStreak;
    }

    //===========================================================================
    //===========================================================================
    //Telos Exercise functions
    //===========================================================================
    //Adds squares status to a square in a given move
    //if square is not empty return false else returns true
    private boolean put(Move move) {
        if (mBoard[move.getRow()][move.getCal()] == SquareStatus.EMPTY) {
            if (mCurrentPlayer == PlayerType.X) {
                mBoard[move.getRow()][move.getCal()] = SquareStatus.X;
            } else {
                //mCurrentPlayer == PlayerType.O
                mBoard[move.getRow()][move.getCal()] = SquareStatus.O;
            }

            return true;
        } else {
            return false;
        }
    }

    private void changePlayers() {
        if (mCurrentPlayer == PlayerType.X) {
            mCurrentPlayer = PlayerType.O;
        } else {
            mCurrentPlayer = PlayerType.X;
        }
    }

    public String display() {
        //return string to print on console
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ROWS; i++) {
            builder.append("\n");
            for (int j = 0; j < COLS; j++) {
                switch (mBoard[i][j]) {
                    case X:
                        builder.append(" X ");
                        break;
                    case O:
                        builder.append(" O ");
                        break;
                    case EMPTY:
                        builder.append("  ");
                        break;
                }

                if (j != COLS - 1) {
                    builder.append("|");
                }

            }

            builder.append("\n");
            builder.append("-----------");
        }

        return builder.toString();
    }

    private void printBoardToLog() {
        Log.d(TAG, display());
    }
}
