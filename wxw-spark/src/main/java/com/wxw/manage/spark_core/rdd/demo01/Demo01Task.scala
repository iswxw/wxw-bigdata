package com.wxw.manage.spark_core.rdd.demo01

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
class Demo01Task extends Serializable {

  val data = List(1, 2, 3, 4)

  // val logic = (num:Int) => {num * 2}
  val logic: (Int) => Int = _ * 2

  // 计算
  def compute(): List[Int] = {
    data.map(logic)
  }

}
