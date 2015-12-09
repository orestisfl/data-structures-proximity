package gr.auth.ee.dsproject.proximity.defplayers;

import gr.auth.ee.dsproject.proximity.board.Board;
import gr.auth.ee.dsproject.proximity.board.ProximityUtilities;
import gr.auth.ee.dsproject.proximity.board.Tile;

public class HeuristicPlayer implements AbstractPlayer {

    int score;
    int id;
    String name;
    int numOfTiles;

    public HeuristicPlayer(final Integer pid) {
        id = pid;
    }

    double getEvaluation(final Board board, final int randomNumber, final Tile tile) {
        final int[] lastMove = board.getOpponentsLastMove();
        if (lastMove[0] == -1 && lastMove[1] == -1) {
            return -0.5;
        }
        final int opponentId = board.getTile(lastMove[0], lastMove[1]).getPlayerId();
        final int myId = opponentId == 1 ? 2 : 1;
        final int x = tile.getX();
        final int y = tile.getY();
        final Tile[] neighbors = ProximityUtilities.getNeighbors(x, y, board);

        int totalBlue = 0;
        int totalRed = 0;
        int k = 0;
        int l = 0;

        double evaluation = 0.0;
        for (final Tile neighbor : neighbors) {
            if (neighbor == null) {
                continue;
            }
            if (neighbor.getPlayerId() == 1) {
                if (randomNumber > neighbor.getScore()) {
                    totalBlue = +neighbor.getScore();
                }
                // posa idiou xrwmatos exoume,���� ������ ���� 1.sta 20ria den
                // mporo na aykshsw
                if (neighbor.getScore() != 20) {
                    k++;
                }
            }
            // athroizo to scre ton geitonikon poy exoun mikrotero score apo to
            // diko m
            else if (neighbor.getPlayerId() == 2) {
                if (randomNumber > neighbor.getScore()) {
                    totalRed = neighbor.getScore();
                }
                if (neighbor.getScore() != 20) {
                    l++;
                }
            }

            // � ��� ���� evaluation �����.�������� +1 ��� ���� ��� ���
            if (myId == 1) {
                evaluation = totalRed + k;
            }
            if (myId == 2) {
                evaluation = totalBlue + l;
            }
        }
        return evaluation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return "Random";
    }

    public int[] getNextMove(final Board board, final int randomNumber) {
        double max = -1;
        final int[] bestCoor = new int[2];
        // HashMap<Integer[], Double> testHashMap = new HashMap<Integer[],
        // Double>();
        for (int i = 0; i < ProximityUtilities.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < ProximityUtilities.NUMBER_OF_COLUMNS; j++) {
                final Tile tile = board.getTile(j, i);
                // Integer[] neigCoordinates = new Integer[2];

                // exo kenh thesh
                if (tile.getPlayerId() == 0) {
                    // neigCoordinates[0] = tile.getX();
                    // neigCoordinates[1] = tile.getY();
                    final double evaluation = getEvaluation(board, randomNumber, tile);
                    System.out.println("" + evaluation + " at " + tile.getX() + " " + tile.getY());
                    if (evaluation >= max) {
                        max = evaluation;
                        bestCoor[0] = tile.getX();
                        bestCoor[1] = tile.getY();
                    }
                    // testHashMap.put(neigCoordinates, evaluation);
                }
            }

        }
        System.out.println(bestCoor[0] + " " + bestCoor[1] + "==" + max);
        return bestCoor;
    }

    public int getNumOfTiles() {
        return numOfTiles;
    }

    public int getScore() {
        return score;
    }

    public void setId(final int id) {
        // TODO Auto-generated method stub
        this.id = id;

    }

    public void setName(final String name) {
        // TODO Auto-generated method stub
        this.name = name;

    }

    public void setNumOfTiles(final int tiles) {
        numOfTiles = tiles;
    }

    public void setScore(final int score) {
        this.score = score;
    }

}
