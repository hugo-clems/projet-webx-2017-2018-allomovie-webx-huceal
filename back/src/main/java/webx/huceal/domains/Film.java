package webx.huceal.domains;

public class Film {

	private String filmId;
	private String titre;
	private String anneeSortie;
	private String duree;
	private String genre;
	private String studio;
	private String producteur;
	private String scenariste;
	private String acteurs;
	private String description;
	private String image;

	public Film() {
		super();
	}

	public Film(String filmId, String titre, String anneeSortie, String duree,
				String genre, String studio, String producteur,
				String scenariste, String acteurs, String description,
				String image) {
		this.filmId = filmId;
		this.titre = titre;
		this.anneeSortie = anneeSortie;
		this.duree = duree;
		this.genre = genre;
		this.studio = studio;
		this.producteur = producteur;
		this.scenariste = scenariste;
		this.acteurs = acteurs;
		this.description = description;
		this.image = image;
	}

	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAnneeSortie() {
		return anneeSortie;
	}

	public void setAnneeSortie(String anneeSortie) {
		this.anneeSortie = anneeSortie;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getProducteur() {
		return producteur;
	}

	public void setProducteur(String producteur) {
		this.producteur = producteur;
	}

	public String getScenariste() {
		return scenariste;
	}

	public void setScenariste(String scenariste) {
		this.scenariste = scenariste;
	}

	public String getActeurs() {
		return acteurs;
	}

	public void setActeurs(String acteurs) {
		this.acteurs = acteurs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
