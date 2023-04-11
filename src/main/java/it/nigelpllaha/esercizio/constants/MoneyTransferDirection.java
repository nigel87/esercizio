package it.nigelpllaha.esercizio.constants;

public enum MoneyTransferDirection {
         INCOMING("INCOMING"),
        OUTGOING("OUTGOING");

        private final String value;

        MoneyTransferDirection(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
}
