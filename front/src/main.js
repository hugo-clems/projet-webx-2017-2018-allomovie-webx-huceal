// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

/* eslint-disable no-new */
var StarRating = require('vue-star-rating')
Vue.component('star-rating', StarRating.default)
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
