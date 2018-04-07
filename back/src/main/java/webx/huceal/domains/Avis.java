package webx.huceal.domains;

public class Avis {

    private String filmID;
    private String note;
    private String commentaire;

    public Avis() {
        super();
    }

    public Avis(String filmID, String note, String commentaire) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}
