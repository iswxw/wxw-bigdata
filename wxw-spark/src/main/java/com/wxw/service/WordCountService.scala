package com.wxw.service

import com.wxw.common.TService
import com.wxw.dao.WordCountDao
import org.apache.spark.rdd.RDD

/**
 * @contract: 公众号：技术能量站
 * @desc: 业务层
 * @link:
 */
class WordCountService extends TService{

  private val wordCountDao = new WordCountDao()

  // 数据分析
  def dataAnalysis()= {

    val linesRDD = wordCountDao.readFile("wxw-spark/input/word.txt")

    // 将文件中的数据进行分词
    // "hello world" => hello,world,hello,world
    val wordRDD: RDD[String] = linesRDD.flatMap(_.split(" "))

    // 转换数据结构,方便统计  word => (word, 1)
    val word2OneRDD: RDD[(String, Int)] = wordRDD.map((_, 1))

    // 将转换结构后的数据按照相同的单词进行分组聚合
    val word2CountRDD: RDD[(String, Int)] = word2OneRDD.reduceByKey(_ + _)

    // 将数据聚合结果采集到内存中
    val word2Count: Array[(String, Int)] = word2CountRDD.collect()

    word2Count
  }

}
