import Vue from 'vue'
import Router from 'vue-router'
import Accueil from '@/components/Accueil'
import Film from '@/components/Film'
import Avis from '@/components/Avis'

Vue.use(Router)

export default new Router({
  routes: [
    {
//rechercher d'un film
      path: '/',
      name: 'Accueil',
      component: Accueil

    },
    {
      path: '/film/:id',
      name: 'Film',
      component: Film
    },
    {
      path: '/avis',
      name: 'Avis',
      component: Avis
    }
  ]
})
