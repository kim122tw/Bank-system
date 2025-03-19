<template>
  <div class="container mt-5">
    <h2>創建銀行帳戶</h2>
    
    <form @submit.prevent="createUser">
      <div class="form-group">
        <div class="input-row">
          <label for="username"><span class="text-danger">*</span>帳戶</label>
          <input type="text" id="username" v-model="user.username" class="form-control" @blur="checkUsername" required>
          
        </div>
        <span v-if="usernameError" :class="{'text-danger': usernameError.includes('已存在'), 'text-success': usernameError.includes('可使用')}">
            {{ usernameError }}
          </span>
      </div>

      <div class="form-group">
        <div class="input-row">
          <label for="password"><span class="text-danger">*</span>密碼</label>
          <input type="password" id="password" v-model="user.password" class="form-control" required>
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

      <div class="form-group">
        <div class="input-radio">
          <label for="payYN">是否要以現金支付開帳號的500元</label>
          <label>
            <input type="radio" v-model="user.payYN" value="yes" class="form-check-input">
            是
          </label>
          <label class="ml-3">
            <input type="radio" v-model="user.payYN" value="no" class="form-check-input">
            否
          </label>
        </div>
      </div>

      <button type="submit" class="btn btn-primary mt-2">創建帳戶</button>
      <button type="clear" class="btn btn-secondary mt-2" @click="clearForm">資料清空</button>
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
        username: '',
        email: '',
        fullName: '',
        password: '',
        phoneNumber: '',
        payYN: 'no'
      },
      usernameError: '',  // ✅ 新增帳號檢查的錯誤訊息
      error: '',   // ✅ 確保這裡有初始化
      message: ''  // ✅ 確保這裡有初始化
    };
  },
  methods: {
    async checkUsername() {
    if (!this.user.username) {
      this.usernameError = ''; // 如果欄位是空的，清除錯誤訊息
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8080/users/username/${this.user.username}`, {
        params: { username: this.user.username }
      });

      if (JSON.parse(response.data)) {
        this.usernameError = '帳號已存在';
      } else {
        this.usernameError = '帳號可使用';
      }
    } catch (error) {
      console.error('檢查帳戶錯誤:', error);
      this.usernameError = '無法檢查帳號，請稍後再試';
    }
  },
    async createUser() {
      try {
        if (this.user.payYN === 'no') {
          const confirmResult = window.confirm('如果開戶沒有存取一定量金額，將無法進行業務。確定要繼續嗎？');
          if (!confirmResult) {
            return; // 使用者選擇取消，直接 return 不執行後續邏輯
          }
        }
        const response = await axios.post('http://localhost:8080/users/createUser', new URLSearchParams({
            username: this.user.username,
            email: this.user.email,
            fullName: this.user.fullName,
            password: this.user.password,
            phoneNumber: this.user.phoneNumber,
            payYN: this.user.payYN
        }));

        // 成功時
        this.message = '帳戶創建成功！';

        setTimeout(() => {
          this.$router.push('/login'); // 創建成功後導向登入頁
        }, 1000);
      } catch (error) {
        if (error.response) {
          // 伺服器回傳錯誤（409, 500等）
          if (error.response.status === 409) {
            this.error = '帳號已存在，請換一個名稱';
          } else {
            this.error = error.response.data.error || '發生未知錯誤';
          }
        } else {
          // 伺服器未回應
          this.error = '無法連線至伺服器，請稍後再試';
        }
      }
    },
    clearForm() {
      this.user = {
        username: '',
        email: '',
        fullName: '',
        password: '',
        phoneNumber: '',
        payYN: 'no'
      };
    }
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

.input-radio {
  display: flex; /* 讓其子元素排列 */
  flex-direction: row; /* 設置為水平方向，讓是/否在同一行 */
  margin-bottom: 10px;
}

.input-radio label {
  width: auto; /* 自動調整寬度 */
  text-align: left;
  margin-right: 15px; /* 給每個選項間隔 */
}

.input-radio input {
  margin-right: 5px; /* 單選按鈕與文字之間的間距 */
}

.ml-3 {
  margin-left: 15px; /* 調整"否"選項的間隔 */
}

button {
  margin-top: 10px;
}

button.mt-2 {
  margin-left: 50px; /* 這裡增加左邊的間距 */
}

.text-danger {
  color: red; /* 紅色星號 */
}

.form-check-input {
  margin-right: 10px;
}
</style>
