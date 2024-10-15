package com.easyhz.patchnote.model.defect

import com.easyhz.patchnote.core.model.defect.EntryDefect
import org.junit.Assert.assertEquals
import org.junit.Test

class EntryDefectTest {
    @Test
    fun `test create entry defect`() {
        val entryDefect = EntryDefect(
            id = "id",
            site = "site",
            building = "building",
            unit = "unit",
            space = "space",
            part = "part",
            workType = "workType",
            thumbnailUrl = "thumbnailUrl",
            beforeDescription = "beforeDescription",
            beforeImageUrls = emptyList(),
            beforeImageSizes = emptyList(),
            requesterId = "requesterId",
            requesterName = "requesterName",
            requesterPhone = "requesterPhone",
        )

        val searchList = entryDefect.exportSearch()

        assertEquals(127, searchList.size)
        assert("building=building||unit=unit||space=space||requesterName=requesterName" in searchList)
        assert("site=site||requesterName=requesterName" in searchList)
        assertEquals("site=site||building=building||unit=unit||space=space||part=part||workType=workType||requesterName=requesterName", searchList.last())

    }
}