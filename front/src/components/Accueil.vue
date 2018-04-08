<template>
  <div class="container" id="elem1">
    <h1>Liste film</h1>

    <div  v-for="film of filmList" class="card" style="width: 18rem;">
      <img class="card-img-top" :src="film.Poster" alt="Card image cap">
      <div class="card-body">
        <h5 class="card-title">{{film.Title}}</h5>
        <p class="card-text">{{film.Year}}</p>
        <router-link :to="{path: '/film/'+film.imdbID}" class="btn btn-primary">Voir Film</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      filmList: [],
      errors: []
    }
  },
  created () {
    var api = "http://www.omdbapi.com/?apikey=5a0f558e&";
    var requete = 's=star+wars';
    axios.get(api+requete)
      .then(response => {
        this.filmList = response.data.Search;
      })
      .catch(e => {
        this.errors.push(e)
      })
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
