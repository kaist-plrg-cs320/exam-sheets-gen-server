package cs320

import java.io.ByteArrayOutputStream

import org.apache.pdfbox.pdmodel.{PDDocument, PDPage, PDPageContentStream}
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject

class ExamSheet(id: String, aes: AES256) {

  private val document = new PDDocument
  private val font = PDType1Font.TIMES_ROMAN

  private val info = document.getDocumentInformation
  info.setTitle(id)

  def addPage(
    questionNo: String,
    year: Int,
    semester: String,
    exam: String
  ) = {
    val page = new PDPage(PDRectangle.A4)
    val stream = new PDPageContentStream(document, page)

    val plain = s"$year-$semester-$exam-$id-$questionNo"
    val encrypted = aes.encrypt(plain)
    val arr = QRUtil.generate(encrypted, 1280, 1280)
    val qrImage = PDImageXObject.createFromByteArray(document, arr, null)
    stream.drawImage(qrImage, 420, 670, 160, 160)

    stream.beginText()
    stream.setFont(font, 30)
    stream.newLineAtOffset(70, 780)
    stream.showText(s"CS320 $exam, $semester $year")
    stream.endText()

    stream.beginText()
    stream.setFont(font, 30)
    stream.newLineAtOffset(100, 730)
    stream.showText(s"Student ID: $id")
    stream.endText()

    stream.beginText()
    stream.setFont(font, 10)
    stream.newLineAtOffset(165, 690)
    stream.showText("Write your answer below this line.")
    stream.endText()

    stream.beginText()
    stream.setFont(font, 20)
    stream.newLineAtOffset(25, 650)
    stream.showText(s"$questionNo)")
    stream.endText()

    stream.moveTo(15, 680)
    stream.lineTo(580, 680)
    stream.stroke()

    stream.close()

    document.addPage(page)
  }

  def toByteArray: Array[Byte] = {
    val out = new ByteArrayOutputStream
    document.save(out)
    out.toByteArray
  }

  def close(): Unit = document.close()

}
