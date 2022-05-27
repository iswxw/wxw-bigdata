package com.wxw.manage.spark_core.rdd.demo02

import java.io.{ObjectOutputStream, OutputStream}
import java.net.Socket

/**
 * @contract: 公众号：技术能量站
 * @desc: 客户端
 * @link:
 */
object Demo02Driver {
  def main(args: Array[String]): Unit = {

    // 连接服务器
    val client1 = new Socket("localhost", 9999)
    val client2 = new Socket("localhost", 8888)

    val task = new Demo02Task()

    // 子任务一
    val out1: OutputStream = client1.getOutputStream
    val objOut1 = new ObjectOutputStream(out1)
    val subTask1 = new Demo02SubTask()
    subTask1.logic = task.logic
    subTask1.data = task.data.take(2)
    objOut1.writeObject(subTask1)
    // 关闭连接
    objOut1.flush()
    objOut1.close()
    client1.close()


    // 子任务二
    val out2: OutputStream = client2.getOutputStream
    val objOut2 = new ObjectOutputStream(out2)
    val subTask2 = new Demo02SubTask()
    subTask2.logic = task.logic
    subTask2.data = task.data.takeRight(2)
    objOut2.writeObject(subTask2)
    // 关闭连接
    objOut2.flush()
    objOut2.close()
    client2.close()

    println("客户端数据发送完毕")

  }
}
