package com.wxw.util

import org.apache.spark.SparkContext

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
object EnvUtil {

  private val scLocal = new ThreadLocal[SparkContext]()

  def put(sc: SparkContext): Unit = {
    scLocal.set(sc)
  }

  def take(): SparkContext = {
    scLocal.get()
  }

  def clear(): Unit = {
    scLocal.remove()
  }


}
