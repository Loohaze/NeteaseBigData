import java.io.{File, PrintWriter}

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary
import com.hankcs.hanlp.tokenizer.NLPTokenizer
import com.nju.netease.utils.AsciiUtil
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

import scala.io.Source





object Dbc{


  //获取指定单个目录下所有文件
  def getFiles1(dir: File): Array[File] = {
    dir.listFiles.filter(_.isFile) ++
      dir.listFiles.filter(_.isDirectory).flatMap(getFiles1)
  }


  def segment(sc:SparkContext): Unit = {
    //stop words
    val stopWordPath = "F:\\网易云数据\\stopWords"
    val bcStopWords = sc.broadcast(sc.textFile(stopWordPath).collect().toSet)

    //content segment
    val inPath = "F:\\网易云数据\\songComments\\64561.txt"
    val segmentRes = sc.textFile(inPath)
      .map(AsciiUtil.sbc2dbcCase) //全角转半角
      .mapPartitions(it =>{
      it.map(ct => {
        try {
          val nlpList = NLPTokenizer.segment(ct)
          import scala.collection.JavaConverters._
          nlpList.asScala.map(term => term.word)
            .filter(!bcStopWords.value.contains(_))
            .mkString(" ")
        } catch {
          case e: NullPointerException => println(ct);""
        }
      })
    })

    //save segment result
    segmentRes.saveAsTextFile("F:\\网易云数据\\output\\64561")
    bcStopWords.unpersist()
  }


  def simpleSegment()={
    val list=getFiles1(new File("F:\\网易云数据\\songComments"))
//    val file=Source.fromFile("F:\\网易云数据\\songComments\\64561.txt")
    val filters=("commentList","commentId","content","likedCount","time","userInfo","birthday","city","gender","level","province","userId","userName")
    for(rfile <- list){
      val file=Source.fromFile(rfile)
      for(line <- file.getLines()){
        val list=NLPTokenizer.segment(line)
        import scala.collection.JavaConverters._
        list.asScala.map(term => term.word)
          //        .filter(!filters.contains(_))
          .mkString(" ")
        CoreStopWordDictionary.apply(list)
        val output=rfile.toString.replace("songComments","songOutput")
        val writer = new PrintWriter(new File(output ))
//        println(list)
              writer.write(list.toString)

      }
    }



  }



def main(args:Array[String]): Unit ={

  simpleSegment()
}

}
