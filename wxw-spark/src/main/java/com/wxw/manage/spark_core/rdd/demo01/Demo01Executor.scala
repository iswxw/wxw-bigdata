package com.wxw.manage.spark_core.rdd.demo01

import java.io.{InputStream, ObjectInputStream}
import java.net.{ServerSocket, Socket}

/**
 * @contract: 公众号：技术能量站
 * @desc: 服务端
 * @link:
 */
object Demo01Executor {
  def main(args: Array[String]): Unit = {

    // 启动服务器，接收数据
    val server = new ServerSocket(9999)

    println("服务器启动，等待接收数据...")

    // 等待客户端的连接
    val client: Socket = server.accept()
    val in: InputStream = client.getInputStream

    val objIn = new ObjectInputStream(in)
    val demo01Task = objIn.readObject().asInstanceOf[Demo01Task]

    val ints: List[Int] = demo01Task.compute()
    println("计算节点计算的节点为：" + ints)

    objIn.close()
    client.close()
    server.close()
  }
}
