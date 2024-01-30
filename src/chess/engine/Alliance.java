package chess.engine;

public enum Alliance {
    BLACK {
        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    },
    WHITE {
        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

    };

    public abstract boolean isWhite();

    public abstract boolean isBlack();
}
