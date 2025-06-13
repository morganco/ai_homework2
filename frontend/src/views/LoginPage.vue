<template>
  <div class="login-page">
    <div class="login-container">
      <h2>Login</h2>
      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label for="email">Email</label>
          <input
            id="email"
            v-model="email"
            type="email"
            required
            class="form-input"
            placeholder="Enter your email"
          />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            id="password"
            v-model="password"
            type="password"
            required
            class="form-input"
            placeholder="Enter your password"
          />
        </div>
        <div v-if="error" class="error-message">
          {{ error }}
        </div>
        <button type="submit" class="submit-btn" :disabled="loading">
          {{ loading ? 'Logging in...' : 'Login' }}
        </button>
        <p class="register-link">
          Don't have an account? <router-link to="/register">Register here</router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

export default defineComponent({
  name: 'LoginPage',
  setup() {
    const router = useRouter()
    const authStore = useAuthStore()
    const email = ref('')
    const password = ref('')
    const error = ref('')
    const loading = ref(false)

    const handleLogin = async () => {
      try {
        loading.value = true
        error.value = ''
        await authStore.login(email.value, password.value)
        router.push('/users')
      } catch (err) {
        error.value = 'Invalid email or password'
      } finally {
        loading.value = false
      }
    }

    return {
      email,
      password,
      error,
      loading,
      handleLogin
    }
  }
})
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: $background-color;
  padding: $spacing-lg;
}

.login-container {
  @include card;
  width: 100%;
  max-width: 400px;
  padding: $spacing-xl;

  h2 {
    text-align: center;
    color: $text-color;
    margin-bottom: $spacing-xl;
  }
}

.login-form {
  .form-group {
    margin-bottom: $spacing-lg;

    label {
      display: block;
      margin-bottom: $spacing-sm;
      color: $text-color;
    }
  }

  .form-input {
    @include input-base;
    width: 100%;
  }

  .submit-btn {
    @include button-primary;
    width: 100%;
    margin-top: $spacing-lg;
  }

  .error-message {
    color: $error-color;
    margin-bottom: $spacing-md;
    text-align: center;
  }

  .register-link {
    text-align: center;
    margin-top: $spacing-lg;
    color: $text-color;

    a {
      color: $primary-color;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style> 