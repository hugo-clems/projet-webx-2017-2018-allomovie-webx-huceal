<template>
  <div class="container">
    <div v-if="film" class="row">
      <div class="col-lg-3">
        <img :src="img(film.image)"/>
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

          <div class="card" v-for="avis in avisList" :key="avis.id">
            <div class="card-body">
              <span class="note">{{afficherNote(avis.note)}}</span>
              <p>{{avis.note}}</p>
              <p>{{avis.commentaire}}</p>
            </div>
          </div>
      </div>
    </div>

    <div class="row">
      <form>
        <div class="form-group">
          <label for="note">Note</label>
          <input type="number" min="0" max="5" v-model="formAvis.note" class="form-control" id="note" placeholder="Note entre 0 et 5">
         </div>
        <div class="form-group">
          <label for="exampleInput">Password</label>
          <textarea v-model="formAvis.commentaire" class="form-control" rows="3" id="exampleInput"></textarea>
        </div>
        <button type="submit" class="btn btn-primary" v-on:click="envoyerAvis">Submit</button>
      </form>
    </div>
    <div class="row">
      <div class="alert alert-danger" v-for="error in errors " :key="error.id" role="alert">
        {{error}}
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
        console.log('Avis envoyé')
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
          this.errors.push(e.response.data.message)
        })
    },
    getFilm (idFilm) {
      const requete = 'film/' + idFilm
      axios.get(this.api + requete)
        .then(response => {
          this.film = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    afficherNote(note){
      let response = "";
      console.log(note);
      for(let i = 0; i < 5; i++ ){
        if(note > 1){
          response += "<i class=\"fas fa-star\"></i>";
          note -= 1;
        }else if(note > 0.5){
          response += "<i class=\"fas fa-star-half\"></i>";
          note -=0.5;
        }else if (note === -1){
          response += "<i class=\"far fa-star\"></i>";
        }else{

        }
      }
      console.log(response);
      return "<span>"+response+"</span>";
    }
  },
  data () {
    return {
      film: null,
      api: 'http://localhost:8081/allomovie/',
      idFilm: null,
      errors: [],
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
</style>
