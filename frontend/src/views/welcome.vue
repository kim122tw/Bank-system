<template>
  <div class="container1">
    <!-- 左上角的歡迎文字 -->
    <div class="welcome">
      <h2>歡迎，{{ username }}！</h2>
    </div>

    <!-- 中央顯示存款金額 -->
    <div class="balance-container1">
      <h3>目前存款金額：</h3>
      <p class="balance">{{ balance }} 元</p>
    </div>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      balance: 0 // 存款金額
    };
  },
  mounted() {
    // 從 localStorage 讀取使用者資訊
    const user = localStorage.getItem('user');
    if (user) {
      this.username = JSON.parse(user).username;
      this.balance = JSON.parse(user).balance; // 取得存款金額
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('user');
      document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
      window.location.href = '/login';
    }
  }
};
</script>

<style>
/* 讓整個畫面使用 Flex 佈局 */
.container1 {
  display: flex;
  flex-direction: column;
  height: 80vh;
  width: 50vw; /* 讓內容佔滿整個螢幕 */
  justify-content: space-between; /* 讓內容分散排列 */
  padding: 20px;
  box-sizing: border-box; /* 避免 padding 影響佈局 */
}

/* 左上角的歡迎文字 */
.welcome {
  align-self: flex-start;
  font-size: 20px;
}

/* 存款金額置中 */
.balance-container1 {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.balance {
  font-size: 30px;
  font-weight: bold;
  color: green;
}

/* 右上角的登出按鈕 */
.logout-btn {
  align-self: flex-end;
  padding: 10px 20px;
  background-color: red;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.logout-btn:hover {
  background-color: darkred;
}
</style>
