# VBlog — 学习与改造版
> 基于 **lenve/VBlog** 的多用户博客平台。此 fork 的目标：**学习项目结构与实践**、**逐步现代化技术栈**、并输出可复用的工程化模板。

## 1. 这个仓库的目的

- 作为学习材料：阅读并运行一个完整的 Vue + Spring Boot 博客系统。
- 作为改造练习：在不改变核心业务的前提下，分阶段完成工程化与技术栈升级。
- 作为示例脚手架：沉淀「前后端分离 + RBAC + 富文本/Markdown、统计可视化」的最佳实践。

上游项目是一个**多用户博客管理平台**，包含文章管理、栏目/分类、用户管理、数据统计等模块，并提供在线演示与完整启动指南。[GitHub](https://github.com/607163943/VBlog)

## 2. 功能概览（沿袭上游）

- 文章：列表、发布、编辑、草稿与发布状态
- 用户：登录/管理
- 栏目/分类：管理与分配
- 数据统计：图表可视化
- 前端：导航、路由、富文本/Markdown 编辑器、ECharts 图表等
  （以上为上游 README 展示的功能与截图概述。）[GitHub](https://github.com/607163943/VBlog)

## 3. 技术栈（当前与目标）

**当前**

- 后端：Spring Boot、Spring Security、MyBatis、MySQL
- 前端：Vue、axios、ElementUI、vue-echarts、mavon-editor、vue-router [GitHub](https://github.com/607163943/VBlog)

**目标（本 fork 的学习与改造方向，可逐步实施）**

- 后端：Spring Boot 3.x、JDK 21、MyBatis-Plus、Knife4j/OpenAPI 3、统一返回体与异常栈
- 前端：Vue 3 + Vite、Pinia、Element Plus、ECharts 5、Markdown 编辑器（bytemd/TipTap 其一）
- 工程化：多环境配置、代码规范（ESLint 9+ / Prettier / Stylelint / Commitlint）、CI 构建

> 注：目标与节奏可按个人学习安排调整。

## 4. 快速启动（先按上游方式跑起来）

1. 克隆并初始化数据库

- 在 `blogserver/resources` 目录找到 `vueblog.sql`，导入到 MySQL。[GitHub](https://github.com/607163943/VBlog)
- 修改 `application.properties` 数据库配置。

1. 启动后端

- 使用 IntelliJ IDEA 运行 `blogserver`。启动成功后可直接访问内置静态资源。[GitHub](https://github.com/607163943/VBlog)

1. 启动前端（如进行二次开发）

```
cd vueblog
npm install
npm run dev   # 默认 http://localhost:8080
# 构建：npm run build
# 构建产物 dist 下的 static 与 index.html 可复制到 Spring Boot 的 resources/static/
```

（端口转发与部署路径沿袭上游说明。）[GitHub](https://github.com/607163943/VBlog)

> 若只想体验：后端启动后，直接访问 `http://localhost:8081/index.html`（或使用上游演示地址）。 [GitHub](https://github.com/607163943/VBlog)

## 5. 分支与提交规范

- `main`：稳定分支（可部署）
- `dev`：日常开发（学习/改造在此推进）
- 推荐采用 Conventional Commits（`feat: `、`fix: `、`refactor: ` 等）


## 6. 目录结构（保持上游结构；改造时逐步分层）

```
VBlog/
├─ blogserver/     # Spring Boot 后端（含静态资源部署位）
├─ vueblog/        # Vue 前端（开发与构建）
└─ doc/            # 文档与脚本
```

## 7. 许可证与致谢

- 许可证：MIT [GitHub](https://github.com/607163943/VBlog)
- 致谢：原项目作者 **lenve/VBlog**（本仓库为学习与改造目的之 fork）。[GitHub](https://github.com/607163943/VBlog)