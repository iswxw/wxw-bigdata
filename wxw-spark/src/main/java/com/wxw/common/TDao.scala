package com.wxw.common

import com.wxw.util.EnvUtil

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
trait TDao {

  def readFile(path: String) = {
    // 读取文件,获取一行一行的数据 hello world
    EnvUtil.take().textFile(path)
  }
}
