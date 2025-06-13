<template>
  <div class="app">
    <header class="header">
      <h1>User Management System</h1>
    </header>
    <main class="main">
      <UserTable />
    </main>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import UserTable from './components/UserTable.vue'

export default defineComponent({
  name: 'App',
  components: {
    UserTable
  }
})
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen,
    Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  line-height: 1.6;
  color: $text-color;
  background-color: $background-color;
}

.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: $primary-color;
  color: white;
  padding: $spacing-md $spacing-lg;
  text-align: center;
  box-shadow: $shadow-sm;

  h1 {
    margin: 0;
    font-size: 1.5rem;
    font-weight: 500;
  }
}

.main {
  flex: 1;
  padding: $spacing-lg 0;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

// Global styles for links
a {
  color: $primary-color;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
}

// Global styles for buttons
button {
  font-family: inherit;
}
</style> 