package webx.huceal.domains;

/**
 * Classe du Film.
 */
public class Film {

    /**
     * L'identifiant du film.
     */
    private String filmId;

    /**
     * Le titre du film.
     */
    private String titre;

    /**
     * L'année de sortie du film.
     */
    private String anneeSortie;

    /**
     * La durée du film.
     */
    private String duree;

    /**
     * Le genre du film.
     */
    private String genre;

    /**
     * Le studio du film.
     */
    private String studio;

    /**
     * Le producteur du film.
     */
    private String producteur;

    /**
     * Le scénariste du film.
     */
    private String scenariste;

    /**
     * Les acteurs du film.
     */
    private String acteurs;

    /**
     * La descirption du film.
     */
    private String description;

    /**
     * L'image du film.
     */
    private String image;

    /**
     * Constructeur par défaut.
     */
    public Film() {
        super();
    }

    /**
     * Créer un nouveau film (version légère).
     * @param newFilmId l'identifiant du nouveau film
     * @param newTitre le titre du nouveau film
     * @param newAnneeSortie l'année de sortie du nouveau film
     * @param newImage l'image du nouveau film
     */
    public Film(final String newFilmId, final String newTitre,
                final String newAnneeSortie, final String newImage) {
        this.filmId = newFilmId;
        this.titre = newTitre;
        this.anneeSortie = newAnneeSortie;
        this.image = newImage;
    }

    /**
     * Créer un nouveau film.
     * @param newFilmId l'identifiant du nouveau film
     * @param newTitre le titre du nouveau film
     * @param newAnneeSortie l'année de sortie du nouveau film
     * @param newDuree la durée du nouveau film
     * @param newGenre le genre du nouveau film
     * @param newStudio le studio du nouveau film
     * @param newProducteur le producteur du nouveau film
     * @param newScenariste le sécnariste du nouveau film
     * @param newActeurs les acteurs du nouveau film
     * @param newDescription la description du nouveau film
     * @param newImage l'image du nouveau film
     */
    public Film(final String newFilmId, final String newTitre, final String newAnneeSortie,
                final String newDuree, final String newGenre, final String newStudio,
                final String newProducteur, final String newScenariste,
                final String newActeurs, final String newDescription, final String newImage) {
        this.filmId = newFilmId;
        this.titre = newTitre;
        this.anneeSortie = newAnneeSortie;
        this.duree = newDuree;
        this.genre = newGenre;
        this.studio = newStudio;
        this.producteur = newProducteur;
        this.scenariste = newScenariste;
        this.acteurs = newActeurs;
        this.description = newDescription;
        this.image = newImage;
    }

    /**
     * Getter filmId.
     * @return identifiant du film
     */
    public final String getFilmId() {
        return filmId;
    }

    /**
     * Setter filmId.
     * @param newFilmId identifiant du film
     */
    public final void setFilmId(final String newFilmId) {
        this.filmId = newFilmId;
    }

    /**
     * Getter titre.
     * @return titre du film
     */
    public final String getTitre() {
        return titre;
    }

    /**
     * Setter titre.
     * @param newTitre titre du film
     */
    public final void setTitre(final String newTitre) {
        this.titre = newTitre;
    }

    /**
     * Getter anneSortie.
     * @return année de sortie du film
     */
    public final String getAnneeSortie() {
        return anneeSortie;
    }

    /**
     * Setter anneSortie.
     * @param newAnneeSortie année de sortie du film
     */
    public final void setAnneeSortie(final String newAnneeSortie) {
        this.anneeSortie = newAnneeSortie;
    }

    /**
     * Getter duree.
     * @return durée du film
     */
    public final String getDuree() {
        return duree;
    }

    /**
     * Setter duree.
     * @param newDuree durée du film
     */
    public final void setDuree(final String newDuree) {
        this.duree = newDuree;
    }

    /**
     * Getter genre.
     * @return genre du film
     */
    public final String getGenre() {
        return genre;
    }

    /**
     * Setter genre.
     * @param newGenre genre du film
     */
    public final void setGenre(final String newGenre) {
        this.genre = newGenre;
    }

    /**
     * Getter studio.
     * @return studio du film
     */
    public final String getStudio() {
        return studio;
    }

    /**
     * Setter studio.
     * @param newStudio studio du film
     */
    public final void setStudio(final String newStudio) {
        this.studio = newStudio;
    }

