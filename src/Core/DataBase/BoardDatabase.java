package Core.DataBase;

import Core.Model.Board;

import java.util.ArrayList;

public class BoardDatabase {
    private ArrayList<Board> boards;

    public BoardDatabase() {
        this.boards = new ArrayList<>();
    }

    public void save(Board board) {
        boards.add(board);
    }

    public ArrayList<Board> getAllBoards() {
        return boards;
    }

    public Board getBoardByTitle(String name) {
        for (Board board : boards) {
            if (board.getName().equals(name)) {
                return board;
            }
        }
        return null; // Board with given title not found
    }

    public void update(Board updatedBoard) {
        for (int i = 0; i < boards.size(); i++) {
            Board board = boards.get(i);
            if (board.getName() == updatedBoard.getName()) {
                // Update the board attributes
                board.setName(updatedBoard.getName());
                board.setDate(updatedBoard.getDate());
                break;
            }
        }
    }

    public void delete(Board board) {
        boards.remove(board);
    }
}
