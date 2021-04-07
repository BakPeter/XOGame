package com.bpapps.xogame.repository.gameengine;

public class MoveResult {
    private SquareStatus[][] mBoard;
    private Move mMove;
    private XOGameException mError;
    private SquareStatus mCurrSquareStatus;
    private PlayerType mCurrPlayer;
    private WinningStreak mWinningStreak;
    private GameStatus mGameStatus;

    public MoveResult() {
    }

    public MoveResult(SquareStatus[][] board,
                      Move move,
                      XOGameException error,
                      SquareStatus currSquareStatus,
                      PlayerType currPlayer,
                      WinningStreak winningStreak,
                      GameStatus gameStatus) {
        setBoard(board);
        setMove(move);
        setError(error);
        setCurrSquareStatus(currSquareStatus);
        setCurrPlayer(currPlayer);
        setWinningStreak(winningStreak);
        setGameStatus(gameStatus);
    }

    public MoveResult(Move move) {
        this(null, move, null, null, null, null, null);
    }

    public SquareStatus[][] getBoard() {
        return mBoard;
    }

    public void setBoard(SquareStatus[][] board) {
        this.mBoard = board;
    }

    public Move getMove() {
        return mMove;
    }

    public void setMove(Move move) {
        this.mMove = move;
    }

    public Exception getError() {
        return mError;
    }

    public void setError(XOGameException error) {
        this.mError = error;
    }

    public SquareStatus getCurrSquareStatus() {
        return mCurrSquareStatus;
    }

    public void setCurrSquareStatus(SquareStatus currSquareStatus) {
        this.mCurrSquareStatus = currSquareStatus;
    }

    public boolean isMoveSucceeded() {
        return mError == null;
    }

    public PlayerType getCurrPlayer() {
        return mCurrPlayer;
    }

    public void setCurrPlayer(PlayerType mCurrPlayer) {
        this.mCurrPlayer = mCurrPlayer;
    }

    public WinningStreak getWinningStreak() {
        return mWinningStreak;
    }

    public void setWinningStreak(WinningStreak mWinningStreak) {
        this.mWinningStreak = mWinningStreak;
    }

    public boolean isGameOver() {
        return mGameStatus != GameStatus.NOUN;
    }

    public GameStatus getGameStatus() {
        return mGameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.mGameStatus = gameStatus;
    }
}
