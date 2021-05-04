package com.wxw.source.stream;

import com.wxw.source.batch.WordCount;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/2
 */
public class StreamWordCount {

    public static void main(String[] args)  throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 从文件中读取数据
        String filePath = "/Users/mac/IdeaProjects/wxw/wxw-bigdata/wxw-flink/wxw-flink-java/src/main/resources/file/hello.txt";
        DataStream<String> inputDataStream = env.readTextFile(filePath);

        //基于数据流进行转换计算，按空格分词展开，转换成（word,1）二元组进行统计
        DataStream<Tuple2<String,Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
                .keyBy(0)
                .sum(1);
        resultStream.print();

        // 执行任务
        env.execute();
    }

    // 自定义类实现 FlatMapFunction 函数
    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String,Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
            // 按空格分词
            String[] words = value.split(" ");
            // 遍历所有的word,包成二元组输出
            for (String word : words) {
                out.collect(new Tuple2<>(word,1));
            }
        }
    }

}
