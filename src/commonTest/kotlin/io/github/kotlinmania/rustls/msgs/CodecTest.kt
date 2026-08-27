// port-lint: tests rustls/src/msgs/codec.rs
package io.github.kotlinmania.rustls.msgs

import io.github.kotlinmania.rustls.InvalidMessage
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CodecTest {
    @Test
    fun interruptedLengthPrefixedBufferLeavesMaximumLength() {
        val buf = OutputBuffer()
        val nested = LengthPrefixedBuffer(ListLength.U16, buf)
        nested.buf.add(0xaa.toByte())
        assertEquals(3, nested.buf.size)
        assertEquals(0xff.toByte(), nested.buf[0])
        assertEquals(0xff.toByte(), nested.buf[1])
        assertEquals(0xaa.toByte(), nested.buf[2])

        nested.finish()
        assertEquals(3, buf.size)
        assertEquals(0x00.toByte(), buf[0])
        assertEquals(0x01.toByte(), buf[1])
        assertEquals(0xaa.toByte(), buf[2])
    }

    @Test
    fun readerBasics() {
        val bytes = byteArrayOf(1, 2, 3, 4, 5)
        val reader = Reader(bytes)

        assertEquals(0, reader.used())
        assertEquals(5, reader.left())
        assertTrue(reader.anyLeft())

        val sub = reader.sub(2)
        assertEquals(2, reader.used())
        assertEquals(3, reader.left())
        assertEquals(2, sub.left())
        assertContentEquals(byteArrayOf(1, 2), sub.rest())

        val next = reader.take(2)
        assertContentEquals(byteArrayOf(3, 4), next)

        val rest = reader.rest()
        assertContentEquals(byteArrayOf(5), rest)
        assertFalse(reader.anyLeft())
        reader.expectEmpty("test")

        assertNull(reader.take(1))
    }

    @Test
    fun readerExpectEmptyFailsOnTrailing() {
        val reader = Reader(byteArrayOf(1))
        assertFailsWith<InvalidMessage.TrailingData> {
            reader.expectEmpty("test")
        }
    }

    @Test
    fun primitivesRoundTrip() {
        val buf = OutputBuffer()
        Codecs.encodeU8(42u, buf)
        Codecs.encodeU16(1234u, buf)
        Codecs.encodeU24(U24(56789u), buf)
        Codecs.encodeU32(987654321u, buf)
        Codecs.encodeU64(1234567890123456789uL, buf)

        val reader = Reader(buf.toByteArray())
        assertEquals(42u.toUByte(), Codecs.readU8(reader))
        assertEquals(1234u.toUShort(), Codecs.readU16(reader))
        assertEquals(U24(56789u), Codecs.readU24(reader))
        assertEquals(987654321u, Codecs.readU32(reader))
        assertEquals(1234567890123456789uL, Codecs.readU64(reader))
        reader.expectEmpty("primitives")
    }
}
