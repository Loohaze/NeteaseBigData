import java.util
import java.util.Collections

import com.mongodb.spark.MongoSpark
import org.apache.spark.mllib.feature.Word2VecModel
import org.apache.spark.{SparkConf, SparkContext}
import org.bson.Document

import scala.collection.JavaConverters._
//import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.mllib.feature.Word2Vec
import org.apache.spark.mllib.recommendation.{ALS, Rating}
//import org.apache.spark.sql.SparkSession


object Ml{

  def word2VecRun(sc:SparkContext):Word2VecModel = {
        val input = sc.textFile("songOutput/64561.txt").map(line => line.split(",").toSeq)
    //model train
    val word2vec = new Word2Vec()
      .setVectorSize(50)
      .setNumPartitions(64)

    val model = word2vec.fit(input)
    println("model word size: " + model.getVectors.size)
//        model.save(sc, "kmeansModel")
    val vectorswords = model.getVectors
    println("vectorswords****************")
    println(vectorswords)
    println("vectorswords****************")
    val unit = vectorswords.foreach(r => {
      println("word2vecRRRR=======")
      println(r)
      var allLinkList = List(r._1)
      val floats = r._2.map(x => {
        x.toString
      })
      allLinkList = List.concat(allLinkList,floats)
      println("allLinkListt")
      println(allLinkList)
      println("allLinkList")


      println("word2vecRRRR=======")
      val documenvvvvv = sc.parallelize(
        Seq(new Document("word2vec", allLinkList.asJava))
      )
      println("documenvvvvv===================")
      println(documenvvvvv)
      println("documenvvvvv===================")
      MongoSpark.save(documenvvvvv)

      documenvvvvv
    })


    return model


  }


  def filtering(sc:SparkContext)={
    // Load and parse the data
    val data = sc.textFile("test.txt")
    val ratings = data.map(_.split(',') match { case Array(user, item, rate,len) =>
      Rating(user.toInt, item.toInt, rate.toDouble)
    })

    // Build the recommendation model using ALS
    val rank = 10
    val numIterations = 10
    val model = ALS.train(ratings, rank, numIterations, 0.01)

    // Evaluate the model on rating data
    val usersProducts = ratings.map { case Rating(user, product, rate) =>
      (user, product)
    }
    val predictions =
      model.predict(usersProducts).map { case Rating(user, product, rate) =>
        ((user, product), rate)
      }
    val ratesAndPreds = ratings.map { case Rating(user, product, rate) =>
      ((user, product), rate)
    }.join(predictions)
    val MSE = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
      val err = (r1 - r2)
      err * err
    }.mean()
    println(s"Mean Squared Error = $MSE")
//    model.productFeatures.collect().foreach{
//      println
//    }
    println("********user recommend***************")

    val features = model.userFeatures
    val value = features.collect().foreach(f => {
      println("======ratings=====")
      println(ratings)
      val unit = ratings.collect().map(x => {
        val doubles = Array(x.user,x.product,x.rating)
        val documenttttt=sc.parallelize(
          Seq(new Document("filter",List(x.user,x.product,x.rating).asJava))
        )
        MongoSpark.save(documenttttt)

        documenttttt
      })
      unit
    })




    //写入mongodb


//    val documents = sc.parallelize((1 to 10).map(i => Document.parse(s"{test: $i}")))

//    model.userFeatures.foreach(r->print(new Tuple2(r)))

////    // 将数据写入mongo
//        MongoSpark.save(model.recommendProductsForUsers(1),writeFilterConfig)

    // Save and load model
//    model.save(sc, "filterModel")
//    val sameModel = MatrixFactorizationModel.load(sc, "target/tmp/myCollaborativeFilter")
  }


  def main(args:Array[String])={
//    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    val sparkConf = new SparkConf().setAppName("netease")
                                    .setMaster("local")
      .set("spark.mongodb.input.uri", "mongodb://172.19.240.124")
      .set("spark.mongodb.input.database", "netease")
      .set("spark.mongodb.output.collection", "word2vec")
      .set("spark.mongodb.output.uri", "mongodb://172.19.240.124")
      .set("spark.mongodb.output.database", "netease")
//      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//      .set("spark.mongodb.output.collection", "graph_result")

    val sc= new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
//    Logger.getLogger("org").setLevel(Level.ERROR)
//    Logger.getLogger("com").setLevel(Level.ERROR)
//    filtering(sc)
    val s=word2VecRun(sc)
  }

}