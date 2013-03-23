//package websiteschema.mpsegment.tools.accurary
//
//import websiteschema.mpsegment.core.WordAtom
//import websiteschema.mpsegment.dict.POSUtil
//
//class NerPlaceErrorAnalyzer extends AbstractErrorAnalyzer {
//
//    override def analysis(expect: WordAtom, possibleErrorWord: String) : Boolean = {
//        var foundError = false
//        if (possibleErrorWord.replaceAll(" ", "").equals(expect.word)) {
//            if (expect.pos == POSUtil.POS_NS) {
//                increaseOccur()
//                addErrorWord(expect.word)
//                foundError = true
//            }
//        }
//        return foundError
//    }
//}