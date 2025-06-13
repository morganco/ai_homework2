<template>
  <div class="register-page">
    <div class="register-container">
      <h2>Register</h2>
      <form @submit.prevent="handleRegister" class="register-form">
        <div class="form-group">
          <label for="name">Name</label>
          <input
            id="name"
            v-model="name"
            type="text"
            required
            class="form-input"
            placeholder="Enter your name"
          />
        </div>
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
        <div class="form-group">
          <label for="confirmPassword">Confirm Password</label>
          <input
            id="confirmPassword"
            v-model="confirmPassword"
            type="password"
            required
            class="form-input"
            placeholder="Confirm your password"
          />
        </div>
        <div v-if="error" class="error-message">
          {{ error }}
        </div>
        <button type="submit" class="submit-btn" :disabled="loading">
          {{ loading ? 'Registering...' : 'Register' }}
        </button>
        <p class="login-link">
          Already have an account? <router-link to="/login">Login here</router-link>
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
  name: 'RegisterPage',
  setup() {
    const router = useRouter()
    const authStore = useAuthStore()
    const name = ref('')
    const email = ref('')
    const password = ref('')
    const confirmPassword = ref('')
    const error = ref('')
    const loading = ref(false)

    const handleRegister = async () => {
      if (password.value !== confirmPassword.value) {
        error.value = 'Passwords do not match'
        return
      }

      try {
        loading.value = true
        error.value = ''
        await authStore.register(name.value, email.value, password.value)
        router.push('/login')
      } catch (err) {
        error.value = 'Registration failed. Please try again.'
      } finally {
        loading.value = false
      }
    }

    return {
      name,
      email,
      password,
      confirmPassword,
      error,
      loading,
      handleRegister
    }
  }
})
</script>

<style lang="scss" scoped>
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: $background-color;
  padding: $spacing-lg;
}

.register-container {
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

.register-form {
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

  .login-link {
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