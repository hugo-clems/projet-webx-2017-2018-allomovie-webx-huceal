<template>
  <div v-if="film" class="container">
    <img :src="film.Poster"/>
    <h1>{{film.Title}}</h1>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Film',
  data () {
    return {
      film: null
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
