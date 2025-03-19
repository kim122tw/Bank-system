<template>
  <div class="container mt-5">
    
    <form @submit.prevent="loginUser">
      <h2>請登入您的帳戶</h2>
      
      <div class="form-group d-flex align-items-center">
        <label for="username" class="form-label me-2">帳號</label>
        <input type="text" id="username" v-model="credentials.username" class="form-control flex-grow-1" required>
      </div>

      <div class="form-group d-flex align-items-center">
        <label for="password" class="form-label me-2">密碼</label>
        <input type="password" id="password" v-model="credentials.password" class="form-control flex-grow-1" required>
      </div>

      <button type="submit" class="btn btn-primary mt-login">登入</button>
    </form>
    
    <p v-if="error" class="text-danger">{{ error }}</p>
    <p v-if="message" class="text-success">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      credentials: {
        username: '',
        password: ''
      },
      error: '',
      message: '',
      user: null, //存客戶資料
    };
  },
  methods: {
    async loginUser() {
      try {
        const response = await axios.post('http://localhost:8080/login', this.credentials);

        if (JSON.parse(response.data.accountExist)) {
          this.error = '';
          this.message = '登入成功！即將跳轉...';
          this.user = response.data.user; // 存後端回傳的用戶資料

          localStorage.setItem("user", JSON.stringify(this.user));

          setTimeout(() => {
            this.$router.push('/welcome');
          }, 1000);
        } else {
          this.error = '登入失敗，請檢查帳號或密碼';
          this.message = '';
        }
      } catch (error) {
        console.error('錯誤:', error);
        this.error = '伺服器錯誤，請稍後再試';
      }
    },
  mounted() {
    localStorage.removeItem("user"); // 🔹 進入登入頁時清除 localStorage
  }
  }
};
</script>

<style>
.container {
  max-width: 400px;
  margin: auto;
  padding-top: 40px;
}
h2 {
  text-align: center;
}
button.mt-login {
  margin-top: 20px;
  margin-left: 80px; /* 這裡增加左邊的間距 */
}
.form-label {
  white-space: nowrap;
}

</style>
