import { defineStore } from 'pinia'
import axios from 'axios'

interface User {
  id: number
  name: string
  email: string
}

interface Address {
  street: string;
  suite: string;
  city: string;
  zipcode: string;
  lat: string;
  lng: string;
}

interface Company {
  name: string;
  catchPhrase: string;
  bs: string;
}

interface AuthState {
  user: User | null
  token: string | null
  isAuthenticated: boolean
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => {
    const token = localStorage.getItem('token')
    if (token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    }
    return {
      user: null,
      token: token,
      isAuthenticated: !!localStorage.getItem('token')
    }
  },

  actions: {
    async login(email: string, password: string) {
      try {
        const response = await axios.post('/api/auth/login', { email, password })
        const { token, user } = response.data
        
        this.token = token
        this.user = user
        this.isAuthenticated = true
        
        localStorage.setItem('token', token)
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
      } catch (error) {
        throw new Error('Login failed')
      }
    },

    async register(
      name: string,
      username: string,
      email: string,
      password: string,
      phone: string,
      website: string,
      address: Address,
      company: Company
    ) {
      try {
        await axios.post('/api/auth/register', {
          name,
          username,
          email,
          password,
          phone,
          website,
          address,
          company
        })
      } catch (error) {
        throw new Error('Registration failed')
      }
    },

    logout() {
      this.user = null
      this.token = null
      this.isAuthenticated = false
      localStorage.removeItem('token')
      delete axios.defaults.headers.common['Authorization']
    },

    async checkAuth() {
      if (this.token) {
        try {
          await axios.get('/api/auth/validate')
          this.isAuthenticated = true
          return true
        } catch (error) {
          this.logout()
          return false
        }
      } else {
        this.logout()
        return false
      }
    }
  }
}) 