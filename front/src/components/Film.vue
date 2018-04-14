<template>
  <div class="container">
    <div v-if="film" class="row">
      <img :src="img(film.image)"/>
      <h1>{{film.titre}}</h1>
    </div>
    <div class="row">
      <ul>
        <li v-for="error in errors" >{{error}}</li>
      </ul>
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
      <div class="card">
        <div class="card-header">
          Avis
        </div>
        <div class="card-body" >
          <ul class="list-unstyled" >
            <li class="media" v-for="avis in avisList">
              <div class="media-body">
                {{avis.note}}
                {{avis.commentaire}}
              </div>
            </li>
          </ul>
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
      if (url == 'N/A') {
        return 'http://via.placeholder.com/300x400'
      } else {
        return url
      }
    },
    envoyerAvis () {
      let note = this.formAvis.note
      if (/[0-5]{1/g.test(note) === false) {
        note = -1
      }
      let commentaire = this.formAvis.commentaire
      axios({
        method: 'post',
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
      this.getAvis(this.idFilm);
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
    this.idFilm = this.$route.params.id;
    console.log(this.idFilm);
    this.getFilm(this.idFilm)
    this.getAvis(this.idFilm)
  }

}
</script>

<style scoped>

</style>
