<template>
  <div id="app">
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <router-link :to="{name: 'Accueil'}" class="navbar-brand">Accueil</router-link>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav mr-auto">
            <!--<li class="nav-item active">
              <a class="nav-link" href="#">Link</a>
            </li>-->
          </ul>
        </div>
      </nav>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'

}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
