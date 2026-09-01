// port-lint: tests msgs/base.rs
package io.github.kotlinmania.rustls.msgs

import io.github.kotlinmania.rustls.AlertDescription
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class BaseAndAlertTest {
    @Test
    fun testPayloadCodec() {
        val bytes = byteArrayOf(1, 2, 3, 4)
        val payload = Payload(bytes)
        assertContentEquals(bytes, payload.bytes)
        assertContentEquals(bytes, payload.intoVec())

        val encoded = Payload.getEncoding(payload)
        assertContentEquals(bytes, encoded)

        val decoded = Payload.readBytes(encoded)
        assertEquals(payload, decoded)
    }

    @Test
    fun testPayloadU24Codec() {
        val bytes = byteArrayOf(10, 20, 30)
        val p24 = PayloadU24(bytes)
        val encoded = PayloadU24.getEncoding(p24)
        assertEquals(6, encoded.size) // 3 bytes length + 3 bytes payload
        assertEquals(0, encoded[0])
        assertEquals(0, encoded[1])
        assertEquals(3, encoded[2])
        assertEquals(10, encoded[3])
        assertEquals(20, encoded[4])
        assertEquals(30, encoded[5])

        val decoded = PayloadU24.readBytes(encoded)
        assertEquals(p24, decoded)
    }

    @Test
    fun testPayloadU16Codec() {
        val bytes = byteArrayOf(5, 6, 7)
        val p16 = PayloadU16(bytes)
        val encoded = PayloadU16.getEncoding(p16)
        assertEquals(5, encoded.size) // 2 bytes length + 3 bytes payload
        assertEquals(0, encoded[0])
        assertEquals(3, encoded[1])

        val decoded = PayloadU16.readBytes(encoded)
        assertEquals(p16, decoded)
    }

    @Test
    fun testPayloadU8Codec() {
        val bytes = byteArrayOf(9, 8)
        val p8 = PayloadU8(bytes)
        val encoded = PayloadU8.getEncoding(p8)
        assertEquals(3, encoded.size) // 1 byte length + 2 bytes payload
        assertEquals(2, encoded[0])

        val decoded = PayloadU8.readBytes(encoded)
        assertEquals(p8, decoded)
    }

    @Test
    fun testAlertMessagePayloadCodec() {
        val alert = AlertMessagePayload(AlertLevel.Fatal, AlertDescription.HandshakeFailure)
        val encoded = AlertMessagePayload.getEncoding(alert)
        assertEquals(2, encoded.size)
        assertEquals(AlertLevel.Fatal.value.toByte(), encoded[0])
        assertEquals(AlertDescription.HandshakeFailure.value.toByte(), encoded[1])

        val decoded = AlertMessagePayload.readBytes(encoded)
        assertEquals(alert, decoded)
    }
}
