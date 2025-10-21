// 判断是否非空、非null、非未定义
export const isNotNullORBlank = (...args) => {
  for (let i = 0; i < args.length; i++) {
    const argument = args[i]
    if (argument == null || argument === '' || argument === undefined) {
      return false
    };
  }
  return true
}
