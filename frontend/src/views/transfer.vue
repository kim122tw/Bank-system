<template>
  <div class="container mt-5">
    <h2>轉帳頁面</h2>

    <form @submit.prevent="transferFunds">
      <!-- 轉入帳號 -->
      <div class="form-group d-flex align-items-center mb-3">
        <label for="toAccount" class="form-label me-2">轉入帳號</label>
        <select id="toAccount" v-model="transferDetails.toAccount" class="form-control" required>
          <option v-for="user in allusers" :key="user.userId" :value="user.userId">
            {{ user.fullName }}
          </option>
        </select>
      </div>

      <!-- 轉帳金額 -->
      <div class="form-group d-flex align-items-center mb-3">
        <label for="amount" class="form-label me-2">匯出金額</label>
        <input type="number" id="amount" v-model="transferDetails.amount" class="form-control" required min="1">
      </div>

      <!-- 提交轉帳 -->
      <button type="submit" class="btn btn-primary mt-3">提交轉帳</button>
    </form>

    <!-- 顯示結果 -->
    <div v-if="message" :class="{'text-success': success, 'text-danger': !success}" class="mt-4">
      {{ message }}
    </div>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      transferDetails: {
        fromAccount: '',
        toAccount: '',
        amount: 0,
        balance: 0
      },
      allusers: [],  // 儲存所有使用者資料
      message: '', // 顯示結果訊息
      success: false, // 判斷是否成功
    };
  },
  mounted() {
    const user = localStorage.getItem('user');
    if (user) {
      this.transferDetails.fromAccount = JSON.parse(user).userId;
      this.transferDetails.balance = JSON.parse(user).balance;
    }
    this.fetchUsers();  // 組件載入時，請求所有使用者
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('http://localhost:8080/users', this.transferDetails);
        const currentUserId = this.transferDetails.fromAccount; // 取得當前登入者 ID
        // 過濾掉當前登入者，避免自己轉帳給自己
        this.allusers = response.data.filter(user => user.userId !== currentUserId);
      } catch (error) {
        console.error('獲取使用者資料錯誤:', error);
        this.message = '無法獲取使用者資料，請稍後再試';
        this.success = false;
      }
    },
    async transferFunds() {
      try {
        if (this.transferDetails.amount > this.transferDetails.balance){
          alert('轉帳金額大於銀行帳戶金額，請在確認');
          return;
        }
        // 假設後端提供的 API 端點為 /transfer
        const response = await axios.post('http://localhost:8080/transfers', this.transferDetails);

        if (response.data) {
          alert('轉帳成功！');
          this.updateUserBalance(this.transferDetails.balance - this.transferDetails.amount);
          setTimeout(() => {
            this.$router.push('/welcome');
          }, 1000);
        } else {
          alert('轉帳失敗，請檢查帳號和金額');
          this.success = false;
        }
      } catch (error) {
        console.error('轉帳錯誤:', error);
        this.message = '伺服器錯誤，請稍後再試';
        this.success = false;
      }
    },
    updateUserBalance(newBalance) {
      let user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        user.balance = newBalance;
        localStorage.setItem('user', JSON.stringify(user));
        this.transferDetails.balance = newBalance; // 更新 Vue 組件內的 balance
      }
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 500px;
  margin: auto;
}

.form-group {
  display: flex;
  flex-direction: row; /* 水平排列 */
  align-items: center; /* 垂直對齊 */
  margin-bottom: 1rem; /* 為每個 form-group 增加底部間距 */
}

.form-group label {
  width: 120px; /* 調整標籤的寬度，這樣可以與輸入框對齊 */
  text-align: right; /* 讓標籤右對齊 */
  margin-right: 10px; /* 調整標籤與輸入框之間的間距 */
}

.form-group input, .form-group select {
  flex-grow: 1; /* 輸入框和選擇框會自動填滿剩餘空間 */
}

button {
  display: block; /* 按鈕設為區塊元素 */
  text-align: center; /* 讓標籤右對齊 */
  margin: 0 auto; /* 讓按鈕居中對齊 */
  width: 30%; /* 按鈕寬度填滿父容器 */
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
