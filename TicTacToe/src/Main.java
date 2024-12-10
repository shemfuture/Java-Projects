import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static char[][] board = new char[3][3];
    private static JButton[][] buttons = new JButton[3][3];
    private static char currentPlayer = 'X';
    private static JLabel playerLabel;

    // Initialize the game board
    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Create the GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Player label
        playerLabel = new JLabel("Player 1's turn", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setBackground(Color.BLACK);
        playerLabel.setOpaque(true);
        frame.add(playerLabel, BorderLayout.NORTH);

        // Game board panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.BLACK);

        // Create buttons for the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(" ");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setForeground(Color.BLACK);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);
    }

    // Button click listener
    private static class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // If the button is already clicked, do nothing
            if (board[row][col] != ' ') {
                return;
            }

            // Mark the board with the current player's symbol
            board[row][col] = currentPlayer;
            buttons[row][col].setText(String.valueOf(currentPlayer));

            // Check if the current player has won
            if (checkWinner(currentPlayer)) {
                JOptionPane.showMessageDialog(null, "Player " + (currentPlayer == 'X' ? "1" : "2") + " wins!");
                if (askToRestart()) {
                    resetBoard();
                }
                return;
            }

            // Check if it's a draw
            if (isBoardFull()) {
                JOptionPane.showMessageDialog(null, "It's a draw!");
                if (askToRestart()) {
                    resetBoard();
                }
                return;
            }

            // Switch player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            // Update the player label
            playerLabel.setText("Player " + (currentPlayer == 'X' ? "1" : "2") + "'s turn");
        }
    }

    // Check if the current player has won
    private static boolean checkWinner(char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }
        return false;
    }

    // Check if the board is full (for draw condition)
    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Reset the board after a win or draw
    private static void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText(" ");
            }
        }
        currentPlayer = 'X'; // Reset to player 'X'
        playerLabel.setText("Player 1's turn");
    }

    // Ask the user if they want to restart the game
    private static boolean askToRestart() {
        int option = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    public static void main(String[] args) {
        // Initialize the board and create the GUI
        initializeBoard();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
