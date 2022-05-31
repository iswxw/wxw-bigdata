package com.wxw.manage.spark_sql

import com.wxw.util.PathUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
object Demo01SparkSQL {
  def main(args: Array[String]): Unit = {

    // 创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Demo01SparkSQL")

    //创建 SparkSession 对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //RDD=>DataFrame=>DataSet 转换需要引入隐式转换规则，否则无法转换
    //spark 不是包名，是上下文环境对象名

    //读取 json 文件 创建 DataFrame {"username": "技术能量站","age": 18}
    println("filePath = " + PathUtil.GetInputPath("user.json"))

    val df: DataFrame = spark.read.json("wxw-spark/input/user.json")
    df.show()

    // RDD <=> DataFrame
    // DataFrame <=> DataSet
    // RDD <=> DataSet


    // 关闭环境
    spark.close()

  }
}
