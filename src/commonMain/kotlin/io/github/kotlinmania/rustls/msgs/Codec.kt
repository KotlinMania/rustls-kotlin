// port-lint: source msgs/codec.rs
package io.github.kotlinmania.rustls.msgs

import io.github.kotlinmania.rustls.InvalidMessage

/**
 * Output buffer for encoding TLS data structures.
 */
class OutputBuffer {
    private val buffer: MutableList<Byte>

    constructor() {
        this.buffer = mutableListOf()
    }

    constructor(initialCapacity: Int) {
        this.buffer = ArrayList(initialCapacity)
    }

    val size: Int get() = buffer.size

    fun add(byte: Byte) {
        buffer.add(byte)
    }

    fun add(bytes: ByteArray) {
        for (b in bytes) {
            buffer.add(b)
        }
    }

    fun toByteArray(): ByteArray = buffer.toByteArray()

    operator fun get(index: Int): Byte = buffer[index]

    operator fun set(index: Int, value: Byte) {
        buffer[index] = value
    }
}

/**
 * Wrapper over a slice of bytes that allows reading chunks from
 * with the current position state held using a cursor.
 */
class Reader(
    public val buffer: ByteArray,
    public var cursor: Int = 0,
) {
    /**
     * Attempts to create a new Reader on a sub section of this
     * reader's bytes by taking a slice of the provided [length].
     */
    fun sub(length: Int): Reader {
        val bytes = take(length) ?: throw InvalidMessage.MessageTooShort
        return Reader(bytes, 0)
    }

    /**
     * Returns a slice of all the remaining bytes
     * that appear after the cursor position.
     * Moves the cursor to the end of the buffer length.
     */
    fun rest(): ByteArray {
        val remaining = buffer.copyOfRange(cursor, buffer.size)
        cursor = buffer.size
        return remaining
    }

    /**
     * Attempts to read a slice of bytes from the current
     * cursor position of [length]. If there is not enough
     * bytes remaining after the cursor, null is returned instead.
     */
    fun take(length: Int): ByteArray? {
        if (left() < length) {
            return null
        }
        val current = cursor
        cursor += length
        return buffer.copyOfRange(current, current + length)
    }

    /**
     * Checks whether the reader has any content left after the cursor.
     */
    fun anyLeft(): Boolean = cursor < buffer.size

    /**
     * Expects no trailing data in the reader, throwing [InvalidMessage.TrailingData] if any remains.
     */
    fun expectEmpty(name: String) {
        if (anyLeft()) {
            throw InvalidMessage.TrailingData(name)
        }
    }

    /**
     * Returns the cursor position which is also the number
     * of bytes that have been read from the buffer.
     */
    fun used(): Int = cursor

    /**
     * Returns the number of bytes that are still able to be read.
     */
    fun left(): Int = buffer.size - cursor

    companion object {
        fun init(bytes: ByteArray): Reader = Reader(bytes, 0)
    }
}

/**
 * Trait for implementing encoding and decoding functionality.
 */
interface Codec<T> {
    /**
     * Function for encoding a value by appending bytes to the provided [output] buffer.
     */
    fun encode(value: T, output: OutputBuffer)

    /**
     * Function for reading a value from a reader.
     */
    fun read(reader: Reader): T

    /**
     * Convenience function for encoding the value into a byte array.
     */
    fun getEncoding(value: T): ByteArray {
        val buf = OutputBuffer()
        encode(value, buf)
        return buf.toByteArray()
    }

    /**
     * Convenience function for decoding a value from a byte array.
     */
    fun readBytes(bytes: ByteArray): T = read(Reader(bytes))
}

/**
 * Primitive codecs.
 */
object CodecU8 : Codec<UByte> {
    override fun encode(value: UByte, output: OutputBuffer) {
        output.add(value.toByte())
    }

    override fun read(reader: Reader): UByte {
        val b = reader.take(1) ?: throw InvalidMessage.MissingData("u8")
        return b[0].toUByte()
    }
}

object CodecU16 : Codec<UShort> {
    override fun encode(value: UShort, output: OutputBuffer) {
        val v = value.toInt()
        output.add(((v ushr 8) and 0xff).toByte())
        output.add((v and 0xff).toByte())
    }

    override fun read(reader: Reader): UShort {
        val b = reader.take(2) ?: throw InvalidMessage.MissingData("u16")
        val v = ((b[0].toInt() and 0xff) shl 8) or (b[1].toInt() and 0xff)
        return v.toUShort()
    }
}

object CodecU24 : Codec<U24> {
    override fun encode(value: U24, output: OutputBuffer) {
        val v = value.value.toInt()
        output.add(((v ushr 16) and 0xff).toByte())
        output.add(((v ushr 8) and 0xff).toByte())
        output.add((v and 0xff).toByte())
    }

    override fun read(reader: Reader): U24 {
        val b = reader.take(3) ?: throw InvalidMessage.MissingData("u24")
        val v =
            ((b[0].toInt() and 0xff) shl 16) or
                ((b[1].toInt() and 0xff) shl 8) or
                (b[2].toInt() and 0xff)
        return U24(v.toUInt())
    }
}

object CodecU32 : Codec<UInt> {
    override fun encode(value: UInt, output: OutputBuffer) {
        val v = value.toLong()
        output.add(((v ushr 24) and 0xff).toByte())
        output.add(((v ushr 16) and 0xff).toByte())
        output.add(((v ushr 8) and 0xff).toByte())
        output.add((v and 0xff).toByte())
    }

