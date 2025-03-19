import { createRouter, createWebHistory } from 'vue-router'
import welcome from '../views/welcome.vue'
import userCreate from '../views/userCreate.vue'
import userUpdate from '../views/userUpdate.vue'
import login from '../views/Login.vue'
import transfer from '../views/transfer.vue'
import transactionList from '../views/transactionList.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: login,
    },
    {
      path: '/welcome',
      name: 'welcome',
      component: welcome,
    },
    {
      path: '/userCreate',
      name: 'userCreate',
      component: userCreate,
    },
    {
      path: '/userUpdate',
      name: 'userUpdate',
      component: userUpdate,
    },
    {
      path: '/transfer',
      name: 'transfer',
      component: transfer,
    },
    {
      path: '/transactionList',
      name: 'transactionList',
      component: transactionList,
    },
  ],
})

router.afterEach((to) => {
  document.title = to.meta.title || '我的銀行系統';
});

export default router
