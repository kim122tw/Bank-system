<template>
  <div class="container mt-5">
    <h2>編輯帳戶資訊</h2>
    
    <form @submit.prevent="updateUser">
      <div class="form-group">
        <div class="input-row">
          <label for="username"><span class="text-danger">*</span>帳戶</label>
          <input type="text" id="username" v-model="user.username" class="form-control" disabled>
        </div>
      </div>

      <div class="form-group">
        <div class="input-row">
          <label for="password">密碼</label>
          <input type="password" id="password" v-model="user.password" class="form-control">
        </div>
      </div>

      <div class="form-group">
        <div class="input-row">
          <label for="fullName"><span class="text-danger">*</span>姓名</label>
          <input type="text" id="fullName" v-model="user.fullName" class="form-control" required>
        </div>
      </div>

      <div class="form-group">
        <div class="input-row">
          <label for="email"><span class="text-danger">*</span>電子郵件</label>
          <input type="email" id="email" v-model="user.email" class="form-control" required>
        </div>
      </div>

      <div class="form-group">
        <div class="input-row">
          <label for="phoneNumber">電話號碼</label>
          <input type="text" id="phoneNumber" v-model="user.phoneNumber" class="form-control">
        </div>
      </div>

      <button type="submit" class="btn btn-primary mt-3">更新資料</button>
      <button type="button" class="btn btn-secondary mt-3" @click="resetForm">還原修改</button>
      <button type="button" class="btn btn-danger mt-3" @click="deleteUser">註銷帳號</button>
      <p v-if="error" class="text-danger">{{ error }}</p>
      <p v-if="message" class="text-success">{{ message }}</p>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: {
        userId: '',
        username: '',
        email: '',
        fullName: '',
        password: '',
        phoneNumber: ''
      },
      originalUser: {},  // 存儲初始數據
      error: '',
      message: ''
    };
  },
  mounted() {
    // 從 localStorage 獲取使用者資料
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.originalUser = JSON.parse(storedUser); // 儲存初始狀態，方便還原
    }
  },
  methods: {
    async updateUser() {
      try {
        const response = await axios.post('http://localhost:8080/users/updateUser', this.user);

        this.message = '帳戶資訊更新成功！';
        alert('帳戶資訊更新成功！');
        localStorage.setItem("user", JSON.stringify(this.user)); // 更新 localStorage 資料
      } catch (error) {
        this.message = '';
        if (error.response) {
          this.error = error.response.data.error || '更新失敗，請稍後再試';
        } else {
          this.error = '無法連線至伺服器，請稍後再試';
        }
      }
    },
    resetForm() {
      this.user = { ...this.originalUser }; // 還原原本的數據
    },
    async deleteUser() {
      try {
        const response = await axios.delete(`http://localhost:8080/users/${this.user.userId}`);

        this.message = '帳戶註銷成功！';
        alert('帳戶註銷成功！');

        // 可以選擇跳轉至其他頁面（如登入頁）
        this.$router.push('/login');
      } catch (error) {
        this.message = '';
        if (error.response) {
          this.error = error.response.data.error || '註銷失敗，請稍後再試';
        } else {
          this.error = '無法連線至伺服器，請稍後再試';
        }
      }
    },
  }
};
</script>

<style>
.input-row {
  display: flex;
  align-items: row;
  margin-bottom: 10px;
}

.input-row label {
  width: 100px; /* 調整標籤寬度 */
  text-align: right;
  margin-right: 10px;
}

.input-row input {
  flex-grow: 1;
}

button {
  margin-top: 10px;
}

button.mt-3 {
  margin-left: 20px;
}

.text-danger {
  color: red;
}

.text-success {
  color: green;
}
</style>
