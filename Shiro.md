核心：主体/用户（Subject）、权限（Permission）、角色（Role）、资源（Resource）

# 方法
* 通过 AOP 进行分散的权限控制，即方法级别的
* 通过 URL 进行权限控制，即一种集中的权限控制

# 操作
* 单个资源单个权限：`currentUser.checkPermissions("system:user:update");`
* 单个资源多个权限：`subject().checkPermissions("system:user:update", "system:user:delete");`或`subject().checkPermissions("system:user:update,delete");`
* 单个资源全部权限：`subject().checkPermissions("system:user:*");`
* 所有资源全部权限：`subject().checkPermissions("user:view");`
* ``
* ``
