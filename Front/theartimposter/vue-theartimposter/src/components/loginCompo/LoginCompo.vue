<template>
  <div class="login">
    <template v-if="isAuthenticated">
<!--      <h1>Welcome, {{ user.name }}!</h1>-->
<!--      <p>Email: {{ user.email }}</p>-->
      <button @click="logout">Logout</button>
    </template>
    <template v-else>
      <div class="login-header">
        <h1>The Art Imposter</h1>
        <h3>로그인 후 진행해주세요 </h3>
      </div>
      <div class="login-input-container">
        <b-form>
          <b-form-group
              id="input-group-1"
              label="Id address:"
              label-for="input-1"
          >
            <b-form-input
                id="input-1"
                type="Id"
                placeholder="ID"
                required
            ></b-form-input>
            <b-form-group id="input-group-2" label="Your Name:" label-for="input-2">
              <b-form-input
                  id="input-2"
                  type="password"
                  placeholder="Password"
                  required
              ></b-form-input>
            </b-form-group>
          </b-form-group>
        </b-form>
      </div>
      <div class="login-container">
        <button class="login-btn naver" @click="loginWithOAuth2('naver')">
          <img src="@/assets/naverBtn.png" alt="Login with Naver">
        </button>
        <button class="login-btn google" @click="loginWithOAuth2('google')">
          <img src="@/assets/googleBtn.png" alt="Login with Google">
        </button>
        <button class="login-btn kakao" @click="loginWithOAuth2('kakao')">
          <img src="@/assets/kakaoBtn.png" alt="Login with Kakao">
        </button>
      </div>
      <div>
        <button class="login-submit-btn">Login</button>
      </div>
    </template>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';

export default {
  computed: {
    ...mapState({
      isAuthenticated: state => state.isAuthenticated,
      user: state => state.user
    })
  },
  methods: {
    ...mapActions(['logout']),

    loginWithOAuth2(provider) {
      window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`;
    }
  },
  created() {
    if (this.isAuthenticated && !this.user) {
      this.$store.dispatch('fetchUser');
    }
  }
}
</script>

<style scoped>

.login {
  border: solid black 1px;
  width: max-content;
}
h1:first-child {
  font-size: 2em;
}
.login-header {
  border: solid black 1px;
  max-width: 700px;
}

.login-container {
  border: solid black 1px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  }

.login-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 250px;
  height: 50px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: transform 0.2s;
}

.login-btn img {
  height: 40px;
  margin-right: 1rem;
}

.login-btn.naver {
  background-color: #03C75A;
  color: white;
}

.login-btn.google {
  background-color:#f2f2f2;
  border: 1px solid #ddd;
}

.login-btn.kakao {
  background-color: #FEE500;
  color: #3C1E1E;
}

.login-btn:hover {
  transform: scale(1.05);
}
#input-group-1{
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
  flex-wrap: wrap;
  align-content: space-around;
}
</style>
