package Core.Manager;

import Core.Model.Board;

import java.util.ArrayList;
import java.util.Date;

public class BoardManager {
    private ArrayList<Board> boards;

    public BoardManager() {
        this.boards = new ArrayList<>();
    }

    public void addBoard(String name) {
        Board board = new Board(name);
        boards.add(board);
    }

    public ArrayList<Board> getAllBoards() {
        return boards;
    }

    public Board getBoardById(String title) {
        for (Board board : boards) {
            if (board.getName().equals(title)) {
                return board;
            }
        }
        return null; // Board with given title not found
    }

    public Board getBoardByTitle(String title) {
        for (Board board : boards) {
            if (board.getName().equals(title)) {
                return board;
            }
        }
        return null; // Board with given title not found
    }

    public void editBoard(String title, String newName, Date newDate) {
        Board boardToUpdate = getBoardByTitle(title);
        if (boardToUpdate != null) {
            boardToUpdate.setName(newName);
            boardToUpdate.setDate(newDate);
        } else {
            System.out.println("Board with title " + title + " not found.");
        }
    }

    public void deleteBoard(String title) {
        Board boardToDelete = getBoardByTitle(title);
        if (boardToDelete != null) {
            boards.remove(boardToDelete);
        } else {
            System.out.println("Board with title " + title + " not found.");
        }
    }
}
