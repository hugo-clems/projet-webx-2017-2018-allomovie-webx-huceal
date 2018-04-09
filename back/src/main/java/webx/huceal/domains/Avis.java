package webx.huceal.domains;

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
     * Constructeur d'un avis que l'on va ajouter à la base de données (sans id donc).
     * @param filmID l'id du film auquel l'avis est lié (String)
     * @param note la note donnée (int)
     * @param commentaire le commentaire donné (String)
     */
    public Avis(String filmID, int note, String commentaire) {
        this.filmID = filmID;
        this.note = note;
        this.commentaire = commentaire;
    }

    /**
     * Constructeur d'un avis déjà ajouté à la base de données.
     * @param id l'id de l'avis dans la base de données
     * @param filmID l'id du film auquel l'avis est lié (String)
     * @param note la note donnée (int)
     * @param commentaire le commentaire donné (String)
     */
    public Avis(long id, String filmID, int note, String commentaire) {
        this.id = id;
        this.filmID = filmID;
        this.note = note;
        this.commentaire = commentaire;
    }

    public String getFilmID() {
        return filmID;
    }

    public void setFilmID(String filmID) {
        this.filmID = filmID;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