    /**
     * Getter producteur.
     * @return producteur du film
     */
    public final String getProducteur() {
        return producteur;
    }

    /**
     * Setter producteur.
     * @param newProducteur producteur du film
     */
    public final void setProducteur(final String newProducteur) {
        this.producteur = newProducteur;
    }

    /**
     * Getter du scenariste.
     * @return scenariste du film
     */
    public final String getScenariste() {
        return scenariste;
    }

    /**
     * Setter scenariste.
     * @param newScenariste scenariste du film
     */
    public final void setScenariste(final String newScenariste) {
        this.scenariste = newScenariste;
    }

    /**
     * Getter acteurs.
     * @return acteurs du film
     */
    public final String getActeurs() {
        return acteurs;
    }

    /**
     * Setter acteurs.
     * @param newActeurs acteurs du film
     */
    public final void setActeurs(final String newActeurs) {
        this.acteurs = newActeurs;
    }

    /**
     * Getter description.
     * @return description du film
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Setter description.
     * @param newDescription description du film
     */
    public final void setDescription(final String newDescription) {
        this.description = newDescription;
    }

    /**
     * Getter image.
     * @return image du film
     */
    public final String getImage() {
        return image;
    }

    /**
     * Setter image.
     * @param newImage image du film
     */
    public final void setImage(final String newImage) {
        this.image = newImage;
    }

    /**
     * Vérifie si 2 instances d'un Film sont égales.
     * @param o instance à comparer
     * @return true si les 2 instances sont égales, false sinon
     */
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Film)) {
            return false;
        }

        Film film = (Film) o;

        if (filmId != null ? !filmId.equals(film.filmId) : film.filmId != null) {
            return false;
        }
        if (titre != null ? !titre.equals(film.titre) : film.titre != null) {
            return false;
        }
        if (anneeSortie != null ? !anneeSortie.equals(film.anneeSortie) : film.anneeSortie != null) {
            return false;
        }
        if (duree != null ? !duree.equals(film.duree) : film.duree != null) {
            return false;
        }
        if (genre != null ? !genre.equals(film.genre) : film.genre != null) {
            return false;
        }
        if (studio != null ? !studio.equals(film.studio) : film.studio != null) {
            return false;
        }
        if (producteur != null ? !producteur.equals(film.producteur) : film.producteur != null) {
            return false;
        }
        if (scenariste != null ? !scenariste.equals(film.scenariste) : film.scenariste != null) {
            return false;
        }
        if (acteurs != null ? !acteurs.equals(film.acteurs) : film.acteurs != null) {
            return false;
        }
        if (description != null ? !description.equals(film.description) : film.description != null) {
            return false;
        }
        return image != null ? image.equals(film.image) : film.image == null;
    }

    /**
     * Fonction de hachage.
     * @return Integer
     */
    @Override
    public final int hashCode() {
        final int trenteEtUn = 31;
        int result = getFilmId() != null ? getFilmId().hashCode() : 0;
        result = trenteEtUn * result + (getTitre() != null ? getTitre().hashCode() : 0);
        result = trenteEtUn * result + (getAnneeSortie() != null ? getAnneeSortie().hashCode() : 0);
        result = trenteEtUn * result + (getDuree() != null ? getDuree().hashCode() : 0);
        result = trenteEtUn * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = trenteEtUn * result + (getStudio() != null ? getStudio().hashCode() : 0);
        result = trenteEtUn * result + (getProducteur() != null ? getProducteur().hashCode() : 0);
        result = trenteEtUn * result + (getScenariste() != null ? getScenariste().hashCode() : 0);
        result = trenteEtUn * result + (getActeurs() != null ? getActeurs().hashCode() : 0);
        result = trenteEtUn * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = trenteEtUn * result + (getImage() != null ? getImage().hashCode() : 0);
        return result;
    }

    @Override
    public final String toString() {
        return "Film{"
                + "filmId='" + filmId + '\''
                + ", titre='" + titre + '\''
                + ", anneeSortie='" + anneeSortie + '\''
                + ", duree='" + duree + '\''
                + ", genre='" + genre + '\''
                + ", studio='" + studio + '\''
                + ", producteur='" + producteur + '\''
                + ", scenariste='" + scenariste + '\''
                + ", acteurs='" + acteurs + '\''
                + ", description='" + description + '\''
                + ", image='" + image + '\''
                + '}';
    }
}
