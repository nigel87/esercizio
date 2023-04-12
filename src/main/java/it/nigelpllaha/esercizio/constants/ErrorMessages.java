package it.nigelpllaha.esercizio.constants;

public class ErrorMessages {
    public static final String NULL_FABRICK_RESPONSE = "Impossibile ottenere i dati richiesti, fabrickResponse è null ";
    public static final String HTTP_ERROR = "Impossibile procedere, errore HTTP: ";
    public static final String REST_CLIENT_EXCEPTION = "Impossibile procedere, RestClientException ";
    public static final String VALIDATION_ERROR = "È necessario specificare un valore correto per i campi obbligatori";
    public static final String DATE_ERROR = "Inserire una data valida";

    public static final String FABRICK_API_ERROR = "Errore durante la chimata al API di Fabrick";

     public static final String FIELD_REQUIRED = "Il campo è obbligatorio";
    public static final String FUTURE_OR_CURRENT_DATE = "Inserire una data valida nel formato YYYY-MM-DD. " +
                                                        "La data deve essere odierna o una data futura.";

    public static final String FIELD_LENGTH_THREE  ="Il campo deve contenere esattamente 3 carateri";

    public static final String INVALID_AMOUNT  = "Inserire un importo valido. (Numero decimale positivo)";
    public static final String MINIMUM_SIZE = "Dimensione minima è {min} carateri";
    public static final String MAXIMUM_SIZE = "Dimensione massima è {max} carateri";





}