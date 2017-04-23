RESTful, Resource Representational State Transfer

RESTful 表现层 -- Representational

* 文本:  txt、html、xml、json、二进制
* 图片: jpg、png
* http协议的content-type和accept
* 案例: 书籍book是一个资源，获取不同的格式

RESTful 状态转化 -- State Transfer

幂等性：每次HTTP请求相同的参数，相同的URI，产生相同的结果

GET 获取资源
POST 创建资源（不具有幂等性）
PUT 创建（更新）
DELETE 删除资源

1. 每一个URI代表一种**资源**
2. 客户端和服务器之间, 传递这种资源的某种表现层
3. 客户端通过**HTTP动词**, 对服务器端资源进行操作，实现“表现层的状态转化”

Recommended Articles:
http://blog.720ui.com/2016/restful_idempotent/