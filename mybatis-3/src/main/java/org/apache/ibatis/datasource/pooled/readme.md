PoolState是实际的Database Connection缓存池的模型，其中idleConnections和activeConnections为实际的缓存链接储存位置。
idleConnections 链表储存的是上层应用尝试关闭、但没有实际关闭的空闲的数据库链接
activeConnections 链表储存的是上层应用正在使用的数据库链接
