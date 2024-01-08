package chess.engine;

public enum Alliance {
    BLACK {
        @Override
        public boolean isWhite() {
            return false;
        }
    },
    WHITE {
        @Override
        public boolean isWhite() {
            return true;
        }
    };

    public abstract boolean isWhite();
}
