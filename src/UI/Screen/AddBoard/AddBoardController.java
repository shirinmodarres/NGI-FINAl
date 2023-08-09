package UI.Screen.AddBoard;

import Core.Manager.BoardManager;

public class AddBoardController {
    private BoardManager boardManager;

    public AddBoardController(BoardManager boardManager){
        this.boardManager=boardManager;

    }
    public void addBoard(String title){
        boardManager.addBoard(title);
    }
}
