package com.wxw.manage.spark_core.rdd.demo01

import java.io.{ObjectOutputStream, OutputStream}
import java.net.Socket

/**
 * @contract: 公众号：技术能量站
 * @desc: 客户端
 * @link:
 */
object Demo01Driver {
  def main(args: Array[String]): Unit = {

    // 连接服务器
    val client = new Socket("localhost", 9999)
    val out: OutputStream = client.getOutputStream

    val objOut = new ObjectOutputStream(out)
    val task = new Demo01Task()
    objOut.writeObject(task)

    // 关闭连接
    objOut.flush()
    objOut.close()
    client.close()
    println("客户端数据发送完毕")

  }
}
