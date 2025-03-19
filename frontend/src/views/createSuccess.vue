<template>
  <div class="container mt-5">
    <h2>{{ message }}</h2>
    <router-link to="/create-account" class="btn btn-primary">返回創建頁面</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: ''
    };
  },
  created() {
    // 獲取 URL 參數中的 message（假設後端在跳轉時傳遞訊息）
    const queryMessage = this.$route.query.message;
    this.message = queryMessage || '帳戶創建成功！';
  }
};
</script>

<style>
.container {
  text-align: center;
  margin-top: 50px;
}
</style>