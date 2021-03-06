package me.yingrui.segment.crf

import me.yingrui.segment.core.{SegmentResult, SegmentWorker}

import scala.collection.mutable.ListBuffer

class CRFSegmentWorker(model: CRFModel) extends SegmentWorker {

  def segment(sen: String): SegmentResult = {
    val document = CRFDocument(sen, model)

    val classifier = new CRFViterbi(model)

    val labels = classifier.calculateResult(document.data).getBestPath.map(l => model.labelRepository.getFeature(l))
    val bestCuts = labels.map(label => if(label=="S" || label=="B") 1 else 0)

    var from = 0
    val words = new ListBuffer[String]()
    for(i <- 0 until sen.length) {
      if(bestCuts(i) == 1) {
        val word = sen.substring(from, i)
        if (!word.isEmpty) words += word
        from = i
      }
      if (i + 1 == sen.length) {
        words += sen.substring(from)
      }
    }

    val result = new SegmentResult(words.length)
    result.setWords(words.toArray)
    result
  }

  def tokenize(sen: String): Array[String] = segment(sen).getWords().map(_.name)

}

object CRFSegmentWorker {

  def apply(modelResource: String): SegmentWorker = new CRFSegmentWorker(CRFModel(modelResource))

  def apply(model: CRFModel): SegmentWorker = new CRFSegmentWorker(model)

}