import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeFrame extends JFrame {
    private TicTacToeTile[][] buttons;
    private String currentPlayer;
    private int moveCount;

    public TicTacToeFrame() {
        super("Tic-Tac-Toe");
        TicTacToe.clearBoard();
        currentPlayer = "X";
        moveCount = 0;
        buttons = new TicTacToeTile[3][3];

        // Layout setup
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                TicTacToeTile tile = new TicTacToeTile(row, col);
                tile.setFont(new Font("Arial", Font.BOLD, 50));
                tile.setFocusPainted(false);
                tile.setText(" ");
                tile.addActionListener(new TileListener());
                buttons[row][col] = tile;
                boardPanel.add(tile);
            }
        }

        // Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        add(boardPanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class TileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeTile tile = (TicTacToeTile)e.getSource();
            int row = tile.getRow();
            int col = tile.getCol();

            if(!tile.getText().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Illegal move! Choose an empty tile.");
                return;
            }

            tile.setText(currentPlayer);
            TicTacToe.board[row][col] = currentPlayer;
            moveCount++;

            if(moveCount >= 5 && TicTacToe.isWin(currentPlayer)) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                if(playAgain()) resetBoard();
                return;
            }

            if(moveCount >= 7 && TicTacToe.isTie()) {
                JOptionPane.showMessageDialog(null, "It's a tie!");
                if(playAgain()) resetBoard();
                return;
            }

            // Switch player
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }

    private boolean playAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Play again?", "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
            }
        }
        TicTacToe.clearBoard();
        currentPlayer = "X";
        moveCount = 0;
    }
}
