最开始黑方先和server通信一次，黑方一直query
当红方走了一步，把信息给黑方，黑方不在query，红方query
如吃就可以交替下去

可能是写了new Thread(Thread).start();这样错误的语句导致返回不了数据