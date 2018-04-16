<template>
  <div id="elem1"><br/>
    <div class="container">
      <div class="row">
        <h1>Liste Films</h1>
      </div>
      <div class="row">
        <div class="alert alert-danger" v-for="error of errors " :key="error.id" role="alert">
          {{error}}
        </div>
      </div>
      <div class="row">
        <form class="form-inline">
          <div class="form-group mb-2">
            <label for="title" class="sr-only">Titre</label>
            <input type="text" v-model="form.search_title" class="form-control" id="title" placeholder="Titre">
          </div>
          <div class="form-group mx-sm-3 mb-2">
            <label for="annee" class="sr-only">Année</label>
            <input type="text" v-model="form.search_year" class="form-control" id="annee" placeholder="Année" >
          </div>
          <button type="submit" class="btn btn-primary mb-2 theme-couleur-1" v-on:click="search">Rechercher</button>
        </form>
      </div>
      <div class="row">
        <div  v-if="filmList.length > 0" v-for="film of filmList" :key="film.id" class="card flex-md-row box-mb-4 box-shadow" >
          <img class="card-img-top " :src="img(film.image)" width="198px" height="305pxx"  alt="Card image cap">
          <div class="card-body">
            <h5 class="card-title">{{film.titre}}</h5>
            <p class="card-text">{{film.anneeSortie}}</p>
            <router-link :to="{path: '/film/'+film.filmId}" class="card-link">
              <div class="bottom">
                Voir Film
              </div>
            </router-link>
          </div>
        </div>
        <div v-if="filmList == undefined">Aucun film trouvé</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {

  data () {
    return {
      api: 'http://localhost:8081/allomovie/',
      filmList: [],
      errors: [],
      form: []
    }
  },
  created () {
    this.getFilmList()
  },
  methods: {
    search: function () {
      var title = this.form.search_title
      var year = this.form.search_year
      this.getFilmList(title, year)
    },
    getFilmList: function (title = 'star wars', year = null) {
      this.errors = []
      // var api = 'http://www.omdbapi.com/?apikey=5a0f558e&'
      // var request = 's=' + title.replace(' ', '+')
      var api = this.api + 'film/liste/'
      var request = title.replace(' ', '+')
      if (year != null) { request += '/' + year }
      axios.get(api + request)
        .then(response => {
          this.filmList = response.data
        })
        .catch(e => {
          this.errors.push(e.response.data.message)
        })
    },
    img (url) {
      if (url === 'N/A') {
        return 'http://via.placeholder.com/300x400'
      } else {
        return url
      }
    }

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
.card{
  display: block;
  width: 200px;
  margin-left: 3px;
  margin-right: 3px;
  margin-bottom: 10px;
}
.card-link{
  float: bottom;
}
.bottom{
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
}
.bottom:hover{
  background-color: #42b983;
  color: #fff;
  transition: color;
  transition-duration: 0.5s;
}
</style>
