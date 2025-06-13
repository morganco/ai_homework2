import { mount } from '@vue/test-utils'
import UserTable from '@/components/UserTable.vue'
import { createPinia, setActivePinia } from 'pinia'

describe('UserTable.vue', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('renders correctly', () => {
    const wrapper = mount(UserTable)
    expect(wrapper.exists()).toBe(true)
  })

  it('displays a loading message when data is loading', () => {
    const wrapper = mount(UserTable)
    // Simulate loading state (assuming userStore.loading is initially true or can be mocked)
    // For a more robust test, you would mock the Pinia store's state
    expect(wrapper.text()).toContain('Loading...')
  })

  // You would add more tests here for filtering, modal interactions, delete, etc.
}) 