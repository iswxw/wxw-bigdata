package com.wxw.util

/**
 * @contract: 公众号：技术能量站
 * @desc:
 * @link:
 */
object PathUtil {

  private val basicPath: String = "wxw-spark/input/"


  def GetInputPath(fileName: String) = {
    basicPath + fileName
  }
}
