import Vue from 'vue'
import Vuex from 'vuex'
import layout from './modules/layout'
import user from './modules/user'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  strict: true,
  state: {},
  mutations: {},
  actions: {},
  getters: {},
  modules: {
    layout,
    user
  },
  plugins: [createPersistedState({
    key: 'vuex',
    storage: window.localStorage,
    reducer (state) {
      return {
        user: {
          userInfo: state.user.userInfo,
          token: state.user.token
        }
      }
    }
  }
  )]
})
