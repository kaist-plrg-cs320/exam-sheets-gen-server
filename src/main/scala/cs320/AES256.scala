package cs320

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

import scala.collection.mutable.StringBuilder

import org.apache.commons.codec.binary.Base64

class AES256(_secret: String) {

  private val encoding = "UTF-8"
  private val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

  private val secret = {
    val len = 16
    val builder = new StringBuilder
    while (builder.length < len) {
      builder ++= _secret
    }
    builder.take(len).toString
  }
  private val secretBytes = secret.getBytes(encoding)
  private val keySpec = new SecretKeySpec(secretBytes, "AES");
  private val paramSpec = new IvParameterSpec(secretBytes)

  def encrypt(msg: String): String = {
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec)
    val encrypted = cipher.doFinal(msg.getBytes(encoding))
    Base64.encodeBase64String(encrypted)
  }
}
