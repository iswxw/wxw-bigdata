package com.wxw.controller

import com.wxw.common.TController
import com.wxw.service.WordCountService

/**
 * @contract: 公众号：技术能量站
 * @desc: 控制层
 * @link:
 */
class WordCountController extends TController{

  private val wordCountService = new WordCountService()

  // 调度
  def dispatch(): Unit = {
    val array = wordCountService.dataAnalysis()
    array.foreach(println)
  }

}
