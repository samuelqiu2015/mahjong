package websiteschema.mpsegment.crf

class CRFModel {

  val windowSize = 1
  val labelCount = 2
  val featuresCount = 26
  val weights: Array[Double] = new Array[Double](featuresCount * labelCount)
  val tolerance = 1.0E-4

  def getLabelCount(feature: Int) = labelCount
}

object CRFUtils {

  def empty2DArray(i: Int, j: Int) = {
    val array = new Array[Array[Double]](i)
    for(index <- 0 until i) {
      array(index) = new Array[Double](j)
    }
    array
  }

  /*
   *  log(exp(lx) + exp(ly))
   */
  def logAdd(lx: Double, ly: Double) = {

    var max: Double = 0D
    var negDiff: Double = 0D
    if (lx > ly) {
      max = lx
      negDiff = ly - lx
    } else {
      max = ly
      negDiff = lx - ly
    }
    if (max == Double.NegativeInfinity) {
      max
    } else if (negDiff < -30.0) {
      max
    } else {
      max + Math.log(1.0 + Math.exp(negDiff))
    }
  }

}

case class CRFDocument(val data: Array[Array[Int]], val label: Array[Int])

class CRFCorpus(val docs: Array[CRFDocument], model: CRFModel) {

  val Ehat: Array[Array[Double]] = {
    val featureArray = CRFUtils.empty2DArray(model.featuresCount, model.labelCount)

    for(doc_i <- docs) {
      for(t <- 0 until doc_i.data.length; k <- doc_i.data(t)) {
        featureArray(k)(doc_i.label(t)) += 1.0D
      }
    }

    featureArray
  }

}

object CRFModel {

  def build(corpus: CRFCorpus) = {
    val model = new CRFModel

    val func = new CRFDiffFunc(corpus, model)

    for(iter <- 0 until 10) {
      val value = func.valueAt(model.weights)
      val grad = if(iter < 3) 0.1 else 1.0
      for (i <- 0 until model.weights.length) {
        model.weights(i) = model.weights(i) + grad * func.derivative(i)
      }
      val sum = func.derivative.sum
      println(s"Iteration $iter: $value, $sum")
    }

    model
  }
}