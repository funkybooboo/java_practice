package com.example.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer {

    private Random random = new Random();

    public Point play(String[][] positions, String token) {
        String opponentToken = "X".equals(token) ? "O" : "X";
        Point point = winningPoint(positions, token, opponentToken);
        if (point == null) {
            point = blockOpponent(positions, token, opponentToken);
            if (point == null) {
                point = makeMove(positions, token, opponentToken);
            }
        }
        if (point == null) {
            return pickAnyPoint(positions, token, opponentToken);
        }
        return point;
    }

    private Point makeMove(String[][] positions, String token, String opponentToken) {
        Point[] preferredPoints = new Point[] {new Point(0, 0), new Point(0, 2), new Point(2, 0), new Point(2, 2)};
        Map<Point, Point> oppositePointMap = new HashMap<>();
        oppositePointMap.put(preferredPoints[0], preferredPoints[3]);
        oppositePointMap.put(preferredPoints[3], preferredPoints[0]);
        oppositePointMap.put(preferredPoints[1], preferredPoints[2]);
        oppositePointMap.put(preferredPoints[2], preferredPoints[1]);
        for (Point preferredPoint : preferredPoints) {
            Point oppositePoint = oppositePointMap.get(preferredPoint);
            if (positions[preferredPoint.getR()][preferredPoint.getC()].equals(token) &&
                    isPointAvailable(positions, token, opponentToken, oppositePoint)) {
                return oppositePoint;
            }
        }
        List<Point> points = new ArrayList<>(oppositePointMap.keySet());
        Collections.shuffle(points); // randomize list
        for (Point point : points) {
            if (isPointAvailable(positions, token, opponentToken, point)) {
                return point;
            }
        }
        for (Point point : points) {
            Point possiblePoint = new Point(point.getR()+1, point.getC());
            if (isPointAvailable(positions, token, opponentToken, possiblePoint)) {
                return possiblePoint;
            }
            possiblePoint = new Point(point.getR(), point.getC()+1);
            if (isPointAvailable(positions, token, opponentToken, possiblePoint)) {
                return possiblePoint;
            }
            possiblePoint = new Point(point.getR()-1, point.getC());
            if (isPointAvailable(positions, token, opponentToken, possiblePoint)) {
                return possiblePoint;
            }
            possiblePoint = new Point(point.getR(), point.getC()-1);
            if (isPointAvailable(positions, token, opponentToken, possiblePoint)) {
                return possiblePoint;
            }
        }
        return null;
    }

    private boolean isPointAvailable(String[][] positions, String token, String opponentToken, Point point) {
        if (point.getR() >= 0 && point.getR() <= 2 && point.getC() >= 0 && point.getC() <= 2) {
            return !positions[point.getR()][point.getC()].equals(token) && !positions[point.getR()][point.getC()].equals(opponentToken);
        }
        return false;
    }

    private Point winningPoint(String[][] positions, String token, String opponentToken) {
        for (int c = 0 ; c < positions.length ; c++) {
            Point point = getWinningPointInRow(positions, token, opponentToken, c);
            if (point != null) {
                return point;
            }
        }
        for (int r = 0 ; r < positions.length ; r++) {
            Point point = getWinningPointInColumn(positions, token, opponentToken, r);
            if (point != null) {
                return point;
            }
        }
        return getWinningPointInDiagnal(positions, token, opponentToken);
    }

    private Point getWinningPointInDiagnal(String[][] positions, String token, String opponentToken) {
        int c = 0;
        int score = 0;
        int opponentScore = 0;
        for (int r = 0; r < positions.length ; r++) {
            if (positions[r][c].equals(token)) {
                score++;
            } else if (positions[r][c].equals(opponentToken)) {
                opponentScore++;
            }
            c++;
        }
        c = 0;
        if (score - opponentScore == 2) {
            for (int r = 0; r < positions.length ; r++) {
                if (!positions[r][c].equals(token)) {
                    return new Point(r, c);
                }
                c++;
            }
        }
        c = 2;
        score = 0;
        opponentScore = 0;
        for (int r = 0; r < positions.length ; r++) {
            if (positions[r][c].equals(token)) {
                score++;
            } else if (positions[r][c].equals(opponentToken)) {
                opponentScore++;
            }
            c--;
        }
        c = 2;
        if (score - opponentScore == 2) {
            for (int r = 0; r < positions.length ; r++) {
                if (!positions[r][c].equals(token)) {
                    return new Point(r, c);
                }
                c--;
            }
        }
        return null;
    }

    private Point getWinningPointInRow(String[][] positions, String token, String opponentToken, int c) {
        int score = 0;
        int opponentScore = 0;
        for (int r = 0; r < positions.length ; r++) {
            if (positions[r][c].equals(token)) {
                score++;
            } else if (positions[r][c].equals(opponentToken)) {
                opponentScore++;
            }
        }
        if (score - opponentScore == 2) {
            for (int r = 0; r < positions.length ; r++) {
                if (!positions[r][c].equals(token)) {
                    return new Point(r, c);
                }
            }
        }
        return null;
    }

    private Point getWinningPointInColumn(String[][] positions, String token, String opponentToken, int r) {
        int score = 0;
        int opponentScore = 0;
        for (int c = 0; c < positions.length ; c++) {
            if (positions[r][c].equals(token)) {
                score++;
            } else if (positions[r][c].equals(opponentToken)) {
                opponentScore++;
            }
        }
        if (score - opponentScore == 2) {
            for (int c = 0; c < positions.length ; c++) {
                if (!positions[r][c].equals(token)) {
                    return new Point(r, c);
                }
            }
        }
        return null;
    }

    private Point pickAnyPoint(String[][] positions, String token, String opponentToken) {
        List<Point> points = new ArrayList<>();
        for (int r = 0 ; r < positions.length ; r++) {
            for (int c = 0 ; c < positions.length ; c++) {
                if (isPointAvailable(positions, token, opponentToken, new Point(r, c))) {
                    points.add(new Point(r, c));
                }
            }
        }
        if (points.size() > 0) {
            int pick = random.nextInt(points.size());
            return points.get(pick);
        }
        return null;
    }

    private Point blockOpponent(String[][] positions, String token, String opponentToken) {
        Point point = blockOpponentHorizontally(positions, token, opponentToken);
        if (point == null) {
            point = blockOpponentVertically(positions, token, opponentToken);
            if (point == null) {
                point = blockOpponentDiagnally(positions, token, opponentToken);
            }
        }
        return point;
    }

    private Point blockOpponentDiagnally(String[][] positions, String token, String opponentToken) {
        int opponentCnt = 0;
        int playerCnt = 0;
        for (int r = 0; r < positions.length ; r++) {
            if (positions[r][r].equals(opponentToken)) {
                opponentCnt++;
            } else if (positions[r][r].equals(token)) {
                playerCnt++;
            }
        }
        if (opponentCnt - playerCnt == 2) {
            for (int r = 0; r < positions.length ; r++) {
                if (!positions[r][r].equals(opponentToken)) {
                    return new Point(r, r);
                }
            }
        }
        opponentCnt = 0;
        playerCnt = 0;
        int c = 0;
        for (int r = positions.length-1 ; r >= 0 ; r--) {
            if (positions[r][c].equals(opponentToken)) {
                opponentCnt++;
            } else if (positions[r][c].equals(token)) {
                playerCnt++;
            }
            c++;
        }
        if (opponentCnt - playerCnt == 2) {
            c = 0;
            for (int r = positions.length-1 ; r >= 0 ; r--) {
                if (!positions[r][c].equals(opponentToken)) {
                    return new Point(r, c);
                }
                c++;
            }
        }
        return null;
    }

    private Point blockOpponentVertically(String[][] positions, String token, String opponentToken) {
        for (int r = 0; r < positions.length ; r++) {
            int opponentCnt = 0;
            int playerCnt = 0;
            for (int c = 0; c < positions.length ; c++) {
                if (positions[c][r].equals(opponentToken)) {
                    opponentCnt++;
                } else if (positions[c][r].equals(token)) {
                    playerCnt++;
                }
            }
            if (opponentCnt - playerCnt == 2) {
                for (int c = 0; c < positions.length ; c++) {
                    if (!positions[c][r].equals(opponentToken)) {
                        return new Point(c, r);
                    }
                }
            }
        }
        return null;
    }

    private Point blockOpponentHorizontally(String[][] positions, String token, String opponentToken) {
        for (int r = 0; r < positions.length ; r++) {
            int opponentCnt = 0;
            int playerCnt = 0;
            for (int c = 0; c < positions.length ; c++) {
                if (positions[r][c].equals(opponentToken)) {
                    opponentCnt++;
                } else if (positions[r][c].equals(token)) {
                    playerCnt++;
                }
            }
            if (opponentCnt - playerCnt == 2) {
                for (int c = 0; c < positions.length ; c++) {
                    if (!positions[r][c].equals(opponentToken)) {
                        return new Point(r, c);
                    }
                }
            }
        }
        return null;
    }
}
