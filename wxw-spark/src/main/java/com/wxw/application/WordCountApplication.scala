package com.wxw.application

import com.wxw.common.TApplication
import com.wxw.controller.WordCountController

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */

// 继承App 类似于实现 main方法
object WordCountApplication extends App with TApplication {

  // 启动应用程序
  start() {
    val controller = new WordCountController()
    controller.dispatch()
  }
}