    override fun read(reader: Reader): UInt {
        val b = reader.take(4) ?: throw InvalidMessage.MissingData("u32")
        val v =
            ((b[0].toLong() and 0xff) shl 24) or
                ((b[1].toLong() and 0xff) shl 16) or
                ((b[2].toLong() and 0xff) shl 8) or
                (b[3].toLong() and 0xff)
        return v.toUInt()
    }
}

object CodecU64 : Codec<ULong> {
    override fun encode(value: ULong, output: OutputBuffer) {
        val v = value.toLong()
        for (i in 7 downTo 0) {
            output.add(((v ushr (i * 8)) and 0xff).toByte())
        }
    }

    override fun read(reader: Reader): ULong {
        val b = reader.take(8) ?: throw InvalidMessage.MissingData("u64")
        var v = 0L
        for (i in 0..7) {
            v = (v shl 8) or (b[i].toLong() and 0xff)
        }
        return v.toULong()
    }
}

object Codecs {
    fun encodeU8(value: UByte, bytes: OutputBuffer) = CodecU8.encode(value, bytes)

    fun readU8(r: Reader): UByte = CodecU8.read(r)

    fun encodeU16(value: UShort, bytes: OutputBuffer) = CodecU16.encode(value, bytes)

    fun readU16(r: Reader): UShort = CodecU16.read(r)

    fun putU16(v: UShort, out: ByteArray, offset: Int = 0) {
        val intV = v.toInt()
        out[offset] = ((intV ushr 8) and 0xff).toByte()
        out[offset + 1] = (intV and 0xff).toByte()
    }

    fun encodeU24(value: U24, bytes: OutputBuffer) = CodecU24.encode(value, bytes)

    fun readU24(r: Reader): U24 = CodecU24.read(r)

    fun encodeU32(value: UInt, bytes: OutputBuffer) = CodecU32.encode(value, bytes)

    fun readU32(r: Reader): UInt = CodecU32.read(r)

    fun encodeU64(value: ULong, bytes: OutputBuffer) = CodecU64.encode(value, bytes)

    fun readU64(r: Reader): ULong = CodecU64.read(r)

    fun putU64(v: ULong, bytes: ByteArray, offset: Int = 0) {
        val longV = v.toLong()
        for (i in 0..7) {
            bytes[offset + i] = ((longV ushr ((7 - i) * 8)) and 0xff).toByte()
        }
    }
}

/**
 * 24-bit unsigned integer type representation.
 */
data class U24(
    val value: UInt,
) {
    init {
        require(value <= 0x00ffffffu) { "u24 value exceeds 24 bits: $value" }
    }

    fun toInt(): Int = value.toInt()

    override fun toString(): String = "u24($value)"

    companion object : Codec<U24> by CodecU24 {
        val ZERO = U24(0u)
        val MAX = U24(0x00ffffffu)
    }
}

/**
 * Trait for types that can be encoded and decoded in a list.
 */
interface TlsListElement {
    val sizeLen: ListLength
}

/**
 * The length of the length prefix for a list.
 */
sealed class ListLength {
    data class NonZeroU8(
        val emptyError: InvalidMessage,
    ) : ListLength()

    data object U16 : ListLength()

    data class NonZeroU16(
        val emptyError: InvalidMessage,
    ) : ListLength()

    data class U24(
        val max: Int,
        val error: InvalidMessage,
    ) : ListLength()

    fun read(r: Reader): Int =
        when (this) {
            is NonZeroU8 -> {
                val len = Codecs.readU8(r).toInt()
                if (len == 0) throw emptyError
                len
            }
            is U16 -> Codecs.readU16(r).toInt()
            is NonZeroU16 -> {
                val len = Codecs.readU16(r).toInt()
                if (len == 0) throw emptyError
                len
            }
            is U24 -> {
                val len = Codecs.readU24(r).toInt()
                if (len > max) throw error
                len
            }
        }
}

/**
 * Tracks encoding a length-delimited structure in a single pass.
 */
class LengthPrefixedBuffer(
    val sizeLen: ListLength,
    val buf: OutputBuffer,
) {
    private val lenOffset: Int = buf.size

    init {
        when (sizeLen) {
            is ListLength.NonZeroU8 -> {
                buf.add(0xff.toByte())
            }
            is ListLength.U16, is ListLength.NonZeroU16 -> {
                buf.add(0xff.toByte())
                buf.add(0xff.toByte())
            }
            is ListLength.U24 -> {
                buf.add(0xff.toByte())
                buf.add(0xff.toByte())
                buf.add(0xff.toByte())
            }
        }
    }

    fun finish() {
        when (sizeLen) {
            is ListLength.NonZeroU8 -> {
                val len = buf.size - lenOffset - 1
                check(len <= 0xff) { "payload exceeds u8 length: $len" }
                buf[lenOffset] = len.toByte()
            }
            is ListLength.U16, is ListLength.NonZeroU16 -> {
                val len = buf.size - lenOffset - 2
                check(len <= 0xffff) { "payload exceeds u16 length: $len" }
                buf[lenOffset] = ((len ushr 8) and 0xff).toByte()
                buf[lenOffset + 1] = (len and 0xff).toByte()
            }
            is ListLength.U24 -> {
                val len = buf.size - lenOffset - 3
                check(len <= 0xffffff) { "payload exceeds u24 length: $len" }
                buf[lenOffset] = ((len ushr 16) and 0xff).toByte()
                buf[lenOffset + 1] = ((len ushr 8) and 0xff).toByte()
                buf[lenOffset + 2] = (len and 0xff).toByte()
            }
        }
    }
}
