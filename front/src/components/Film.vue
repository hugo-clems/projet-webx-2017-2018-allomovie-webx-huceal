<template>
  <div>
    <div class="container">
      <div v-if="film" class="row">
        <div class="col-lg-3">
          <img :src="img(film.image)" width="275px"/>
        </div>
        <div class="col-lg-9">
          <h1>{{film.titre}}</h1>
          <ul class="info">
            <li><b>Année de sortie :</b> {{film.anneeSortie}}</li>
            <li><b>Durée :</b> {{film.duree}}</li>
            <li><b>Genre :</b> {{film.genre}}</li>
            <li><b>Producteur :</b> {{film.producteur}}</li>
            <li><b>Scénariste :</b> {{film.scenariste}}</li>
            <li><b>Studio :</b> {{film.studio}}
            <li><b>Description :</b></li>
            <li>{{film.description}}</li>
          </ul>
          <div class="commentaireList">
            <ul class="list-group">
              <li class="list-group-item list-group-item-dark"><b>Avis</b></li>
              <li class="list-group-item" v-if="avisList.length > 0" v-for="avis in avisList" :key="avis.id">
                <span class="note float-left" v-html="afficherNote(avis.note)"></span>
                <span class="clearfix"></span>
                <p class="commentaire float-left">{{avis.commentaire}}</p>
              </li>
              <li class="list-group-item" v-if="avisList.length < 1">Aucun commentaire</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="container next-container">
      <div class="row ">
        <div class="col-lg-6  offset-lg-4">
          <h2>Laisser un avis</h2>
          <div v-if="success.length > 0" v-for="suc in success" :key="suc.id" class="alert alert-success" role="alert">
            {{suc}}
          </div>
          <form>
            <div class="form-group row">
              <label for="note" class="col-sm-3 col-form-label">Note</label>
              <div class="col-sm-9">
                <input type="number" min="0" max="5" v-model="formAvis.note" class="form-control" id="note" placeholder="Note entre 0 et 5">
              </div>
             </div>
            <div class="form-group row">
              <label for="commentaire" class="col-sm-3 col-form-label">Commentaire</label>
              <div class="col-sm-9">
                <textarea v-model="formAvis.commentaire" class="form-control" rows="3" id="commentaire"></textarea>
              </div>
            </div>
            <button type="submit" class="btn btn-primary" v-on:click="envoyerAvis">Submit</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  methods: {
    img (url) {
      if (url === 'N/A') {
        return 'http://via.placeholder.com/300x400'
      } else {
        return url
      }
    },
    envoyerAvis () {
      this.success = []
      let note = this.formAvis.note
      if (/[0-5]{1}/g.test(note) === false) {
        note = -1
      }
      let commentaire = this.formAvis.commentaire
      axios({
        method: 'post',
        headers: { 'content-type': 'application/json' },
        url: this.api + 'avis/',
        data: {
          filmID: this.idFilm,
          note: note,
          commentaire: commentaire
        }
      }).then(response => {
        this.success.push('Avis envoyé'); this.getAvis(this.idFilm)
      }).catch(e => {
        this.errors.push(e)
      })
      this.getAvis(this.idFilm)
    },
    getAvis (idFilm) {
      const requete = 'avis/film/' + idFilm
      axios.get(this.api + requete)
        .then(response => {
          this.avisList = response.data
        }).catch(e => {
          this.errors.push(e.response.data.message); this.avisList = []
        })
    },
    getFilm (idFilm) {
      this.errors = []
      const requete = 'film/' + idFilm
      axios.get(this.api + requete)
        .then(response => {
          this.film = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    afficherNote (note) {
      let response = ''
      for (let i = 0; i < 5; i++) {
        if (note >= 1) {
          response += '<i class="fas fa-star"></i>'
          note -= 1
        } else if (note >= 0.5) {
          response += '<i class="fas fa-star-half"></i>'
          note -= 0.5
        } else if (note === -1) {
          response += '<i class="far fa-star"></i>'
        } else {

        }
      }
      return '<span>' + response + '</span>'
    }
  },
  data () {
    return {
      film: null,
      api: 'http://localhost:8081/allomovie/',
      idFilm: null,
      errors: [],
      success: [],
      formAvis: [],
      avisList: [{
        commentaire: 'Bien bien bien.',
        filmID: 'tt0076759',
        id: 1,
        note: 3
      }
      ]
    }
  },
  created () {
    this.idFilm = this.$route.params.id
    console.log(this.idFilm)
    this.getFilm(this.idFilm)
    this.getAvis(this.idFilm)
  }

}
</script>

<style scoped>
.info{
  text-align: left;
  list-style: none;
}
  .commentaireList{
    margin-left: 2.5rem;
  }
  h1{
    margin-left: 2rem;
  }
  .next-container{
    margin-top: 1rem;
  }
</style>
