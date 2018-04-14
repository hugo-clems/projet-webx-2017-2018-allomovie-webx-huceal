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
     * Constructeur d'un avis pour les tests (pas encore dans la base de données).
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avis avis = (Avis) o;
        if (id != avis.id) return false;
        if (note != avis.note) return false;
        if (filmID != null ? !filmID.equals(avis.filmID) : avis.filmID != null) return false;
        return commentaire != null ? commentaire.equals(avis.commentaire) : avis.commentaire == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (filmID != null ? filmID.hashCode() : 0);
        result = 31 * result + note;
        result = 31 * result + (commentaire != null ? commentaire.hashCode() : 0);
        return result;
    }

}
