<template>
  <div id="elem1">
    <h1>Liste film</h1>
    {{errors}}
    <div class="container">
      <form class="form-inline">
        <div class="form-group mb-2">
          <label for="title" class="sr-only">Titre</label>
          <input type="text" v-model="form.search_title" class="form-control" id="title" placeholder="Titre">
        </div>
        <div class="form-group mx-sm-3 mb-2">
          <label for="annee" class="sr-only">Année</label>
          <input type="text" v-model="form.search_year" class="form-control" id="annee" placeholder="Année" >
        </div>
        <button type="submit" class="btn btn-primary mb-2" v-on:click="search">Rechercher</button>
      </form>
    </div>
    <div class="container">
      <div  v-if="filmList.length > 0" v-for="film of filmList" class="card flex-md-row box-mb-4 box-shadow" style="width: 18rem;">
        <img class="card-img-top " :src="img(film.Poster)" alt="Card image cap">
        <div class="card-body">
          <h5 class="card-title">{{film.Title}}</h5>
          <p class="card-text">{{film.Year}}</p>
          <router-link :to="{path: '/film/'+film.imdbID}" class="btn btn-primary">Voir Film</router-link>
        </div>
      </div>
      <div v-if="filmList == undefined">Aucun film trouvé</div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  methods: {
    search: function () {
      console.log(this.form.search_title, this.form.search_year)
      var title = this.form.search_title
      var year = this.form.search_year
      this.getFilmList(title, year)
    },
    getFilmList: function (title = 'star wars', year = null) {
      console.log(title, year)

      // var api = 'http://www.omdbapi.com/?apikey=5a0f558e&'
      // var request = 's=' + title.replace(' ', '+')
      var api = 'http://localhost:8081/allomovie/film/liste/'
      var request = title.replace(' ', '+')
      if (year != null) { request += '&annee=' + year }
      console.log(api + request)
      axios.get(api + request)
        .then(response => {
          console.log(response)
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    img (url) {
      if (url == 'N/A') {
        return 'http://via.placeholder.com/300x400'
      } else {
        return url
      }
    }
  },
  data () {
    return {
      filmList: [],
      errors: [],
      form: []
    }
  },
  created () {
    this.getFilmList()
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
.liste-film{
  display: block;
  float: left;

}
</style>
