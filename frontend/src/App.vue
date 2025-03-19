<script setup>
import { useRoute } from 'vue-router'
const route = useRoute()
</script>

<template>
  <header class="navbar">
    <div class="header-container">
      <div class="brand">
        <img alt="Vue logo" class="logo" src="@/assets/logo.svg" />
        <h1 class="page-title">QuickBank</h1>
      </div>
    </div>

    <nav class="nav-links"  v-if="route.path === '/login' || route.path === '/userCreate'">
      <RouterLink to="/login">登入</RouterLink>
      <RouterLink to="/userCreate">創建帳戶</RouterLink>
    </nav>

    <nav class="nav-links"  v-if="route.path != '/login' && route.path != '/userCreate'">
      <RouterLink to="/welcome">首頁</RouterLink>
      <RouterLink to="/userUpdate">基本資料設定</RouterLink>
      <RouterLink to="/transfer">轉帳</RouterLink>
      <RouterLink to="/transactionList">交易紀錄</RouterLink>
      <RouterLink to="/login">登出</RouterLink>
    </nav>
  </header>

  <main class="content">
    <RouterView />
  </main>
</template>

<style scoped>
/* 讓標題和 Logo 在同一行 */
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  background-color: #f8f9fa;
  padding: 15px 30px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  left: 0;
  height: 70px;
}

.brand {
  display: flex;
  align-items: center;
}

.brand .logo {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

/* 讓導覽列內的連結水平排列 */
.nav-links {
  display: flex;
  gap: 15px;
}

.nav-links a {
  text-decoration: none;
  font-size: 18px;
  color: #007bff;
  padding: 5px 10px;
}

.nav-links a:hover {
  color: #0056b3;
}

/* 避免內容被固定的 Navbar 擋住 */
.content {
  margin-top: 80px;
}
</style>
