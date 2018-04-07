package webx.huceal.domain;

public class Avis {

    private String filmID;
    private int note;
    private String commentaire;

    public Avis() {
        super();
    }

    public Avis(String filmID, int note, String commentaire) {
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

}
