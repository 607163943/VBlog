export default {
  namespaced: true,
  state: {
    BreadcrumbNavArr: []
  },
  mutations: {
    createBreadcrumbNavArr (state, route) {
      state.BreadcrumbNavArr = []
      if (route.meta.name !== '首页') {
        state.BreadcrumbNavArr.push(route.meta.name)
      }
    }
  },
  actions: {},
  getters: {}
}
