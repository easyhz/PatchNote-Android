package com.easyhz.patchnote.data.util

import android.content.Context
import android.os.Environment
import com.easyhz.patchnote.R
import com.easyhz.patchnote.core.model.defect.ExportDefect
import com.easyhz.patchnote.core.model.defect.ExportDefectHeader
import dagger.hilt.android.qualifiers.ApplicationContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ExportUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun exportDefects(defects: List<ExportDefect>): File {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet1")

        val headerRow = sheet.createRow(0)
        ExportDefectHeader.entries.forEachIndexed { index, header ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(context.getString(header.resId))
        }
        defects.forEachIndexed { rowIndex, rowData ->
            val row = sheet.createRow(rowIndex + 1)

            rowData.toPair(context).forEachIndexed { colIndex, cellData ->
                val cell = row.createCell(colIndex)
                cell.setCellValue(cellData.second)
            }
        }

        val fileName = getFileName()
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, fileName)
        FileOutputStream(file).use { fos ->
            workbook.write(fos)
            workbook.close()
        }

        return file
    }

    private fun getFileName(): String {
        val format = SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault())
        return "${context.getString(R.string.export_defect_list)}_${format.format(Date())}.xlsx"
    }
}