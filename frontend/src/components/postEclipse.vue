<script>
import axios from "axios";
export default {
  data() {
            return {
                postList: [],
            };
        },
  async mounted() {
            await this.postRequest()
        },
        methods: {
        postRequest() {
              axios.get("http://localhost:8080/api/posts")
                    .then(res => {
                        res = JSON.stringify(res.data)
                        res = JSON.parse(res)
                        this.postList = res.content;
                      
                    })
                    // Manage errors if 
                    .catch(error => {
                        if (error.request.status == 503) {
                            setTimeout(() => {
                                this.postRequest()
                            }, 0.1);
                        }
                    })
            }
          }

}
</script>
<template>
  <h1>Post Test</h1>
  <ul v-for="post in postList" :key="post.id">
    <li >{{ post.description }}
    </li>
  </ul>
</template>

<style scoped>
</style>