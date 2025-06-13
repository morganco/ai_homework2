<template>
  <div class="user-table">
    <div class="table-header">
      <h2>Users</h2>
      <div class="table-actions">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search users..."
          class="search-input"
        />
      </div>
    </div>

    <div v-if="userStore.loading" class="loading">
      Loading...
    </div>

    <div v-else-if="userStore.error" class="error">
      {{ userStore.error }}
    </div>

    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>Name / Email</th>
            <th>Address</th>
            <th>Phone</th>
            <th>Website</th>
            <th>Company</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in filteredUsers" :key="user.id" @click="openUserModal(user)">
            <td>
              <div class="user-info">
                <span class="name">{{ user.name }}</span>
                <span class="email">{{ user.email }}</span>
              </div>
            </td>
            <td>
              <div class="address">
                {{ user.address.street }}, {{ user.address.suite }}
                <br />
                {{ user.address.city }}, {{ user.address.zipcode }}
              </div>
            </td>
            <td>{{ user.phone }}</td>
            <td>
              <a :href="user.website" target="_blank" @click.stop>{{ user.website }}</a>
            </td>
            <td>{{ user.company.name }}</td>
            <td>
              <div class="actions" @click.stop>
                <button class="delete-btn" @click="confirmDelete(user)">
                  Delete
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- User Detail Modal -->
    <div v-if="selectedUser" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <button class="close-btn" @click="closeModal">&times;</button>
        <h3>{{ selectedUser.name }}</h3>
        <div class="modal-body">
          <div class="info-section">
            <h4>Contact Information</h4>
            <p><strong>Username:</strong> {{ selectedUser.username }}</p>
            <p><strong>Email:</strong> {{ selectedUser.email }}</p>
            <p><strong>Phone:</strong> {{ selectedUser.phone }}</p>
            <p><strong>Website:</strong> <a :href="selectedUser.website" target="_blank">{{ selectedUser.website }}</a></p>
          </div>

          <div class="info-section">
            <h4>Address</h4>
            <p>{{ selectedUser.address.street }}, {{ selectedUser.address.suite }}</p>
            <p>{{ selectedUser.address.city }}, {{ selectedUser.address.zipcode }}</p>
            <a
              :href="`https://www.google.com/maps?q=${selectedUser.address.geo.lat},${selectedUser.address.geo.lng}`"
              target="_blank"
              class="map-link"
            >
              View on Map
            </a>
          </div>

          <div class="info-section">
            <h4>Company</h4>
            <p><strong>Name:</strong> {{ selectedUser.company.name }}</p>
            <p><strong>Catch Phrase:</strong> {{ selectedUser.company.catchPhrase }}</p>
            <p><strong>Business:</strong> {{ selectedUser.company.bs }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, onMounted } from 'vue'
import { useUserStore } from '../stores/user'
import type { User } from '../types/user'

export default defineComponent({
  name: 'UserTable',
  setup() {
    const userStore = useUserStore()
    const searchQuery = ref('')
    const selectedUser = ref<User | null>(null)

    onMounted(() => {
      userStore.fetchUsers()
    })

    const filteredUsers = computed(() => {
      const query = searchQuery.value.toLowerCase()
      return userStore.users.filter(
        (user) =>
          user.name.toLowerCase().includes(query) ||
          user.email.toLowerCase().includes(query) ||
          user.company.name.toLowerCase().includes(query)
      )
    })

    const openUserModal = (user: User) => {
      selectedUser.value = user
    }

    const closeModal = () => {
      selectedUser.value = null
    }

    const confirmDelete = async (user: User) => {
      if (confirm(`Are you sure you want to delete ${user.name}?`)) {
        try {
          await userStore.deleteUser(user.id)
        } catch (error) {
          console.error('Error deleting user:', error)
        }
      }
    }

    return {
      userStore,
      searchQuery,
      selectedUser,
      filteredUsers,
      openUserModal,
      closeModal,
      confirmDelete,
    }
  },
})
</script>

<style lang="scss" scoped>
.user-table {
  padding: $spacing-lg;
  max-width: 1200px;
  margin: 0 auto;
  
  .table-header {
    @include flex-between;
    margin-bottom: $spacing-lg;

    h2 {
      margin: 0;
      color: $text-color;
    }

    .search-input {
      @include input-base;
      width: 250px;
    }
  }

  .table-container {
    overflow-x: auto;
    @include card;
    @include scrollbar;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    min-width: 800px;
    
    th, td {
      padding: $spacing-md;
      text-align: left;
      border-bottom: 1px solid $border-color;
    }
    
    th {
      background: $background-color;
      font-weight: 600;
      color: $primary-color;
    }

    tr {
      cursor: pointer;
      transition: background-color $transition-fast;

      &:hover {
        background-color: lighten($background-color, 5%);
      }
    }
  }

  .user-info {
    .name {
      display: block;
      font-weight: 500;
      color: $text-color;
    }
    
    .email {
      display: block;
      font-size: 0.9em;
      color: lighten($text-color, 30%);
    }
  }

  .address {
    font-size: 0.9em;
    color: lighten($text-color, 30%);
  }

  .actions {
    display: flex;
    gap: $spacing-sm;

    button {
      @include button-danger;
    }
  }

  // Modal styles
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    @include flex-center;
    z-index: $z-index-modal;
    @include fade-in;
  }

  .modal-content {
    @include card;
    width: 90%;
    max-width: 600px;
    max-height: 90vh;
    overflow-y: auto;
    position: relative;
    @include slide-in;
    @include scrollbar;

    h3 {
      margin: 0 0 $spacing-lg;
      color: $text-color;
      padding-right: $spacing-xl;
    }
  }

  .close-btn {
    position: absolute;
    top: $spacing-md;
    right: $spacing-md;
    background: none;
    border: none;
    font-size: 24px;
    cursor: pointer;
    color: lighten($text-color, 30%);
    padding: 0;
    line-height: 1;

    &:hover {
      color: $text-color;
    }
  }

  .modal-body {
    .info-section {
      margin-bottom: $spacing-lg;

      h4 {
        margin: 0 0 $spacing-md;
        color: $text-color;
        font-size: 1.1em;
      }

      p {
        margin: $spacing-sm 0;
        color: lighten($text-color, 30%);
        line-height: 1.5;
      }
    }

    .map-link {
      display: inline-block;
      margin-top: $spacing-sm;
      color: $primary-color;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideIn {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

// Responsive styles
@include responsive(md) {
  .user-table {
    padding: $spacing-md;

    .table-header {
      flex-direction: column;
      gap: $spacing-md;

      .search-input {
        width: 100%;
      }
    }

    .modal-content {
      width: 95%;
      padding: $spacing-md;
    }
  }
}
</style> 