# MyPlans
to do list project

myplan todo list

11.11-11.12
- [x] - [x] 数据库设计，flyaway集成，yml文档配置
- [x] - [x] - [x] 增删改查代码
- [x] - [x] - [x] 代码测试
- [x] - [x] buddy检查

11.15
- [x] 添加type字段
- [x] Delete 字段验证 （columnDefinition & ddl-auto）
- [x] - [x] addAndUpdateTodo (getById方法需要id不能为空，没有合并成功)

11.16
- [x] - [x] - [x] - [x] 分层设计实体结构【DTO，TODO，Entity】
- [x] - [x] 自动化测试

11.17
- [x] - [x] 需求：用户只能操作自己的内容（注解和表设计）
- [x] - [x] - [x] - [x] - [x] 自动化测试【fix】

11.18
- [x] 整理笔记【见有道云笔记】
- [x] 循环依赖的消除【注入相互依赖的其中一方的下一层】
- [x] 整理ManyToOne的思路【sql需要写外键】
- [x] 晚上更改全部代码

11.19
- [ ] 修自动化测试代码【迷糊！】
- [x] 修新代码 bug  and showcase

11.22
- [x] - [x] - [x] - [x] 多条件查询【能够满足仅查询当前用户下的task的条件】
- [x] - [x] 单条件查询

11.23
- [x] 78%覆盖率的测试代码，并提交【多条件查询测试有问题，异常测试有问题 。部分！】

11.24
- [ ] TDD+鉴权实践
- [x] 鉴权修改了表  修改之前的测试
- [x] 鉴权的测试【待验证】
- [ ] 多条件查询代码测试修复

11.25
整理需求：
- [ ] 普通用户可以转化为Admin权限 【  user.setAuthorities(Collections.singletonList(new Authority(username, "ROLE_NORMAL")));   】【未做】
- [x] Admin有权限查看和修改其它用户【鉴权，目前仅admin权限work了】
- [x] 异常测试代码修复【有when,返回optional.empty】

11.26
- [ ] 字段异常的抛错
- [x] 暂时使代码处于work状态 并和大家讨论近期工作

11.29
- [x] 部分能够的security的鉴权搞定

11.30
- [x] 修改测试【加入权限的测试代码修改】
- [x] 重构代码
- [x] 部分补全权限【较深入地理解权限】
