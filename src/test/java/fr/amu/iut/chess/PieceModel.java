package fr.amu.iut.chess;

import java.util.ArrayList;

public class PieceModel {

        private static int dernierId = 0;  // Compteur statique pour donner un identifiant unique à chaque pion
        private boolean fristMove;  // Indique si le pion est dans son premier mouvement
        private boolean isWhite;
        private int x;
        private int y;

        public PieceModel(boolean isWhite, int x, int y) {
            this.isWhite = isWhite;
            this.x = x;
            this.y = y;
            dernierId++;

            // Initialiser fristMove à true pour chaque nouvelle instance de Pion
            fristMove = true;
        }

        /**
         * Vérifie si un mouvement est valide pour le pion.
         *
         * @param x       Coordonnée x de destination.
         * @param y       Coordonnée y de destination.
         * @param plateau Plateau de jeu contenant toutes les pièces.
         * @return true si le mouvement est valide, false sinon.
         */

        public boolean isValide(int x, int y, ArrayList<ArrayList<PieceModel>> plateau) {
            int direction = isWhite() ? -1 : 1;

            // Vérifier que les indices sont dans les limites du plateau
            if (x < 0 || x >= plateau.size() || y < 0 || y >= plateau.get(0).size()) {
                return false;
            }

            // Avancer d'une case
            if (x == getX() + direction && y == getY() && plateau.get(x).get(y) == null) {
                return true;
            }

            // Avancer de deux cases depuis la position initiale
            if (fristMove && x == getX() + 2 * direction && y == getY() &&
                    plateau.get(getX() + direction).get(getY()) == null && plateau.get(x).get(y) == null) {
                return true;
            }

            // Capturer une pièce en diagonale
            if (x == getX() + direction && (y == getY() + 1 || y == getY() - 1)) {
                PieceModel piece = plateau.get(x).get(y);
                if (piece != null && piece.isWhite() != isWhite()) {
                    return true;
                }
            }

            return false;
        }

    public boolean isValideCavalier(int x, int y, ArrayList<ArrayList<PieceModel>> plateau) {
        int deltaX = Math.abs(x - getX());
        int deltaY = Math.abs(y - getY());

        // Vérifier que le mouvement correspond à un mouvement en "L" (2 cases dans une direction et 1 case dans l'autre)
        boolean isLMove = (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);

        if (!isLMove) {
            return false;
        }

        // Vérifier que la case de destination n'est pas occupée par une pièce de la même couleur
        PieceModel destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }



    public boolean isValideRoi(int x, int y, ArrayList<ArrayList<PieceModel>> plateau) {
        int deltaX = Math.abs(x - getX());
        int deltaY = Math.abs(y - getY());

        // Vérifier si le mouvement est d'une case dans n'importe quelle direction
        if (deltaX > 1 || deltaY > 1) {
            return false;
        }

        // Vérifier si la case d'arrivée est occupée par une pièce de la même couleur
        PieceModel destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }

    public boolean isValideTour(int x, int y, ArrayList<ArrayList<PieceModel>> plateau) {
        // Vérifier si le mouvement est horizontal ou vertical
        if (x != getX() && y != getY()) {
            return false;
        }

        // Vérifier si le chemin est bloqué
        if (x == getX()) {
            // Mouvement vertical
            int startY = Math.min(y, getY()) + 1;
            int endY = Math.max(y, getY());
            for (int i = startY; i < endY; i++) {
                if (plateau.get(x).get(i) != null) {
                    return false;
                }
            }
        } else {
            // Mouvement horizontal
            int startX = Math.min(x, getX()) + 1;
            int endX = Math.max(x, getX());
            for (int i = startX; i < endX; i++) {
                if (plateau.get(i).get(y) != null) {
                    return false;
                }
            }
        }

        // Vérifier si la case d'arrivée est occupée par une pièce de la même couleur
        PieceModel destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }

    public boolean isValideFou(int x, int y, ArrayList<ArrayList<PieceModel>> plateau) {
        int deltaX = Math.abs(x - getX());
        int deltaY = Math.abs(y - getY());

        // Vérifier que le mouvement est diagonal
        if (deltaX != deltaY) {
            return false;
        }

        // Déterminer la direction du mouvement
        int xDirection = (x - getX()) > 0 ? 1 : -1;
        int yDirection = (y - getY()) > 0 ? 1 : -1;

        // Vérifier que les indices sont dans les limites du plateau
        if (x < 0 || x >= plateau.size() || y < 0 || y >= plateau.get(0).size()) {
            return false;
        }

        // Vérifier qu'il n'y a pas de pièces sur le chemin
        int currentX = getX() + xDirection;
        int currentY = getY() + yDirection;
        while (currentX != x && currentY != y) {
            if (currentX < 0 || currentX >= plateau.size() || currentY < 0 || currentY >= plateau.get(0).size()) {
                return false;
            }
            if (plateau.get(currentX).get(currentY) != null) {
                return false;
            }
            currentX += xDirection;
            currentY += yDirection;
        }

        // Vérifier la case de destination
        PieceModel destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }

    public boolean isValideReine(int x, int y, ArrayList<ArrayList<PieceModel>> plateau) {
        int deltaX = Math.abs(x - getX());
        int deltaY = Math.abs(y - getY());

        // Vérifier si le mouvement est horizontal, vertical ou diagonal
        boolean isHorizontalOrVertical = (x == getX() || y == getY());
        boolean isDiagonal = (deltaX == deltaY);

        if (!isHorizontalOrVertical && !isDiagonal) {
            return false;
        }

        // Vérifier si le chemin est bloqué
        if (isHorizontalOrVertical) {
            if (x == getX()) {
                // Mouvement vertical
                int startY = Math.min(y, getY()) + 1;
                int endY = Math.max(y, getY());
                for (int i = startY; i < endY; i++) {
                    if (plateau.get(x).get(i) != null) {
                        return false;
                    }
                }
            } else {
                // Mouvement horizontal
                int startX = Math.min(x, getX()) + 1;
                int endX = Math.max(x, getX());
                for (int i = startX; i < endX; i++) {
                    if (plateau.get(i).get(y) != null) {
                        return false;
                    }
                }
            }
        } else {
            // Mouvement diagonal
            int xDirection = (x - getX()) > 0 ? 1 : -1;
            int yDirection = (y - getY()) > 0 ? 1 : -1;
            int currentX = getX() + xDirection;
            int currentY = getY() + yDirection;
            while (currentX != x && currentY != y) {
                if (plateau.get(currentX).get(currentY) != null) {
                    return false;
                }
                currentX += xDirection;
                currentY += yDirection;
            }
        }

        // Vérifier si la case d'arrivée est occupée par une pièce de la même couleur
        PieceModel destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }

    public int getY() {
            return y;
    }

    public int getX() {
        return x;
    }

    public boolean isWhite() {
            return true;
    }

    /**
         * Déplace le pion vers de nouvelles coordonnées.
         *
         * @param x Nouvelle coordonnée x.
         * @param y Nouvelle coordonnée y.
         */

        public void move(int x, int y) {
            // Ne désactivez le premier mouvement que si le pion a effectivement bougé
            fristMove = false;
            setCoordonne(x, y);
        }

    public void setCoordonne(int x, int y) {
            this.x = x;
            this.y = y;
    }


}
