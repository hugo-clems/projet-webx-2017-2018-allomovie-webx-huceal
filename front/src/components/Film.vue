<template>
  <div class="container">
    <div v-if="film" class="row">
      <img :src="img(film.Poster)"/>
      <h1>{{film.Title}}</h1>
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
      var note = this.formAvis.note
      var commentaire = this.formAvis.commentaire
      axios({
        method: 'post',
        url: this.api + '/truc',
        data: {
          note: note,
          commentaire: commentaire
        }
      }).then(response => {
        console.log('Avis envoyé')
      }).catch(e => {
        this.errors.push(e)
      })
    }
  },
  data () {
    return {
      film: null,
      api: 'http://www.omdbapi.com/?apikey=5a0f558e&',
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
    var idFilm = this.$route.params.id
    var requete = 'i=' + idFilm
    axios.get(this.api + requete)
      .then(response => {
        this.film = response.data
      })
      .catch(e => {
        this.errors.push(e)
      })
  }

}
</script>

<style scoped>

</style>
