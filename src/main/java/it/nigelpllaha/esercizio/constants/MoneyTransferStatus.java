package it.nigelpllaha.esercizio.constants;

public enum MoneyTransferStatus {
    EXECUTED("EXECUTED"),
    BOOKED("BOOKED"),
    WORK_IN_PROGRESS("WORK_IN_PROGRESS"),
    CANCELLED("CANCELLED"),
    REJECTED("REJECTED");

    private final String value;

    MoneyTransferStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
