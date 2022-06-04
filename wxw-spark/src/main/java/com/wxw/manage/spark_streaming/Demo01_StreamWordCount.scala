package com.wxw.manage.spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @contract: 公众号：技术能量站
 * @desc: netcat 持续发数据
 * @link:
 */
object Demo01_StreamWordCount {

  /**
   * netcat 使用 来模拟实时数据流
   *  - win10: nc -l -p 9999
   *  - linux: nc -lk 9999
   * @param args
   */

  def main(args: Array[String]): Unit = {

    // 初始化spark 配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Demo01_StreamWordCount")

    // 初始化 SparkStreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // 通过监控端口创建 DStream，读进来的数据为一行行
    val lineStreams = ssc.socketTextStream("localhost", 9999)

    // 将每一行数据做切分，形成一个个单词
    val wordStreams = lineStreams.flatMap(_.split(" "))

    // 将单词映射成元组（word,1）
    val wordAndOneStreams = wordStreams.map((_, 1))

    // 将相同的单词次数做统计
    val wordAndCountStreams = wordAndOneStreams.reduceByKey(_+_)

    //打印
    wordAndCountStreams.print()

    //启动 SparkStreamingContext
    ssc.start()

    ssc.awaitTermination()

  }
}
