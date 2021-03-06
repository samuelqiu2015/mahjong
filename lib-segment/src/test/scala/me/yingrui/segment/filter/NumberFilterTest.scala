package me.yingrui.segment.filter

import org.junit.Assert
import org.junit.Test
import me.yingrui.segment.core.SegmentResult
import me.yingrui.segment.dict.POSUtil

class NumberFilterTest {

    var filter = new NumberAndTimeFilter()

    @Test
    def should_merge_two_word_with_POS_M() {
        val segmentResult = new SegmentResult(6)
        segmentResult.setWords(List[String]("一个", "几十", "万", "人口", "的", "社区").toArray)
        segmentResult.setPOSArray(List[Int](POSUtil.POS_M, POSUtil.POS_M, POSUtil.POS_M, POSUtil.POS_N, POSUtil.POS_U, POSUtil.POS_N).toArray)
        segmentResult.setDomainTypes(List[Int](0, 0, 0, 0, 0, 0).toArray)
        segmentResult.setConcepts(List[String]("N/A", "N/A", "N/A", "N/A", "N/A", "N/A").toArray)

        filter.setSegmentResult(segmentResult)
        filter.filtering()
        Assert.assertEquals("一个", segmentResult.getWord(0))
        Assert.assertEquals("几十万", segmentResult.getWord(1))
        Assert.assertEquals(POSUtil.POS_M, segmentResult.getPOS(0))
        Assert.assertEquals(POSUtil.POS_M, segmentResult.getPOS(1))
    }

    @Test
    def should_merge_two_word_with_POS_T() {
        val segmentResult = new SegmentResult(6)
        segmentResult.setWords(List[String]("本报", "北京", "１", "月", "１", "日").toArray)
        segmentResult.setPOSArray(List[Int](POSUtil.POS_R, POSUtil.POS_NS, POSUtil.POS_M, POSUtil.POS_T, POSUtil.POS_M, POSUtil.POS_T).toArray)
        segmentResult.setDomainTypes(List[Int](0, 0, 0, 0, 0, 0).toArray)
        segmentResult.setConcepts(List[String]("N/A", "N/A", "N/A", "N/A", "N/A", "N/A").toArray)

        filter.setSegmentResult(segmentResult)
        filter.filtering()
        Assert.assertEquals("１月", segmentResult.getWord(2))
        Assert.assertEquals("１日", segmentResult.getWord(3))
        Assert.assertEquals(POSUtil.POS_T, segmentResult.getPOS(2))
        Assert.assertEquals(POSUtil.POS_T, segmentResult.getPOS(3))
    }

}
