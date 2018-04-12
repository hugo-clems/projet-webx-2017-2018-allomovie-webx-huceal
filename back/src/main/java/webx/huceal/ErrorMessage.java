package webx.huceal;

/**
 * Classe de message d'erreur pour le front.
 */
public class ErrorMessage {

    /**
     * Le message d'erreur.
     */
    private String message;

    /**
     * Constructeur par défaut.
     */
    public ErrorMessage() {
        super();
    }

    /**
     * Créer un nouveau message d'erreur.
     * @param newMessage le message d'erreur
     */
    public ErrorMessage(final String newMessage) {
        this.message = newMessage;
    }

    /**
     * Getter message.
     * @return message d'erreur
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Setter message.
     * @param newMessage message d'erreur
     */
    public final void setMessage(final String newMessage) {
        this.message = newMessage;
    }

    /**
     * Vérifie si 2 instances d'un message d'erreur sont égales.
     * @param o instance à comparer
     * @return true si les 2 instances sont égales, false sinon
     */
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorMessage)) {
            return false;
        }
        ErrorMessage that = (ErrorMessage) o;
        boolean ret;
        if (getMessage() != null) {
            ret = getMessage().equals(that.getMessage());
        } else {
            ret = that.getMessage() == null;
        }
        return ret;
    }

    /**
     * Fonction de hachage.
     * @return Integer
     */
    @Override
    public final int hashCode() {
        int h = 0;
        if (getMessage() != null) {
            h = getMessage().hashCode();
        }
        return h;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
