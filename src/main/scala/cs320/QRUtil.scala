package cs320

import java.io.ByteArrayOutputStream

import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.client.j2se.MatrixToImageWriter

object QRUtil {

  def generate(msg: String, w: Int, h: Int): Array[Byte] = {
    val writer = new QRCodeWriter
    val matrix = writer.encode(msg, BarcodeFormat.QR_CODE, w, h)
    val out = new ByteArrayOutputStream
    MatrixToImageWriter.writeToStream(matrix, "PNG", out)
    out.toByteArray
  }

}
