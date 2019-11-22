import org.apache.spark.mllib.feature.Word2VecModel
import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.feature.Word2Vec
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating
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
    model.getVectors.foreach{
      println
    }
//    model.getVectors.foreach(r->r)

    return model


    //Save and load model
//    model.save(sc, "kmeansModel.txt")
//    val local = model.getVectors.map{
//      case (word, vector) => Seq(word, vector.mkString(" ")).mkString(":")
//    }.toArray
//    sc.parallelize(local).saveAsTextFile("kmeansVector.txt")
//
//    //predict similar words
//    val like = model.findSynonyms("现在", 40)
    ////    for ((item, cos) <- like) {
    ////      println(s"$item  $cos")
    ////    }

    //val sameModel = Word2VecModel.load(sc, "word2vec模型路径")
  }

  def kmeans(sc:SparkContext)={
    val data = sc.textFile("songOutput/64561.txt")
    val parsedData = data.map(s => Vectors.dense(s.split(',').map(_.toDouble))).cache()
    val model1=word2VecRun(sc);
//    val array:Array[Vector] = model1.
//    val parsedData=sc.parallelize(array,1)

    // Cluster the data into two classes using KMeans
    val numClusters = 2
    val numIterations = 20
    val clusters = KMeans.train(parsedData, numClusters, numIterations)

    // Evaluate clustering by computing Within Set Sum of Squared Errors
    val WSSSE = clusters.computeCost(parsedData)
    println(s"Within Set Sum of Squared Errors = $WSSSE")

    // Save and load model
//    clusters.save(sc, "kmeansModel")
//    val sameModel = KMeansModel.load(sc, "target/org/apache/spark/KMeansExample/KMeansModel")
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
    model.productFeatures.collect().foreach{
      println
    }
    model.userFeatures.collect().foreach{
      println
    }

    // Save and load model
//    model.save(sc, "filterModel")
//    val sameModel = MatrixFactorizationModel.load(sc, "target/tmp/myCollaborativeFilter")
  }


  def main(args:Array[String])={
    val sparkConf = new SparkConf().setAppName("netease").setMaster("local")
    val sc= new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    filtering(sc)
//    kmeans(sc)
//    val s=word2VecRun(sc)
  }

}