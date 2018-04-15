package webx.huceal.domains;

/**
 * Classe des Avis.
 */
public class Avis {

    /**
     * ID de l'avis dans la base de données.
     */
    private long id;

    /**
     * ID du film auquel l'avis est lié.
     */
    private String filmID;

    /**
     * Note de 0 à 5.
     * Note = -1 si aucune note n'a été entrée
     */
    private int note;

    /**
     * Commentaire (longueur max 500 caractères).
     */
    private String commentaire;

    /**
     * Constructeur vide d'un avis.
     */
    public Avis() {
        super();
    }

    /**
     * Constructeur d'un avis déjà ajouté à la base de données.
     * @param i l'id de l'avis dans la base de données
     * @param f l'id du film auquel l'avis est lié (String)
     * @param n la note donnée (int)
     * @param c le commentaire donné (String)
     */
    public Avis(final long i, final String f, final int n, final String c) {
        this.id = i;
        this.filmID = f;
        this.note = n;
        this.commentaire = c;
    }

    /**
     * Constructeur d'un avis pour les tests
     * (pas encore dans la base de données).
     * @param f l'id du film auquel l'avis est lié (String)
     * @param n la note donnée (int)
     * @param c le commentaire donné (String)
     */
    public Avis(final String f, final int n, final String c) {
        this.filmID = f;
        this.note = n;
        this.commentaire = c;
    }

    /**
     * Getter de la variable filmID.
     * @return String représentant l'id du film
     */
    public final String getFilmID() {
        return filmID;
    }

    /**
     * Setter de la variable filmID.
     * @param f String de l'id du film
     */
    public final void setFilmID(final String f) {
        this.filmID = f;
    }

    /**
     * Getter de la variable note.
     * @return int représentant la note attribuée
     */
    public final int getNote() {
        return note;
    }

    /**
     * Setter de la variable note.
     * @param n une note donnée
     */
    public final void setNote(final int n) {
        this.note = n;
    }

    /**
     * Getter de la variable commentaire.
     * @return String du commentaire
     */
    public final String getCommentaire() {
        return commentaire;
    }

    /**
     * Setter de la variable commentaire.
     * @param c String du commentaire donné
     */
    public final void setCommentaire(final String c) {
        this.commentaire = c;
    }

    /**
     * Getter de la variable id.
     * @return L'id de l'avis au sein de la base de données
     */
    public final long getId() {
        return id;
    }

    /**
     * Setter de la variable id.
     * @param i long identifiant de l'avis dans la base de données
     */
    public final void setId(final long i) {
        this.id = i;
    }

    /**
     * Vérifie si 2 instances d'un Avis sont égales.
     * @param o instance à comparer
     * @return true si les 2 instances sont égales, false sinon
     */
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Avis avis = (Avis) o;
        if (id != avis.id) {
            return false;
        }
        if (note != avis.note) {
            return false;
        }
        if (filmID != null) {
            if (!filmID.equals(avis.filmID)) {
                return false;
            }
        } else {
            if (avis.filmID != null) {
                return false;
            }
        }
        if (commentaire != null) {
            return commentaire.equals(avis.commentaire);
        } else {
            return avis.commentaire == null;
        }
    }

    /**
     * Fonction de hachage.
     * @return Integer
     */
    @Override
    public final int hashCode() {
        final int trenteEtUn = 31;
        final int trenteDeux = 32;

        int result = (int) (id ^ (id >>> trenteDeux));

        if (filmID != null) {
            result = trenteEtUn * result + filmID.hashCode();
        } else {
            result = trenteEtUn * result;
        }

        result = trenteEtUn * result + note;

        if (commentaire != null) {
            result = trenteEtUn * result + commentaire.hashCode();
        } else {
            result = trenteEtUn * result;
        }

        return result;
    }

    /**
     * Affiche un Avis.
     * @return l'avis en chaîne de caractères
     */
    @Override
    public final String toString() {
        return "Avis{"
                + "id=" + id
                + ", filmID='" + filmID + '\''
                + ", note=" + note
                + ", commentaire='" + commentaire + '\''
                + '}';
    }
}
