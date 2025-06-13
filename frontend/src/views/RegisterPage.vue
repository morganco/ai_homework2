<template>
  <div class="register-page">
    <div class="register-container">
      <h2>Register</h2>
      <form class="register-form" @submit.prevent="handleRegister">
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
          <label for="username">Username</label>
          <input
            id="username"
            v-model="username"
            type="text"
            required
            class="form-input"
            placeholder="Enter your username"
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
        <div class="form-group">
          <label for="phone">Phone</label>
          <input
            id="phone"
            v-model="phone"
            type="text"
            required
            class="form-input"
            placeholder="Enter your phone number"
          />
        </div>
        <div class="form-group">
          <label for="website">Website</label>
          <input
            id="website"
            v-model="website"
            type="text"
            required
            class="form-input"
            placeholder="Enter your website"
          />
        </div>

        <h3>Address Information</h3>
        <div class="form-group">
          <label for="street">Street</label>
          <input
            id="street"
            v-model="address.street"
            type="text"
            class="form-input"
            placeholder="Enter street"
          />
        </div>
        <div class="form-group">
          <label for="suite">Suite</label>
          <input
            id="suite"
            v-model="address.suite"
            type="text"
            class="form-input"
            placeholder="Enter suite"
          />
        </div>
        <div class="form-group">
          <label for="city">City</label>
          <input
            id="city"
            v-model="address.city"
            type="text"
            class="form-input"
            placeholder="Enter city"
          />
        </div>
        <div class="form-group">
          <label for="zipcode">Zipcode</label>
          <input
            id="zipcode"
            v-model="address.zipcode"
            type="text"
            class="form-input"
            placeholder="Enter zipcode"
          />
        </div>
        <div class="form-group">
          <label for="lat">Latitude</label>
          <input
            id="lat"
            v-model="address.lat"
            type="text"
            class="form-input"
            placeholder="Enter latitude"
          />
        </div>
        <div class="form-group">
          <label for="lng">Longitude</label>
          <input
            id="lng"
            v-model="address.lng"
            type="text"
            class="form-input"
            placeholder="Enter longitude"
          />
        </div>

        <h3>Company Information</h3>
        <div class="form-group">
          <label for="companyName">Company Name</label>
          <input
            id="companyName"
            v-model="company.name"
            type="text"
            class="form-input"
            placeholder="Enter company name"
          />
        </div>
        <div class="form-group">
          <label for="catchPhrase">Catch Phrase</label>
          <input
            id="catchPhrase"
            v-model="company.catchPhrase"
            type="text"
            class="form-input"
            placeholder="Enter catch phrase"
          />
        </div>
        <div class="form-group">
          <label for="bs">BS</label>
          <input
            id="bs"
            v-model="company.bs"
            type="text"
            class="form-input"
            placeholder="Enter BS"
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
import { defineComponent, ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

export default defineComponent({
  name: 'RegisterPage',
  setup() {
    const router = useRouter()
    const authStore = useAuthStore()
    const name = ref('')
    const username = ref('')
    const email = ref('')
    const password = ref('')
    const confirmPassword = ref('')
    const phone = ref('')
    const website = ref('')
    const address = reactive({
      street: '',
      suite: '',
      city: '',
      zipcode: '',
      lat: '',
      lng: ''
    })
    const company = reactive({
      name: '',
      catchPhrase: '',
      bs: ''
    })
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
        await authStore.register(
          name.value,
          username.value,
          email.value,
          password.value,
          phone.value,
          website.value,
          address,
          company
        )
        router.push('/login')
      } catch (err) {
        error.value = 'Registration failed. Please try again.'
      } finally {
        loading.value = false
      }
    }

    return {
      name,
      username,
      email,
      password,
      confirmPassword,
      phone,
      website,
      address,
      company,
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