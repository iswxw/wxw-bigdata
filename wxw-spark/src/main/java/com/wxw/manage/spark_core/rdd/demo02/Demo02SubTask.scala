package com.wxw.manage.spark_core.rdd.demo02

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
class Demo02SubTask extends Serializable {
  var data: List[Int] = _
  var logic: (Int) => Int = _

  // 计算
  def compute(): List[Int] = {
    data.map(logic)
  }

}
