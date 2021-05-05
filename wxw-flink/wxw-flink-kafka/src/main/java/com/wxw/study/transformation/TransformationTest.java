package com.wxw.study.transformation;

import com.alibaba.fastjson.JSON;
import com.wxw.domain.Student;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/5/4
 */
public class TransformationTest {


    public static void main(String[] args) {

    }

    public static void test_Map() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put("bootstrap.servers", "wxw.plus:9092");
        props.put("zookeeper.connect", "wxw.plus:2181");
        props.put("group.id", "metric-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest"); //value 反序列化
        SingleOutputStreamOperator<Student> student = env.addSource(new FlinkKafkaConsumer<>(
                "student",  //kafka topic
                new SimpleStringSchema(),  // String 序列化
                props)).setParallelism(1).map(str-> JSON.parseObject(str,Student.class));
        /**
         * Map
         */
        SingleOutputStreamOperator<Student> map = student.map(new MapFunction<Student, Student>() {
            @Override
            public Student map(Student value) throws Exception {
                Student s1 = new Student();
                s1.id = value.id;
                s1.name = value.name;
                s1.password = value.password;
                s1.age = value.age + 5;
                return s1;
            }
        });
        map.print();


        /**
         * FlatMap
         */
        SingleOutputStreamOperator<Student> flatMap = student.flatMap(new FlatMapFunction<Student, Student>() {
            @Override
            public void flatMap(Student value, Collector<Student> out) throws Exception {
                if (value.id % 2 == 0) { // 将 id 为偶数的聚集出来。
                    out.collect(value);
                }
            }
        });
        flatMap.print();

        /**
         * Filter 按条件过滤
         */
        SingleOutputStreamOperator<Student> filter = student.filter(new FilterFunction<Student>() {
            @Override
            public boolean filter(Student value) throws Exception {
                if (value.id > 95) {
                    return true;
                }
                return false;
            }
        });
        filter.print();

        /**
         * KeyBy 基于 key 对流进行分区
         */
        KeyedStream<Student, Integer> keyBy = student.keyBy(new KeySelector<Student, Integer>() {
            @Override
            public Integer getKey(Student value) throws Exception {
                // 对 student 的 age 做 KeyBy 操作分区
                return value.age;
            }
        });
        keyBy.print();

    }



}
