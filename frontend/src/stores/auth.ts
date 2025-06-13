import { defineStore } from 'pinia'
import axios from 'axios'

interface User {
  id: number
  name: string
  email: string
}

interface AuthState {
  user: User | null
  token: string | null
  isAuthenticated: boolean
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    user: null,
    token: localStorage.getItem('token'),
    isAuthenticated: !!localStorage.getItem('token')
  }),

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

    async register(name: string, email: string, password: string) {
      try {
        await axios.post('/api/auth/register', { name, email, password })
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
      if (!this.token) return false

      try {
        const response = await axios.get('/api/auth/me')
        this.user = response.data
        this.isAuthenticated = true
        return true
      } catch (error) {
        this.logout()
        return false
      }
    }
  }
}) 