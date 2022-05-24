package com.wxw.test

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
object Spark01_WordCount {
  def main(args: Array[String]): Unit = {
    // TODO: 建立和spark框架的链接
    var sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    var sc = new SparkContext(sparkConf)
    // TODO: 执行业务操作
    // TODO: 关闭链接
    sc.stop()
  }
}
