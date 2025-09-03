import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/HomePage.vue')
    },
    {
      path: '/editor',
      name: 'Editor',
      component: () => import('@/views/EditorPage.vue')
    },
    {
      path: '/projects',
      name: 'Projects',
      component: () => import('@/views/ProjectsPage.vue')
    },
    {
      path: '/components',
      name: 'Components',
      component: () => import('@/views/ComponentsPage.vue')
    },
    {
      path: '/settings',
      name: 'Settings',
      component: () => import('@/views/SettingsPage.vue')
    }
  ]
})

export default router