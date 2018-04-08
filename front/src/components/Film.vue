<template>
  <div>
    <div v-if="film" class="container">
      <img :src="film.Poster"/>
      <h1>{{film.Title}}</h1>
    </div>
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
</template>

<script>
import axios from 'axios'
export default {
  name: 'Film',
  data () {
    return {
      film: null,
      avisList: [{
        commentaire: 'Bien bien bien.',
        filmID: 'tt0076759',
        id: 1,
        note: 3
      }, {
        commentaire: 'Bien bien bien.',
        filmID: 'tt0076759',
        id: 1,
        note: 3
      }]
    }
  },
  created () {
    var id = this.$route.params.id
    var api = 'http://www.omdbapi.com/?apikey=5a0f558e&'
    var requete = 'i=' + id
    axios.get(api + requete)
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
