import { defineStore } from 'pinia'
import axios from 'axios'
import type { User, UserCreate, UserUpdate } from '../types/user'

interface UserState {
  users: User[]
  currentUser: User | null
  loading: boolean
  error: string | null
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    users: [],
    currentUser: null,
    loading: false,
    error: null
  }),

  getters: {
    getUserById: (state: UserState) => (id: number) => state.users.find((user: User) => user.id === id),
  },

  actions: {
    async fetchUsers(this: UserState) {
      this.loading = true
      this.error = null
      try {
        const response = await axios.get<User[]>('/api/users')
        this.users = response.data
      } catch (error) {
        this.error = 'Failed to fetch users'
        console.error('Error fetching users:', error)
      } finally {
        this.loading = false
      }
    },

    async fetchUser(this: UserState, id: number) {
      this.loading = true
      this.error = null
      try {
        const response = await axios.get<User>(`/api/users/${id}`)
        this.currentUser = response.data
      } catch (error) {
        this.error = 'Failed to fetch user'
        console.error('Error fetching user:', error)
      } finally {
        this.loading = false
      }
    },

    async createUser(this: UserState, userData: UserCreate) {
      this.loading = true
      this.error = null
      try {
        const response = await axios.post<User>('/api/users', userData)
        this.users.push(response.data)
        return response.data
      } catch (error) {
        this.error = 'Failed to create user'
        console.error('Error creating user:', error)
        throw error
      } finally {
        this.loading = false
      }
    },

    async updateUser(this: UserState, userData: UserUpdate) {
      this.loading = true
      this.error = null
      try {
        const response = await axios.put<User>(`/api/users/${userData.id}`, userData)
        const index = this.users.findIndex((user: User) => user.id === userData.id)
        if (index !== -1) {
          this.users[index] = response.data
        }
        if (this.currentUser?.id === userData.id) {
          this.currentUser = response.data
        }
        return response.data
      } catch (error) {
        this.error = 'Failed to update user'
        console.error('Error updating user:', error)
        throw error
      } finally {
        this.loading = false
      }
    },

    async deleteUser(this: UserState, id: number) {
      this.loading = true
      this.error = null
      try {
        await axios.delete(`/api/users/${id}`)
        this.users = this.users.filter((user: User) => user.id !== id)
        if (this.currentUser?.id === id) {
          this.currentUser = null
        }
      } catch (error) {
        this.error = 'Failed to delete user'
        console.error('Error deleting user:', error)
        throw error
      } finally {
        this.loading = false
      }
    },
  },
}) 