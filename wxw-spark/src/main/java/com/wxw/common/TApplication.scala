package com.wxw.common

import com.wxw.util.EnvUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
trait TApplication {

  def start(master: String = "local[*]", app: String = "WordCount")(op: => Unit): Unit = {
    val sparkConf = new SparkConf().setMaster(master).setAppName(app)
    // 创建 Spark 上下文环境对象（连接对象）
    val sc: SparkContext = new SparkContext(sparkConf)
    EnvUtil.put(sc)

    // 执行代码
    try {
      op
    } catch {
      case ex => println(ex.getMessage)
    }

    // 关闭 Spark 连接
    sc.stop()
    EnvUtil.clear()
  }
}
