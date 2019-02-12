package com.zcx.redsoft.sparkclient;

import org.apache.spark.api.java.Optional;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 类说明
 *
 * @author zcx
 * @version 创建时间：2018/11/7  15:35
 */
@Lazy
@Component
public class Streaming implements Serializable {

    private static JavaPairDStream<String, Integer> result;

    public Streaming() {
//        Map<String, Object> kafkaParams = new HashMap<>();
//        kafkaParams.put("bootstrap.servers", "localhost:9092,anotherhost:9092");
//        kafkaParams.put("key.deserializer", StringDeserializer.class);
//        kafkaParams.put("value.deserializer", StringDeserializer.class);
//        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
//        kafkaParams.put("auto.offset.reset", "latest");
//        kafkaParams.put("enable.auto.commit", false);

    }

    public static void startRead() {
        try {
            JavaStreamingContext ssc = new JavaStreamingContext("local[2]", "test", Durations.seconds(6));
            ssc.checkpoint("D:/Checkpoint/WordCount");
//        Collection<String> topics = Arrays.asList("topicA");
//        JavaInputDStream<ConsumerRecord<String, String>> lines =KafkaUtils.createDirectStream(
//                ssc,
//                LocationStrategies.PreferConsistent(),
//                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
//        );
            //        JavaReceiverInputDStream<String> lines = ssc.socketTextStream("localhost", 9999);
            JavaReceiverInputDStream<String> lines = ssc.receiverStream(new MyReceiver());
            result = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                    //以" " 分割每一行
                    .mapToPair(rdd -> new Tuple2<>(rdd, 1))
                    //添加权重
                    .reduceByKey((v1, v2) -> v1 + v2)
                    //reduce计算相同词出现次数
                    .updateStateByKey((values, state) -> {
                        //values    List<Integer>类型        本次接收数据
                        //state     Optional<Integer>类型    之前已接收的全部数据
                        //返回值    Optional<Integer>类型    更新后的全部数据
                        Integer newValue = state.orElse(0);
                        for (Integer value : values)
                            newValue += value;
                        return Optional.of(newValue);
                    });
            result.print();

            ssc.start();
            ssc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
