<template>
  <div class="container mt-5">
    <h2>交易紀錄</h2>

    <!-- 顯示結果訊息 -->
    <div v-if="message" :class="{'text-success': success, 'text-danger': !success}" class="mt-4">
      {{ message }}
    </div>

    <!-- 下載按鈕 -->
    <button v-if="transactions.length > 0" class="btn btn-success mt-3" @click="downloadExcel">
      下載交易紀錄
    </button>

    <!-- 交易紀錄列表 -->
    <table class="table table-striped mt-4" v-if="transactions.length > 0">
      <thead>
        <tr>
          <th>交易方式</th>
          <th>交易金額</th>
          <th>交易日期</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="transaction in transactions" :key="transaction.id">
          <td>{{ transaction.transactionType }}</td>
          <td>{{ transaction.amount }}</td>
          <td>{{ new Date(transaction.createdAt).toLocaleString() }}</td>
        </tr>
      </tbody>
    </table>

    <!-- 如果沒有交易紀錄 -->
    <div v-else class="mt-4">
      <p>目前沒有任何交易紀錄。</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      transactions: [], // 儲存交易紀錄
      message: '', // 顯示訊息
      success: false, // 顯示成功或失敗
    };
  },
  mounted() {
    this.fetchTransactions(); // 組件載入時請求交易紀錄
  },
  methods: {
    async fetchTransactions() {
      try {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) {
          this.message = '請先登入';
          this.success = false;
          return;
        }

        const response = await axios.get(`http://localhost:8080/transactions/${user.userId}`);
        if (response.data && response.data.length > 0) {
          this.transactions = response.data; // 設置交易紀錄
          this.message = '成功載入交易紀錄';
          this.success = true;
        } else {
          this.message = '目前沒有任何交易紀錄';
          this.success = false;
        }
      } catch (error) {
        console.error('獲取交易紀錄錯誤:', error);
        this.message = '無法載入交易紀錄，請稍後再試';
        this.success = false;
      }
    },
    downloadExcel() {
      const user = JSON.parse(localStorage.getItem('user'));
      if (!user) {
        alert('請先登入');
        return;
      }

      // 發送 GET 請求下載 Excel 文件
      window.location.href = `http://localhost:8080/transactions/downloadTransactions?userId=${user.userId}`;
    }
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
}

table {
  width: 100%;
}

th, td {
  text-align: center;
}

th {
  background-color: #f8f9fa;
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
