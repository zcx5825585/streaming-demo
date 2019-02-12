import com.zcx.redsoft.sparkclient.MyReceiver
import org.apache.spark.streaming.StreamingContext

class WordCountStreaming {
  val ssc = new StreamingContext("local[2]", "test", Seconds(1))

  def startRead(): Unit = {
    ssc.receiverStream(new MyReceiver)
      .flatMap((line) => line.split(" "))
      .map((rdd) => (rdd, 1))
      .updateStateByKey((currValues: Seq[Int], preValue: Option[Int]) => {
        /*//将目前值相加
        val currValueSum = 0
        for (currValue <- currValues) {
          currValueSum += currValue
        }
        //上面其实可以这样：val currValueSum = currValues.sum，我是为了让读者更直观。
        //上面的Int类型都可以用对象类型替换*/
        Some(currValues.sum + preValue.getOrElse(0)) //目前值的和加上历史值
      })
      .print()
  }
}
